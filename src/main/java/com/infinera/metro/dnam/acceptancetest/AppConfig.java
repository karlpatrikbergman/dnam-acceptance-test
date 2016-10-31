

package com.infinera.metro.dnam.acceptancetest;

import com.infinera.metro.dnam.acceptancetest.applicationdriver.NodeService;
import com.infinera.metro.dnam.acceptancetest.applicationdriver.RmiServiceFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    private static final int DEFAULT_MAX_TOTAL_CONNECTIONS = 100;
    private static final int DEFAULT_MAX_CONNECTIONS_PER_ROUTE = 5;

//    @Bean
//    public TestConfiguration testConfig() {
//        return new TestConfiguration();
//    }
//
//    @Bean
//    public DnamNodesTest addNodeAcceptanceTest() {
//        return new DnamNodesTest();
//    }
//

    @Bean
    public RmiServiceFactory rmiServiceFactory() {
        RmiServiceFactory.RmiConfig rmiConfig = new RmiServiceFactory.RmiConfig();
        rmiConfig.setHostname("172.35.0.3");
        rmiConfig.setRmiPort(1099);
        return new RmiServiceFactory(rmiConfig);
    }

    //Part of Application Driver
    @Bean
    public NodeService nodeAdministrationController() {
        return new NodeService(rmiServiceFactory());
    }
}
