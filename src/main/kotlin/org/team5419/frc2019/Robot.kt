package org.team5419.frc2019

import edu.wpi.first.wpilibj.TimedRobot
import org.team5419.fault.hardware.LazyTalonSRX
import org.team5419.fault.hardware.LazyVictorSPX
import edu.wpi.first.wpilibj.XboxController
import edu.wpi.first.wpilibj.GenericHID.Hand
import com.ctre.phoenix.motorcontrol.ControlMode

@SuppressWarnings("MagicNumber")
class Robot : TimedRobot() {
    // Hardware
    private val mLeftMaster: LazyTalonSRX
    private val mLeftSlave1: LazyVictorSPX
    private val mLeftSlave2: LazyVictorSPX

    private val xbox: XboxController
    private val mRightMaster: LazyTalonSRX
    private val mRightSlave1: LazyVictorSPX
    private val mRightSlave2: LazyVictorSPX

    private val mElvMaster: LazyTalonSRX
    private val mElvSlave: LazyTalonSRX
    // subsystems

    init {
        mLeftMaster = LazyTalonSRX(12)
        mLeftSlave1 = LazyVictorSPX(2)
        mLeftSlave2 = LazyVictorSPX(3)

        mRightMaster = LazyTalonSRX(6) // 8
        mRightSlave1 = LazyVictorSPX(7) // 34
        mRightSlave2 = LazyVictorSPX(8) // 34
        xbox = XboxController(0)

        mElvMaster = LazyTalonSRX(9)
        mElvSlave = LazyTalonSRX(5)
    }

    override fun robotInit() { // constructor
        println("Hello World from Kotlin!")
    }
    override fun robotPeriodic() { // loop
    }
    override fun teleopPeriodic() {
        mLeftMaster.set(ControlMode.PercentOutput, (xbox.getY(Hand.kLeft) + xbox.getY(Hand.kLeft)))
        mLeftSlave1.set(ControlMode.PercentOutput, (xbox.getY(Hand.kLeft) + xbox.getY(Hand.kLeft)))
        mLeftSlave2.set(ControlMode.PercentOutput, (xbox.getY(Hand.kLeft) + xbox.getY(Hand.kLeft)))
        mRightMaster.set(ControlMode.PercentOutput, (-xbox.getY(Hand.kLeft) - xbox.getY(Hand.kLeft)))
        mRightSlave1.set(ControlMode.PercentOutput, (-xbox.getY(Hand.kLeft) - xbox.getY(Hand.kLeft)))
        mRightSlave2.set(ControlMode.PercentOutput, (-xbox.getY(Hand.kLeft) - xbox.getY(Hand.kLeft)))
    }
}
