package org.team5419.frc2019.controllers

import org.team5419.fault.Controller
import org.team5419.frc2019.subsystems.SubsystemsManager
import org.team5419.frc2019.input.ControlBoard

public class TeleopController(
    private val mSubsystems: SubsystemsManager,
    private val mControlBoard: ControlBoard
) : Controller() {
    public override fun start() {
    }

    public override fun update() {
        mSubsystems.drivetrain.setLeft(mControlBoard.driverControls.getLeft())
        mSubsystems.drivetrain.setLeft(mControlBoard.driverControls.getRight())
    }

    public override fun reset() {
    }
}
