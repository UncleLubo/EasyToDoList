# EasyToDoList

A modern, clean, and efficient Todo List application built with Jetpack Compose.

## 🚀 Features

- **Jetpack Compose UI**: Modern declarative UI for a smooth user experience.
- **Task Management**: Create, toggle, and delete tasks with ease.
- **Drag-and-Drop Reordering**: Organize your tasks exactly how you want them using `sh.calvin.reorderable`.
- **Swipe-to-Dismiss**: Quickly remove tasks with a simple swipe gesture.
- **Android Glance Widgets**: Keep track of your tasks directly from your home screen.
- **Room Database**: Local persistence to ensure your data is always available, even offline.
- **Hilt Dependency Injection**: Clean and maintainable architecture.
- **MVVM Pattern**: Separation of concerns for better testability and scalability.

## 🛠️ Tech Stack

- **Language**: Kotlin
- **UI Framework**: Jetpack Compose
- **Database**: Room
- **Dependency Injection**: Hilt
- **Asynchronous Programming**: Coroutines & Flow
- **Home Screen Widgets**: Jetpack Glance
- **Reordering Library**: [Reorderable](https://github.com/Calvin-Sh/Reorderable)

## 📁 Project Structure

- `data`: Contains Room entities, DAOs, and the database implementation.
- `repository`: Handles data operations and provides a clean API to the ViewModel.
- `ui`: Contains Compose screens, ViewModels, and theme definitions.
- `widget`: Implementation of Android Glance home screen widgets.
- `di`: Hilt modules for dependency injection.

## 🚦 Getting Started

1. Clone the repository.
2. Open the project in Android Studio (Ladybug or newer recommended).
3. Sync the project with Gradle files.
4. Run the app on an emulator or a physical device.

## 📝 Recent Updates

- Implemented drag-and-drop reordering for todo items.
- Added database migration from v1 to v2 to support task positioning.
- Integrated bulk updates for efficient reordering persistence.
- Enhanced UI to disable swipe actions during drag-and-drop.
