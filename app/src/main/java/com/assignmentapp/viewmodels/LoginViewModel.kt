package com.assignmentapp.viewmodels


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LoginViewModel:ViewModel() {

    private val _loginUiState = MutableStateFlow<LoginUiState>(LoginUiState.Empty)
    val loginUiState: StateFlow<LoginUiState> = _loginUiState
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
    var userName:String =""
    var password:String?= ""

    fun checkValidation(value:String,field: String) = viewModelScope.launch {

        if (field == "User Name") {
            userName = value
            _loginUiState.value = LoginUiState.IsValidUserName(value.matches(userNameRegrex.toRegex()))
        } else {
            password = value
            _loginUiState.value = LoginUiState.IsValidPassword(value.matches(passwordRegrex.toRegex()))
        }

        _loginUiState.value = LoginUiState.Success(
            userName!!.matches(userNameRegrex.toRegex()) &&
                 password!!.matches(passwordRegrex.toRegex()))

    }

    sealed class LoginUiState {
        data class IsValidUserName(val state:Boolean) : LoginUiState()
        data class IsValidPassword(val state: Boolean) : LoginUiState()
        data class Success(val state: Boolean) : LoginUiState()
        object Empty: LoginUiState()
    }
}