package org.team5419.frc2019

import org.team5419.fault.hardware.LazyTalonSRX
import edu.wpi.first.wpilibj.TimedRobot
import edu.wpi.first.wpilibj.Solenoid
import edu.wpi.first.wpilibj.XboxController

import org.team5419.frc2019.subsystems.Vacuum
import org.team5419.frc2019.subsystems.SubsystemsManager
import org.team5419.frc2019.input.IDriverControls
import org.team5419.frc2019.input.RemoteDriver
import org.team5419.frc2019.controllers.TeleopController
import org.team5419.frc2019.controllers.ControllersManager

class Robot : TimedRobot() {
    // hardware

    private val mVacuumTalon: LazyTalonSRX
    private val mVacuumSolenoid: Solenoid

    // subsystems

    private val mVacuum: Vacuum
    private val mSubsystemsManager: SubsystemsManager

    // input

    private val mDriverControls: IDriverControls

    // controllers

    private val mTeleopController: TeleopController
    private val mControllersManager: ControllersManager

    init {
        // hardware init

        mVacuumTalon = LazyTalonSRX(Constants.Vacuum.TALON_PORT)
        mVacuumSolenoid = Solenoid(Constants.Vacuum.PCM_CHANNEL)

        // subsystem init

        mVacuum = Vacuum(
            mVacuumTalon,
            mVacuumSolenoid
        )

        mSubsystemsManager = SubsystemsManager(
            mVacuum
        )

        // input init

        var xboxController = XboxController(Constants.Input.DRIVER_PORT)
        mDriverControls = RemoteDriver(xboxController)

        // controllers init

        mTeleopController = TeleopController(
            subsystemsManager = mSubsystemsManager,
            remoteDriver = mDriverControls
        )

        mControllersManager = ControllersManager(
            teleopController = mTeleopController,
            autoController = mTeleopController
        )
    }

    // robot

    override fun robotInit() {
        mControllersManager.robotInit()
    }

    override fun robotPeriodic() {
        mControllersManager.robotPeriodic()
    }

    // disabled mode

    override fun disabledInit() {
        mControllersManager.disabledInit()
    }

    override fun disabledPeriodic() {
        mControllersManager.disabledPeriodic()
    }

    // autonomous mode

    override fun autonomousInit() {
        mControllersManager.autonomousInit()
    }

    override fun autonomousPeriodic() {
        mControllersManager.autonomousPeriodic()
    }

    // teleop mode

    override fun teleopInit() {
        mControllersManager.teleopInit()
    }

    override fun teleopPeriodic() {
        mControllersManager.teleopPeriodic()
    }

    // test mode

    override fun testInit() {
        mControllersManager.testInit()
    }

    override fun testPeriodic() {
        mControllersManager.testPeriodic()
    }
}
