# demo_mvvm_clean_2023

The DEMO MVVM CLEAR - App consuming a new examples it has been built with clean architecture principles, Repository Pattern, and MVVM pattern as well as Architecture Components.

#### 1. Functions

- Get data from API
- Show gridview data
- Search local
- Sort local

#### 2. Modules

- data : The data layer implements the repository interface that the domain layer defines. This layer provide a single source of truth for data.
- di : Handles data interacting with the network. 
- doman : The domain layer contains the UseCases that encapsulate a single and very specific task that can be performed. This task is part of the business logic of the application. 
- ui : MVVM with ViewModels exposing LiveData that the UI consume. The ViewModel does not know anything about it's consumers.
- util : Some support functions and constant value

#### 3. ScreenShot

![alt text](https://github.com/HiepHunbie93/demo_mvvm_clean_2023/blob/master/app/src/main/res/drawable/screenshot1.jpg?raw=true)

![alt text](https://github.com/HiepHunbie93/demo_mvvm_clean_2023/blob/master/app/src/main/res/drawable/screenshot2.jpg?raw=true)

![alt text](https://github.com/HiepHunbie93/demo_mvvm_clean_2023/blob/master/app/src/main/res/drawable/screenshot3.jpg?raw=true)
