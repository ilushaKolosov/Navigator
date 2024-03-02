package com.example.example.example

import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import com.example.example.example.core.Core
import kotlinx.coroutines.*
import java.io.IOException


class MainActivity : AppCompatActivity() {
    companion object {
        lateinit var fragmentManager: FragmentManager
    }

    private val myCoroutineScope = CoroutineScope(Dispatchers.Main)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main)


        val thread = Thread {
            try {
                Core.readSmejnostFile(assets, "Smejnost.txt")
            } catch (e: IOException) {
                e.printStackTrace()
            } finally {
                runOnUiThread {
                    Toast.makeText(this.applicationContext, "done", Toast.LENGTH_SHORT).show()
                }
            }
        }

        val thread1 = Thread {
            try {
                Core.readMapOfAll(assets, "mapOfAll.txt")
            } catch (e: IOException) {
                e.printStackTrace()
            } finally {
                runOnUiThread {
                    Toast.makeText(this.applicationContext, "done", Toast.LENGTH_SHORT).show()
                }
            }
        }

        val thread2 = Thread {
            try {
                Core.readEndPoints(assets, "endPoints.txt")
            } catch (e: IOException) {
                e.printStackTrace()
            } finally {
                runOnUiThread {
                    Toast.makeText(this.applicationContext, "done", Toast.LENGTH_SHORT).show()
                }
            }
        }

        thread2.start()
        thread1.start()
        thread.start()


        MainActivity.fragmentManager = supportFragmentManager
        MainActivity.fragmentManager!!.beginTransaction()
            .add(R.id.container, MainFragment())
            .commit()
    }

}