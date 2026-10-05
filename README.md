# SlotMachine

## Tipos de rueda en 

En el banco de objetos de BlueJ, crea un objeto `SlotMachine` y ejecuta
`addWheel(int pos, String type)`. `pos` es la posición de inserción empezando
en 1. El texto `type` acepta estos nombres (sin importar mayúsculas):

| Tipo | Comportamiento al girar |
| --- | --- |
| `normal` | Cambia a un símbolo aleatorio. |
| `lefty` | Copia el símbolo de la rueda inmediatamente a su izquierda. La primera rueda no tiene vecina a la izquierda y cambia aleatoriamente. |
| `rebel` | No puede bloquearse, intercambiarse ni eliminarse. |
| `crazy` | En cada giro tiene posibilidades iguales de cambiar a un símbolo aleatorio, copiar el símbolo de su izquierda o conservar su símbolo. Si no tiene vecina izquierda, la opción de copiar conserva el símbolo. |

También se puede elegir el tipo desde el menú de BlueJ con
`addWheel(int pos, Wheel.Type type)`, usando `Wheel.Type.NORMAL`,
`Wheel.Type.LEFTY`, `Wheel.Type.REBEL` o `Wheel.Type.CRAZY`.
