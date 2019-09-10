package org.team5419.frc2019.subsystems

import org.team5419.fault.Subsystem

class SubsystemsManager(
    public val drivetrain: DriveTrain
) {

    public val mList: List<Subsystem>

    init {
        mList = listOf(
            this.drivetrain
        )
    }

    public fun updateAll() {
        for (i in mList) {
            i.update()
        }
    }
    public fun stopAll() {
        for (i in mList) {
            i.stop()
        }
    }
    public fun resetAll() {
        for (i in mList) {
            i.reset()
        }
    }
}
