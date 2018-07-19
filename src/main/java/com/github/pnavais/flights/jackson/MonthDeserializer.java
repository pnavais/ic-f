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
package com.github.pnavais.flights.jackson;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.time.Month;

/**
 * A custom deserializer for Month objects
 */
public class MonthDeserializer extends StdDeserializer<Month> {

    /**
     * Default constructor
     */
    private MonthDeserializer() {
        this(null);
    }

    /**
     * Generic parameterized constructor
     * @param vc the class type
     */
    private MonthDeserializer(Class<?> vc) {
        super(vc);
    }


    /**
     * Retrieves the default instance
     *
     * @return the default deserializer
     */
    public static MonthDeserializer getDefault() {
        return MonthDeserializerHolder.instance;
    }


    /**
     * Lazy-instantiated singleton holder on-demand
     */
    private static class MonthDeserializerHolder {
        private static MonthDeserializer instance = new MonthDeserializer();
    }


    /**
     * Custom deserialization for Months
     *
     * @param p the JSON parser
     * @param ctxt the context
     * @return the deserialized schedule
     * @throws IOException on errors when dealing with incorrect I/O
     */
    @Override
    public Month deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        return Month.of(Integer.parseInt(p.getText().trim()));
    }


}
