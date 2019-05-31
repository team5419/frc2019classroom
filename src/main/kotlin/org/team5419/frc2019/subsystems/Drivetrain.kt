package org.team5419.frc2019.subsystems

import com.ctre.phoenix.motorcontrol.can.TalonSRX

public class Drivetrain(
    leftMaster: TalonSRX,
    leftSlave1: TalonSRX,
    leftSlave2: TalonSRX,
    rightMaster: TalonSRX,
    rightSlave1: TalonSRX,
    rightSlave2: TalonSRX
): Subsystem() {
    
    private val mLeftMaster: TalonSRX
    private val mLeftSlave1: TalonSRX
    private val mLeftSlave2: TalonSRX
    private val mRightMaster: TalonSRX
    private val mRightSlave1: TalonSRX
    private val mRightSlave2: TalonSRX

    public var brakemode: Boolean = false

    init {
        mLeftMaster: leftMaster.apply {
            setSensorPhase(true) //Percent output => positive change when controller doesn't correlate with talon
            setInverted(false)  //doesnt invert h bridge
        }
        mLeftSlave1: leftSlave1.apply {
            follow(mLeftMaster)
            setInverted(false)
        }
        mLeftSlave2: leftSlave2.apply {
            follow(mLeftMaster)
            setInverted(false)
        }
        mRightMaster: rightMaster.apply {
            setSensorPhase(true)
            setInverted(true)
        }
        mRightSlave1: rightSlave1.apply {
            follow(mRightMaster)
            setInverted(true)
        }
        mRightSlave2: rightSlave2.apply {
            follow(mRightMaster)
            setInverted(true)
        }
    }

    public fun setPercent(left: Double, right: Double, brakemode: Boolean = false) {
        mLeftMaster.set(ControlMode.PercentOutput, left)
        mRightMaster.set(ControlMode.PercentOutput, right)
        this.brakemode = brakemode
    }

}