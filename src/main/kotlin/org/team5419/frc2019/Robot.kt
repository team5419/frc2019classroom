package org.team5419.frc2019

import edu.wpi.first.wpilibj.TimedRobot
import edu.wpi.first.networktables.NetworkTableInstance
import edu.wpi.first.networktables.NetworkTable
import edu.wpi.first.wpilibj.DriverStation

class Robot : TimedRobot() {
    val nt: NetworkTableInstance
    val table: NetworkTable
    val ds: DriverStation

    init {
        nt = NetworkTableInstance.getDefault()
        table = nt.getTable("stormx")
        ds = DriverStation.getInstance()
    }

    override fun robotInit() {
        println("Hello World from Kotlin!")
    }

    override fun robotPeriodic() {
        table.getEntry("matchTime").setDouble(ds.getMatchTime())
        table.getEntry("matchNumber").setDouble(ds.getMatchNumber().toDouble())
        table.getEntry("eventName").setString(ds.getEventName())
        table.getEntry("isEnabled").setBoolean(ds.isEnabled())
    }
}
