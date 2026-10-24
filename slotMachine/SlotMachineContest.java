import java.util.ArrayList;
import java.util.Random;

/** Solver and simulator for the slot-machine alignment problem. */
public class SlotMachineContest {
    private static final int MAX_ACTIONS = 10000;

    /** Returns wheel/step actions that try to reach a jackpot. */
    public int[][] solve(int n) {
        SlotMachine machine = new SlotMachine(n);
        machine.makeInvisible();
        return play(machine);
    }

    /** Runs the same solution loop with graphics when a display is available. */
    public void simulate(int n) {
        SlotMachine machine = new SlotMachine(n);
        if (!java.awt.GraphicsEnvironment.isHeadless()) machine.makeVisible();
        play(machine);
        machine.isJackpot();
    }

    private int[][] play(SlotMachine machine) {
        ArrayList<int[]> actions = new ArrayList<>();
        int wheelCount = machine.configuration().length;
        if (wheelCount == 0) return new int[0][2];

        Random random = new Random();
        int attempts = 0;
        while (machine.distinctSymbols() > 1 && attempts < MAX_ACTIONS) {
            int wheel = random.nextInt(wheelCount) + 1;
            machine.spin(wheel, 1);
            if (!machine.ok()) break;
            actions.add(new int[] { wheel, 1 });
            attempts++;
        }

        return actions.toArray(new int[actions.size()][2]);
    }
}
