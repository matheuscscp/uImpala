package org.unbiquitous.ubiengine.resources.network;

import org.unbiquitous.uos.core.network.exceptions.NetworkException;
import org.unbiquitous.uos.core.network.model.NetworkDevice;
import org.unbiquitous.uos.network.socket.SocketDevice;
import org.unbiquitous.uos.network.socket.connectionManager.TCPConnectionManager;

public class NetworkNO extends TCPConnectionManager{

  private SocketDevice serverDevice;
  
  public NetworkNO() throws NetworkException {
    super();
  }
  
  public NetworkDevice getNetworkDevice() {
    if(serverDevice == null){
      return new SocketDevice("192.168.1.110", 14984, EthernetConnectionType.TCP);
    }
    return serverDevice;
  }

}
