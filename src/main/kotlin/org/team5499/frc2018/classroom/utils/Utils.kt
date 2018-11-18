package org.team5499.frc2018.classroom.utils

import org.team5499.frc2018.classroom.Constants

object Utils {

    const val INCHES_PER_SECOND_PER_ENCODER_TICKS_PER_100MS = 10.0
    const val DEGREES_PER_ROTATION = 360.0

    fun inchesPerSecondToEncoderTicksPer100Ms(ips: Double): Double {
        return inchesToEncoderTicks(ips) / INCHES_PER_SECOND_PER_ENCODER_TICKS_PER_100MS
    }

    fun encoderTicksPer100MsToInchesPerSecond(eps: Int): Double {
        return encoderTicksPer100MsToInchesPerSecond(eps) * INCHES_PER_SECOND_PER_ENCODER_TICKS_PER_100MS
    }

    fun inchesToEncoderTicks(inches: Double): Int {
        // val temp = (Constants.ENCODER_TICKS_PER_ROTATION / Constants.WHEEL_CIR ) * inches
        return ((Constants.ENCODER_TICKS_PER_ROTATION / Constants.WHEEL_CIRCUMFERENCE) * inches).toInt()
    }

    fun encoderTicksToInches(ticks: Int): Double {
        return (Constants.WHEEL_CIRCUMFERENCE / Constants.ENCODER_TICKS_PER_ROTATION) * ticks
    }

    fun talonAngleToDegrees(ticks: Int): Double {
        return (DEGREES_PER_ROTATION / Constants.Gyro.TURN_UNITS_PER_ROTATION) * ticks
    }

    fun degreesToTalonAngle(degrees: Double): Int {
        return ((Constants.Gyro.TURN_UNITS_PER_ROTATION / DEGREES_PER_ROTATION) * degrees).toInt()
    }
}
