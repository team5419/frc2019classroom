package org.team5419.frc2019.controllers

import org.team5419.frc2019.input.XboxDriver
import org.team5419.frc2019.subsystems.SubsystemsManager

import org.team5419.fault.input.DriveHelper

public class TeleopController(
    subsystems: SubsystemsManager,
    xbox: XboxDriver
) {

    public val mSubsystems: SubsystemsManager
    public val mXbox: XboxDriver

    init {
        mSubsystems = subsystems
        mXbox = xbox
    }

    public override fun update() {
        val driveSignal = mDriveHelper.calculateOutput(mXbox.getThrottle(), mXbox.getTurn())
        mSubsystems.drivetrain.setPercent(driveSignal.left, driveSignal.right)
    }
}