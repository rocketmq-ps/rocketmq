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
import io.prometheus.client.Gauge;
import org.apache.rocketmq.common.MixAll;
import org.apache.rocketmq.common.constant.LoggerName;
import org.apache.rocketmq.exporter.common.MQRoleName;
import org.apache.rocketmq.exporter.common.MetricConstant;
import org.apache.rocketmq.logging.InternalLogger;
import org.apache.rocketmq.logging.InternalLoggerFactory;

import java.util.Locale;

public class BrokerMetricCollector {
    private static InternalLogger log = InternalLoggerFactory.getLogger(LoggerName.EXPORTER_LOGGER_NAME);
    //max offset of topic consume queue
    private Gauge topicOffset;
    //max offset of retry topic consume queue
    private Gauge retryTopicOffset;
    //max offset of dlq topic consume queue
    private Gauge dLQTopicOffset;

    //total put numbers for topics
    private Gauge topicPutNums;
    //total get numbers for topics
    private Gauge topicPutSize;

    //diff for consumer group
    private Gauge consumerDiff;
    //retry diff for consumer group
    private Gauge consumerRetryDiff;
    //dlq diff for consumer group
    private Gauge consumerDLQDiff;
    //consumer count
    private Gauge consumerCounts;

    //count of consume fail
    private Gauge consumerClientFailedMsgCounts;
    //TPS of consume fail
    private Gauge consumerClientFailedTPS;
    //TPS of consume success
    private Gauge consumerClientOKTPS;
    //rt of consume
    private Gauge consumerClientRT;
    //pull RT
    private Gauge consumerClientPullRT;
    //pull tps
    private Gauge consumerClientPullTPS;

    //broker offset for consumer-topic
    private Gauge groupBrokerTotalOffset;
    //consumer offset for consumer-topic
    private Gauge groupConsumeTotalOffset;
    //consume tps
    private Gauge groupConsumeTPS;
    //consumed message count for consumer-topic
    private Gauge groupGetNums;
    //consumed message size(byte) for consumer-topic
    private Gauge groupGetSize;
    //re-consumed message count for consumer-topic
    private Gauge sendBackNums;
    // group latency time
    private Gauge groupLatencyByTime;

    //total put message count for one broker
    private Gauge brokerPutNums;
    //total get message count for one broker
    private Gauge brokerGetNums;

    private Gauge brokerRuntimeMsgPutTotalTodayNow;
    private Gauge brokerRuntimeMsgGetTotalTodayNow;

    private Gauge brokerRuntimeMsgGetTotalYesterdayMorning;
    private Gauge brokerRuntimeMsgPutTotalYesterdayMorning;
    private Gauge brokerRuntimeMsgGetTotalTodayMorning;
    private Gauge brokerRuntimeMsgPutTotalTodayMorning;

    private Gauge brokerRuntimeDispatchBehindBytes;
    private Gauge brokerRuntimePutMessageSizeTotal;

    private Gauge brokerRuntimePutMessageAverageSize;
    private Gauge brokerRuntimeQueryThreadPoolQueueCapacity;
    private Gauge brokerRuntimeRemainTransientStoreBufferNumbs;
    private Gauge brokerRuntimeEarliestMessageTimeStamp;
    private Gauge brokerRuntimePutMessageEntireTimeMax;
    private Gauge brokerRuntimeStartAcceptSendRequestTimeStamp;
    private Gauge brokerRuntimeSendThreadPoolQueueSize;
    private Gauge brokerRuntimePutMessageTimesTotal;
    private Gauge brokerRuntimeGetMessageEntireTimeMax;
    private Gauge brokerRuntimePageCacheLockTimeMills;
    private Gauge brokerRuntimeCommitLogDiskRatio;
    private Gauge brokerRuntimeConsumeQueueDiskRatio;

    private Gauge brokerRuntimeGetFoundTps600;
    private Gauge brokerRuntimeGetFoundTps60;
    private Gauge brokerRuntimeGetFoundTps10;
    private Gauge brokerRuntimeGetTotalTps600;
    private Gauge brokerRuntimeGetTotalTps60;
    private Gauge brokerRuntimeGetTotalTps10;
    private Gauge brokerRuntimeGetTransferedTps600;
    private Gauge brokerRuntimeGetTransferedTps60;
    private Gauge brokerRuntimeGetTransferedTps10;
    private Gauge brokerRuntimeGetMissTps600;
    private Gauge brokerRuntimeGetMissTps60;
    private Gauge brokerRuntimeGetMissTps10;
    private Gauge brokerRuntimePutTps600;
    private Gauge brokerRuntimePutTps60;
    private Gauge brokerRuntimePutTps10;

    private Gauge brokerRuntimeDispatchMaxBuffer;

    private Gauge brokerRuntimePutMessageDistributeTimeMap10toMore;
    private Gauge brokerRuntimePutMessageDistributeTimeMap5to10s;
    private Gauge brokerRuntimePutMessageDistributeTimeMap4to5s;
    private Gauge brokerRuntimePutMessageDistributeTimeMap3to4s;
    private Gauge brokerRuntimePutMessageDistributeTimeMap2to3s;
    private Gauge brokerRuntimePutMessageDistributeTimeMap1to2s;
    private Gauge brokerRuntimePutMessageDistributeTimeMap500to1s;
    private Gauge brokerRuntimePutMessageDistributeTimeMap200to500ms;
    private Gauge brokerRuntimePutMessageDistributeTimeMap100to200ms;
    private Gauge brokerRuntimePutMessageDistributeTimeMap50to100ms;
    private Gauge brokerRuntimePutMessageDistributeTimeMap10to50ms;
    private Gauge brokerRuntimePutMessageDistributeTimeMap0to10ms;
    private Gauge brokerRuntimePutMessageDistributeTimeMap0ms;

    private Gauge brokerRuntimePullThreadPoolQueueCapacity;
    private Gauge brokerRuntimeSendThreadPoolQueueCapacity;
    private Gauge brokerRuntimePullThreadPoolQueueSize;
    private Gauge brokerRuntimeQueryThreadPoolQueueSize;

    private Gauge brokerRuntimePullThreadPoolQueueHeadWaitTimeMills;
    private Gauge brokerRuntimeQueryThreadPoolQueueHeadWaitTimeMills;
    private Gauge brokerRuntimeSendThreadPoolQueueHeadWaitTimeMills;
    private Gauge brokerRuntimeCommitLogDirCapacityFree;
    private Gauge brokerRuntimeCommitLogDirCapacityTotal;

    private Gauge brokerRuntimeCommitLogMaxOffset;
    private Gauge brokerRuntimeCommitLogMinOffset;
    private Gauge brokerRuntimeRemainHowManyDataToFlush;

    // request qps
    private Counter reqResCodeCounter;

    public BrokerMetricCollector(CollectorRegistry registry) {
        this.initMetrics(registry);
    }

    public void addTopicOffsetMetric(String clusterName, String brokerName, String topic, long lastUpdateTimestamp, double value) {
        if (topic.startsWith(MixAll.RETRY_GROUP_TOPIC_PREFIX)) {
            retryTopicOffset.labels(clusterName, brokerName, topic, String.valueOf(lastUpdateTimestamp)).set(value);
        } else if (topic.startsWith(MixAll.DLQ_GROUP_TOPIC_PREFIX)) {
            dLQTopicOffset.labels(clusterName, brokerName, topic, String.valueOf(lastUpdateTimestamp)).set(value);
        } else {
            topicOffset.labels(clusterName, brokerName, topic, String.valueOf(lastUpdateTimestamp)).set(value);
        }
    }

    public void addReqResCodeCounter(String clusterName, String nameOfNamesrvOrBroker, String ipAndPort, String requestCode, String responseCode) {
        this.reqResCodeCounter.labels(clusterName, nameOfNamesrvOrBroker, ipAndPort, requestCode, responseCode).inc();
    }

    public void addBrokerRuntimeMetric() {

    }

    private void initMetrics(CollectorRegistry registry) {
        reqResCodeCounter = buildCounterMetric(MQRoleName.BROKER.name().toLowerCase(Locale.ROOT), "req_res_code_counter", new String[]{"cluster", "nodeName", "ipAndPort", "req_code", "res_code"}, "Request and Response Code Counter", registry);

        topicOffset = buildGaugeMetric("", "", "rocketmq_producer_offset", new String[]{"cluster", "broker", "topic", "lastUpdateTimestamp"}, "topic offset", registry);
        retryTopicOffset = buildGaugeMetric("", "", "rocketmq_topic_retry_offset", new String[]{"cluster", "broker", "group", "lastUpdateTimestamp"}, "retry topic offset", registry);
        dLQTopicOffset = buildGaugeMetric("", "", "rocketmq_topic_dlq_offset", new String[]{"cluster", "broker", "group", "lastUpdateTimestamp"}, "dlq topic offset", registry);

        topicPutNums = buildGaugeMetric("", "", "rocketmq_producer_tps", new String[]{"cluster", "broker", "topic"}, "TopicPutNums", registry);
        topicPutSize = buildGaugeMetric("", "", "rocketmq_producer_message_size", new String[]{"cluster", "broker", "topic"}, "TopicPutMessageSize", registry);

        consumerDiff = buildGaugeMetric("", "", "rocketmq_group_diff", new String[]{"group", "topic", "countOfOnlineConsumers", "msgModel"}, "GroupDiff", registry);
        consumerRetryDiff = buildGaugeMetric("", "", "rocketmq_group_retrydiff", new String[]{"group", "topic", "countOfOnlineConsumers", "msgModel"}, "GroupRetryDiff", registry);
        consumerDLQDiff = buildGaugeMetric("", "", "rocketmq_group_dlqdiff", new String[]{"group", "topic", "countOfOnlineConsumers", "msgModel"}, "GroupDLQDiff", registry);
        consumerCounts = buildGaugeMetric("", "", "rocketmq_group_count", new String[]{"caddr", "localaddr", "group"}, "GroupCount", registry);

        consumerClientFailedMsgCounts = buildGaugeMetric("", "", "rocketmq_client_consume_fail_msg_count", new String[]{"clientAddr", "clientId", "group", "topic"}, "consumerClientFailedMsgCounts", registry);
        consumerClientFailedTPS = buildGaugeMetric("", "", "rocketmq_client_consume_fail_msg_tps", new String[]{"clientAddr", "clientId", "group", "topic"}, "consumerClientFailedTPS", registry);
        consumerClientOKTPS = buildGaugeMetric("", "", "rocketmq_client_consume_ok_msg_tps", new String[]{"clientAddr", "clientId", "group", "topic"}, "consumerClientOKTPS", registry);
        consumerClientRT = buildGaugeMetric("", "", "rocketmq_client_consume_rt", new String[]{"clientAddr", "clientId", "group", "topic"}, "consumerClientRT", registry);
        consumerClientPullRT = buildGaugeMetric("", "", "rocketmq_client_consumer_pull_rt", new String[]{"clientAddr", "clientId", "group", "topic"}, "consumerClientPullRT", registry);
        consumerClientPullTPS = buildGaugeMetric("", "", "rocketmq_client_consumer_pull_tps", new String[]{"clientAddr", "clientId", "group", "topic"}, "consumerClientPullTPS", registry);

        groupBrokerTotalOffset = buildGaugeMetric("", "", "rocketmq_consumer_offset", new String[]{"cluster", "broker", "topic", "group"}, "GroupBrokerTotalOffset", registry);
        groupConsumeTotalOffset = buildGaugeMetric("", "", "rocketmq_group_consume_total_offset", new String[]{"cluster", "broker", "topic", "group"}, "groupConsumeTotalOffset", registry);
        groupConsumeTPS = buildGaugeMetric("", "", "rocketmq_group_consume_tps", new String[]{"cluster", "broker", "topic", "group"}, "groupConsumeTPS", registry);
        groupGetNums = buildGaugeMetric("", "", "rocketmq_consumer_tps", new String[]{"cluster", "broker", "topic", "group"}, "groupGetNums", registry);
        groupGetSize = buildGaugeMetric("", "", "rocketmq_consumer_message_size", new String[]{"cluster", "broker", "topic", "group"}, "groupGetSize", registry);
        sendBackNums = buildGaugeMetric("", "", "rocketmq_send_back_nums", new String[]{"cluster", "broker", "topic", "group"}, "sendBackNums", registry);
        groupLatencyByTime = buildGaugeMetric("", "", "rocketmq_group_get_latency_by_storetime", new String[]{"cluster", "broker", "topic", "group"}, "groupLatencyByTime", registry);

        brokerPutNums = buildGaugeMetric("", "", "rocketmq_broker_tps", new String[]{"cluster", "brokerIP", "broker"}, "brokerPutNums", registry);
        brokerGetNums = buildGaugeMetric("", "", "rocketmq_broker_qps", new String[]{"cluster", "brokerIP", "broker"}, "brokerGetNums", registry);

        brokerRuntimeMsgPutTotalTodayNow = buildGaugeMetric("", "", "rocketmq_brokeruntime_msg_put_total_today_now", new String[]{"cluster", "brokerIP", "brokerHost", "des", "boottime", "broker_version"}, "brokerRuntimeMsgPutTotalTodayNow", registry);
        brokerRuntimeMsgGetTotalTodayNow = buildGaugeMetric("", "", "rocketmq_brokeruntime_msg_gettotal_today_now", new String[]{"cluster", "brokerIP", "brokerHost", "des", "boottime", "broker_version"}, "brokerRuntimeMsgGetTotalTodayNow", registry);
        brokerRuntimeMsgGetTotalYesterdayMorning = buildGaugeMetric("", "", "rocketmq_brokeruntime_msg_gettotal_yesterdaymorning", new String[]{"cluster", "brokerIP", "brokerHost", "des", "boottime", "broker_version"}, "brokerRuntimeMsgGetTotalYesterdayMorning", registry);
        brokerRuntimeMsgPutTotalYesterdayMorning = buildGaugeMetric("", "", "rocketmq_brokeruntime_msg_puttotal_yesterdaymorning", new String[]{"cluster", "brokerIP", "brokerHost", "des", "boottime", "broker_version"}, "rocketmq_brokeruntime_msg_puttotal_yesterdaymorning", registry);
        brokerRuntimeMsgGetTotalTodayMorning = buildGaugeMetric("", "", "rocketmq_brokeruntime_msg_gettotal_todaymorning", new String[]{"cluster", "brokerIP", "brokerHost", "des", "boottime", "broker_version"}, "brokerRuntimeMsgGetTotalTodayMorning", registry);
        brokerRuntimeMsgPutTotalTodayMorning = buildGaugeMetric("", "", "rocketmq_brokeruntime_msg_puttotal_todaymorning", new String[]{"cluster", "brokerIP", "brokerHost", "des", "boottime", "broker_version"}, "brokerRuntimeMsgPutTotalTodayMorning", registry);
        brokerRuntimeDispatchBehindBytes = buildGaugeMetric("", "", "rocketmq_brokeruntime_dispatch_behind_bytes", new String[]{"cluster", "brokerIP", "brokerHost", "des", "boottime", "broker_version"}, "brokerRuntimeDispatchBehindBytes", registry);
        brokerRuntimePutMessageSizeTotal = buildGaugeMetric("", "", "rocketmq_brokeruntime_put_message_size_total", new String[]{"cluster", "brokerIP", "brokerHost", "des", "boottime", "broker_version"}, "brokerRuntimePutMessageSizeTotal", registry);
        brokerRuntimePutMessageAverageSize = buildGaugeMetric("", "", "rocketmq_brokeruntime_put_message_average_size", new String[]{"cluster", "brokerIP", "brokerHost", "des", "boottime", "broker_version"}, "brokerRuntimePutMessageAverageSize", registry);
        brokerRuntimeQueryThreadPoolQueueCapacity = buildGaugeMetric("", "", "rocketmq_brokeruntime_query_threadpool_queue_capacity", new String[]{"cluster", "brokerIP", "brokerHost", "des", "boottime", "broker_version"}, "brokerRuntimeQueryThreadPoolQueueCapacity", registry);
        brokerRuntimeRemainTransientStoreBufferNumbs = buildGaugeMetric("", "", "rocketmq_brokeruntime_remain_transientstore_buffer_numbs", new String[]{"cluster", "brokerIP", "brokerHost", "des", "boottime", "broker_version"}, "brokerRuntimeRemainTransientStoreBufferNumbs", registry);
        brokerRuntimeEarliestMessageTimeStamp = buildGaugeMetric("", "", "rocketmq_brokeruntime_earliest_message_timestamp", new String[]{"cluster", "brokerIP", "brokerHost", "des", "boottime", "broker_version"}, "brokerRuntimeEarliestMessageTimeStamp", registry);
        brokerRuntimePutMessageEntireTimeMax = buildGaugeMetric("", "", "rocketmq_brokeruntime_putmessage_entire_time_max", new String[]{"cluster", "brokerIP", "brokerHost", "des", "boottime", "broker_version"}, "brokerRuntimePutMessageEntireTimeMax", registry);
        brokerRuntimeStartAcceptSendRequestTimeStamp = buildGaugeMetric("", "", "rocketmq_brokeruntime_start_accept_sendrequest_time", new String[]{"cluster", "brokerIP", "brokerHost", "des", "boottime", "broker_version"}, "brokerRuntimeStartAcceptSendRequestTimeStamp", registry);
        brokerRuntimeSendThreadPoolQueueSize = buildGaugeMetric("", "", "rocketmq_brokeruntime_send_threadpool_queue_size", new String[]{"cluster", "brokerIP", "brokerHost", "des", "boottime", "broker_version"}, "brokerRuntimeSendThreadPoolQueueSize", registry);
        brokerRuntimePutMessageTimesTotal = buildGaugeMetric("", "", "rocketmq_brokeruntime_putmessage_times_total", new String[]{"cluster", "brokerIP", "brokerHost", "des", "boottime", "broker_version"}, "brokerRuntimePutMessageTimesTotal", registry);
        brokerRuntimeGetMessageEntireTimeMax = buildGaugeMetric("", "", "rocketmq_brokeruntime_getmessage_entire_time_max", new String[]{"cluster", "brokerIP", "brokerHost", "des", "boottime", "broker_version"}, "brokerRuntimeGetMessageEntireTimeMax", registry);
        brokerRuntimePageCacheLockTimeMills = buildGaugeMetric("", "", "rocketmq_brokeruntime_pagecache_lock_time_mills", new String[]{"cluster", "brokerIP", "brokerHost", "des", "boottime", "broker_version"}, "brokerRuntimePageCacheLockTimeMills", registry);
        brokerRuntimeCommitLogDiskRatio = buildGaugeMetric("", "", "rocketmq_brokeruntime_commitlog_disk_ratio", new String[]{"cluster", "brokerIP", "brokerHost", "des", "boottime", "broker_version"}, "brokerRuntimeCommitLogDiskRatio", registry);
        brokerRuntimeConsumeQueueDiskRatio = buildGaugeMetric("", "", "rocketmq_brokeruntime_consumequeue_disk_ratio", new String[]{"cluster", "brokerIP", "brokerHost", "des", "boottime", "broker_version"}, "brokerRuntimeConsumeQueueDiskRatio", registry);
        brokerRuntimeGetFoundTps600 = buildGaugeMetric("", "", "rocketmq_brokeruntime_getfound_tps600", new String[]{"cluster", "brokerIP", "brokerHost", "des", "boottime", "broker_version"}, "brokerRuntimeGetFoundTps600", registry);
        brokerRuntimeGetFoundTps60 = buildGaugeMetric("", "", "rocketmq_brokeruntime_getfound_tps60", new String[]{"cluster", "brokerIP", "brokerHost", "des", "boottime", "broker_version"}, "brokerRuntimeGetFoundTps60", registry);
        brokerRuntimeGetFoundTps10 = buildGaugeMetric("", "", "rocketmq_brokeruntime_getfound_tps10", new String[]{"cluster", "brokerIP", "brokerHost", "des", "boottime", "broker_version"}, "brokerRuntimeGetFoundTps10", registry);
        brokerRuntimeGetTotalTps600 = buildGaugeMetric("", "", "rocketmq_brokeruntime_gettotal_tps600", new String[]{"cluster", "brokerIP", "brokerHost", "des", "boottime", "broker_version"}, "brokerRuntimeGetTotalTps600", registry);
        brokerRuntimeGetTotalTps60 = buildGaugeMetric("", "", "rocketmq_brokeruntime_gettotal_tps60", new String[]{"cluster", "brokerIP", "brokerHost", "des", "boottime", "broker_version"}, "brokerRuntimeGetTotalTps60", registry);
        brokerRuntimeGetTotalTps10 = buildGaugeMetric("", "", "rocketmq_brokeruntime_gettotal_tps10", new String[]{"cluster", "brokerIP", "brokerHost", "des", "boottime", "broker_version"}, "brokerRuntimeGetTotalTps10", registry);
        brokerRuntimeGetTransferedTps600 = buildGaugeMetric("", "", "rocketmq_brokeruntime_gettransfered_tps600", new String[]{"cluster", "brokerIP", "brokerHost", "des", "boottime", "broker_version"}, "brokerRuntimeGetTransferedTps600", registry);
        brokerRuntimeGetTransferedTps60 = buildGaugeMetric("", "", "rocketmq_brokeruntime_gettransfered_tps60", new String[]{"cluster", "brokerIP", "brokerHost", "des", "boottime", "broker_version"}, "brokerRuntimeGetTransferedTps60", registry);
        brokerRuntimeGetTransferedTps10 = buildGaugeMetric("", "", "rocketmq_brokeruntime_gettransfered_tps10", new String[]{"cluster", "brokerIP", "brokerHost", "des", "boottime", "broker_version"}, "brokerRuntimeGetTransferedTps10", registry);
        brokerRuntimeGetMissTps600 = buildGaugeMetric("", "", "rocketmq_brokeruntime_getmiss_tps600", new String[]{"cluster", "brokerIP", "brokerHost", "des", "boottime", "broker_version"}, "brokerRuntimeGetMissTps600", registry);
        brokerRuntimeGetMissTps60 = buildGaugeMetric("", "", "rocketmq_brokeruntime_getmiss_tps60", new String[]{"cluster", "brokerIP", "brokerHost", "des", "boottime", "broker_version"}, "brokerRuntimeGetMissTps60", registry);
        brokerRuntimeGetMissTps10 = buildGaugeMetric("", "", "rocketmq_brokeruntime_getmiss_tps10", new String[]{"cluster", "brokerIP", "brokerHost", "des", "boottime", "broker_version"}, "brokerRuntimeGetMissTps10", registry);
        brokerRuntimePutTps600 = buildGaugeMetric("", "", "rocketmq_brokeruntime_put_tps600", new String[]{"cluster", "brokerIP", "brokerHost", "des", "boottime", "broker_version"}, "brokerRuntimePutTps600", registry);
        brokerRuntimePutTps60 = buildGaugeMetric("", "", "rocketmq_brokeruntime_put_tps60", new String[]{"cluster", "brokerIP", "brokerHost", "des", "boottime", "broker_version"}, "brokerRuntimePutTps60", registry);
        brokerRuntimePutTps10 = buildGaugeMetric("", "", "rocketmq_brokeruntime_put_tps10", new String[]{"cluster", "brokerIP", "brokerHost", "des", "boottime", "broker_version"}, "brokerRuntimePutTps10", registry);
        brokerRuntimeDispatchMaxBuffer = buildGaugeMetric("", "", "rocketmq_brokeruntime_dispatch_maxbuffer", new String[]{"cluster", "brokerIP", "brokerHost", "des", "boottime", "broker_version"}, "brokerRuntimeDispatchMaxBuffer", registry);
        brokerRuntimePutMessageDistributeTimeMap10toMore = buildGaugeMetric("", "", "rocketmq_brokeruntime_pmdt_10stomore", new String[]{"cluster", "brokerIP", "brokerHost", "des", "boottime", "broker_version"}, "brokerRuntimePutMessageDistributeTimeMap10toMore", registry);
        brokerRuntimePutMessageDistributeTimeMap5to10s = buildGaugeMetric("", "", "rocketmq_brokeruntime_pmdt_5to10s", new String[]{"cluster", "brokerIP", "brokerHost", "des", "boottime", "broker_version"}, "brokerRuntimePutMessageDistributeTimeMap5to10s", registry);
        brokerRuntimePutMessageDistributeTimeMap4to5s = buildGaugeMetric("", "", "rocketmq_brokeruntime_pmdt_4to5s", new String[]{"cluster", "brokerIP", "brokerHost", "des", "boottime", "broker_version"}, "brokerRuntimePutMessageDistributeTimeMap4to5s", registry);
        brokerRuntimePutMessageDistributeTimeMap3to4s = buildGaugeMetric("", "", "rocketmq_brokeruntime_pmdt_3to4s", new String[]{"cluster", "brokerIP", "brokerHost", "des", "boottime", "broker_version"}, "brokerRuntimePutMessageDistributeTimeMap3to4s", registry);
        brokerRuntimePutMessageDistributeTimeMap2to3s = buildGaugeMetric("", "", "rocketmq_brokeruntime_pmdt_2to3s", new String[]{"cluster", "brokerIP", "brokerHost", "des", "boottime", "broker_version"}, "brokerRuntimePutMessageDistributeTimeMap2to3s", registry);
        brokerRuntimePutMessageDistributeTimeMap1to2s = buildGaugeMetric("", "", "rocketmq_brokeruntime_pmdt_1to2s", new String[]{"cluster", "brokerIP", "brokerHost", "des", "boottime", "broker_version"}, "brokerRuntimePutMessageDistributeTimeMap1to2s", registry);
        brokerRuntimePutMessageDistributeTimeMap500to1s = buildGaugeMetric("", "", "rocketmq_brokeruntime_pmdt_500to1s", new String[]{"cluster", "brokerIP", "brokerHost", "des", "boottime", "broker_version"}, "brokerRuntimePutMessageDistributeTimeMap500to1s", registry);
        brokerRuntimePutMessageDistributeTimeMap200to500ms = buildGaugeMetric("", "", "rocketmq_brokeruntime_pmdt_200to500ms", new String[]{"cluster", "brokerIP", "brokerHost", "des", "boottime", "broker_version"}, "brokerRuntimePutMessageDistributeTimeMap200to500ms", registry);
        brokerRuntimePutMessageDistributeTimeMap100to200ms = buildGaugeMetric("", "", "rocketmq_brokeruntime_pmdt_100to200ms", new String[]{"cluster", "brokerIP", "brokerHost", "des", "boottime", "broker_version"}, "brokerRuntimePutMessageDistributeTimeMap100to200ms", registry);
        brokerRuntimePutMessageDistributeTimeMap50to100ms = buildGaugeMetric("", "", "rocketmq_brokeruntime_pmdt_50to100ms", new String[]{"cluster", "brokerIP", "brokerHost", "des", "boottime", "broker_version"}, "brokerRuntimePutMessageDistributeTimeMap50to100ms", registry);
        brokerRuntimePutMessageDistributeTimeMap10to50ms = buildGaugeMetric("", "", "rocketmq_brokeruntime_pmdt_10to50ms", new String[]{"cluster", "brokerIP", "brokerHost", "des", "boottime", "broker_version"}, "brokerRuntimePutMessageDistributeTimeMap10to50ms", registry);
        brokerRuntimePutMessageDistributeTimeMap0to10ms = buildGaugeMetric("", "", "rocketmq_brokeruntime_pmdt_0to10ms", new String[]{"cluster", "brokerIP", "brokerHost", "des", "boottime", "broker_version"}, "brokerRuntimePutMessageDistributeTimeMap0to10ms", registry);
        brokerRuntimePutMessageDistributeTimeMap0ms = buildGaugeMetric("", "", "rocketmq_brokeruntime_pmdt_0ms", new String[]{"cluster", "brokerIP", "brokerHost", "des", "boottime", "broker_version"}, "brokerRuntimePutMessageDistributeTimeMap0ms", registry);
        brokerRuntimePullThreadPoolQueueCapacity = buildGaugeMetric("", "", "rocketmq_brokeruntime_pull_threadpoolqueue_capacity", new String[]{"cluster", "brokerIP", "brokerHost", "des", "boottime", "broker_version"}, "brokerRuntimePullThreadPoolQueueCapacity", registry);
        brokerRuntimeSendThreadPoolQueueCapacity = buildGaugeMetric("", "", "rocketmq_brokeruntime_send_threadpoolqueue_capacity", new String[]{"cluster", "brokerIP", "brokerHost", "des", "boottime", "broker_version"}, "brokerRuntimeSendThreadPoolQueueCapacity", registry);
        brokerRuntimePullThreadPoolQueueSize = buildGaugeMetric("", "", "rocketmq_brokeruntime_pull_threadpoolqueue_size", new String[]{"cluster", "brokerIP", "brokerHost", "des", "boottime", "broker_version"}, "brokerRuntimePullThreadPoolQueueSize", registry);
        brokerRuntimeQueryThreadPoolQueueSize = buildGaugeMetric("", "", "rocketmq_brokeruntime_query_threadpoolqueue_size", new String[]{"cluster", "brokerIP", "brokerHost", "des", "boottime", "broker_version"}, "brokerRuntimeQueryThreadPoolQueueSize", registry);
        brokerRuntimePullThreadPoolQueueHeadWaitTimeMills = buildGaugeMetric("", "", "rocketmq_brokeruntime_pull_threadpoolqueue_headwait_timemills", new String[]{"cluster", "brokerIP", "brokerHost", "des", "boottime", "broker_version"}, "brokerRuntimePullThreadPoolQueueHeadWaitTimeMills", registry);
        brokerRuntimeQueryThreadPoolQueueHeadWaitTimeMills = buildGaugeMetric("", "", "rocketmq_brokeruntime_query_threadpoolqueue_headwait_timemills", new String[]{"cluster", "brokerIP", "brokerHost", "des", "boottime", "broker_version"}, "brokerRuntimeQueryThreadPoolQueueHeadWaitTimeMills", registry);
        brokerRuntimeSendThreadPoolQueueHeadWaitTimeMills = buildGaugeMetric("", "", "rocketmq_brokeruntime_send_threadpoolqueue_headwait_timemills", new String[]{"cluster", "brokerIP", "brokerHost", "des", "boottime", "broker_version"}, "brokerRuntimeSendThreadPoolQueueHeadWaitTimeMills", registry);
        brokerRuntimeCommitLogDirCapacityFree = buildGaugeMetric("", "", "rocketmq_brokeruntime_commitlogdir_capacity_free", new String[]{"cluster", "brokerIP", "brokerHost", "des", "boottime", "broker_version"}, "brokerRuntimeCommitLogDirCapacityFree", registry);
        brokerRuntimeCommitLogDirCapacityTotal = buildGaugeMetric("", "", "rocketmq_brokeruntime_commitlogdir_capacity_total", new String[]{"cluster", "brokerIP", "brokerHost", "des", "boottime", "broker_version"}, "brokerRuntimeCommitLogDirCapacityTotal", registry);
        brokerRuntimeCommitLogMaxOffset = buildGaugeMetric("", "", "rocketmq_brokeruntime_commitlog_maxoffset", new String[]{"cluster", "brokerIP", "brokerHost", "des", "boottime", "broker_version"}, "brokerRuntimeCommitLogMaxOffset", registry);
        brokerRuntimeCommitLogMinOffset = buildGaugeMetric("", "", "rocketmq_brokeruntime_commitlog_minoffset", new String[]{"cluster", "brokerIP", "brokerHost", "des", "boottime", "broker_version"}, "brokerRuntimeCommitLogMinOffset", registry);
        brokerRuntimeRemainHowManyDataToFlush = buildGaugeMetric("", "", "rocketmq_brokeruntime_remain_howmanydata_toflush", new String[]{"cluster", "brokerIP", "brokerHost", "des", "boottime", "broker_version"}, "brokerRuntimeRemainHowManyDataToFlush", registry);
    }

    private static Counter buildCounterMetric(String subSystem, String name, String[] labels, String help, CollectorRegistry registry) {
        return Counter.build()
                .namespace(MetricConstant.NAMESPACE)
                .subsystem(subSystem)
                .name(name)
                .labelNames(labels)
                .help(help)
                .create()
                .register(registry);
    }

    private static Gauge buildGaugeMetric(String namespace, String subSystem, String name, String[] labels, String help, CollectorRegistry registry) {
        return Gauge.build()
                .namespace(namespace)
                .subsystem(subSystem)
                .name(name)
                .labelNames(labels)
                .help(help)
                .create()
                .register(registry);
    }

}
