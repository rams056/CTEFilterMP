package org.example.project

import kotlinx.cinterop.ExperimentalForeignApi
import platform.UIKit.UIDevice
import platform.Foundation.*

class IOSPlatform: Platform {
    override val name: String = UIDevice.currentDevice.systemName() + " " + UIDevice.currentDevice.systemVersion
}

actual fun getPlatform(): Platform = IOSPlatform()

@OptIn(ExperimentalForeignApi::class)
actual fun readJsonFile(fileName: String): String {
    val path = NSBundle.mainBundle.pathForResource(fileName, "json")
    return NSString.stringWithContentsOfFile(path!!, encoding = NSUTF8StringEncoding, error = null) as String
}