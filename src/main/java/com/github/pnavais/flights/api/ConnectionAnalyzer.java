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

import com.github.pnavais.flights.model.Route;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Defines the methods needed to provide
 * route connections analysis.
 */
public interface ConnectionAnalyzer<T extends Path<K>, K, C extends Connection<K>> {

    /**
     * Retrieves the valid paths between a given origin
     * and destination with a maximum number of intermediate transitions.
     * <p><p>
     * For example :<pre>
     *
     * A -> C      => 1 transition
     * A -> B -> C => 2 transitions
     * </pre>
     * </p></p>
     *
     * @param origin         the origin
     * @param destination    the destination
     * @param maxTransitions the maximum number of transitions
     * @return the list of valid paths
     */
    List<T> findPaths(K origin, K destination, int maxTransitions);

    /**
     * Find all valid connections for the given route matching
     * the specified criteria.
     *
     * @param routes            the routes to analyze
     * @param departureDateTime the departure time
     * @param arrivalDateTime   the arrival time
     * @return the valid connections
     */
    List<C> findValidConnections(List<Route> routes, LocalDateTime departureDateTime,
                                 LocalDateTime arrivalDateTime);
}