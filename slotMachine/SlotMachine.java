import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import javax.swing.JOptionPane;

/**
 * Represents a slot machine composed of a body, a lever, and up to nine wheels
 * with colored symbols.
 *
 * <p>Demo en BlueJ: crea una maquina vacia con {@code new SlotMachine()}.
 * Luego, en el objeto del Object Bench, ejecuta {@code addWheel(1, "normal")},
 * {@code addSymbol(1, "red")} y {@code makeVisible()}. Las posiciones empiezan
 * en 1 y los textos (tipos y colores) se escriben entre comillas.</p>
 *
 * <p>Tipos de rueda permitidos: {@code normal}, {@code lefty}, {@code rebel} y
 * {@code crazy}. Colores permitidos: red, black, green, orange, yellow,
 * magenta, brown, gray, pink y cyan. Las operaciones con argumentos invalidos
 * lanzan {@link SlotMachineException}; BlueJ muestra el mensaje y permite
 * continuar usando el objeto.</p>
 *
 * @author Samuel Infante Camargo
 * @author Juan Pablo Cuervo Contreras
 * @version Cierre
 */
public class SlotMachine {
    private static final int STEP_DELAY_MILLISECONDS = 100;

    // ok() method+ and Visible Operation.
    private boolean isVisible;
    private boolean lastOperationOk;
    //Constructor
    private Rectangle body; 
    private Circle leverCircle;
    private Rectangle leverHorizontal;
    //Wheels
    private Rectangle leverVertical;
    private ArrayList<Wheel> wheels;
    //Winner
    private boolean winner;

    /**
     * Constructs an empty slot machine and initializes its visual elements.
     *
     * <p>BlueJ: elige este constructor sin parametros. Despues agrega ruedas y
     * simbolos llamando metodos desde el objeto creado.</p>
     */
    public SlotMachine() {
        lastOperationOk = true;
        // wheels
        wheels = new ArrayList<>();
        bodyConstructor();
        leversConstructor();
        // mover
        leverVertical.moveHorizontal(1248);
        leverVertical.moveVertical(80);
        leverCircle.moveHorizontal(1277);
    }

    /**
     * Creates a machine with {@code n} wheels and symbols, randomly started.
     * Values below 0 are treated as 0 and values above 9 as 9.
     *
     * @param n cantidad de ruedas/simbolos; en BlueJ escribe un entero, por
     *          ejemplo {@code 3} (valores fuera de 0..9 se limitan al rango)
     * @throws SlotMachineException if initialization of a wheel or spin fails
     */
    public SlotMachine(int n) throws SlotMachineException {
        lastOperationOk = false;
        wheels = new ArrayList<>();
        bodyConstructor();
        leversConstructor();
        leverVertical.moveHorizontal(1248);
        leverVertical.moveVertical(80);
        leverCircle.moveHorizontal(1277);

        n = Math.max(0, Math.min(n, 9));
        try {
            for (int i = 0; i < n; i++) {
                addWheel(i);
            }
            List<String> palette = Symbol.getAvailableColors();
            String[] baseSymbols = new String[n];
            for (int i = 0; i < n; i++) {
                baseSymbols[i] = palette.get(i % palette.size());
            }
            for (int i = 0; i < n; i++) {
                for (String color : baseSymbols) {
                    wheels.get(i).addSymbolWheel(new Symbol(color));
                }
            }
            Random random = new Random();
            for (int i = 0; i < n; i++) {
                int randomSteps = random.nextInt(n);
                spin(i + 1, randomSteps);
            }
            while (n > 1 && distinctSymbols() == 1) {
                spin(1, 1);
            }
            lastOperationOk = true;
        } catch (SlotMachineException exception) {
            throw new SlotMachineException(exception.getMessage());
        }
        makeInvisible();
    }

    /**
     * Adjusts a position to the closest valid one in the range 1..maxLimit.
     *
     * @param pos position to validate
     * @param maxLimit highest valid position
     * @return the position adjusted to the valid range
     */
    private int validatePosition(int pos, int maxLimit) {
        if (pos <= 0) {
            return 1;
        } else if (pos > maxLimit) {
            return maxLimit;
        }
        return pos;
    }

    /**
     * Creates and configures the body of the slot machine.
     */
    private void bodyConstructor() {
        lastOperationOk = false;
        body = new Rectangle();
        body.changeSize(530,1200);
        // Acá hay que buscar centrarlo por mientras tanto
        body.moveHorizontal(7);
        body.moveVertical(60);
        body.changeColor("blue");
        lastOperationOk = true;
    }

    /**
     * Creates and configures the three visual elements that form the lever.
     */
    private void leversConstructor() {
        lastOperationOk = false;
        // This Rectangle makes the horizontal part of the lever
        leverHorizontal = new Rectangle();
        leverHorizontal.changeColor("black");
        leverHorizontal.changeSize(50, 92);
        // This Rectangle makes the vertical part of the lever
        leverHorizontal.moveHorizontal(1206); // mover
        leverHorizontal.moveVertical(400);
        leverVertical = new Rectangle();
        leverVertical.changeColor("black");
        leverVertical.changeSize(360, 50);
        // The circle of the lever:
        leverCircle = new Circle();
        leverCircle.changeColor("red");
        leverCircle.changeSize(90);
        lastOperationOk = true;
    }

    /**
     * Adds a normal wheel at the requested position. Positions outside the
     * current valid range are adjusted to the nearest valid position.
     *
     * @param pos posicion donde insertar la rueda, empezando en 1; para la
     *            primera rueda escribe {@code 1} en BlueJ
     * @throws SlotMachineException if the machine already has nine wheels
     */
    public void addWheel(int pos) throws SlotMachineException {
        addWheel(pos, Wheel.Type.NORMAL);
    }

    /**
     * Adds a wheel with one of the supported wheel behaviors. Positions outside
     * the current valid range are adjusted to the nearest valid position. A
     * {@code null} type selects the normal wheel for backward compatibility.
     *
     * @param pos posicion donde insertar la rueda, empezando en 1; por ejemplo
     *            {@code 1}
     * @param type comportamiento de la rueda: {@code Wheel.Type.NORMAL},
     *             {@code LEFTY}, {@code REBEL} o {@code CRAZY}; {@code null}
     *             crea una rueda normal
     * @throws SlotMachineException if the machine already has nine wheels
     */
    public void addWheel(int pos, Wheel.Type type) throws SlotMachineException {
        addWheel(pos, WheelFactory.create(type));
    }

    /**
     * Adds a custom wheel implementation at the requested position. This
     * overload is for Java code or BlueJ objects created from a Wheel subclass.
     *
     * @param pos posicion donde insertar la rueda, empezando en 1; por ejemplo
     *            {@code 1}
     * @param wheel objeto de una subclase de {@link Wheel}, creado previamente
     * @throws SlotMachineException if the wheel is null or the machine already
     *         has nine wheels
     */
    public void addWheel(int pos, Wheel wheel) throws SlotMachineException {
        lastOperationOk = false;
        if (wheel == null) {
            throw new SlotMachineException(SlotMachineException.WHEEL_TYPE_NOTFOUND);
        }
        if (wheels.size() >= 9) {
            throw new SlotMachineException(SlotMachineException.WHEEL_LIMIT_REACHED);
        }
        pos = validatePosition(pos, wheels.size() + 1);
        int index = pos - 1;
        wheels.add(index, wheel);
        locationWheel();
        lastOperationOk = true;
    }

    /**
     * Adds a wheel using the case-insensitive names {@code normal}, {@code lefty},
     * {@code rebel}, or {@code crazy}.
     *
     * @param pos posicion donde insertar la rueda, empezando en 1; escribe
     *            {@code 1} para la primera
     * @param type nombre entre comillas; en BlueJ escribe {@code "normal"},
     *             {@code "lefty"}, {@code "rebel"} o {@code "crazy"}
     * @throws SlotMachineException if the type is null or unknown, or the
     *         machine already has nine wheels
     */
    public void addWheel(int pos, String type) throws SlotMachineException {
        lastOperationOk = false;
        if (type == null) {
            throw new SlotMachineException(SlotMachineException.WHEEL_TYPE_NOTFOUND);
        }
        Wheel.Type parsedType = null;
        for (Wheel.Type candidate : Wheel.Type.values()) {
            if (candidate.name().equalsIgnoreCase(type.trim())) {
                parsedType = candidate;
            }
        }
        if (parsedType == null) {
            throw new SlotMachineException(SlotMachineException.WHEEL_TYPE_NOTFOUND);
        }
        addWheel(pos, parsedType);
        lastOperationOk = true;
    }

    /**
     * Convenience parameter order for BlueJ's object bench.
     *
     * @param type nombre entre comillas; en BlueJ escribe {@code "normal"},
     *             {@code "lefty"}, {@code "rebel"} o {@code "crazy"}
     * @param pos posicion donde insertar la rueda, empezando en 1; escribe
     *            {@code 1} para la primera
     * @throws SlotMachineException if the type is null or unknown, or the
     *         machine already has nine wheels
     */
    public void addWheel(String type, int pos) throws SlotMachineException {
        addWheel(pos, type);
    }

    /**
     * Returns the type of the wheel at the requested position.
     *
     * @param pos posicion de la rueda, empezando en 1; escribe {@code 1} para
     *            consultar la primera
     * @return the type of the wheel
     * @throws SlotMachineException if the wheel does not exist
     */
    public Wheel.Type wheelType(int pos) throws SlotMachineException {
        validWheelPosition(pos);
        return wheels.get(pos - 1).getType();
    }

    /**
     * Repositions all wheels in their current order.
     */
    private void locationWheel() throws SlotMachineException {
        lastOperationOk = false;
        int startX = 120;
        int startY = 210;
        int space = 130;
        if (wheels.isEmpty()) {
            throw new SlotMachineException(SlotMachineException.WHEEL_NOT_FOUND);
        }
        for (int i = 0; i < wheels.size(); i++) {
            wheels.get(i).setPositionWheel(startX + (i * space), startY);
            if (this.isVisible) {
                wheels.get(i).makeWheelVisible();
            }
        }
        lastOperationOk = true;
    }

    /**
     * Deletes the wheel at the requested position and shifts the remaining
     * wheels to preserve their order. Positions outside the valid range are
     * adjusted to the nearest valid position.
     *
     * @param pos posicion de la rueda que se eliminara, empezando en 1; por
     *            ejemplo, {@code 1}
     * @throws SlotMachineException if there are no wheels or the wheel is rebel
     */
    public void delWheel(int pos) throws SlotMachineException {
        lastOperationOk = false;
        if (wheels.isEmpty()) {
            throw new SlotMachineException(SlotMachineException.WHEEL_NOT_FOUND);
        }
        pos = validatePosition(pos, wheels.size());
        int index = pos - 1;
        if (wheels.get(index).isRebel()) {
            throw new SlotMachineException(SlotMachineException.WHEEL_REBEL);
        }
        wheels.get(index).makeWheelInvisible();
        wheels.remove(index);
        if (!wheels.isEmpty()) {
            locationWheel();
        }
        lastOperationOk = true;
    }

    /**
     * Adds a normal colored symbol to the wheel at the requested position.
     * Positions outside the valid range are adjusted to the nearest valid one.
     * In BlueJ, call {@code addSymbol(1, "red")} to add a red symbol to the
     * first wheel.
     *
     * @param pos posicion de rueda, empezando en 1; escribe un entero como
     *            {@code 1}
     * @param color color permitido entre comillas; por ejemplo {@code "red"}
     * @throws SlotMachineException if there are no wheels or the color is not allowed
     */
    public void addSymbol(int pos, String color) throws SlotMachineException {
        lastOperationOk = false;
        if (wheels.isEmpty()) {
            throw new SlotMachineException(SlotMachineException.WHEEL_NOT_FOUND);
        }
        if (!Symbol.isAvailableColor(color)) {
            throw new SlotMachineException(SlotMachineException.SYMBOL_NOT_ALLOWED);
        }
        pos = validatePosition(pos, wheels.size());
        wheels.get(pos - 1).addSymbolWheel(new Symbol(color));
        lastOperationOk = true;
    }
    
    /**
     * Adds a symbol of the given type to the wheel at the requested position.
     * The symbol class is loaded by its name, so any subclass of Symbol with a
     * public constructor receiving a color can be used. In BlueJ, for example,
     * enter {@code "ShySymbol"}, {@code 1}, and {@code "red"}.
     *
     * @param type escribe {@code "normal"}, {@code "ShySymbol"} o
     *             {@code "EphemeralSymbol"} entre comillas; los nombres de
     *             subclase distinguen mayusculas y minusculas
     * @param pos posicion de rueda, empezando en 1; escribe un entero como
     *            {@code 1}
     * @param color color permitido entre comillas; por ejemplo {@code "red"}
     * @throws SlotMachineException if there are no wheels, the wheel does not
     *         exist, the color is not allowed, or the type is null, unknown or
     *         not a subclass of Symbol
     */
    public void addSymbol(String type, int pos, String color) throws SlotMachineException {
        lastOperationOk = false;
        if (wheels.isEmpty()) {
            throw new SlotMachineException(SlotMachineException.WHEEL_NOT_FOUND);
        }
        if (pos < 1 || pos > wheels.size()) {
            throw new SlotMachineException(SlotMachineException.WHEEL_NOT_FOUND);
        }
        if (!Symbol.isAvailableColor(color)) {
            throw new SlotMachineException(SlotMachineException.SYMBOL_NOT_ALLOWED);
        }
        if (type == null) {
            throw new SlotMachineException(SlotMachineException.SYMBOL_TYPE_NOTFOUND);
        }

        String className;
        if (type.equalsIgnoreCase("normal")) {
            className = "Symbol";
        } else {
            className = type;
        }

        try {
            Symbol symbol = (Symbol) Class.forName(className).getConstructor(String.class).newInstance(color);
            wheels.get(pos - 1).addSymbolWheel(symbol);
            lastOperationOk = true;
        } catch (ReflectiveOperationException | ClassCastException exception) {
            throw new SlotMachineException(SlotMachineException.SYMBOL_TYPE_NOTFOUND);
        }
    }

    /**
     * Adds an already created symbol to the wheel at the requested position.
     *
     * @param pos posicion de rueda, empezando en 1; escribe un entero como
     *            {@code 1}
     * @param symbol objeto {@link Symbol} existente, creado por ejemplo con
     *               {@code new Symbol("red")}
     * @throws SlotMachineException if the wheel does not exist, or the symbol
     *         is null or has a color that is not allowed
     */
    public void addSymbol(int pos, Symbol symbol) throws SlotMachineException {
        lastOperationOk = false;
        validWheelPosition(pos);
        if (symbol == null || !Symbol.isAvailableColor(symbol.getColor())) {
            throw new SlotMachineException(SlotMachineException.SYMBOL_NOT_ALLOWED);
        }
        wheels.get(pos - 1).addSymbolWheel(symbol);
        lastOperationOk = true;
    }

    /**
     * Removes the symbols of the given color from the wheels that currently
     * show that color.
     *
     * @param color color permitido entre comillas; por ejemplo {@code "red"}
     * @throws SlotMachineException if there are no wheels or the color is not allowed
     */
    public void delSymbol(String color) throws SlotMachineException {
        lastOperationOk = false;
        if (wheels.isEmpty()) {
            throw new SlotMachineException(SlotMachineException.WHEEL_NOT_FOUND);
        }
        if (!Symbol.isAvailableColor(color)) {
            throw new SlotMachineException(SlotMachineException.SYMBOL_NOT_ALLOWED);
        }
        for (Wheel wheel : wheels) {
            if (wheel.getSymbol() != null && wheel.getSymbol().hasColor(color)) {
                wheel.delSymbolWheel(color);
            }
        }
        lastOperationOk = true;
    }

    /**
     * Places a symbol on the requested wheel, replacing the ones it had.
     * Positions outside the valid range are adjusted to the nearest valid one.
     *
     * @param wheel posicion de rueda, empezando en 1; escribe un entero como
     *              {@code 1}
     * @param symbol color permitido entre comillas; por ejemplo {@code "yellow"}
     * @throws SlotMachineException if there are no wheels or the color is not allowed
     */
    public void placeSymbol(int wheel, String symbol) throws SlotMachineException {
        lastOperationOk = false;
        if (wheels.isEmpty()) {
            throw new SlotMachineException(SlotMachineException.WHEEL_NOT_FOUND);
        }
        if (!Symbol.isAvailableColor(symbol)) {
            throw new SlotMachineException(SlotMachineException.SYMBOL_NOT_ALLOWED);
        }
        wheel = validatePosition(wheel, wheels.size());
        wheels.get(wheel - 1).placeSymbolWheel(symbol);
        lastOperationOk = true;
    }

    /**
     * Exchanges two wheels and their symbols.
     *
     * @param first posicion de la primera rueda, empezando en 1; por ejemplo
     *              {@code 1}
     * @param second posicion de otra rueda; escribe un numero diferente, por
     *               ejemplo {@code 2}
     * @throws SlotMachineException if a wheel does not exist, both positions
     *         are the same, or any of the wheels is rebel or locked
     */
    public void swap(int first, int second) throws SlotMachineException {
        lastOperationOk = false;
        validWheelPositions(first, second);

        int firstIndex = first - 1;
        int secondIndex = second - 1;
        if (wheels.get(firstIndex).isRebel() || wheels.get(secondIndex).isRebel()) {
            throw new SlotMachineException(SlotMachineException.WHEEL_REBEL);
        }
        if (wheels.get(firstIndex).isLocked() || wheels.get(secondIndex).isLocked()) {
            throw new SlotMachineException(SlotMachineException.WHEEL_LOCKED);
        }
        Wheel temporaryWheel = wheels.get(firstIndex);
        wheels.set(firstIndex, wheels.get(secondIndex));
        wheels.set(secondIndex, temporaryWheel);
        locationWheel();
        lastOperationOk = true;
    }

    /**
     * Fixes a wheel so a complete spin leaves it unchanged.
     *
     * @param pos posicion de rueda que se bloqueara, empezando en 1; por
     *            ejemplo {@code 1}
     * @throws SlotMachineException if the wheel does not exist or is rebel
     */
    public void lock(int pos) throws SlotMachineException {
        lastOperationOk = false;
        validWheelPosition(pos);
        if (wheels.get(pos - 1).isRebel()) {
            throw new SlotMachineException(SlotMachineException.WHEEL_REBEL);
        }
        wheels.get(pos - 1).lock();
        lastOperationOk = true;
    }

    /**
     * Releases a previously fixed wheel.
     *
     * @param pos posicion de rueda que se desbloqueara, empezando en 1; por
     *            ejemplo {@code 1}
     * @throws SlotMachineException if the wheel does not exist or is rebel
     */
    public void unlock(int pos) throws SlotMachineException {
        lastOperationOk = false;
        validWheelPosition(pos);
        if (wheels.get(pos - 1).isRebel()) {
            throw new SlotMachineException(SlotMachineException.WHEEL_REBEL);
        }
        wheels.get(pos - 1).unlock();
        lastOperationOk = true;
    }
    
    /**
     * Spins the requested wheel once, according to the behavior of its type.
     *
     * @param wheel posicion de rueda que se girara, empezando en 1; por ejemplo
     *              {@code 1}
     * @throws SlotMachineException if the wheel does not exist or is locked
     */
    public void spin(int wheel) throws SlotMachineException {
        lastOperationOk = false;
        if (wheels.isEmpty() || wheel < 1 || wheel > wheels.size()) {
            throw new SlotMachineException(SlotMachineException.WHEEL_NOT_FOUND);
        }
        if (wheels.get(wheel - 1).isLocked()) {
            throw new SlotMachineException(SlotMachineException.WHEEL_LOCKED);
        }
        int index = wheel - 1;
        wheels.get(index).spin(index > 0 ? wheels.get(index - 1) : null);
        lastOperationOk = true;
        if (isVisible) JOptionPane.showMessageDialog(null, "Se ha girado la palanca!");
    }

    /**
     * Spins all the wheels that are not locked. There is a 20% chance that
     * every unlocked wheel ends with the same random color. Then the jackpot
     * state is updated.
     *
     * <p>BlueJ: selecciona {@code spin()} sin parametros para girar todas las
     * ruedas que no esten bloqueadas.</p>
     *
     * @throws SlotMachineException if there are no wheels
     */
    public void spin() throws SlotMachineException {
        lastOperationOk = false;
        if (wheels.isEmpty()) {
            throw new SlotMachineException(SlotMachineException.WHEEL_NOT_FOUND);
        }
        for (int i = 0; i < wheels.size(); i++) {
            Wheel wheel = wheels.get(i);
            if (!wheel.isLocked()) {
                wheel.spin(i > 0 ? wheels.get(i - 1) : null);
            }
        }
        // 20% of Probability of win
        Random random = new Random();
        if (random.nextDouble() < 0.2) {
            String[] available = symbols();
            if (available.length > 0) {
                String symbolWinner = available[random.nextInt(available.length)];
                for (Wheel w : wheels) {
                    if (!w.isLocked()) {
                        w.selectColor(symbolWinner);
                    }
                }
            }
        }
        lastOperationOk = true;
        isjackpot();
    }
    
    /**
     * Rotates one wheel the requested number of steps. Each step spins the
     * wheel according to its type. When the machine is visible, a short pause
     * lets the user see every intermediate symbol.
     *
     * @param wheel posicion de rueda que se girara, empezando en 1; por ejemplo
     *              {@code 1}
     * @param steps cantidad de pasos, debe ser 0 o mayor; por ejemplo
     *              {@code 3}
     * @throws SlotMachineException if there are no wheels, the wheel does not
     *         exist, steps is negative, the wheel is locked or the animation
     *         is interrupted
     */
    public void spin(int wheel, int steps) throws SlotMachineException {
        lastOperationOk = false;
        if (!hasWheels()) {
            throw new SlotMachineException(SlotMachineException.WHEEL_NOT_FOUND);
        }
        validWheelPosition(wheel);
        if (steps < 0) {
            throw new SlotMachineException(SlotMachineException.STEPS_NEGATIVE);
        }
        if (wheels.get(wheel - 1).isLocked()) {
            throw new SlotMachineException(SlotMachineException.WHEEL_LOCKED);
        }
        for (int step = 0; step < steps; step++) {
            int index = wheel - 1;
            wheels.get(index).spin(index > 0 ? wheels.get(index - 1) : null);
            pauseBetweenSteps();
        }
        lastOperationOk = true;
    }

    /**
     * Leaves the machine in the configuration described by an array with one
     * color per wheel. A locked wheel may only keep its current color.
     *
     * <p>Ejemplo en BlueJ para una maquina de tres ruedas:
     * {@code new String[] {"red", "green", "cyan"}}. Escribe un color
     * permitido entre comillas por cada rueda, en el orden de las posiciones.</p>
     *
     * @param setSymbols arreglo con un color permitido por rueda, en orden; el
     *                   largo debe ser igual a la cantidad de ruedas
     * @throws SlotMachineException if there are no wheels, the array is null or
     *         has a wrong size, a color is not allowed, or the configuration
     *         changes a locked wheel
     */
    public void spin(String[] setSymbols) throws SlotMachineException {
        lastOperationOk = false;
        if (!hasWheels()) {
            throw new SlotMachineException(SlotMachineException.WHEEL_NOT_FOUND);
        }
        validateConfiguration(setSymbols);

        for (int i = 0; i < wheels.size(); i++) {
            String currentColor;
            if (wheels.get(i).getSymbol() != null) {
                currentColor = wheels.get(i).getSymbol().getColor();
            } else {
                currentColor = "white";
            }
            if (wheels.get(i).isLocked() && !setSymbols[i].equals(currentColor)) {
                throw new SlotMachineException(SlotMachineException.CONFIGURATION_LOCKED);
            }
        }
        for (int i = 0; i < wheels.size(); i++) {
            if (!wheels.get(i).isLocked()) {
                wheels.get(i).placeSymbolWheel(setSymbols[i]);
            }
        }
        winner = allSymbolsEqual(setSymbols);
        lastOperationOk = true;
        isjackpot();
    }

    /**
     * Checks whether a requested configuration contains the same symbol on
     * every wheel.
     *
     * @param requestedSymbols colors of the requested configuration
     * @return true when all the colors are equal
     */
    private boolean allSymbolsEqual(String[] requestedSymbols) {
        for (int i = 1; i < requestedSymbols.length; i++) {
            if (!requestedSymbols[0].equals(requestedSymbols[i])) {
                return false;
            }
        }
        return true;
    }

    /**
     * Checks whether all the wheels currently show the same visible color.
     *
     * @return true when every wheel shows the same visible color
     */
    private boolean hasJackpot() {
        lastOperationOk = false;
        if (wheels.isEmpty()) return false;
        String first = wheels.get(0).symbolColor();
        if (first == null || first.isEmpty()) return false;
        for (int i = 1; i < wheels.size(); i++) {
            String current = wheels.get(i).symbolColor();
            if (current == null || current.isEmpty() || !first.equals(current)) return false;
        }
        lastOperationOk = true;
        return true;
    }

    /**
     * Splits the textual representation of a configuration into colors.
     *
     * @param setSymbols text with one color per wheel
     * @return the colors found in the text
     * @throws SlotMachineException if the text is null or empty
     */
    private String[] parseConfiguration(String setSymbols) throws SlotMachineException {
        lastOperationOk = false;
        if (setSymbols == null) {
            throw new SlotMachineException(SlotMachineException.CONFIGURATION_INVALID);
        }
        String configurationText = setSymbols.trim();
        if (configurationText.startsWith("[") && configurationText.endsWith("]")) {
            configurationText = configurationText.substring(1, configurationText.length() - 1).trim();
        }
        if (configurationText.isEmpty()) {
            throw new SlotMachineException(SlotMachineException.CONFIGURATION_INVALID);
        }
        lastOperationOk = true;
        return configurationText.split("[,;\\s]+");
    }

    /**
     * Waits between visible steps without hiding interruption from callers.
     *
     * @throws SlotMachineException if the wait is interrupted
     */
    private void pauseBetweenSteps() throws SlotMachineException {
        if (!isVisible) {
            return;
        }
        try {
            Thread.sleep(STEP_DELAY_MILLISECONDS);
        } catch (InterruptedException exception) {
            Thread.currentThread().interrupt();
            lastOperationOk = false;
            throw new SlotMachineException(SlotMachineException.SPIN_INTERRUPTED);
        }
    }

    /**
     * Indicates whether the machine contains at least one wheel.
     *
     * @return true when there is at least one wheel
     */
    private boolean hasWheels() {
        return !wheels.isEmpty();
    }

    /**
     * Returns the colors of all the symbols that exist in the machine, whether
     * or not they are shown in a wheel window. Repeated colors appear once.
     *
     * @return all symbol colors, or an empty array when there are no symbols;
     *         BlueJ displays the result as a {@code String[]} value
     */
    public String[] symbols() {
        lastOperationOk = false;
        ArrayList<String> all = new ArrayList<>();
        for (Wheel wheel : wheels) {
            for (String color : wheel.allColors()) {
                if (!all.contains(color)) {
                    all.add(color);
                }
            }
        }
        lastOperationOk = true;
        return all.toArray(new String[0]);
    }

    /**
     * Returns the number of distinct symbols in the machine.
     *
     * @return the number of distinct colors currently shown; returns 0 when
     *         there are no wheels
     */
    public int distinctSymbols() {
        lastOperationOk = false;
        if (wheels.isEmpty()) {
            return 0;
        }

        String[] visibles = configuration();
        ArrayList<String> uniqueColors = new ArrayList<>();
        for (int i = 0; i < visibles.length; i++) {
            String colorActual = visibles[i];
            if (colorActual != null && !uniqueColors.contains(colorActual)) {
                uniqueColors.add(colorActual);
            }
        }
        lastOperationOk = true;
        return uniqueColors.size();
    }

    /**
     * Returns the current configuration of the slot machine.
     *
     * @return the color shown by each wheel ("white" when a wheel has no
     *         symbol, empty text when a shy symbol is hidden), or an empty
     *         array when the machine has no wheels; BlueJ displays the result
     *         as a {@code String[]} in wheel order
     */
    public String[] configuration() {
        lastOperationOk = false;
        if (wheels.isEmpty()) {
            return new String[0];
        }

        String[] visibles = new String[wheels.size()];
        for (int i = 0; i < wheels.size(); i++) {
            String current = wheels.get(i).symbolColor();
            visibles[i] = current == null ? "white" : current;
        }
        lastOperationOk = true;
        return visibles;
    }

    /**
     * Determines whether all wheels have the same visible symbol. The body of
     * the machine turns green on a jackpot and blue otherwise.
     *
     * <p>BlueJ: call {@code isJackpot()} on the object bench to see the
     * {@code true} or {@code false} result.</p>
     *
     * @return {@code true} when the machine is a jackpot; otherwise
     *         {@code false}
     */
    public boolean isjackpot() {
        lastOperationOk = false;
        winner = hasJackpot();
        if (winner) {
            if (isVisible) JOptionPane.showMessageDialog(null, "GANASTE! FELICITACIONEES!!");
            winner = true;
            body.changeColor("green");
            if (isVisible) {
                makeVisible();
            }
            lastOperationOk = true;
            return true;
        } else {
            body.changeColor("blue");
            winner = false;
            if (isVisible){
                makeVisible();
            }
            lastOperationOk = true;
            return false;
        }
    }

    /**
     * Same jackpot query with conventional Java camel-case spelling.
     * Prefer this method in BlueJ to check whether all wheels show the same
     * visible color.
     *
     * @return {@code true} when the machine is a jackpot; otherwise
     *         {@code false}
     */
    public boolean isJackpot() { return isjackpot(); }

    /**
     * Makes the slot machine and all its wheels visible.
     */
    public void makeVisible() {
        lastOperationOk = false;
        isVisible = true;
        if (isVisible){
            body.makeVisible();
            leverCircle.makeVisible();
            leverHorizontal.makeVisible();
            leverVertical.makeVisible();
            for (Wheel wheel : wheels) {
                wheel.makeWheelVisible();
            }
        }
        lastOperationOk = true;
    }

    /**
     * Makes the slot machine and all its wheels invisible.
     */
    public void makeInvisible() {
        lastOperationOk = false;
        isVisible = false;
        if (isVisible == false) {
            body.makeInvisible();
            leverCircle.makeInvisible();
            leverHorizontal.makeInvisible();
            leverVertical.makeInvisible();
            for (Wheel wheel : wheels) {
                wheel.makeWheelInvisible();
            }
        }
        lastOperationOk = true;
    }

    /**
     * Hides the machine, if necessary, and exits the application. Use
     * {@link #makeInvisible()} instead when only closing the display during a
     * BlueJ demo; this method terminates the running application.
     */
    public void exit() {
        lastOperationOk = false;
        if(isVisible){
            makeInvisible();
        }
        lastOperationOk = true;
        System.exit(0);
    }

    /**
     * Reports whether the last operation was completed successfully.
     *
     * @return {@code true} when the last operation succeeded; otherwise
     *         {@code false}
     */
    public boolean ok() {
        if (lastOperationOk) {
            return true;    
        }
        return false;
    }

    /**
     * Checks two one-based wheel positions.
     *
     * @param first position of the first wheel
     * @param second position of the second wheel
     * @throws SlotMachineException if a wheel does not exist or both positions are the same
     */
    private void validWheelPositions(int first, int second) throws SlotMachineException {
        validWheelPosition(first);
        validWheelPosition(second);
        if (first == second) {
            throw new SlotMachineException(SlotMachineException.WHEEL_SAME_POSITION);
        }
    }

    /**
     * Checks a one-based wheel position without silently adjusting it.
     *
     * @param pos position of the wheel
     * @throws SlotMachineException if the wheel does not exist
     */
    private void validWheelPosition(int pos) throws SlotMachineException {
        if (pos < 1 || pos > wheels.size()) {
            throw new SlotMachineException(SlotMachineException.WHEEL_NOT_FOUND);
        }
    }
    /**
     * Validates a requested configuration.
     *
     * @param requested colors, one per wheel
     * @throws SlotMachineException if the array is null, has a wrong number of
     *         colors, or contains a color that is not allowed
     */
    private void validateConfiguration(String[] requested) throws SlotMachineException {
        if (requested == null || requested.length != wheels.size()) {
            throw new SlotMachineException(SlotMachineException.CONFIGURATION_INVALID);
        }
        for (String color : requested) {
            if (!Symbol.isAvailableColor(color)) {
                throw new SlotMachineException(SlotMachineException.SYMBOL_NOT_ALLOWED);
            }
        }
    }
    
}
    