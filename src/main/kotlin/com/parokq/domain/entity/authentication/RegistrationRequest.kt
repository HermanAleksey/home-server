package com.parokq.domain.entity.authentication

data class RegistrationRequest(
    val name: String,
    val email: String,
    val password: String,
)