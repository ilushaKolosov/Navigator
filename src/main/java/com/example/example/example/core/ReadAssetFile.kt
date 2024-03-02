package com.example.example.example.core

import android.content.Context
import android.content.res.AssetManager
import android.util.Log
import java.io.IOException
import java.io.InputStream

class ReadAssetFile(context: Context) {
    private val context: Context

    init {
        this.context = context
    }

    fun readAssetFile(filename: String): String? {
        val assetPath = "assets/$filename"
        return try {
            // ПолучаемAssetManager
            val assetManager: AssetManager = context.assets
            // Открываем файл для чтения
            val inputStream: InputStream = assetManager.open(assetPath)
            // Читаем содержимое файла
            val data = ByteArray(inputStream.available())
            inputStream.read(data)
            // Закрываем поток
            inputStream.close()
            // Возвращаем содержимое файла
            return String(data)
        } catch (e: IOException) {
            Log.e("ReadAssetFile", "Не удалось прочитать файл: " + e.toString())
            null
        }
    }
}