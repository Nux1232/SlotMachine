import java.util.ArrayList;
import java.util.Random;

/**
 * Class that resolves and simulates the SlotMachine problem.
 * The solver only knows how many distinct symbols the machine shows
 * after each action, like in the original problem.
 *
 * @author Samuel Infante Camargo
 * @author Juan Pablo Cuervo Contreras
 * @version Ciclo 4
 */
public class SlotMachineContest {
    private static final int MAX_ACTIONS = 10000;

    /**
     * Returns the sequence of actions (i, j) needed to win.
     * The machine is invisible.
     *
     * @param n wheels and symbols
     * @return matrix where each row is an action: {wheel, steps}
     */
    public int[][] solve(int n) {
        SlotMachine machine = new SlotMachine(n);
        machine.makeInvisible();
        ArrayList<int[]> moves = play(machine, n);

        int[][] result = new int[moves.size()][2];
        for (int i = 0; i < moves.size(); i++) {
            result[i] = moves.get(i);
        }
        return result;
    }

    /**
     * Simulates the actions needed to win. The machine is visible.
     *
     * @param n wheels and symbols
     */
    public void simulate(int n) {
        SlotMachine machine = new SlotMachine(n);
        machine.makeVisible();
        play(machine, n);
        machine.isjackpot();
    }

    /**
     * Plays until all wheels show the same symbol or the action limit is
     * reached. Each turn takes a random wheel, turns it a full circle
     * measuring the number of distinct symbols, and leaves it in one of
     * the positions where that number is the lowest.
     *
     * @return the actions that changed the machine, as {wheel, steps}
     */
    private ArrayList<int[]> play(SlotMachine machine, int n) {
        ArrayList<int[]> moves = new ArrayList<>();
        Random random = new Random();
        int actions = 0;

        while (machine.distinctSymbols() > 1 && actions < MAX_ACTIONS) {
            int wheel = random.nextInt(n) + 1;

            // One full turn: k[offset] = distinct symbols with the wheel
            // moved "offset" positions from where it started
            int[] k = new int[n];
            k[0] = machine.distinctSymbols();
            int best = k[0];
            for (int offset = 1; offset < n; offset++) {
                machine.spin(wheel, 1);
                k[offset] = machine.distinctSymbols();
                best = Math.min(best, k[offset]);
            }
            machine.spin(wheel, 1);   // back to the starting position
            actions += n;

            // Stay in one of the best positions (random among ties)
            ArrayList<Integer> candidates = new ArrayList<>();
            for (int offset = 0; offset < n; offset++) {
                if (k[offset] == best) {
                    candidates.add(offset);
                }
            }
            int chosen = candidates.get(random.nextInt(candidates.size()));
            if (chosen > 0) {
                machine.spin(wheel, chosen);
                moves.add(new int[]{wheel, chosen});
                actions++;
            }
        }
        return moves;
    }
}