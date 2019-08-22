package org.team5419.frc2019

import org.team5499.monkeyLib.input.XboxButton
import org.team5499.monkeyLib.input.button
import org.team5499.monkeyLib.input.xboxController

object Controls {

    const val kDefaultDeadband = 0.02
    const val kSpaceDriveTurnConst = 0.4
    const val kSlowMultiplier = 0.5

    val driverXbox = xboxController(0) {}


    var manualControl = false
        private set


    val codriverXbox = xboxController(1) {

        button(XboxButton.Back) {
            manualControl = true
        }

        button(XboxButton.Start) {
            manualControl = false
        }

        state( {!manualControl} ) {



        }
    }


    fun update() {
        driverXbox.update()
        codriverXbox.update()
    }
}