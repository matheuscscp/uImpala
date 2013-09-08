package org.unbiquitous.ubiengine.game;

import java.util.ListResourceBundle;

import org.unbiquitous.ubiengine.resources.input.keyboard.KeyboardReceptionDriver;
import org.unbiquitous.ubiengine.resources.network.NetworkNO;
import org.unbiquitous.uos.core.UOS;
import org.unbiquitous.uos.network.socket.radar.PingRadar;

public final class GameStarter {
  public static void startGame(final String game) {
    new UOS().init(new ListResourceBundle() {
      protected Object[][] getContents() {
        return new Object[][] {
          {"ubiquitos.connectionManager", NetworkNO.class.getName()},
          {"ubiquitos.radar", PingRadar.class.getName()},
          {"ubiquitos.eth.tcp.port", "14984"},
          {"ubiquitos.eth.tcp.passivePortRange", "14985-15000"},
          {"ubiquitos.uos.deviceName","compDevice"},
          {"ubiquitos.driver.deploylist", KeyboardReceptionDriver.class.getName()},
          {"ubiquitos.application.deploylist", game}
        };
      }
    });
  }
}
