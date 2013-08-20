package org.unbiquitous.ubiengine.resources.input;

import java.util.ArrayList;
import java.util.List;

import org.unbiquitous.uos.core.adaptabitilyEngine.Gateway;
import org.unbiquitous.uos.core.applicationManager.CallContext;
import org.unbiquitous.uos.core.driverManager.UosDriver;
import org.unbiquitous.uos.core.messageEngine.dataType.UpDriver;
import org.unbiquitous.uos.core.messageEngine.dataType.UpService.ParameterType;
import org.unbiquitous.uos.core.messageEngine.messages.ServiceCall;
import org.unbiquitous.uos.core.messageEngine.messages.ServiceResponse;

public class KeyboardReceptionDriver implements UosDriver {

  public static final String TRANSMISSION_DRIVER  = "KeyboardTransmissionDriver";
  public static final String RECEPTION_DRIVER     = "KeyboardReceptionDriver";
  
  private KeyboardReceptionDriverManager manager = null;
  
  public UpDriver getDriver() {
    UpDriver driver = new UpDriver(RECEPTION_DRIVER);

    driver.addService("requestAccepted");
    driver.addService("keyboardClosed");
    driver.addService("keyDown").addParameter("unicodeChar", ParameterType.MANDATORY);
    driver.addService("keyUp").addParameter("unicodeChar", ParameterType.MANDATORY);
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

  public void requestAccepted(ServiceCall serviceCall,
      ServiceResponse serviceResponse, CallContext messageContext) {
    if (manager != null)
      manager.requestAccepted();
  }

  public void keyboardClosed(ServiceCall serviceCall,
      ServiceResponse serviceResponse, CallContext messageContext) {
    if (manager != null)
      manager.keyboardClosed();
  }

  public void keyDown(ServiceCall serviceCall,
      ServiceResponse serviceResponse, CallContext messageContext) {
    if (manager != null)
      manager.keyDown(((Integer) serviceCall.getParameter("unicodeChar")).intValue());
  }

  public void keyUp(ServiceCall serviceCall,
      ServiceResponse serviceResponse, CallContext messageContext) {
    if (manager != null)
      manager.keyUp(((Integer) serviceCall.getParameter("unicodeChar")).intValue());
  }

  public void setManager(ServiceCall serviceCall,
      ServiceResponse serviceResponse, CallContext messageContext) {
    if (manager == null)
      manager = (KeyboardReceptionDriverManager) serviceCall.getParameter("manager");
  }
}
