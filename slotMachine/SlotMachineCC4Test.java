import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/** Cycle 4 regression tests adapted to the project's JUnit 5 API. */
public class SlotMachineCC4Test {
    private SlotMachine machine;

    @BeforeEach
    public void setUp() throws SlotMachineException {
        machine = new SlotMachine();
        String[] types = { "normal", "lefty", "rebel" };
        String[] colors = { "red", "green", "black" };
        for (int wheel = 1; wheel <= types.length; wheel++) {
            machine.addWheel(types[wheel - 1], wheel);
            for (String color : colors) machine.addSymbol(wheel, color);
        }
    }

    @AfterEach
    public void clean() {
        machine.makeInvisible();
    }

    /**
     * Verifica que un símbolo efímero reduzca su tamaño con cada giro.
     */
    @Test
    public void shouldDecreaseEphemeralSymbolSize() {
        EphemeralSymbol symbol = new EphemeralSymbol("red");
        int initialSize = symbol.getSize();

        symbol.spinEffect();
        int afterFirstSpin = symbol.getSize();
        assertTrue(afterFirstSpin < initialSize);

        symbol.onWheelSpin();
        assertTrue(symbol.getSize() < afterFirstSpin);
    }

    /**
     * Verifica que un símbolo tímido alterne su visibilidad cada vez que es seleccionado.
     */
    @Test
    public void shouldAlternateShySymbolVisibility() {
        ShySymbol symbol = new ShySymbol("green");
        boolean initialState = symbol.isShyVisible();

        symbol.onSelected();
        assertNotEquals(initialState, symbol.isShyVisible());
        symbol.onSelected();
        assertEquals(initialState, symbol.isShyVisible());
    }

    /**
     * Verifica que una rueda lefty copie el símbolo de la rueda a su izquierda.
     */
    @Test
    public void leftyShouldCopyTheNormalWheelImmediatelyToItsLeft() throws SlotMachineException {
        machine.placeSymbol(1, "magenta");

        machine.spin(2);

        assertTrue(machine.ok());
        assertEquals("magenta", machine.configuration()[1]);
    }

    /**
     * Verifica que una rueda rebel rechace bloqueo, intercambio y eliminación.
     */
    @Test
    public void rebelShouldRejectLockSwapAndRemoval() {
        String[] original = machine.configuration();

        assertThrows(SlotMachineException.class, () -> machine.lock(3));
        assertFalse(machine.ok());
        assertThrows(SlotMachineException.class, () -> machine.swap(3, 1));
        assertFalse(machine.ok());
        assertThrows(SlotMachineException.class, () -> machine.delWheel(3));
        assertFalse(machine.ok());

        assertArrayEquals(original, machine.configuration());
        assertEquals(3, machine.configuration().length);
    }

    /**
     * Verifica que el mensaje de la excepción sea el de rueda rebelde.
     */
    @Test
    public void rebelLockShouldReportRebelMessage() {
        SlotMachineException exception =
                assertThrows(SlotMachineException.class, () -> machine.lock(3));

        assertEquals(SlotMachineException.WHEEL_REBEL, exception.getMessage());
    }

    /**
     * Verifica que un símbolo tímido oculto no produzca jackpot.
     */
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

    /**
     * Verifica que todos los tipos de rueda se acepten y que la crazy conserve un símbolo válido.
     */
    @Test
    public void allWheelTypesAreAcceptedAndCrazyKeepsAValidSymbol() throws SlotMachineException {
        SlotMachine typedMachine = new SlotMachine();
        typedMachine.addWheel(1, Wheel.Type.CRAZY);
        typedMachine.addSymbol(1, "red");
        typedMachine.addSymbol(1, "green");
        for (int i = 0; i < 20; i++) typedMachine.spin(1);

        assertEquals(Wheel.Type.CRAZY, typedMachine.wheelType(1));
        assertTrue("red".equals(typedMachine.configuration()[0])
                || "green".equals(typedMachine.configuration()[0]));
        typedMachine.makeInvisible();
    }

    /**
     * Verifies that a custom wheel can provide its own behavior through inheritance.
     */
    @Test
    public void customWheelUsesItsOverriddenSpinBehavior() throws SlotMachineException {
        SlotMachine customMachine = new SlotMachine();
        customMachine.addWheel(1, new Wheel() {
            @Override
            public void spin(Wheel leftNeighbor) {
                selectColor("green");
            }
        });
        customMachine.addSymbol(1, "red");
        customMachine.addSymbol(1, "green");

        customMachine.spin(1);

        assertEquals("green", customMachine.configuration()[0]);
        customMachine.makeInvisible();
    }

    /**
     * Verifica que addSymbol cree símbolos a partir del nombre de la clase.
     */
    @Test
    public void shouldCreateSymbolsByClassName() throws SlotMachineException {
        SlotMachine m = new SlotMachine();
        m.addWheel(1);

        m.addSymbol("EphemeralSymbol", 1, "red");
        assertTrue(m.ok());
        m.addSymbol("ShySymbol", 1, "green");
        assertTrue(m.ok());
        m.addSymbol("normal", 1, "black");
        assertTrue(m.ok());

        assertEquals("red", m.configuration()[0]);
        m.makeInvisible();
    }

    /**
     * Verifica que addSymbol rechace tipos desconocidos, nulos o que no heredan de Symbol.
     */
    @Test
    public void shouldRejectInvalidSymbolTypes() throws SlotMachineException {
        SlotMachine m = new SlotMachine();
        m.addWheel(1);

        assertThrows(SlotMachineException.class, () -> m.addSymbol("Foo", 1, "red"));
        assertFalse(m.ok());
        assertThrows(SlotMachineException.class, () -> m.addSymbol("Wheel", 1, "red"));
        assertThrows(SlotMachineException.class, () -> m.addSymbol("String", 1, "red"));
        assertThrows(SlotMachineException.class, () -> m.addSymbol(null, 1, "red"));
        m.makeInvisible();
    }

    /**
     * Verifica que addSymbol rechace posiciones, colores y máquinas inválidas.
     */
    @Test
    public void shouldRejectInvalidWheelOrColorOnAddSymbol() throws SlotMachineException {
        SlotMachine empty = new SlotMachine();
        assertThrows(SlotMachineException.class, () -> empty.addSymbol("normal", 1, "red"));

        SlotMachine m = new SlotMachine();
        m.addWheel(1);
        assertThrows(SlotMachineException.class, () -> m.addSymbol("normal", 5, "red"));
        assertThrows(SlotMachineException.class, () -> m.addSymbol("normal", 1, "blue"));
        empty.makeInvisible();
        m.makeInvisible();
    }

    /**
     * Verifica las excepciones de swap y spin sobre ruedas bloqueadas o inexistentes.
     */
    @Test
    public void shouldRejectSwapAndSpinOnInvalidWheels() throws SlotMachineException {
        assertThrows(SlotMachineException.class, () -> machine.swap(1, 1));
        assertThrows(SlotMachineException.class, () -> machine.swap(1, 9));
        assertThrows(SlotMachineException.class, () -> machine.spin(9));
        assertThrows(SlotMachineException.class, () -> machine.spin(1, -1));

        machine.lock(1);
        assertThrows(SlotMachineException.class, () -> machine.spin(1));
        assertThrows(SlotMachineException.class, () -> machine.swap(1, 2));
        machine.unlock(1);
        machine.spin(1);
        assertTrue(machine.ok());
    }
}