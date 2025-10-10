package coconuts;

import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

// An abstraction of all objects that can be hit by another object
// This captures the Subject side of the Observer pattern; observers of the hit event will take action
//   to process that event
// This is a domain class; do not introduce JavaFX or other GUI components here
public class HitEvent implements GroundSubject, CrabSubject, LaserSubject {
    private final List<LaserObserver> laserObservers = new ArrayList<>();
    private final List<CrabObserver> crabObservers = new ArrayList<>();
    private final List<GroundObserver> groundObservers = new ArrayList<>();

    @Override
    public void attach(CrabObserver observer) {
        crabObservers.add(observer);
    }

    @Override
    public void detach(CrabObserver observer) {
        crabObservers.remove(observer);
    }

    @Override
    public void notifyCrabObservers() {
        for (CrabObserver observer : crabObservers) {
            observer.notifyCrab();
        }
    }

    @Override
    public void attach(GroundObserver observer) {
        groundObservers.add(observer);
    }

    @Override
    public void detach(GroundObserver observer) {
        groundObservers.remove(observer);
    }

    @Override
    public void notifyGroundObservers() {
        for (GroundObserver observer : groundObservers) {
            observer.notifyGround();
        }
    }

    @Override
    public void attach(LaserObserver observer) {
        laserObservers.add(observer);
    }

    @Override
    public void detach(LaserObserver observer) {
        laserObservers.remove(observer);
    }

    @Override
    public void notifyLaserObservers() {
        for (LaserObserver observer : laserObservers) {
            observer.notifyLaser();
        }
    }
}
