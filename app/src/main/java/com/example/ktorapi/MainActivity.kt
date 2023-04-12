package com.example.ktorapi

import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        GetMenuItemsTask().execute()
    }

    private inner class GetMenuItemsTask : AsyncTask<Void, Void, Array<MenuItem>>() {
        override fun doInBackground(vararg params: Void): Array<MenuItem>? {
            var menuItems: Array<MenuItem>? = null

            try {
                val url = URL("http://10.0.2.2:8080/menu")
                val connection = url.openConnection() as HttpURLConnection
                connection.connectTimeout = 5000
                connection.readTimeout = 5000
                connection.requestMethod = "GET"

                val responseCode = connection.responseCode
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    val inputStream = connection.inputStream
                    val bufferedReader = BufferedReader(InputStreamReader(inputStream))
                    val response = StringBuilder()

                    var inputLine: String?
                    while (bufferedReader.readLine().also { inputLine = it } != null) {
                        response.append(inputLine)
                    }
                    bufferedReader.close()

                    val gson = Gson()
                    menuItems = gson.fromJson(response.toString(), Array<MenuItem>::class.java)
                } else {
                    Log.e("GetMenuItemsTask", "Error: HTTP $responseCode")
                }
            } catch (e: Exception) {
                Log.e("GetMenuItemsTask", "Error: ${e.message}")
            }

            return menuItems
        }

        override fun onPostExecute(result: Array<MenuItem>?) {
            super.onPostExecute(result)

            // Handle the menu items data here
            result?.forEach { menuItem ->
                Log.d("GetMenuItemsTask", "MenuItem: ${menuItem.name}, Price: ${menuItem.price}")
            }
        }
    }
}
