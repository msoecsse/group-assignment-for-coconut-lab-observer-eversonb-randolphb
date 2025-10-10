
/*
 * Course: SWE2410-121
 * Fall 2025-2026
 * File header contains class CoconutObserver
 * Name: eversonb
 * Created 10/10/2025
 */
package coconuts;

/**
 * Course SWE2410-121
 * Fall 2025-2026
 * Interface CoconutObserver Purpose: Create an observer for the coconut hit events
 *
 * @author eversonb
 * @version created on 10/10/2025 11:40 AM
 */
public interface CoconutObserver {
    void notifyEvent(HitEvent event);
}
