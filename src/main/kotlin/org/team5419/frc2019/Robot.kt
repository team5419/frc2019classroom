package org.team5419.frc2019


import edu.wpi.first.wpilibj.RobotState
import org.team5419.frc2019.controllers.AutoController
import org.team5419.frc2019.controllers.TeleopController
import org.team5419.frc2019.subsystems.Drivetrain
import org.team5499.monkeyLib.MonkeyRobot
import org.team5499.monkeyLib.math.units.millisecond

object Robot : MonkeyRobot(5.millisecond) {

    init {
        +Drivetrain // register drivetrain
    }

    override fun autonomousInit() {
        AutoController.start()
    }

    override fun autonomousPeriodic() {
        AutoController.update()
    }

    override fun teleopInit() {
        TeleopController.start()
    }

    override fun teleopPeriodic() {
        TeleopController.update()
    }

    override fun robotPeriodic() { if(!RobotState.isDisabled()) Controls.update() }

}

fun main() {
    Robot.start()
}
