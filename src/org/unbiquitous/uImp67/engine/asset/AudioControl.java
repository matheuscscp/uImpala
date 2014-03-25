package org.unbiquitous.uImp67.engine.asset;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.openal.AL10;
import org.newdawn.slick.openal.OggInputStream;
import org.unbiquitous.uImp67.engine.io.Speaker;

public class AudioControl {
  protected AudioControl(Speaker speaker, OggInputStream stream, float volume, boolean loop) {
    // init fields, generate source and buffers
    this.stream = stream;
    source = AL10.alGenSources();
    format = stream.getChannels() > 1 ? AL10.AL_FORMAT_STEREO16 : AL10.AL_FORMAT_MONO16;
    freq = stream.getRate();
    buffers = BufferUtils.createIntBuffer(BUFFER_COUNT);
    AL10.alGenBuffers(buffers);
    stopped = false;
    nextBuffer = 0;
    
    // fill buffers
    for (int i = 0; i < BUFFER_COUNT; i++) {
      try {
        AL10.alBufferData(buffers.get(i), format, read(), freq);
      } catch (IOException e) {
        if (i > 0)
          break;
        else {
          close();
          return;
        }
      }
    }
    
    // set source parameters
    AL10.alSourcef(source, AL10.AL_GAIN, volume);
    AL10.alSourcei(source, AL10.AL_LOOPING, loop ? AL10.AL_TRUE : AL10.AL_FALSE);
    
    AL10.alSourceQueueBuffers(source, buffers);
    
    AL10.alSourcePlay(source);
    
    // starting update thread
    new Thread(new Runnable() {
      public void run() {
        while (!stopped) {
          // sleep
          try {
            Thread.sleep(UPDATE_PERIOD);
          } catch (InterruptedException e) {
          }
          
          update();
        }
        close();
      }
    }).start();
  }
  
  public synchronized boolean isPlaying() {
    if (stopped)
      return false;
    return AL10.alGetSourcei(source, AL10.AL_SOURCE_STATE) == AL10.AL_PLAYING;
  }
  
  public synchronized void pause() {
    if (!stopped)
      AL10.alSourcePause(source);
  }
  
  public synchronized void resume() {
    if (!stopped)
      AL10.alSourcePlay(source);
  }
  
  public synchronized void stop() {
    if (stopped)
      return;
    stopped = true;
    AL10.alSourceStop(source);
  }
  
  public synchronized void volume(float volume) {
    if (!stopped)
      AL10.alSourcef(source, AL10.AL_GAIN, volume);
  }
//==============================================================================
//nothings else matters from here to below
//==============================================================================
  private void close() {
    try {
      stream.close();
    } catch (IOException e) {
    }
    AL10.alDeleteBuffers(buffers);
    AL10.alDeleteSources(source);
  }
  
  private ByteBuffer read() throws IOException {
    byte[] tmp = new byte[SECTION_SIZE];
    int count = stream.read(tmp);
    ByteBuffer buf = BufferUtils.createByteBuffer(count);
    buf.put(tmp, 0, count);
    buf.flip();
    return buf;
  }
  
  private synchronized void update() {
    if (!isPlaying())
      stopped = true;
  }
  
  private static final int BUFFER_COUNT = 3;
  private static final int SECTION_SIZE = 4096*20;
  private static final long UPDATE_PERIOD = 30;
  
  private OggInputStream stream;
  private int source, format, freq;
  private IntBuffer buffers;
  private boolean stopped;
  private int nextBuffer;
}

//void update() {
//  int sampleRate = audio.getRate();
//  int sampleSize = audio.getChannels() > 1 ? AL10.AL_FORMAT_STEREO16 : AL10.AL_FORMAT_MONO16;
//  
//  int processed = AL10.alGetSourcei(source, AL10.AL_BUFFERS_PROCESSED);
//  
//  if (processed > 0)
//    AL10.alSourceUnqueueBuffers(source, BufferUtils.createIntBuffer(processed));
//  
//  int queue = BUFFER_COUNT - AL10.alGetSourcei(source, AL10.AL_BUFFERS_QUEUED);
//  
//  for (; queue > 0; --queue) {
//    int count = audio.readBlock(buffer);
//    
//    if (count <= 0)
//    {
//      // TODO: setar done e tratar loop
//    }
//
//    bufferData.clear();
//    bufferData.put(buffer, 0, count);
//    bufferData.flip();
//    
//    int buf = buffers.get(nextBuffer);
//
//    int format = audio.getChannels() > 1 ? AL10.AL_FORMAT_STEREO16 : AL10.AL_FORMAT_MONO16;
//    AL10.alBufferData(buf, format, bufferData, audio.getRate());
//    AL10.alSourceQueueBuffers(source, buf);
//    
//    nextBuffer = (nextBuffer + 1) % 3;
//  }
//}
