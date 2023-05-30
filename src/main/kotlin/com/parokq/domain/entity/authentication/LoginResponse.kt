package com.parokq.domain.entity.authentication

data class LoginResponse(
    val userName: String,
    val photo: String,
    val placeOfResidence: String,
    val otherData: String
)