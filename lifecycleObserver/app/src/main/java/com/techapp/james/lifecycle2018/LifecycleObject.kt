package com.techapp.james.lifecycle2018

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.OnLifecycleEvent
import timber.log.Timber

class LifecycleObject : LifecycleObserver {
    constructor()

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreate() {
        Timber.d("OnCreate")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onStart() {
        Timber.d("OnStart")
    }
}