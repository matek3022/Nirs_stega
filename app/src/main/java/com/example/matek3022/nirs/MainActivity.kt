package com.example.matek3022.nirs

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val options = BitmapFactory.Options()
        options.outConfig = Bitmap.Config.ARGB_8888
        options.inPreferredConfig = Bitmap.Config.ARGB_8888
        var c = BitmapFactory.decodeResource(resources, R.drawable.test, options)
//        val os = ByteArrayOutputStream()
//        c.compress(Bitmap.CompressFormat.JPEG, 100, os)
//        val array = os.toByteArray()
//        c = BitmapFactory.decodeByteArray(array, 0, array.size)
        val height = c.height
        val width = c.width
        val pixels = c.getPixels()

        val dct = pixels.toDct()
        val fromDct = dct.fromDct()

        val fromDctPicture = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)

        fromDctPicture.setPixels(fromDct)
    }
}