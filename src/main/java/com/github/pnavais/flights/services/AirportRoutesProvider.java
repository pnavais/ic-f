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
package com.github.pnavais.flights.services;

import com.github.pnavais.flights.api.RoutesIndex;
import com.github.pnavais.flights.api.RoutesProvider;
import com.github.pnavais.flights.client.RoutesClient;
import com.github.pnavais.flights.model.FlightPath;
import com.github.pnavais.flights.model.Route;
import feign.Feign;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import feign.slf4j.Slf4jLogger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * A dedicated class storing the services provided by the RoutesClient
 * and made it accessible externally.
 *
 * The information contained in the Routes can be refreshed manually.
 */
@Component
public class AirportRoutesProvider extends AbstractServiceProvider
        implements RoutesProvider<RoutesIndex<FlightPath,String>, FlightPath, String> {

    /** The logger instance */
    private static final Logger logger = LoggerFactory.getLogger(RoutesProvider.class);

    /** The services client */
    private RoutesClient routesClient;

    /** The routes storage */
    @Autowired
    private RoutesIndex<FlightPath, String> routes;

    /** The routes service endpoint */
    @Value("${routes.endpoint.url}")
    String routesServiceEndpoint;

    /**
     * Performs initialization on bean creation.
     * Creates the REST client to consume
     * Routes API and stores the services.
     *
     * @param event the spring context event
     */
    @EventListener
    public void onApplicationEvent(ContextRefreshedEvent event) {
        // Create the REST client
        this.routesClient = Feign.builder()
                .client(buildHttpClient())
                .encoder(new JacksonEncoder())
                .decoder(new JacksonDecoder())
                .logger(new Slf4jLogger(RoutesClient.class))
                .logLevel(feign.Logger.Level.FULL)
                .target(RoutesClient.class, routesServiceEndpoint);

        logger.info("Routes initialization : fetching routes");

        // Retrieve routes
        fetch();
    }

    /**
     * Updates the services by accessing the external
     * controller.
     */
    @Override
    public RoutesIndex<FlightPath, String> fetch() {
        List<Route> routesList = this.routesClient.findAll();
        routesList.forEach(route -> this.routes.add(route.getAirportFrom(), route.getAirportTo()));
        return this.routes;
    }

    @Override
    public List<FlightPath> findPaths(String origin, String destination, int maxStops) {
        return this.routes.findRoutes(origin, destination, maxStops);
    }

    /**
     * Obtain the fetched services.
     *
     * @return the fetched services
     */
    public RoutesIndex<FlightPath, String> getRoutes() {
        return routes;
    }

}
