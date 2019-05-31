package org.team5419.frc2019.input

import edu.wpi.first.wpilibj.XboxController
import edu.wpi.first.wpilibj.GenericHID.Hand

public class XboxDriver(xbox: XboxController) {
    public val mXbox: XboxController

    init {
        mXbox = xbox
    }

    public fun getThrottle() = mXbox.getY(Hand.kLeft)

    public fun getRight() = mXbox.getY(Hand.kRight)

    public fun getTurn() = mXbox.getX(Hand.kRight)
}