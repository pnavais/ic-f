/*
 * Copyright 2018 Pablo Navais
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.github.pnavais.flights.controller;

import com.github.pnavais.flights.api.ConnectionAnalyzer;
import com.github.pnavais.flights.model.FlightConnection;
import com.github.pnavais.flights.model.FlightPath;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/interconnections")
public class InterconnectionsController {

    /** Default number of stops */
    private static final int DEFAULT_MAX_ROUTE_SIZE = 2;

    /** The flight connection Analyzer */
    @Autowired
    private ConnectionAnalyzer<FlightPath, String, FlightConnection> connectionAnalyzer;

    /** The logger instance */
    private static final Logger logger = LoggerFactory.getLogger(InterconnectionsController.class);

    /**
     * Provides a list of flights departing from a given departure airport not earlier
     * than the specified departure datetime and arriving to a given arrival airport not later than the
     * specified arrival datetime.
     * <p>
     * The list consists of:
     * all direct flights if available (for example: DUB - WRO)
     * m
     * all interconnected flights with a maximum of one stop if available
     * (for example: DUB - STN - WRO)
     * </p>
     * <p>
     * For interconnected flights the difference between the arrival and the next departure should be 2h
     * or greater
     * </p>
     * @param srcAirport the origin airport
     * @param targetAirport the destination airport
     * @param departure the departure date time
     * @param arrival the arrival date time
     * @return the list of flights matching the criteria
     */
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<FlightConnection> getPaths(@RequestParam("departure") String srcAirport,
                                     @RequestParam("arrival")String targetAirport,
                                     @RequestParam("departureDateTime")String departure,
                                     @RequestParam("arrivalDateTime")String arrival) {

        LocalDateTime departureDateTime = LocalDateTime.parse(departure);
        LocalDateTime arrivalDateTime = LocalDateTime.parse(arrival);

        logger.info("Departure      =>  {}", srcAirport);
        logger.info("Arrival        =>  {}", targetAirport);
        logger.info("Departure Time =>  {}", departureDateTime);
        logger.info("Arrival Time   =>  {}", arrivalDateTime);


        List<FlightConnection> connections = new ArrayList<>();

        // Check dates are consistent
        if (arrivalDateTime.compareTo(departureDateTime)>=0) {

            // Find all available paths
            List<FlightPath> pathList = connectionAnalyzer.findPaths(srcAirport, targetAirport, DEFAULT_MAX_ROUTE_SIZE);

            logger.info("Found {} paths between {} and {}", pathList.size(), srcAirport, targetAirport);

            // Find all valid connection flights for each route
            pathList.forEach(flightPath -> {

                logger.info("Analyzing path : [ {} ]", flightPath);

                // Find valid legs for the route
                connections.addAll(connectionAnalyzer.findValidConnections(flightPath.toRoutes(), departureDateTime,
                        arrivalDateTime));

            });
        }

        return connections;
    }


}
