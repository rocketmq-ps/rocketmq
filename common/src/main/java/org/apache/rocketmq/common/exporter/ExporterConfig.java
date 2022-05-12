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
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.rocketmq.common.exporter;

import org.apache.rocketmq.common.constant.LoggerName;
import org.apache.rocketmq.logging.InternalLogger;
import org.apache.rocketmq.logging.InternalLoggerFactory;
import org.apache.rocketmq.remoting.common.RemotingUtil;

public class ExporterConfig {
    private static final InternalLogger log = InternalLoggerFactory.getLogger(LoggerName.EXPORTER_LOGGER_NAME);
    private String brokerIP3 = RemotingUtil.getLocalAddress();
    private int metricPort = 15557;
    private int outOfTimeSeconds = 60;

    public int getMetricPort() {
        return metricPort;
    }

    public void setMetricPort(int metricPort) {
        this.metricPort = metricPort;
    }

    public String getBrokerIP3() {
        return brokerIP3;
    }

    public void setBrokerIP3(String brokerIP3) {
        this.brokerIP3 = brokerIP3;
    }

    public int getOutOfTimeSeconds() {
        return outOfTimeSeconds;
    }

    public void setOutOfTimeSeconds(int outOfTimeSeconds) {
        this.outOfTimeSeconds = outOfTimeSeconds;
    }
}
