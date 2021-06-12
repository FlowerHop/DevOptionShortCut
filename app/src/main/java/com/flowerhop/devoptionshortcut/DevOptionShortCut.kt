package com.flowerhop.devoptionshortcut

import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.os.Build
import android.provider.Settings
import android.provider.Settings.ACTION_APPLICATION_DEVELOPMENT_SETTINGS
import android.provider.Settings.ACTION_SETTINGS
import android.provider.Settings.Global.DEVELOPMENT_SETTINGS_ENABLED
import android.service.quicksettings.TileService
import android.util.Log
import androidx.annotation.RequiresApi

@RequiresApi(Build.VERSION_CODES.N)
class DevOptionShortCut: TileService() {
    private val TAG = "DevOptionShortCut"
    private val DEBUG = false

    override fun onClick() {
        super.onClick();
        val devOptionEnable = isDeveloperOptionEnable()

        val msg = "Developer option is " + if (devOptionEnable) "enable" else "disable"
        logMsg(msg)

        if (devOptionEnable) {
            startActivityAndCollapse(getIntent(ACTION_APPLICATION_DEVELOPMENT_SETTINGS))
        } else {
            startActivityAndCollapse(getIntent(ACTION_SETTINGS))
        }
    }

    private fun isDeveloperOptionEnable(): Boolean {
        return Settings.Secure.getInt(contentResolver, DEVELOPMENT_SETTINGS_ENABLED) == 1
    }

    private fun getIntent(action: String): Intent {
        return Intent(action).apply {
            flags = FLAG_ACTIVITY_NEW_TASK
        }
    }

    private fun logMsg(msg: String) {
        if (!DEBUG) return
        Log.d(TAG, msg)
    }
}