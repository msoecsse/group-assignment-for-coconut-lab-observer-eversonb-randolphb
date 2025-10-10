/*
 * Course: CSC2410 - 121
 * Fall 2025
 * GroundSubject
 * Name: Ben Randolph
 * Created: 10/10/2025
 */

package coconuts;

/**
 * Laser Subject
 * @author randolphben
 * @version created on 10/10/2025 11:47 AM
 */
public interface GroundSubject {
    void attach(GroundObserver observer);
    void detach(GroundObserver observer);
    void notifyGroundObservers();
}
