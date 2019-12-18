package org.team5419.frc2019

import org.team5419.fault.math.physics.DCMotorTransmission
import org.team5419.fault.math.physics.DifferentialDrive
import org.team5419.fault.math.units.derived.acceleration
import org.team5419.fault.math.units.derived.velocity
import org.team5419.fault.math.units.derived.radians
import org.team5419.fault.math.units.*
import org.team5419.fault.math.kEpsilon

import org.team5419.fault.math.units.native.NativeUnitLengthModel
import org.team5419.fault.math.units.native.nativeUnits
import kotlin.math.PI
import kotlin.math.pow

@SuppressWarnings("MaxLineLength, WildcardImport")
object RobotConstants {
    val kRobotLength = 32.inches
    val kRobotWidth = 27.5.inches
    val kBumperThickness = 2.inches
}

object TrajectoryConstants {
    val kMaxCentripetalAcceleration = 4.0.feet.acceleration
    val kMaxAcceleration = 4.0.feet.acceleration
    val kMaxAngularAcceleration = 2.0.radians.acceleration
    val kMaxVelocity = 10.0.feet.velocity
}

object DriveConstants {
    // ports
    const val kLeftMasterPort = 1
    const val kLeftSlave1Port = 2
    const val kLeftSlave2Port = 3

    const val kRightMasterPort = 4
    const val kRightSlave1Port = 5
    const val kRightSlave2Port = 6

    const val kGyroPort = 7

    // misc
    const val kEncoderPhase = true

    // path following parameters
    const val kBeta = 1.0 // m^-2
    const val kZeta = 1.0 // unitless

    val kMotionMagicVelocity = 130.inches.velocity
    val kMotionMagicAcceleration = 50.inches.acceleration

    // dimensions and constants
    val kWheelRadius = 3.inches
    val kWheelDiameter = kWheelRadius * 2.0
    val kWheelCir = kWheelDiameter * PI

    val kTrackWidth = 20.inches
    val kEffectiveWheelbaseRadius = kTrackWidth / 2.0

    val kMass = 120.lbs
    val kMoi = 0.0 // kg * m^2
    val kAngularDrag = 10.0 // (N * m) / (rad / s)  TUNE ME

    val kTicksPerRotation = 4096.nativeUnits
    val kPigeonConversion = (3600.0 / 8192.0).nativeUnits

    const val kDriveKv = kEpsilon
    const val kDriveKa = kEpsilon
    const val kDriveKs = kEpsilon

    val kLeftDriveGearbox = DCMotorTransmission(
            1 / kDriveKv,
            kWheelRadius.value.pow(2) * kMass.value / (2.0 * kDriveKa),
            kDriveKs
    )

    val kRightDriveGearbox = DCMotorTransmission(
            1 / kDriveKv,
            kWheelRadius.value.pow(2) * kMass.value / (2.0 * kDriveKa),
            kDriveKs
    )

    val kDriveModel = DifferentialDrive(
            kMass.value,
            kMoi,
            kAngularDrag, // tune me
            kWheelRadius.value,
            kEffectiveWheelbaseRadius.value,
            kLeftDriveGearbox,
            kRightDriveGearbox
    )

    val kNativeGearboxConversion = NativeUnitLengthModel(
            kTicksPerRotation,
            kWheelRadius
    )
}
