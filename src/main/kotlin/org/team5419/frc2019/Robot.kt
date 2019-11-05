package org.team5419.frc2019

import org.team5419.frc2019.AutoController
import org.team5419.frc2019.Drivetrain

import org.team5419.fault.auto.Routine
import org.team5419.fault.hardware.LazyTalonSRX
import org.team5419.fault.hardware.LazyVictorSPX
import org.team5419.fault.auto.QuasistaticCharacterizationAction
import org.team5419.fault.auto.StepVoltageCharacterizationAction



import com.ctre.phoenix.motorcontrol.ControlMode
import com.ctre.phoenix.sensors.PigeonIMU
import edu.wpi.first.wpilibj.TimedRobot

@SuppressWarnings("MagicNumber")
class Robot : TimedRobot() {

    private val mLeftMaster: LazyTalonSRX
    private val mLeftSlave1: LazyVictorSPX
    private val mLeftSlave2: LazyVictorSPX

    private val mRightMaster: LazyTalonSRX
    private val mRightSlave1: LazyVictorSPX
    private val mRightSlave2: LazyVictorSPX

    private val mGyro: PigeonIMU

    private val mRoutine: Routine
    private val mDrivetrain: Drivetrain
    private val mAutoController: AutoController

    init {
        mLeftMaster = LazyTalonSRX(6)
        mLeftSlave1 = LazyVictorSPX(7)
        mLeftSlave2 = LazyVictorSPX(8)
        mRightMaster = LazyTalonSRX(12)
        mRightSlave1 = LazyVictorSPX(2)
        mRightSlave2 = LazyVictorSPX(3)
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
				mDrivetrain/*,
				wheelRadius,
				effectiveWheelbaseRadius*/
			)
        )

        mAutoController = AutoController(mRoutine)

    }

    override fun robotInit() {
    }

    override fun robotPeriodic() {
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
