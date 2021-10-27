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

### Screenshots
![homepage](screenshots/Homepage.png)

![addTask](screenshots/AddTask.png)

![allTasks](screenshots/AllTasks.png)

![taskDetails](screenshots/TaskDetails.png)

![settings](screenshots/Settings.png)