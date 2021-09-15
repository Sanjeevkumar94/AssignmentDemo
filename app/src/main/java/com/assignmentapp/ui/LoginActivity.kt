package com.assignmentapp.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.widget.AppCompatEditText
import androidx.lifecycle.lifecycleScope
import com.assignmentapp.utils.StateTextWatcher
import com.assignmentapp.viewmodels.LoginViewModel
import com.assignmentdemo.R
import com.assignmentdemo.databinding.ActivityLoginBinding
import kotlinx.coroutines.flow.collect
import java.util.regex.Pattern


class LoginActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var binding:ActivityLoginBinding

    private val viewMode:LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setListeners()
        disableEnableLoginButton(false)
        handleResponse()
    }

    private fun handleResponse() {

        lifecycleScope.launchWhenCreated {
            viewMode.loginUiState.collect {
                when(it){
                    is LoginViewModel.LoginUiState.IsValidUserName->{
                        if(!it.state){
                            disableEnableLoginButton(false)
                            binding.tlUserName.error = "Enter proper user name"
                        } else{
                            binding.tlUserName.error = null

                        }
                    }
                    is LoginViewModel.LoginUiState.IsValidPassword->{
                        if(!it.state){
                            disableEnableLoginButton(false)
                            binding.tlPswd.error = "Password not meeting the criteria"
                        } else{
                            binding.tlPswd.error = null

                        }
                    }
                    is LoginViewModel.LoginUiState.Success->{
                        if(!it.state){
                            disableEnableLoginButton(false)
                        } else{
                            disableEnableLoginButton(true)
                        }
                    }

                }
            }
        }

    }

    fun setListeners() {
        binding.btnLogin.setOnClickListener(this)
        addTextChangeListener(binding.etUserName, getString(R.string.user_name))
        addTextChangeListener(binding.etPswd, getString(R.string.password))

    }

    override fun onClick(p0: View?) {

        when (p0?.id) {
            R.id.btn_login -> {

                startActivity(
                    Intent(
                        this,
                        MainActivity::class.java
                    )
                        .putExtra("user_name", binding.etUserName.text.toString())
                )

                finish()

            }
        }
    }

    fun disableEnableLoginButton(state: Boolean) {
        if (!state) {
            binding.btnLogin.alpha = 0.5f
        } else {
            binding.btnLogin.alpha = 1f
        }
        binding.btnLogin.isEnabled = state
        binding.btnLogin.isClickable = state
    }

    private fun addTextChangeListener(editTextLayout: AppCompatEditText, field: String) {
        editTextLayout.addTextChangedListener(object : StateTextWatcher() {
            override fun afterTextChanged(p0: Editable?) {
                viewMode.checkValidation(p0.toString(),field)
            }

        })
    }
}
