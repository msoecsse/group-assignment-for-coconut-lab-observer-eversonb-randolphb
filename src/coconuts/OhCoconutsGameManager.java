package coconuts;

// https://stackoverflow.com/questions/42443148/how-to-correctly-separate-view-from-model-in-javafx

import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

import java.util.Collection;
import java.util.LinkedList;

/**
 * Manages the game, including tracing all island objects and detecting when they hit
 * Modified by: eversonb and randolphb
 * Date modified: 10/17/2025
 */
public class OhCoconutsGameManager{
    private final Collection<IslandObject> allObjects = new LinkedList<>();
    private final Collection<HittableIslandObject> hittableIslandSubjects = new LinkedList<>();
    private final Collection<IslandObject> scheduledForRemoval = new LinkedList<>();
    HitEvent hitEvent = new HitEvent(this);
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

        hitEvent.checkForHits(allObjects, hittableIslandSubjects);

        // actually remove the objects as needed
        for (IslandObject thisObj : scheduledForRemoval) {
            allObjects.remove(thisObj);
            if (thisObj.isHittable()) {
                hittableIslandSubjects.remove((HittableIslandObject) thisObj);
            }
            if (thisObj.isCrab()){
                killCrab();
            }
        }
        scheduledForRemoval.clear();
    }

    public void scheduleForDeletion(IslandObject islandObject) {
        scheduledForRemoval.add(islandObject);
        gamePane.getChildren().remove(islandObject.getImageView());
        islandObject.isDead = true;
    }

    public boolean done() {
        return coconutsInFlight == 0 && gameTick >= MAX_TIME;
    }

    public void shootLaser() {
        LaserBeam thisObj = new LaserBeam(this, (int) theCrab.getImageView().getLayoutY(), theCrab.x);
        gamePane.getChildren().add(thisObj.getImageView());
        registerObject(thisObj);
    }
}
