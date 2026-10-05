# SlotMachine

Proyecto académico en Java que modela una máquina tragamonedas gráfica. Permite construir una máquina con ruedas configurables, agregar símbolos, girar o fijar ruedas, consultar la configuración y comprobar si se obtuvo un jackpot. También incluye símbolos con comportamientos especiales y una clase para simular el problema de alineación.

## Funcionalidades

- Crear una máquina vacía o inicializarla con hasta nueve ruedas.
- Añadir, eliminar e intercambiar ruedas.
- Elegir entre cuatro tipos de rueda: normal, lefty, rebel y crazy.
- Agregar varios símbolos a una rueda, quitar símbolos o dejar uno específico.
- Girar una rueda, girar toda la máquina o establecer una configuración directamente.
- Fijar y liberar ruedas para que no cambien durante los giros.
- Consultar los símbolos, la configuración actual, la cantidad de símbolos distintos y el estado del jackpot.
- Mostrar u ocultar la representación gráfica de la máquina.
- Usar símbolos normales, efímeros o tímidos.
- Generar configuraciones iniciales y ejecutar una simulación/solución con `SlotMachineContest`.

## Requisitos

- Java JDK instalado.
- BlueJ o IntelliJ IDEA para abrir el proyecto e interactuar con sus clases.
- JUnit 5 para ejecutar las pruebas automatizadas.

El proyecto no incluye una clase `main`, ni configuración de Maven o Gradle. La máquina se utiliza creando un objeto desde BlueJ o ejecutando los métodos desde un entorno Java/IDE. El código fuente está en `slotMachine/`; en IntelliJ, esa carpeta está configurada como raíz de fuentes.

## Empezar en BlueJ

1. Abre el proyecto en BlueJ desde la carpeta `slotMachine`.
2. Compila el proyecto.
3. Crea un objeto `SlotMachine` desde el banco de objetos. El constructor sin argumentos crea una máquina vacía.
4. Invoca los métodos sobre ese objeto. Por ejemplo:

```java
addWheel(1);
addWheel(2, "lefty");
addSymbol(1, "red");
addSymbol(1, "green");
addSymbol(2, "black");
makeVisible();
spin(1);
spin();
configuration();
isJackpot();
```

Las posiciones de ruedas empiezan en **1**. La máquina admite hasta **nueve ruedas**. También puedes crear una máquina inicializada con `new SlotMachine(3)`: crea tres ruedas con los mismos tres colores disponibles en cada rueda y selecciona una configuración inicial aleatoria.

## Operaciones principales

### Ruedas

| Operación | Descripción |
| --- | --- |
| `addWheel(pos)` | Añade una rueda normal en la posición indicada. |
| `addWheel(pos, type)` | Añade una rueda indicando el tipo como texto, sin distinguir mayúsculas. |
| `addWheel(pos, Wheel.Type)` | Añade una rueda usando `Wheel.Type.NORMAL`, `LEFTY`, `REBEL` o `CRAZY`. |
| `delWheel(pos)` | Elimina la rueda de esa posición, salvo que sea rebelde. |
| `swap(first, second)` | Intercambia dos ruedas; no permite intercambiar una rueda rebelde o fijada. |
| `lock(pos)` / `unlock(pos)` | Fija o libera una rueda. Las ruedas rebeldes no se pueden fijar. |
| `wheelType(pos)` | Devuelve el tipo de rueda de esa posición. |

Los tipos de rueda determinan qué ocurre al girar:

| Tipo | Comportamiento |
| --- | --- |
| `normal` | Avanza al siguiente símbolo de los que tiene asignados. |
| `lefty` | Copia el símbolo seleccionado en la rueda inmediatamente a su izquierda. La primera rueda, que no tiene vecina, avanza normalmente. |
| `rebel` | Avanza normalmente, pero no se puede fijar, intercambiar ni eliminar. |
| `crazy` | Elige aleatoriamente entre avanzar, copiar el símbolo de la rueda izquierda o conservar el símbolo. Si no hay vecina izquierda, la opción de copiar conserva el símbolo. |

### Símbolos

Los colores admitidos son `red`, `black`, `green`, `orange`, `yellow`, `magenta`, `brown`, `gray`, `pink` y `cyan`.

| Operación | Descripción |
| --- | --- |
| `addSymbol(pos, color)` | Añade un símbolo normal a la rueda. |
| `addSymbol(type, pos, color)` | Añade un símbolo `normal`, `ephemeral` o `shy`. |
| `placeSymbol(pos, color)` | Reemplaza los símbolos de esa rueda por uno del color indicado. |
| `delSymbol(color)` | Quita de las ruedas el símbolo seleccionado que tenga ese color. |

- **Normal:** conserva su tamaño y visibilidad.
- **Ephemeral:** se reduce cada vez que la rueda lo selecciona, hasta alcanzar su tamaño mínimo.
- **Shy:** alterna entre visible y oculto cuando la rueda lo selecciona. Un símbolo oculto no cuenta como coincidencia para el jackpot.

### Giros, consulta y jackpot

- `spin(pos)` gira una rueda concreta según su tipo.
- `spin(pos, steps)` aplica varios pasos a una rueda; `steps` no puede ser negativo.
- `spin()` gira todas las ruedas que no estén fijadas. Además, tiene una probabilidad del 20 % de asignar un mismo color aleatorio a todas las ruedas no fijadas.
- `spin("red, green, black")` establece directamente un color por rueda. También acepta colores separados por espacios o punto y coma; la cantidad debe coincidir con el número de ruedas. Una rueda fijada solo puede conservar su color actual.
- `symbols()` devuelve los símbolos seleccionados; `configuration()` devuelve la configuración actual. En una rueda sin símbolo, la configuración usa `white`; los símbolos tímidos ocultos se representan como una cadena vacía.
- `distinctSymbols()` devuelve cuántos símbolos distintos hay en la configuración.
- `isJackpot()` devuelve `true` cuando todas las ruedas muestran el mismo símbolo visible. La máquina cambia a verde cuando hay jackpot y a azul cuando no lo hay.
- `ok()` indica si la última operación se completó correctamente. Los mensajes de error se muestran cuando la máquina está visible.

## Solucionador y simulación

`SlotMachineContest` ofrece dos métodos para probar el problema de alinear las ruedas:

```java
int[][] acciones = new SlotMachineContest().solve(3);
new SlotMachineContest().simulate(3);
```

`solve(n)` devuelve una secuencia de intentos en la que cada fila contiene `[posiciónDeRueda, pasos]`. El proceso selecciona ruedas aleatoriamente y se detiene al alinear los símbolos o después de 10 000 intentos; por tanto, no garantiza encontrar un jackpot en todos los casos. `simulate(n)` ejecuta el mismo proceso y muestra la máquina cuando el entorno tiene pantalla disponible. Ambos métodos crean internamente una máquina de prueba con `n` ruedas.

## Pruebas

Las clases de prueba disponibles como código fuente están en `slotMachine/` y utilizan **JUnit 5**. En IntelliJ, configura JUnit 5 como dependencia de pruebas y ejecuta `ShapesTest`, `SlotMachineCC4Test`, `SlotMachineContestTest` y `SlotMachineContestCTest`. BlueJ permite compilar y utilizar las clases principales, pero las pruebas JUnit requieren tener JUnit 5 configurado.

## Estructura del proyecto

```text
.
├── README.md
├── docs/
│   ├── Retrospectiva.md
│   └── slotMachine Diagrams.asta
└── slotMachine/
    ├── SlotMachine.java
    ├── SlotMachineContest.java
    ├── Wheel.java
    ├── Symbol.java
    ├── EphemeralSymbol.java
    ├── ShySymbol.java
    ├── Shapes.java, Circle.java, Rectangle.java, Triangle.java
    └── pruebas JUnit 5 y archivos del proyecto BlueJ
```

La interfaz gráfica reutiliza las clases de figuras y el lienzo del proyecto educativo Shapes de BlueJ.
