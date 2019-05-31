package org.team5419.frc2019.subsystems

import com.ctre.phoenix.motorcontrol.ControlMode
import org.team5419.fault.Subsystem
import org.team5419.fault.hardware.LazyTalonSRX
import edu.wpi.first.wpilibj.Solenoid

public class Vacuum(
    private val mPump: LazyTalonSRX,
    private val mSolenoid: Solenoid
) : Subsystem() {
    public fun grab() {
        mPump.set(ControlMode.PercentOutput, 1.0) // turn pump on
        mSolenoid.set(false) // turn valve off
    }

    public fun release() {
        mPump.set(ControlMode.PercentOutput, 0.0) // turn pump off
        mSolenoid.set(true) // turn valve on
    }

    public override fun update() {
    }

    public override fun reset() {
    }

    public override fun stop() {
    }
}
