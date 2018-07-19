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

import com.github.pnavais.flights.client.ProxyConfig;
import feign.Client;
import okhttp3.OkHttpClient;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Common methods and configuration for abstract service providers
 */
public abstract class AbstractServiceProvider {

    /** The proxy for client requests */
    @Autowired
    private ProxyConfig proxyBuilder;

    /**
     * Retrieve the HTTP client to be used
     * in the REST client
     *
     * @return the http client
     */
    protected Client buildHttpClient() {
        OkHttpClient httpClient = new OkHttpClient.Builder().proxy(proxyBuilder.getProxy()).build();
        return new feign.okhttp.OkHttpClient(httpClient);
    }
}
