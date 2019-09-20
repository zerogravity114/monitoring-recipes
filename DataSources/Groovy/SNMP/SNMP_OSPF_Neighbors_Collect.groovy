/*******************************************************************************
 *  © 2007-2019 - LogicMonitor, Inc. All rights reserved.
 ******************************************************************************/
import com.santaba.agent.groovyapi.snmp.Snmp

// variable to hold system hostname
def host = hostProps.get('system.hostname')
def props = hostProps.toProperties()
def timeout = 10000 // 10 sec timeout.

// Throw into a try/catch
try {
    /*
    The following SNMP walkAsMap will handle v1 , v2 and v3. 
    Props contains a map of ALL host properties and the SNMP walk method will automatically
    handle the proper connection based on which SNMP version is configured.
    */

    // define maps we will walk.
    def NameWalkAsMap = Snmp.walkAsMap(host, ".1.3.6.1.2.1.14.10.1.1", props, timeout)
    def peerStateMap = Snmp.walkAsMap(host, ".1.3.6.1.2.1.14.10.1.6", props, timeout)
	def neighborEventsMap = Snmp.walkAsMap(host, ".1.3.6.1.2.1.14.10.1.7", props, timeout)

    NameWalkAsMap.each
            { key, val ->

				println NameWalkAsMap[key] + ".PeerState=" + peerStateMap[key]
				println NameWalkAsMap[key] + ".NeighborEvents=" + neighborEventsMap[key]

            }

    // execution was successful, return 0;
    return 0
}

catch (Exception e) {
    // if exception is caught, print it out and return 1;
    println e
    return 1
}
