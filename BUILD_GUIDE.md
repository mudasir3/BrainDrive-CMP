# Build Guide - Separate APKs for Each Game

This project is structured to allow building separate APKs for each game, suitable for publishing individually on the Play Store.

## Game Modules

The project includes the following games:
1. **Math It** - Math operation identification game
2. **Categorize - Edible** - Categorize edible items
3. **Categorize - Consumer** - Categorize consumer products
4. **Categorize - Human** - Categorize human-related items
5. **Memory Game 1** - Memory matching game with emojis
6. **Memory Game 2** - Memory matching game with numbers

## Building Individual Game APKs

### Using Product Flavors

The main app module uses product flavors to build separate APKs. Each flavor represents a different game.

#### Build Commands

To build a specific game APK, use:

```bash
# Math Game
./gradlew assembleMathGameRelease

# Categorize Edible
./gradlew assembleCategorizeEdibleRelease

# Categorize Consumer
./gradlew assembleCategorizeConsumerRelease

# Categorize Human
./gradlew assembleCategorizeHumanRelease

# Memory Game 1
./gradlew assembleMemoryGame1Release

# Memory Game 2
./gradlew assembleMemoryGame2Release

# All games at once
./gradlew assembleAllGamesRelease
```

#### Debug Builds

For debug builds, replace `Release` with `Debug`:

```bash
./gradlew assembleMathGameDebug
```

### APK Locations

Built APKs will be located at:
```
app/build/outputs/apk/{flavorName}/release/app-{flavorName}-release.apk
```

For example:
- `app/build/outputs/apk/mathGame/release/app-mathGame-release.apk`
- `app/build/outputs/apk/categorizeEdible/release/app-categorizeEdible-release.apk`

## Modular Structure

Each game is implemented as a separate feature module:
- `feature/games/math/` - Math game
- `feature/games/categorize/` - Categorize games
- `feature/games/memory/` - Memory games

The core functionality is shared through:
- `core/domain/` - Business logic and use cases
- `core/data/` - Data layer (Room database, DataStore)
- `core/ui/` - Shared UI components and theme

## Play Store Publishing

Each game APK can be published separately with:
- Unique application ID (configured in product flavors)
- Unique app name and icon (configured per flavor)
- Independent versioning
- Separate Play Store listings

## Features

All games include:
- ✅ Difficulty levels (Easy, Medium, Hard)
- ✅ Score tracking and high scores
- ✅ Timer-based gameplay
- ✅ Beautiful Material 3 UI
- ✅ Smooth animations
- ✅ Result screens with statistics

## Development

To work on a specific game:
1. Navigate to the game's feature module
2. Make changes in the respective package
3. Test using the specific flavor build
4. Build release APK when ready

## Notes

- All games share the same core database for scores (optional: can be separated per game)
- User preferences are shared across all games
- Each game can be configured with different app icons and names via flavor-specific resources

