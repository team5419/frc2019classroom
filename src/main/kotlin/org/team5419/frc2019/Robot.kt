package org.team5419.frc2019

import edu.wpi.first.wpilibj.TimedRobot
import org.team5419.fault.hardware.LazyTalonSRX
import org.team5419.fault.hardware.LazyVictorSPX
import org.team5419.frc2019.subsystems.DriveTrain
import org.team5419.frc2019.subsystems.SubsystemsManager
import org.team5419.frc2019.controllers.TeleopController

class Robot : TimedRobot() {

    // hardware

    private val mLeftMaster: LazyTalonSRX
    private val mLeftSlave1: LazyVictorSPX
    private val mLeftSlave2: LazyVictorSPX

    private val mRightMaster: LazyTalonSRX
    private val mRightSlave1: LazyVictorSPX
    private val mRightSlave2: LazyVictorSPX

    // subsystems

    private val mDriveTrain: DriveTrain
    private val mSubsystemsManager: SubsystemsManager

    // controllers

    private val mTeleopController: TeleopController

    init {
        // hardware init

        mLeftMaster = LazyTalonSRX(Constants.DriveTrain.LEFT_MASTER_TALON_PORT)
        mLeftSlave1 = LazyVictorSPX(Constants.DriveTrain.LEFT_SLAVE1_TALON_PORT)
        mLeftSlave2 = LazyVictorSPX(Constants.DriveTrain.LEFT_SLAVE2_TALON_PORT)

        mRightMaster = LazyTalonSRX(Constants.DriveTrain.RIGHT_MASTER_TALON_PORT)
        mRightSlave1 = LazyVictorSPX(Constants.DriveTrain.RIGHT_SLAVE1_TALON_PORT)
        mRightSlave2 = LazyVictorSPX(Constants.DriveTrain.RIGHT_SLAVE2_TALON_PORT)

        // subsystems init

        mDriveTrain = DriveTrain(
            mLeftMaster,
            mLeftSlave1,
            mLeftSlave2,

            mRightMaster,
            mRightSlave1,
            mRightSlave2
        )

        mSubsystemsManager = SubsystemsManager(
            mDriveTrain
        )

        // controllers init

        mTeleopController = TeleopController(
            mSubsystemsManager
        )
    }

    override fun robotInit() {
    }

    override fun robotPeriodic() {
    }

    override fun disabledInit() {
    }

    override fun disabledPeriodic() {
    }

    override fun teleopInit() {
        mTeleopController.start()
    }

    override fun teleopPeriodic() {
        mTeleopController.update()
        mSubsystemsManager.updateAll()
    }
}
