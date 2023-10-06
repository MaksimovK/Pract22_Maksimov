package com.example.pract22

import android.graphics.Color
import android.widget.Button
import android.widget.EditText
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.util.Log
import android.widget.TextView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val showButton = findViewById<Button>(R.id.show_weather)
        val cityNameEditText = findViewById<EditText>(R.id.city_name)

        showButton.setOnClickListener {
            val cityName = cityNameEditText.text.toString()
            getResult(cityName)
        }
    }

    private fun getResult(city: String) {
        if (city.isNotEmpty()) {
            val key = "91e5176808a56bbfb2735dd0c49813bb"
            val url = "https://api.openweathermap.org/data/2.5/weather?q=$city&appid=$key&units=metric&lang=ru"

            val queue: RequestQueue = Volley.newRequestQueue(this)
            val stringRequest = StringRequest(
                Request.Method.GET, url,
                { response ->
                    try {
                        val obj = JSONObject(response)
                        val temp = obj.getJSONObject("main").getString("temp")
                        val pressure = obj.getJSONObject("main").getString("pressure")
                        val windSpeed = obj.getJSONObject("wind").getString("speed")

                        val temperatureTextView = findViewById<TextView>(R.id.temperatureTextView)
                        val pressureTextView = findViewById<TextView>(R.id.pressureTextView)
                        val windSpeedTextView = findViewById<TextView>(R.id.windSpeedTextView)

                        temperatureTextView.text = "Температура: $temp°C"
                        pressureTextView.text = "Давление: $pressure мм рт. ст."
                        windSpeedTextView.text = "Скорость ветра: $windSpeed м/с"

                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                },
                { error ->
                    Log.d("MyLog", "Volley error: ${error.message}")
                })

            queue.add(stringRequest)
        } else {
            val sn = Snackbar.make(
                findViewById(android.R.id.content),
                R.string.error,
                Snackbar.LENGTH_LONG
            )
            sn.setActionTextColor(Color.RED)
            sn.show()
        }
    }
}