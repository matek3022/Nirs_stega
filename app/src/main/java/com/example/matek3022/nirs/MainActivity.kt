package com.example.matek3022.nirs

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        val options = BitmapFactory.Options()
//        options.outConfig = Bitmap.Config.ARGB_8888
//        options.inPreferredConfig = Bitmap.Config.ARGB_8888
//        options.inMutable = true
//        var c = BitmapFactory.decodeResource(resources, R.drawable.test, options)
//        var c1 = BitmapFactory.decodeResource(resources, R.drawable.test, options)

//        var os = ByteArrayOutputStream()
//        c.compress(Bitmap.CompressFormat.JPEG, 100, os)
//        var array = os.toByteArray()
//        c = BitmapFactory.decodeByteArray(array, 0, array.size, options)
//        val text = generateTextToPercentage(c, 100)
//        c.codeText(text)

//        os = ByteArrayOutputStream()
//        c.compress(Bitmap.CompressFormat.JPEG, 100, os)
//        array = os.toByteArray()
//        c = BitmapFactory.decodeByteArray(array, 0, array.size, options)

//        val outText = c.getText()

//        val psnr = computePsnr(c, c1)
        val resList = ArrayList<Pair<Int, Double>>()
        for (i in 1..100) {
            resList.add(i to getPsnrFromPercentage(i))
        }
//        getPsnrFromPercentage(23)
    }

    fun getPsnrFromPercentage(percentage: Int): Double{
        val options = BitmapFactory.Options()
        options.outConfig = Bitmap.Config.ARGB_8888
        options.inPreferredConfig = Bitmap.Config.ARGB_8888
        options.inMutable = true
        var c = BitmapFactory.decodeResource(resources, R.drawable.test, options)
        var c1 = BitmapFactory.decodeResource(resources, R.drawable.test, options)
        val text = generateTextToPercentage(c, percentage)
        c.codeText(text)
        Log.wtf("tag_percentage", percentage.toString())
        return computePsnr(c, c1)
    }
}
