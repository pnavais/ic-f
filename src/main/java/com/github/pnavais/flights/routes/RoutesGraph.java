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
package com.github.pnavais.flights.routes;

import com.github.pnavais.flights.api.RoutesIndex;
import com.github.pnavais.flights.model.FlightPath;
import org.jgrapht.Graph;
import org.jgrapht.alg.shortestpath.AllDirectedPaths;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Contains the connections between incoming/outgoing airports
 */
@Component
public class RoutesGraph implements RoutesIndex<FlightPath, String> {

    /** The graph of nodes */
    private final Graph<String, DefaultEdge> graph;

    /**
     * Default constructor
     */
    public RoutesGraph() {
        this.graph = new DefaultDirectedGraph<>(DefaultEdge.class);
    }

    /**
     * Adds to the Map a connection between the given
     * departure and arrival airports in IATA codes
     * @param srcAirport the departure airport
     * @param targetAirport the arrival airport
     */
    @Override
    public void add(String srcAirport, String targetAirport) {
        this.graph.addVertex(srcAirport);
        this.graph.addVertex(targetAirport);
        this.graph.addEdge(srcAirport, targetAirport);
    }

    /**
     * Find all services in the graph between the departure airport to
     * the destination airport with a maximum number of interconnections.
     *
     * @param srcAirport the departure airport
     * @param targetAirport the target airport
     * @param maxStop the maximum number of stops
     *
     * @return the possible services
     */
    @Override
    public List<FlightPath> findRoutes(String srcAirport, String targetAirport, int maxStop) {

        // Use Dijkstra algorithm to find shortest path matching the restriction
        AllDirectedPaths<String, DefaultEdge> allDirectedPaths = new AllDirectedPaths<>(this.graph);

        return allDirectedPaths.getAllPaths(srcAirport, targetAirport, false, maxStop)
                .stream()
                .map(path -> FlightPath.from(path.getVertexList()))
                .collect(Collectors.toList());
    }

}
