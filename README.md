# Racing Championship Management Application

This Spring Boot application manages data and analytics for a racing championship system. It covers drivers, teams, races, championships, and analytics. The functionality is grouped into several modules with dedicated RESTful APIs.

## 1. Races Module

### Functionality:
- Create a new race, including race location, date, and results for each participating driver.
- Get details of a specific race by its ID.
- Fetch the winner of the most recent race (i.e., the driver who finished in 1st place).

### Use Case:
Race organizers input new race data after events. Applications and users can retrieve detailed race results and identify recent winners for display or analysis.

## 2. Analytics Module

### Functionality:
- Retrieve statistics for a specific driver.
- Retrieve statistics for a specific team.
- Fetch current driver rankings based on performance.
- Fetch current team rankings.
- Recalculate and refresh all statistics manually.

### Use Case:
This module supports leaderboard views, performance analytics, and historical tracking. Admins can trigger a statistics refresh when data changes (e.g., after races).

## 3. Championships Module

### Functionality:
- Retrieve a list of all championships.
- Get details of a specific championship by ID, including associated drivers.
- Fetch standings for a specific year.
- Create a new championship.
- Trigger a recalculation of standings for a specific championship.

### Use Case:
Used by the system to manage seasonal competitions. Viewers and participants can see how drivers are ranked across a season.

## 4. Drivers Module

### Functionality:
- Get a list of all drivers.
- Get detailed info about a specific driver by ID.
- Get all drivers that belong to a specific team.
- Add a new driver to the system.

### Use Case:
Supports roster management and enables teams to associate drivers with results and analytics.

## 5. Teams Module

### Functionality:
- Get a list of all teams.
- Get detailed info about a specific team by ID, including its drivers.
- Add a new team to the system.

### Use Case:
Team data is essential for race and championship context. This supports organization and driver-team associations.

## Summary

This application acts as the backend for a racing management platform, providing endpoints to manage core entities (drivers, teams, races, championships) and analyze performance metrics. It's suitable for use in sports dashboards, admin portals, and analytics tools.

| HTTP Method | Full URL                                                    | Controller             | Description                         | Example Request Body |
|-------------|-------------------------------------------------------------|------------------------|-------------------------------------|----------------------|
| GET         | http://localhost:8080/api/races/{id}                        | RaceController         | Get race by ID                      | — |
| POST        | http://localhost:8080/api/races                             | RaceController         | Create a new race                   | `{"location":"Monaco","date":"2025-05-15","results":[{"driverId":1,"position":1},{"driverId":2,"position":2}]}` |
| DELETE      | http://localhost:8080/api/races/{id}                        | RaceController         | Delete race by ID                   | — |
| GET         | http://localhost:8080/api/races/winner                      | RaceController         | Get the winner of the latest race  | — |
| GET         | http://localhost:8080/api/analytics/drivers/{driverId}      | AnalyticsController    | Get driver statistics by driver ID | — |
| GET         | http://localhost:8080/api/analytics/teams/{teamId}          | AnalyticsController    | Get team statistics by team ID     | — |
| GET         | http://localhost:8080/api/analytics/drivers/ranking         | AnalyticsController    | Get driver ranking                 | — |
| GET         | http://localhost:8080/api/analytics/teams/ranking           | AnalyticsController    | Get team ranking                   | — |
| POST        | http://localhost:8080/api/analytics/refresh                 | AnalyticsController    | Refresh all statistics             | — |
| GET         | http://localhost:8080/api/championships                     | ChampionshipController | Get all championships              | — |
| GET         | http://localhost:8080/api/championships/{id}                | ChampionshipController | Get championship by ID             | — |
| GET         | http://localhost:8080/api/championships/{year}/standings    | ChampionshipController | Get championship standings         | — |
| POST        | http://localhost:8080/api/championships                     | ChampionshipController | Create a new championship          | `{"year":2025,"name":"Formula One 2025"}` |
| POST        | http://localhost:8080/api/championships/{id}/calculate      | ChampionshipController | Calculate championship standings   | — |
| DELETE      | http://localhost:8080/api/championships/{id}                | ChampionshipController | Delete championship by ID          | — |
| GET         | http://localhost:8080/api/drivers                           | DriverController       | Get all drivers                    | — |
| GET         | http://localhost:8080/api/drivers/{id}                      | DriverController       | Get driver by ID                   | — |
| GET         | http://localhost:8080/api/drivers/team/{teamId}             | DriverController       | Get drivers by team ID             | — |
| POST        | http://localhost:8080/api/drivers                           | DriverController       | Create a new driver                | `{"name":"Max Verstappen","teamId":1}` |
| DELETE      | http://localhost:8080/api/drivers/{id}                      | DriverController       | Delete driver by ID                | — |
| GET         | http://localhost:8080/api/teams                             | TeamController         | Get all teams                      | — |
| GET         | http://localhost:8080/api/teams/{id}                        | TeamController         | Get team by ID                     | — |
| POST        | http://localhost:8080/api/teams                             | TeamController         | Create a new team                  | `{"name":"Red Bull Racing"}` |
| DELETE      | http://localhost:8080/api/teams/{id}                        | TeamController         | Delete team by ID                  | — |