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
package org.apache.rocketmq.exporter.model.common;

public class RequestCodeCounterMetric extends CommonMetric {
    private String nodeName; // namesrv name or broker name. namesrv config for now no
    private String ipAndPort; // ip and port for namesrv or broker
    private String type; // Broker or Namesrv
    private int requestCode;

    public RequestCodeCounterMetric(String nodeName, String ipAndPort, int requestCode, String type) {
        this.nodeName = nodeName;
        this.ipAndPort = ipAndPort;
        this.requestCode = requestCode;
        this.type = type;
    }

    public int getRequestCode() {
        return requestCode;
    }

    public void setRequestCode(int requestCode) {
        this.requestCode = requestCode;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public String getIpAndPort() {
        return ipAndPort;
    }

    public void setIpAndPort(String ipAndPort) {
        this.ipAndPort = ipAndPort;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof RequestCodeCounterMetric)) {
            return false;
        }
        RequestCodeCounterMetric other = (RequestCodeCounterMetric) obj;

        return other.getClusterName().equals(clusterName) && other.getType().equals(getType());
    }

    @Override
    public int hashCode() {
        int hash = 1;
        hash = 37 * hash + clusterName.hashCode();
        hash = 37 * hash + nodeName.hashCode();
        hash = 37 * hash + ipAndPort.hashCode();
        hash = 37 * hash + type.hashCode();
        hash = 37 * hash + String.valueOf(requestCode).hashCode();
        return hash;
    }

    @Override
    public String toString() {
        return String.format("cluster=%s, node=%s, ipport=%s, type=%s, code=%d", getClusterName(), getNodeName(), getIpAndPort(), getType(), getRequestCode());
    }
}
