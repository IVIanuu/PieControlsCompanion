package com.ivianuu.piecontrolscompanion

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.provider.Settings
import android.widget.Toast

/**
 * Receiver for global actions from pie and unique controls
 */
class PerformGlobalActionReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val intentAction = intent.action
        if (intentAction != null && intentAction == ACTION_PERFORM_GLOBAL_ACTION) {
            if (context.isCompanionEnabled()) {
                val globalAction = intent.getIntExtra(EXTRA_GLOBAL_ACTION, -1)
                if (globalAction != -1) {
                    val internalIntent = Intent(ACTION_PERFORM_GLOBAL_ACTION_INTERNAL)
                    internalIntent.putExtra(EXTRA_GLOBAL_ACTION, globalAction)
                    context.sendBroadcast(internalIntent)
                }
            } else {
                val accessibilityIntent = Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS)
                accessibilityIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                try {
                    context.startActivity(accessibilityIntent)
                    Toast.makeText(context, R.string.msg_enable_companion, Toast.LENGTH_LONG).show()
                } catch (e: Exception) {
                    Toast.makeText(context, R.string.error_launch_accessibility_settings,
                            Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}