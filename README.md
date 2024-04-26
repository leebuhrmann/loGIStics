# S.N.O.W. - Strategic Notification & Operational Weatherwatch

## About The Project

This repository hosts the development for S.N.O.W. (Strategic Notification & Operational Weatherwatch), a comprehensive GIS microservice designed to deliver critical weather data alerts for US cities. The project is developed as part of CSCI 4970 (CS Capstone).

### Built With

- **Frontend**: React.js
- **GIS Microservice**: Java Backend with SpringBoot, managed with Gradle
- **Database**: PostGIS

## Getting Started

To get a local copy up and running follow these simple steps.
- As of right now, you must have access to the Virtual Machine hosting the PostGIS database
- Launch the `snow` application using `npm run dev`
- In the snow-api application run the SnowApiApplication.java to launch the backend
- Nagivate to localhost:3000 in a modern web browser

### Prerequisites

List all the prerequisites and how to install them.
- Access to the Virtual Machine hosting the PostGIS database
- Install the latest npm inside your local repository of the `snow` application

## Release Notes

### Milestone #1

- Plus icon allows the user to draw a polygon, visual only, does not send data anywhere
- Side panel (currently all components are nonfunctional)
  - Placeholder data to demonstrate what the user might see, not live data from the server
  - Tab that allows the user to view boundaries or alerts, this is scrollable
  - Search bar
  - Subscriptions check box
- Makes a WMS API call to the Geoserver and recieves a rendered map layer

### Milestone #2

- The user is able to create and edit boundaries on the map
- Created a form for boundary creation, the boundary editing form has not been implemented yet
- We are currently adjusting the way in which we will send boundary details from the frontend to the backend, no implementation yet
- Added stylizing to the headers
- Established a Java Spring Boot environment
- Created the relational database. We are currently revising our database model.
- Created mappers for basic CRUD operations of boundaries 
- Began building out needed API calls 
- Created scheduled API calls to NWS gathering weather alert data and store that data into the database

### Milestone #3

- Have implemented Geojson deserializer and are able to insert geospatial data into the database
- We have not implemented Kafka as originally planned, and has been reclassified as a stretch goal
- Can now receive weather alerts in real time and store UGC zone data
- Have implemented WebSocket to facilitate real time alerts
- When the user selects a boundary to edit, the side panel that corresponds to editing is visible
- We do not have functionality to view and subscribe to boundaries
- We do have have API calls implemented in the frontend

### Milestone #4

- Have implemented MultiPolygon Deserilializer and Serializer to allow BoundaryController endpoints to manipulate Geospatial data
- Have implemented functionality that maintains accurate entries on the ugc_boundary table
- Have implemented functionality that inserts entries into the ugc_alert table
- Boundary name, description, and polygon coordinates can be posted to the database
- Implemented Recoil state management
