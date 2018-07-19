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

import java.time.LocalTime;

/**
 * Represents an information of a scheduled flight.
 */
public class FlightInfo {

    /** The flight number */
    private int number;

    /** The departure time */
    private LocalTime departureTime;

    /** The arrival time */
    private LocalTime arrivalTime;

    /**
     * Retrieves the flight number
     *
     * @return the flight number
     */
    public int getNumber() {
        return number;
    }

    /**
     * Sets the flight number
     *
     * @param number the flight number
     */
    public void setNumber(int number) {
        this.number = number;
    }

    /**
     * Retrieves the departure time
     *
     * @return the departure time
     */
    public LocalTime getDepartureTime() {
        return departureTime;
    }

    /**
     * Sets the departure time
     *
     * @param departureTime the departure time
     */
    public void setDepartureTime(LocalTime departureTime) {
        this.departureTime = departureTime;
    }

    /**
     * Retrieves the arrival time
     *
     * @return the arrival time
     */
    public LocalTime getArrivalTime() {
        return arrivalTime;
    }

    /**
     * Sets the arrival time
     *
     * @param arrivalTime the arrival time
     */
    public void setArrivalTime(LocalTime arrivalTime) {
        this.arrivalTime = arrivalTime;
    }


    @Override
    public String toString() {
        return "FlightInfo{" +
                "number=" + number +
                ", departureTime=" + departureTime +
                ", arrivalTime=" + arrivalTime +
                '}';
    }
}
