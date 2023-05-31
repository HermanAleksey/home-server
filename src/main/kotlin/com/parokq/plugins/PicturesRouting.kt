package com.parokq.plugins

import com.parokq.domain.file.FileSystemWorker
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configurePictureRouting() {

    val fileSystemWorker = FileSystemWorker()

    //doc authentication: https://ktor.io/docs/authentication.html#authenticate-route
    //query params: https://ktor.io/docs/requests.html#query_parameters
    //send response: https://ktor.io/docs/responses.html
    authentication {
        basic(name = "myauth1") {
            realm = "Ktor Server"
            validate { credentials ->
                if (credentials.name == credentials.password) {
                    UserIdPrincipal(credentials.name)
                } else {
                    null
                }
            }
        }
    }
    routing {
        authenticate("myauth1") {
            post("/protected/photo") {
                val contentLength = call.request.header(HttpHeaders.ContentLength)
                val multipartData = call.receiveMultipart()
                var fileName = ""
                var fileDescription = ""

                multipartData.forEachPart { part ->
                    when (part) {
                        is PartData.FormItem -> {
                            fileDescription = part.value
                        }

                        is PartData.FileItem -> {
                            fileName = part.originalFileName as String
                            val fileBytes = part.streamProvider().readBytes()

                            fileSystemWorker.addPicture(2L, fileBytes)
                        }

                        else -> {}
                    }
                    part.dispose()
                }

                call.respondText(
                    text = "$fileDescription is uploaded to 'uploads/$fileName'",
                    status = HttpStatusCode.OK
                )
            }
        }
        authenticate("myauth1") {
            get("/protected/photo") {
                //todo read corresponding photo
                val photoId = call.request.queryParameters["id"]?.toLongOrNull() ?: return@get

                val file = fileSystemWorker.getPicture(photoId)
                call.response.header(
                    HttpHeaders.ContentDisposition,
                    ContentDisposition.Attachment.withParameter(
                        key = ContentDisposition.Parameters.FileName,
                        value = file.name
                    ).toString()
                )

                call.respondFile(file)
            }
        }
    }
}
