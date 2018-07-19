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

import java.util.List;

/**
 * An index to store the routes between two
 * destinations.
 *
 * @param <K> the type of paths of the route
 * @param <L> the path actual content
 */
public interface RoutesIndex<K extends Path<L>, L> {

    /**
     * Adds a new route between two adjacent nodes
     * to the route index.
     *
     * @param origin the origin of the route
     * @param destination the destination of the route
     */
    void add(L origin, L destination);

    /**
     * Find all paths connecting origin with destination
     * with the given maximum number of intermediate
     * nodes.
     *
     * @param origin the origin of the toute
     * @param destination the destination
     * @param maxStop the maxim number of intermediate nodes
     *
     * @return the list of paths
     */
    List<K> findRoutes(L origin, L destination, int maxStop);
}
