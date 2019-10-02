package tests

import org.junit.jupiter.api.Test

import org.junit.Assert.assertEquals

import org.team5419.frc2019.Constants

public class ConstantsTest {
    @Test
    fun testIfAssertionWorks() {
        assertEquals(3.141592, Constants.Math.PI)
    }
}
