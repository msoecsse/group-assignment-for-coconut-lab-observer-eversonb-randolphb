/*
 * Course: CSC2410 - 121
 * Fall 2025
 * CrabSubject
 * Name: Ben Randolph
 * Created: 10/10/2025
 */

package coconuts;

/**
 * Crab
 * @author randolphben
 * @version created on 10/10/2025 11:47 AM
 */
public interface CrabSubject {
    void attach(CrabObserver observer);
    void detach(CrabObserver observer);
    void notifyCrabObservers();
}
