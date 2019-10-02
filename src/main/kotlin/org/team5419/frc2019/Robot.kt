package org.team5419.frc2019

import edu.wpi.first.wpilibj.TimedRobot

import org.team5419.fault.hardware.LazyTalonSRX
import org.team5419.fault.hardware.LazyVictorSPX
import org.team5419.frc2019.subsystems.DriveTrain
import com.ctre.phoenix.motorcontrol.ControlMode

import edu.wpi.first.wpilibj.XboxController
import edu.wpi.first.wpilibj.GenericHID.Hand
import edu.wpi.first.wpilibj.Timer

import com.ctre.phoenix.motorcontrol.FeedbackDevice

@SuppressWarnings("MagicNumber", "ComplexMethod", "LongMethod")

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

    public var startingRotations: Double = 0.0

    public var chainSpeed: Double = 0.0

    public var chainOn: Boolean = false

    public var listPIDs: Array<Int> = arrayOf(0, 10, 20, 30)

    public var currentPIDIndex: Int = 0

    public var totalErrors: Double = 0.0

    public var previousError: Double = 0.0

    public var mTimer = Timer()

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

        mChainBottom.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative)
    }

    fun convert(position: Int): Double {
        val initialRotations: Double =
        (position / Constants.DriveTrain.GEAR_REDUCTION) / Constants.DriveTrain.TICKS_PER_ROTATION
        return -2 * (3.141592 * (1.23 * initialRotations))
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
        startingRotations = convert(mChainBottom.getSelectedSensorPosition())
        mTimer.start()
    }

    override fun teleopPeriodic() {

        var frameVal: Double = mTimer.get()
        mTimer.reset()

        var total: Double = convert(mChainBottom.getSelectedSensorPosition()) - startingRotations

        var error = listPIDs[currentPIDIndex] - total

        var chainUp: Double = 0.0

        if (mXboxController.getPOV() >= 315 || mXboxController.getPOV() < 45) {
            currentPIDIndex = 0
            totalErrors = 0.0
            chainOn = true
        }
        if (mXboxController.getPOV() >= 45 && mXboxController.getPOV() < 135) {
            currentPIDIndex = 1
            totalErrors = 0.0
            chainOn = true
        }
        if (mXboxController.getPOV() >= 135 && mXboxController.getPOV() < 225) {
            currentPIDIndex = 2
            totalErrors = 0.0
            chainOn = true
        }
        if (mXboxController.getPOV() >= 225 && mXboxController.getPOV() < 315) {
            currentPIDIndex = 3
            totalErrors = 0.0
            chainOn = true
        }

        var kP: Double = 0.2

        if (chainOn == true) {
            if (total > listPIDs[currentPIDIndex] || total < listPIDs[currentPIDIndex]) {
                chainUp = (listPIDs[currentPIDIndex] - total) * kP
            } else {
                chainUp = 0.0
                chainOn = false
            }
        }

        val leftHand: Double = mXboxController.getY(Hand.kLeft) / 1
        val rightHand: Double = mXboxController.getY(Hand.kRight) / -1

        chainUp += (0.000001 * totalErrors)
        chainUp += (.01 * ((error - previousError) / frameVal))

        if (chainUp > 0.5) {
            chainUp = 0.5
        }

        if (chainUp < -0.5) {
            chainUp = -0.5
        }

        mLeftMaster.set(ControlMode.PercentOutput, leftHand)
        mLeftSlave1.set(ControlMode.PercentOutput, leftHand)
        mLeftSlave2.set(ControlMode.PercentOutput, leftHand)

        mRightMaster.set(ControlMode.PercentOutput, rightHand)
        mRightSlave1.set(ControlMode.PercentOutput, rightHand)
        mRightSlave2.set(ControlMode.PercentOutput, rightHand)

        mChainLift.set(ControlMode.PercentOutput, chainUp)
        mChainBottom.set(ControlMode.PercentOutput, chainUp)

        totalErrors += error
        previousError = error
    }
}
