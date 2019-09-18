package org.team5419.frc2019.input

import edu.wpi.first.wpilibj.XboxController
import edu.wpi.first.wpilibj.GenericHID.Hand

public class XBoxDriver(
    private val mXbox: XboxController
) : IDriverControls {
    public override fun getLeft() = mXbox.getY(Hand.kLeft)

    public override fun getRight() = mXbox.getY(Hand.kRight)
}
