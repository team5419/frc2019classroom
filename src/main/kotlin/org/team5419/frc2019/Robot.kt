package org.team5419.frc2019

import edu.wpi.first.wpilibj.TimedRobot

import org.team5419.fault.hardware.LazyTalonSRX
import org.team5419.fault.hardware.LazyVictorSPX
import org.team5419.frc2019.subsystems.DriveTrain
import com.ctre.phoenix.motorcontrol.ControlMode

import edu.wpi.first.wpilibj.XboxController
import edu.wpi.first.wpilibj.GenericHID.Hand

@SuppressWarnings("MagicNumber")

class Robot : TimedRobot() {

    private val mLeftMaster: LazyTalonSRX
    private val mLeftSlave1: LazyVictorSPX
    private val mLeftSlave2: LazyVictorSPX

    private val mRightMaster: LazyTalonSRX
    private val mRightSlave1: LazyVictorSPX
    private val mRightSlave2: LazyVictorSPX

    private val mChainLift: LazyTalonSRX
    private val mChainBottom: LazyTalonSRX

    private val mXboxController: XboxController

    init {
        mLeftMaster = LazyTalonSRX(Constants.DriveTrain.LEFT_MASTER_TALON_PORT)
        mLeftSlave1 = LazyVictorSPX(Constants.DriveTrain.LEFT_SLAVE1_TALON_PORT)
        mLeftSlave2 = LazyVictorSPX(Constants.DriveTrain.LEFT_SLAVE2_TALON_PORT)

        mRightMaster = LazyTalonSRX(Constants.DriveTrain.RIGHT_MASTER_TALON_PORT)
        mRightSlave1 = LazyVictorSPX(Constants.DriveTrain.RIGHT_SLAVE1_TALON_PORT)
        mRightSlave2 = LazyVictorSPX(Constants.DriveTrain.RIGHT_SLAVE2_TALON_PORT)

        mChainLift = LazyTalonSRX(Constants.DriveTrain.CHAIN_LIFT_PORT)
        mChainBottom = LazyTalonSRX(Constants.DriveTrain.CHAIN_BOTTOM_PORT)

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

        val chainDown: Double = mXboxController.getTriggerAxis(Hand.kLeft) / 4
        val chainUp: Double = mXboxController.getTriggerAxis(Hand.kRight) / 4

        val leftHand: Double = mXboxController.getY(Hand.kLeft) / 1
        val rightHand: Double = mXboxController.getY(Hand.kRight) / -1

        mLeftMaster.set(ControlMode.PercentOutput, leftHand)
        mLeftSlave1.set(ControlMode.PercentOutput, leftHand)
        mLeftSlave2.set(ControlMode.PercentOutput, leftHand)

        mRightMaster.set(ControlMode.PercentOutput, rightHand)
        mRightSlave1.set(ControlMode.PercentOutput, rightHand)
        mRightSlave2.set(ControlMode.PercentOutput, rightHand)

        mChainLift.set(ControlMode.PercentOutput, chainUp - chainDown)
        mChainBottom.set(ControlMode.PercentOutput, chainUp - chainDown)
    }
}
