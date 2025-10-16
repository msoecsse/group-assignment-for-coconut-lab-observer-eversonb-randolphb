package coconuts;

// https://stackoverflow.com/questions/42443148/how-to-correctly-separate-view-from-model-in-javafx

import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

import java.util.Collection;
import java.util.LinkedList;

// This class manages the game, including tracking all island objects and detecting when they hit
public class OhCoconutsGameManager{
    private final Collection<IslandObject> allObjects = new LinkedList<>();
    private final Collection<HittableIslandObject> hittableIslandSubjects = new LinkedList<>();
    private final Collection<IslandObject> scheduledForRemoval = new LinkedList<>();
    HitEvent hitEvent = new HitEvent();
    private Scoreboard scoreboard;

    private final int height, width;
    private final int DROP_INTERVAL = 25;
    private final int MAX_TIME = 100;
    private Pane gamePane;
    private Crab theCrab;
    private Beach theBeach;
    /* game play */
    private int coconutsInFlight = 0;
    private int gameTick = 0;

    public OhCoconutsGameManager(int height, int width, Pane gamePane, Label killedLabel, Label groundedLabel) {
        this.height = height;
        this.width = width;
        this.gamePane = gamePane;

        this.theCrab = new Crab(this, height, width);
        registerObject(theCrab);
        gamePane.getChildren().add(theCrab.getImageView());

        this.theBeach = new Beach(this, height, width);
        registerObject(theBeach);
        if (theBeach.getImageView() != null)
            System.out.println("Unexpected image view for beach");

        this.scoreboard = new Scoreboard(killedLabel, groundedLabel);
        hitEvent.attach((CrabObserver) scoreboard);
        hitEvent.attach((GroundObserver) scoreboard);
        hitEvent.attach((LaserObserver) scoreboard);
    }

    private void registerObject(IslandObject object) {
        allObjects.add(object);
        if (object.isHittable()) {
            HittableIslandObject asHittable = (HittableIslandObject) object;
            hittableIslandSubjects.add(asHittable);
        }
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public void coconutDestroyed() {
        coconutsInFlight -= 1;
    }

    public void tryDropCoconut() {
        if (gameTick % DROP_INTERVAL == 0 && theCrab != null) {
            coconutsInFlight += 1;
            Coconut c = new Coconut(this, (int) (Math.random() * width));
            registerObject(c);
            gamePane.getChildren().add(c.getImageView());
        }
        gameTick++;
    }

    public Crab getCrab() {
        return theCrab;
    }

    public void killCrab() {
        hitEvent.detach((CrabObserver) scoreboard);
        hitEvent.detach((GroundObserver) scoreboard);
        hitEvent.detach((LaserObserver) scoreboard);
        theCrab = null;
    }

    public void advanceOneTick() {
        for (IslandObject o : allObjects) {
            o.step();
            o.display();
        }

        scheduledForRemoval.clear();
        IslandObject prevObj = null;
        for (IslandObject thisObj : allObjects) {
            for (HittableIslandObject hittableObject : hittableIslandSubjects) {
                if (thisObj.canHit(hittableObject) && thisObj.isTouching(hittableObject)) {
                    Coconut nut = new Coconut(this, 1);
                    LaserBeam laser = new LaserBeam(this, 1, 1);
                    if (thisObj.getClass().equals(laser.getClass()) && hittableObject.getClass().equals(nut.getClass())) {
                        // coconut shot
                        if (!thisObj.equals(prevObj) && !thisObj.isDead){
                            hitEvent.notifyLaserObservers();

                            scheduledForRemoval.add(thisObj);
                            gamePane.getChildren().remove(thisObj.getImageView());
                            hittableObject.isDead = true;
                        }
                        prevObj = thisObj;

                    } else if (thisObj.getClass().equals(theBeach.getClass())){
                        // Coconut hit ground
                        hitEvent.notifyGroundObservers();
                    } else if (theCrab != null && hittableObject.getClass().equals(theCrab.getClass())){
                        // crab died
                        hitEvent.notifyCrabObservers();
                    }

                    scheduledForRemoval.add(hittableObject);
                    gamePane.getChildren().remove(hittableObject.getImageView());
                    hittableObject.isDead = true;
                }
            }
        }
        // actually remove the objects as needed
        for (IslandObject thisObj : scheduledForRemoval) {
            allObjects.remove(thisObj);
            if (thisObj instanceof HittableIslandObject) {
                hittableIslandSubjects.remove((HittableIslandObject) thisObj);
            }
            if (theCrab != null && thisObj.getClass().equals(theCrab.getClass())){
                killCrab();
            }
        }
        scheduledForRemoval.clear();
    }

    public void scheduleForDeletion(IslandObject islandObject) {
        scheduledForRemoval.add(islandObject);
    }

    public boolean done() {
        return coconutsInFlight == 0 && gameTick >= MAX_TIME;
    }

    public void shootLaser() {
        if (theCrab != null) {
            LaserBeam thisObj = new LaserBeam(this, (int) theCrab.getImageView().getLayoutY(), theCrab.x);
            gamePane.getChildren().add(thisObj.getImageView());
            registerObject(thisObj);
        }
    }
}
