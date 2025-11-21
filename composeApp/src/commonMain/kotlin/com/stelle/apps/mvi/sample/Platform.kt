package com.stelle.apps.mvi.sample

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform