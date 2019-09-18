package org.team5419.frc2019.input

public class ControlBoard(
    public val driverControls: IDriverControls
) {

    companion object {
        private const val kThreshold = 0.05
    }
}
