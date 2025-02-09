package com.example.neonseekbar

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        val neonSeekBar: NeonSeekBar = findViewById(R.id.neonSeekbar)
            neonSeekBar.background = null
            neonSeekBar.setShowShadows(true)

            neonSeekBar.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                // Update the TextView with the progress of the SeekBar
                neonSeekBar.setAnchorText(progress.toString())

            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {
                // Handle when the user starts dragging the thumb
            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {
                // Handle when the user stops dragging the thumb
            }
        })
    }
    }
