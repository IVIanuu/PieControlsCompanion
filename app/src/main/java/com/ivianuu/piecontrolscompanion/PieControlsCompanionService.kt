package com.ivianuu.piecontrolscompanion

import android.accessibilityservice.AccessibilityService
import android.content.*
import android.content.pm.PackageManager
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
        setMainActivityEnabled(false)
        registerReceiver(receiver, IntentFilter(ACTION_PERFORM_GLOBAL_ACTION_INTERNAL))
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(receiver)
        setMainActivityEnabled(true)
    }

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
    }

    override fun onInterrupt() {
    }

    private fun setMainActivityEnabled(enabled: Boolean) {
        val state = if (enabled) PackageManager.COMPONENT_ENABLED_STATE_ENABLED
        else PackageManager.COMPONENT_ENABLED_STATE_DISABLED
        packageManager.setComponentEnabledSetting(
                ComponentName(this, MainActivity::class.java), state, PackageManager.DONT_KILL_APP)
    }
}