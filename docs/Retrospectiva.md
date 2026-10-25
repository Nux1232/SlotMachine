## Ciclo 1

1\. ¿Cuáles fueron los mini-ciclos definidos? Justifíquenlos.



2\. ¿Cuál es el estado actual del proyecto en términos de mini-ciclos? ¿por qué?

Mini-Ciclos: Mini-ciclo 1 (Estructura base y componentes visuales): Creación de la clase principal SlotMachine, el constructor, posicionamiento de las ruedas (addWheel, delWheel, locationWheel) y la visibilidad del slotMachine reutilizando el paquete shapes. Se organizó así para garantizar que el entorno gráfico y la jerarquía de objetos funcionaran visualmente antes de programar la lógica interna.

&#x20; 

Mini-ciclo 2 (Gestión de símbolos y operaciones de la máquina): Implementación de métodos para añadir, eliminar y colocar símbolos (addSymbol, delSymbol, placeSymbol), junto con las acciones de giro (spin) y la validación del estado operacional con el método ok(). 

&#x20;

Mini-ciclo 3 (Consultas y estado de victoria): Desarrollo de los métodos de consulta (symbols, distinctSymbols, configuration) y la verificación del jackpot (isjackpot) para cambiar la apariencia visual de la máquina en caso de victoria y agregar un mensaje de felicitaciones por tener buena suerte.


3\. ¿Cuál fue el tiempo total invertido por cada uno de ustedes? (Horas/Hombre)	

* &#x09;JUAN PABLO CUERVO CONTRERAS 14 horas
* &#x09;SAMUEL INFANTE CAMARGO : 14 horas



4\. ¿Cuál consideran fue el mayor logro? ¿Por qué?
	Implementar el diseño para que sea funcional 

5\. ¿Cuál consideran que fue el mayor problema técnico? ¿Qué hicieron para resolverlo?
	Las ArrayList y los diagramas de secuencias, leer la documentación de java, búsqueda de ejemplos de 	diagramas de secuencia y consulta a LLM's 


6\. ¿Qué hicieron bien como equipo? ¿Qué se comprometen a hacer para mejorar los resultados?
	Se trabajo en equipo de manera adecuada, creemos que al empezar un poco antes el proyecto podríamos 	obtener mejores resultados.

7\. Considerando las prácticas XP incluidas en los laboratorios. ¿cuál fue la más útil? ¿por qué?
	Pair programing: porque se hizo supervisión de los progresos en las cuales se aportaron puntos de 	mejora.

8\. ¿Qué referencias usaron? ¿Cuál fue la más útil? Incluyan citas con estándares adecuados.

	Oracle. (s. f.). Class ArrayList. Oracle Help Center. Recuperado de 	https://docs.oracle.com/javase/8/docs/api/java/util/ArrayList.html

&#x20;

&#x09;Oracle. (s. f.). Class JOptionPane. Oracle Help Center. Recuperado de 	https://docs.oracle.com/javase/8/docs/api/javax/swing/JOptionPane.html

&#x20;

&#x09;Oracle. (s. f.). Class Random. Oracle Help Center. Recuperado de 	https://docs.oracle.com/javase/8/docs/api/java/util/Random.html

&#x20;

&#x09;Oracle. (s. f.). Interface List. Oracle Help Center. Recuperado de 	https://docs.oracle.com/javase/8/docs/api/java/util/List.html

	Consultas LLM's para solucionar dudas de programación y implementación 

## Ciclo 2
1. ¿Cuáles fueron los mini-ciclos definidos? Justifíquenlos.

Mini-ciclo 1 (Refactorización y corrección estructural): Revisión y corrección del diagrama de clases y los diagramas de secuencia del ciclo pasado. Justificación: Era fundamental asegurar que el diseño estuviera correcto y sólido antes de empezar a programar la lógica de los nuevos métodos exigidos para la entrega.

Mini-ciclo 2 (Extensión - Gestión de ruedas): Implementación de los requisitos para intercambiar dos ruedas (swap), fijar una rueda (lock) y soltar una rueda (unlock). Justificación: Se requería extender la funcionalidad base para permitir al usuario un mayor control sobre el estado individual de las ruedas de la máquina.

Mini-ciclo 3 (Extensión - Nuevas rotaciones): Desarrollo de los requisitos funcionales para rotar una rueda un número específico de pasos con el método spin(wheel, steps) y dejar la máquina en una configuración dada con el método spin(setSymbols). Justificación: Eran el núcleo de la extensión del comportamiento de giro requerido.

2. ¿Cuál es el estado actual del proyecto en términos de mini-ciclos? ¿por qué?
El estado actual es de un gran adelanto funcional, habiendo completado satisfactoriamente el rediseño visual (diagrama de clases) y la implementación de gran parte de la extensión requerida. Sin embargo, el Mini-ciclo 1 y Mini-ciclo 3 quedaron con pendientes (deuda técnica) que debemos resolver:

Aún debemos cambiar la implementación interna de numwheels a un ArrayList de objetos Wheel.

Tenemos dudas de diseño, particularmente con el método distinctSymbols, ya que actualmente tiene muchas responsabilidades y necesita ser refactorizado.

Faltó cambiar la visibilidad a private de varios métodos encargados de la construcción inicial del SlotMachine.

Se implementaron los nuevos métodos de giro, pero todavía tenemos dudas específicas sobre la implementación final y el comportamiento exacto de spin(setSymbols).

3. ¿Cuál fue el tiempo total invertido por cada uno de ustedes? (Horas/Hombre)

	JUAN PABLO CUERVO CONTRERAS: 12 horas

	SAMUEL INFANTE CAMARGO: 12 horas

4. ¿Cuál consideran fue el mayor logro? ¿Por qué?
Lograr dividir muy bien el trabajo de manera coordinada; mientras uno se encargaba de diseñar y corregir los diagramas (logrando corregir el diagrama de clases), el otro implementaba la lógica de ese diseño en código. Gracias a esta dinámica y a una buena comunicación, se logró tener un avance significativo y funcional del proyecto en un tiempo óptimo.

5. ¿Cuál consideran que fue el mayor problema técnico? ¿Qué hicieron para resolverlo?
El manejo de las responsabilidades de los métodos (específicamente darnos cuenta de que distinctSymbols está sobrecargado) y el planteamiento de la lógica para el método spin(setSymbols) que asigna una configuración dada. Para avanzar, nos apoyamos en la división del trabajo (diseño vs. lógica) y en debates constantes de equipo, aunque seguimos consultando documentación para decidir la mejor manera de refactorizar la estructura hacia ArrayList<Wheel> en los próximos pasos.

6. ¿Qué hicieron bien como equipo? ¿Qué se comprometen a hacer para mejorar los resultados?
Mantuvimos una excelente comunicación y una muy buena división de las tareas, lo que agilizó las prácticas. Nos comprometemos firmemente a saldar la deuda técnica de este ciclo: cambiar la estructura de ruedas a un ArrayList, refactorizar distinctSymbols para aplicar correctamente los principios de diseño (separación de responsabilidades), y cambiar a privado los métodos de construcción del SlotMachine.

7. Considerando las prácticas XP incluidas en los laboratorios. ¿cuál fue la más útil? ¿por qué?
Pair Programming y Diseño Simple. La constante comunicación y el dividir los enfoques (diseño de diagramas vs. codificación de la lógica) permitieron una validación continua del trabajo del otro. Estar conectados validando mutuamente las ideas hizo posible asimilar los nuevos requisitos de la extensión y llevar el proyecto a un muy buen nivel de avance.

8. ¿Qué referencias usaron? ¿Cuál fue la más útil? Incluyan citas con estándares adecuados.

Oracle. (s. f.). Class ArrayList. Oracle Help Center. Recuperado de https://docs.oracle.com/javase/8/docs/api/java/util/ArrayList.html

Oracle. (s. f.). Class JOptionPane. Oracle Help Center. Recuperado de https://docs.oracle.com/javase/8/docs/api/javax/swing/JOptionPane.html

Oracle. (s. f.). Class Random. Oracle Help Center. Recuperado de https://docs.oracle.com/javase/8/docs/api/java/util/Random.html

Oracle. (s. f.). Interface List. Oracle Help Center. Recuperado de https://docs.oracle.com/javase/8/docs/api/java/util/List.html

Oracle. (s. f.). Enum TimeUnit. Oracle Help Center. Recuperado de https://docs.oracle.com/javase/8/docs/api/java/util/concurrent/TimeUnit.html

Consultas a LLM's para solucionar dudas de programación e implementación.

## Ciclo 3

**1. ¿Cuáles fueron los mini-ciclos definidos? Justifíquenlos.**

* **Mini-ciclo 1 (Refactorización y Deuda Técnica):** Resolución de los pendientes del ciclo anterior. Se implementó definitivamente el uso de ArrayList<Wheel>, se refactorizó distinctSymbols para no sobrecargar responsabilidades y se ajustó la visibilidad de los métodos internos a private. 
* **Mini-ciclo 2 (Herramienta de Testing):** Creación del nuevo constructor SlotMachine(int n). Se programó para generar n ruedas con n símbolos idénticos y desordenarlos aleatoriamente. 
* **Mini-ciclo 3 (Extensión - El Solucionador):** Creación de la clase directora SlotMachineContest con los métodos solve(n) y simulate(n). 


**2. ¿Cuál es el estado actual del proyecto en términos de mini-ciclos? ¿por qué?**
El estado actual es de cumplimiento total de la arquitectura y diseño del Tercer Ciclo. Se saldó la deuda técnica estructural y se resolvieron los "glitches" visuales del simulador (como las ruedas que desaparecían al hacer jackpot o parpadeaban en el modo invisible). La clase SlotMachineContest ya está instanciada y lista con su ciclo de control para ejecutar la matemática del algoritmo final.

**3. ¿Cuál fue el tiempo total invertido por cada uno de ustedes? (Horas/Hombre)**

* JUAN PABLO CUERVO CONTRERAS: 15 horas
* SAMUEL INFANTE CAMARGO: 15 horas

**4. ¿Cuál consideran fue el mayor logro? ¿Por qué?**
La correcta separación de responsabilidades exigida por el diseño. Lograr que SlotMachine dejara de tomar decisiones y pasara a ser únicamente una herramienta de prueba invisible, mientras la nueva clase SlotMachineContest asumió el rol del solucionador interactuando a ciegas. Esto blindó nuestra arquitectura.

**5. ¿Cuál consideran que fue el mayor problema técnico? ¿Qué hicieron para resolverlo?**
Los fallos visuales provocados por la manipulación del Canvas desde diferentes clases, específicamente las ruedas dibujándose en el aire durante el método invisible solve o siendo tapadas por el fondo verde del jackpot. Lo resolvimos implementando validaciones estrictas de estado (if (isVisible)) antes de cada orden gráfica y forzando redibujados en capas para no perder los objetos.

**6. ¿Qué hicieron bien como equipo? ¿Qué se comprometen a hacer para mejorar los resultados?**
Hicimos una excelente labor identificando errores previos y adaptando el código sin romper la lógica que ya funcionaba (como los desplazamientos y las validaciones de límites). Nos comprometemos a concentrar el esfuerzo final en la pureza del algoritmo matemático para resolver el acertijo en menos de 10,000 pasos ahora que el entorno de pruebas está perfecto.

**7. Considerando las prácticas XP incluidas en los laboratorios. ¿cuál fue la más útil? ¿por qué?**
Pair Programming y Refactoring. Tener a uno estructurando la lógica de las nuevas clases (SlotMachineContest) mientras el otro refactorizaba el código heredado (SlotMachine) fue vital para no romper el proyecto. El rediseño constante nos permitió asimilar el salto de un simple simulador a un entorno de evaluación algorítmica.

**8. ¿Qué referencias usaron? ¿Cuál fue la más útil? Incluyan citas con estándares adecuados.**

* Oracle. (s. f.). *Class ArrayList*. Oracle Help Center. Recuperado de [https://docs.oracle.com/javase/8/docs/api/java/util/ArrayList.html](https://docs.oracle.com/javase/8/docs/api/java/util/ArrayList.html)
* Oracle. (s. f.). *Class JOptionPane*. Oracle Help Center. Recuperado de [https://docs.oracle.com/javase/8/docs/api/javax/swing/JOptionPane.html](https://docs.oracle.com/javase/8/docs/api/javax/swing/JOptionPane.html)
* Oracle. (s. f.). *Class Random*. Oracle Help Center. Recuperado de [https://docs.oracle.com/javase/8/docs/api/java/util/Random.html](https://docs.oracle.com/javase/8/docs/api/java/util/Random.html)
* Oracle. (s. f.). *Interface List*. Oracle Help Center. Recuperado de [https://docs.oracle.com/javase/8/docs/api/java/util/List.html](https://docs.oracle.com/javase/8/docs/api/java/util/List.html)
* Oracle. (s. f.). *Enum TimeUnit*. Oracle Help Center. Recuperado de [https://docs.oracle.com/javase/8/docs/api/java/util/concurrent/TimeUnit.html](https://docs.oracle.com/javase/8/docs/api/java/util/concurrent/TimeUnit.html)

## Ciclo 4

**1. ¿Cuáles fueron los mini-ciclos definidos? Justifíquenlos.**

* **Mini-ciclo 1 (Jerarquía de símbolos):** Se extendió Symbol con las subclases EphemeralSymbol (reduce su tamaño 10 unidades en cada giro hasta quedar en un punto) y ShySymbol (alterna entre visible e invisible cada vez que es seleccionado en la rueda). Ambas sobrescriben spinEffect(), de modo que Wheel no necesita conocer el tipo concreto de cada símbolo. Se hizo primero porque es la base del requisito de extensibilidad.
* **Mini-ciclo 2 (Tipos de ruedas):** Se añadió a Wheel el enumerado Type (`NORMAL`, `LEFTY`, `REBEL`, `CRAZY`). La rueda *lefty* copia el estado de la rueda a su izquierda al girar, la *rebel* no se deja bloquear, y la *crazy* es el nuevo elemento propuesto por nosotros (requisito 19): en cada giro elige al azar entre rotar, copiar a su vecina o solo aplicar el efecto del símbolo.
* **Mini-ciclo 3 (Integración en SlotMachine):** Se agregó addSymbol(type, pos, color), que crea el símbolo según el tipo pedido y valida tipo, color y posición. El método anterior addSymbol(pos, color) quedó delegando a este con el tipo "normal, para no romper el código previo.
* **Mini-ciclo 4 (Pruebas y diseño):** Se adaptaron las pruebas de los ciclos anteriores a los cambios del código, se escribió SlotMachineC4Test (pruebas propias de este ciclo) y SlotMachineCC4Test (dos pruebas compartidas con otros grupos y dos propias), y se actualizó el diagrama de clases en Astah.

**2. ¿Cuál es el estado actual del proyecto en términos de mini-ciclos? ¿por qué?**
Los mini-ciclos 1, 2 y 3 están implementados: existen los tres tipos de símbolos, los tipos de ruedas y la creación de símbolos por tipo desde SlotMachine,  El mini-ciclo 4 está avanzado pero **no está cerrado**, porque quedan dos pendientes:

* **Revisión de detalles de código y diseño.** Hay que revisar algunos puntos pequeños, por ejemplo que Wheel use instanceof ShySymbol para decidir cómo mostrar el símbolo (lo ideal sería que lo resuelva el propio símbolo con polimorfismo), que los diálogos JOptionPane de SlotMachine se muestren solo cuando la máquina es visible, y que el diagrama de clases coincida exactamente con las firmas del código.
* **Corrección de los diagramas de secuencia.** Quedamos debiendo ajustar los diagramas de secuencia a la nueva estructura, en particular los de addSymbol(type, pos, color) y spin, donde ahora interviene spinEffect() de cada tipo de símbolo.

**3. ¿Cuál fue el tiempo total invertido por cada uno de ustedes? (Horas/Hombre)**

* JUAN PABLO CUERVO CONTRERAS: 12 horas
* SAMUEL INFANTE CAMARGO: 12 horas

**4. ¿Cuál consideran fue el mayor logro? ¿Por qué?**
Demostrar la extensibilidad del diseño mediante polimorfismo. Agregar EphemeralSymbol y ShySymbol no obligó a modificar la lógica de giro de Wheel: basta con que cada símbolo implemente su propio spinEffect(). Esto confirma que la separación de responsabilidades de los ciclos anteriores sirvió para crecer sin reescribir lo que ya funcionaba.

**5. ¿Cuál consideran que fue el mayor problema técnico? ¿Qué hicieron para resolverlo?**
Dos problemas relacionados. Primero, la interacción entre ShySymbol y la visibilidad: Wheel vuelve a mostrar el símbolo seleccionado justo después de aplicar el efecto, lo que anulaba el ocultamiento. Lo resolvimos haciendo que ShySymbol sobrescriba makeVisible() y respete su estado oculto. Segundo, al cambiar el comportamiento de placeSymbol (que dejó de crear símbolos y pasó a rotar o reemplazar), varias pruebas de ciclos anteriores quedaron desactualizadas. Las reescribimos para preparar primero el estado con addSymbol y verificar el resultado final.

**6. ¿Qué hicieron bien como equipo? ¿Qué se comprometen a hacer para mejorar los resultados?**
Mantuvimos la compatibilidad con el código anterior (el addSymbol original sigue funcionando) y adaptamos las pruebas en lugar de eliminarlas. Nos comprometemos a cerrar los pendientes: revisar los detalles de código y diseño señalados, corregir los diagramas de secuencia y verificar que el diagrama de clases y el código queden sincronizados antes de la entrega final.

**7. Considerando las prácticas XP incluidas en los laboratorios. ¿cuál fue la más útil? ¿por qué?**
Las pruebas unitarias y el *refactoring*. Las pruebas nos mostraron de inmediato qué dejó de funcionar al cambiar Wheel y placeSymbol, y el refactoring nos permitió introducir los nuevos tipos sin romper el comportamiento existente. El trabajo en pareja ayudó a revisar cada cambio antes de integrarlo.

**8. ¿Qué referencias usaron? ¿Cuál fue la más útil? Incluyan citas con estándares adecuados.**

* Anthropic. (2026). *Claude Sonnet 5.5* [Modelo de lenguaje de gran tamaño]. https://claude.ai. Se usó como herramienta de guía ante confusiones en cuánto conceptos y errores de sintaxis.
* Oracle. (s. f.). *Inheritance*. The Java Tutorials. https://docs.oracle.com/javase/tutorial/java/IandI/subclasses.html
* Oracle. (s. f.). *Polymorphism*. The Java Tutorials. https://docs.oracle.com/javase/tutorial/java/IandI/polymorphism.html
* Oracle. (s. f.). *Enum Types*. The Java Tutorials. https://docs.oracle.com/javase/tutorial/java/javaOO/enum.html
* JUnit Team. (s. f.). *JUnit 5 User Guide*. https://junit.org/junit5/docs/current/user-guide/
* Oracle. (s. f.). *Class ArrayList*. Oracle Help Center. https://docs.oracle.com/javase/8/docs/api/java/util/ArrayList.html
* Oracle. (s. f.). *Class JOptionPane*. Oracle Help Center. https://docs.oracle.com/javase/8/docs/api/javax/swing/JOptionPane.html
* Oracle. (s. f.). *Class Random*. Oracle Help Center. https://docs.oracle.com/javase/8/docs/api/java/util/Random.html
