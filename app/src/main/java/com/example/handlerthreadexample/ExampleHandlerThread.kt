package com.example.handlerthreadexample

import android.annotation.SuppressLint
import android.os.*
import timber.log.Timber

class ExampleHandlerThread:
    HandlerThread("ExampleHandlerThread", Process.THREAD_PRIORITY_BACKGROUND) {

    private lateinit var handler: Handler


    override fun onLooperPrepared() {
        @SuppressLint("HandlerLeak")
        handler = object : Handler() {
            override fun handleMessage(msg: Message) {
                when (msg.what) {
                    EXAMPLE_TASK -> {
                        Timber.d("Example Task, arg1: ${msg.arg1}, obj: ${msg.obj}")
                        for (i in 0..4) {
                            Timber.d("handleMessage: $i")
                            SystemClock.sleep(1000)
                        }
                    }
                }
            }
        }
    }

    fun getHandler(): Handler {
        return handler
    }
}