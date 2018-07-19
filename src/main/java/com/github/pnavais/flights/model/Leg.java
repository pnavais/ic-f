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

import java.time.LocalDateTime;

/**
 * Represents a travel between two Aiports
 * with departure and arrival date and times.
 */
public class Leg<K> {

    /** The departure airport */
    private K departureAirport;

    /** The arrival airport */
    private K arrivalAirport;

    /** The departure date and time */
    private LocalDateTime departureDateTime;

    /** The arrival date and time */
    private LocalDateTime arrivalDateTime;

    /**
     * Retrieves the departure airport
     *
     * @return the departure airport
     */
    public K getDepartureAirport() {
        return departureAirport;
    }

    /**
     * Sets the departure airport
     *
     * @param departureAirport the departure airport
     */
    public void setDepartureAirport(K departureAirport) {
        this.departureAirport = departureAirport;
    }

    /**
     * Retrieves the arrival airport
     *
     * @return the arrival airport
     */
    public K getArrivalAirport() {
        return arrivalAirport;
    }

    /**
     * Sets the arrival airport
     *
     * @param arrivalAirport the arrival airport
     */
    public void setArrivalAirport(K arrivalAirport) {
        this.arrivalAirport = arrivalAirport;
    }

    /**
     * Retrieves the departure date and time
     *
     * @return the departure date and time
     */
    public LocalDateTime getDepartureDateTime() {
        return departureDateTime;
    }

    /**
     * Sets the departure date and time
     *
     * @param departureDateTime the departure date and time
     */
    public void setDepartureDateTime(LocalDateTime departureDateTime) {
        this.departureDateTime = departureDateTime;
    }

    /**
     * Retrieves the arrival date and time
     *
     * @return the arrival date and time
     */
    public LocalDateTime getArrivalDateTime() {
        return arrivalDateTime;
    }

    /**
     * Sets the arrival date and time
     *
     * @param arrivalDateTime the arrival date and time
     */
    public void setArrivalDateTime(LocalDateTime arrivalDateTime) {
        this.arrivalDateTime = arrivalDateTime;
    }
}
