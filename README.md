# 💹 Converto 
A simple app for currency conversion which uses [Open Exchange Rates (free account)](https://openexchangerates.org/) 

| Splash | Home | Converion |
|--------|------|-----------|
| ![Splash](media/app_logo_splash.png) | ![Home](media/home_initial.png) | ![Conversion](media/home_with_data.png) |

### Achitecture
This project follows MVVM (clean architecture) which increases code testability and quality. As this is a small preject, I've skipped the UseCase layer which is beneficial for large projects with many screens and usecases.

This app is created using latest UI framework (Jetpack Compose). Also added support for "Material You" theme engine (i.e. App adapts to device theme colors).

### Caching
App caches the latest rates to save bandwidth which is refreshed every 30 minutes. Shared Prefs is used for caching as it is simple and takes less time for development but Datastore or Room is a preferred way (which is easy to migrate from as MVVM clean architecture used).

App also caches user's last session data which is selected currencies.

### Unit tests 
Unit test for Data layer, Utils and ViewModels are added to ensure functional correctness. I was unable to cover 100% code coverage given time constraints but I'm sure that I've covered almost all the important functions and all the different scenarios which can be referred to write other testcases.

### Directory structure
Here is the directory structure of the app with unit tests code coverage

```bash
Converto
├── data                      // Data layer (Class 68%, Method 58%, Line 71%)
│   ├── datasources           // Class 63%, Method 60%, Line 70%
│   │   ├── local             // Class 68%, Method 63%, Line 64%
│   │   │   └── prefs
│   │   └── remote            // Class 57%, Method 54%, Line 75%
│   │       ├── interceptors
│   │       ├── mappers
│   │       └── services
│   ├── models
│   └── repos                 // Class 75%, Method 51%, Line 67%
│       ├── open_exchange
│       └── user             
├── di
├── ui
│   ├── components
│   │   └── core
│   ├── models
│   ├── screens
│   │   └── home              // HomeContentViewModel (Class 100%, Method 96%, Line 93%)
│   └── theme
└── utils                     // Class 92%, Method 94%, Line 91%
    └── extensions
```
