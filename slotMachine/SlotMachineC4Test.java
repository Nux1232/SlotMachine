import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import java.time.Duration;
import org.junit.jupiter.api.Test;

/** General regression checks for the fourth cycle. */
public class SlotMachineC4Test {
    @Test
    public void oneWheelConstructorShouldReturnWithoutLooping() {
        assertTimeoutPreemptively(Duration.ofSeconds(2), () -> {
            SlotMachine machine = new SlotMachine(1);
            assertEquals(1, machine.configuration().length);
            machine.makeInvisible();
        });
    }

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
    @Test
    public void specialSymbolsShouldHaveABadgeAndNormalOnesNot() {
        assertFalse(new Symbol("red").hasBadge());
        assertTrue(new ShySymbol("red").hasBadge());
        assertTrue(new EphemeralSymbol("red").hasBadge());
    }
}
