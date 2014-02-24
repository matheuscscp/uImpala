package org.unbiquitous.ubiengine.engine.system.input.mouse;

import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import org.unbiquitous.ubiengine.engine.system.Screen;
import org.unbiquitous.ubiengine.engine.system.input.InputSource;
import org.unbiquitous.ubiengine.engine.system.input.InputManager;
import org.unbiquitous.ubiengine.util.ComponentContainer;

public final class MouseManager extends InputManager implements MouseListener, MouseMotionListener {

  private MouseDevice main_mouse;
  
  public MouseManager(ComponentContainer components) {
    main_mouse = new MouseDevice();
    main_mouse.active(true);
    Screen screen = components.get(Screen.class);
    screen.addMouseListener(this);
    screen.addMouseMotionListener(this);
  }

  public MouseDevice getMainMouse() {
    return main_mouse;
  }
  
  public void update() {
    main_mouse.update();
  }

  public void mouseClicked(java.awt.event.MouseEvent e) {
    
  }

  public void mouseEntered(java.awt.event.MouseEvent e) {
    
  }

  public void mouseExited(java.awt.event.MouseEvent e) {
    
  }

  public void mousePressed(java.awt.event.MouseEvent e) {
    main_mouse.forceMousePressed(e.getButton());
  }

  public void mouseReleased(java.awt.event.MouseEvent e) {
    main_mouse.forceMouseReleased(e.getButton());
  }

  public void mouseDragged(java.awt.event.MouseEvent e) {
    main_mouse.forceMouseMotion(e.getX(), e.getY());
  }

  public void mouseMoved(java.awt.event.MouseEvent e) {
    main_mouse.forceMouseMotion(e.getX(), e.getY());
  }

  public void externalRequestAccepted(String transmitter_device) {
    
  }
  
  public void externalDeviceClosed(String transmitter_device) {
    
  }
  
  public void sendRequest(InputSource input_device) {
    
  }
}
