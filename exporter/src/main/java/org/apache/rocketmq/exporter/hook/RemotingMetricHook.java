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

package org.apache.rocketmq.exporter.hook;

import org.apache.rocketmq.exporter.ExporterController;
import org.apache.rocketmq.remoting.RPCHook;
import org.apache.rocketmq.remoting.common.RemotingUtil;
import org.apache.rocketmq.remoting.netty.NettyServerConfig;
import org.apache.rocketmq.remoting.protocol.RemotingCommand;

public class RemotingMetricHook implements RPCHook {
    private ExporterController controller;
    private NettyServerConfig serverConfig;
    private static final String LOCA_IP = RemotingUtil.getLocalAddress();

    public RemotingMetricHook(
            ExporterController controller,
            NettyServerConfig serverConfig) {
        this.controller = controller;
        this.serverConfig = serverConfig;
    }

    @Override
    public void doBeforeRequest(String remoteAddr, RemotingCommand request) {

    }

    @Override
    public void doAfterResponse(String remoteAddr, RemotingCommand request, RemotingCommand response) {
        if (this.controller.getNamesrvCollector() != null) {
            this.controller.getNamesrvCollector().addReqResCodeCounter(
                    "",
                    "",
                    String.format("%s:%d", LOCA_IP, this.serverConfig.getListenPort()),
                    String.valueOf(request.getCode()),
                    String.valueOf(response.getCode()));
        }
        if (this.controller.getBrokerCollector() != null) {
            this.controller.getBrokerCollector().addReqResCodeCounter(
                    "",
                    "",
                    String.format("%s:%d", LOCA_IP, this.serverConfig.getListenPort()),
                    String.valueOf(request.getCode()),
                    String.valueOf(response.getCode()));
        }

    }
}
