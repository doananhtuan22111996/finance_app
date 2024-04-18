# Finance App

Finance App is a mobile application designed to help users manage their finances efficiently. Whether you want to track your expenses, create budgets, or analyze your spending habits, this app provides the tools you need to stay on top of your financial health.

## Architecture

The Finance App follows the architecture demonstrated in the [android_architecture](https://github.com/doananhtuan22111996/android_architecture) repository, which encompasses various architectural patterns and best practices. This architecture includes:

- **MVVM Architecture**: The project follows the Model-View-ViewModel (MVVM) architecture, separating concerns between data, UI, and logic.
- **Data Binding**: Utilizes Android Data Binding Library to bind UI components in layouts to data sources in the app's architecture.
- **Room Database**: Integrates Room Persistence Library to provide an abstraction layer over SQLite, making database interactions smoother and more robust.
- **Retrofit**: Implements Retrofit library for handling network requests and simplifying REST API consumption.
- **LiveData and ViewModel**: Uses LiveData and ViewModel to manage UI-related data in a lifecycle-conscious way and to persist data across configuration changes.
- **Dependency Injection**: Incorporates Dagger Hilt for dependency injection to keep the code modular, maintainable, and testable.
- **Coroutines**: Integrates Kotlin Coroutines for asynchronous programming, making code more concise and readable.
- **Unit Testing**: Provides unit tests for ViewModel classes using JUnit and Mockito frameworks.

## Technologies Used

- **Programming Language**: Kotlin
- **Database**: SQLite
- **Dependency Injection**: Dagger Hilt
- **Networking**: Retrofit
- **Authentication**: Firebase Authentication
- **Data Binding**: Android Data Binding Library
- **Charts**: MPAndroidChart
- **Unit Testing**: JUnit, Mockito

## Getting Started

To get started with Finance App, follow these steps:

1. Clone this repository: `git clone https://github.com/doananhtuan22111996/finance_app.git`
2. Open the project in Android Studio.
3. Set up Firebase Authentication and update the google-services.json file with your configuration.
4. Build and run the project on an emulator or a physical device.

## Contributing

Contributions are welcome! If you have suggestions, bug reports, or want to add new features, feel free to open an issue or submit a pull request.

## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.
