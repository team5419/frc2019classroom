package org.team5419.frc2019

import edu.wpi.first.wpilibj.TimedRobot
import edu.wpi.first.wpilibj.XboxController
import edu.wpi.first.wpilibj.GenericHID.Hand

import org.team5419.fault.auto.Routine
import org.team5419.fault.auto.DriveTrajectoryAction
import org.team5419.fault.auto.DriveStraightAction
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
import org.team5419.fault.input.DriveSignal
import org.team5419.fault.input.DriveHelper
import org.team5419.fault.input.SpaceDriveHelper

@SuppressWarnings("MagicNumber")
class Robot : TimedRobot() {
    private val mXboxController: XboxController = XboxController(0)
    val trajectoryGenerator: TrajectoryGenerator = DefaultTrajectoryGenerator
    val controller = XboxController(0)
    private val kMaxVelocity = 10.feet.velocity
    private val kMaxAcceleration = 2.feet.acceleration
    private val kMaxHabVelocity = 2.feet.velocity
    private val kFirstPathVMaxAcceleration = 6.feet.velocity
    private val kVelocityRadiusConstraintRadius = 3.feet
    private val kVelocityRadiusConstraintVelocity = 3.feet.velocity
    private val kMaxCentripetalAcceleration = 9.feet.acceleration
    private val kMaxVoltage = 10.volts

    private val driveHelper: DriveHelper = SpaceDriveHelper(
        { controller.getY(Hand.kLeft) },
        { controller.getX(Hand.kRight) },
        { controller.getBumper(Hand.kLeft) },
        { controller.getBumper(Hand.kRight) }
    )

    val trajectory: TimedTrajectory<Pose2dWithCurvature> = trajectoryGenerator.generateTrajectory(
        listOf(
            Pose2d(100.0.feet, 0.0.feet),
            Pose2d(101.0.feet, 0.0.feet)
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


    var routine = generateRoutines()
    
    fun generateRoutines() : Routine {
        val trajectoryRoutine: Routine = Routine(
            "default",
            Pose2d(),
            DriveTrajectoryAction(Drivetrain, Drivetrain.trajectoryFollower, trajectory)
        )
        val driveStraightRoutine = Routine(
            "drive straight",
            Pose2d(),
            DriveStraightAction(Drivetrain, 10.feet)
        )
        return trajectoryRoutine
    }


    override fun robotInit() {
    }

    override fun robotPeriodic() {
        // println(Drivetrain.leftDistance.value.toString() + " " + Drivetrain.rightDistance.value.toString() + " " + Drivetrain.angle.degree.toString())
        println(
            "angle: " + Drivetrain.robotPosition.rotation.degree.toString() + "\n" +
            "left: " + Drivetrain.leftDistance.value.toString() + "\n" +
            "right: " + Drivetrain.rightDistance.value.toString()
        )
        Drivetrain.periodic()
    }

    override fun disabledInit() {
        Drivetrain.zeroOutputs()
    }

    override fun disabledPeriodic() {}

    override fun autonomousInit() {
        println("start routine")
        routine = generateRoutines()
        routine.start()
    }

    override fun autonomousPeriodic() {
        // routine.update()
    }

    override fun teleopInit() {
        // println(trajectory.path.toString())
    }

    override fun teleopPeriodic() {
        // println("Left: " + Drivetrain.leftDistance.toString() + "Right: " + Drivetrain.rightDistance.toString())
        Drivetrain.setPercent(driveHelper.output())
    }
}
