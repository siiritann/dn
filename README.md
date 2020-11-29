# Requirements

1. Create a REST API in Spring boot that consists of the following endpoints:
    1) possibility to save trackable cities into DB
    2) possibility to save trackable cities' weather into DB
        * Required fields: city name, temperature, wind speed, humidity, time
 
2. This endpoint must get data from some public weather api, e.g. https://openweathermap.org/api
    1) add functionality that polls data for tracked cities in every 15min

3. Create a frontend (vue / angular / ... ) that uses your REST API & allows user to 
    1) get list of my trackable cities
    2) add city
    3) remove city
    4) see weather data for one of my cities
    5) see weather data for all of my cities

4. Add README file that explains the steps how to start & use the program
  