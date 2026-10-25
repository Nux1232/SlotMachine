/** A wheel that cannot be locked. */
public class RebelWheel extends Wheel {
    /** Creates a wheel that cannot be locked, unlocked, swapped, or deleted. */
    public RebelWheel() {
        super(Type.REBEL);
    }

    /** @return always {@code true} */
    @Override
    public boolean isRebel() {
        return true;
    }
}
