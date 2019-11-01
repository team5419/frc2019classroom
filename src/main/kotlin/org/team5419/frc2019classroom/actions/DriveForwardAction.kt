package org.team5419.frc2019classroom.actions

import org.team5419.fault.auto.Action
import org.team5419.frc2019classroom.Drivetrain

public class DriveForwardAction( timeout: Double, drivetrain: Drivetrain ): Action(timeout) {

    override fun start(){

    }

    override fun update(){
        drivetrain.setPercent(0.5, 0.5)
    }

    override fun finish(){

    }
}