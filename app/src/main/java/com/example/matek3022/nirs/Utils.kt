package com.example.matek3022.nirs

import android.graphics.Bitmap
import android.graphics.Color

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
            res[i].add(Pixel(0f, 0f, 0f))
        }
    }
    for (i in 0 until blockNcount) {
        for (j in 0 until blockMcount) {
            for (ik in 0..7) {
                for (jk in 0..7) {
                    matr8x8Red[ik * 8 + jk] = this[i * 8 + ik][j * 8 + jk].red
                    matr8x8Green[ik * 8 + jk] = this[i * 8 + ik][j * 8 + jk].green
                    matr8x8Blue[ik * 8 + jk] = this[i * 8 + ik][j * 8 + jk].blue
                }
            }
            Dct.forwardDCT8x8(matr8x8Red)
            Dct.forwardDCT8x8(matr8x8Green)
            Dct.forwardDCT8x8(matr8x8Blue)
            for (ik in 0..7) {
                for (jk in 0..7) {
                    res[i * 8 + ik][j * 8 + jk].red = matr8x8Red[ik * 8 + jk]
                    res[i * 8 + ik][j * 8 + jk].green = matr8x8Green[ik * 8 + jk]
                    res[i * 8 + ik][j * 8 + jk].blue = matr8x8Blue[ik * 8 + jk]
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
            res[i].add(Pixel(0f, 0f, 0f))
        }
    }
    for (i in 0 until blockNcount) {
        for (j in 0 until blockMcount) {
            for (ik in 0..7) {
                for (jk in 0..7) {
                    matr8x8Red[ik * 8 + jk] = this[i * 8 + ik][j * 8 + jk].red
                    matr8x8Green[ik * 8 + jk] = this[i * 8 + ik][j * 8 + jk].green
                    matr8x8Blue[ik * 8 + jk] = this[i * 8 + ik][j * 8 + jk].blue
                }
            }
            Dct.inverseDCT8x8(matr8x8Red)
            Dct.inverseDCT8x8(matr8x8Green)
            Dct.inverseDCT8x8(matr8x8Blue)
            for (ik in 0..7) {
                for (jk in 0..7) {
                    res[i * 8 + ik][j * 8 + jk].red = (matr8x8Red[ik * 8 + jk] / (64))
                    res[i * 8 + ik][j * 8 + jk].green = (matr8x8Green[ik * 8 + jk] / (64))
                    res[i * 8 + ik][j * 8 + jk].blue = (matr8x8Blue[ik * 8 + jk] / (64))
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
                com.example.matek3022.nirs.Pixel(
                    android.graphics.Color.red(bitmapPixel).toFloat(),
                    android.graphics.Color.green(bitmapPixel).toFloat(),
                    android.graphics.Color.blue(bitmapPixel).toFloat()
                )
            )
        }
    }
    return pixels
}

fun Bitmap.setPixels(pixels: ArrayList<ArrayList<Pixel>>) {
    for (i in 0 until width) {
        for (j in 0 until height) {
            val pixel = pixels[i][j]
            this.setPixel(i, j, Color.rgb(pixel.red / 256, pixel.green / 256, pixel.blue / 256))
        }
    }
}