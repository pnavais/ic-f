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
package com.github.pnavais.flights.model;

import com.github.pnavais.flights.api.Path;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Contains a complete path between flights
 */
public class FlightPath implements Path<String> {

    /** List of airports */
    private List<String> airports;

    /**
     * Default constructor
     */
    public FlightPath() {
        this.airports = new ArrayList<>();
    }

    /**
     * Constructor with a predefined
     * list of airports.
     *
     * @param airports the airports
     */
    public FlightPath(List<String> airports) {
        this.airports = airports;
    }

    /**
     * Static factory constructor
     *
     * @param airports the list of airports.
     * @return the FlightPath instance
     */
    public static FlightPath from(List<String> airports) {
        return new FlightPath(airports);
    }

    /**
     * Converts flight paths to routes
     *
     * @return list of routes
     */
    public List<Route> toRoutes() {
        List<Route> routes = new ArrayList<>();

        for (int i = 0; i <airports.size()-1 ; i++) {
            routes.add(new Route(airports.get(i), airports.get(i+1)));
        }

        return routes;
    }

    /**
     * Adds a new airport to the flight
     * path.
     *
     * @param airport the airport name
     */
    @Override
    public void add(String airport) {
        this.airports.add(airport);
    }

    /**
     * The size of the flight path.
     *
     * @return the size of the flight path
     */
    @Override
    public int size() {
        return airports.size();
    }

    @Override
    public String toString() {
        return airports.stream().collect(Collectors.joining(" -> "));
    }
}
