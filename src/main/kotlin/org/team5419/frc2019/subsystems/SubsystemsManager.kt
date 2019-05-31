package org.team5419.frc2019.subsystems

import org.team5419.fault.Subsystem

class SubsystemException(message: String) : Exception(message)

public class SubsystemsManager(
    vararg subsystems: Subsystem
) {
    private val mSubsystems: List<Subsystem>

    init {
        mSubsystems = ArrayList<Subsystem>()

        for (subsystem in subsystems) {
            mSubsystems.add(subsystem)
        }
    }

    public fun updateAll() {
        for (subsystem in mSubsystems) {
            subsystem.update()
        }
    }

    public fun stopAll() {
        for (subsystem in mSubsystems) {
            subsystem.stop()
        }
    }

    public fun resetAll() {
        for (subsystem in mSubsystems) {
            subsystem.reset()
        }
    }

    public fun getSubsystems(): List<Subsystem> {
        return mSubsystems
    }

    public inline fun <reified SubsystemType : Subsystem> get(): SubsystemType {
        for (subsystem in getSubsystems()) {
            if (subsystem is SubsystemType) {
                return subsystem
            }
        }

        throw SubsystemException("Does not have subsystem")
    }
}
