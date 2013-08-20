package org.unbiquitous.ubiengine.game;

import java.lang.Object;
import java.util.ListResourceBundle;

import org.unbiquitous.ubiengine.resources.input.KeyboardReceptionDriver;
import org.unbiquitous.ubiengine.resources.network.NetworkNO;
import org.unbiquitous.uos.core.UOS;
import org.unbiquitous.uos.network.socket.radar.EthernetPingRadar;

public final class Starter {
  public static void startGame(final String game) {
    try {
      new UOS().init(new ListResourceBundle() {
        protected Object[][] getContents() {
          return new Object[][] {
            {"ubiquitos.connectionManager", NetworkNO.class.getName()},
            {"ubiquitos.radar", EthernetPingRadar.class.getName()},
            {"ubiquitos.eth.tcp.port", "14984"},
            {"ubiquitos.eth.tcp.passivePortRange", "14985-15000"},
            {"ubiquitos.uos.deviceName","compDevice"},
            {"ubiquitos.driver.deploylist", KeyboardReceptionDriver.class.getName()},
            {"ubiquitos.application.deploylist", game}
          };
        }
      });
    }
    catch (Throwable e) {
      org.unbiquitous.ubiengine.util.Logger.log(new Error(e));
    }
  }
}
