/*
 * Copyright (c) 2013 Raycloud.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.trilemon.boss.infra.base.service;

import com.google.common.collect.Maps;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ThreadPoolExecutor;


/**
 * @author kevin
 */
public class AppService {
    private static Logger logger = LoggerFactory.getLogger(AppService.class);
    private Map<String, ThreadPoolExecutor> threadPoolExecutorMap = Maps.newHashMap();
    private String serviceName;
    private String serviceId;
    private Map<String, ThreadPoolExecutor> threadMap = Maps.newHashMap();

    public DateTime getLocalSystemTime() {
        return DateTime.now();
    }

    public void addThreadPoolExecutor(String name, ThreadPoolExecutor threadPoolExecutor) {
        threadPoolExecutorMap.put(name, threadPoolExecutor);
    }

    public Map<String, ThreadPoolExecutor> getThreadPoolExecutorMap() {
        return threadPoolExecutorMap;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public void addThread(String name,ThreadPoolExecutor threadPoolExecutor) {
        threadMap.put(name,threadPoolExecutor);
    }
    public void addThreads(Map<String,ThreadPoolExecutor> threadPoolExecutorMap) {
        threadMap.putAll(threadPoolExecutorMap);
    }
}
