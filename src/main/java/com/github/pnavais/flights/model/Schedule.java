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

import java.time.Month;
import java.util.List;

/**
 * Represents the list of flights from an origin airport
 * to a destination airport for a given month.
 */
public class Schedule {

    /** The year */
    private int year;

    /** The month of the year */
    private Month month;

    /** The list of flight by day */
    private List<FlightsDay> days;

    /**
     * Retrieves the year
     *
     * @return the year
     */
    public int getYear() {
        return year;
    }

    /**
     * Sets the year
     *
     * @param year the year
     */
    public void setYear(int year) {
        this.year = year;
    }

    /**
     * Retrieves the month of the year
     *
     * @return the month of the year
     */
    public Month getMonth() {
        return month;
    }

    /**
     * Sets the month of the year
     *
     * @param month the month of the year
     */
    public void setMonth(Month month) {
        this.month = month;
    }

    /**
     * Retrieves the list of scheduled flights
     * by day in the current month.
     *
     * @return the scheduled flights by day
     */
    public List<FlightsDay> getDays() {
        return days;
    }

    /**
     * Sets the list of scheduled flights
     * by day in the current month
     *
     * @param days the scheduled flights by day
     */
    public void setDays(List<FlightsDay> days) {
        this.days = days;
    }

    @Override
    public String toString() {
        return "Schedule{" +
                "year=" + year +
                ", month=" + month +
                ", days=" + days +
                '}';
    }
}
