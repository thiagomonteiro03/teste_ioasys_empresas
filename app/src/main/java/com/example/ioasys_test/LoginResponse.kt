package com.example.ioasys_test

import com.google.gson.annotations.SerializedName


data class LoginResponse (
    @SerializedName("uid")
    var uid: Int,

    @SerializedName("access-token")
    var authToken: String,

    @SerializedName("client")
    var user: LoginModel
)