Slot Machine
============

This Java project models and displays a slot machine with up to nine wheels.
Each wheel displays one selected colored symbol, and the machine supports
spinning, locking, swapping, configuration, and jackpot detection.

Wheel types
-----------

Wheel is the base class for wheel behavior. The built-in subclasses are:

- NormalWheel: advances through its symbols in insertion order.
- LeftyWheel: copies the selected symbol from its left neighbor.
- RebelWheel: cannot be locked, unlocked, swapped, or deleted.
- CrazyWheel: randomly advances, copies its left neighbor, or reapplies the
  selected symbol's effect.

SlotMachine keeps the legacy Wheel.Type API and creates the matching subclass
through WheelFactory. Custom wheel behavior can be added by extending Wheel
and passing an instance to SlotMachine.addWheel(position, wheel).

Symbols and graphics
--------------------

Symbol represents a colored shape. ShySymbol alternates visibility, while
EphemeralSymbol shrinks when selected. Shapes, Circle, Rectangle, and Triangle
provide the drawing primitives; Canvas owns the shared drawing surface.

The supported symbol colors are red, black, green, orange, yellow, magenta,
brown, gray, pink, and cyan.

Build and tests
---------------

The Maven project file is pom.xml. From this directory, run:

    mvn test

The tests use JUnit 5 and cover shapes, wheel and symbol behavior, slot-machine
operations, and the contest solver.
