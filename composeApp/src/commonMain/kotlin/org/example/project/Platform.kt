package org.example.project

import com.ytubebuddy.ctefilters.models.Filters

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform


expect fun readJsonFile(fileName: String): String