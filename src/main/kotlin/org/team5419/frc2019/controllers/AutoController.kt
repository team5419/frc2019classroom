package org.team5419.frc2019.controllers

import org.team5419.frc2019.auto.Routines
import org.team5419.frc2019.subsystems.Drivetrain
import org.team5499.monkeyLib.Controller
import org.team5499.monkeyLib.auto.Routine

object AutoController : Controller {

    private var mSelectedRoutine: Routine? = null
    private var mFinished = false

    override fun start() {
        mSelectedRoutine = Routines.kTestRoutine
        Drivetrain.robotPosition = mSelectedRoutine!!.startPose
    }

    override fun update() {
        if(mFinished) {
            return
        } else if(!mFinished && mSelectedRoutine!!.next()) {
            mSelectedRoutine!!.finish()
            mFinished = true
        }
        mSelectedRoutine!!.update()
    }

    override fun reset() {
        mFinished = false
    }

}
