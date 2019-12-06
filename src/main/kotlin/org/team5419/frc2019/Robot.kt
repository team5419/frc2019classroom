package org.team5419.frc2019

import org.team5419.fault.hardware.LazyTalonSRX

import com.ctre.phoenix.motorcontrol.ControlMode

import edu.wpi.first.wpilibj.TimedRobot
import edu.wpi.first.wpilibj.XboxController
import edu.wpi.first.wpilibj.GenericHID.Hand
import edu.wpi.first.wpilibj.Timer

@SuppressWarnings("MagicNumber")
class Robot : TimedRobot() {

    private val mLeftMaster: LazyTalonSRX
    private val mLeftSlave1: LazyTalonSRX
    // private val mLeftSlave2: LazyVictorSPX

    private val mRightMaster: LazyTalonSRX
    private val mRightSlave1: LazyTalonSRX
    // private val mRightSlave2: LazyVictorSPX

    // private val mChainLift: LazyTalonSRX
    // private val mChainBottom: LazyTalonSRX

    private val mXboxController: XboxController

    private val mTimer: Timer

    // private val mDistance: Int

    init {
        // Left Wheel Motors
        mLeftMaster = LazyTalonSRX(Constants.Ports.LEFT_MASTER)
        mLeftSlave1 = LazyTalonSRX(Constants.Ports.LEFT_SLAVE_1)
        // mLeftSlave2 = LazyVictorSPX(Constants.Ports.LEFT_SLAVE_2)

        // Right Wheel Motors
        mRightMaster = LazyTalonSRX(Constants.Ports.RIGHT_MASTER)
        mRightSlave1 = LazyTalonSRX(Constants.Ports.RIGHT_SLAVE_1)
        // mRightSlave2 = LazyVictorSPX(Constants.Ports.RIGHT_SLAVE_2)

        // Elevator Motors
        // mChainLift = LazyTalonSRX(Constants.Ports.CHAIN_LIFT)
        // mChainBottom = LazyTalonSRX(Constants.Ports.CHAIN_BOTTOM)

        // Xbox Controller
        mXboxController = XboxController(0)

        // Timer
        mTimer = Timer()

        // Distance
        // mDistance = UnitConverter.inchesToSensorPosition(12.0)

        // -- Feedback Device Configuration --\\

        // Bottom Chain
        /**
        mChainBottom.apply {
            configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative)
            config_kP(0, Constants.PID.E_KP, 0)
            config_kI(0, Constants.PID.E_KI, 0)
            config_kD(0, Constants.PID.E_KD, 0)
            config_kF(0, Constants.PID.E_KF, 0)
        }

        // Left Side
        mLeftMaster.apply {
            setInverted(false)
            configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative)
            config_kP(0, Constants.PID.DT_KP, 0)
            config_kI(0, Constants.PID.DT_KI, 0)
            config_kD(0, Constants.PID.DT_KD, 0)
            config_kF(0, Constants.PID.DT_KF, 0)
        }

        mLeftSlave1.apply {
            setInverted(InvertType.FollowMaster)
            follow(mLeftMaster)
        }

        mLeftSlave2.apply {
            setInverted(InvertType.FollowMaster)
            follow(mLeftMaster)
        }

        // Right Side
        mRightMaster.apply {
            setInverted(true)
            configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative)
            config_kP(0, Constants.PID.DT_KP, 0)
            config_kI(0, Constants.PID.DT_KI, 0)
            config_kD(0, Constants.PID.DT_KD, 0)
            config_kF(0, Constants.PID.DT_KF, 0)
        }

        mRightSlave1.apply {
            setInverted(InvertType.FollowMaster)
            follow(mRightMaster)
        }

        mRightSlave2.apply {
            setInverted(InvertType.FollowMaster)
            follow(mRightMaster)
        } */
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
        // -- Sets Motor Values --\\
        // mLeftMaster.set(ControlMode.Position, mDistance.toDouble())
        // mLeftSlave1.set(ControlMode.Position, mDistance.toDouble())
        // mRightMaster.set(ControlMode.Position, mDistance.toDouble())
        // mRightSlave1.set(ControlMode.Position, mDistance.toDouble())
    }

    @SuppressWarnings("LongMethod")
    override fun teleopPeriodic() {
        // -- Input --\\
        var leftHand = mXboxController.getY(Hand.kLeft)
        var rightHand = mXboxController.getY(Hand.kRight)

        mLeftMaster.set(ControlMode.PercentOutput, leftHand * 0.5)
        mLeftSlave1.set(ControlMode.PercentOutput, leftHand * 0.5)
        mRightMaster.set(ControlMode.PercentOutput, rightHand * 0.5)
        mRightSlave1.set(ControlMode.PercentOutput, rightHand * 0.5)
    }
}
