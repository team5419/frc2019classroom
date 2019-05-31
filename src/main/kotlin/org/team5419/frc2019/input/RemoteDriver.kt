package org.team5419.frc2019.input

import edu.wpi.first.wpilibj.XboxController

public class RemoteDriver(xbox: XboxController) : IDriverControls {
    private val mXbox: XboxController

    init {
        mXbox = xbox
    }

    public override fun getGrab() = mXbox.getAButtonPressed()

    public override fun getRealease() = mXbox.getBButtonPressed()
}
