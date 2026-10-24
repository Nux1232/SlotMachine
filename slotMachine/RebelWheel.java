/** A wheel that cannot be locked. */
public class RebelWheel extends Wheel {
    public RebelWheel() {
        super(Type.REBEL);
    }

    @Override
    public boolean isRebel() {
        return true;
    }
}
