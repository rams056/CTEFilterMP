package org.example.project

import java.io.InputStream

actual fun readJsonFile(fileName: String): String {
    val stream: InputStream = main()::class.java.classLoader.getResourceAsStream(fileName)
        ?: throw IllegalArgumentException("File not found: $fileName")
    return stream.bufferedReader().use { it.readText() }
}