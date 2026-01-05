package com.eeszen.locationtracking.data.repo

import com.eeszen.locationtracking.data.model.LoginReq
import com.eeszen.locationtracking.data.model.RegisterReq

interface AuthRepo {
    suspend fun login(request: LoginReq): Boolean
    suspend fun registerUser(request: RegisterReq): Boolean
}