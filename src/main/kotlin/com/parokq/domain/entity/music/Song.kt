package com.parokq.domain.entity.music

data class Song(
    val mediaId: String,
    val title: String,
    val subtitle: String,
    val songSource: String,//url to song on this server
    val imageSource: String,//url to album
)