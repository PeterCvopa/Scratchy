package com.cvopa.peter.scratchy

import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber


private const val LOG_TAG_PREFIX = "SCRATCHY"

@HiltAndroidApp
class ScratchyApplication : android.app.Application() {
    override fun onCreate() {
        super.onCreate()
        Timber.plant(OliveaDebugTree())
    }
}

private class OliveaDebugTree() : Timber.DebugTree() {
    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        super.log(priority, LOG_TAG_PREFIX + tag!!, message, t)
    }
}
