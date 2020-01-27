package org.team5419.frc2019

import edu.wpi.first.wpilibj.TimedRobot
import edu.wpi.first.wpilibj.XboxController
import edu.wpi.first.wpilibj.GenericHID.Hand

import org.team5419.fault.auto.Routine
import org.team5419.fault.auto.DriveTrajectoryAction
import org.team5419.fault.auto.DriveStraightAction
import org.team5419.fault.math.units.*
import org.team5419.fault.math.units.derived.*
import org.team5419.fault.math.geometry.Pose2dWithCurvature
import org.team5419.frc2019.subsystems.Drivetrain
import org.team5419.fault.input.SpaceDriveHelper
import com.ctre.phoenix.motorcontrol.ControlMode

import edu.wpi.first.wpilibj.kinematics.DifferentialDriveKinematics
import edu.wpi.first.wpilibj.trajectory.*
import edu.wpi.first.wpilibj.controller.*
import edu.wpi.first.wpilibj.geometry.*
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveWheelSpeeds
import edu.wpi.first.wpilibj.Timer

@SuppressWarnings("MagicNumber")
class Robot : TimedRobot() {
    val xboxController = XboxController(0)

    val ks = 2.17
    val kv = 0.041
    val ka = 0.0279
    val r2 = 0.997
    val kt = 2.77
    val kd = 1.4

    private val driveHelper = SpaceDriveHelper(
        { xboxController.getY(Hand.kLeft) },
        { xboxController.getX(Hand.kRight) },
        { xboxController.getBumper(Hand.kLeft) },
        { xboxController.getBumper(Hand.kRight) }
    )

    private val kMaxVelocity = 3.0
    private val kMaxAcceleration = 3.0

    val config = TrajectoryConfig( kMaxVelocity, kMaxAcceleration )

    val trajectory = TrajectoryGenerator.generateTrajectory(
        // Start at the origin facing the +X direction
        Pose2d(0.0, 0.0, Rotation2d(0.0)),
        // Pass through these two interior waypoints, making an 's' curve path
        listOf<Translation2d>(
            // Translation2d(1.0, 1.0),
            // Translation2d(2.0, -1.0)
        ),
        // End 3 meters straight ahead of where we started, facing forward
        Pose2d(0.0, -3.0, Rotation2d(0.0)),
        // Pass config
        config
    )

    val controller = RamseteController(DriveConstants.kBeta, DriveConstants.kZeta)

    val differentialDriveKinematics = DifferentialDriveKinematics(
        DriveConstants.kTrackWidth.inMeters()
    )

    val feedforward = SimpleMotorFeedforward( ka, ks, kv )

    var prevTime = 0.0
    var prevSpeed = DifferentialDriveWheelSpeeds(0.0, 0.0)

    val timer = Timer()

    override fun robotInit() {}

    override fun robotPeriodic() {
        Drivetrain.periodic()

        println( "angle: " + Drivetrain.angle.degree )
        println( "left distance: " + Drivetrain.leftDistance.inMeters() )
        println( "right distance: " + Drivetrain.rightDistance.inMeters() )
    }

    override fun disabledInit() {
        Drivetrain.zeroOutputs()
    }

    override fun disabledPeriodic() {}

    override fun autonomousInit() {
        timer.start()
    }

    override fun autonomousPeriodic() {
        val time = timer.get()
        val dt = time - prevTime

        prevTime = time

        val chassisSpeed = controller.calculate(
            Pose2d(
                Drivetrain.leftDistance.inMeters(),
                Drivetrain.rightDistance.inMeters(),
                Rotation2d( Drivetrain.angle.radian.value )
            ),
            trajectory.sample(time)
        )

        val out = differentialDriveKinematics.toWheelSpeeds( chassisSpeed )

        val leftSpeedSetpoint = out.leftMetersPerSecond
        val rightSpeedSetpoint = out.rightMetersPerSecond

        val leftFeedForward = feedforward.calculate(
            leftSpeedSetpoint,
            (leftSpeedSetpoint - prevSpeed.leftMetersPerSecond) / dt
        )

        val rightFeedForward = feedforward.calculate(
            rightSpeedSetpoint,
            (rightSpeedSetpoint - prevSpeed.rightMetersPerSecond) / dt
        )

        prevSpeed = out

        Drivetrain.setVelocity(
            out.leftMetersPerSecond.meters.velocity,
            out.rightMetersPerSecond.meters.velocity,
            leftFeedForward.volts,
            rightFeedForward.volts
        )

        println( "target right velocity: " + out.rightMetersPerSecond )
        println( "target left velocity: " + out.leftMetersPerSecond )
        println( "target linear velocity: " + chassisSpeed.vxMetersPerSecond )
        println( "target angular velocity: " + chassisSpeed.omegaRadiansPerSecond )
    }

    override fun teleopInit() {
    }

    override fun teleopPeriodic() {
        Drivetrain.setPercent(
            xboxController.getY(Hand.kLeft),
            xboxController.getY(Hand.kRight)
        )
    }
}
