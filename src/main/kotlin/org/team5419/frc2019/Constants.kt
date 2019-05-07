package org.team5419.frc2019

object Constants {

    const val CTRE_TIMEOUT_MS = 100
    const val SENSOR_FEEDBACK_MS = 5

    object Drivetrain {

        // pid constants
        const val POSITION_KP = 0.0
        const val POSITION_KI = 0.0
        const val POSITION_KD = 0.0
        const val POSITION_KF = 0.0

        const val VELOCITY_KP = 0.0
        const val VELOCITY_KI = 0.0
        const val VELOCITY_KD = 0.0
        const val VELOCITY_KF = 0.0

        const val TURN_KP = 0.0
        const val TURN_KI = 0.0
        const val TURN_KD = 0.0
        const val TURN_KF = 0.0

        const val IZONE = 0

        // model data (SI UNITS!!!!!!!!!!!!!!!!!!!!!)
        const val MASS = 0.0 // kg
        const val MOMENT_OF_INERTIA = 0.0
        const val ANGULAR_DRAG = 0.0
        const val WHEEL_RADIUS_METERS = 0.0 // meters
        const val EFFECTIVE_WHEELBASE_RADIUS = 0.0 // meters

        const val SPEED_PER_VOLT = 0.0 // m / s / volts
        const val TORQUE_PER_VOLT = 0.0
        const val FRICTION_VOLTAGE = 0.0 // volts

        // control
        const val MOTION_MAGIC_VELOCITY = 2000
        const val MOTION_MAGIC_ACCELERATION = 2000
        const val CLOSED_LOOP_RAMP = 0.0 // seconds
        const val ALLOWABLE_TURN_ERROR = 0

        // voltage comp
        const val VOLTAGE_COMP_SATURATION = 12.0
        const val VOLTAGE_COMP_FILTER = 32

        // dimension
        const val WHEEL_DIAMETER = 6.0 // inches
        const val WHEEL_RADIUS = WHEEL_DIAMETER / 2.0
        const val WHEEL_CIR = WHEEL_DIAMETER * Math.PI
        const val TICKS_PER_ROTATION = 4096
    }
}
