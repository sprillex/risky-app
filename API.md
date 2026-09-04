# Dummy Threat API Documentation

## Overview

**Dummy Threat** (`com.example.dummythreat`) is an inert Android application designed for security analysis and threat-detection evaluation. As a client-side Android application, Dummy Threat does not expose HTTP/REST endpoints or network RPC services. Instead, its public interface surface consists of **Android Inter-Process Communication (IPC)** entry points, exported application components (`Activity`, `AccessibilityService`, `DeviceAdminReceiver`), and requested Android system permissions.

- **Package Name**: `com.example.dummythreat`
- **Interface Protocol**: Android Intent System & Binder IPC (`android.content.Intent`)
- **Target SDK**: Android 14 (API Level 34)
- **Minimum SDK**: Android 7.0 (API Level 24)

---

## Authentication, Authorizations & Permissions

Android security permissions govern access to the application's components and system capabilities. Dummy Threat intentionally requests high-risk dangerous and signature/system permissions to trigger security scanners while remaining non-malicious.

### Declared System Permissions

| Permission Name | Category / Risk Level | Description |
| :--- | :--- | :--- |
| `android.permission.SYSTEM_ALERT_WINDOW` | Display / Overlay Privilege | Allows drawing overlay windows over other applications. Used by security tools to check display overdraw risk. |
| `android.permission.SEND_SMS` | Dangerous / Privacy | Allows sending SMS messages. |
| `android.permission.READ_SMS` | Dangerous / Privacy | Allows reading SMS messages stored on the device. |
| `android.permission.RECEIVE_SMS` | Dangerous / Privacy | Allows monitoring and receiving incoming SMS messages. |

### Component Binding Permissions

| Permission Name | Enforcement Level | Description |
| :--- | :--- | :--- |
| `android.permission.BIND_ACCESSIBILITY_SERVICE` | Signature / System | Guarantees that only the Android System can bind to `DummyAccessibility`. |
| `android.permission.BIND_DEVICE_ADMIN` | Signature / System | Guarantees that only the Android System Device Policy Manager can send device admin intents to `DummyAdmin`. |

---

## Standard Envelopes & Response/Intent Formats

### Intent Payload Format

When invoking or receiving intents from Dummy Threat components, communication occurs via standard Android `Intent` objects (`android.content.Intent`).

#### Intent Structure
- **Component**: `com.example.dummythreat/.<ComponentName>`
- **Action**: String action identifier (e.g. `android.intent.action.MAIN`)
- **Categories**: List of category identifiers (e.g. `android.intent.category.HOME`)
- **Flags**: Android Intent flags (e.g. `FLAG_ACTIVITY_NEW_TASK`)
- **Extras Bundle**: Key-value pair payload (`android.os.Bundle`)

#### Sample Intent Dispatch Payload (JSON representation)
```json
{
  "target_package": "com.example.dummythreat",
  "target_class": "com.example.dummythreat.MainActivity",
  "action": "android.intent.action.MAIN",
  "categories": [
    "android.intent.category.HOME",
    "android.intent.category.DEFAULT"
  ],
  "flags": 268435456,
  "extras": {}
}
```

### Response / Outcome Envelopes

Because all components in Dummy Threat are **inert**, invocations result in benign UI rendering or silent no-op execution.

#### Activity Success Envelope
- **Status**: `200 OK` (Activity Launched / UI Rendered)
- **Rendered Content**: `TextView` displaying `"This is a dummy app for testing."`

#### Service / Receiver Execution Envelope
- **Status**: `200 OK` (Callback Invoked)
- **Side Effects**: None (`Inert: does nothing`)

---

## Endpoints / Exported Interfaces by Resource

### 1. Main Activity (`MainActivity`)

- **Class Name**: `com.example.dummythreat.MainActivity`
- **Type**: Android Activity (`androidx.appcompat.app.AppCompatActivity`)
- **Exported Status**: `true` (`android:exported="true"`)
- **Description**: Main UI entry point and custom home launcher activity.
- **Required Permissions**: None.

#### Intent Filters

| Action | Categories |
| :--- | :--- |
| `android.intent.action.MAIN` | `android.intent.category.HOME`<br>`android.intent.category.DEFAULT` |

#### Request Parameters & Launch Intent
- **Path / Component**: `com.example.dummythreat/.MainActivity`
- **HTTP/CLI Equivalent**:
  ```bash
  adb shell am start -n com.example.dummythreat/.MainActivity
  ```

#### Response / UI Output
When launched, `MainActivity` creates a programmatic `TextView` and displays:
```json
{
  "status": "LAUNCHED",
  "component": "com.example.dummythreat.MainActivity",
  "view_hierarchy": {
    "type": "android.widget.TextView",
    "text": "This is a dummy app for testing."
  }
}
```

---

### 2. Dummy Accessibility Service (`DummyAccessibility`)

- **Class Name**: `com.example.dummythreat.DummyAccessibility`
- **Type**: Android Accessibility Service (`android.accessibilityservice.AccessibilityService`)
- **Exported Status**: `true` (`android:exported="true"`)
- **Description**: Accessibility service component registered with system accessibility filters.
- **Required Permission**: `android.permission.BIND_ACCESSIBILITY_SERVICE`

#### Intent Filters

| Action | Permission Guard |
| :--- | :--- |
| `android.accessibilityservice.AccessibilityService` | `android.permission.BIND_ACCESSIBILITY_SERVICE` |

#### Methods & Callbacks

##### `onAccessibilityEvent(event: AccessibilityEvent?)`
- **Description**: Triggered by the Android OS when accessibility events occur.
- **Parameters**: `event` (`AccessibilityEvent?`) - Event details passed by OS.
- **Behavior**: Inert callback (no-op).

##### `onInterrupt()`
- **Description**: Triggered when the service is interrupted by the OS.
- **Behavior**: Inert callback (no-op).

#### Sample Accessibility Event Payload (Received from OS)
```json
{
  "event_type": "TYPE_VIEW_CLICKED",
  "package_name": "com.example.dummythreat",
  "class_name": "android.widget.Button",
  "is_enabled": true
}
```

---

### 3. Dummy Device Admin Receiver (`DummyAdmin`)

- **Class Name**: `com.example.dummythreat.DummyAdmin`
- **Type**: Device Admin Receiver (`android.app.admin.DeviceAdminReceiver`)
- **Exported Status**: `true` (`android:exported="true"`)
- **Description**: Broadcast receiver that listens for device administration privilege events.
- **Required Permission**: `android.permission.BIND_DEVICE_ADMIN`

#### Intent Filters

| Action | Permission Guard |
| :--- | :--- |
| `android.app.action.DEVICE_ADMIN_ENABLED` | `android.permission.BIND_DEVICE_ADMIN` |

#### Methods & Callbacks

##### `onEnabled(context: Context, intent: Intent)`
- **Description**: Invoked when the user enables device admin privileges for Dummy Threat.
- **Parameters**: `context` (`Context`), `intent` (`Intent`)
- **Behavior**: Inert callback (no-op).

##### `onDisabled(context: Context, intent: Intent)`
- **Description**: Invoked when device admin privileges are revoked.
- **Parameters**: `context` (`Context`), `intent` (`Intent`)
- **Behavior**: Inert callback (no-op).

#### Sample Device Admin Event Payload
```json
{
  "action": "android.app.action.DEVICE_ADMIN_ENABLED",
  "receiver": "com.example.dummythreat/.DummyAdmin",
  "status": "ENABLED"
}
```

---

## Error Handling & Status Codes

Dummy Threat components interact with the standard Android application framework. Inter-process component calls yield standard system status codes:

| Error / Status Code | Condition | Description / Result |
| :--- | :--- | :--- |
| `200 OK` | Successful Launch / Callback | Component launched or callback triggered successfully. |
| `401 Unauthorized` | Security Exception | Caller lacks required system permissions (e.g. attempting to bind service without `BIND_ACCESSIBILITY_SERVICE`). |
| `404 Not Found` | `ActivityNotFoundException` | Specified target component or intent action is not registered in manifest. |
| `422 Unprocessable` | Illegal Argument | Intent payload format or extra parameters violate component expectations. |

### Sample Error Response Payload
```json
{
  "error": "SecurityException",
  "code": 401,
  "message": "Permission Denial: binding to service com.example.dummythreat/.DummyAccessibility requires android.permission.BIND_ACCESSIBILITY_SERVICE"
}
```

---

## Pagination & Querying

Because Dummy Threat does not expose database or network query endpoints, pagination, offset, limit, and sorting parameters are **not applicable**.
