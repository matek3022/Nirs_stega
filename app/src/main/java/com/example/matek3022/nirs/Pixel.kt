package com.example.matek3022.nirs

data class Pixel(var red: Float, var green: Float, var blue: Float) {
    fun compare(pixel: Pixel) = Math.abs(red - pixel.red) + Math.abs(green - pixel.green) + Math.abs(blue - pixel.blue)
}