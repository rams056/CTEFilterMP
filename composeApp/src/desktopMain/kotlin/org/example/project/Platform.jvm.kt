package org.example.project

import java.io.InputStream

class JVMPlatform: Platform {
    override val name: String = "Java ${System.getProperty("java.version")}"
}

actual fun getPlatform(): Platform = JVMPlatform()

/*actual fun readJsonFile(fileName: String): String {
    println("Resource Path: Start")
    try {
        val resourcePath = main()::class.java.classLoader?.getResource("instafilters.json")
        println("Resource Path: $resourcePath")
    }catch (e: Exception) {
        println("Error: ${e.message}")
    }



    val stream: InputStream = main()::class.java.classLoader?.getResourceAsStream(fileName)
        ?: throw IllegalArgumentException("File not found: $fileName")

    println("Resource Code: $stream.bufferedReader().use { it.readText() }")
    return stream.bufferedReader().use { it.readText() }
}*/

actual fun readJsonFile(fileName: String): String {
    println("Executing desktopMain readJsonFile for file: $fileName")

    //val classLoader = main()::class.java.classLoader
    //requireNotNull(classLoader) { "ClassLoader is null" }

    val classLoader = Thread.currentThread().contextClassLoader
    requireNotNull(classLoader) { "ClassLoader is null" }


    // Fetch the resource as a URL
    val resource = classLoader.getResource(fileName)
        ?: throw IllegalArgumentException("Resource not found: $fileName")
    println("Resource Path: $resource")

    // Open stream and read content
    return resource.openStream().bufferedReader().use { reader ->
        reader.readText() // Read the entire content
    }
}
