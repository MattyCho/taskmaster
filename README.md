# taskmaster

## Description
A simple task managing app built for Android Studio practice. The app contains 3 pages:
    - Homepage
    - Add Task - This page includes a simple form for a task/description and submit button.
    - All Tasks - Page to display all tasks.

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

### Screenshots
![homepage](screenshots/Homepage.png)

![homepage v3](screenshots/Homepagev3.png)

![addTask](screenshots/AddTask.png)

![allTasks](screenshots/AllTasks.png)

![taskDetails](screenshots/TaskDetails.png)

![settings](screenshots/Settings.png)