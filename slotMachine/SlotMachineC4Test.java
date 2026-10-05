import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
    public void shouldSetAndReportAnExplicitWinningConfiguration() {
        SlotMachine machine = new SlotMachine();
        machine.addWheel(1);
        machine.addWheel(2);

        machine.spin("red, red");

        assertArrayEquals(new String[] { "red", "red" }, machine.configuration());
        assertTrue(machine.isJackpot());
        machine.makeInvisible();
    }
}
