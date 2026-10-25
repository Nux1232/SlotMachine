import java.util.ArrayList;
import java.util.Random;

/** Solver and simulator for the slot-machine alignment problem. */
public class SlotMachineContest {
    private static final int MAX_ACTIONS = 10000;

    /**
     * Computes single-step wheel spins intended to reach a jackpot.
     *
     * @param n number of wheels in the simulated machine
     * @return actions represented as {@code [wheel position, step count]} pairs
     */
    public int[][] solve(int n) {
        SlotMachine machine;
        try {
            machine = new SlotMachine(n);
        } catch (SlotMachineException exception) {
            return new int[0][2];
        }
        machine.makeInvisible();
        return play(machine);
    }

    /**
     * Runs the solver and displays the machine when a graphical environment is
     * available.
     *
     * @param n number of wheels in the simulated machine
     */
    public void simulate(int n) {
        SlotMachine machine;
        try {
            machine = new SlotMachine(n);
        } catch (SlotMachineException exception) {
            return;
        }
        if (!java.awt.GraphicsEnvironment.isHeadless()) machine.makeVisible();
        play(machine);
        machine.isJackpot();
    }

    /**
     * Spins randomly selected wheels until a jackpot is reached or the attempt
     * limit is exhausted.
     *
     * @param machine machine instance to operate on
     * @return recorded actions as wheel-position and step-count pairs
     */
    private int[][] play(SlotMachine machine) {
        ArrayList<int[]> actions = new ArrayList<>();
        int wheelCount = machine.configuration().length;
        if (wheelCount == 0) return new int[0][2];

        Random random = new Random();
        int attempts = 0;
        while (machine.distinctSymbols() > 1 && attempts < MAX_ACTIONS) {
            int wheel = random.nextInt(wheelCount) + 1;
            try {
                machine.spin(wheel, 1);
            } catch (SlotMachineException exception) {
                break;
            }
            actions.add(new int[] { wheel, 1 });
            attempts++;
        }

        return actions.toArray(new int[actions.size()][2]);
    }
}
