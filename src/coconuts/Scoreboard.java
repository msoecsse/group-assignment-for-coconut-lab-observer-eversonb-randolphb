/*
 * Course: CSC2410 - 121
 * Fall 2025
 * Scoreboard
 * Name: Ben Randolph
 * Created: 10/10/2025
 */
package coconuts;
import javafx.scene.control.Label;

/**
 * scoreboard class
 * @author randolphben
 * @version created on 10/10/2025 11:47 AM
 */
public class Scoreboard implements CoconutObserver {
    private final Label coconutKilledLabel;
    private final Label coconutsHitGroundLabel;
    private int killed = 0;
    private int grounded = 0;

    public Scoreboard(Label killedLabel, Label hitGroundLabel){
        this.coconutKilledLabel = killedLabel;
        this.coconutsHitGroundLabel = hitGroundLabel;
    }

    @Override
    public void notifyEvent(HitEvent event) {
        coconutKilledLabel.setText("Coconuts killed: " + killed);
        killed++;
        coconutsHitGroundLabel.setText("Coconuts on ground: " + grounded);
        grounded++;
    }
}
