package com.assignmentapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import androidx.appcompat.widget.AppCompatTextView
import com.assignmentdemo.R

class MainActivity : AppCompatActivity() {


    lateinit var tvUserName: AppCompatTextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tvUserName = findViewById(R.id.tv_user_name)

        if (intent.extras?.getString("user_name") != null && !TextUtils.isEmpty(
                intent.extras?.getString(
                    "user_name"
                )
            )
        ) {

            val userName = intent.extras?.getString("user_name")
            tvUserName.text = "Hello $userName"

        }

    }
}