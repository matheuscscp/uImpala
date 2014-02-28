package org.unbiquitous.ubiengine.engine.system.io;

import java.util.ArrayList;
import java.util.List;

import org.unbiquitous.uos.core.adaptabitilyEngine.Gateway;
import org.unbiquitous.uos.core.applicationManager.CallContext;
import org.unbiquitous.uos.core.driverManager.UosDriver;
import org.unbiquitous.uos.core.messageEngine.dataType.UpDriver;
import org.unbiquitous.uos.core.messageEngine.dataType.UpService.ParameterType;
import org.unbiquitous.uos.core.messageEngine.messages.Call;
import org.unbiquitous.uos.core.messageEngine.messages.Response;

class KeyboardReceptionDriver implements UosDriver {

  public static final String TRANSMISSION_DRIVER  = "KeyboardTransmissionDriver";
  public static final String RECEPTION_DRIVER     = "KeyboardReceptionDriver";
  
  private KeyboardReceptionDriverManager manager = null;
  
  public UpDriver getDriver() {
    UpDriver driver = new UpDriver(RECEPTION_DRIVER);

    driver.addService("requestAccepted")
    .addParameter("transmitter_device", ParameterType.MANDATORY);
    driver.addService("keyboardClosed")
    .addParameter("transmitter_device", ParameterType.MANDATORY);
    driver.addService("keyDown")
    .addParameter("transmitter_device", ParameterType.MANDATORY)
    .addParameter("unicode_char", ParameterType.MANDATORY);
    driver.addService("keyUp")
    .addParameter("transmitter_device", ParameterType.MANDATORY)
    .addParameter("unicode_char", ParameterType.MANDATORY);
    driver.addService("setManager").addParameter("manager", ParameterType.MANDATORY);
    
    return driver;
  }

  public List<UpDriver> getParent() {
    return new ArrayList<UpDriver>();
  }

  public void init(Gateway gateway, String instanceId) {
    
  }

  public void destroy() {
    
  }

  public void requestAccepted(Call serviceCall,
      Response serviceResponse, CallContext messageContext) {
    if (manager != null)
      manager.requestAccepted(serviceCall.getParameterString("transmitter_device"));
  }

  public void keyboardClosed(Call serviceCall,
      Response serviceResponse, CallContext messageContext) {
    if (manager != null)
      manager.keyboardClosed(serviceCall.getParameterString("transmitter_device"));
  }

  public void keyDown(Call serviceCall,
      Response serviceResponse, CallContext messageContext) {
    if (manager != null)
      manager.keyDown(
        serviceCall.getParameterString("transmitter_device"),
        ((Integer) serviceCall.getParameter("unicode_char")).intValue()
      );
  }

  public void keyUp(Call serviceCall,
      Response serviceResponse, CallContext messageContext) {
    if (manager != null)
      manager.keyUp(
        serviceCall.getParameterString("transmitter_device"),
        ((Integer) serviceCall.getParameter("unicode_char")).intValue()
      );
  }

  public void setManager(Call serviceCall,
      Response serviceResponse, CallContext messageContext) {
    if (manager == null)
      manager = (KeyboardReceptionDriverManager) serviceCall.getParameter("manager");
  }
}
