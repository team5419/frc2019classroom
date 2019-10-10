package org.team5419.frc2019.maths

import kotlin.math.pow

@SuppressWarnings("MagicNumber")
public object MathFunctions {
    fun roundToDecimal(value: Double, decimals: Int): Double {
        var multiplier = (10.0).pow(decimals)
        return Math.round(value * multiplier) / multiplier
    }
}
