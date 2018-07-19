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

import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.github.pnavais.flights.api.SchedulesProvider;
import com.github.pnavais.flights.client.SchedulesClient;
import com.github.pnavais.flights.jackson.MonthDeserializer;
import com.github.pnavais.flights.model.Leg;
import com.github.pnavais.flights.model.Schedule;
import feign.Feign;
import feign.FeignException;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import feign.slf4j.Slf4jLogger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Retrieves the schedules between two airports for a given time frame
 */
@Component
public class AirportSchedulesProvider extends AbstractServiceProvider implements SchedulesProvider<String> {

    /** The schedules client */
    private SchedulesClient schedulesClient;

    /** The logger */
    private Logger logger = LoggerFactory.getLogger(AirportSchedulesProvider.class);

    /** The Schedule Service endpoint */
    @Value("${schedules.endpoint.url}")
    private String schedulesServiceEndpoint;

    /**
     * Performs initialization on bean creation.
     * Creates the REST client to consume
     * Schedules API and stores the services.
     *
     * @param event the spring context event
     */
    @EventListener
    public void onApplicationEvent(ContextRefreshedEvent event) {
        // Create the REST client
        this.schedulesClient = Feign.builder()
                .client(buildHttpClient())
                .encoder(new JacksonEncoder())
                .decoder(new JacksonDecoder(buildDecoderModule()))
                .logger(new Slf4jLogger(SchedulesClient.class))
                .logLevel(feign.Logger.Level.FULL)
                .target(SchedulesClient.class, schedulesServiceEndpoint);
    }

    /**
     * Creates the custom modules for the decoder
     *
     * @return the custom modules
     */
    private List<Module> buildDecoderModule() {
        SimpleModule extraModule = new SimpleModule();
        extraModule.addDeserializer(Month.class, MonthDeserializer.getDefault());
        return Arrays.asList(extraModule, new JavaTimeModule(), new Jdk8Module());
    }

    /**
     * Retrieves the flights between two airport for a given time frame.
     *
     * @param origin the origin node
     * @param destination the destination node
     * @param departureDate the departure date
     * @param arrivalDate the arrival date
     * @return the list of scheduled flights
     */
    @Override
    public List<Leg<String>> getFlights(String origin, String destination, LocalDateTime departureDate, LocalDateTime
            arrivalDate) {
        List<Leg<String>> flights = new ArrayList<>();

        try {

            logger.info("Obtaining flights between : {} -> {} from {} to {}", origin, destination, departureDate, arrivalDate);

            LocalDateTime dateToFetch = departureDate;
            do {
                // Retrieve flights for the target date
                Schedule scheduledFlights = schedulesClient.getScheduledFlights(origin, destination,
                        dateToFetch.getYear(), dateToFetch.getMonth().getValue());
                scheduledFlights.setYear(dateToFetch.getYear());

                // Filter legs in range
                LocalDateTime finalDateToFetch = dateToFetch;
                scheduledFlights.getDays().forEach(fd -> {
                    List<Leg<String>> legs = fd.getFlights().stream()
                            .filter(f -> {
                                LocalDate date = LocalDate.of(scheduledFlights.getYear(), scheduledFlights.getMonth(), fd.getDay());
                                LocalDateTime departureDateTime = LocalDateTime.of(date, f.getDepartureTime());
                                return (finalDateToFetch.compareTo(departureDateTime) <= 0) && (arrivalDate.compareTo
                                        (departureDateTime)>=0);
                            }).map(f -> {
                                // Translate to a valid leg
                                LocalDate date = LocalDate.of(scheduledFlights.getYear(), scheduledFlights.getMonth(), fd.getDay());
                                LocalDateTime departureDateTime = LocalDateTime.of(date, f.getDepartureTime());
                                LocalDateTime arrivalDateTime = LocalDateTime.of(date, f.getArrivalTime());

                                Leg<String> leg = new Leg<>();
                                leg.setDepartureAirport(origin);
                                leg.setArrivalAirport(destination);
                                leg.setDepartureDateTime(departureDateTime);
                                leg.setArrivalDateTime(arrivalDateTime);

                                return leg;
                            }).collect(Collectors.toList());

                    // Add matched legs
                    flights.addAll(legs);
                });

                // Fetch next month if necessary
                LocalDate localDate = dateToFetch.plusMonths(1).withDayOfMonth(1).toLocalDate();
                dateToFetch = LocalDateTime.of(localDate, LocalTime.MIN);
            } while (dateToFetch.compareTo(arrivalDate)<=0);

            logger.info("{} flights obtained between {} -> {} from {} to {}",
                    flights.size(),
                    origin, destination,
                    departureDate,
                    arrivalDate);

        } catch (FeignException e) {
            logger.error("Error retrieving schedules : {}", e.getLocalizedMessage());
        }

        return flights;
    }

}
