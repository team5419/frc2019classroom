package org.team5419.frc2019

import org.team5419.fault.hardware.LazyTalonSRX
import org.team5419.fault.hardware.LazyVictorSPX

import com.ctre.phoenix.motorcontrol.ControlMode
import com.ctre.phoenix.motorcontrol.FeedbackDevice

import edu.wpi.first.wpilibj.TimedRobot
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

    private val mHighestPosition: Int = 100

    init {
        // Left Wheel Motors
        mLeftMaster = LazyTalonSRX(12)
        mLeftSlave1 = LazyVictorSPX(2)
        mLeftSlave2 = LazyVictorSPX(3)

        // Right Wheel Motors
        mRightMaster = LazyTalonSRX(6)
        mRightSlave1 = LazyVictorSPX(7)
        mRightSlave2 = LazyVictorSPX(8)

        // Elevator Motors
        mChainLift = LazyTalonSRX(5)
        mChainBottom = LazyTalonSRX(4)

        // Xbox Controller
        mXboxController = XboxController(0)

        // Feedback Device Configuration
        mChainBottom.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative)
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
        val ePosition: Int = mChainBottom.getSelectedSensorPosition()
        println(ePosition)

        val leftHandY: Double = mXboxController.getY(Hand.kLeft) / 1
        val leftHandX: Double = mXboxController.getX(Hand.kLeft) / 1
        val rightHand: Double = mXboxController.getY(Hand.kRight) / -1

        mLeftMaster.set(ControlMode.PercentOutput, leftHandY)
        mLeftSlave1.set(ControlMode.PercentOutput, leftHandY)
        mLeftSlave2.set(ControlMode.PercentOutput, leftHandY)

        mRightMaster.set(ControlMode.PercentOutput, leftHandX)
        mRightSlave1.set(ControlMode.PercentOutput, leftHandX)
        mRightSlave2.set(ControlMode.PercentOutput, leftHandX)

        mChainLift.set(ControlMode.PercentOutput, rightHand)
        mChainBottom.set(ControlMode.PercentOutput, rightHand)
    }
}
