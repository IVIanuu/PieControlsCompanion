package com.ivianuu.piecontrolscompanion

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast

/**
 * Main activity
 */
class MainActivity: Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (!isCompanionEnabled()) {
            AlertDialog.Builder(this)
                    .setCancelable(false)
                    .setTitle(R.string.dialog_enable_companion_title)
                    .setMessage(R.string.dialog_enable_companion_summary)
                    .setPositiveButton(R.string.dialog_ok, { _, _ ->
                        val accessibilityIntent = Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS)
                        try {
                            startActivityForResult(accessibilityIntent, REQUEST_CODE)
                            Toast.makeText(this, R.string.msg_enable_companion,
                                    Toast.LENGTH_LONG).show()
                        } catch (e: Exception) {
                            Toast.makeText(this, R.string.error_launch_accessibility_settings,
                                    Toast.LENGTH_LONG).show()
                        }
                    })
                    .setNegativeButton(R.string.dialog_cancel, { _, _ ->
                        finish()
                    })
                    .show()
        } else {
            Toast.makeText(this, R.string.msg_companion_enabled,
                    Toast.LENGTH_LONG).show()
            finish()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE) {
            if (isCompanionEnabled()) {
                Toast.makeText(this, R.string.msg_companion_enabled,
                        Toast.LENGTH_LONG).show()
            }
        }

        finish()
    }

    private companion object {
        private const val REQUEST_CODE = 1234
    }
 }