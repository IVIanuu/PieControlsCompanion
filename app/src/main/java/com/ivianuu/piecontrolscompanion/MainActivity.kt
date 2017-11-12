package com.ivianuu.piecontrolscompanion

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.text.TextUtils
import android.widget.Toast

/**
 * Main activity
 */
class MainActivity: Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (!isAccessibilityEnabled()) {
            AlertDialog.Builder(this)
                    .setCancelable(false)
                    .setTitle(R.string.dialog_enable_accessibility_title)
                    .setMessage(R.string.dialog_enable_accessibility_summary)
                    .setPositiveButton(R.string.dialog_ok, { _, _ ->
                        val intent = Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS)
                        try {
                            startActivityForResult(intent, REQUEST_CODE)
                        } catch (e: Exception) {
                            Toast.makeText(this, R.string.error_activity_not_found,
                                    Toast.LENGTH_SHORT).show()
                        }
                    })
                    .setNegativeButton(R.string.dialog_cancel, { _, _ ->
                        finish()
                    })
                    .show()
        } else {
            Toast.makeText(this, R.string.msg_accessibility_already_enabled,
                    Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE) {
            if (isAccessibilityEnabled()) {
                Toast.makeText(this, R.string.msg_accessibility_granted,
                        Toast.LENGTH_SHORT).show()
            }
        }

        finish()
    }

    private fun isAccessibilityEnabled(): Boolean {
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

    private companion object {
        private const val REQUEST_CODE = 1234
    }
 }