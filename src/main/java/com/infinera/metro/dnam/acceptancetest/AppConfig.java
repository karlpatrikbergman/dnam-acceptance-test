

package com.infinera.metro.dnam.acceptancetest;

import com.infinera.metro.dnam.acceptancetest.applicationdriver.NodeService;
import com.infinera.metro.dnam.acceptancetest.applicationdriver.RmiServiceFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public RmiServiceFactory rmiServiceFactory() {
        RmiServiceFactory.RmiConfig rmiConfig = new RmiServiceFactory.RmiConfig();
        rmiConfig.setHostname("172.35.0.100");
        rmiConfig.setRmiPort(1099);
        return new RmiServiceFactory(rmiConfig);
    }

    //Part of Application Driver
    @Bean
    public NodeService nodeService() {
        return new NodeService(rmiServiceFactory());
    }
}
