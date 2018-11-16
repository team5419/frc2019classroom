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

class Robot : TimedRobot() {
    override fun robotInit() {
        super.setPeriod(Constants.UPDATE_PERIOD)
    }
}
