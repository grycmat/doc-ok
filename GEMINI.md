## Project Overview

This is an Android application named **DokOk**, built with Kotlin and Jetpack Compose. The application is designed to assist medical professionals in recording and managing patient appointments. The main feature of the app is to create a new appointment by filling in patient details, appointment type, and clinical notes, and then start a recording of the appointment.

**Key Technologies:**

*   **UI:** Jetpack Compose
*   **Language:** Kotlin
*   **Build Tool:** Gradle
*   **Architecture:** The app follows a single-activity architecture with a `NavHost` to manage different screens. It uses a `ViewModel` to manage the UI state.

## Building and Running

To build and run the project, you can use Android Studio or the Gradle wrapper from the command line.

**Using Android Studio:**

1.  Open the project in Android Studio.
2.  Let Gradle sync the project.
3.  Select the `app` run configuration.
4.  Choose an emulator or a connected device.
5.  Click the "Run" button.

**Using the command line:**

*   **Build the project:**

    ```bash
    ./gradlew build
    ```

*   **Install the app on a connected device or emulator:**

    ```bash
    ./gradlew installDebug
    ```

*   **Run tests:**

    ```bash
    ./gradlew test
    ```

## Development Conventions

*   **UI:** The UI is built entirely with Jetpack Compose. Reusable UI components are located in the `app/src/main/java/com/gigapingu/docok/ui/components` directory.
*   **State Management:** The UI state is managed by `ViewModel`s. The state is exposed to the UI using `StateFlow` and collected as state in the composable functions.
*   **Navigation:** Navigation between screens is handled by a `NavHost` in `app/src/main/java/com/gigapingu/docok/navigation/AppNavigation.kt`.
*   **Styling:** The app uses a custom theme defined in `app/src/main/java/com/gigapingu/docok/ui/theme`.