package tests

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.assertFalse

import org.team5419.frc2019.Constants

public class ConstantsTest {
    @Test
    fun checkRangeOfPID() {
        assertFalse(Constants.PID.E_KP < 0)
        assertFalse(Constants.PID.E_KI < 0)
        assertFalse(Constants.PID.E_KD < 0)
    }
}
