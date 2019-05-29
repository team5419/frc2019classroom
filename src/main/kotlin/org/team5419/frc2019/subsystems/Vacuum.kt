package org.team5419.frc2019.subsystems

import com.ctre.phoenix.motorcontrol.ControlMode
import org.team5419.fault.Subsystem
import org.team5419.fault.hardware.LazyTalonSRX
import edu.wpi.first.wpilibj.Solenoid

public class Vacuum(
    private val mValve: LazyTalonSRX,
    private val mPCM: Solenoid
) : Subsystem() {
    public fun grap() {
        mPCM.set(false) // turn PCM on
    }

    public fun release() {
        mPCM.set(true) // turn PCM off
    }

    public override fun update() {
    }

    public override fun reset() {
        mValve.set(ControlMode.PercentOutput, 1.0)
    }

    public override fun stop() {
        mValve.set(ControlMode.PercentOutput, 0.0)
    }
}
