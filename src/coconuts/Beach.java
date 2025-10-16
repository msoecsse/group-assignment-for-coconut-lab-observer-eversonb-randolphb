package coconuts;

// the beach catches (hits) coconuts and increases the coconut score
// This is a domain class; do not introduce JavaFX or other GUI components here
public class Beach extends HittableIslandObject {

    public Beach(OhCoconutsGameManager game, int skyHeight, int islandWidth) {
        super(game, 0, skyHeight, islandWidth, null);
    }

    @Override
    public boolean canHit(IslandObject other) {
        return other.isFalling();
    }

    @Override
    public boolean isGroundObject() {
        return true;
    }

    @Override
    public boolean isTouching(IslandObject other) {
        return other.y >= this.y;
    }

    @Override
    public void step() { /* do nothing */ }
}
