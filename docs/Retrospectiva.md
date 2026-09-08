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
