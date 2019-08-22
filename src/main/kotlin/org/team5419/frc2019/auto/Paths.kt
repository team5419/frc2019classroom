package org.team5419.frc2019.auto

import org.team5419.frc2019.DriveConstants
import org.team5419.frc2019.RobotConstants
import org.team5499.monkeyLib.math.geometry.Pose2d
import org.team5499.monkeyLib.math.geometry.Pose2dWithCurvature
import org.team5499.monkeyLib.math.geometry.Rectangle2d
import org.team5499.monkeyLib.math.geometry.Vector2
import org.team5499.monkeyLib.math.units.derived.LinearAcceleration
import org.team5499.monkeyLib.math.units.derived.LinearVelocity
import org.team5499.monkeyLib.math.units.derived.Volt
import org.team5499.monkeyLib.math.units.derived.acceleration
import org.team5499.monkeyLib.math.units.derived.velocity
import org.team5499.monkeyLib.math.units.derived.volt
import org.team5499.monkeyLib.math.units.feet
import org.team5499.monkeyLib.math.units.inch
import org.team5499.monkeyLib.trajectory.DefaultTrajectoryGenerator
import org.team5499.monkeyLib.trajectory.constraints.CentripetalAccelerationConstraint
import org.team5499.monkeyLib.trajectory.constraints.DifferentialDriveDynamicsConstraint
import org.team5499.monkeyLib.trajectory.constraints.TimingConstraint
import org.team5499.monkeyLib.trajectory.constraints.VelocityLimitRadiusConstraint
import org.team5499.monkeyLib.trajectory.constraints.VelocityLimitRegionConstraint
import org.team5499.monkeyLib.trajectory.types.TimedTrajectory

object Paths {

    // PARAMETERS
    private val kMaxVelocity = 12.feet.velocity
    private val kMaxAcceleration = 6.feet.acceleration

    private val kMaxHabVelocity = 6.feet.velocity

    private val kFirstPathVMaxAcceleration = 6.feet.velocity

    private val kVelocityRadiusConstraintRadius = 3.feet
    private val kVelocityRadiusConstraintVelocity = 3.feet.velocity

    private val kMaxCentripetalAcceleration = 9.feet.acceleration

    private val kMaxVoltage = 10.volt

    // POSES + DIMENSIONS + LOCATIONS

    val kHabLevel1Platform = Rectangle2d(Vector2(4.feet, 7.feet), Vector2(8.feet, 20.feet))

    val kStart = Pose2d(0.inch, 0.inch)
    val kEnd = Pose2d(100.inch, 100.inch)

    // TRAJECTORY
    val kTestTrajectory1 = generateTrajectory(
            false,
            listOf(kStart, kEnd),
            getConstraints(kEnd),
            kMaxVelocity,
            kMaxAcceleration,
            kMaxVoltage,
            true
    )

    val kTestTrajectory2 = generateTrajectory(
            true,
            listOf(kEnd, kStart),
            getConstraints(kStart),
            kMaxVelocity,
            kMaxAcceleration,
            kMaxVoltage,
            true
    )


    // methods
    private fun getConstraints(trajectoryEndpoint: Pose2d) =
            listOf(
                    CentripetalAccelerationConstraint(kMaxCentripetalAcceleration),
                    VelocityLimitRadiusConstraint(
                            trajectoryEndpoint.translation,
                            kVelocityRadiusConstraintRadius,
                            kVelocityRadiusConstraintVelocity
                    ),
                    VelocityLimitRegionConstraint(kHabLevel1Platform, kMaxHabVelocity)
            )

    private fun generateTrajectory(
            reversed: Boolean,
            points: List<Pose2d>,
            constraints: List<TimingConstraint<Pose2dWithCurvature>>,
            maxVelocity: LinearVelocity,
            maxAcceleration: LinearAcceleration,
            maxVoltage: Volt,
            optimizeCurvature: Boolean = true
    ): TimedTrajectory<Pose2dWithCurvature> {

        val driveDynamicsConstraint = DifferentialDriveDynamicsConstraint(DriveConstants.kDriveModel, maxVoltage)
        val allConstraints = mutableListOf<TimingConstraint<Pose2dWithCurvature>>()

        allConstraints.add(driveDynamicsConstraint)
        if(constraints.isNotEmpty()) allConstraints.addAll(constraints)

        return DefaultTrajectoryGenerator.generateTrajectory(
                points,
                allConstraints,
                0.inch.velocity,
                0.inch.velocity,
                maxVelocity,
                maxAcceleration,
                reversed,
                optimizeCurvature
        )
    }



}