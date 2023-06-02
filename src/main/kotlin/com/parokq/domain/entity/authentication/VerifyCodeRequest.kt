package com.parokq.domain.entity.authentication

data class VerifyCodeRequest(
    val email: String,
    val code: String,
)