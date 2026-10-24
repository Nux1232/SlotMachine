import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;

import java.time.Duration;

import org.junit.jupiter.api.Test;

/**
 * Pruebas unitarias para la solución del problema de concurso.
 */
public class SlotMachineContestTest {

    @Test
    public void accordingCcIcsolveShouldReturnAnEmptyPlanForOneWheel() {
        SlotMachineContest contest = new SlotMachineContest();

        int[][] result = contest.solve(1);

        assertNotNull(result);
        assertEquals(0, result.length);
    }

    @Test
    public void accordingCcIcsolveShouldReturnActionsWithValidWheelAndStepValues() {
        SlotMachineContest contest = new SlotMachineContest();

        int[][] result = assertTimeoutPreemptively(
                Duration.ofSeconds(5),
                () -> contest.solve(3));

        assertNotNull(result);
        for (int[] action : result) {
            assertNotNull(action);
            assertEquals(2, action.length);
            assertTrue(action[0] >= 1 && action[0] <= 3);
            assertEquals(1, action[1]);
        }
    }

    @Test
    public void accordingCcIcsimulateShouldFinishForTwoWheels() {
        SlotMachineContest contest = new SlotMachineContest();

        assertDoesNotThrow(() -> assertTimeoutPreemptively(
                Duration.ofSeconds(5),
                () -> contest.simulate(2)));
    }
}
