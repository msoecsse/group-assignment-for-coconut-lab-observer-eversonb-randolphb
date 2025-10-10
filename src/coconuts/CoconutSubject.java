
/*
 * Course: SWE2410-121
 * Fall 2025-2026
 * File header contains class CoconutSubject
 * Name: eversonb
 * Created 10/10/2025
 */
package coconuts;

/**
 * Course SWE2410-121
 * Fall 2025-2026
 * Interface CoconutSubject Purpose: Provide a subject for coconut hit events
 *
 * @author eversonb
 * @version created on 10/10/2025 11:44 AM
 */
public interface CoconutSubject {
    void attach(CoconutObserver observer);
    void detach(CoconutObserver observer);
    void notifyObservers(HitEvent event);
}
