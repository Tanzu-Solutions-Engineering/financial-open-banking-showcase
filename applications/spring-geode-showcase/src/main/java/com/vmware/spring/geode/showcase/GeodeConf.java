package com.vmware.spring.geode.showcase;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.gemfire.config.annotation.ClientCacheApplication;
import org.springframework.data.gemfire.config.annotation.EnableClusterDefinedRegions;

@ClientCacheApplication
@EnableClusterDefinedRegions
@Configuration
public class GeodeConf
{

    public GeodeConf()
    {
        System.out.println("Hello");
    }
}
