package org.unbiquitous.ubiengine.resources.input;

import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

import org.unbiquitous.ubiengine.resources.video.Screen;
import org.unbiquitous.ubiengine.util.SingletonContainer;
import org.unbiquitous.ubiengine.util.observer.Event;
import org.unbiquitous.ubiengine.util.observer.MissingEventType;
import org.unbiquitous.ubiengine.util.observer.Stack;
import org.unbiquitous.ubiengine.util.observer.Subject;
import org.unbiquitous.ubiengine.util.observer.SubjectDevice;


public final class InputManager implements Subject, KeyListener, MouseListener, MouseMotionListener {

  public static final String KEYDOWN      = "KEYDOWN";
  public static final String KEYUP        = "KEYUP";
  public static final String MOUSEDOWN    = "MOUSEDOWN";
  public static final String MOUSEUP      = "MOUSEUP";
  public static final String MOUSEMOTION  = "MOUSEMOTION";
  
  private SubjectDevice subject;
  
  public void connect(String event_type, Method handler)
      throws MissingEventType {
    subject.connect(event_type, handler);
  }

  public void connect(String event_type, Object observer, Method handler)
      throws MissingEventType {
    subject.connect(event_type, observer, handler);
  }

  public void disconnect(Method handler) {
    subject.disconnect(handler);
  }

  public void disconnect(String event_type, Method handler)
      throws MissingEventType {
    subject.disconnect(event_type, handler);
  }

  public void disconnect(Object observer) {
    subject.disconnect(observer);
  }

  public void disconnect(String event_type, Object observer)
      throws MissingEventType {
    subject.disconnect(event_type, observer);
  }

  public static final class KeyEvent extends Event {
    private int unicodeChar;
    
    public KeyEvent(String type, int unicodeChar) {
      super(type);
      this.unicodeChar = unicodeChar;
    }
    
    public int getUnicodeChar() {
      return unicodeChar;
    }
  }

  public static final class MouseEvent extends Event {
    private int button;
    
    public MouseEvent(String type, int button) {
      super(type);
      this.button = button;
    }
    
    public int getButton() {
      return button;
    }
  }
  
  public static final class MouseMotionEvent extends Event {
    private int x, y;
    
    public MouseMotionEvent(String type, int x, int y) {
      super(type);
      this.x = x;
      this.y = y;
    }
    
    public int getX() {
      return x;
    }
    
    public int getY() {
      return y;
    }
  }
  
  private Queue<Event> events = new LinkedList<Event>();
  
  private HashMap<Integer, Boolean> key_pressed = new HashMap<Integer, Boolean>();
  private HashMap<Integer, Boolean> mouse_pressed = new HashMap<Integer, Boolean>();
  private int mouse_x;
  private int mouse_y;

  public InputManager(SingletonContainer singletons) {
    subject = new SubjectDevice(
      singletons.get(Stack.class),
      KEYDOWN,
      KEYUP,
      MOUSEDOWN,
      MOUSEUP,
      MOUSEMOTION
    );
    Screen screen = singletons.get(Screen.class);
    screen.addKeyListener(this);
    screen.addMouseListener(this);
    screen.addMouseMotionListener(this);
  }
  
  public void update() throws Throwable {
    while (!events.isEmpty()) {
      Event event = events.poll();
      if (event instanceof KeyEvent)
        key_pressed.put(Integer.valueOf(((KeyEvent) event).getUnicodeChar()), event.getType().equals(KEYDOWN));
      else if (event instanceof MouseEvent)
        mouse_pressed.put(Integer.valueOf(((MouseEvent) event).getButton()), event.getType().equals(MOUSEDOWN));
      else {
        mouse_x = ((MouseMotionEvent) event).getX();
        mouse_y = ((MouseMotionEvent) event).getY();
      }
      subject.broadcast(event);
    }
  }

  public void forceKeyPressed(int unicodeChar) {
    events.add(new KeyEvent(KEYDOWN, unicodeChar));
  }

  public void forceKeyReleased(int unicodeChar) {
    events.add(new KeyEvent(KEYUP, unicodeChar));
  }

  public void keyPressed(java.awt.event.KeyEvent e) {
    events.add(new KeyEvent(KEYDOWN, e.getKeyCode()));
  }

  public void keyReleased(java.awt.event.KeyEvent e) {
    events.add(new KeyEvent(KEYUP, e.getKeyCode()));
  }

  public void keyTyped(java.awt.event.KeyEvent e) {
    
  }

  public void mouseClicked(java.awt.event.MouseEvent e) {
    
  }

  public void mouseEntered(java.awt.event.MouseEvent e) {
    
  }

  public void mouseExited(java.awt.event.MouseEvent e) {
    
  }

  public void mousePressed(java.awt.event.MouseEvent e) {
    events.add(new MouseEvent(MOUSEDOWN, e.getButton()));
  }

  public void mouseReleased(java.awt.event.MouseEvent e) {
    events.add(new MouseEvent(MOUSEUP, e.getButton()));
  }

  public void mouseDragged(java.awt.event.MouseEvent e) {
    events.add(new MouseMotionEvent(MOUSEMOTION, e.getX(), e.getY()));
  }

  public void mouseMoved(java.awt.event.MouseEvent e) {
    events.add(new MouseMotionEvent(MOUSEMOTION, e.getX(), e.getY()));
  }
  
  public boolean keyPressed(int unicodeChar) {
    Boolean check = key_pressed.get(Integer.valueOf(unicodeChar));
    if (check == null)
      return false;
    return check.booleanValue();
  }
  
  public boolean mousePressed(int button) {
    Boolean check = mouse_pressed.get(Integer.valueOf(button));
    if (check == null)
      return false;
    return check.booleanValue();
  }
  
  public int mouseX() {
    return mouse_x;
  }
  
  public int mouseY() {
    return mouse_y;
  }
}
