package org.team5419.frc2019

import org.team5419.fault.hardware.LazyTalonSRX
import org.team5419.fault.hardware.LazyVictorSPX

import com.ctre.phoenix.motorcontrol.ControlMode
import com.ctre.phoenix.motorcontrol.FeedbackDevice

import edu.wpi.first.wpilibj.TimedRobot
import edu.wpi.first.wpilibj.XboxController
import edu.wpi.first.wpilibj.GenericHID.Hand

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

    private var initialPosition: Int
    private var initialDistance: Double
    private var preferredPosition: Double

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

        initialPosition = 0
        initialDistance = 0.0
        preferredPosition = 40.0
    }

    fun convertToInches(position: Int): Double {
        val initialRotations: Double = (position / (Constants.GEAR_REDUCTION)) / Constants.TICKS_PER_ROTATION
        return -2 * (Constants.PI * (Constants.SPROCKET_DIAMETER * initialRotations))
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
        initialDistance = convertToInches(initialPosition)
    }

    override fun teleopPeriodic() {
        val ePosition: Int = mChainBottom.getSelectedSensorPosition()
        val elevatorDist: Double = convertToInches(ePosition)

        var leftHandY: Double = mXboxController.getY(Hand.kLeft) / 1
        val leftHandX: Double = mXboxController.getX(Hand.kLeft) / 1
        // val rightHand: Double = mXboxController.getY(Hand.kRight) / -1

        var errorValue = preferredPosition - elevatorDist
        var percentageOutput = Constants.PROPORTIONAL_ELEVATOR * errorValue
        println(errorValue)

        mLeftMaster.set(ControlMode.PercentOutput, leftHandY)
        mLeftSlave1.set(ControlMode.PercentOutput, leftHandY)
        mLeftSlave2.set(ControlMode.PercentOutput, leftHandY)

        mRightMaster.set(ControlMode.PercentOutput, leftHandX)
        mRightSlave1.set(ControlMode.PercentOutput, leftHandX)
        mRightSlave2.set(ControlMode.PercentOutput, leftHandX)

        mChainLift.set(ControlMode.PercentOutput, percentageOutput)
        mChainBottom.set(ControlMode.PercentOutput, percentageOutput)
    }
}
