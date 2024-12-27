package org.example.project

import android.content.Context
import android.os.Build
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

class AndroidPlatform : Platform {
    override val name: String = "Android ${Build.VERSION.SDK_INT}"
}

actual fun getPlatform(): Platform = AndroidPlatform()

private lateinit var appContext: Context

fun initContext(context: Context) {
    appContext = context
}

actual fun readJsonFile(fileName: String): String {
    if (!::appContext.isInitialized) {
        throw IllegalStateException("Context is not initialized. Call initContext first.")
    }

    val assetManager = appContext.assets
    return assetManager.open(fileName).bufferedReader().use { it.readText() }
}
