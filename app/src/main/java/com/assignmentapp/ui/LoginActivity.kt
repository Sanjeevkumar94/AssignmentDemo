package com.assignmentapp.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatEditText
import com.assignmentapp.utils.StateTextWatcher
import com.assignmentdemo.R
import com.google.android.material.textfield.TextInputLayout
import java.util.regex.Pattern


class LoginActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var btnLogin: AppCompatButton
    lateinit var etUserName: AppCompatEditText
    lateinit var etPswd: AppCompatEditText
    lateinit var tlUserName: TextInputLayout
    lateinit var tlPswd: TextInputLayout

    val passwordRegrex = "^" +
            "(?=.*[0-9])" +         //at least 1 digit
            "(?=.*[a-z])" +         //at least 1 lower case letter
            "(?=.*[A-Z])" +         //at least 1 upper case letter
            "(?=.*[a-zA-Z])" +      //any letter
            "(?=.*[@?,!])" +    //at least 1 special character
            "(?=\\S+$)" +           //no white spaces
            ".{5,}" +               //at least 5 characters
            "$"

    val userNameRegrex = "^[a-zA-Z0-9]+\$"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        setViews()
        setListeners()
        disableEnableLoginButton(false)
    }

    fun setViews() {
        btnLogin = findViewById(R.id.btn_login)
        etUserName = findViewById(R.id.et_user_name)
        etPswd = findViewById(R.id.et_pswd)
        tlUserName = findViewById(R.id.tl_user_name)
        tlPswd = findViewById(R.id.tl_pswd)
    }

    fun setListeners() {
        btnLogin.setOnClickListener(this)
        addTextChangeListener(etUserName, getString(R.string.user_name))
        addTextChangeListener(etPswd, getString(R.string.password))

    }

    override fun onClick(p0: View?) {

        when (p0?.id) {
            R.id.btn_login -> {

                startActivity(
                    Intent(
                        this,
                        MainActivity::class.java
                    )
                        .putExtra("user_name", etUserName.text.toString())
                )

            }
        }
    }

    fun disableEnableLoginButton(state: Boolean) {

        if (!state) {
            btnLogin.alpha = 0.5f
        } else {
            btnLogin.alpha = 1f

        }
        btnLogin.isEnabled = state
        btnLogin.isClickable = state
    }

    private fun addTextChangeListener(editTextLayout: AppCompatEditText, value: String) {
        editTextLayout.addTextChangedListener(object : StateTextWatcher() {
            override fun afterTextChanged(p0: Editable?) {
                checkValidation(p0, value)
            }

        })
    }


    private fun checkValidation(s: Editable?, field: String) {

        if (field == getString(R.string.user_name)) {
            if (!Pattern.matches(userNameRegrex, etUserName.text)) {
                disableEnableLoginButton(false)
                tlUserName.error = "Enter proper user name"
                return
            } else {
                tlUserName.error = null

            }
        } else {
            if (!Pattern.matches(passwordRegrex, etPswd.text)) {
                disableEnableLoginButton(false)
                tlPswd.error = "Password not meeting the criteria"
                return
            } else {
                tlPswd.error = null

            }
        }

        if (!TextUtils.isEmpty(etUserName.text) && !TextUtils.isEmpty(etPswd.text))
            disableEnableLoginButton(true)
    }

}
