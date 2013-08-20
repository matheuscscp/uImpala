package org.unbiquitous.ubiengine.resources.network;

import org.unbiquitous.uos.core.network.exceptions.NetworkException;
import org.unbiquitous.uos.core.network.model.NetworkDevice;
import org.unbiquitous.uos.network.socket.EthernetDevice;
import org.unbiquitous.uos.network.socket.connectionManager.EthernetTCPConnectionManager;

public class NetworkNO extends EthernetTCPConnectionManager{

  private EthernetDevice serverDevice;
  
  public NetworkNO() throws NetworkException {
    super();
  }
  
  public NetworkDevice getNetworkDevice() {
    if(serverDevice == null){
      return new EthernetDevice("192.168.1.106", 14984, EthernetConnectionType.TCP);
    }
    return serverDevice;
  }

}
