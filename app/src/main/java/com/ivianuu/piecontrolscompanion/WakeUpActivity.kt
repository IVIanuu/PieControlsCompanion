package com.ivianuu.piecontrolscompanion

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log

/**
 * Wakes this app
 */
class WakeUpActivity: Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("Wake", "good morning")
        if (!isCompanionEnabled()) {
            startActivity(Intent(this, MainActivity::class.java))
        }

        finish()
    }
}