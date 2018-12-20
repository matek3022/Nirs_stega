package com.example.matek3022.nirs

data class Pixel(var red: Float, var green: Float, var blue: Float) {
    fun compare(pixel: Pixel) = Math.abs(red - pixel.red) + Math.abs(green - pixel.green) + Math.abs(blue - pixel.blue)
    fun maxDiff(pixel: Pixel): Float{
        val a1 = Math.abs(red - pixel.red)
        val a2 = Math.abs(green - pixel.green)
        val a3 = Math.abs(blue - pixel.blue)
        return Math.max(a1, Math.max(a2, a3))
    }

}