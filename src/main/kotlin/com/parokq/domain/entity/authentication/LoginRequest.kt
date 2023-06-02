package com.parokq.domain.entity.authentication

data class LoginRequest(
    val email: String,
    val password: String,
)