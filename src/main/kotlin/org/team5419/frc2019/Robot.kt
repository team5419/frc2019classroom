package org.team5419.frc2019

import edu.wpi.first.wpilibj.TimedRobot
import edu.wpi.first.wpilibj.XboxController
import edu.wpi.first.wpilibj.GenericHID.Hand

import org.team5419.fault.auto.Routine
import org.team5419.fault.auto.DriveTrajectoryAction
import org.team5419.fault.math.units.*
import org.team5419.fault.math.units.derived.*
import org.team5419.fault.math.geometry.Pose2d
import org.team5419.fault.math.geometry.Vector2d
import org.team5419.fault.math.geometry.Pose2dWithCurvature
import org.team5419.fault.trajectory.types.TimedTrajectory
import org.team5419.fault.trajectory.TrajectoryGenerator
import org.team5419.fault.trajectory.DefaultTrajectoryGenerator
import org.team5419.fault.trajectory.constraints.*

import org.team5419.frc2019.subsystems.Drivetrain

@SuppressWarnings("MagicNumber")
class Robot : TimedRobot() {
    // private val mXboxController: XboxController
    val trajectoryGenerator: TrajectoryGenerator = DefaultTrajectoryGenerator
    val controller = XboxController(0)
    private val kMaxVelocity = 12.feet.velocity
    private val kMaxAcceleration = 6.feet.acceleration
    private val kMaxHabVelocity = 6.feet.velocity
    private val kFirstPathVMaxAcceleration = 6.feet.velocity
    private val kVelocityRadiusConstraintRadius = 3.feet
    private val kVelocityRadiusConstraintVelocity = 3.feet.velocity
    private val kMaxCentripetalAcceleration = 9.feet.acceleration
    private val kMaxVoltage = 10.volts

    val trajectory: TimedTrajectory<Pose2dWithCurvature> = trajectoryGenerator.generateTrajectory(
        listOf(
            Pose2d(10.feet, 0.feet),
            Pose2d(10.feet, 10.feet)
        ),
        listOf<TimingConstraint<Pose2dWithCurvature>>(
            CentripetalAccelerationConstraint(kMaxCentripetalAcceleration),
            VelocityLimitRadiusConstraint(
                Vector2d(10.feet, 10.feet),
                kVelocityRadiusConstraintRadius,
                kVelocityRadiusConstraintVelocity
            ),
            DifferentialDriveDynamicsConstraint(DriveConstants.kDriveModel, kMaxVoltage)
        ),
        0.0.feet.velocity,
        0.0.feet.velocity,
        TrajectoryConstants.kMaxVelocity,
        TrajectoryConstants.kMaxAcceleration,
        true
    )



    val routine: Routine = Routine(
        "default",
        Pose2d(),
        DriveTrajectoryAction(
            Drivetrain,
            Drivetrain.trajectoryFollower,
            trajectory
        )
    )

    override fun robotInit() {}

    override fun robotPeriodic() {
        Drivetrain.periodic()
    }

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
        // println("Left: " + Drivetrain.leftDistance.toString() + "Right: " + Drivetrain.rightDistance.toString())
        Drivetrain.setPercent(controller.getY(Hand.kLeft), controller.getY(Hand.kRight))
    }
}
