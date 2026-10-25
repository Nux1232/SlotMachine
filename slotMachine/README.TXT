MAQUINA TRAGAMONEDAS
====================

El proyecto modela y dibuja una maquina tragamonedas con hasta nueve ruedas.
Cada rueda muestra un simbolo de color. La maquina permite girar, bloquear,
intercambiar y configurar ruedas, y comprobar si hay jackpot.

EJECUTAR UNA DEMO MANUAL EN BLUEJ
=================================

1. Abre la carpeta slotMachine en BlueJ y pulsa Compile para compilar el
   proyecto.
2. En el diagrama, haz clic derecho sobre SlotMachine y elige el constructor
   SlotMachine() sin parametros. Se creara un objeto en el Object Bench.
3. Haz clic derecho sobre ese objeto y elige addWheel(int, String). Completa
   los cuadros de parametros asi:

       pos: 1
       type: "normal"

   Repite addWheel para crear tres ruedas, usando estos pares:

       pos: 2    type: "lefty"
       pos: 3    type: "rebel"

   Los nombres de tipo aceptados son "normal", "lefty", "rebel" y "crazy";
   no distinguen entre mayusculas y minusculas.

4. En el mismo objeto, llama tres veces a addSymbol(int, String), con estos
   valores:

       pos: 1    color: "red"
       pos: 2    color: "green"
       pos: 3    color: "cyan"

   Los colores permitidos son red, black, green, orange, yellow, magenta,
   brown, gray, pink y cyan. Escribe los textos entre comillas en los cuadros
   de BlueJ.

5. Llama makeVisible() para mostrar la maquina.
6. Prueba spin(1) para girar solo la primera rueda. Luego usa spin() para girar
   todas las ruedas que no esten bloqueadas. La rueda Lefty copia el simbolo
   de la rueda de su izquierda.
7. Usa configuration() para consultar los colores que se muestran, y
   isJackpot() para comprobar si todas las ruedas muestran el mismo color.
8. Al terminar, llama makeInvisible() para ocultar la ventana.

EJEMPLOS PARA LOS CUADROS DE PARAMETROS
---------------------------------------

- addWheel(int, String): 1 y "crazy"
- addSymbol(int, String): 1 y "yellow"
- spin(int, int): 1 y 3 (gira la rueda 1 tres pasos)
- spin(String[]): new String[] {"red", "red", "red"}
- swap(int, int): 1 y 2
- lock(int) / unlock(int) / delWheel(int): 1

Las posiciones de rueda empiezan en 1. Para spin(String[]), escribe una
expresion Java como la anterior: debe haber exactamente un color por rueda.

DEMO DE EXCEPCIONES
-------------------

Estas llamadas son intencionalmente invalidas y BlueJ debe mostrar
SlotMachineException:

- lock(3) con la tercera rueda rebel: WHEEL_REBEL.
- swap(1, 1): WHEEL_SAME_POSITION.
- spin(1, -1): STEPS_NEGATIVE.
- spin(99): WHEEL_NOT_FOUND.
- addWheel(1, "unknown"): WHEEL_TYPE_NOTFOUND.
- addSymbol(1, "purple"): SYMBOL_NOT_ALLOWED.

Para probar WHEEL_LIMIT_REACHED, crea una maquina nueva y agrega nueve ruedas;
la siguiente llamada addWheel debe fallar. Una excepcion significa que la
operacion solicitada no se realizo; puedes continuar con otras llamadas.

RUEDAS Y SIMBOLOS
-----------------

Wheel es la clase base. NormalWheel avanza entre sus simbolos, LeftyWheel copia
el simbolo de la rueda izquierda, RebelWheel no permite bloquear, desbloquear,
intercambiar ni eliminar la rueda, y CrazyWheel aplica un comportamiento
aleatorio. WheelFactory conserva la API basada en Wheel.Type. Para agregar un
tipo nuevo, extiende Wheel y pasa una instancia a addWheel(pos, wheel).

Symbol representa un simbolo de color. ShySymbol alterna su visibilidad y
EphemeralSymbol se hace mas pequeno cuando es seleccionado. Shapes, Circle,
Rectangle y Triangle son primitivas graficas; Canvas administra la superficie
de dibujo compartida.

EXCEPCIONES
-----------

SlotMachineException es una excepcion comprobada (checked): Java exige que
los metodos que pueden producirla la declaren, y BlueJ muestra el error si una
operacion invalida la lanza. Los casos principales son:

- WHEEL_NOT_FOUND: posicion inexistente o maquina sin ruedas.
- WHEEL_TYPE_NOTFOUND: tipo de rueda desconocido/null o rueda personalizada
  null.
- WHEEL_LIMIT_REACHED: se intento superar el maximo de nueve ruedas.
- WHEEL_REBEL: operacion prohibida sobre una rueda rebel.
- WHEEL_LOCKED: se intento girar o intercambiar una rueda bloqueada.
- WHEEL_SAME_POSITION: se intento intercambiar una rueda consigo misma.
- SYMBOL_NOT_ALLOWED / SYMBOL_TYPE_NOTFOUND: color o tipo de simbolo invalido.
- CONFIGURATION_INVALID / CONFIGURATION_LOCKED: configuracion con estructura
  incorrecta o que cambia una rueda bloqueada.
- STEPS_NEGATIVE / SPIN_INTERRUPTED: cantidad de pasos negativa o giro
  animado interrumpido.

La sobrecarga addWheel(int, Wheel.Type) conserva el comportamiento previo:
si el argumento enum es null, crea una rueda normal. La sobrecarga que recibe
String rechaza null o un nombre desconocido.

COMPILAR Y EJECUTAR LAS PRUEBAS
-------------------------------

El proyecto Maven esta definido en pom.xml. Desde esta carpeta ejecuta:

    mvn test

Las pruebas usan JUnit 5 y cubren figuras, ruedas, simbolos, operaciones de la
maquina y el solver del concurso.
