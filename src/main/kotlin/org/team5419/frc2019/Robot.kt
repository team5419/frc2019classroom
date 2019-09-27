
package org.team5419.frc2019

import edu.wpi.first.wpilibj.TimedRobot

import org.team5419.fault.hardware.LazyTalonSRX
import org.team5419.fault.hardware.LazyVictorSPX
import com.ctre.phoenix.motorcontrol.ControlMode

import edu.wpi.first.wpilibj.XboxController
import edu.wpi.first.wpilibj.GenericHID.Hand

import com.ctre.phoenix.motorcontrol.FeedbackDevice

@SuppressWarnings("MagicNumber")
class Robot : TimedRobot() {

    private val mChainUp: LazyTalonSRX
    private val mChainDown: LazyTalonSRX

    private val mLeftMaster: LazyTalonSRX
    private val mLeftSlave1: LazyVictorSPX
    private val mLeftSlave2: LazyVictorSPX

    private val mRightMaster: LazyTalonSRX
    private val mRightSlave1: LazyVictorSPX
    private val mRightSlave2: LazyVictorSPX

    private val mXboxController: XboxController

    private val setPoint: Double

    private val kP: Double

    init {
        mChainUp = LazyTalonSRX(4)
        mChainDown = LazyTalonSRX(5)

        mLeftMaster = LazyTalonSRX(12)
        mLeftSlave1 = LazyVictorSPX(2)
        mLeftSlave2 = LazyVictorSPX(3)

        mRightMaster = LazyTalonSRX(6)
        mRightSlave1 = LazyVictorSPX(7)
        mRightSlave2 = LazyVictorSPX(8)

        mXboxController = XboxController(0)

        mChainUp.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative)

        setPoint = -10000.0
        kP = .0001
    }

    override fun teleopPeriodic() {
        var lefthand: Double = mXboxController.getY(Hand.kLeft) / 1
        var righthand: Double = mXboxController.getY(Hand.kRight) / -1

        var ePosition: Double = mChainUp.getSelectedSensorPosition().toDouble()

        var error: Double = ePosition - setPoint

        var rightTrigger: Double = mXboxController.getTriggerAxis(Hand.kRight) / 1
        var leftTrigger: Double = mXboxController.getTriggerAxis(Hand.kLeft) / 1

        var output = kP * error
        if(output > 0.5) {
            output = 0.5
        } else if(output < -0.5) {
            output = -0.5
        }

        if (mXboxController.aButton) {
            mChainUp.set(ControlMode.PercentOutput, kP * error)
            mChainDown.set(ControlMode.PercentOutput, kP * error)
        }

        mLeftMaster.set(ControlMode.PercentOutput, lefthand)
        mLeftSlave1.set(ControlMode.PercentOutput, lefthand)
        mLeftSlave2.set(ControlMode.PercentOutput, lefthand)

        mRightMaster.set(ControlMode.PercentOutput, righthand)
        mRightSlave1.set(ControlMode.PercentOutput, righthand)
        mRightSlave2.set(ControlMode.PercentOutput, righthand)

        println(error)
    }
}
