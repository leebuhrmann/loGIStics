# S.N.O.W. - Strategic Notification & Operational Weatherwatch

## About The Project

This repository hosts the development for S.N.O.W. (Strategic Notification & Operational Weatherwatch), a comprehensive GIS microservice designed to deliver critical weather data alerts for US cities. The project is developed as part of CSCI 4970 (CS Capstone).

### Built With

- **Frontend**: React.js
- **GIS Microservice**: Java Backend with SpringBoot, managed with Gradle
- **Database**: PostGIS

## Getting Started

To get a local copy up and running follow these simple steps.
- As of right now, you must have access to the Virtual Machine hosting the Geoserver service.
- In the VM, start up the Geoserver using the `./startup.sh` configuration file.
- Launch the `snow` application using `npm run dev`
- Nagivate to localhost:3000 in a modern web browser

### Prerequisites

List all the prerequisites and how to install them.
- Access to the Virtual Machine hosting the Geoserver service.
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
