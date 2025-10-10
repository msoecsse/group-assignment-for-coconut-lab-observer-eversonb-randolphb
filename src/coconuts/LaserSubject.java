/*
 * Course: CSC2410 - 121
 * Fall 2025
 * LaserSubject
 * Name: Ben Randolph
 * Created: 10/10/2025
 */

package coconuts;

/**
 * Laser Subject
 * @author randolphben
 * @version created on 10/10/2025 11:47 AM
 */
public interface LaserSubject {
    void attach(LaserObserver observer);
    void detach(LaserObserver observer);
    void notifyLaserObservers();
}
