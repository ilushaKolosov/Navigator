package com.example.example.example.core
import android.content.res.AssetManager
import kotlinx.coroutines.*
import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.*
import kotlin.collections.ArrayDeque
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class Core {
    companion object {
        val mas = arrayOf(39552, 93408, 152810, 218013, 284049, 356369, 373427)

        var endPoints: HashMap<String, Int> = HashMap<String, Int>()
        var Smejnost: ArrayList<ArrayList<Int>> = ArrayList<ArrayList<Int>> ()
        var mapOfAll: HashMap<Int, String> = HashMap<Int, String>()

        var path: List<Int> = ArrayList<Int>()
        var way: Array<ArrayList<String>> = Array(7) {ArrayList<String>()}

        fun getEndPoint(v: String): Int { return if (endPoints[v] != null) (endPoints[v]!!) else -1  }

        fun getWayFromPath() {
            fun geter(value: Int): Int {
                for (floor in mas.indices) { if (value < mas[floor]) { return floor} }
                return 0
            }
            path.forEachIndexed { index, value ->
                way[geter(value)].add(mapOfAll[value]!!)
            }
        }

        fun readEndPoints(assets: AssetManager, file: String) {
            var reader: BufferedReader? = null;
            reader = BufferedReader(InputStreamReader(assets.open(file)));
            var mLine = reader.readLine();
            var line = arrayOf("1")
            while (mLine != null) {
                line = mLine.split(" - ").toTypedArray()
                val key: String = line[1]
                val value: String = line[0].split(": ")[0]
                endPoints[key] = Integer.valueOf(value)
                mLine = reader.readLine();
            }
        }

        fun readMapOfAll(assets: AssetManager, file: String) {
            var reader: BufferedReader? = null;
            reader = BufferedReader(InputStreamReader(assets.open(file)));
            var mLine = reader.readLine();
            var line = arrayOf("1")
            while (mLine != null) {
                line = mLine.split(": ").toTypedArray()
                val key: Int = line[0].toInt()
                val value: String = line[1]
                mapOfAll[key] = value
                mLine = reader.readLine();
            }
        }

        fun readSmejnostFile(assets: AssetManager, file: String) {
            var reader: BufferedReader? = null;
            reader = BufferedReader(InputStreamReader(assets.open(file)));
            var mLine = reader.readLine();
            while (mLine != null) {
                Smejnost.add(mLine.split(": ").get(1).split(" ").map { it.toInt() }.toMutableList() as java.util.ArrayList<Int>)
                mLine = reader.readLine();
            }
        }

        fun setPath(s: Int, f: Int) { Core.path = findPath(Core.Smejnost, s, f) }

        fun findPath(Smejnost: ArrayList<ArrayList<Int>>, s: Int, f: Int): ArrayList<Int> {
            var fin = f
            var n = Smejnost.size;
            val path = ArrayList<Int>()
            val Q = ArrayDeque<Int>()
            Q.add(s);
            val used = BooleanArray(n) { false }
            val P = IntArray(n)
            used[s] = true
            P[s] = -1
            while (!Q.isEmpty()) {
                var v = Q.first()
                Q.removeFirst()
                for (i in (0 until Smejnost[v].size)) {
                    val to = Smejnost[v][i];
                    if (!used[to]) {
                        used[to] = true;
                        Q.add(to);
                        P[to] = v;
                    }
                }
            }
            if (!used[fin])
                return path
            else {
                while (fin != -1) {
                    path.add(fin);
                    fin = P[fin];
                }
            }
            return path.reversed() as ArrayList<Int>
        }

    }

    fun setAllFiles() {

    }
}