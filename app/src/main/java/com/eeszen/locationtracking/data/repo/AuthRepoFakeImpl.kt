package com.eeszen.locationtracking.data.repo

import com.eeszen.locationtracking.data.model.LoginReq
import com.eeszen.locationtracking.data.model.RegisterReq
import jakarta.inject.Inject
import jakarta.inject.Singleton
import kotlinx.coroutines.delay

@Singleton
class AuthRepoFakeImpl @Inject constructor() : AuthRepo {
    private val users = mutableMapOf<String, Pair<String, String>>() // email -> (password, name)

    override suspend fun login(request: LoginReq): Boolean {
        val user = users[request.email]
        return user?.first == request.password
    }

    override suspend fun registerUser(request: RegisterReq): Boolean {
        if (users.containsKey(request.email)) return false
        users[request.email] = request.password to request.name
        return true
    }

}