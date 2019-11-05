package org.team5419.frc2019

import org.team5419.frc2019.Drivetrain
import org.team5419.fault.Controller
import org.team5419.fault.auto.Routine
import org.team5419.fault.auto.Action

class AutoController (routine: Routine) : Controller() {
	private var currentAction: Action?
		get() = routine.getCurrentAction()!!
	private val isFinished: boolean
		get() = routine.isFinished

	init{

	}

    override fun start() {

    }

    override fun update() {
		if (isFinished) {
            return
        }
        if (routine.isLastStep() && currentAction!!.next()) {
            currentAction!!.finish()
            isFinished = true
            return
        }
        else if (currentAction!!.next()) {
            currentAction.finish()
            routine.advanceRoutine()
            currentAction.start()
        } else {
            currentAction.update()
        }
    }

    override fun reset() {

    }
}
