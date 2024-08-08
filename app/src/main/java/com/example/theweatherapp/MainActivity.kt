package com.example.theweatherapp

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    private var api_key = "12af725510c5bd32ee2d8dd56235a9d8"

    private lateinit var btVar1: Button
    private lateinit var textView: TextView
    private lateinit var etCityName: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textView = findViewById(R.id.textView)
        btVar1 = findViewById(R.id.btVar1)
        etCityName = findViewById(R.id.etCityName)

        btVar1.setOnClickListener {
            val cityName = etCityName.text.toString()
            if (cityName.isNotEmpty()) {
                fetchWeatherData(cityName)
            } else {
                Toast.makeText(this, "Please enter a city name", Toast.LENGTH_SHORT).show()
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun fetchWeatherData(cityName: String) {
        val weatherUrl = "https://api.openweathermap.org/data/2.5/weather?q=$cityName&units=metric&appid=$api_key"
        val queue = Volley.newRequestQueue(this)
        val stringReq = StringRequest(Request.Method.GET, weatherUrl, { response ->
            val obj = JSONObject(response)
            val main: JSONObject = obj.getJSONObject("main")
            val temperature = main.getString("temp")
            val city = obj.getString("name")
            textView.text = "$temperature deg Celsius in $city"
        }, {
            textView.text = "That didn't work!"
        })
        queue.add(stringReq)
    }
}