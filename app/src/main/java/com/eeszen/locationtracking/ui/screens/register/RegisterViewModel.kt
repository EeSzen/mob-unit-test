package com.eeszen.locationtracking.ui.screens.register

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eeszen.locationtracking.data.model.RegisterReq
import com.eeszen.locationtracking.data.repo.AuthRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val repo: AuthRepo
): ViewModel() {

    fun register(
        email:String,
        name:String,
        password: String
    ){
        try {
            viewModelScope.launch {
                repo.registerUser(
                    request = RegisterReq(
                        email = email,
                        name = name,
                        password = password
                    )
                )
            }
        }catch (e:Exception){
            Log.d("debugging",e.toString())
        }
    }

}