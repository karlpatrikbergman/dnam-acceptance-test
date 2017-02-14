package com.infinera.metro.dnam.acceptancetest.testimplementation;

import com.infinera.metro.dnam.acceptancetest.AppConfig;
import com.infinera.metro.dnam.acceptancetest.applicationdriver.NodeService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.rmi.RemoteException;
import java.util.Map;

/**
 *  This class would belong to middle layer, "Test Implementation Layer"
 */
@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class)
public class DnamNodesTest {

    private static final String NODE_IP_ADDRESS = "172.35.0.101";

    @Autowired
    private NodeService nodeService;

    @Test
    public void add_node_test() throws RemoteException {

        //Given
        log.info("Adding node with ip {}", NODE_IP_ADDRESS);
        nodeService.addNode(NODE_IP_ADDRESS);

        //When
//        log.info("Asserting node with ip {} was added", NODE_IP_ADDRESS);
//        NodeEntry nodeEntry = nodeService.getNode(NODE_IP_ADDRESS);

        //Then
//        assertNotNull(nodeEntry);
//        assertEquals(NODE_IP_ADDRESS, nodeEntry.getUserRef());
//        log.info("SUCCESS");
    }

    private void printEnvironmentVariables() {
        Map<String, String> env = System.getenv();
        for (String envName : env.keySet()) {
            System.out.format("%s=%s%n",
                    envName,
                    env.get(envName));
        }
    }
}