import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import java.time.Duration;
import org.junit.jupiter.api.Test;

/** General regression checks for the fourth cycle. */
public class SlotMachineC4Test {
    /** Ensures construction with one wheel terminates and creates that wheel. */
    @Test
    public void oneWheelConstructorShouldReturnWithoutLooping() {
        assertTimeoutPreemptively(Duration.ofSeconds(2), () -> {
            SlotMachine machine = new SlotMachine(1);
            assertEquals(1, machine.configuration().length);
            machine.makeInvisible();
        });
    }

    /** Assigns a matching explicit configuration and verifies jackpot detection. */
    @Test
    public void shouldSetAndReportAnExplicitWinningConfiguration() throws SlotMachineException {
        SlotMachine machine = new SlotMachine();
        machine.addWheel(1);
        machine.addWheel(2);
    
        machine.spin(new String[] { "red", "red" });
    
        assertArrayEquals(new String[] { "red", "red" }, machine.configuration());
        assertTrue(machine.isJackpot());
        machine.makeInvisible();
    }
    /** Confirms only special symbols have a visual badge. */
    @Test
    public void specialSymbolsShouldHaveABadgeAndNormalOnesNot() {
        assertFalse(new Symbol("red").hasBadge());
        assertTrue(new ShySymbol("red").hasBadge());
        assertTrue(new EphemeralSymbol("red").hasBadge());
    }
}
