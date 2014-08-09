package org.unbiquitous.uImpala.engine.io;

import java.util.HashSet;

import org.unbiquitous.uImpala.util.observer.Observation;

/**
 * Class for mouses management.
 * 
 * @author Pimenta
 *
 */
public class MouseManager implements InputManager {
	public IOResource alloc() {
		return null;
	}

	public boolean free(IOResource rsc) {
		return false;
	}

	public void update() {
		for (MouseSource ms : screenMouses)
			ms.update();
	}

	public void close() {
		for (MouseSource ms : screenMouses)
			ms.close();
	}

	public void connect(Integer eventType, Observation obs) {
		for (MouseSource ms : screenMouses) {
			ms.connect(eventType, obs);
		}
	}

	/**
	 * Engine's private use.
	 */
	public void add(MouseSource ms) {
		screenMouses.add(ms);
	}

	/**
	 * Engine's private use.
	 */
	public void remove(MouseSource ms) {
		screenMouses.remove(ms);
	}

	/**
	 * Engine's private use.
	 */
	protected HashSet<MouseSource> screenMouses = new HashSet<MouseSource>();
}
