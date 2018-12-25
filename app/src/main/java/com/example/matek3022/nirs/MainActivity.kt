package com.example.matek3022.nirs

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import java.io.ByteArrayOutputStream

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val options = BitmapFactory.Options()
        options.outConfig = Bitmap.Config.ARGB_8888
        options.inPreferredConfig = Bitmap.Config.ARGB_8888
        options.inMutable = true
        var c = BitmapFactory.decodeResource(resources, R.drawable.img, options)
        val text = "Testtext"
        c.codeText(text)

        val os = ByteArrayOutputStream()
        c.compress(Bitmap.CompressFormat.PNG, 50, os)
        val array = os.toByteArray()
        c = BitmapFactory.decodeByteArray(array, 0, array.size, options)
        val outText = c.getText()
//        val height = c.height
//        val width = c.width
//        val pixels = c.getPixels()
//
//        val dct = pixels.toDct()
//        val fromDct = dct.fromDct()
//        var diff = 0f
//        var maxDiff = 0
//        pixels.forEachIndexed { index1, arrayList ->
//            arrayList.forEachIndexed { index2, pixel ->
//                val currDiff = pixel.maxDiff(fromDct[index1][index2])
//                if (currDiff > maxDiff) maxDiff = currDiff
//                diff += pixel.compare(fromDct[index1][index2])
//            }
//        }
//        val fromDctPicture = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
//
//        fromDctPicture.setPixels(fromDct)
    }
}
