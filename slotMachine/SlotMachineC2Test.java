import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Casos de prueba unitaria para SlotMachine (Ciclo 2) en modo invisible.
 */
public class SlotMachineC2Test {

    private SlotMachine slotMachine;

    @BeforeEach
    public void setUp() {
        // Se instancia en modo invisible por defecto
        slotMachine = new SlotMachine();
    }

    // ==========================================
    // 1. Pruebas de ¿Qué debería hacer?
    // ==========================================

    @Test
    public void accordingCcIcshouldAddWheelSuccessfully() {
        slotMachine.addWheel(1);

        assertTrue(slotMachine.ok());
        assertEquals(1, slotMachine.configuration().length);
    }

    @Test
    public void accordingCcIcshouldAddSymbolToExistingWheel() {
        slotMachine.addWheel(1);
        slotMachine.addSymbol(1, "red");

        assertTrue(slotMachine.ok());
        assertArrayEquals(new String[]{"red"}, slotMachine.symbols());
    }

    @Test
    public void accordingCcIcshouldDeleteWheelCorrectly() {
        slotMachine.addWheel(1);
        slotMachine.addWheel(2);
        slotMachine.delWheel(1);

        assertTrue(slotMachine.ok());
        assertEquals(1, slotMachine.configuration().length);
    }

    @Test
    public void accordingCcIcshouldSwapUnlockedWheels() {
        slotMachine.addWheel(1);
        slotMachine.addWheel(2);
        slotMachine.placeSymbol(1, "red");
        slotMachine.placeSymbol(2, "blue");

        slotMachine.swap(1, 2);

        assertTrue(slotMachine.ok());
        assertArrayEquals(new String[]{"blue", "red"}, slotMachine.configuration());
    }

    @Test
    public void accordingCcIcshouldLockAndUnlockWheel() {
        slotMachine.addWheel(1);
        slotMachine.lock(1);
        assertTrue(slotMachine.ok());

        slotMachine.unlock(1);
        assertTrue(slotMachine.ok());
    }

    @Test
    public void accordingCcIcshouldCalculateDistinctSymbolsCorrectly() {
        slotMachine.addWheel(1);
        slotMachine.addWheel(2);
        slotMachine.addWheel(3);

        slotMachine.placeSymbol(1, "red");
        slotMachine.placeSymbol(2, "red");
        slotMachine.placeSymbol(3, "green");

        assertEquals(2, slotMachine.distinctSymbols());
        assertTrue(slotMachine.ok());
    }

    @Test
    public void accordingCcIcshouldSpinWheelTheRequestedNumberOfSteps() {
        slotMachine.addWheel(1);
        slotMachine.placeSymbol(1, "red");

        slotMachine.spin(1, 3);

        assertNotNull(slotMachine.configuration()[0]);
        assertTrue(slotMachine.ok());
    }

    @Test
    public void accordingCcIcshouldKeepWheelSymbolWhenSpinHasZeroSteps() {
        slotMachine.addWheel(1);
        slotMachine.placeSymbol(1, "red");

        slotMachine.spin(1, 0);

        assertEquals("red", slotMachine.configuration()[0]);
        assertTrue(slotMachine.ok());
    }

    @Test
    public void accordingCcIcshouldSetConfigurationWithSpinString() {
        slotMachine.addWheel(1);
        slotMachine.addWheel(2);

        slotMachine.spin("red, blue");

        assertArrayEquals(new String[]{"red", "blue"},
                slotMachine.configuration());
        assertTrue(slotMachine.ok());
    }

    // ==========================================
    // 2. Pruebas de ¿Qué NO debería hacer?
    // ==========================================

    @Test
    public void accordingCcIcshouldNotExceedMaximumWheelsLimit() {
        // Intentar agregar más del límite de 9 ruedas
        for (int i = 1; i <= 10; i++) {
            slotMachine.addWheel(i);
        }

        assertFalse(slotMachine.ok());
        assertEquals(9, slotMachine.configuration().length);
    }

    @Test
    public void accordingCcIcshouldNotAllowOperationsWhenNoWheelsExist() {
        slotMachine.delWheel(1);
        assertFalse(slotMachine.ok());

        slotMachine.addSymbol(1, "red");
        assertFalse(slotMachine.ok());

        slotMachine.spin(1);
        assertFalse(slotMachine.ok());
    }

    @Test
    public void accordingCcIcshouldNotSwapSameWheel() {
        slotMachine.addWheel(1);
        slotMachine.swap(1, 1);

        assertFalse(slotMachine.ok());
    }

    @Test
    public void accordingCcIcshouldNotSwapLockedWheels() {
        slotMachine.addWheel(1);
        slotMachine.addWheel(2);
        slotMachine.lock(1);

        slotMachine.swap(1, 2);

        assertFalse(slotMachine.ok());
    }

    @Test
    public void accordingCcIcshouldNotSpinLockedWheel() {
        slotMachine.addWheel(1);
        slotMachine.lock(1);

        slotMachine.spin(1);

        assertFalse(slotMachine.ok());
    }

    @Test
    public void accordingCcIcshouldNotSpinWheelWithNegativeSteps() {
        slotMachine.addWheel(1);
        slotMachine.placeSymbol(1, "red");

        slotMachine.spin(1, -1);

        assertEquals("red", slotMachine.configuration()[0]);
        assertFalse(slotMachine.ok());
    }

    @Test
    public void accordingCcIcshouldNotSetConfigurationWithUnsupportedSymbol() {
        slotMachine.addWheel(1);
        slotMachine.addWheel(2);
        slotMachine.placeSymbol(1, "red");
        slotMachine.placeSymbol(2, "blue");

        slotMachine.spin("red, purple");

        assertArrayEquals(new String[]{"red", "blue"},
                slotMachine.configuration());
        assertFalse(slotMachine.ok());
    }

    @Test
    public void accordingCcIcshouldNotSetConfigurationWithWrongNumberOfSymbols() {
        slotMachine.addWheel(1);
        slotMachine.addWheel(2);

        slotMachine.spin("red");

        assertFalse(slotMachine.ok());
    }

    @Test
    public void accordingCcIcshouldNotFailOnInvalidWheelIndexWhenAdding() {
        // Posiciones fuera de rango deben ajustarse al límite más cercano
        slotMachine.addWheel(-5); // Se ajusta a 1
        assertTrue(slotMachine.ok());

        slotMachine.addWheel(99); // Se ajusta al final
        assertTrue(slotMachine.ok());

        assertEquals(2, slotMachine.configuration().length);
    }
}
