package org.team5419.frc2019.controllers

import edu.wpi.first.wpilibj.GenericHID
import org.team5419.frc2019.Controls
import org.team5419.frc2019.subsystems.Drivetrain
import org.team5499.monkeyLib.Controller
import org.team5499.monkeyLib.input.MonkeyXboxController
import org.team5499.monkeyLib.input.SpaceDriveHelper
import org.team5499.monkeyLib.input.XboxButton
import org.team5499.monkeyLib.input.getRawButton
import org.team5499.monkeyLib.input.getX
import org.team5499.monkeyLib.input.getY

object TeleopController : Controller {

    private val mHelper = SpaceDriveHelper(
            Controls.driverXbox.getY(GenericHID.Hand.kLeft),
            Controls.driverXbox.getX(GenericHID.Hand.kRight),
            Controls.driverXbox.getRawButton(XboxButton.RightBumper),
            { false },
            Controls.kDefaultDeadband,
            Controls.kSpaceDriveTurnConst,
            Controls.kSlowMultiplier
    )

    init {}


    override fun start() {

    }

    override fun update() {
        val output = mHelper.output()
        Drivetrain.setOpenLoop(output.left, output.right)
    }

    override fun reset() {

    }

}