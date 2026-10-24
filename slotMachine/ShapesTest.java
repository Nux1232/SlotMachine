import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Pruebas unitarias para el comportamiento común de Shapes.
 */
public class ShapesTest {

    @Test
    public void shouldSetPositionAndMoveHorizontally() {
        TestShape shape = new TestShape();

        shape.setPosition(10, 20);
        shape.moveHorizontal(15);

        assertEquals(25, shape.getX());
        assertEquals(20, shape.getY());
        assertTrue(shape.getDrawCount() >= 2);
    }

    @Test
    public void shouldSetPositionAndMoveVertically() {
        TestShape shape = new TestShape();

        shape.setPosition(10, 20);
        shape.moveVertical(15);

        assertEquals(10, shape.getX());
        assertEquals(35, shape.getY());
        assertTrue(shape.getDrawCount() >= 2);
    }

    @Test
    public void shouldMoveUsingConvenienceMethods() {
        TestShape shape = new TestShape();
        shape.setPosition(100, 100);

        shape.moveRight();
        shape.moveDown();
        shape.moveLeft();
        shape.moveUp();

        assertEquals(100, shape.getX());
        assertEquals(100, shape.getY());
    }

    @Test
    public void shouldMoveSlowlyInBothDirections() {
        TestShape shape = new TestShape();
        shape.setPosition(10, 20);

        shape.slowMoveHorizontal(5);
        shape.slowMoveVertical(-3);

        assertEquals(15, shape.getX());
        assertEquals(17, shape.getY());
        assertEquals(9, shape.getDrawCount());
    }

    @Test
    public void shouldChangeColorAndVisibility() {
        TestShape shape = new TestShape();

        shape.changeColor("red");
        assertEquals("red", shape.getColor());

        shape.makeVisible();
        assertTrue(shape.isShapeVisible());

        shape.makeInvisible();
        assertFalse(shape.isShapeVisible());
    }

    /**
     * Minimal concrete shape that exposes Shapes state for unit testing.
     */
    private static class TestShape extends Shapes {
        private int drawCount;

        @Override
        protected void draw() {
            drawCount++;
        }

        int getDrawCount() {
            return drawCount;
        }

        int getX() {
            return xPosition;
        }

        int getY() {
            return yPosition;
        }

        String getColor() {
            return color;
        }

        boolean isShapeVisible() {
            return isVisible;
        }
    }
}
