package com.example.dummythreat

import android.accessibilityservice.AccessibilityService
import android.view.accessibility.AccessibilityEvent

class DummyAccessibility : AccessibilityService() {
    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        // Inert: does nothing
    }

    override fun onInterrupt() {
        // Inert: does nothing
    }
}
