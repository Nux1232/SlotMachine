# Pruebas de aceptación manuales — Slot Machine

## Objetivo

Comprobar desde BlueJ, mediante llamadas a los métodos públicos de las clases, que el proyecto satisface sus funciones principales. Estos escenarios son recorridos manuales; no son código de pruebas unitarias.

## Preparación

1. Abrir `slotMachine` en BlueJ y pulsar **Compile**.
2. Para cada recorrido, crear la instancia indicada desde el diagrama y ejecutar sus métodos desde el Object Bench.
3. Las posiciones de rueda empiezan en `1`. En los cuadros de BlueJ, los argumentos `String` llevan comillas, por ejemplo `"red"`.
4. Colores permitidos: `"red"`, `"black"`, `"green"`, `"orange"`, `"yellow"`, `"magenta"`, `"brown"`, `"gray"`, `"pink"` y `"cyan"`.
5. Para cerrar solo la ventana de una máquina, llamar `makeInvisible()`. No usar `exit()`, porque termina la aplicación.
6. Al provocar un error, BlueJ mostrará la excepción. Algunos métodos también actualizan el indicador `ok()`; cuando el recorrido lo indique, consultarlo antes de ejecutar otra operación.

---

## PA-01 — Crear, configurar y mostrar ruedas de todos los tipos

**Tipo:** Recorrido exitoso — creación, orden, símbolos, comportamiento y visualización.

**Llamadas desde BlueJ**

1. Crear `new SlotMachine()`.
2. Sobre la instancia, agregar cuatro ruedas con `addWheel(int, String)`:

   | `pos` | `type` |
   |---:|---|
   | `1` | `"normal"` |
   | `2` | `"lefty"` |
   | `3` | `"rebel"` |
   | `4` | `"crazy"` |

3. Agregar símbolos con `addSymbol(int, String)`:

   | `pos` | `color` |
   |---:|---|
   | `1` | `"red"` |
   | `1` | `"green"` |
   | `2` | `"black"` |
   | `2` | `"yellow"` |
   | `3` | `"cyan"` |
   | `4` | `"orange"` |
   | `4` | `"pink"` |

4. Consultar `wheelType(1)`, `wheelType(2)`, `wheelType(3)`, `wheelType(4)`, `configuration()`, `symbols()` y `distinctSymbols()`.
5. Llamar `makeVisible()`.
6. Girar ruedas de forma individual: `spin(1, 1)`, `spin(2, 1)`, `spin(3, 1)` y `spin(4, 1)`. Consultar otra vez `configuration()`.
7. Finalizar con `makeInvisible()`.

**Resultado esperado**

- La configuración contiene cuatro colores, en orden de rueda; `wheelType` devuelve los cuatro tipos en el orden agregado.
- `symbols()` lista colores presentes en las ruedas sin repetirlos; `distinctSymbols()` cuenta los colores que se muestran actualmente.
- La rueda normal avanza entre sus símbolos; la lefty copia el símbolo seleccionado de la rueda inmediatamente a su izquierda cuando se gira.
- La rueda crazy conserva un símbolo válido aunque su resultado dependa de una elección aleatoria.
- La rueda rebel puede girarse, pero no bloquearse, desbloquearse, intercambiarse ni eliminarse.
- La ventana muestra cuerpo, palanca, ruedas y barras de tipo: blanca para normal, cyan para lefty, roja para rebel y magenta para crazy. No se muestran puntos ni figuras adicionales para identificar ruedas.
- Al ocultar la máquina, desaparecen sus elementos gráficos y el objeto sigue disponible en BlueJ.

---

## PA-02 — Efectos de símbolos especiales y lectura de la configuración

**Tipo:** Recorrido exitoso — símbolos `ShySymbol` y `EphemeralSymbol`.

**Llamadas desde BlueJ**

1. Crear `new SlotMachine()`.
2. Agregar dos ruedas normales con `addWheel(1)` y `addWheel(2)`.
3. Agregar un símbolo tímido a la primera rueda: `addSymbol("ShySymbol", 1, "red")`.
4. Agregar dos símbolos a la segunda: `addSymbol("EphemeralSymbol", 2, "green")` y `addSymbol(2, "black")`.
5. Consultar `configuration()` y llamar `makeVisible()`.
6. Ejecutar varias veces `spin(1, 1)` para volver a seleccionar el símbolo tímido; ejecutar varias veces `spin(2, 1)` para que el efímero vuelva a seleccionarse.
7. Después de cada giro, consultar `configuration()`. Al terminar, llamar `makeInvisible()`.

**Resultado esperado**

- La configuración devuelve una entrada por rueda. Un símbolo tímido oculto se reporta como texto vacío; un símbolo visible devuelve su color.
- Cada vez que `ShySymbol` es seleccionado alterna entre visible y oculto.
- `EphemeralSymbol` reduce su tamaño cuando se selecciona, hasta alcanzar su tamaño mínimo.
- Los giros no deben provocar colores ajenos a la paleta ni dejar inválida la máquina.

---

## PA-03 — Bloqueos, intercambio y protección de una rueda rebel

**Tipo:** Recorrido exitoso y fallido — reglas de modificación y conservación de estado.

**Llamadas desde BlueJ**

1. Crear `new SlotMachine()` y agregar tres ruedas: normal en la posición 1, normal en la 2 y rebel en la 3.
2. Establecer símbolos con `spin(new String[] {"red", "green", "cyan"})`; consultar `configuration()`.
3. Ejecutar `lock(1)` y luego `spin(1)`.
4. Ejecutar `spin(new String[] {"black", "orange", "magenta"})`. Tras la excepción, consultar inmediatamente `ok()` y luego `configuration()`.
5. Ejecutar `spin(new String[] {"red", "orange", "magenta"})`, que conserva el color de la rueda bloqueada y cambia las otras ruedas.
6. Intentar individualmente `lock(3)`, `unlock(3)`, `delWheel(3)` y `swap(1, 3)`.
7. Intentar `swap(1, 1)`, luego desbloquear la rueda 1 con `unlock(1)` y ejecutar `swap(1, 2)`.

**Resultado esperado**

- `spin(1)` sobre la rueda bloqueada lanza `WHEEL_LOCKED`.
- La configuración que intenta cambiar la rueda 1 lanza `CONFIGURATION_LOCKED`, `ok()` indica fallo antes de hacer otra consulta y ninguna rueda cambia.
- La configuración que conserva `"red"` en la rueda 1 es aceptada; las ruedas desbloqueadas toman `"orange"` y `"magenta"`.
- Las operaciones prohibidas sobre la rebel lanzan `WHEEL_REBEL` y conservan las ruedas.
- Intercambiar una rueda consigo misma lanza `WHEEL_SAME_POSITION`.
- Después de desbloquear la primera rueda, el intercambio de dos ruedas normales se acepta y su configuración cambia de orden.

---

## PA-04 — Límites de ruedas, posiciones inválidas y símbolos rechazados

**Tipo:** Recorrido fallido — excepciones, límites y ausencia de cambios parciales.

**Llamadas desde BlueJ**

1. Crear `new SlotMachine()` e intentar `spin(1)`, `addSymbol(1, "red")` y `delWheel(1)` sin agregar ruedas.
2. Agregar una rueda con `addWheel(1, "normal")`. Consultar `wheelType(2)` y llamar `spin(1, -1)`.
3. Con la rueda todavía vacía, intentar agregar `addSymbol(1, "purple")` y después `addSymbol(1, "blue")`.
4. Intentar `addWheel(2, "unknown")`.
5. Agregar ruedas hasta tener nueve; intentar una décima con `addWheel(10)`. Consultar `configuration()` para comprobar el tamaño.

**Resultado esperado**

- Las operaciones que requieren ruedas en una máquina vacía lanzan `WHEEL_NOT_FOUND`.
- Consultar una rueda inexistente lanza `WHEEL_NOT_FOUND`; los pasos negativos lanzan `STEPS_NEGATIVE`.
- Los colores no admitidos lanzan `SYMBOL_NOT_ALLOWED`; el tipo desconocido lanza `WHEEL_TYPE_NOTFOUND`.
- La décima rueda lanza `WHEEL_LIMIT_REACHED` y la configuración conserva exactamente nueve entradas.
- Las llamadas inválidas no agregan símbolos ni ruedas. Comprueba la cantidad con `configuration()` después del intento de agregar la décima rueda.

---

## PA-05 — Configuración directa, jackpot y control visual del resultado

**Tipo:** Recorrido exitoso — configuración completa y detección de victoria.

**Llamadas desde BlueJ**

1. Crear `new SlotMachine()` y agregar tres ruedas normales con `addWheel(1)`, `addWheel(2)` y `addWheel(3)`.
2. Llamar `spin(new String[] {"red", "green", "cyan"})` y consultar `configuration()` e `isJackpot()`.
3. Intentar `spin(new String[] {"red", "green"})`; debe fallar. Consultar `configuration()` para confirmar que no cambió.
4. Intentar `spin(new String[] {"red", "purple", "cyan"})`; debe fallar por el color. Consultar `configuration()` para confirmar que tampoco cambió.
5. Llamar `spin(new String[] {"yellow", "yellow", "yellow"})` y consultar `configuration()` e `isJackpot()`.
6. Llamar `makeVisible()`. Repetir `isJackpot()` para observar el estado visual; si BlueJ muestra un diálogo de victoria, cerrarlo. Terminar con `makeInvisible()`.

**Resultado esperado**

- La primera configuración aparece en el mismo orden y `isJackpot()` devuelve `false`.
- La segunda configuración deja las tres ruedas con `"yellow"` y `isJackpot()` devuelve `true`.
- Con la ventana visible, un jackpot pinta de verde el cuerpo y muestra el mensaje de victoria; cuando no hay jackpot, el cuerpo queda azul.
- La configuración debe tener exactamente tres colores. Un arreglo de tamaño incorrecto, por ejemplo `new String[] {"red", "green"}`, lanza `CONFIGURATION_INVALID`; un color inválido lanza `SYMBOL_NOT_ALLOWED`.

---

## PA-06 — Canvas, figuras y operaciones de visibilidad

**Tipo:** Recorrido exitoso — componentes gráficos del proyecto.

**Llamadas desde BlueJ**

1. Crear una instancia de `Circle`, otra de `Rectangle` y otra de `Triangle` desde el diagrama.
2. En cada objeto, probar `changeColor(...)`, `changeSize(...)`, `moveHorizontal(...)`, `moveVertical(...)` y `makeVisible()`. Usar colores válidos, por ejemplo `"red"`, `"green"` y `"cyan"`.
3. Mover y redimensionar las figuras una vez visibles; luego ejecutar `makeInvisible()` en cada instancia.
4. Crear una máquina con una rueda y un símbolo, llamar `makeVisible()`, agregar otra rueda mientras sigue visible y comprobar que también aparece; ocultar la máquina con `makeInvisible()`.

**Resultado esperado**

- El Canvas muestra círculo, rectángulo y triángulo con la forma, color, tamaño y posición configurados; las modificaciones se reflejan al ejecutarlas.
- `makeInvisible()` borra las figuras y se puede volver a llamar sin cerrar la aplicación.
- Una rueda agregada a una máquina ya visible también se dibuja y se posiciona en el orden correcto. Al ocultar la máquina se ocultan cuerpo, palanca, ruedas, barras y símbolos.

---

## PA-07 — Resolver y simular el reto de alineación

**Tipo:** Recorrido exitoso / límite — solver automático y simulación.

**Llamadas desde BlueJ**

1. Crear `new SlotMachineContest()`.
2. Llamar `solve(0)` y revisar la matriz retornada.
3. Llamar `solve(3)` y revisar cada fila de la matriz.
4. Llamar `simulate(3)` en un entorno con interfaz gráfica y observar la ejecución.
5. Como comprobación de límites, llamar `solve(-1)` y `solve(12)`.

**Resultado esperado**

- `solve(0)` devuelve una matriz vacía.
- Para tres ruedas, cada acción retornada tiene dos enteros: posición de rueda entre 1 y 3, y cantidad de pasos igual a 1. La matriz puede variar entre ejecuciones por la aleatoriedad.
- `simulate(3)` presenta la máquina, ejecuta giros hasta alcanzar un jackpot o agotar el máximo de intentos y consulta el resultado final.
- La cantidad de ruedas se limita a 0..9 en el constructor; no se espera una secuencia específica ni se debe exigir que el solver encuentre jackpot en todos los intentos.

---

## Criterio global de aceptación

El recorrido se acepta si las llamadas válidas producen los valores y efectos descritos, las inválidas muestran la excepción indicada sin dejar cambios parciales, y la interfaz gráfica concuerda con el estado consultado. En los comportamientos aleatorios se comprueban límites e invariantes, no un resultado exacto predeterminado.
