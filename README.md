# taskmaster
[Initial Google Play Store Release v1.0 APK](./app/release/app-release.aab)

## Description
A simple task managing app built for Android Studio practice. The app contains 3 pages:
    - Homepage - Displays all tasks.
    - Add Task - This page includes a simple form for creating a task and a submit button.
    - All Tasks - Page only displays an image.
    - Settings - Click the wrench in the bottom right corner. User can set a username and a team.

## Release Process and Notes
- Had to use an android emulator which was incredibly slow, crashed, and wouldn't let me sign in to my Google account.
- Creating an account for Google Play Console was really easy.
- On the other hand, creating a release for my app was incredibly time consuming. So many questions and their page layout is not the most intuitive.
- I'm guessing if I make changes to my app from here on out, I have to create a new signed bundle and upload that to the Google Play Console.
- Screenshots:
![App Download Screen](screenshots/Download.png)
![Release v1.0](screenshots/Release1.0.png)

## Changelog

### [1.0.0] - 2021-10-25
### Added
- `Homepage` with 2 buttons that redirect to `Add Task` and `All Tasks` pages.
- `Add Task` page with a simple form for task title/description and a submit button.
- Submit button will display a "submitted!" message.
- `All Tasks` page, currently no functions, just displays an image.

### [1.0.1] - 2021-10-26
### Added
- `TaskDetail` activity that displays the title and description of the task.
- `Settings` activity that allows the user to save a username which is reflected on the `Homepage`.

### Changed
- `Homepage` now has 3 hardcoded tasks with a button that takes it to a `TaskDetail` page.
- `Homepage` also has a wrench icon that takes the user to the settings page.

### [1.0.2] - 2021-10-27
### Added
- Implemented a `RecyclerView` to render a task list on the homepage
- Created a fragment and `ViewAdapter` to support the `RecyclerView`.
- `Task` model which takes in a title, description, state, and date.

### Changed
- Homepage now displays a hardcoded list which implements a `RecyclerView`.
- Clicking on a task in the list takes you to the `TaskDetail` page with the appropriate title.

### Removed
- Hardcoded list and buttons on the homepage.

### [1.0.3] - 2021-10-28
### Added
- Room implementation for database storage.
- Created a Database, DAO, and a converter.
- Adding a task now adds it to a database which is displayed on the homepage.

### Changed
- Task Status property has been changed to an enum instead of a string.

### [1.0.4] - 2021-11-1
### Added
- Added an Espresso test to check that the two buttons properly load on the homepage.a
- Added an Espresso test to check that buttons on the homepage lead to the correct activities.
- Added an Espresso test to make sure that the settings activity properly updates the username on the homepage.

### Removed
- Removed Room and database storage to prepare for AWS Amplify.

## [1.1.0] - 2021-11-2
### Added
- Implemented AWS Amplify and DynamoDB for cloud storage of task data.

## Changed
- Reworked all database related code to use AWS Amplify instead of Room.
- Task data now pulls information from DynamoDB.

## [1.1.1] - 2021-11-3
### Added
- Team entity. Tasks now have a Team property.
- Teams are stored to the database.
- 3 Teams were manually created and then commented out so the program doesn't automatically keep making teams.

### Changed
- When adding a new task there is now a spinner to select which team that task belongs too.
- Tasks and teams are connected by a One-To-Many relationship.

## [1.1.2] - 2021-11-4
### Added
- Filter Tasks by Team by going to the settings page and selecting a team.

### Changed
- Fixed DateTime format to look cleaner
- Fixed Spinner values to normal strings (Not capitalized/enum values)
- Settings page now includes a Team spinner.

### Screenshots
![homepage](screenshots/Homepage.png)

![homepage v3](screenshots/Homepagev3.png)

![addTask](screenshots/AddTask.png)

![allTasks](screenshots/AllTasks.png)

![taskDetails](screenshots/TaskDetail.png)

![settings](screenshots/Settings.png)
