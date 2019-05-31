package org.team5419.frc2019.controllers

import org.team5419.fault.Controller

@Suppress("TooManyFunctions")
public class ControllersManager(
    teleopController: Controller,
    autoController: Controller
) {
    private val mTeleopController: Controller
    private val mAutoController: Controller

    init {
        mTeleopController = teleopController
        mAutoController = autoController
    }

    // general

    fun resetAll() {
        mTeleopController.reset()
        mAutoController.reset()
    }

    // robot

    fun robotInit() {
        mTeleopController.start()
        mAutoController.start()
    }

    fun robotPeriodic() {
    }

    // disabled mode

    fun disabledInit() {
        resetAll()
    }

    fun disabledPeriodic() {
    }

    // autonomous mode

    fun autonomousInit() {
        resetAll()

        mAutoController.start()
    }

    fun autonomousPeriodic() {
        mAutoController.update()
    }

    // teleop mode

    fun teleopInit() {
        resetAll()

        mTeleopController.start()
    }

    fun teleopPeriodic() {
        mTeleopController.update()
    }

    // test mode

    fun testInit() {
        resetAll()
    }

    fun testPeriodic() {
    }
}
