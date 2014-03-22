package org.unbiquitous.uImp67.engine.asset;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.openal.AL10;
import org.unbiquitous.uImp67.engine.io.Speaker;

/**
 * Class to handle audio execution.
 * @author Lucas Carvalho
 *
 */
public class AudioControl {
  private Audio audio;
  private int source;
  
  /** The number of buffers to maintain */
  public static final int BUFFER_COUNT = 3;
  /** The size of the sections to stream from the stream */
  private static final int sectionSize = 4096 * 20;
  /** The buffer read from the data stream */
  private byte[] buffer = new byte[sectionSize];
  /** Holds the OpenAL buffer names */
  private IntBuffer buffers;
  
  private int nextBuffer = 0;
  private ByteBuffer bufferData = BufferUtils.createByteBuffer(sectionSize);
  
  private boolean done = false;
  
  public AudioControl(Audio audio, Speaker speaker) {
    this.audio = audio;
    
    // generate source
    this.source = AL10.alGenSources(); // TODO: tem que deletar isso alguma hora
    
    // generate buffers TODO: tem que deletar isso alguma hora
    buffers = BufferUtils.createIntBuffer(BUFFER_COUNT);
    AL10.alGenBuffers(buffers);
  }
  
  // TODO: chamar periodicamente
  void update() {
    int sampleRate = audio.getRate();
    int sampleSize = audio.getChannels() > 1 ? AL10.AL_FORMAT_STEREO16 : AL10.AL_FORMAT_MONO16;
    
    int processed = AL10.alGetSourcei(source, AL10.AL_BUFFERS_PROCESSED);
    
    if (processed > 0)
      AL10.alSourceUnqueueBuffers(source, BufferUtils.createIntBuffer(processed));
    
    int queue = BUFFER_COUNT - AL10.alGetSourcei(source, AL10.AL_BUFFERS_QUEUED);
    
    for (; queue > 0; --queue) {
      int count = audio.ReadBlock(buffer);
      
      if (count <= 0)
      {
        // TODO: setar done e tratar loop
      }

      bufferData.clear();
      bufferData.put(buffer, 0, count);
      bufferData.flip();
      
      int buf = buffers.get(nextBuffer);

      int format = audio.getChannels() > 1 ? AL10.AL_FORMAT_STEREO16 : AL10.AL_FORMAT_MONO16;
      AL10.alBufferData(buf, format, bufferData, audio.getRate());
      AL10.alSourceQueueBuffers(source, buf);
      
      nextBuffer = (nextBuffer + 1) % 3;
    }
  }
  
  public void Play(boolean loop) {
  }
  
  public void Stop() {
  }
  
  public void Pause() {
  }
}
