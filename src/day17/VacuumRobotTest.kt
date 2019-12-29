package day17

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class VacuumRobotTest {
    @Test
    fun getPath() {
        val robot = VacuumRobot(Position(0, 0), Up)

        robot.moveForward()
        robot.moveForward()
        robot.moveForward()
        robot.turnLeft()
        robot.moveForward()
        robot.moveForward()
        robot.turnRight()
        robot.moveForward()

        assertEquals(listOf('3', 'L', '2', 'R', '1'), robot.getPath())
    }

    @Test
    fun getPath2() {
        val robot = VacuumRobot(Position(0, 0), Up)

        robot.turnRight()
        robot.moveForward()
        robot.moveForward()

        assertEquals(listOf('R', '2'), robot.getPath())
    }
}