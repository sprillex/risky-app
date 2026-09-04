# Dummy Threat

Dummy Threat is an inert Android application specifically designed to safely test Android security and monitoring tools (such as Guard Dog). By declaring sensitive permissions, device admin receivers, accessibility services, and launcher intent filters without executing any malicious payload, Dummy Threat allows security software to evaluate threat detection capabilities in a controlled environment.

## Features

- **Inert Threat Simulation**: Deliberately declares high-risk permissions and components while remaining completely benign and safe to run.
- **System Alert Window Permission**: Declares `android.permission.SYSTEM_ALERT_WINDOW` for testing overlay and display privilege detection.
- **SMS Permissions**: Requests `SEND_SMS`, `READ_SMS`, and `RECEIVE_SMS` permissions to simulate SMS-handling capabilities.
- **Home Screen Launcher Intent**: Registers as a custom home screen (`android.intent.category.HOME`) launcher.
- **Device Admin Receiver**: Includes a custom `DeviceAdminReceiver` (`DummyAdmin`) bound with `BIND_DEVICE_ADMIN`.
- **Accessibility Service**: Includes an `AccessibilityService` (`DummyAccessibility`) bound with `BIND_ACCESSIBILITY_SERVICE`.

## Tech Stack & Architecture

- **Language**: Kotlin (`1.9.22`)
- **Framework & Libraries**: AndroidX Core KTX (`1.12.0`), AppCompat (`1.6.1`), Material Components (`1.11.0`), ConstraintLayout (`2.1.4`)
- **Target SDK**: Android 14 (API Level 34)
- **Minimum SDK**: Android 7.0 (API Level 24 / `minSdk = 24`)
- **Build System**: Gradle (`8.8`) with Android Gradle Plugin (`8.2.1`)
- **JVM Compatibility**: Java 17

## Repository Layout

```text
.
├── app/
│   ├── build.gradle.kts           # App module build configuration and dependencies
│   └── src/
│       └── main/
│           ├── AndroidManifest.xml # Declares permissions, activity, service, and receiver
│           └── java/com/example/dummythreat/
│               ├── DummyAccessibility.kt # Inert AccessibilityService implementation
│               ├── DummyAdmin.kt         # Inert DeviceAdminReceiver implementation
│               └── MainActivity.kt       # Application entry point activity
├── gradle/
│   └── wrapper/                    # Gradle wrapper configuration files and jar
├── releases/
│   └── dummythreat-debug.apk       # Pre-compiled debug APK distribution binary
├── build.gradle.kts               # Root Gradle project configuration
├── gradle.properties              # Project-wide Gradle build properties
├── gradlew                        # Unix Gradle wrapper executable script
├── gradlew.bat                    # Windows Gradle wrapper batch script
└── settings.gradle.kts            # Gradle settings and module inclusion
```

## Prerequisites & Setup

### Prerequisites
- **Java Development Kit (JDK)**: Version 17 or higher.
- **Android SDK**: API Level 34 with Android SDK Platform-Tools (includes `adb`).
- **Gradle**: Gradle 8.8 (or use the included Gradle wrapper `./gradlew`).

### Setup
1. Clone the repository:
   ```bash
   git clone https://github.com/example/dummy-threat.git
   cd dummy-threat
   ```
2. Set `ANDROID_HOME` environment variable if not set automatically:
   ```bash
   export ANDROID_HOME=/path/to/android-sdk
   ```
3. Grant execution permissions to the Gradle wrapper:
   ```bash
   chmod +x gradlew
   ```

## Configuration

Dummy Threat does not require external `.env` files or runtime secret configuration. All app metadata, permissions, and component configurations are managed in the Android manifest and Gradle build files:

- **Application ID**: `com.example.dummythreat`
- **Label**: `System Update Helper` (declared in `app/src/main/AndroidManifest.xml`)
- **Permissions**: Defined in `app/src/main/AndroidManifest.xml`
- **Compile / Target SDK**: `34` (configured in `app/build.gradle.kts`)

## Building & Running

### Build Debug APK
To assemble the debug build APK:
```bash
./gradlew assembleDebug
```
The output APK will be generated at:
`app/build/outputs/apk/debug/app-debug.apk`

### Install and Launch on Device / Emulator
Ensure an Android device or emulator is connected via `adb`:

1. Install the APK:
   ```bash
   adb install -r app/build/outputs/apk/debug/app-debug.apk
   ```
2. Launch the main activity:
   ```bash
   adb shell am start -n com.example.dummythreat/.MainActivity
   ```

Alternatively, you can install the pre-compiled APK directly from the `releases/` folder:
```bash
adb install -r releases/dummythreat-debug.apk
```

## Testing

### Run Unit Tests
To run local JVM unit tests:
```bash
./gradlew test
```

### Run Android Lint / Code Checks
To execute static analysis and code verification checks:
```bash
./gradlew check
```

## API Reference

Dummy Threat is a client-side Android application that does not expose HTTP REST endpoints or remote network APIs. Instead, its public interface consists of Android Inter-Process Communication (IPC) entry points, system permissions, and exported application components (`Activity`, `AccessibilityService`, `DeviceAdminReceiver`).

For complete details on declared permissions, intent filters, exported component parameters, and lifecycle behaviors, see [API.md](./API.md).
