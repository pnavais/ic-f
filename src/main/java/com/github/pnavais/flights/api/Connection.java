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

import java.util.List;

/**
 * A generic parameterized connection
 *
 * @param <T> the type of connections
 */
public interface Connection<T> {

    /**
     * Retrieves the number of
     * intermediate nodes in the connection
     *
     * @return the number of intermediate nodes in the connection
     */
    int getStops();

    /**
     * Retrieves the list of legs
     *
     * @return the list of legs
     */
    List<Leg<T>> getLegs();


}
