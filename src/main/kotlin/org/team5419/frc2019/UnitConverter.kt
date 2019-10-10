package org.team5419.frc2019

import org.team5419.frc2019.maths.MathFunctions

@SuppressWarnings("MagicNumber")
public object UnitConverter {
    fun sensorPositionToInches(sensorPosition: Int): Double {
        var initialRotations = (sensorPosition / (Constants.RobotValues.GEAR_REDUCTION))
        initialRotations /= Constants.RobotValues.TICKS_PER_ROTATION
        return -2 * (Constants.Math.PI * (Constants.RobotValues.SPROCKET_DIAMETER * initialRotations))
    }

    fun inchesToSensorPosition(inches: Double): Int {
        var e = inches / -2
        e /= Constants.Math.PI
        e /= Constants.RobotValues.SPROCKET_DIAMETER
        e *= Constants.RobotValues.TICKS_PER_ROTATION
        return MathFunctions.roundToDecimal(e * Constants.RobotValues.GEAR_REDUCTION, 0).toInt()
    }
}
