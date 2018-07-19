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

/**
 * Models the information contained by a route
 * between two different Airports.
 */
public class Route {

    /** The departure Airport IATA code */
    private String airportFrom;

    /** The arrival Airport IATA code */
    private String airportTo;

    /** Controls whether the route is new */
    private boolean newRoute;

    /** Controls whether the route is seasonal */
    private boolean seasonalRoute;

    /**
     * Default constructor
     */
    public Route() {}

    /**
     * Constructor with two airports
     *
     * @param srcAirport the origin airport
     * @param targetAirport the destination airport
     */
    public Route(String srcAirport, String targetAirport) {
        this.airportFrom = srcAirport;
        this.airportTo = targetAirport;
    }

    /**
     * Retrieves the departure Airport
     *
     * @return the departure airport
     */
    public String getAirportFrom() {
        return airportFrom;
    }

    /**
     * Sets the departure Airport
     */
    public void setAirportFrom(String airportFrom) {
        this.airportFrom = airportFrom;
    }

    /**
     * Retrieves the departure Airport
     *
     * @return the departure airport
     */
    public String getAirportTo() {
        return airportTo;
    }

    /**
     * Sets the destination Airport
     */
    public void setAirportTo(String airportTo) {
        this.airportTo = airportTo;
    }

    /**
     * Checks whether the route is
     * new or not
     *
     * @return true if new, false otherwise
     */
    public boolean isNewRoute() {
        return newRoute;
    }

    /**
     * Sets the flag controlling
     * whether the route is new or not.
     */
    public void setNewRoute(boolean newRoute) {
        this.newRoute = newRoute;
    }

    /**
     * Checks whether the route is
     * seasonal or not
     *
     * @return true if seasonal, false otherwise
     */
    public boolean isSeasonalRoute() {
        return seasonalRoute;
    }

    /**
     * Sets the flag controlling
     * whether the route is seasonal or not.
     */
    public void setSeasonalRoute(boolean seasonalRoute) {
        this.seasonalRoute = seasonalRoute;
    }

    @Override
    public String toString() {
        return "Route{" +
                "airportFrom='" + airportFrom + '\'' +
                ", airportTo='" + airportTo + '\'' +
                ", newRoute=" + newRoute +
                ", seasonalRoute=" + seasonalRoute +
                '}';
    }
}
