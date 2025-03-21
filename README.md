# Weather Forecast Service

## Overview


The Weather Forecast Service is a Java-based Spring Boot application that retrieves and displays weather forecast data based on user-provided addresses. It provides current temperature information, high/low temperatures, and extended forecasts. The application also caches forecast details for efficient retrieval, reducing redundant API calls.

This service integrates with the [Weather API](https://www.weatherapi.com/) to fetch real-time weather data. The Weather API provides accurate and detailed weather information, including current conditions, forecasts, and location-based weather data.

## Features

- Accepts an address as input to retrieve weather forecast data.
- Displays current temperature, with options for high/low temperatures and extended forecasts.
- Caches forecast data for 30 minutes based on zip codes to improve performance.
- Indicates when results are retrieved from the cache.


## Project Structure

```
weather-forecast-service
├── src
│   ├── main
│   │   ├── java
│   │   │   └── com
│   │   │       └── example
│   │   │           └── weather
│   │   │               ├── WeatherApplication.java      // Project Main
|   |   |               |   
|   |   |               └── aspect                       // Custom Annotation Support
|   |   |               |   └── CacheCounter.java
|   |   |               |   └── CacheResultAspect.java
|   |   |               |  
|   |   |               └── config                       // Startup Config (ie Cache Config)
|   |   |               |   └── CacheConfig.java
|   |   |               |   └── CacheConfigProperties.java
|   |   |               |  
│   │   │               ├── controller                   // Web Layer + Http Error Response Handler
│   │   │               │   └── WeatherController.java
│   │   │               │   └── ResponseExceptionHanderl.java
|   |   |               |  
│   │   │               ├── provider                     // Business Logic Layer
│   │   │               │   └── ForecastProvider.java
|   |   |               |  
│   │   │               ├── model
│   │   │               │   ├── Forecast.java
│   │   │               │   ├── ForecastDayInfo.java
│   │   │               │   └── CacheResult.java
|   |   |               |  
│   │   │               ├── util                       // Helpers
│   │   │               │   └── ForecastInputValidatorUtil.java
|   |   |               |  
│   │   │               └── service                   // External Service Layers
|   |   |                   └── ExternalService
│   │   │                   └── weatherapi
│   │   │                       ├── ForecastResponseTransformer.java
│   │   │                       ├── WeatherServiceWrapper.java
|   |   |                       └── WeatherService.java
│   │   │                       └── payload
│   │   │                           ├── ForecastResponse.java
│   │   │                           ├── Location.java
│   │   │                           ├── WeatherInfo.java
│   │   │                           └── ForecastDay.java
│   │   └── resources
│   │       ├── application.properties
│   │       └── cache-config.xml
│   └── test
│       └── java
│           └── com
│               └── example
│                   └── weather
│                       ├── WeatherControllerTest.java
│                       ├── WeatherServiceTest.java
│                       └── ForecastProviderTest.java
├── pom.xml
└── README.md
```

## Setup Instructions (WITH DOCKER)
**Prerequisites:**
1. Install [Docker Desktop](https://docs.docker.com/desktop/)
2. Ensure Docker is running
```
docker ps
```   

### Build the Docker Image ###
```
docker build -t weather-forecast-service .
```

### Run Docker ###
```
docker run -p 8080:8080 weather-forecast-service
```

## Setup Instructions (WITHOUT DOCKER)
**Prerequisites:**
1. Install Java 17 is Required. [See SDKMan](https://sdkman.io/usage/)
2. Install [Mvn](https://maven.apache.org/install.html)
3. Obtain API Key from [Weather API](https://www.weatherapi.com/)  (or leave **weatherapi.key** config property as blank to use mock data)
4. **Clone the repository:**

```
git clone git@github.com:sarunya-wa/weather-forecast-service.git
cd weather-forecast-service
```

### Build ###
```
mvn clean install
```

### Run Application ###
```
mvn spring-boot:run
```

### Access the API ###
The application will be available at `http://localhost:8080`. You can send requests to the weather forecast endpoint with the desired address.
```
GET http://localhost:8080/forecast?q=98665
```

### Test ###
```
mvn test
```

