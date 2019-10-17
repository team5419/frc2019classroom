package org.team5419.frc2019

import edu.wpi.first.wpilibj.TimedRobot

import org.team5419.fault.hardware.LazyTalonSRX
import org.team5419.fault.hardware.LazyVictorSPX
import org.team5419.fault.hardware.PS4Controller

import edu.wpi.first.wpilibj.GenericHID.Hand

@SuppressWarnings("MagicNumber", "ComplexMethod")
class Robot : TimedRobot() {

    private val mLeftMaster: LazyTalonSRX
    private val mLeftSlave1: LazyVictorSPX
    private val mLeftSlave2: LazyVictorSPX

    private val mRightMaster: LazyTalonSRX
    private val mRightSlave1: LazyVictorSPX
    private val mRightSlave2: LazyVictorSPX

    private val mPS4Controller: PS4Controller

    init {
        mLeftMaster = LazyTalonSRX(12)
        mLeftSlave1 = LazyVictorSPX(2)
        mLeftSlave2 = LazyVictorSPX(3)

        mRightMaster = LazyTalonSRX(6)
        mRightSlave1 = LazyVictorSPX(7)
        mRightSlave2 = LazyVictorSPX(8)

        mPS4Controller = PS4Controller(1)
    }

    fun testButtonPressed() {
        if (mPS4Controller.getXButtonPressed()) { println("X Button Pressed") }
        if (mPS4Controller.getOButtonPressed()) { println("O Button Pressed") }
        if (mPS4Controller.getTriangleButtonPressed()) { println("Triangle Button Pressed") }
        if (mPS4Controller.getSquareButtonPressed()) { println("Square Button Pressed") }
        if (mPS4Controller.getBumperPressed(Hand.kLeft)) { println("Left Bumper Pressed") }
        if (mPS4Controller.getBumperPressed(Hand.kRight)) { println("Right Bumper Pressed") }
        if (mPS4Controller.getTriggerPressed(Hand.kLeft)) { println("Left Trigger Pressed") }
        if (mPS4Controller.getTriggerPressed(Hand.kRight)) { println("Right Trigger Pressed") }
        if (mPS4Controller.getShareButtonPressed()) { println("Share Button Pressed") }
        if (mPS4Controller.getOptionsButtonPressed()) { println("Options Button Pressed") }
        if (mPS4Controller.getStickButtonPressed(Hand.kLeft)) { println("Left Stick Pressed") }
        if (mPS4Controller.getStickButtonPressed(Hand.kRight)) { println("Right Stick Pressed") }
        if (mPS4Controller.getHomeButtonPressed()) { println("Home Button Pressed") }
        if (mPS4Controller.getTouchPadPressed()) { println("Touch Pad Pressed") }
    }

    fun testButtonReleased() {
        if (mPS4Controller.getXButtonReleased()) { println("X Button Released") }
        if (mPS4Controller.getOButtonReleased()) { println("O Button Released") }
        if (mPS4Controller.getTriangleButtonReleased()) { println("Triangle Button Released") }
        if (mPS4Controller.getSquareButtonReleased()) { println("Square Button Released") }
        if (mPS4Controller.getBumperReleased(Hand.kLeft)) { println("Left Bumper Released") }
        if (mPS4Controller.getBumperReleased(Hand.kRight)) { println("Right Bumper Released") }
        if (mPS4Controller.getTriggerReleased(Hand.kLeft)) { println("Left Trigger Released") }
        if (mPS4Controller.getTriggerReleased(Hand.kRight)) { println("Right Trigger Released") }
        if (mPS4Controller.getShareButtonReleased()) { println("Share Button Released") }
        if (mPS4Controller.getOptionsButtonReleased()) { println("Options Button Released") }
        if (mPS4Controller.getStickButtonReleased(Hand.kLeft)) { println("Left Stick Released") }
        if (mPS4Controller.getStickButtonReleased(Hand.kRight)) { println("Right Stick Released") }
        if (mPS4Controller.getHomeButtonReleased()) { println("Home Button Released") }
        if (mPS4Controller.getTouchPadReleased()) { println("Touch Pad Released") }
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
        testButtonReleased()
    }
}
