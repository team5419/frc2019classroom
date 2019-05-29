package org.team5419.frc2019

import org.team5419.fault.hardware.LazyTalonSRX
import org.team5419.frc2019.subsystems.Vacuum
import edu.wpi.first.wpilibj.TimedRobot
import edu.wpi.first.wpilibj.Solenoid

class Robot : TimedRobot() {
    // hardware

    private val mVacuumTalon: LazyTalonSRX
    private val mVacuumSolenoid: Solenoid

    // subsystems

    private val mVacuum: Vacuum

    init {
        // hardware init

        mVacuumTalon = LazyTalonSRX(Constants.Vacuum.TALON_PORT)
        mVacuumSolenoid = Solenoid(Constants.Vacuum.PCM_CHANNEL)

        // subsystem init

        mVacuum = Vacuum(
            mVacuumTalon,
            mVacuumSolenoid
        )
    }

    override fun robotInit() {
    }
}
