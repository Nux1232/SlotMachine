import java.util.ArrayList;
import java.util.Random;
/**
 * Class that resolves and simulates the SlotMachine problem
 */
public class SlotMachineContest {
    /**
     * Return the secuence of actions (i, j) to win.
     * The machine must be invisible
     * @param n wheels and symbols
     * @return Matrix where each row is an action: {wheel, steps}
     */
    public int[][] solve(int n) {
        SlotMachine machine = new SlotMachine(n);
        ArrayList<int[]> moves = new ArrayList<>();
        machine.makeInvisible();

        int k = machine.distinctSymbols();
        Random random = new Random();

        while(k >1 && moves.size() < 10000) {
            // Acá me guío con todas las herramientas que existen
            // y las que habían en el video.
            int wheelToSpin = random.nextInt(n) + 1;
            int stepsToSpin = 1;

            machine.spin(wheelToSpin, stepsToSpin);
            moves.add(new int[]{wheelToSpin, stepsToSpin});
            k = machine.distinctSymbols();
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

        machine.makeVisible();

        int k = machine.distinctSymbols();
        Random random = new Random();
        int intentos = 0;
        while (k >1 && intentos < 10000) {
            int wheelToSpin = random.nextInt(n) + 1;
            int stepsToSpin = 1;

            machine.spin(wheelToSpin, stepsToSpin);
            k = machine.distinctSymbols();
            intentos++;

            if (k == 1) {
                machine.isjackpot();
                break;
            }
        }
    }
}