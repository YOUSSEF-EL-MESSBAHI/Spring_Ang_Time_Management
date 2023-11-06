# Employee Time Tracking Application

This project is a web application developed for employees to track their working hours. The application allows employees to enter their start and end of the workday, breaks taken, and generates a weekly report of their working hours. Additionally, it enables employees to submit their mood at the end of the day, ensuring anonymity within the system. Employees receive an email before 6 p.m. from Monday to Friday, allowing them to access a page and submit their mood.

## Technologies Used

- Spring Boot
- Angular
- JWT
- Docker

## Project Overview

### Steps Involved

1. **Development of the User Interface:** The user interface facilitates the input of the following information:
   - Date
   - Start time
   - End time
   - Breaks

2. **Development of the Database:** The database includes the following tables:
   - **Employees:** Information related to employees:
     - ID
     - Name
     - Surname
     - Email
     - Password
   - **Work Times:** Data about employees' work schedules:
     - ID
     - Employee ID
     - Date
     - Start time
     - End time
     - Breaks

3. **Development of the Cron Job:** A cron job is used to create the weekly report, including:
   - Employee ID
   - Employee Name
   - Employee Surname
   - Worked hours
   - Overtime hours

4. **Docker Hosting:** The application can be hosted on a web server or in a Docker container, simplifying deployment and maintenance.

## Installation

To run the application, follow these steps:

1. Install Docker on your system.
2. Pull the Docker image from the repository.
3. Run the Docker container with the required environment variables and configurations.
   
For more detailed instructions, please refer to the documentation provided in the repository.

## Contributing

Contributions to this project are encouraged. If you encounter any issues or have suggestions for improvement, feel free to:
- Create a pull request
- Open an issue in the repository
