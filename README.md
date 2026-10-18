# noxen playground

`noxen playground` is the Android target app used to validate noxen during local
development and testing.

The app intentionally exposes buttons that exercise common Android communication
surfaces:

![noxen playground app](assets/screenshots/app.png)

- Activity launch and result flows
- BroadcastReceiver and ordered broadcast flows
- Three separate broadcast permission examples: protected receiver, recipient
  permission, and both constraints. `Receiver2` requires senders to hold
  `dev.noxen.playground.permission.SEND_TO_RECEIVER`; the permission argument passed
  to `sendBroadcast` requires recipients to hold
  `dev.noxen.playground.permission.RECEIVE_BROADCAST`. Both are signature permissions
  declared and requested by the playground. These same-app examples demonstrate
  configuration and capture, not rejection of unauthorized external apps.
- Service and bindService flows
- PendingIntent creation paths
- Concurrent broadcasts from multiple threads
- Explicit and implicit attack-surface examples

## Build and run with noxen

Build the app:

```bash
./gradlew assembleDebug
```

Install it on a connected device, then launch noxen:

```bash
noxen
```

From the Home tab, select the device, choose **Spawn** mode, and select package
`dev.noxen.playground`. If the app is already running, use **Attach
(app name)** with `noxen playground`.

## Android Studio

Open this directory directly:

```text
noxen-playground/
```

## App identity

- App label: `noxen playground`
- Package: `dev.noxen.playground`

## License

`noxen playground` is released under the [MIT License](LICENSE).
