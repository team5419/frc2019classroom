package org.team5419.frc2019

import edu.wpi.first.wpilibj.TimedRobot
import com.ctre.phoenix.motorcontrol.ControlMode
import org.team5419.fault.hardware.LazyVictorSPX
import org.team5419.fault.hardware.LazyTalonSRX

import edu.wpi.first.wpilibj.XboxController
import edu.wpi.first.wpilibj.GenericHID.Hand

class Robot : TimedRobot() {

    // hardware

    private val mLeftMaster: LazyTalonSRX
    private val mLeftSlave1: LazyVictorSPX
    private val mLeftSlave2: LazyVictorSPX

    private val mRightMaster: LazyTalonSRX
    private val mRightSlave1: LazyVictorSPX
    private val mRightSlave2: LazyVictorSPX

    private val mXboxController: XboxController

    init {
        // hardware init

        mLeftMaster = LazyTalonSRX(Constants.DriveTrain.LEFT_MASTER_TALON_PORT)
        mLeftSlave1 = LazyVictorSPX(Constants.DriveTrain.LEFT_SLAVE1_TALON_PORT)
        mLeftSlave2 = LazyVictorSPX(Constants.DriveTrain.LEFT_SLAVE2_TALON_PORT)

        mRightMaster = LazyTalonSRX(Constants.DriveTrain.RIGHT_MASTER_TALON_PORT)
        mRightSlave1 = LazyVictorSPX(Constants.DriveTrain.RIGHT_SLAVE1_TALON_PORT)
        mRightSlave2 = LazyVictorSPX(Constants.DriveTrain.RIGHT_SLAVE2_TALON_PORT)

        // controllers init

        mXboxController = XboxController(Constants.Input.DRIVER_PORT)
    }

    override fun robotInit() {
    }

    override fun robotPeriodic() {
    }

    override fun disabledInit() {
    }

    override fun disabledPeriodic() {
    }

    override fun teleopInit() {
    }

    override fun teleopPeriodic() {
        mLeftMaster.set(ControlMode.PercentOutput, mXboxController.getY(Hand.kLeft))
        mLeftSlave1.set(ControlMode.PercentOutput, mXboxController.getY(Hand.kLeft))
        mLeftSlave2.set(ControlMode.PercentOutput, mXboxController.getY(Hand.kLeft))

        mRightMaster.set(ControlMode.PercentOutput, mXboxController.getY(Hand.kRight))
        mRightSlave1.set(ControlMode.PercentOutput, mXboxController.getY(Hand.kRight))
        mRightSlave2.set(ControlMode.PercentOutput, mXboxController.getY(Hand.kRight))
    }
}
