# interconnecting-flights
Ryanair - Task 2 - Java/Spring - Interconnecting Flights

## Overview
This is my implementation of the Interconnecting Flights problem. It has been developed in Java 8, as a Spring Boot application. 
The application can be packaged by Maven as a WAR (`mvn clean package`) and expected to be deployed on Tomcat (v9.0.10 recommended).

Declarative REST clients (using Feign) have been developed to consume Ryanair microservices API while providing a simple Spring MVC REST controller to expose the endpoint API required which can be reached at : 

http://localhost:8080/interconnecting-flights/interconnections

## Solution
In order to solve the Interconnecting Flights problem it has been divided in two steps.
In the first step, during bootstrap, all possible routes between airports are retrieved at once and a directed graph is built. Using Djikstra algorithm, all paths of the desired size are retrieved (in this case a maximum of 1 interconnected flight i.e. 3 nodes). In order to speed up development the excellent [JgraphT library](https://jgrapht.org/) has been used. 
In the second step, for each possible path, all compatible flights are retrieved. In case of direct flights (paths of 2 nodes) , directly the departure/arrival date and times are checked.
In case of interconnected flights, an additional check is performed taking into account a minimum delay of 2h between previous arrival and next departure.

## Known Limitations
Obviously, the implementation is not generic enough. It is only intended to be used for paths with 1 maximum stop. In case arbitrary sizes were required it is recommended to replace the Connections Analyzer interface implementation and use some more advanced reasoning logic. This can be done, for example, using an inference engine [JBoss Drools](https://www.drools.org/) which would provide a robust and fast decision tree (RETE) with external business logic execution (rules). This is clearly a way more performant solution over simpler cartesian product handcrafted implementations when trying to obtain all possible permutations (interconnected flights). 

## Usage
The interconnections service can be accesed using the following URL pattern : 

http://localhost:8080/interconnecting-flights/interconnections?departure=DEPARTURE_AIRPORT&arrival=ARRIVAL_AIRPORT&departureDateTime=DEPARTURE_DATE&arrivalDateTime=ARRIVAL_DATE

where DEPARTURE_AIRPORT and ARRIVAL_AIPORT uses IATA codes
and date times are expressed in the following format : `YYYY-MM-DDTHH:MM:SS`

The following URL should provide a JSON list containing the list of flights departing from a given departure airport not earlier
than the specified departure datetime and arriving to a given arrival airport not later than the
specified arrival datetime. The list should consist of:
* all direct flights if available (for example: DUB - WRO)
* all interconnected flights with a maximum of one stop if available (for example: DUB - STN - WRO)
* for interconnected flights the difference between the arrival and the next departure should be 2h or greater

## Example
http://localhost:8080/interconnecting-flights/interconnections?departure=DUB&arrival=WRO&departureDateTime=2018-07-19T16:00&arrivalDateTime=2018-07-20T23:50

The resulting list should be of the following form:

![alt text](results-example.png "Example results")





