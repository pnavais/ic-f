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

import com.github.pnavais.flights.services.AbstractServiceProvider;
import com.github.pnavais.flights.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.net.InetSocketAddress;
import java.net.Proxy;

/**
 * Provides Proxy configuration
 */
@Component
public class ProxyConfig {

    private Logger logger = LoggerFactory.getLogger(AbstractServiceProvider.class);

    @Value("${http.proxy.enable:false}")
    private Boolean useProxy;

    @Value("${http.proxy.host:}")
    private String httpProxyHost;

    @Value("${http.proxy.port:0}")
    private int httpProxyPort;

    private Proxy proxy;

    /**
     * Creates the proxy after bean configuration.
     *
     * @param event the context event
     */
    @EventListener
    public void onApplicationEvent(ContextRefreshedEvent event) {
        this.proxy = Proxy.NO_PROXY;

        if (useProxy && !StringUtils.empty(httpProxyHost) && httpProxyPort>=0) {
            logger.debug("Using HTTP proxy => {}:{}", httpProxyHost, httpProxyPort);
            proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(httpProxyHost, httpProxyPort));
        }
    }

    /**
     * Retrieves the configured proxy
     *
     * @return the configured proxy
     */
    public Proxy getProxy() {
        return proxy;
    }
}
