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
package com.github.pnavais.flights.api;

import com.github.pnavais.flights.model.Leg;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Provides the schedules between two destinations
 * for a given input (year and month)
 *
 * @param <K> the type of destination
 */
public interface SchedulesProvider<K> {

    /**
     * Retrieves the scheduled flights between an origin and destination
     * for a given time frame.
     *
     * @param origin the origin node
     * @param destination the destination node
     * @param departureDate the departure date
     * @param arrivalDate the arrival date
     * @return the scheduled flights
     */
    List<Leg<K>> getFlights(K origin, K destination, LocalDateTime departureDate, LocalDateTime arrivalDate);
}
