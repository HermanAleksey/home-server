package com.parokq.plugins.music

import com.parokq.domain.file.FileSystemWorker
import io.ktor.http.*
import io.ktor.server.routing.*
import io.ktor.server.response.*
import io.ktor.server.application.*

fun Application.configureSongRouting() {
    val worker = FileSystemWorker()

    routing {
        get("song/stream") {
            val id = 1L//call.request.queryParameters["id"]?.toLongOrNull() ?: return@get

            val song = worker.getSong(id)

            call.respondFile(song)
        }
        get("song/download") {
            val id = 1L//call.request.queryParameters["id"]?.toLongOrNull() ?: return@get

            val song = worker.getSong(id)
            call.response.apply {
                header(
                    HttpHeaders.ContentDisposition,
                    ContentDisposition.Attachment.withParameter(
                        key = ContentDisposition.Parameters.FileName,
                        value = song.name
                    ).toString()
                )
            }

            call.respondFile(song)
        }

        //todo get songs paging? all songs?
    }
}
