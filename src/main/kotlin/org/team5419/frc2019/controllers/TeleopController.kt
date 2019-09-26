package org.team5419.frc2019.controllers

import org.team5419.fault.Controller
import org.team5419.frc2019.subsystems.SubsystemsManager

import edu.wpi.first.wpilibj.XboxController
import edu.wpi.first.wpilibj.GenericHID.Hand

public class TeleopController(
    private val mSubsystems: SubsystemsManager
) : Controller() {
    private val xbox: XboxController

    init {
        xbox = XboxController(0)
    }
    public override fun start() {
    }

    public override fun update() {
        mSubsystems.drivetrain.set(xbox.getY(Hand.kRight), xbox.getY(Hand.kRight))
    }

    public override fun reset() {
    }
}
