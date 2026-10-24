import java.util.Random;

/** A wheel that randomly advances, copies its left neighbor, or reapplies its effect. */
public class CrazyWheel extends Wheel {
    public CrazyWheel() {
        super(Type.CRAZY);
    }

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

    @Override
    public void spin(String randomColor, String leftColor) {
        spin();
    }
}
