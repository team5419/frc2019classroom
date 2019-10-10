package tests.physics

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.assertEquals

import org.team5419.frc2019.UnitConverter
import org.team5419.frc2019.maths.MathFunctions

class UnitConversionTest {
    @Test
    fun mathFunctionsTest() {
        var a = MathFunctions.roundToDecimal(5.141251234, 2)
        var b = MathFunctions.roundToDecimal(1.1234512, 0)
        assertEquals(5.14, a)
        assertEquals(1.0, b)
    }

    @Test
    fun sensorPositionToInchesTest() {
        assertEquals(-0.0, UnitConverter.sensorPositionToInches(0)) // -0.0 because why not
        var a: Int = UnitConverter.inchesToSensorPosition(UnitConverter.sensorPositionToInches(10))
        assertEquals(a, 10)
    }
}
