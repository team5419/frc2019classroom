package org.team5419.frc2019classroom

import org.team5419.frc2019classroom.AutoController
import org.team5419.frc2019classroom.Drivetrain 

import org.team5419.fault.auto.Routine
import org.team5419.fault.hardware.ctre.BerkeliumSPX
import org.team5419.fault.hardware.ctre.BerkeliumSRX
import org.team5419.fault.auto.QuasistaticCharacterizationAction


import com.ctre.phoenix.motorcontrol.ControlMode
import com.ctre.phoenix.sensors.PigeonIMU
import edu.wpi.first.wpilibj.TimedRobot

@SuppressWarnings("MagicNumber")
class Robot : TimedRobot() {

    private val mLeftMaster: BerkeliumSRX
    private val mLeftSlave1: BerkeliumSPX
    private val mLeftSlave2: BerkeliumSPX

    private val mRightMaster: BerkeliumSRX
    private val mRightSlave1: BerkeliumSPX
    private val mRightSlave2: BerkeliumSPX

    private val mGyro: PigeonIMU 

    private val mRoutine: Routine
    private val mDrivetrain: Drivetrain
    private val mAutoController: AutoController

    init {
        mLeftMaster = BerkeliumSRX(6)
        mLeftSlave1 = BerkeliumSPX(7)
        mLeftSlave2 = BerkeliumSPX(8)
        mRightMaster = BerkeliumSRX(12)
        mRightSlave1 = BerkeliumSPX(2)
        mRightSlave2 = BerkeliumSPX(3)
        mGyro = PigeonIMU(13)

        mDrivetrain = Drivetrain(
            mLeftMaster,
            mLeftSlave1,
            mLeftSlave2,
            mRightMaster,
            mRightSlave1,
            mRightSlave2,
            mGyro
        )

        mRoutine = Routine(
            "Routine",
            QuasistaticCharacterizationAction(
                mDrivetrain,
                2.0,
                1.0,
                1.0
            )
        )

        mAutoController = AutoController(mRoutine, mDrivetrain)

    }

    override fun robotInit() {
    }

    override fun robotPeriodic() {
        mDrivetrain.update()
    }

    override fun disabledInit() {
        
    }

    override fun disabledPeriodic() {

    }

    override fun autonomousInit() {

    }

    override fun autonomousPeriodic() {

    }
}
