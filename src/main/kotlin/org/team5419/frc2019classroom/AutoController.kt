package org.team5419.frc2019classroom.controllers

import org.team5419.fault.Controller
import org.team5419.fault.auto.Routine
import org.team5419.fault.auto.Action
import org.team5419.fault.math.geometry.Rotation2d

import org.team5419.frc2019classroom.Drivetrain

public class AutoController(drivetrain: Drivetrain, routine: Routine) : Controller() {

    private val mDrivetrain: Drivetrain
    private val mRoutine: Routine
    private var currentAction: Action?
    private var isFinished: Boolean

    init {
        mDrivetrain = drivetrain
        mRoutine = routine
        isFinished = false
        currentAction = null
    }

    public override fun start() {
        // TODO choose routine from dashboard
        println("auto controller starting")
        reset()
        // mRoutine = mRoutines.rocketRight
        mDrivetrain.brakeMode = true
        mDrivetrain.heading = Rotation2d(mRoutine.startPose.rotation)
        mDrivetrain.setPosition(mRoutine.startPose.translation)
        currentAction = mRoutine.getCurrentAction()
        currentAction!!.start()
    }

    public override fun update() {
        if (isFinished) {
            return
        }
        if (mRoutine.isLastStep() && currentAction!!.next()) {
            currentAction!!.finish()
            isFinished = true
            return
        }
        if (currentAction == null) {
            currentAction = mRoutine.getCurrentAction()
            currentAction!!.start()
        } else if (currentAction!!.next()) {
            currentAction!!.finish()
            mRoutine.advanceRoutine()
            currentAction = mRoutine.getCurrentAction()
            currentAction!!.start()
        } else {
            currentAction!!.update()
        }
    }

    public override fun reset() {
        currentAction = null
        mRoutine = mRoutine.baseline
        mRoutine.resetAll()
        isFinished = false
    }
}
