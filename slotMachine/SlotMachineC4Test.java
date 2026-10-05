import static org.junit.jupiter.api.Assertions.*;
import java.time.Duration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/** Regression tests for wheel and symbol features added in Cycle 4. */
public class SlotMachineC4Test {

    private SlotMachine slotMachine;

    @BeforeEach
    public void setUp() {
        slotMachine = new SlotMachine();
    }

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
        slotMachine.placeSymbol(2, "black");

        slotMachine.swap(1, 2);

        assertTrue(slotMachine.ok());
        assertArrayEquals(new String[]{"black", "red"}, slotMachine.configuration());
    }

    @Test
    public void accordingCcIcshouldLockAndUnlockWheel() {
        slotMachine.addWheel(1);
        slotMachine.placeSymbol(1, "red");
        slotMachine.lock(1);
        assertTrue(slotMachine.ok());

        slotMachine.spin("red");
        assertTrue(slotMachine.ok());
        assertEquals("red", slotMachine.configuration()[0]);

        slotMachine.unlock(1);
        assertTrue(slotMachine.ok());

        slotMachine.spin("black");
        assertTrue(slotMachine.ok());
        assertEquals("black", slotMachine.configuration()[0]);
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

        assertTrue(Symbol.isAvailableColor(slotMachine.configuration()[0]));
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

        slotMachine.spin("red, black");

        assertArrayEquals(new String[]{"red", "black"},
                slotMachine.configuration());
        assertTrue(slotMachine.ok());
    }

    @Test
    public void shouldExposeAllFourWheelTypesThroughBlueJFriendlyMethods() {
        SlotMachine machine = new SlotMachine();
        machine.addWheel(1, "normal");
        machine.addWheel(2, Wheel.Type.LEFTY);
        machine.addWheel(3, "rebel");
        machine.addWheel(4, "CRAZY");

        assertArrayEquals(new Wheel.Type[] {
                Wheel.Type.NORMAL, Wheel.Type.LEFTY, Wheel.Type.REBEL, Wheel.Type.CRAZY
        }, new Wheel.Type[] { machine.wheelType(1), machine.wheelType(2),
                machine.wheelType(3), machine.wheelType(4) });
        assertTrue(machine.ok());
    }

    @Test
    public void shouldRejectUnknownWheelTypeWithoutAddingAWheel() {
        SlotMachine machine = new SlotMachine();

        machine.addWheel(1, "confused");

        assertFalse(machine.ok());
        assertEquals(0, machine.configuration().length);
    }

    @Test
    public void leftyShouldCopyItsImmediateLeftNeighborWhenSpun() {
        Wheel left = new Wheel(Wheel.Type.NORMAL);
        Wheel lefty = new Wheel(Wheel.Type.LEFTY);
        left.addSymbolWheel("orange");

        lefty.spin("pink", left.symbolColor());

        assertEquals("orange", lefty.symbolColor());
    }

    @Test
    public void crazyWheelShouldOnlyUseItsSpinOptions() {
        Wheel crazy = new Wheel(Wheel.Type.CRAZY);
        crazy.addSymbolWheel("yellow");
        for (int i = 0; i < 100; i++) crazy.spin("red", "black");

        assertTrue("red".equals(crazy.symbolColor())
                || "black".equals(crazy.symbolColor())
                || "yellow".equals(crazy.symbolColor()));
    }

    @Test
    public void machineSpinWithStepsShouldApplyLeftyRule() {
        SlotMachine machine = new SlotMachine();
        machine.addWheel(1);
        machine.addWheel(2, Wheel.Type.LEFTY);
        machine.placeSymbol(1, "magenta");
        machine.placeSymbol(2, "black");

        machine.spin(2, 1);

        assertArrayEquals(new String[] { "magenta", "magenta" }, machine.configuration());
        assertTrue(machine.ok());
    }

    @Test
    public void rebelShouldRejectLockSwapAndRemoval() {
        SlotMachine machine = new SlotMachine();
        machine.addWheel(1, Wheel.Type.REBEL);
        machine.addWheel(2);

        machine.lock(1);
        assertFalse(machine.ok());
        machine.swap(1, 2);
        assertFalse(machine.ok());
        machine.delWheel(1);
        assertFalse(machine.ok());
        assertEquals(2, machine.configuration().length);
    }

    @Test
    public void shouldRejectUnsupportedSymbolsWithoutReplacingCurrentSymbol() {
        SlotMachine machine = new SlotMachine();
        machine.addWheel(1);
        machine.placeSymbol(1, "red");

        machine.placeSymbol(1, "purple");

        assertFalse(machine.ok());
        assertArrayEquals(new String[] { "red" }, machine.configuration());
    }

    @Test
    public void readOnlyQueriesShouldNotEraseTheLastOperationResult() {
        SlotMachine machine = new SlotMachine();
        machine.addWheel(1);
        machine.spin(1, -1);

        machine.configuration();
        machine.symbols();
        machine.distinctSymbols();

        assertFalse(machine.ok());
    }

    @Test
    public void shySymbolShouldAlternateVisibilityOnSpinEffect() {
        ShySymbol symbol = new ShySymbol("green");

        assertFalse(symbol.isHidden());
        symbol.spinEffect();
        assertTrue(symbol.isHidden());
        symbol.spinEffect();
        assertFalse(symbol.isHidden());
    }

    @Test
    public void ephemeralSymbolShouldShrinkToItsMinimumSize() {
        EphemeralSymbol symbol = new EphemeralSymbol("red");

        for (int i = 0; i < 20; i++) symbol.spinEffect();

        assertEquals(10, symbol.getSize());
    }

    @Test
    public void wheelSpinShouldApplySpecialSymbolEffects() {
        Wheel wheel = new Wheel();
        EphemeralSymbol ephemeral = new EphemeralSymbol("red");
        wheel.addSymbolWheel(ephemeral);
        wheel.spin("black", null);
        assertEquals(45, ephemeral.getSize());
        assertEquals("red", wheel.symbolColor());

        ShySymbol shy = new ShySymbol("green");
        wheel.addSymbolWheel(shy);
        wheel.spin("black", null);
        assertTrue(shy.isHidden());
    }

    @Test
    public void oneWheelConstructorShouldFinishAndNegativeCountShouldBeEmpty() {
        assertTimeoutPreemptively(Duration.ofSeconds(2), () -> new SlotMachine(1));
        SlotMachine empty = new SlotMachine(-3);
        assertNotNull(empty.configuration());
        assertEquals(0, empty.configuration().length);
    }

    @Test
    public void accordingCcIcshouldNotExceedMaximumWheelsLimit() {
        for (int i = 1; i <= 10; i++) {
            slotMachine.addWheel(i);
        }

        assertFalse(slotMachine.ok());
        assertEquals(9, slotMachine.configuration().length);
        assertEquals(9, slotMachine.symbols().length);
    }

    @Test
    public void accordingCcIcshouldNotAllowOperationsWhenNoWheelsExist() {
        slotMachine.delWheel(1);
        assertFalse(slotMachine.ok());
        assertEquals(0, slotMachine.configuration().length);

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
        slotMachine.placeSymbol(2, "black");

        slotMachine.spin("red, purple");

        assertArrayEquals(new String[]{"red", "black"},
                slotMachine.configuration());
        assertFalse(slotMachine.ok());
    }

    @Test
    public void accordingCcIcshouldRejectBlueAsAnObsoleteSymbol() {
        slotMachine.addWheel(1);
        slotMachine.placeSymbol(1, "blue");

        assertFalse(slotMachine.ok());
        assertArrayEquals(new String[]{"white"}, slotMachine.configuration());
    }

    @Test
    public void accordingCcIcshouldAcceptTheThreeCurrentSymbolColors() {
        slotMachine.addWheel(1);
        slotMachine.addWheel(2);
        slotMachine.addWheel(3);

        slotMachine.placeSymbol(1, "red");
        slotMachine.placeSymbol(2, "black");
        slotMachine.placeSymbol(3, "green");

        assertArrayEquals(new String[]{"red", "black", "green"},
                slotMachine.configuration());
        assertTrue(slotMachine.ok());
    }

    @Test
    public void accordingCcIcshouldNotSetConfigurationWithWrongNumberOfSymbols() {
        slotMachine.addWheel(1);
        slotMachine.addWheel(2);
        slotMachine.placeSymbol(1, "red");
        slotMachine.placeSymbol(2, "black");

        slotMachine.spin("red");

        assertFalse(slotMachine.ok());
        assertArrayEquals(new String[]{"red", "black"},
                slotMachine.configuration());
    }

    @Test
    public void accordingCcIcshouldNotFailOnInvalidWheelIndexWhenAdding() {
        slotMachine.addWheel(-5);
        assertTrue(slotMachine.ok());

        slotMachine.addWheel(99);
        assertTrue(slotMachine.ok());

        assertEquals(2, slotMachine.configuration().length);
    }

    @Test
    public void accordingCcIcshouldShrinkEphemeralWhenItReachesTheWindow() {
        Wheel wheel = new Wheel();
        wheel.addSymbolWheel(new EphemeralSymbol("red"));
        wheel.addSymbolWheel(new Symbol("black"));

        wheel.rotateOnce();
        wheel.rotateOnce();

        assertEquals("red", wheel.getSymbol().getColor());
        assertEquals(40, ((EphemeralSymbol) wheel.getSymbol()).getCurrentSize());
    }

    @Test
    public void accordingCcIcshouldHideShyWhenItIsSelectedInTheWheel() {
        Wheel wheel = new Wheel();
        wheel.addSymbolWheel(new ShySymbol("red"));
        wheel.addSymbolWheel(new Symbol("black"));

        wheel.rotateOnce();
        wheel.rotateOnce();

        assertEquals("red", wheel.getSymbol().getColor());
        assertTrue(((ShySymbol) wheel.getSymbol()).isHidden());
    }

    @Test
    public void accordingCcIcshouldNotAddSymbolWithUnknownType() {
        slotMachine.addWheel(1);

        slotMachine.addSymbol("giant", 1, "red");

        assertFalse(slotMachine.ok());
        assertArrayEquals(new String[]{"white"}, slotMachine.configuration());
    }
}

    }
}
