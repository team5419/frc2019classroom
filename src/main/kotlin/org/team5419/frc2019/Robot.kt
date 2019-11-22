package org.team5419.frc2019

import edu.wpi.first.wpilibj.TimedRobot

import org.team5419.fault.hardware.LazyTalonSRX
import org.team5419.fault.hardware.LazyVictorSPX
import com.ctre.phoenix.motorcontrol.ControlMode
import com.ctre.phoenix.motorcontrol.FeedbackDevice

import edu.wpi.first.wpilibj.XboxController
import edu.wpi.first.wpilibj.GenericHID.Hand

@SuppressWarnings("MagicNumber")
class Robot : TimedRobot() {

    private val mLeftMaster: LazyTalonSRX
    private val mLeftSlave1: LazyVictorSPX

    private val mRightMaster: LazyTalonSRX
    private val mRightSlave1: LazyVictorSPX

    private val mXboxController: XboxController

    private val mChainLift: LazyTalonSRX
    private val mChainBottom: LazyTalonSRX

    private var initialPosition: Int
    private var initialDistance: Double

    init {
        mLeftMaster = LazyTalonSRX(10)
        mLeftSlave1 = LazyVictorSPX(8)

        mRightMaster = LazyTalonSRX(9)
        mRightSlave1 = LazyVictorSPX(12)

        mXboxController = XboxController(0)

        mChainLift = LazyTalonSRX(5)
        mChainBottom = LazyTalonSRX(4)

        mChainBottom.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative)

        initialPosition = 0
        initialDistance = 0.0
    }

    fun sensorPositionToInches(sensorPosition: Int): Double {
        var initialRotations = sensorPosition / (38.0 / 24.0)
        initialRotations /= 4096.0
        return -2 * (Math.PI * (1.23 * initialRotations))
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

        initialPosition = mChainBottom.getSelectedSensorPosition()
        initialDistance = sensorPositionToInches(initialPosition)
    }

    override fun teleopPeriodic() {
        val leftHand: Double = mXboxController.getY(Hand.kLeft) / 1
        val rightHand: Double = mXboxController.getX(Hand.kRight) / -1

        mLeftMaster.set(ControlMode.PercentOutput, leftHand - rightHand)
        mLeftSlave1.set(ControlMode.PercentOutput, leftHand - rightHand)

        mRightMaster.set(ControlMode.PercentOutput, -leftHand - rightHand)
        mRightSlave1.set(ControlMode.PercentOutput, leftHand + rightHand)

        println(sensorPositionToInches(mChainBottom.getSelectedSensorPosition()) - initialDistance)
    }
}
