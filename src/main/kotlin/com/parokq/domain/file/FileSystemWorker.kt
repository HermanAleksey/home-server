package com.parokq.domain.file

import java.io.File
import java.io.FileOutputStream
import java.nio.file.Files
import java.nio.file.Paths
import kotlin.io.path.Path

class FileSystemWorker {

    init {
        Files.createDirectories(Path(PICTURES_DIR))
        Files.createDirectories(Path(SONGS_DIR))
    }

    fun addPicture(id: Long, picture: ByteArray) {
        val file = File(id.toPictureName())
        val fos = FileOutputStream(file)
        fos.write(picture)
        fos.flush()
        fos.close()
    }

    fun getPicture(id: Long): File {
        return File(id.toPictureName())
    }

    fun getSong(id: Long): File {
        return File(id.toSongName())
    }

    private fun Long.toPictureName(): String = PICTURES_DIR + "pic_$this.png"
    //todo or remove there since server doesnt need to know types.

    private fun Long.toSongName(): String = SONGS_DIR + "song_$this"//todo add .type

    companion object {
        private const val RESOURCES_DIR = "./storage"
        const val PICTURES_DIR = "$RESOURCES_DIR/pictures/"
        const val SONGS_DIR = "$RESOURCES_DIR/songs/"
    }
}

fun main() {
    val worker = FileSystemWorker()

    val filePath = "./storage/pictures/media_16ad2258cac6171d66942b13b8cd4839f0b6be6f3.png"

    fun fileToByteArray(path: String): ByteArray {
        return Files.readAllBytes(Paths.get(path))
    }

    val bytes = fileToByteArray(filePath)
    val stringAra = bytes.toString()
    worker.addPicture(1L, bytes)
    val a = worker.getPicture(1L)
}