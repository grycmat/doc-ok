# MedRecord App - Project Context for Claude Code

## Project Overview
I'm building a medical recording app called MedRecord (package: `com.gigapingu.dokok`) for Android using Kotlin and Jetpack Compose. The app records doctor-patient consultations, transcribes them on-device, sanitizes sensitive data, and creates appointment summaries.

## Tech Stack
- **Language**: Kotlin
- **UI**: Jetpack Compose with Material3
- **Architecture**: MVVM with simple architecture (not Clean Architecture - too boilerplatey for solo dev)
- **DI**: Hilt/Dagger (learning purposes)
- **Navigation**: Navigation Compose
- **Database**: Room (when needed)
- **Transcription**: Vosk (offline, on-device)

## Design System
The app uses a vibrant, modern design inspired by the Taskoo task management app with:
- **Primary colors**: Coral/Pink (#FF6B6B)
- **Secondary colors**: Mint Green (#4ECDC4)
- **Playful elements**: Floating circles, gradients, rounded cards
- **Professional medical context** with approachable, anxiety-reducing design

## Current Project Structure
```
com.gigapingu.dokok/
├── ui/
│   ├── theme/
│   │   ├── Color.kt          # Comprehensive color system
│   │   ├── Type.kt           # Typography definitions
│   │   └── Theme.kt          # MedRecordTheme with custom colors
│   ├── components/           # Reusable composables
│   │   ├── GradientBackground.kt
│   │   ├── HeaderCard.kt
│   │   ├── SearchInput.kt
│   │   ├── CategoryCard.kt
│   │   ├── TaskCard.kt
│   │   ├── InputField.kt
│   │   ├── DateTimePill.kt
│   │   ├── ProgressBar.kt
│   │   ├── ConsentCard.kt
│   │   ├── ActionButton.kt
│   │   ├── FloatingActionButton.kt
│   │   ├── SectionTitle.kt
│   │   ├── Divider.kt
│   │   ├── Toast.kt
│   │   └── LoadingIndicator.kt
│   └── screens/
│       ├── NewAppointmentScreen.kt  # Main appointment creation screen
│       └── NewAppointmentViewModel.kt
├── navigation/
│   └── AppNavigation.kt      # Navigation setup with routes
├── MainActivity.kt            # Entry point with Hilt
└── MedRecordApplication.kt    # Application class with @HiltAndroidApp
```

## Key Features Implemented
1. **New Appointment Screen**: Complete form with patient info, appointment type selection, date/time pickers, consent checkbox
2. **Custom Components Library**: 15+ reusable composables with animations
3. **Theme System**: Comprehensive Material3 theme with custom colors
4. **Navigation**: Full navigation graph setup
5. **Hilt Setup**: Basic dependency injection configured

## Design Principles
- **Minimalist but not boring**: Clean UI with playful touches
- **Medical-appropriate**: Professional yet approachable
- **User-friendly**: Clear visual hierarchy, intuitive interactions
- **Animated**: Smooth transitions and micro-interactions
- **Accessible**: Proper content descriptions and focus handling

## Current Development Phase
We're in early development, focusing on:
1. Frontend UI implementation first (to show and gain appreciation)
2. State management without backend initially
3. Adding backend functionality gradually (database, filesystem, services)

## Development Philosophy
- **Simple architecture** - No over-engineering
- **Add dependencies as needed** - Not "for future"
- **One-person project** - Optimize for solo developer productivity
- **Learning-oriented** - Using Hilt to learn, even if not strictly necessary

## Key Colors Reference
- Primary: Coral (#FF6B6B)
- Secondary: Mint Green (#4ECDC4)
- Consultation: Blue (#5F9CF3)
- Follow-up: Orange (#FFB347)
- Routine: Mint Green (#4ECDC4)
- Emergency: Red (#FF6B6B)

## Navigation Routes
- home
- new_appointment
- recording/{appointmentData}
- transcription_processing/{recordingPath}
- appointment_summary/{transcriptionId}
- appointment_list
- appointment_detail/{appointmentId}
- settings

## Next Steps
1. Implement Recording Screen UI
2. Add Room database for appointments
3. Implement audio recording service
4. Add Vosk transcription
5. Build data sanitization logic
6. Create appointment summary screen

## Important Notes
- Using `hiltViewModel()` instead of regular `viewModel()` for dependency injection
- Custom theme system with `MaterialTheme.customColors` for extended colors
- All composables use animated states for smooth UX
- Package name is `com.gigapingu.dokok` (not com.medical.recorder)

## Code Style Preferences
- Prefer simplicity over complexity
- Direct implementation over abstractions when appropriate
- Single activity with composable screens
- State hoisting where it makes sense
- Animated interactions for better UX

When implementing new features:
1. Start with UI/state management
2. Add backend functionality after UI works
3. Use existing components from ui/components/
4. Follow established color and typography patterns
5. Include animations for state changes
6. Keep it simple - this is a solo project