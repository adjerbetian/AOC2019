package day11

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

internal class PaintingRobotTest {
    @Test
    fun move() {
        val robot = PaintingRobot()

        assertEquals(Position(0, 0), robot.position)
        assertEquals(Up, robot.direction)

        robot.moveForward()
        assertEquals(Position(0, 1), robot.position)

        robot.moveForward()
        assertEquals(Position(0, 2), robot.position)
    }

    @Test
    fun turn() {
        val robot = PaintingRobot()

        robot.turnRight()
        assertEquals(Right, robot.direction)

        robot.turnRight()
        assertEquals(Down, robot.direction)

        robot.turnRight()
        assertEquals(Left, robot.direction)

        robot.turnRight()
        assertEquals(Up, robot.direction)

        robot.turnLeft()
        assertEquals(Left, robot.direction)

        robot.turnLeft()
        assertEquals(Down, robot.direction)

        robot.turnLeft()
        assertEquals(Right, robot.direction)

        robot.turnLeft()
        assertEquals(Up, robot.direction)
    }

    @Test
    fun moveAndTurn() {
        val robot = PaintingRobot()

        robot.moveForward()
        robot.turnRight()
        robot.moveForward()
        assertEquals(Position(1, 1), robot.position)

        robot.moveForward()
        robot.moveForward()
        robot.turnLeft()
        robot.moveForward()
        assertEquals(Position(3, 2), robot.position)
    }

    @Test
    fun paint() {
        val robot = PaintingRobot()

        assertEquals(0, robot.getCurrentColor())
        robot.paint(1)
        assertEquals(1, robot.getCurrentColor())
        robot.paint(0)
        assertEquals(0, robot.getCurrentColor())
    }

    @Test
    fun paintedPanels() {
        val robot = PaintingRobot()

        robot.paint(1)
        robot.turnLeft()
        robot.moveForward()

        robot.paint(0)
        robot.turnLeft()
        robot.moveForward()

        robot.paint(1)
        robot.turnLeft()
        robot.moveForward()

        robot.paint(1)
        robot.turnLeft()
        robot.moveForward()

        robot.paint(0)
        robot.turnRight()
        robot.moveForward()

        robot.paint(1)
        robot.turnLeft()
        robot.moveForward()

        robot.paint(1)
        robot.turnLeft()
        robot.moveForward()

        assertEquals(6, robot.paintedPanels.size)
        assertTrue(robot.paintedPanels.contains(Panel(Position(0, 0), 0)))
        assertTrue(robot.paintedPanels.contains(Panel(Position(-1, 0), 0)))
        assertTrue(robot.paintedPanels.contains(Panel(Position(-1, -1), 1)))
        assertTrue(robot.paintedPanels.contains(Panel(Position(0, -1), 1)))
        assertTrue(robot.paintedPanels.contains(Panel(Position(1, 0), 1)))
        assertTrue(robot.paintedPanels.contains(Panel(Position(1, 1), 1)))
    }
}