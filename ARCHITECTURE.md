# Brain Drive - Architecture Documentation

## Overview

Brain Drive has been completely rewritten from the ground up using modern Android development practices, following Clean Architecture principles with MVI pattern, and designed to be Kotlin Multiplatform-ready for future iOS deployment.

## Architecture Layers

### 1. Presentation Layer (Features)
- **Location**: `feature/` modules
- **Components**: 
  - Compose UI Screens
  - ViewModels (MVI pattern)
  - UI State and Intents
- **Pattern**: MVI (Model-View-Intent)
  - **Model**: UI State (immutable data classes)
  - **View**: Compose UI functions
  - **Intent**: User actions (sealed classes)

### 2. Domain Layer
- **Location**: `core/domain/`
- **Components**:
  - Use Cases (business logic)
  - Domain Models (pure Kotlin data classes)
  - Repository Interfaces
- **Dependencies**: None (pure Kotlin, no Android dependencies)

### 3. Data Layer
- **Location**: `core/data/`
- **Components**:
  - Repository Implementations
  - Room Database (entities, DAOs)
  - DataStore (preferences)
  - Data Mappers (Entity ↔ Domain)
- **Dependencies**: Android-specific (Room, DataStore)

## Module Structure

```
app/                    # Main application module
├── MainActivity.kt
├── BrainDriveApplication.kt
└── navigation/         # Navigation setup

core/
├── common/             # Shared utilities, constants
├── data/               # Data layer implementation
│   ├── local/          # Room, DataStore
│   ├── repository/     # Repository implementations
│   └── mapper/         # Data mappers
├── domain/             # Business logic layer
│   ├── model/          # Domain models
│   ├── repository/     # Repository interfaces
│   └── usecase/        # Use cases
└── ui/                 # Shared UI components
    └── theme/          # Material 3 theme

feature/
├── splash/             # Splash screen feature
├── home/               # Home screen feature
├── games/              # Games feature
│   └── math/           # Math game implementation
└── settings/           # Settings feature
```

## MVI Pattern Implementation

Each feature follows the MVI pattern:

```kotlin
// State (Model)
data class FeatureUiState(
    val data: String = "",
    val isLoading: Boolean = false
)

// Intent (User Actions)
sealed class FeatureIntent {
    object LoadData : FeatureIntent()
    data class UpdateData(val data: String) : FeatureIntent()
}

// ViewModel
@HiltViewModel
class FeatureViewModel @Inject constructor(
    private val useCase: SomeUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(FeatureUiState())
    val uiState: StateFlow<FeatureUiState> = _uiState.asStateFlow()
    
    fun handleIntent(intent: FeatureIntent) {
        when (intent) {
            is FeatureIntent.LoadData -> loadData()
            is FeatureIntent.UpdateData -> updateData(intent.data)
        }
    }
}

// View (Compose)
@Composable
fun FeatureScreen(
    viewModel: FeatureViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    // UI implementation
}
```

## Dependency Injection

Using **Hilt** for dependency injection:
- Application-level: `@HiltAndroidApp`
- Activity-level: `@AndroidEntryPoint`
- ViewModel: `@HiltViewModel`
- Modules: `@Module` with `@InstallIn`

## Database

**Room Database** replaces the old SQLite implementation:
- Entities: `GameScoreEntity`
- DAOs: `GameScoreDao`
- Database: `BrainDriveDatabase`
- Type Converters: `GameTypeConverter`

## Data Storage

**DataStore Preferences** for user settings:
- Replaces SharedPreferences
- Type-safe, coroutine-based
- Flow-based reactive updates

## Navigation

**Navigation Compose** for type-safe navigation:
- Sealed class `Screen` for routes
- Type-safe arguments
- Deep linking support ready

## Kotlin Multiplatform Readiness

The architecture is designed to be Kotlin Multiplatform-ready:

### Already Compatible:
- ✅ Domain layer (pure Kotlin, no Android dependencies)
- ✅ Use cases (pure Kotlin)
- ✅ Repository interfaces (pure Kotlin)
- ✅ Domain models (pure Kotlin data classes)

### Needs Platform-Specific Implementation:
- ⚠️ Data layer (Room → SQLDelight for multiplatform)
- ⚠️ UI layer (Compose → Compose Multiplatform)
- ⚠️ Navigation (Navigation Compose → Decompose or custom)

### Migration Path:
1. Replace Room with SQLDelight
2. Migrate Compose to Compose Multiplatform
3. Use Decompose or custom navigation solution
4. Platform-specific implementations in `expect/actual` modules

## SOLID Principles

### Single Responsibility Principle (SRP)
- Each class has one responsibility
- ViewModels handle UI logic only
- Use cases handle single business operations
- Repositories handle data operations only

### Open/Closed Principle (OCP)
- Use sealed classes for extensibility
- Repository interfaces allow different implementations

### Liskov Substitution Principle (LSP)
- Repository implementations are interchangeable
- Use case implementations follow contracts

### Interface Segregation Principle (ISP)
- Small, focused interfaces
- Repository interfaces are specific to their domain

### Dependency Inversion Principle (DIP)
- High-level modules depend on abstractions (interfaces)
- Low-level modules implement interfaces
- Dependency injection via Hilt

## Testing Strategy

### Unit Tests
- Use cases (pure Kotlin, easy to test)
- ViewModels (with coroutines test)
- Repository implementations

### Integration Tests
- Database operations
- DataStore operations

### UI Tests
- Compose UI tests
- Navigation tests

## Best Practices

1. **Immutable State**: All UI states are immutable data classes
2. **Unidirectional Data Flow**: State flows down, events flow up
3. **Separation of Concerns**: Clear boundaries between layers
4. **Dependency Injection**: All dependencies injected via Hilt
5. **Coroutines & Flow**: Reactive, non-blocking operations
6. **Type Safety**: Sealed classes for type-safe navigation and intents
7. **Material 3**: Modern design system
8. **Animations**: Smooth, delightful user experience

## Future Enhancements

- [ ] Kotlin Multiplatform migration
- [ ] More comprehensive test coverage
- [ ] Performance optimizations
- [ ] Accessibility improvements
- [ ] Internationalization (i18n)
- [ ] Analytics integration
- [ ] Crash reporting
- [ ] Remote configuration

