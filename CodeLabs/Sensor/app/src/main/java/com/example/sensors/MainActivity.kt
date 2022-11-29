package com.example.sensors

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.widget.Toast
import com.example.sensors.databinding.ActivityMainBinding

class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main),
    SensorEventListener {
    private lateinit var sensorManager: SensorManager
    private var accSensor : Sensor?= null
    private var gyroSensor : Sensor?= null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager

        binding.btnAcc.setOnClickListener {
            sensorManager.unregisterListener(this)
            accSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
            Toast.makeText(this, "ACC", Toast.LENGTH_SHORT).show()
            sensorManager.apply {
                registerListener(this@MainActivity, accSensor, SensorManager.SENSOR_DELAY_NORMAL)
            }
        }


        binding.btnGyro.setOnClickListener {
            sensorManager.unregisterListener(this)
            gyroSensor = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE)
            Toast.makeText(this, "GYRO", Toast.LENGTH_SHORT).show()
            sensorManager.apply {
                registerListener(this@MainActivity, gyroSensor, SensorManager.SENSOR_DELAY_NORMAL)
            }
        }


    }
    
    override fun onSensorChanged(event: SensorEvent?) {
        if (event != null) {
            when (event.sensor.type) {
                Sensor.TYPE_ACCELEROMETER -> acc(event)
                Sensor.TYPE_GYROSCOPE -> gyro(event)
            }
        }
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) = Unit

    private fun gyro(event: SensorEvent) {
        binding.tvType.text = "GYRO"
        binding.tvXvalue.text = event.values[0].toString() //x축
        binding.tvYValue.text = event.values[1].toString() //y축
        binding.tvZValue.text = event.values[2].toString() //z축
    }

    private fun acc(event: SensorEvent) {
        binding.tvType.text = "ACC"
        binding.tvXvalue.text = event.values[0].toString() //x축
        binding.tvYValue.text = event.values[1].toString() //y축
        binding.tvZValue.text = event.values[2].toString() //z축
    }


}