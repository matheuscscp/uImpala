package org.unbiquitous.uImpala.engine.core;

/**
 * Use this class to implement components for the component-based
 * game object class.
 * @see ComponentGameObject
 * @author Pimenta
 *
 */
public abstract class GameObjectComponent {
  protected ComponentGameObject object;
  
  /**
   * Method to hook on a field. Every time the field is written, handle will
   * be called.
   * @param field Field name.
   */
  protected void hook(String field) {
    object.hook(field, family());
  }
  
  /**
   * Method to unhook on a field. The handle method will stop being called
   * every time the field is written.
   * @param field Field name.
   */
  protected void unhook(String field) {
    object.unhook(field, family());
  }
  
  /**
   * Method to add a rendering operation to the container.
   * @param z Plane of renderization. The renderization will happen
   * in ascending order.
   * @param renderer Renderer to be called.
   */
  protected void render(int z, Runnable renderer) {
    object.putRenderer(z, renderer);
  }
  
  /**
   * Method to tell the component family. Implement this method in subclasses
   * to tell the component family.
   * @return Component family name.
   */
  protected abstract String family();
  
  /**
   * Method to tell the components' families on which this component depends.
   * Implement this method in subclasses to tell the components on which
   * your will depend.
   * @return Components' families.
   */
  protected String[] depends() {
    return new String[0];
  }
  
  /**
   * Method to initialize shared fields of the parent game object.
   */
  protected abstract void init();
  
  /**
   * Method to implement update.
   */
  protected abstract void update();
  
  /**
   * Handle a pop from the stack of game scenes.
   * @param args Arguments passed from the scene popped.
   */
  protected abstract void wakeup(Object... args);
  
  /**
   * Method to close whatever is necessary.
   */
  protected abstract void destroy();
  
  /**
   * Method to handle a field write.
   * @param field Field name.
   * @param value Field value.
   */
  protected abstract void handle(String field, Object value);
}
