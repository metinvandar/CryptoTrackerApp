package com.metinvandar.cryptotrackerapp.common

interface ResourceManager {
    fun getString(resId: Int, vararg formatArgs: Any): String
    fun getString(resId: Int): String
}
