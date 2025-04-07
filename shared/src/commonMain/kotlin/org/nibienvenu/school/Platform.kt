package org.nibienvenu.school

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform