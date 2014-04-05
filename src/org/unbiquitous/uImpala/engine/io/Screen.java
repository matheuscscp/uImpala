package org.unbiquitous.uImpala.engine.io;


/**
 * Class for screen resource.
 * @author Pimenta
 *
 */
public abstract class Screen extends OutputResource {
  protected static interface Factory {
    public Screen create();
  }
  
  protected static Factory factory = null;
  
  /**
   * Constructor.
   * @return Screen created.
   */
  public static synchronized Screen create() {
    Screen tmp = factory.create();
    if (tmp != null)
      tmp.observations.addEvents(EVENT_CLOSE_REQUEST);
    return tmp;
  }
  
  /**
   * Broadcasted when the user requests to close the screen.
   */
  public static final int EVENT_CLOSE_REQUEST = IOResource.LAST_EVENT + 1;
  
  /**
   * The last event of this class.
   */
  public static final int LAST_EVENT          = EVENT_CLOSE_REQUEST;
  
  /**
   * Opens the screen with default graphics implementation.
   * @param t Title.
   * @param w Width.
   * @param h Height.
   * @param f Fullscreen flag.
   * @param i Icon path.
   */
  public void open(String t, int w, int h, boolean f, String i) {
    open(t, w, h, f, i, true);
  }
  
  /**
   * Open the screen.
   * @param t Title.
   * @param w Width.
   * @param h Height.
   * @param f Fullscreen flag.
   * @param i Icon path.
   * @param gl Flag to tell if the default graphics implementation should be initialized.
   */
  public abstract void open(String t, int w, int h, boolean f, String i, boolean gl);
  
  public abstract String getTitle();
  public abstract void setTitle(String title);
  public abstract int getWidth();
  public abstract int getHeight();
  public abstract void setSize(int width, int height);
  public abstract boolean isFullscreen();
  public abstract void setFullscreen(boolean fullscreen);
  public abstract String getIcon();
  public abstract void setIcon(String icon);
  
  public abstract boolean isCloseRequested();
  
  public abstract MouseSource getMouse();
  public abstract KeyboardSource getKeyboard();
}
