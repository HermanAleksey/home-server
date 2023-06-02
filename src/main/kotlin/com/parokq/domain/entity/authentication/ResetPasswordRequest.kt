package com.parokq.domain.entity.authentication

data class ResetPasswordRequest (
    val email: String,
    val password: String,
)