/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */


package org.apache.rocketmq.exporter;

import io.prometheus.client.CollectorRegistry;
import org.apache.rocketmq.common.exporter.ExporterConfig;
import org.apache.rocketmq.exporter.collector.BrokerMetricCollector;
import org.apache.rocketmq.exporter.collector.NamesrvMetricCollector;
import org.apache.rocketmq.exporter.server.HTTPServer;
import org.apache.rocketmq.logging.InternalLogger;

import java.io.IOException;

public class ExporterController {
    private static InternalLogger log;
    private final ExporterConfig config;
    private HTTPServer server;
    private BrokerMetricCollector brokerCollector = null;
    private NamesrvMetricCollector namesrvCollector = null;
    private final boolean isNamesrv;

    public ExporterController(ExporterConfig exporterConfig, boolean isNamesrv, InternalLogger log) {
        this.config = exporterConfig;
        this.isNamesrv = isNamesrv;
        ExporterController.log = log;
    }

    public void initialize() throws IOException {
        CollectorRegistry registry = new CollectorRegistry();

        if (this.isNamesrv) {
            this.namesrvCollector = new NamesrvMetricCollector(registry);
        } else {
            this.brokerCollector = new BrokerMetricCollector(registry);
        }

        this.server = new HTTPServer.Builder()
                .withHostname(config.getBrokerIP3())
                .withPort(config.getMetricPort())
                .withRegistry(registry)
                .build(); //  init and start the server

        log.info("exporter start at {}:{} finished", config.getBrokerIP3(), config.getMetricPort());
    }

    public void start() {
    }

    public void shutdown() {
        this.server.close();
    }

    public BrokerMetricCollector getBrokerCollector() {
        return brokerCollector;
    }

    public NamesrvMetricCollector getNamesrvCollector() {
        return namesrvCollector;
    }
}
