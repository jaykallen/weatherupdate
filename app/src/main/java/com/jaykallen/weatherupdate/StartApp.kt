package com.jaykallen.weatherupdate

import android.app.Application
import android.content.Context
import timber.log.Timber
import timber.log.Timber.DebugTree


class StartApp : Application() {
    init {
        instance = this
    }

    companion object {
        private var instance: Application? = null

        fun applicationContext() : Context {
            return instance!!.applicationContext
        }
    }

    override fun onCreate() {
        super.onCreate()
        Timber.plant(object : DebugTree() {
            override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
                super.log(priority, tag + "~!@", message, t)
            }
        })
    }
}