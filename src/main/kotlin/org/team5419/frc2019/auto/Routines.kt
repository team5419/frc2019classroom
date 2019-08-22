package org.team5419.frc2019.auto

import org.team5419.frc2019.subsystems.Drivetrain
import org.team5499.monkeyLib.auto.DriveTrajectoryAction
import org.team5499.monkeyLib.auto.NothingAction
import org.team5499.monkeyLib.auto.serial
import org.team5499.monkeyLib.auto.toRoutine
import org.team5499.monkeyLib.math.units.second

object Routines {

    val kTestRoutine = serial {
        +NothingAction().withTimeout(1.second)
        +DriveTrajectoryAction(Drivetrain, Drivetrain.trajectoryFollower, Paths.kTestTrajectory1)
        +NothingAction().withTimeout(1.second)
        +DriveTrajectoryAction(Drivetrain, Drivetrain.trajectoryFollower, Paths.kTestTrajectory2)
    }.toRoutine("Test", Paths.kStart)

    val kTuningRoutine = serial {

    }.toRoutine("Tune", Paths.kStart)




}