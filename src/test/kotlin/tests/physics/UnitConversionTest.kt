package tests.physics

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue

import org.team5419.frc2019.UnitConverter

class UnitConversionTest {
    @Test
    fun sensorPositionToInchesTest() {
        assertEquals(-0.0, UnitConverter.sensorPositionToInches(0)) // -0.0 because why not
        var e = UnitConverter.sensorPositionToInches(UnitConverter.inchesToSensorPosition(10.0))
        assertTrue(e >= 10.0 || e <= 10.001) // assertTrue because decimals are a fish
    }
}
