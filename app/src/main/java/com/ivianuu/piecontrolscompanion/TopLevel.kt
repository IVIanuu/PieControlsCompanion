package com.ivianuu.piecontrolscompanion

import android.content.Context
import android.provider.Settings
import android.text.TextUtils

const val ACTION_PERFORM_GLOBAL_ACTION = "com.ivianuu.piecontrolscompanion.PERFORM_GLOBAL_ACTION"
const val EXTRA_GLOBAL_ACTION = "action"

const val ACTION_PERFORM_GLOBAL_ACTION_INTERNAL =
        "com.ivianuu.piecontrolscompanion.PERFORM_GLOBAL_ACTION_INTERNAL"

fun Context.isCompanionEnabled(): Boolean {
    var accessibilityEnabled = 0
    val service = packageName + "/" + PieControlsCompanionService::class.java.canonicalName
    try {
        accessibilityEnabled = Settings.Secure.getInt(
                applicationContext.contentResolver,
                android.provider.Settings.Secure.ACCESSIBILITY_ENABLED)
    } catch (e: Settings.SettingNotFoundException) {
        e.printStackTrace()
    }

    val mStringColonSplitter = TextUtils.SimpleStringSplitter(':')

    if (accessibilityEnabled == 1) {
        val settingValue = Settings.Secure.getString(
                applicationContext.contentResolver,
                Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES)
        if (settingValue != null) {
            mStringColonSplitter.setString(settingValue)
            while (mStringColonSplitter.hasNext()) {
                val accessibilityService = mStringColonSplitter.next()
                if (accessibilityService.equals(service, ignoreCase = true)) {
                    return true
                }
            }
        }
    }

    return false
}