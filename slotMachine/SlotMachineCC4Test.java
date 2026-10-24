
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Pruebas adicionales para SlotMachine (Ciclo 4).
 */
public class SlotMachineCC4Test {
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
    
    // Pruebas tomadas del foro Symbols:
    /**
     * Verifica que se puedan agregar simbolos de los tres tipos.
     * (Compartida de RojasH, adaptada.)
     */
    @Test
    public void shouldAddSymbolsOfEveryType() {
        slotMachine.addWheel(1);
        slotMachine.addWheel(2);
        slotMachine.addWheel(3);
 
        slotMachine.addSymbol("normal", 1, "red");
        slotMachine.addSymbol("ephemeral", 2, "black");
        slotMachine.addSymbol("shy", 3, "green");
 
        assertTrue(slotMachine.ok());
        assertEquals(3, slotMachine.symbols().length);
        assertArrayEquals(new String[]{"red", "black", "green"},
                slotMachine.configuration());
    }
 
    /**
     * Verifica que un simbolo ephemeral disminuya
     * su tamaño cada vez que se ejecuta un giro.
     * (Compartida de BustosL-GomezG, adaptada.)
     */
    @Test
    public void shouldDecreaseEphemeralSymbolSize() {
<<<<<<< Updated upstream
        EphemeralSymbol symbol = new EphemeralSymbol("red");
        int initialSize = symbol.getCurrentSize();
 
        symbol.spinEffect();
 
        assertTrue(symbol.getCurrentSize() < initialSize);
=======
        EphemeralSymbol symbol = new EphemeralSymbol("red", "red");
        int initialSize = symbol.getSize();

        symbol.onWheelSpin();

        assertTrue(symbol.getSize() < initialSize);
    }

    @Test
    public void shouldAlternateShySymbolVisibility() {
        ShySymbol symbol = new ShySymbol("green", "green");
        boolean initialState = symbol.isShyVisible();

        symbol.onSelected();
        assertNotEquals(initialState, symbol.isShyVisible());
        symbol.onSelected();
        assertEquals(initialState, symbol.isShyVisible());
    }

    @Test
    public void leftyShouldCopyTheNormalWheelImmediatelyToItsLeft() {
        machine.placeSymbol(1, "magenta");

        machine.spin(2);

        assertTrue(machine.ok());
        assertEquals("magenta", machine.configuration()[1]);
    }

    @Test
    public void rebelShouldRejectLockSwapAndRemoval() {
        String[] original = machine.configuration();

        machine.lock(3);
        assertFalse(machine.ok());
        machine.swap(3, 1);
        assertFalse(machine.ok());
        machine.delWheel(3);
        assertFalse(machine.ok());

        assertArrayEquals(original, machine.configuration());
        assertEquals(3, machine.configuration().length);
    }

    @Test
    public void hiddenShySymbolShouldNotProduceAJackpot() throws SlotMachineException {
        SlotMachine shyMachine = new SlotMachine();
        shyMachine.addWheel(1);
        shyMachine.addWheel(2);
        shyMachine.addSymbol("ShySymbol", 1, "red");
        shyMachine.placeSymbol(2, "red");
        assertTrue(shyMachine.isJackpot());

        shyMachine.spin(1);

        assertFalse(shyMachine.isJackpot());
        assertEquals("", shyMachine.configuration()[0]);
        shyMachine.makeInvisible();
    }

    @Test
    public void allWheelTypesAreAcceptedAndCrazyKeepsAValidSymbol() {
        SlotMachine typedMachine = new SlotMachine();
        typedMachine.addWheel(1, Wheel.Type.CRAZY);
        typedMachine.addSymbol(1, "red");
        typedMachine.addSymbol(1, "green");
        for (int i = 0; i < 20; i++) typedMachine.spin(1);

        assertEquals(Wheel.Type.CRAZY, typedMachine.wheelType(1));
        assertTrue("red".equals(typedMachine.configuration()[0])
                || "green".equals(typedMachine.configuration()[0]));
        typedMachine.makeInvisible();
>>>>>>> Stashed changes
    }
}
