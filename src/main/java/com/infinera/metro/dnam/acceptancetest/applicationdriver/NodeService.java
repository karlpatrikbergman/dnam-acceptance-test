package com.infinera.metro.dnam.acceptancetest.applicationdriver;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import se.transmode.tnm.crypto.PasswordEncrypter;
import se.transmode.tnm.model.nodes.AlarmHandling;
import se.transmode.tnm.model.nodes.NodeFamily;
import se.transmode.tnm.model.nodes.NodeRef;
import se.transmode.tnm.model.nodes.TopologyUsed;
import se.transmode.tnm.model.nodes.details.EnmLoginDetails;
import se.transmode.tnm.model.nodes.details.FtpLoginDetails;
import se.transmode.tnm.model.nodes.inventory.SubrackType;
import se.transmode.tnm.model.nodes.snmp.SnmpContactDetails;
import se.transmode.tnm.model.nodes.snmp.SnmpVersion;
import se.transmode.tnm.mtosi.model.enums.LagActive;
import se.transmode.tnm.mtosi.model.enums.vendorext.IpTableStatus;
import se.transmode.tnm.rmiclient.server.rmiserver.Server;
import se.transmode.tnm.rmiclient.server.rmiserver.ServerDefs;
import se.transmode.tnm.rmiclient.server.rmiserver.ServerSessionType;
import se.transmode.tnm.rmiclient.server.rmiserver.Session;
import se.transmode.tnm.rmiclient.server.services.discovery.NodeEntry;
import se.transmode.tnm.rmiclient.server.services.discovery.NodeEntryFactory;
import se.transmode.tnm.rmiclient.server.services.discovery.NodesDiscoveryRequest;
import se.transmode.tnm.rmiclient.server.services.discovery.SubnetEntry;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

/**
 *  This class (along with several others) would belong to lowest layer, "Application Driver Layer".
 *  The API for this layer should be expressed in domain language.
 *  With a well-designed application driver layer, it becomes possible to
 *  completely dispense with the acceptance criteria layer and express the
 *  acceptance criteria in the implementation of the test.
 */
@Slf4j
@Component
public class NodeService {
    private final RmiServiceFactory rmiServiceFactory;
    private Session session;

    @Autowired
    public NodeService(RmiServiceFactory rmiServiceFactory) {
        log.debug("Initializing NodeService");
        this.rmiServiceFactory = rmiServiceFactory;
    }

    public void addNode(String nodeIp) throws RemoteException {
        log.debug("\n ############ About to checkSession");
        checkSession();
        NodeEntry nodeEntry = getNodeEntry(nodeIp, new ArrayList<>());
        session.process(NodesDiscoveryRequest.add(nodeEntry));
        log.debug("\n\nAfter session.process\n\n");
    }

    private void checkSession() {
        log.debug("\n ############ In checkSession");
        if(session == null) {
            log.debug("\n ############ Rmi session was null, create a new one");
            createSession();
            boolean sessionIsNull = (session == null) ? true : false;
            log.debug("\n ############ After createSession method call, sessionIsNull " + sessionIsNull);
        }
    }

    public NodeEntry getNode(String nodeIp) throws RemoteException {
        return session.process(NodesDiscoveryRequest.getNode(nodeIp))
                .asNodesDiscoveryResponse()
                .getNodeEntry();
    }

//    public void deleteNode(String nodeIp) throws RemoteException {
//        NodeEntry nodeEntry = getNodeEntry(nodeIp, new ArrayList<>());
//        session.process(NodesDiscoveryRequest.deleteNode(nodeEntry));
//    }

    @PostConstruct
    public void createSession() {
        log.debug("\n ############ In createSession");
        final Server server = rmiServiceFactory.lookupRemoteService(Server.class, ServerDefs.SERVER_RMI_NAME);
        try {
            this.session = server.createSession(ServerSessionType.WEBAPP);
            log.debug("\n ############ Created rmi session");
            boolean sessionIsNull = (session == null) ? true : false;
            log.debug("\n ############ In createSession after creating session, sessionIsNull " + sessionIsNull);
        } catch (RemoteException e) {
            log.error("\n ############ Failed to create rmi session {}", e.getMessage(), e);
        }
    }

    @PreDestroy
    public void destroySession() {
        log.info("Closing rmi session");
        try {
            session.disconnect();
            log.info("Rmi session closed");
        } catch (RemoteException e) {
            log.warn("Failed to close rmi session {}", e.getMessage(), e);
        }
    }

    private NodeEntry getNodeEntry(String nodeIp, List<SubnetEntry> nodeEntrySubnets) {
        SnmpContactDetails snmpContactDetails = SnmpContactDetails.useSpecified(
                SnmpContactDetails.SNMP_PORT_UNDEFINED,
                SnmpVersion.V3,
                60,
                "public",
                "oper",
                PasswordEncrypter.encrypt("1234567890", true)
        );

        return NodeEntryFactory.createSnmpContacted(
                "",
                "",
                NodeRef.ipAddress(nodeIp),
                NodeFamily.TM_3000,
                SubrackType.Subrack_Unknown,
                AlarmHandling.EnableNodeAlarms,
                false,
                TopologyUsed.UseTopology,
                "",
                "",
                60,
                snmpContactDetails,
                FtpLoginDetails.EMPTY,
                EnmLoginDetails.EMPTY,
                "",
                nodeEntrySubnets,
                "",
                "",
                IpTableStatus.UNKNOWN,
                "",
                "",
                LagActive.ISNOTACTIVE
        );
    }
}

