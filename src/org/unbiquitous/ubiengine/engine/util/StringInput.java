package org.unbiquitous.ubiengine.engine.util;

import org.unbiquitous.ubiengine.engine.core.GameObject;
import org.unbiquitous.ubiengine.engine.core.RendererContainer;
import org.unbiquitous.ubiengine.engine.io.KeyboardSource;

public class StringInput extends GameObject {
  public StringInput(KeyboardSource keyb) {
    keyboard = keyb;
  }
  
  protected void update() {
    // TODO Auto-generated method stub
    
  }
  
  protected void render(RendererContainer renderers) {
    
  }
  
  protected void wakeup(Object... args) {
    // TODO Auto-generated method stub
    
  }
  
  protected void destroy() {
    
  }
  
  private KeyboardSource keyboard;
}
