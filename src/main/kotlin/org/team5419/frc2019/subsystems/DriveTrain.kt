package org.team5419.frc2019.subsystems

import org.team5419.fault.Subsystem
import com.ctre.phoenix.motorcontrol.ControlMode
import org.team5419.fault.hardware.LazyTalonSRX
import org.team5419.fault.hardware.LazyVictorSPX

public class DriveTrain(
    private val mLeftMaster: LazyTalonSRX,
    private val mLeftSlave1: LazyVictorSPX,
    private val mLeftSlave2: LazyVictorSPX,

    private val mRightMaster: LazyTalonSRX,
    private val mRightSlave1: LazyVictorSPX,
    private val mRightSlave2: LazyVictorSPX
) : Subsystem() {

    companion object {
        private const val kPositionSlot = 0
        private const val kVelocitySlot = 1
        private const val kTurnSlot = 2
        private const val kVolts = 12.0
        private const val kWeirdPigeonConversion = 3600.0 / 8192.0
    }

    public override fun update() {
    }

    public override fun reset() {
    }

    public override fun stop() {
    }

    public fun setLeft(speed: Double) {
        mLeftMaster.set(ControlMode.PercentOutput, speed)
        mLeftSlave1.set(ControlMode.PercentOutput, speed)
        mLeftSlave2.set(ControlMode.PercentOutput, speed)
    }

    public fun setRight(speed: Double) {
        mRightMaster.set(ControlMode.PercentOutput, speed)
        mRightSlave1.set(ControlMode.PercentOutput, speed)
        mRightSlave2.set(ControlMode.PercentOutput, speed)
    }
}
