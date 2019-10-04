package org.team5419.frc2019

import org.team5419.fault.hardware.LazyTalonSRX
import org.team5419.fault.hardware.LazyVictorSPX

import com.ctre.phoenix.motorcontrol.ControlMode
import com.ctre.phoenix.motorcontrol.FeedbackDevice

import edu.wpi.first.wpilibj.TimedRobot
import edu.wpi.first.wpilibj.XboxController
import edu.wpi.first.wpilibj.GenericHID.Hand
import edu.wpi.first.wpilibj.Timer

// 4096 ticks is a rotation of the shaft
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

    private val mTimer: Timer

    private var initialPosition: Int
    private var initialDistance: Double
    private var previousPosition: Int
    private var preferredPosition: Double
    private var integralError: Double

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

        // Timer
        mTimer = Timer()

        initialPosition = 0
        initialDistance = 0.0
        previousPosition = initialPosition
        integralError = 0.0
        preferredPosition = 40.0
    }

    fun getTimeSinceLastStep(): Double {
        mTimer.stop()
        var re = mTimer.get()
        mTimer.reset()
        mTimer.start()
        return re
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
        initialDistance = UnitConverter.sensorPositionToInches(initialPosition)
    }

    override fun teleopPeriodic() {
        var secondsSinceLastFrame = getTimeSinceLastStep()

        val ePosition: Int = mChainBottom.getSelectedSensorPosition()
        val elevatorDist: Double = UnitConverter.sensorPositionToInches(ePosition)
        var leftHandY: Double = mXboxController.getY(Hand.kLeft) / 1
        val leftHandX: Double = mXboxController.getX(Hand.kLeft) / 1
        // val rightHand: Double = mXboxController.getY(Hand.kRight) / -1

        val velocity: Double = (elevatorDist - previousPosition) / secondsSinceLastFrame
        var errorValue = preferredPosition - elevatorDist
        var percentageOutput = (Constants.PID.E_PROPORTIONAL * errorValue) // P
        percentageOutput += (Constants.PID.E_DERIVATIVE * velocity) // D
        percentageOutput += (Constants.PID.E_INTEGRAL * integralError) // I

        mLeftMaster.set(ControlMode.PercentOutput, leftHandY)
        mLeftSlave1.set(ControlMode.PercentOutput, leftHandY)
        mLeftSlave2.set(ControlMode.PercentOutput, leftHandY)
        mRightMaster.set(ControlMode.PercentOutput, leftHandX)
        mRightSlave1.set(ControlMode.PercentOutput, leftHandX)
        mRightSlave2.set(ControlMode.PercentOutput, leftHandX)
        mChainLift.set(ControlMode.PercentOutput, percentageOutput)
        mChainBottom.set(ControlMode.PercentOutput, percentageOutput)

        integralError += errorValue
    }
}
