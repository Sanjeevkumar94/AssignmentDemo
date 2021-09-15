package com.assignmentapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import androidx.appcompat.widget.AppCompatTextView
import com.assignmentdemo.R
import com.assignmentdemo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {


    lateinit var binding:ActivityMainBinding


    lateinit var tvUserName: AppCompatTextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (intent.extras?.getString("user_name") != null && !TextUtils.isEmpty(
                intent.extras?.getString(
                    "user_name"
                )
            )
        ) {

            val userName = intent.extras?.getString("user_name")
            binding.tvUserName.text = "Hello $userName"

        }

    }
}