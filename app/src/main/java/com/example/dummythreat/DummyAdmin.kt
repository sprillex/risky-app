package com.example.dummythreat

import android.app.admin.DeviceAdminReceiver
import android.content.Context
import android.content.Intent

class DummyAdmin : DeviceAdminReceiver() {
    override fun onEnabled(context: Context, intent: Intent) {
        super.onEnabled(context, intent)
        // Inert: does nothing
    }

    override fun onDisabled(context: Context, intent: Intent) {
        super.onDisabled(context, intent)
        // Inert: does nothing
    }
}
