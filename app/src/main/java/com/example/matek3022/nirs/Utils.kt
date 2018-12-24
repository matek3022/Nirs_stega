package com.example.matek3022.nirs

import android.graphics.Bitmap
import android.graphics.Color
import java.util.*
import kotlin.collections.ArrayList

/**
 * if true then 1
 * if false then 0
 */

const val seed = 4L

fun Bitmap.codeText(text: String) {
    val bitArray = ArrayList<Boolean>()
    text.toByteArray().forEach {
        bitArray.addAll(getBits(it).toList())
    }
    val pixels = getPixels()
    pixels.inToPixels(bitArray)
    setPixels(pixels, 1)
}

fun Bitmap.getText(): String {
    val pixels = getPixels()
    val bits = pixels.fromPixels()
    val byteArray = ByteArray(bits.size / 8)
    for (i in 0 until bits.size / 8) {
        byteArray[i] = getByte(BooleanArray(8) {
            bits[i * 8 + it]
        })
    }
    return String(byteArray)
}

fun ArrayList<ArrayList<Pixel>>.inToPixels(bitsArray: ArrayList<Boolean>) {
    val n = this.size
    val m = this[0].size
//    val res = ArrayList<ArrayList<Pixel>>()
    val matr8x8Red = FloatArray(8 * 8)
    val matr8x8Green = FloatArray(8 * 8)
    val matr8x8Blue = FloatArray(8 * 8)
    val bitsCount = bitsArray.size
    val random = Random(seed)
    val blockNcount = n / 8
    val blockMcount = m / 8
    val maxBlocks = blockNcount * blockMcount
    val randomIndexes = IntArray(bitsCount) { i -> -1 }
    randomIndexes.forEachIndexed { index, i ->
        var curr = -1
        while (randomIndexes.contains(curr)) {
            curr = random.nextInt(maxBlocks - 1)
        }
        randomIndexes[index] = curr
    }

    var bitsInput = 0

//    for (i in 0 until n) {
//        res.add(ArrayList())
//        for (j in 0 until m) {
//            res[i].add(Pixel(0, 0, 0))
//        }
//    }
    for (i in 0 until blockNcount) {
        for (j in 0 until blockMcount) {

            if (bitsInput < bitsCount) {
                val currIndex = i * blockMcount + j
                val findingIndex = randomIndexes.indexOf(currIndex)
                if (findingIndex != -1) {
                    for (ik in 0..7) {
                        for (jk in 0..7) {
                            matr8x8Red[ik * 8 + jk] = this[i * 8 + ik][j * 8 + jk].red.toFloat()
                            matr8x8Green[ik * 8 + jk] = this[i * 8 + ik][j * 8 + jk].green.toFloat()
                            matr8x8Blue[ik * 8 + jk] = this[i * 8 + ik][j * 8 + jk].blue.toFloat()
                        }
                    }
                    //todo понять почему в 64 раза больше яркость получается и разобраться куда теряется бит
                    Dct.forwardDCT8x8(matr8x8Red)
                    Dct.forwardDCT8x8(matr8x8Green)
                    Dct.forwardDCT8x8(matr8x8Blue)
                    val bit = bitsArray[findingIndex]
                    val lastBit = Math.abs(Math.round(matr8x8Blue[0]) % 2)
//                    matr8x8Blue[0] = (Math.round(matr8x8Blue[0]) / 64).toFloat()
                    if (lastBit == 1) {
                        if (!bit) matr8x8Blue[0] = (Math.round(matr8x8Blue[0]) + 1).toFloat()
                    } else {
                        if (bit) matr8x8Blue[0] = (Math.round(matr8x8Blue[0]) + 1).toFloat()
                    }
                    bitsInput++
                    Dct.inverseDCT8x8(matr8x8Red)
                    Dct.inverseDCT8x8(matr8x8Green)
                    Dct.inverseDCT8x8(matr8x8Blue)
                    for (ik in 0..7) {
                        for (jk in 0..7) {
                            this[i * 8 + ik][j * 8 + jk].red = Math.round(matr8x8Red[ik * 8 + jk] / 64)
                            this[i * 8 + ik][j * 8 + jk].green = Math.round(matr8x8Green[ik * 8 + jk] / 64)
                            this[i * 8 + ik][j * 8 + jk].blue = Math.round(matr8x8Blue[ik * 8 + jk] / 64)
                        }
                    }
                }
            }
        }
    }
}

fun ArrayList<ArrayList<Pixel>>.fromPixels(): List<Boolean> {
    val n = this.size
    val m = this[0].size
    val matr8x8Red = FloatArray(8 * 8)
    val matr8x8Green = FloatArray(8 * 8)
    val matr8x8Blue = FloatArray(8 * 8)
    val random = Random(seed)
    val blockNcount = n / 8
    val blockMcount = m / 8
    val bitsCount = blockMcount * blockNcount / 4
    val maxBlocks = blockNcount * blockMcount
    val randomIndexes = IntArray(bitsCount) { i -> -1 }
    val res = BooleanArray(bitsCount) { i -> false}.toMutableList()
    randomIndexes.forEachIndexed { index, i ->
        var curr = -1
        while (randomIndexes.contains(curr)) {
            curr = random.nextInt(maxBlocks - 1)
        }
        randomIndexes[index] = curr
    }

    var bitsInput = 0

    for (i in 0 until blockNcount) {
        for (j in 0 until blockMcount) {
            if (bitsInput < bitsCount) {
                if (bitsInput < bitsCount) {
                    val currIndex = i * blockMcount + j
                    val findingIndex = randomIndexes.indexOf(currIndex)
                    if (findingIndex != -1) {
                        for (ik in 0..7) {
                            for (jk in 0..7) {
                                matr8x8Red[ik * 8 + jk] = this[i * 8 + ik][j * 8 + jk].red.toFloat()
                                matr8x8Green[ik * 8 + jk] = this[i * 8 + ik][j * 8 + jk].green.toFloat()
                                matr8x8Blue[ik * 8 + jk] = this[i * 8 + ik][j * 8 + jk].blue.toFloat()
                            }
                        }
                        Dct.forwardDCT8x8(matr8x8Red)
                        Dct.forwardDCT8x8(matr8x8Green)
                        Dct.forwardDCT8x8(matr8x8Blue)
                        val lastBit = Math.abs(Math.round(matr8x8Blue[0] % 2))
                        res[findingIndex] = lastBit == 1
                        bitsInput++
                    }
                }
            }
        }
    }
    return res
}

fun ArrayList<ArrayList<Pixel>>.toDct(): ArrayList<ArrayList<Pixel>> {
    val n = this.size
    val m = this[0].size
    val res = ArrayList<ArrayList<Pixel>>()
    val matr8x8Red = FloatArray(8 * 8)
    val matr8x8Green = FloatArray(8 * 8)
    val matr8x8Blue = FloatArray(8 * 8)
    val blockNcount = n / 8
    val blockMcount = m / 8
    for (i in 0 until n) {
        res.add(ArrayList())
        for (j in 0 until m) {
            res[i].add(Pixel(0, 0, 0))
        }
    }
    for (i in 0 until blockNcount) {
        for (j in 0 until blockMcount) {
            for (ik in 0..7) {
                for (jk in 0..7) {
                    matr8x8Red[ik * 8 + jk] = this[i * 8 + ik][j * 8 + jk].red.toFloat()
                    matr8x8Green[ik * 8 + jk] = this[i * 8 + ik][j * 8 + jk].green.toFloat()
                    matr8x8Blue[ik * 8 + jk] = this[i * 8 + ik][j * 8 + jk].blue.toFloat()
                }
            }
            Dct.forwardDCT8x8(matr8x8Red)
            Dct.forwardDCT8x8(matr8x8Green)
            Dct.forwardDCT8x8(matr8x8Blue)
            for (ik in 0..7) {
                for (jk in 0..7) {
                    res[i * 8 + ik][j * 8 + jk].red = Math.round(matr8x8Red[ik * 8 + jk])
                    res[i * 8 + ik][j * 8 + jk].green = Math.round(matr8x8Green[ik * 8 + jk])
                    res[i * 8 + ik][j * 8 + jk].blue = Math.round(matr8x8Blue[ik * 8 + jk])
                }
            }
        }
    }
    return res
}

fun ArrayList<ArrayList<Pixel>>.fromDct(): ArrayList<ArrayList<Pixel>> {
    val n = this.size
    val m = this[0].size
    val res = ArrayList<ArrayList<Pixel>>()
    val matr8x8Red = FloatArray(8 * 8)
    val matr8x8Green = FloatArray(8 * 8)
    val matr8x8Blue = FloatArray(8 * 8)

    val blockNcount = n / 8
    val blockMcount = m / 8
    for (i in 0 until n) {
        res.add(ArrayList())
        for (j in 0 until m) {
            res[i].add(Pixel(0, 0, 0))
        }
    }
    for (i in 0 until blockNcount) {
        for (j in 0 until blockMcount) {
            for (ik in 0..7) {
                for (jk in 0..7) {
                    matr8x8Red[ik * 8 + jk] = this[i * 8 + ik][j * 8 + jk].red.toFloat()
                    matr8x8Green[ik * 8 + jk] = this[i * 8 + ik][j * 8 + jk].green.toFloat()
                    matr8x8Blue[ik * 8 + jk] = this[i * 8 + ik][j * 8 + jk].blue.toFloat()
                }
            }
            Dct.inverseDCT8x8(matr8x8Red)
            Dct.inverseDCT8x8(matr8x8Green)
            Dct.inverseDCT8x8(matr8x8Blue)
            for (ik in 0..7) {
                for (jk in 0..7) {
                    res[i * 8 + ik][j * 8 + jk].red = Math.round(matr8x8Red[ik * 8 + jk] / (64))
                    res[i * 8 + ik][j * 8 + jk].green = Math.round(matr8x8Green[ik * 8 + jk] / (64))
                    res[i * 8 + ik][j * 8 + jk].blue = Math.round(matr8x8Blue[ik * 8 + jk] / (64))
                }
            }
        }
    }
    return res
}

fun Bitmap.getPixels(): ArrayList<ArrayList<Pixel>> {
    val pixels = ArrayList<ArrayList<Pixel>>()
    for (i in 0 until width) {
        pixels.add(ArrayList())
        for (j in 0 until height) {
            val bitmapPixel = this.getPixel(i, j)
            pixels[i].add(
                Pixel(
                    Color.red(bitmapPixel),
                    Color.green(bitmapPixel),
                    Color.blue(bitmapPixel)
                )
            )
        }
    }
    return pixels
}

fun Bitmap.setPixels(pixels: ArrayList<ArrayList<Pixel>>, corr: Int = 256) {
    for (i in 0 until width) {
        for (j in 0 until height) {
            val pixel = pixels[i][j]
            this.setPixel(i, j, Color.rgb(pixel.red / corr, pixel.green / corr, pixel.blue / corr))
        }
    }
}

fun getBits(byte: Byte): BooleanArray {
    val currByte = byte.toInt() + 128
    val res = BooleanArray(8)
    val bit0 = currByte / 128
    val bit1 = currByte % 128 / 64
    val bit2 = currByte % 128 % 64 / 32
    val bit3 = currByte % 128 % 64 % 32 / 16
    val bit4 = currByte % 128 % 64 % 32 % 16 / 8
    val bit5 = currByte % 128 % 64 % 32 % 16 % 8 / 4
    val bit6 = currByte % 128 % 64 % 32 % 16 % 8 % 4 / 2
    val bit7 = currByte % 128 % 64 % 32 % 16 % 8 % 4 % 2
    res[0] = bit0 > 0
    res[1] = bit1 > 0
    res[2] = bit2 > 0
    res[3] = bit3 > 0
    res[4] = bit4 > 0
    res[5] = bit5 > 0
    res[6] = bit6 > 0
    res[7] = bit7 > 0
    return res
}

fun getByte(arr: BooleanArray): Byte {
    var currInt = 0
    currInt += 128 * if (arr[0]) 1 else 0
    currInt += 64 * if (arr[1]) 1 else 0
    currInt += 32 * if (arr[2]) 1 else 0
    currInt += 16 * if (arr[3]) 1 else 0
    currInt += 8 * if (arr[4]) 1 else 0
    currInt += 4 * if (arr[5]) 1 else 0
    currInt += 2 * if (arr[6]) 1 else 0
    currInt += 1 * if (arr[7]) 1 else 0
    val byte = currInt - 128
    return byte.toByte()
}