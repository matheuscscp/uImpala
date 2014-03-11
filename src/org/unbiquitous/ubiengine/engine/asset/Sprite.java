package org.unbiquitous.ubiengine.engine.asset;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;
import org.newdawn.slick.opengl.Texture;
import org.unbiquitous.ubiengine.engine.io.Screen;

public class Sprite {
  private Texture texture;
  
  public Sprite(AssetManager assets, String path) {
    texture = assets.getTexture(path);
  }
  
  public void render(Screen screen, float x, float y) {
    Color.white.bind();
    texture.bind();
    
    GL11.glBegin(GL11.GL_QUADS);
      GL11.glTexCoord2f(0,0);
      GL11.glVertex2f(x, y);
      GL11.glTexCoord2f(1,0);
      GL11.glVertex2f(x + texture.getTextureWidth(), y);
      GL11.glTexCoord2f(1,1);
      GL11.glVertex2f(x + texture.getTextureWidth(), y + texture.getTextureHeight());
      GL11.glTexCoord2f(0,1);
      GL11.glVertex2f(x, y + texture.getTextureHeight());
    GL11.glEnd();
  }
}
