package org.unbiquitous.uImpala.engine.core;

import org.unbiquitous.uos.core.InitialProperties;
import org.unbiquitous.uos.network.socket.connectionManager.TCPConnectionManager;
import org.unbiquitous.uos.network.socket.radar.MulticastRadar;

/**
 * Just a "typedef" for
 * <code>HashMap{@literal <}String, Object{@literal >}</code>.
 * @author Pimenta
 *
 */
@SuppressWarnings("serial")
public class GameSettings extends InitialProperties {
  
	public GameSettings() {
		addConnectionManager(TCPConnectionManager.class);
		addRadar(MulticastRadar.class,TCPConnectionManager.class);
		put("ubiquitos.eth.tcp.port", 14984);
		put("ubiquitos.eth.tcp.passivePortRange", "14985-15000");
        put("uImpala.gameSettings", this);
	}
	
}
