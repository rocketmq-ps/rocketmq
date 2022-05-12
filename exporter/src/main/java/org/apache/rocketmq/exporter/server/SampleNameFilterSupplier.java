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


package org.apache.rocketmq.exporter.server;

import io.prometheus.client.Predicate;
import io.prometheus.client.Supplier;

/**
 * For convenience, an implementation of a {@code Supplier<Predicate<String>>} that
 * always returns the same sampleNameFilter.
 */
public class SampleNameFilterSupplier implements Supplier<Predicate<String>> {

    private final Predicate<String> sampleNameFilter;

    public static SampleNameFilterSupplier of(Predicate<String> sampleNameFilter) {
        return new SampleNameFilterSupplier(sampleNameFilter);
    }

    private SampleNameFilterSupplier(Predicate<String> sampleNameFilter) {
        this.sampleNameFilter = sampleNameFilter;
    }

    @Override
    public Predicate<String> get() {
        return sampleNameFilter;
    }
}
