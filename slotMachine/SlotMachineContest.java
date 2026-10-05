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
     * @param n wheels and symbols
     * @return matrix where each row is an action: {wheel, steps}
     */
    public int[][] solve(int n) {
        SlotMachine machine = new SlotMachine(n);
        // Garantiza que la máquina permanezca invisible según la regla 4
        machine.makeInvisible();
        ArrayList<int[]> moves = play(machine, n);

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
        // Requisito 3 y 4: La máquina debe ser visible en el simulador
        if (!java.awt.GraphicsEnvironment.isHeadless()) {
            machine.makeVisible();
        }
        play(machine, n);
        machine.isjackpot();
        }
        return moves;
    }
}
