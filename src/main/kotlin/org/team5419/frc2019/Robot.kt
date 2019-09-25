
package org.team5419.frc2019

import edu.wpi.first.wpilibj.TimedRobot

import org.team5419.fault.hardware.LazyTalonSRX
import org.team5419.fault.hardware.LazyVictorSPX
import com.ctre.phoenix.motorcontrol.ControlMode

import edu.wpi.first.wpilibj.XboxController
import edu.wpi.first.wpilibj.GenericHID.Hand

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
    }

    override fun teleopPeriodic() {
        var lefthand: Double = mXboxController.getY(Hand.kLeft) / 1
        var righthand: Double = mXboxController.getY(Hand.kRight) / -1

        var rightTrigger: Double = mXboxController.getTriggerAxis(Hand.kRight) / 1.5
        var leftTrigger: Double = mXboxController.getTriggerAxis(Hand.kLeft) / 1.5

        mChainUp.set(ControlMode.PercentOutput, rightTrigger - leftTrigger)
        mChainDown.set(ControlMode.PercentOutput, rightTrigger - leftTrigger)

        mLeftMaster.set(ControlMode.PercentOutput, lefthand)
        mLeftSlave1.set(ControlMode.PercentOutput, lefthand)
        mLeftSlave2.set(ControlMode.PercentOutput, lefthand)

        mRightMaster.set(ControlMode.PercentOutput, righthand)
        mRightSlave1.set(ControlMode.PercentOutput, righthand)
        mRightSlave2.set(ControlMode.PercentOutput, righthand)
    }
}
