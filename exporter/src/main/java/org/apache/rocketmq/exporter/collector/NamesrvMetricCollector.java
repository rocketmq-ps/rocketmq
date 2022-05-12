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
package org.apache.rocketmq.exporter.collector;

import io.prometheus.client.CollectorRegistry;
import io.prometheus.client.Counter;
import org.apache.rocketmq.common.constant.LoggerName;
import org.apache.rocketmq.exporter.common.MQRoleName;
import org.apache.rocketmq.exporter.common.MetricConstant;
import org.apache.rocketmq.logging.InternalLogger;
import org.apache.rocketmq.logging.InternalLoggerFactory;

import java.util.Locale;

public class NamesrvMetricCollector {
    private static final InternalLogger log = InternalLoggerFactory.getLogger(LoggerName.EXPORTER_LOGGER_NAME);
    private Counter reqResCodeCounter;

    public NamesrvMetricCollector(CollectorRegistry registry) {
        this.initMetrics(registry);
    }

    private void initMetrics(CollectorRegistry registry) {
        reqResCodeCounter = Counter.build()
                .namespace(MetricConstant.NAMESPACE)
                .subsystem(MQRoleName.NAMESRV.name().toLowerCase(Locale.ROOT))
                .name("req_res_code_counter")
                .labelNames("cluster", "nodeName", "ipAndPort", "req_code", "res_code")
                .help("Request and Response Code Counter")
                .create()
                .register(registry);

    }

    public void addReqResCodeCounter(String clusterName, String nameOfNamesrvOrBroker, String ipAndPort, String requestCode, String responseCode) {
        this.reqResCodeCounter.labels(clusterName, nameOfNamesrvOrBroker, ipAndPort, requestCode, responseCode).inc();
    }
}
