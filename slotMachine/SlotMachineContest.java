import java.util.ArrayList;
import java.util.Random;

/**
 * Class that resolves and simulates the SlotMachine problem
 */
public class SlotMachineContest {

    /**
     * Return the sequence of actions (i, j) to win.
     * The machine must be invisible.
     * @param n wheels and symbols
     * @return Matrix where each row is an action: {wheel, steps}
     */
    public int[][] solve(int n) {
        SlotMachine machine = new SlotMachine(n);
        // Garantiza que la máquina permanezca invisible según la regla 4
        machine.makeInvisible();

        ArrayList<int[]> moves = new ArrayList<>();
        Random random = new Random();

        // Se usa exclusivamente distinctSymbols() para evaluar el estado
        while (machine.distinctSymbols() > 1) {
            int wheelToSpin = random.nextInt(n) + 1;
            int stepsToSpin = 1;

            machine.spin(wheelToSpin, stepsToSpin);
            moves.add(new int[]{wheelToSpin, stepsToSpin});
        }

        int[][] result = new int[moves.size()][2];
        for (int i = 0; i < moves.size(); i++) {
            result[i] = moves.get(i);
        }
        return result;
    }

    /**
     * Simulates the actions necessaries to win.
     * The machine must be visible in this method.
     * @param n wheels and symbols.
     */
    public void simulate(int n) {
        SlotMachine machine = new SlotMachine(n);
        // Requisito 3 y 4: La máquina debe ser visible en el simulador
        machine.makeVisible();

        Random random = new Random();

        // Iteramos visualmente hasta que las ruedas coincidan (k == 1)
        while (machine.distinctSymbols() > 1) {
            int wheelToSpin = random.nextInt(n) + 1;
            int stepsToSpin = 1;

            // Único método de acción permitido
            machine.spin(wheelToSpin, stepsToSpin);
        }
    }
}