package org.team5419.frc2019.subsystems

import org.team5419.fault.Subsystem
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
}