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
    const val kLeftMasterPort = 3
    const val kLeftSlavePort = 9

    const val kRightMasterPort = 5
    const val kRightSlavePort = 4

    const val kGyroPort = 7

    // misc
    const val kLeftEncoderPhase = true
    const val kRightEncoderPhase = true

    // path following parameters
    const val kBeta = 2.0 // m^-2
    const val kZeta = 0.7 // unitless

    val kMotionMagicVelocity = 130.inches.velocity
    val kMotionMagicAcceleration = 50.inches.acceleration

    // dimensions and constants
    val kWheelRadius = 3.inches
    val kWheelDiameter = kWheelRadius * 2.0
    val kWheelCir = kWheelDiameter * PI

    val kTrackWidth = 25.75.inches
    val kEffectiveWheelbaseRadius = kTrackWidth / 2.0

    val kMass = 50.lbs
    val kMoi = kMass * 0.46 * 0.46 // kg * m^2
    val kAngularDrag = 10.0 // (N * m) / (rad / s)  TUNE ME

    val kTicksPerRotation = 2048.nativeUnits
    val kPigeonConversion = (3600.0 / 8192.0).nativeUnits

    const val kLeftDriveKv = 1.71
    const val kLeftDriveKa = 0.348
    const val kLeftDriveKs = 1.66
    const val kRightDriveKv = kEpsilon
    const val kRightDriveKa = kEpsilon
    const val kRightDriveKs = kEpsilon

    val kLeftDriveGearbox = DCMotorTransmission(
        1 / kLeftDriveKv,
        kWheelRadius.value.pow(2) * kMass.value / (2.0 * kLeftDriveKa),
        kLeftDriveKs
    )

    val kRightDriveGearbox = DCMotorTransmission(
        1 / kLeftDriveKv,
        kWheelRadius.value.pow(2) * kMass.value / (2.0 * kLeftDriveKa),
        kLeftDriveKs
    )

    val kDriveModel = DifferentialDrive(
        kMass.value,
        kMoi.value,
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
