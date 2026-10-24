import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Duration;

import org.junit.jupiter.api.Test;

/**
 * Pruebas unitarias adicionales para SlotMachineContest.
 */
public class SlotMachineContestCTest {

    /**
     * Verifica que SlotMachine(n) cree exactamente n ruedas y que cada rueda
     * tenga un símbolo actualmente montado.
     */
    @Test
    public void accordingCjMcShouldCreateEqualWheelsAndSymbolsPerWheel() {
        int n = 5;
        SlotMachine sm = new SlotMachine(n);

        assertEquals(n, sm.configuration().length);
        // symbols() devuelve el símbolo visible de cada rueda, no un
        // inventario de todos los símbolos usados durante la inicialización.
        assertEquals(n, sm.symbols().length);
        for (String symbol : sm.symbols()) {
            assertNotNull(symbol);
        }
    }

    /**
     * Verifica que solve(n) solo proponga ruedas dentro del rango válido.
     * La cantidad de acciones no se limita a n: una solución puede necesitar
     * girar la misma rueda varias veces antes de obtener un jackpot.
     */
    @Test
    public void accordingCjMcShouldProposeActionsForValidWheels() {
        int n = 4;
        int[][] actions = assertTimeoutPreemptively(
                Duration.ofSeconds(5),
                () -> new SlotMachineContest().solve(n));

        assertNotNull(actions);
        for (int[] action : actions) {
            assertNotNull(action);
            assertEquals(2, action.length);
            int wheel = action[0];
            assertTrue(wheel >= 1 && wheel <= n);
        }
    }
}
