package com.eeszen.locationtracking.data.repo

class UserRepo: IUserRepo {
    override fun getUser():String {
        // call actual api or database query to get the info
        return "Hello User"
    }
}