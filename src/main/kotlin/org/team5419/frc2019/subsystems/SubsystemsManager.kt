package org.team5419.frc2019.subsystems

public class SubsystemsManager(
    drivetrain: Drivetrain,
) {
    public val drivetrain: Drivetrain()

    public val mList: List<Subsystem>

    init {
        this.drivetrain: Drivetrain

        mList = listOf(this.drivetrain)
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