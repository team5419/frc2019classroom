package org.team5419.frc2019

import org.team5419.frc2019.subsystems.Drivetrain

import edu.wpi.first.wpilibj.TimedRobot
import edu.wpi.first.wpilibj.GenericHID.Hand

import edu.wpi.first.wpilibj.XboxController

import org.team5499.monkeyLib.hardware.LazyTalonSRX
import org.team5499.monkeyLib.hardware.LazyVictorSPX
import org.team5499.monkeyLib.util.loops.Looper
import org.team5499.monkeyLib.subsystems.SubsystemsManager

import com.ctre.phoenix.sensors.PigeonIMU

class Robot : TimedRobot() {

    private val mLooper: Looper

    // hardware
    private val mLeftMaster: LazyTalonSRX
    private val mLeftSlave1: LazyVictorSPX
    private val mLeftSlave2: LazyVictorSPX

    private val mRightMaster: LazyTalonSRX
    private val mRightSlave1: LazyVictorSPX
    private val mRightSlave2: LazyVictorSPX

    private val mGyro: PigeonIMU

    // subsystems
    private val mSubsystemsManager: SubsystemsManager
    private val mDrivetrain: Drivetrain

    // input
    private val mDriverController: XboxController

    init {
        mLooper = Looper(Constants.LOOPER_PERIOD)

        // init hardware
        mLeftMaster = LazyTalonSRX(Constants.Drivetrain.LEFT_MASTER_PORT)
        mLeftSlave1 = LazyVictorSPX(Constants.Drivetrain.LEFT_SLAVE1_PORT)
        mLeftSlave2 = LazyVictorSPX(Constants.Drivetrain.LEFT_SLAVE2_PORT)

        mRightMaster = LazyTalonSRX(Constants.Drivetrain.RIGHT_MASTER_PORT)
        mRightSlave1 = LazyVictorSPX(Constants.Drivetrain.RIGHT_SLAVE1_PORT)
        mRightSlave2 = LazyVictorSPX(Constants.Drivetrain.RIGHT_SLAVE2_PORT)

        mGyro = PigeonIMU(Constants.Drivetrain.GYRO_PORT)

        // reset hardware
        mLeftMaster.configFactoryDefault()
        mLeftSlave1.configFactoryDefault()
        mLeftSlave2.configFactoryDefault()

        mRightMaster.configFactoryDefault()
        mRightSlave1.configFactoryDefault()
        mRightSlave2.configFactoryDefault()

        mGyro.configFactoryDefault()

        // initialize subsystems
        mDrivetrain = Drivetrain(
            mLeftMaster,
            mLeftSlave1,
            mLeftSlave2,
            mRightMaster,
            mRightSlave1,
            mRightSlave2,
            mGyro
        )

        // initialize inputs
        mDriverController = XboxController(1)

        mSubsystemsManager = SubsystemsManager(mDrivetrain)
        mSubsystemsManager.registerLoops(mLooper)
    }

    override fun disabledInit() {
        mSubsystemsManager.stop()
        mLooper.stop()
    }

    override fun disabledPeriodic() {
        mSubsystemsManager.zeroSensors()
        mDrivetrain.loadGains()
    }

    override fun autonomousInit() {
        mLooper.start()
    }

    override fun autonomousPeriodic() {
    }

    override fun teleopInit() {
        mLooper.start()
    }

    override fun teleopPeriodic() {
        mDrivetrain.setPercent(
            -mDriverController.getY(Hand.kLeft),
            -mDriverController.getY(Hand.kRight)
        )
    }

    override fun testInit() {
    }

    override fun testPeriodic() {
    }
}
