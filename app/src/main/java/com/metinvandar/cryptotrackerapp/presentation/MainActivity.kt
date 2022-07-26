package com.metinvandar.cryptotrackerapp.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.metinvandar.cryptotrackerapp.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }
}
