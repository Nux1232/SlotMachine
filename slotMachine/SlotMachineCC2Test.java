

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * The test class SlowMachineCC2Test.
 *
 * @author  (your name)
 * @version (a version number or a date)
 */
public class SlowMachineCC2Test
{
    /**
     * Verifica que swap intercambie los símbolos de dos ruedas válidas.
     */
    @Test
    public void accordingMsRhShouldSwapSymbolsBetweenTwoValidWheels() {
        slotMachine.addWheel(1);
        slotMachine.addWheel(2);
        slotMachine.addSymbol(1, "red");
        slotMachine.addSymbol(2, "blue");

        slotMachine.swap(1, 2);

        assertArrayEquals(new String[]{"blue", "red"},
                slotMachine.configuration());
    }

    /**
     * Verifica que una rueda bloqueada conserve su símbolo al girarla.
     */
    @Test
    public void accordingMsRhShouldNotChangeLockedWheelWhenSpinning() {
        slotMachine.addWheel(1);
        slotMachine.addSymbol(1, "red");
        slotMachine.lock(1);

        slotMachine.spin(1);

        assertEquals("red", slotMachine.configuration()[0]);
        assertFalse(slotMachine.ok());
    }

    /**
     * Verifica que spin(String) establezca una configuración ganadora.
     */
    @Test
    public void accordingMsRhShouldDetectJackpotAfterForcedSpin() {
        slotMachine.addWheel(1);
        slotMachine.addWheel(2);

        slotMachine.spin("red, red");

        assertArrayEquals(new String[]{"red", "red"},
                slotMachine.configuration());
        assertTrue(slotMachine.isjackpot());
    }

    /**
     * Verifica que una posición mayor al número de ruedas elimine la última.
     */
    @Test
    public void accordingMsRhShouldDeleteLastWheelWhenPositionGreaterThanSize() {
        slotMachine.addWheel(1);
        slotMachine.addWheel(2);

        slotMachine.delWheel(10);

        assertEquals(1, slotMachine.configuration().length);
    }
}

// tomado de Grupo: MoralesS-RojasH
}