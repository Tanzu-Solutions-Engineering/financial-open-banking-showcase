/*
 *
 *  * Copyright 2023 VMware, Inc.
 *  * SPDX-License-Identifier: GPL-3.0
 *
 */

package com.vmware.gemfire.spring.redis.server;


import com.vmware.gemfire.redis.internal.data.RedisKey;
import com.vmware.gemfire.redis.internal.data.RedisString;
import lombok.extern.slf4j.Slf4j;
import nyla.solutions.core.util.Config;
import org.apache.geode.cache.*;
import org.apache.geode.cache.asyncqueue.AsyncEventListener;
import org.apache.geode.cache.asyncqueue.AsyncEventQueue;
import org.apache.geode.cache.wan.GatewaySender;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.gemfire.PartitionedRegionFactoryBean;
import org.springframework.data.gemfire.PeerRegionFactoryBean;
import org.springframework.data.gemfire.config.annotation.CacheServerApplication;
import org.springframework.data.gemfire.config.annotation.RegionConfigurer;
import org.springframework.data.gemfire.util.ArrayUtils;
import org.springframework.data.gemfire.wan.AsyncEventQueueFactoryBean;
import org.springframework.data.gemfire.wan.GatewayReceiverFactoryBean;
import org.springframework.data.gemfire.wan.GatewaySenderFactoryBean;

import static org.springframework.data.gemfire.util.ArrayUtils.asArray;


/**
 * spring.data.gemfire.locators
 */
@Configuration
@CacheServerApplication
@Slf4j
public class GemFireConfig {

    @Value("${spring.data.gemfire.locators}")
    private String locators;

    /**
     * REGION_NAME = "GF_REDIS_REGION"
     */
    public static final String REGION_NAME = "GF_REDIS_REGION";

    /**
     * PARTITION
     * PARTITION_REDUNDANT
     */
    @Value("${gemfire.redis.region.type:PARTITION}")
    private String regionTypeName;


    @Value("${spring.data.gemfire.cache.name}")
    private String serverName;

    @Value("${spring.data.gemfire.cache.server.port}")
    private int port;

    @Value("${gemfire.gemfire-for-redis-port}")
    private String redisPort;

    @Bean
    GatewayReceiverFactoryBean gatewayReceiver(Cache cache) {

        GatewayReceiverFactoryBean gatewayReceiver = new GatewayReceiverFactoryBean(cache);
        //  gatewayReceiver.setHostnameForSenders(GATEWAY_RECEIVER_HOSTNAME_FOR_SENDERS);
        //  gatewayReceiver.setStartPort(GATEWAY_RECEIVER_START_PORT);
        //  gatewayReceiver.setEndPort(GATEWAY_RECEIVER_END_PORT);

        return gatewayReceiver;
    }

    @Bean
    GatewaySenderFactoryBean customersByNameGatewaySender(Cache cache,
                                                          @Value("${gemfire.remote-distributed-system-id}") int remoteDistributedSystemId) {

        GatewaySenderFactoryBean gatewaySender = new GatewaySenderFactoryBean(cache);
        gatewaySender.setRemoteDistributedSystemId(remoteDistributedSystemId);

        return gatewaySender;
    }



    @Bean
    RegionConfigurer regionConfigurer(GatewaySender gatewaySender) {

        return new RegionConfigurer() {

            @Override
            public void configure(String beanName, PeerRegionFactoryBean<?, ?> regionBean) {


                System.setProperty("gemfire-for-redis-port", redisPort);
                System.setProperty("gemfire-for-redis-enabled","true");
                System.setProperty("gemfire-for-redis-use-default-region-config","false");
                System.setProperty("gemfire-for-redis-use-default-region-config","false");
                System.setProperty("gemfire-for-redis-region-name",GemFireConfig.REGION_NAME);

                if (REGION_NAME.equals(beanName)) {
                    regionBean.setGatewaySenders(asArray(gatewaySender));
                }
            }
        };
    }


    /**
     * @param gemFireCache    the GemFire cache
     * @return the factory to create the Redis region
     */
    @Bean
    PartitionedRegionFactoryBean<String,Object> region(GemFireCache gemFireCache, RegionConfigurer regionConfigurer)
    {

        log.info("Create region {} with regionTypeName {}",REGION_NAME,regionTypeName);

        var region = new PartitionedRegionFactoryBean<String,Object>();

        region.setCache(gemFireCache);
        region.setDataPolicy(DataPolicy.PARTITION);

        region.setPersistent(false);
        region.setName(REGION_NAME);


        region.setRegionConfigurers(regionConfigurer);

        return region;
    }

    /**
     * Working around for
     * (error) MOVED 6918 192.168.1.76:6379
     * @return
     */
    @Bean
    ApplicationContextAware startupListener() {
        return applicationContext -> {
            byte[] bytes = {1};
            var region = applicationContext.getBean(Region.class);

            var key = new RedisKey(bytes);
            var data = new RedisString(bytes);

            log.info("Putting data in region {}", region);
            region.put(key, data);

        };
    }
}
