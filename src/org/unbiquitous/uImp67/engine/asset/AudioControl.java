package org.unbiquitous.uImp67.engine.asset;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.openal.AL10;
import org.newdawn.slick.openal.OggInputStream;
import org.unbiquitous.uImp67.engine.io.Speaker;

/**
 * Class to handle audio playback.
 * @author Pimenta
 *
 */
public class AudioControl {
  /**
   * Query playback state.
   * @return Returns a value in {Audio.PLAYING, Audio.PAUSED, Audio.STOPPED}.
   */
  public synchronized int state() {
    if (closed)
      return Audio.STOPPED;
    return AL10.alGetSourcei(source, AL10.AL_SOURCE_STATE);
  }
  
  /**
   * Pause playback.
   */
  public synchronized void pause() {
    if (!closed && state() == Audio.PLAYING)
      AL10.alSourcePause(source);
  }
  
  /**
   * Resume playback.
   */
  public synchronized void resume() {
    if (!closed && state() == Audio.PAUSED)
      AL10.alSourcePlay(source);
  }
  
  /**
   * Stop playback.
   */
  public synchronized void stop() {
    if (!closed)
      AL10.alSourceStop(source);
  }
  
  /**
   * Set playback volume.
   * @param volume A number in [0, 1].
   */
  public synchronized void volume(float volume) {
    if (!closed)
      AL10.alSourcef(source, AL10.AL_GAIN, volume*speaker.getVolume());
  }
//==============================================================================
//nothings else matters from here to below
//==============================================================================
  /**
   * Engine's private use.
   */
  protected AudioControl(Speaker speaker, Audio audio, float volume, boolean loop) {
    // init fields, generate source and buffers
    this.speaker = speaker;
    this.audio = audio;
    stream = audio.stream();
    source = AL10.alGenSources();
    format = stream.getChannels() > 1 ? AL10.AL_FORMAT_STEREO16 : AL10.AL_FORMAT_MONO16;
    freq = stream.getRate();
    nextBuffer = 0;
    buffers = BufferUtils.createIntBuffer(BUFFER_COUNT);
    AL10.alGenBuffers(buffers);
    this.loop = loop;
    done = false;
    closed = false;
    
    // load chunks
    for (int i = 0; i < BUFFER_COUNT; i++) {
      int buf = buffers.get(i);
      
      // fill buffer
      try {
        AL10.alBufferData(buf, format, read(), freq);
      } catch (IOException e) {
        if (i == 0)
          throw new Error(e);
        
        // if no loop then done
        if (!loop) {
          done = true;
          break;
        }
        
        // reopen stream
        try {
          stream.close();
        } catch (IOException e1) {
        }
        stream = audio.stream();
        
        try {
          AL10.alBufferData(buf, format, read(), freq);
        } catch (IOException e1) {
          throw new Error(e1);
        }
      }
    }
    
    // set volume, queue buffers and play
    AL10.alSourcef(source, AL10.AL_GAIN, volume*speaker.getVolume());
    AL10.alSourceQueueBuffers(source, buffers);
    AL10.alSourcePlay(source);
    
    // update thread
    new Thread(new Runnable() {
      public void run() {
        while (state() != Audio.STOPPED) {
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
  
  private synchronized void close() {
    closed = true;
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
    if (count <= 0)
      throw new IOException();
    ByteBuffer buf = BufferUtils.createByteBuffer(count);
    buf.put(tmp, 0, count);
    buf.flip();
    return buf;
  }
  
  private void update() {
    if (done)
      return;
    
    // unqueue processed buffers
    int processed = AL10.alGetSourcei(source, AL10.AL_BUFFERS_PROCESSED);
    if (processed == 0)
      return;
    AL10.alSourceUnqueueBuffers(source, BufferUtils.createIntBuffer(processed));
    
    // queue more chunks
    for (int queue = BUFFER_COUNT - AL10.alGetSourcei(source, AL10.AL_BUFFERS_QUEUED); queue > 0; queue--) {
      int buf = buffers.get(nextBuffer);
      
      // fill buffer
      try {
        AL10.alBufferData(buf, format, read(), freq);
      } catch (IOException e) {
        // if no loop then done
        if (!loop) {
          done = true;
          return;
        }
        
        // reopen stream
        try {
          stream.close();
        } catch (IOException e1) {
        }
        stream = audio.stream();
        
        try {
          AL10.alBufferData(buf, format, read(), freq);
        } catch (IOException e1) {
          throw new Error(e1);
        }
      }
      
      AL10.alSourceQueueBuffers(source, buf);
      
      nextBuffer = (nextBuffer + 1)%3;
    }
  }
  
  private static final int BUFFER_COUNT = 3;
  private static final int SECTION_SIZE = 4096*20;
  private static final long UPDATE_PERIOD = 30;
  
  private Speaker speaker;
  private Audio audio;
  private OggInputStream stream;
  private int source, format, freq, nextBuffer;
  private IntBuffer buffers;
  private boolean loop, done, closed;
}
