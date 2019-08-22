package org.team5419.frc2019

object Constants {

    const val CTRE_TIMEOUT_MS = 100
    const val SENSOR_FEEDBACK_MS = 5
    const val LOOPER_PERIOD = 0.005

    object Drivetrain {

        // ports
        const val LEFT_MASTER_PORT = 1
        const val LEFT_SLAVE1_PORT = 3
        const val LEFT_SLAVE2_PORT = 4

        const val RIGHT_MASTER_PORT = 5
        const val RIGHT_SLAVE1_PORT = 6
        const val RIGHT_SLAVE2_PORT = 7

        const val GYRO_PORT = 8

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

        const val TURN_IZONE = 400 // stolen from citrus

        const val IZONE = 0

        // model data (SI UNITS!!!!!!!!!!!!!!!!!!!!!)
        const val MAX_VOLTAGE = 12.0 // Volts
        const val FREE_SPEED = 0.0 // rad / s

        const val DRIVE_KV = MAX_VOLTAGE / FREE_SPEED // V / (rad / s)
        const val DRIVE_KA = 0.0 // V / (rad / s^2 )
        const val DRIVE_KS = 0.0 // static friction voltage (V)

        const val MASS = 52.1631 // kg (roughly 115 lbs)

        const val MOMENT_OF_INERTIA = 0.0 // kg * m^2
        const val ANGULAR_DRAG = 0.0 // (N*m) / (Rad / s) // TUNE ME

        const val WHEEL_RADIUS_METERS = 0.1524 // meters (6 inches)
        const val EFFECTIVE_WHEELBASE_RADIUS = 0.7112 / 2.0 // meters (14 inches(?))

        const val SPEED_PER_VOLT = 1.0 / DRIVE_KV // (rads / s ) / v
        const val TORQUE_PER_VOLT = WHEEL_RADIUS_METERS * WHEEL_RADIUS_METERS * MASS / (2.0 * DRIVE_KA)
        const val FRICTION_VOLTAGE = DRIVE_KS

        // control
        const val MOTION_MAGIC_VELOCITY = 2000
        const val MOTION_MAGIC_ACCELERATION = 2000

        const val CLOSED_LOOP_RAMP = 0.0 // seconds from 0 to full
        const val ALLOWABLE_TURN_ERROR = 0

        // voltage comp
        const val VOLTAGE_COMP_FILTER = 32

        // dimension
        const val WHEEL_DIAMETER = 6.0 // inches
        const val WHEEL_RADIUS = WHEEL_DIAMETER / 2.0
        const val WHEEL_CIR = WHEEL_DIAMETER * Math.PI
        const val TICKS_PER_ROTATION = 4096
    }
}
