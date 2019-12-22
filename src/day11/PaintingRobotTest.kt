package day11

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

internal class PaintingRobotTest {
    @Test
    fun move() {
        val robot = PaintingRobot()

        assertEquals(Position(0, 0), robot.currentPosition)
        assertEquals(Up, robot.currentDirection)

        robot.moveForward()
        assertEquals(Position(0, 1), robot.currentPosition)

        robot.moveForward()
        assertEquals(Position(0, 2), robot.currentPosition)
    }

    @Test
    fun turn() {
        val robot = PaintingRobot()

        robot.turnRight()
        assertEquals(Right, robot.currentDirection)

        robot.turnRight()
        assertEquals(Down, robot.currentDirection)

        robot.turnRight()
        assertEquals(Left, robot.currentDirection)

        robot.turnRight()
        assertEquals(Up, robot.currentDirection)

        robot.turnLeft()
        assertEquals(Left, robot.currentDirection)

        robot.turnLeft()
        assertEquals(Down, robot.currentDirection)

        robot.turnLeft()
        assertEquals(Right, robot.currentDirection)

        robot.turnLeft()
        assertEquals(Up, robot.currentDirection)
    }

    @Test
    fun moveAndTurn() {
        val robot = PaintingRobot()

        robot.moveForward()
        robot.turnRight()
        robot.moveForward()
        assertEquals(Position(1, 1), robot.currentPosition)

        robot.moveForward()
        robot.moveForward()
        robot.turnLeft()
        robot.moveForward()
        assertEquals(Position(3, 2), robot.currentPosition)
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