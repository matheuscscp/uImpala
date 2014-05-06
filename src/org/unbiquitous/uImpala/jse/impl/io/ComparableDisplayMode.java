package org.unbiquitous.uImpala.jse.impl.io;

import org.lwjgl.opengl.DisplayMode;

class ComparableDisplayMode implements Comparable<ComparableDisplayMode> {
  public DisplayMode displayMode;
  public float error;
  public boolean sameAspect;
  
  public ComparableDisplayMode(DisplayMode dm, int width, int height) {
    float areaRatio        = ((float)(dm.getWidth()*dm.getHeight()))/(width*height);
    float aspectRatio      = ((float)width)/height;
    float aspectRatioDM    = ((float)dm.getWidth())/dm.getHeight();
    float errorWidth       = getError(width, dm.getWidth());
    float errorHeight      = getError(height, dm.getHeight());
    float errorArea        = getError(1, areaRatio < 1 ? 1/areaRatio : areaRatio);
    float errorAspectRatio = getError(aspectRatio, aspectRatioDM);
    displayMode            = dm;
    error                  = errorWidth + errorHeight + errorArea + errorAspectRatio;
    sameAspect             = (aspectRatio > 1 && aspectRatioDM > 1) || (aspectRatio < 1 && aspectRatioDM < 1);
  }
  
  public int compareTo(ComparableDisplayMode other) {
    if (displayMode.getBitsPerPixel() == other.displayMode.getBitsPerPixel()) {
      if (displayMode.getFrequency() == other.displayMode.getFrequency()) {
        if (sameAspect == other.sameAspect)
          return error < other.error ? -1 : 1;
        return sameAspect ? -1 : 1;
      }
      return displayMode.getFrequency() > other.displayMode.getFrequency() ? -1 : 1;
    }
    return displayMode.getBitsPerPixel() > other.displayMode.getBitsPerPixel() ? -1 : 1;
  }
  
  private static float getError(float value, float other) {
    return Math.abs(other - value)/value;
  }
}
