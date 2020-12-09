package com.example.handlerthreadexample

import android.os.*
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import timber.log.Timber
import java.util.*

class MainActivity : AppCompatActivity() {
    private val handlerThread = ExampleHandlerThread()
    private val runnable1 = ExampleRunnable1()
    private val token = Object()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())

        handlerThread.start()
    }

    fun doWork(view: View) {
        val msg = Message.obtain(handlerThread.getHandler())
        msg.what = EXAMPLE_TASK
        msg.arg1 = 23
        msg.obj = "Obj String"
        //msg.data

        msg.sendToTarget()

        //handlerThread.getHandler().sendMessage(msg)

        //handlerThread.getHandler().sendEmptyMessage(1)

        handlerThread.getHandler().postAtTime(runnable1, token, SystemClock.uptimeMillis() )
        handlerThread.getHandler().post(runnable1)
        //handlerThread.getHandler().post(ExampleRunnable1())
        //handlerThread.getHandler().postAtFrontOfQueue(ExampleRunnable2())
    }

    fun removeMessages(view: View) {
        handlerThread.getHandler().removeCallbacks(runnable1, token)
    }

    override fun onDestroy() {
        super.onDestroy()
        handlerThread.quit()
    }

    class ExampleRunnable1 : Runnable {
        override fun run() {
            for (i in 0..4) {
                Timber.d("Runnable1: $i")
                SystemClock.sleep(1000)
            }
        }
    }

    class ExampleRunnable2 : Runnable {
        override fun run() {
            for (i in 0..4) {
                Timber.d("Runnable2: $i")
                SystemClock.sleep(1000)
            }
        }
    }
}