package org.example.project

import com.ytubebuddy.ctefilters.models.Filters

class WasmPlatform: Platform {
    override val name: String = "Web with Kotlin/Wasm"
}

actual fun getPlatform(): Platform = WasmPlatform()
actual fun readJsonFile(fileName: String): String {
    TODO("Not yet implemented")
}