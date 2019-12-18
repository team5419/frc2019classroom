package org.team5419.frc2019

import edu.wpi.first.wpilibj.TimedRobot

import org.team5419.fault.auto.Routine
import org.team5419.fault.auto.DriveTrajectoryAction
import org.team5419.fault.math.units.feet
import org.team5419.fault.math.units.derived.velocity
import org.team5419.fault.math.geometry.Pose2d
import org.team5419.fault.math.geometry.Pose2dWithCurvature
import org.team5419.fault.trajectory.types.TimedTrajectory
import org.team5419.fault.trajectory.TrajectoryGenerator
import org.team5419.fault.trajectory.DefaultTrajectoryGenerator
import org.team5419.fault.trajectory.constraints.TimingConstraint

import org.team5419.frc2019.subsystems.Drivetrain

@SuppressWarnings("MagicNumber")
class Robot : TimedRobot() {
    // private val mXboxController: XboxController
    val trajectoryGenerator: TrajectoryGenerator = DefaultTrajectoryGenerator

    val trajectory: TimedTrajectory<Pose2dWithCurvature> = trajectoryGenerator.generateTrajectory(
        listOf(
            Pose2d(),
            Pose2d(10.feet, 5.feet),
            Pose2d(10.feet, 10.feet)
        ),
        listOf<TimingConstraint<Pose2dWithCurvature>>(),
        0.0.feet.velocity,
        0.0.feet.velocity,
        TrajectoryConstants.kMaxVelocity,
        TrajectoryConstants.kMaxAcceleration,
        true
    )

    val routine: Routine = Routine(
        "default",
        Pose2d(),
        DriveTrajectoryAction(Drivetrain, Drivetrain.trajectoryFollower, trajectory)
    )

    override fun robotInit() {}

    override fun robotPeriodic() {}

    override fun disabledInit() {}

    override fun disabledPeriodic() {}

    override fun autonomousInit() {
        routine.start()
    }

    override fun autonomousPeriodic() {
        routine.update()
    }

    override fun teleopInit() {
    }

    override fun teleopPeriodic() {
    }
}
