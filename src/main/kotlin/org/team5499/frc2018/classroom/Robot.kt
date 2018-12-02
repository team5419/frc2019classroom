/**
* Documentation for WPILIB:
* http://first.wpi.edu/FRC/roborio/release/docs/java/
* Documentation for CTRE Phoenix(talons, pigeon gyro):
* http://www.ctr-electronics.com/downloads/api/java/html/index.html
*
* functions that are available in the class below:
* override fun robotInit()
* override fun robotPeriodic()
* override fun disableInit()
* override fun disabledPeriodic()
* override fun teleopInit()
* override fun teleopPeriodic()
* override fun autonomousInit()
* override fun autonomousPeriodic()
* override fun testInit()
* override fun testPeriodic()
*/

package org.team5499.frc2018.classroom

import edu.wpi.first.wpilibj.TimedRobot // this is the base class of our Robot class, and takes care of calling the periodic and init functions
// import org.team5499.frc2018.classroom.utils.Utils
import org.team5499.frc2018.classroom.subsystems.Drivetrain
import edu.wpi.first.wpilibj.Timer

@Suppress("MagicNumber")
class Robot : TimedRobot() {
    var timer = Timer()
    var driveDistance = 96.0
    var distToTarget = driveDistance
    var power = distToTarget * 0.05
    var leftPower = 0.0
    var rightPower = 0.0
    var eDif = 0.0
    var firstE = 0.0
    var lastE = 0.0
    var timeDif = 0.0
    var rightAngleAdd = 0.0
    var leftAngleAdd = 0.0
    override fun robotInit() {
        super.setPeriod(Constants.UPDATE_PERIOD)
    }

    override fun disabledInit() {
        Drivetrain.stop()
    }

    override fun autonomousInit() {
        Drivetrain.stop()
        Drivetrain.reset()
    }

    override fun autonomousPeriodic() {
        distToTarget = driveDistance - ((Drivetrain.rightDistance + Drivetrain.leftDistance) / 2.0)
        firstE = distToTarget
        eDif = firstE - lastE
        timeDif = timer.get()
        var slope = eDif / timeDif
        var angleError = 0 - gyroAngle
        println(slope)
        power = (distToTarget * 0.025) + (slope * 0.004)

        if (power > 0.4) {
            power = 0.4
        }
        if (power < -0.4) {
            power = -0.4
        }
        leftPower = power
        rightPower = power
        Drivetrain.setPercent(leftPower * 1.0, rightPower * 1.0)
        timer.reset()
        timer.start()
        lastE = distToTarget
    }
}
