package com.metinvandar.cryptotrackerapp.common

import android.content.Context

class ResourceManagerImpl(private val context: Context): ResourceManager {
    override fun getString(resId: Int, vararg formatArgs: Any): String {
        return context.getString(resId, *formatArgs)
    }

    override fun getString(resId: Int): String {
        return context.getString(resId)
    }
}
