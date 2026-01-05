package com.eeszen.locationtracking.ui.screens.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eeszen.locationtracking.data.model.LoginReq
import com.eeszen.locationtracking.data.repo.AuthRepo
import com.eeszen.locationtracking.data.repo.IUserRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repo: IUserRepo
) : ViewModel() {

    private val _error = MutableSharedFlow<String>()
    val error = _error.asSharedFlow()

    private val _finish = MutableSharedFlow<Unit>()
    val finish = _finish.asSharedFlow()


    private val _greetings = MutableStateFlow("")
    val greetings = _greetings.asStateFlow()

    fun greet(name:String){
        viewModelScope.launch {
            delay(1000)
            _greetings.value = "Hello $name"
        }
    }

    fun greetings():String{
        return "Hello ${fetchUser()}"
    }

    fun fetchUser():String{
        val name = repo.getUser()
        _greetings.value = "hello $name"
        return name
    }

    fun login(
        email: String,
        password: String
    ){
        val vRes = validate(email,password)
        if (vRes != null){
            viewModelScope.launch {
                _error.emit(vRes)
            }
            return
        }

        viewModelScope.launch {
            _finish.emit(Unit)
        }
//        try {
//            viewModelScope.launch {
//                repo.login(
//                    LoginReq(
//                        email = email,
//                        password = password
//                    )
//                )
//            }
//        }catch (e:Exception){
//            Log.d("debugging",e.toString())
//        }
    }

    fun validate(email: String, password: String):String?{
        return try {
            require(email.isNotBlank() && email == "email@a.com"){"Invalid email"}
            require(password.isNotBlank() && password == "password"){"Invalid password"}
            null
        }catch (e:Exception){
            e.message.toString()
        }
    }

}