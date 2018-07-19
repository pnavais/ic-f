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

import com.github.pnavais.flights.api.Connection;

import java.util.List;

/**
 * Represents an assembly of legs describing
 * the interconnected flights between a source
 * and origin airports.
 */
public class FlightConnection implements Connection<String> {

    /** The number of legs */
    private int stops;

    /** The list of legs */
    private List<Leg<String>> legs;

    /**
     * Retrieves the number of stops
     *
     * @return the number of stops
     */
    @Override
    public int getStops() {
        return stops;
    }

    /**
     * Sets the number of stops
     * @param stops the number of stops
     */
    public void setStops(int stops) {
        this.stops = stops;
    }

    /**
     * Retrieves the list of legs
     *
     * @return the list of legs
     */
    @Override
    public List<Leg<String>> getLegs() {
        return legs;
    }

    /**
     * Sets the list of legs
     *
     * @param legs the list of legs
     */
    public void setLegs(List<Leg<String>> legs) {
        this.legs = legs;
    }
}
