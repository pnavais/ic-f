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
package com.github.pnavais.flights.connections;

import com.github.pnavais.flights.api.ConnectionAnalyzer;
import com.github.pnavais.flights.api.RoutesIndex;
import com.github.pnavais.flights.api.RoutesProvider;
import com.github.pnavais.flights.api.SchedulesProvider;
import com.github.pnavais.flights.model.FlightConnection;
import com.github.pnavais.flights.model.FlightPath;
import com.github.pnavais.flights.model.Leg;
import com.github.pnavais.flights.model.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * Flight connection analyzer for routes with a maximum of one intermediate stop.
 */
@Component
public class FlightsConnectionAnalyzer implements ConnectionAnalyzer<FlightPath, String, FlightConnection> {

    /**
     * The routes provider
     */
    @Autowired
    private RoutesProvider<RoutesIndex<FlightPath, String>, FlightPath, String> routesProvider;

    /**
     * The schedules provider
     */
    @Autowired
    private SchedulesProvider<String> schedulesProvider;

    /**
     * Retrieves valid connections for the given route
     * and specified date time constraints.
     *
     * @param routes            the routes to analyze
     * @param departureDateTime the departure date time
     * @param arrivalDateTime   the arrival date time
     * @return the list of valid connections
     */
    @Override
    public List<FlightConnection> findValidConnections(List<Route> routes, LocalDateTime departureDateTime, LocalDateTime
            arrivalDateTime) {
        List<FlightConnection> connections = new ArrayList<>();

        if (!routes.isEmpty()) {

            // Retrieve initial legs
            List<Leg<String>> initialLegs = schedulesProvider.getFlights(routes.get(0).getAirportFrom(), routes.get(0).getAirportTo(),
                    departureDateTime,
                    arrivalDateTime);

            // Obtain all direct connections
            if (routes.size() == 1) {
                processDirectRoute(initialLegs, connections);
            } else if (routes.size() == 2) {
                // Get all intermediate flights starting from minimum departure + 2h
                processIntermediateRoute(routes, arrivalDateTime, initialLegs, connections);
            }
        }

        return connections;
    }

    /**
     * Process routes with no interconnected flights
     *  @param initialLegs the initial valid legs
     * @param connections the connections target
     */
    private void processDirectRoute(List<Leg<String>> initialLegs, List<FlightConnection> connections) {
        // Create a new connection for each direct leg
        initialLegs.forEach(l -> {
            FlightConnection connection = new FlightConnection();
            connection.setStops(0);
            connection.setLegs(Arrays.asList(l));
            connections.add(connection);
        });
    }

    /**
     * Process routes with one interconnected flight (maximum 1 stop).
     *
     * @param routes the interconnected route
     * @param arrivalDateTime the arrival time
     * @param initialLegs initial valid legs
     * @param connections the connections target
     */
    private void processIntermediateRoute(List<Route> routes, LocalDateTime arrivalDateTime, List<Leg<String>> initialLegs, List<FlightConnection> connections) {
        initialLegs.stream().min(Comparator.comparing(Leg::getDepartureDateTime))
                .ifPresent(min -> {

                    LocalDateTime nextMinDeparture = min.getDepartureDateTime().plusHours(2);

                    // Find final route step valid legs
                    List<Leg<String>> finalLegs = schedulesProvider.getFlights(routes.get(1).getAirportFrom(),
                            routes.get(1).getAirportTo(), nextMinDeparture, arrivalDateTime);

                    // For each initial leg, combine with compatible final leg
                    initialLegs.forEach(i -> finalLegs.stream()
                            .filter(f -> (i.getArrivalDateTime().plusHours(2).compareTo(f.getDepartureDateTime()) <= 0))
                            .forEach(f -> {
                                FlightConnection connection = new FlightConnection();
                                connection.setStops(1);
                                List<Leg<String>> legs = new ArrayList<>();
                                legs.add(i);
                                legs.add(f);
                                connection.setLegs(legs);
                                connections.add(connection);
                            }));
                });
    }

    /**
     * Retrieves all valid flight paths between the origin and destination
     * given a maximum number of transitions.
     *
     * @param origin         the origin
     * @param destination    the destination
     * @param maxTransitions the maximum number of transitions
     * @return the list of valid flight paths
     */
    @Override
    public List<FlightPath> findPaths(String origin, String destination, int maxTransitions) {
        return routesProvider.findPaths(origin, destination, maxTransitions);
    }

}
