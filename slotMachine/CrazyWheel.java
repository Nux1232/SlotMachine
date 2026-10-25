import java.util.Random;

/** A wheel that randomly advances, copies its left neighbor, or reapplies its effect. */
public class CrazyWheel extends Wheel {
    /** Creates a wheel with randomized spin behavior. */
    public CrazyWheel() {
        super(Type.CRAZY);
    }

    /**
     * Randomly advances, copies the left neighbor, or reapplies the selected
     * symbol's effect.
     */
    @Override
    public void spin(Wheel leftNeighbor) {
        if (isLocked() || getSymbol() == null) return;
        int choice = new Random().nextInt(3);
        if (choice == 0) {
            rotateOnce();
        } else if (choice == 1 && leftNeighbor != null && leftNeighbor.getSymbol() != null) {
            copySelectedFrom(leftNeighbor);
        } else {
            applySelectedEffect();
        }
    }

    /** Performs a randomized spin; the supplied colors are not used. */
    @Override
    public void spin(String randomColor, String leftColor) {
        spin();
    }
}
