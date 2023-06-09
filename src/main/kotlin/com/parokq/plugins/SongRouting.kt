package com.parokq.plugins

import com.parokq.domain.entity.authentication.LoginResponse
import com.parokq.domain.file.FileSystemWorker
import io.ktor.http.*
import io.ktor.serialization.gson.*
import io.ktor.server.routing.*
import io.ktor.server.response.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*

fun Application.configureSongRouting() {
    val worker = FileSystemWorker()

        // do not install plugin twice
//    install(ContentNegotiation) {
//        gson {
//            setPrettyPrinting()
//        }
//    }

    routing {
        get("song1") {
            val id = call.request.queryParameters["id"]?.toLongOrNull() ?: return@get

            //todo add processing
            val song = worker.getSong(id)

            val response = LoginResponse(
                userName = "uName",
                photo = "qweqe",
                placeOfResidence = "qwe",
                otherData = "fwedfq"
            )
            //todo do we return whole File or just URL?
            call.respond(response)
        }
        get("song2") {
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
                    //todo try headers to convert into stream instead of loading file
//                header(
//                    HttpHeaders.ContentType,
//                    ContentType.Audio.MP4.toString()
//                )
            }

            //this let you load mp3 file
            call.respondFile(song)
        }

        //todo get songs paging? all songs?
    }
}
