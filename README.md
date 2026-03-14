# Pins

> A travel planning app for Android — pin locations on a map and organize them into trips.

![Min SDK](https://img.shields.io/badge/minSdk-24%20(Android%207.0)-blue)
![Target SDK](https://img.shields.io/badge/targetSdk-36-blue)
![Kotlin](https://img.shields.io/badge/Kotlin-2.3.10-purple)

---

## About

Pins lets you search for places, drop pins on a map, and group them into trips. Each trip can have
one or multiple stops, with arrival and departure dates per stop. Trips are stored locally and
visualized on an interactive Google Map.

---

## Features

- Search for places using the Google Places API
- Pin locations on a Google Map
- Create single-stop or multi-stop trips
- Set arrival and departure dates per stop
- View and edit saved trips in a trip list
- Country flag icons displayed alongside locations
- Local mock flavor for development without API keys

---

## Tech Stack

| Category              | Library / Tool                         |
|-----------------------|----------------------------------------|
| UI                    | Jetpack Compose + Material 3           |
| Navigation            | Navigation Compose 2.9.7               |
| Dependency Injection  | Hilt 2.59.2                            |
| Maps                  | Google Maps Compose 8.2.0              |
| Places Search         | Google Places API 5.1.1                |
| Local Database        | Room 2.8.4                             |
| Async                 | Kotlin Coroutines + StateFlow          |
| Testing               | JUnit 4, MockK, Espresso, Hilt Testing |
| Memory Leak Detection | LeakCanary (debug only)                |

---

## Architecture

The app follows **MVVM** with a single-activity design:

- **Single Activity** (`MainActivity`) hosts a `NavHost` with bottom navigation
- **3 tabs:** Map, Trip List, Settings
- **ViewModels** expose `StateFlow<State>` consumed by Compose screens via
  `collectAsStateWithLifecycle()`
- **Hilt** provides dependencies; `@AssistedInject` is used for ViewModels that require navigation
  arguments (e.g. trip ID, place ID)
- **Room** persists trips and stops locally
- **Navigation** is handled through a `Navigator` abstraction passed into ViewModels

---

## Getting Started

### Prerequisites

- Android Studio Hedgehog or newer
- JDK 21
- A Google Cloud project with **Maps SDK for Android** and **Places API** enabled
- An API key for the above services

### Setup

1. Clone the repository:
   ```bash
   git clone https://github.com/ArturBorowy/Pins.git
   cd Pins
   ```

2. Add your Google Maps API key to `app/src/main/res/values/keys.xml`:
   ```xml
   <string name="maps_api_key">YOUR_API_KEY_HERE</string>
   ```
   *(Only required for the `remoteReal` flavor — see Build Variants below.)*

3. Open the project in Android Studio and sync Gradle.

4. Select a build variant (see below) and run on a device or emulator.

---

## Build Variants

The app has two product flavors on the `remote` dimension:

| Flavor       | Version suffix | Description                                                                                 |
|--------------|----------------|---------------------------------------------------------------------------------------------|
| `localMock`  | `-mock`        | Uses in-memory mock data. No Google API key required. Ideal for development and UI testing. |
| `remoteReal` | `-real`        | Uses the real Google Maps and Places APIs. Requires a valid API key in `keys.xml`.          |

To select a variant in Android Studio: **Build > Select Build Variant**, then choose
`localMockDebug` or `remoteRealDebug`.

---

## Screenshots
