
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Pruebas adicionales para SlotMachine (Ciclo 2).
 */
public class SlotMachineCC2Test {
    private SlotMachine slotMachine;

    @BeforeEach
    public void setUp() {
        slotMachine = new SlotMachine();
    }

    /**
     * Verifica que swap intercambie los símbolos de dos ruedas válidas.
     */
    @Test
    public void accordingMsRhShouldSwapSymbolsBetweenTwoValidWheels() {
        slotMachine.addWheel(1);
        slotMachine.addWheel(2);
        slotMachine.addSymbol(1, "red");
        slotMachine.addSymbol(2, "black");

        slotMachine.swap(1, 2);

        assertArrayEquals(new String[]{"black", "red"},
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

    /**
     * Verifica que los tres colores actuales sean aceptados.
     */
    @Test
    public void shouldAcceptTheCurrentSymbolColors() {
        slotMachine.addWheel(1);
        slotMachine.addWheel(2);
        slotMachine.addWheel(3);

        slotMachine.addSymbol(1, "red");
        slotMachine.addSymbol(2, "black");
        slotMachine.addSymbol(3, "green");

        assertArrayEquals(new String[]{"red", "black", "green"},
                slotMachine.configuration());
        assertTrue(slotMachine.ok());
    }

    /**
     * Verifica que un color eliminado no se pueda usar como símbolo.
     */
    @Test
    public void shouldRejectBlueAsASymbol() {
        slotMachine.addWheel(1);
        slotMachine.addSymbol(1, "blue");

        assertFalse(slotMachine.ok());
        assertArrayEquals(new String[]{"white"},
                slotMachine.configuration());
    }
}
