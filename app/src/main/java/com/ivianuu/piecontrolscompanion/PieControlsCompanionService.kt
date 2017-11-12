package com.ivianuu.piecontrolscompanion

import android.accessibilityservice.AccessibilityService
import android.view.accessibility.AccessibilityEvent

/**
 * Pie controls companion service
 */
class PieControlsCompanionService(): AccessibilityService() {

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
    }

    override fun onInterrupt() {
    }

    companion object {
        private const val ACTION_PERFORM_GLOBAL_ACTION = "com.ivianuu.piecontrolscompanion.PERFORM_GLOBAL_ACTION"
    }
}