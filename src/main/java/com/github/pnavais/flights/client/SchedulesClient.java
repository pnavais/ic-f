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
package com.github.pnavais.flights.client;

import com.github.pnavais.flights.model.Schedule;
import feign.Param;
import feign.RequestLine;

/**
 * Consumes the Schedules API:
 * https://api.ryanair.com/timetable/3/schedules/{departure}/{arrival}/years/{year}/months/{month}
 */
public interface SchedulesClient {

    /**
     * Retrieves the list of available flights for a given departure airport IATA code, an arrival airport
     * IATA code, a year and a month.
     *
     * @param srcAirport the origin airport
     * @param targetAirport the destination airport
     * @param year the year
     * @param month the month
     *
     * @return the list of available flights
     */
    @RequestLine("GET /{departure}/{arrival}/years/{year}/months/{month}")
    Schedule getScheduledFlights(@Param("departure")String srcAirport,
                                 @Param("arrival")String targetAirport,
                                 @Param("year")int year,
                                 @Param("month")int month);


}
