package org.team5499.frc2019.controllers

import org.team5499.monkeyLib.Controller
import org.team5499.monkeyLib.auto.Routine
import org.team5499.monkeyLib.auto.Action
import org.team5499.monkeyLib.math.geometry.Rotation2d

import org.team5499.frc2019.subsystems.SubsystemsManager
import org.team5499.frc2019.auto.Routines
import org.team5499.frc2019.subsystems.Vision.LEDState
import org.team5499.frc2019.subsystems.Vision.VisionMode

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser

public class AutoController(subsystems: SubsystemsManager, routines: Routines) : Controller() {

    private val mSubsystems: SubsystemsManager
    private val mRoutines: Routines

    private val mAutoSelector: SendableChooser<Routine>
    // private val mAllianceSelector: StringChooser

    private var currentRoutine: Routine
    private var currentAction: Action?

    private var isFinished: Boolean

    init {
        mSubsystems = subsystems
        mRoutines = routines
        mAutoSelector = SendableChooser<Routine>()

        isFinished = false
        currentRoutine = mRoutines.baseline
        currentAction = null
        // val tempArray = Array<String>(mRoutines.routineMap.size, { "" })
        // var i = 0
        // for ((key, _) in mRoutines.routineMap) {
        //     mAutoSelector.addOption(mRoutines.routineMap[i].name, mRoutines.routineMap[i])
        //     i++
        // }

        // @Suppress("SpreadOperator")

        // mAutoSelector = SendableChooser("AUTO_MODE", "baseline", *tempArray)
        // mRoutines.forEach(
        //     { mAutoSelector.addOption(it.name, it) }
        // )

        // mAllianceSelector = StringChooser("ALLIANCE_COLOR", "Blue", "Blue", "Red", "None")
    }

    public override fun start() {
        println("auto controller starting")
        reset()
        // val routine = mRoutines.getRoutineWithName(mAutoSelector.selected)
        // currentRoutine = if (routine == null) mRoutines.baseline else routine
        currentRoutine = mRoutines.test
        mSubsystems.drivetrain.brakeMode = true
        mSubsystems.vision.ledState = LEDState.ON
        mSubsystems.vision.visionMode = VisionMode.VISION
        mSubsystems.drivetrain.heading = Rotation2d(currentRoutine.startPose.rotation)
        mSubsystems.drivetrain.setPosition(currentRoutine.startPose.translation)
        currentAction = currentRoutine.getCurrentAction()
        currentAction!!.start()
    }

    public override fun update() {
        if (isFinished) {
            return
        }
        if (currentRoutine.isLastStep() && currentAction!!.next()) {
            currentAction!!.finish()
            isFinished = true
            return
        }
        if (currentAction == null) {
            currentAction = currentRoutine.getCurrentAction()
            currentAction!!.start()
        } else if (currentAction!!.next()) {
            currentAction!!.finish()
            currentRoutine.advanceRoutine()
            currentAction = currentRoutine.getCurrentAction()
            currentAction!!.start()
        } else {
            currentAction!!.update()
        }
    }

    public override fun reset() {
        currentAction = null
        currentRoutine = mRoutines.baseline
        mRoutines.resetAll()
        isFinished = false
    }
}
