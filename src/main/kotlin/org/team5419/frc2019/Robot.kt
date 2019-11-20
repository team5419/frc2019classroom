package org.team5419.frc2019

import org.team5419.fault.hardware.LazyTalonSRX
import org.team5419.fault.hardware.LazyVictorSPX

import com.ctre.phoenix.motorcontrol.ControlMode
import com.ctre.phoenix.motorcontrol.FeedbackDevice
import com.ctre.phoenix.motorcontrol.InvertType

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

    private val mDistance: Int

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

        // Timer
        mTimer = Timer()

        // Distance
        mDistance = UnitConverter.inchesToSensorPosition(12.0)

        // -- Feedback Device Configuration --\\

        // Bottom Chain
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
        }
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
        mLeftMaster.set(ControlMode.Position, mDistance.toDouble())
        mRightMaster.set(ControlMode.Position, mDistance.toDouble())
    }

    @SuppressWarnings("LongMethod")
    override fun teleopPeriodic() {
        // -- Input --\\
        var leftHand = mXboxController.getY(Hand.kLeft)
        var rightHand = mXboxController.getY(Hand.kRight)
    }
}
