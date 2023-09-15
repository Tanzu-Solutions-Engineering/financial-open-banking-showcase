/*
 *
 *  * Copyright 2023 VMware, Inc.
 *  * SPDX-License-Identifier: GPL-3.0
 *
 */

package com.vmware.gemfire.spring.redis.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GfRedisServer {

    private static final String DEFAULT_REDIS_PORT = "6379";

    /**
     * --J=-Dgemfire-for-redis-port=6379  --J=-Dgemfire-for-redis-enabled=true
     */
    public static void main(String[] args) {
        System.setProperty("gemfire-for-redis-enabled","true");
        SpringApplication.run(GfRedisServer.class,args);
    }
}
