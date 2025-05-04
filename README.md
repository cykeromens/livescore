# ⚽ Live ScoreBoard System

This is a simple Java-based system for tracking live football matches, scores, and summaries in real time.

## 🧩 Features

- Start a new match between two teams
- Update scores for ongoing matches
- Finish a match to remove it from the active scoreboard
- View a summary of all ongoing matches, sorted by:
    - Total score (descending)
    - Most recent match (if scores are equal)

## 🛠️ Technologies

- Java 17+
- JUnit 5 for testing
- SLF4J with Logback for logging
- Clean Code and SOLID Principles
- TDD-first design approach

## 🧠 Assumptions

- A match is uniquely identified by its home and away team names.
- Team names are case-insensitively compared for uniqueness (e.g., "Brazil" and "BRAZIL" are considered the same).
- A team cannot play against itself.
- Only ongoing matches are stored in memory; finished matches are discarded.
- Match time (`startTime`) is recorded using `System.nanoTime()` at match creation.
- Scores cannot be negative.
- The scoreboard is not persistent — restarting the application clears all matches.

## 🧪 Testing Strategy

- Test-Driven Development (TDD) followed to define behavior before implementation.
- Parameterized tests verify case-insensitive validation.
- All constructor and behavior edge cases are tested (null/blank teams, duplicate names, invalid scores).

## 🔐 Validation

- Teams must not be `null` or blank.
- Home and away teams must be different (ignoring case).
- Scores must be non-negative integers.
- Attempting to update or finish a non-existent match throws `MatchNotFoundException`.

## 🔧 Design Principles Used

- **Single Responsibility Principle**: Logic is broken down into clear methods and validation routines.
- **Open/Closed Principle**: The system can be extended (e.g., to add persistence) without modifying core classes.
- **Encapsulation**: Match data is encapsulated with proper getters/setters.
- **Fail-Fast**: Input validation immediately throws descriptive exceptions on invalid data.
- **Logging**: SLF4J-based logging enables production diagnostics.

## 🚀 Potential Extensions

- Add match ID or UUID for better uniqueness instead of just team names.
- Add persistence (database or file-based).
- Introduce concurrency support (e.g., for multi-user environments).
- Add REST API endpoints with Spring Boot.
- WebSocket-based live updates for real-time clients.

