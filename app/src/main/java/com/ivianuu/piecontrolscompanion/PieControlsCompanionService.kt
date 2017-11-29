package com.ivianuu.piecontrolscompanion

import android.accessibilityservice.AccessibilityService
import android.content.*
import android.content.pm.PackageManager
import android.util.Log
import android.view.accessibility.AccessibilityEvent


/**
 * Pie controls companion service
 */
class PieControlsCompanionService : AccessibilityService() {

    private val receiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val action = intent.getIntExtra(EXTRA_GLOBAL_ACTION, -1)
            performGlobalAction(action)
        }
    }

    override fun onCreate() {
        super.onCreate()
        registerReceiver(receiver, IntentFilter(ACTION_PERFORM_GLOBAL_ACTION_INTERNAL))

        Log.d("Service", "on create")
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(receiver)
        Log.d("Service", "on destroy")
    }

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
    }

    override fun onInterrupt() {
    }
}