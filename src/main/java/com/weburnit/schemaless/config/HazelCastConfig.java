package com.weburnit.schemaless.config;

import com.hazelcast.config.Config;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HazelCastConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(HazelCastConfig.class);

    @Bean
    public HazelcastInstance hazelcastInstance() {
        Config config = new Config();

        config.getNetworkConfig().getJoin()
            .getTcpIpConfig().addMember("localhost").setEnabled(true);
        config.getNetworkConfig().getJoin()
            .getMulticastConfig().setEnabled(false);

        return Hazelcast.newHazelcastInstance(config);
    }

}
