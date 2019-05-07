package org.team5419.frc2019.subsystems

import org.team5419.frc2019.Constants

import org.team5499.monkeyLib.subsystems.Subsystem
import org.team5499.monkeyLib.subsystems.drivetrain.IDrivetrain
import org.team5499.monkeyLib.subsystems.drivetrain.IDrivetrain.TurnType
import org.team5499.monkeyLib.subsystems.drivetrain.IDrivetrain.DriveMode
import org.team5499.monkeyLib.hardware.LazyTalonSRX
import org.team5499.monkeyLib.hardware.LazyVictorSPX
import org.team5499.monkeyLib.math.geometry.Rotation2d
import org.team5499.monkeyLib.math.geometry.Vector2
import org.team5499.monkeyLib.math.geometry.Pose2d
import org.team5499.monkeyLib.math.Position
import org.team5499.monkeyLib.math.physics.DifferentialDrive
import org.team5499.monkeyLib.math.physics.DCMotorTransmission
import org.team5499.monkeyLib.util.Utils
import org.team5499.monkeyLib.util.loops.ILooper
import org.team5499.monkeyLib.util.loops.Loop

import com.ctre.phoenix.sensors.PigeonIMU
import com.ctre.phoenix.motorcontrol.FeedbackDevice
import com.ctre.phoenix.motorcontrol.RemoteSensorSource
import com.ctre.phoenix.motorcontrol.InvertType
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced
import com.ctre.phoenix.motorcontrol.VelocityMeasPeriod
import com.ctre.phoenix.motorcontrol.NeutralMode
import com.ctre.phoenix.motorcontrol.ControlMode
import com.ctre.phoenix.motorcontrol.DemandType
import com.ctre.phoenix.motorcontrol.FollowerType

@Suppress("TooManyFunctions")
class Drivetrain(
    private val mLeftMaster: LazyTalonSRX,
    private val mLeftSlave1: LazyVictorSPX,
    private val mLeftSlave2: LazyVictorSPX,
    private val mRightMaster: LazyTalonSRX,
    private val mRightSlave1: LazyVictorSPX,
    private val mRightSlave2: LazyVictorSPX,
    private val mGyro: PigeonIMU
) : Subsystem(), IDrivetrain {

    companion object {
        private const val kPositionSlot = 0
        private const val kVelocitySlot = 1
        private const val kTurnSlot = 2
        private const val kVolts = 12.0
        // i have no idea what the second number is lol. converts degrees to pigeon units or something lol
        private const val kWeirdPigeonConversion = 3600.0 / 8192.0
    }

    init {
        // mess with limit switch config
        mRightMaster.overrideLimitSwitchesEnable(false)
        mLeftMaster.overrideLimitSwitchesEnable(false)

        // configure  encoders feedback
        mLeftMaster.configSelectedFeedbackSensor(
            FeedbackDevice.CTRE_MagEncoder_Relative, kPositionSlot, Constants.CTRE_TIMEOUT_MS
        )
        mLeftMaster.configSelectedFeedbackSensor(
            FeedbackDevice.CTRE_MagEncoder_Relative, kVelocitySlot, Constants.CTRE_TIMEOUT_MS
        )
        mRightMaster.configSelectedFeedbackSensor(
            FeedbackDevice.CTRE_MagEncoder_Relative, kPositionSlot, Constants.CTRE_TIMEOUT_MS
        )
        mRightMaster.configSelectedFeedbackSensor(
            FeedbackDevice.CTRE_MagEncoder_Relative, kVelocitySlot, Constants.CTRE_TIMEOUT_MS
        )

        // configure gryo feedback
        mRightMaster.configRemoteFeedbackFilter(
            mGyro.getDeviceID(),
            RemoteSensorSource.Pigeon_Yaw,
            1,
            Constants.CTRE_TIMEOUT_MS
        )
        mRightMaster.configSelectedFeedbackCoefficient(
            kWeirdPigeonConversion, 1, Constants.CTRE_TIMEOUT_MS
        )

        // enable and config voltage compensation
        mLeftMaster.enableVoltageCompensation(true)
        mLeftMaster.configVoltageCompSaturation(
            Constants.Drivetrain.VOLTAGE_COMP_SATURATION,
            Constants.CTRE_TIMEOUT_MS
        )
        mLeftMaster.configVoltageMeasurementFilter(
            Constants.Drivetrain.VOLTAGE_COMP_FILTER,
            Constants.CTRE_TIMEOUT_MS
        )

        mRightMaster.enableVoltageCompensation(true)
        mRightMaster.configVoltageCompSaturation(
            Constants.Drivetrain.VOLTAGE_COMP_SATURATION,
            Constants.CTRE_TIMEOUT_MS
        )
        mRightMaster.configVoltageMeasurementFilter(
            Constants.Drivetrain.VOLTAGE_COMP_FILTER,
            Constants.CTRE_TIMEOUT_MS
        )

        // set motion magic cuise velo
        mLeftMaster.configMotionCruiseVelocity(Constants.Drivetrain.MOTION_MAGIC_VELOCITY, Constants.CTRE_TIMEOUT_MS)
        mRightMaster.configMotionCruiseVelocity(Constants.Drivetrain.MOTION_MAGIC_VELOCITY, Constants.CTRE_TIMEOUT_MS)

        // set motion magic acceleration
        mLeftMaster.configMotionAcceleration(Constants.Drivetrain.MOTION_MAGIC_ACCELERATION, Constants.CTRE_TIMEOUT_MS)
        mRightMaster.configMotionAcceleration(Constants.Drivetrain.MOTION_MAGIC_ACCELERATION, Constants.CTRE_TIMEOUT_MS)

        // set sensor positions to 0
        mGyro.setYaw(0.0, Constants.CTRE_TIMEOUT_MS)
        mLeftMaster.setSelectedSensorPosition(kPositionSlot, 0, Constants.CTRE_TIMEOUT_MS)
        mLeftMaster.setSelectedSensorPosition(kVelocitySlot, 0, Constants.CTRE_TIMEOUT_MS)
        mRightMaster.setSelectedSensorPosition(kPositionSlot, 0, Constants.CTRE_TIMEOUT_MS)
        mRightMaster.setSelectedSensorPosition(kVelocitySlot, 0, Constants.CTRE_TIMEOUT_MS)

        // set turn pid orientation
        mRightMaster.configAuxPIDPolarity(true, Constants.CTRE_TIMEOUT_MS)
        mRightMaster.configClosedLoopPeakOutput(kTurnSlot, 1.0, Constants.CTRE_TIMEOUT_MS)

        // configure ramp rate
        mLeftMaster.configClosedloopRamp(Constants.Drivetrain.CLOSED_LOOP_RAMP, Constants.CTRE_TIMEOUT_MS)
        mRightMaster.configClosedloopRamp(Constants.Drivetrain.CLOSED_LOOP_RAMP, Constants.CTRE_TIMEOUT_MS)

        // configure followers
        mLeftSlave1.follow(mLeftMaster)
        mRightSlave2.follow(mLeftMaster)

        mRightSlave1.follow(mRightMaster)
        mRightSlave2.follow(mRightMaster)

        // sensor phases
        mLeftMaster.setSensorPhase(false)
        mRightMaster.setSensorPhase(false)

        // configure inverted
        mLeftMaster.setInverted(true)
        mLeftSlave1.setInverted(InvertType.FollowMaster)
        mLeftSlave2.setInverted(InvertType.FollowMaster)

        mRightMaster.setInverted(false)
        mRightSlave1.setInverted(InvertType.FollowMaster)
        mRightSlave2.setInverted(InvertType.FollowMaster)

        //  configure turn error
        mRightMaster.configAllowableClosedloopError(
            kTurnSlot,
            Constants.Drivetrain.ALLOWABLE_TURN_ERROR,
            Constants.CTRE_TIMEOUT_MS
        )

        // can bus messaging
        mLeftMaster.setStatusFramePeriod(
            StatusFrameEnhanced.Status_2_Feedback0,
            Constants.SENSOR_FEEDBACK_MS,
            Constants.CTRE_TIMEOUT_MS
        )
        mRightMaster.setStatusFramePeriod(
            StatusFrameEnhanced.Status_2_Feedback0,
            Constants.SENSOR_FEEDBACK_MS,
            Constants.CTRE_TIMEOUT_MS
        )

        // velocity measurement
        mLeftMaster.configVelocityMeasurementPeriod(VelocityMeasPeriod.Period_50Ms, Constants.CTRE_TIMEOUT_MS)
        mRightMaster.configVelocityMeasurementPeriod(VelocityMeasPeriod.Period_50Ms, Constants.CTRE_TIMEOUT_MS)

        // load gains
        loadGains()
    }

    private val mLoop = object : Loop {

        override fun onStart(timestamp: Double) {
            synchronized(this) {
            }
        }

        override fun onLoop(timestamp: Double) {
            synchronized(this) {
                mPositionTracker.update(leftDistance, rightDistance, heading.degrees)
            }
        }

        override fun onStop(timestamp: Double) {
            synchronized(this) {
                mPositionTracker.reset()
                stop()
            }
        }
    }

    private var mDriveMode: DriveMode = DriveMode.PERCENT
        set(mode) {
            if (field == mode) return
            when (mode) {
                DriveMode.PERCENT -> configureForPercent()
                DriveMode.TURN -> configureForTurn()
                DriveMode.POSITION -> configureForPosition()
                DriveMode.VELOCITY -> configureForVelocity()
            }
            field = mode
        }

    override val model = DifferentialDrive(
            Constants.Drivetrain.MASS,
            Constants.Drivetrain.MOMENT_OF_INERTIA,
            Constants.Drivetrain.ANGULAR_DRAG,
            Constants.Drivetrain.WHEEL_RADIUS_METERS,
            Constants.Drivetrain.EFFECTIVE_WHEELBASE_RADIUS,
            DCMotorTransmission(
                Constants.Drivetrain.SPEED_PER_VOLT,
                Constants.Drivetrain.TORQUE_PER_VOLT,
                Constants.Drivetrain.FRICTION_VOLTAGE
            ),
            DCMotorTransmission(
                Constants.Drivetrain.SPEED_PER_VOLT,
                Constants.Drivetrain.TORQUE_PER_VOLT,
                Constants.Drivetrain.FRICTION_VOLTAGE
            )
        )

    // hardware functions
    override var braking: Boolean = false
        set(value) {
            if (value == field) return
            val mode = if (value) NeutralMode.Brake else NeutralMode.Coast
            mLeftMaster.setNeutralMode(mode)
            mLeftSlave1.setNeutralMode(mode)
            mLeftSlave2.setNeutralMode(mode)

            mRightMaster.setNeutralMode(mode)
            mRightSlave1.setNeutralMode(mode)
            mRightSlave2.setNeutralMode(mode)
            field = value
        }

    private var mGyroOffset = Rotation2d()

    override var heading: Rotation2d
        @Synchronized get() = Rotation2d.fromDegrees(mGyro.getFusedHeading()).rotateBy(mGyroOffset)
        @Synchronized set(value) {
            println("SET HEADING: ${heading.degrees}")
            mGyroOffset = value.rotateBy(Rotation2d.fromDegrees(mGyro.getFusedHeading()).inverse())
            println("Gyro offset: ${mGyroOffset.degrees}")
        }

    override val angularVelocity: Double
        get() {
            val xyz = doubleArrayOf(0.0, 0.0, 0.0)
            mGyro.getRawGyro(xyz)
            return xyz[1]
        }

    private val mPositionTracker = Position()

    @get:Synchronized @set:Synchronized
    override var position: Vector2
        get() = mPositionTracker.positionVector
        set(value) {
            mPositionTracker.positionVector = value
        }

    override var pose: Pose2d
        get() = Pose2d(position, heading)
        set(value) {
            heading = value.rotation
            position = value.translation
        }

    @get:Synchronized @set:Synchronized
    override var leftDistance: Double
        get() = -Utils.encoderTicksToInches(
            Constants.Drivetrain.TICKS_PER_ROTATION,
            Constants.Drivetrain.WHEEL_CIR,
            mLeftMaster.getSelectedSensorPosition(kPositionSlot)
        )
        set(inches) {
            mLeftMaster.setSelectedSensorPosition(
                Utils.inchesToEncoderTicks(
                    Constants.Drivetrain.TICKS_PER_ROTATION,
                    Constants.Drivetrain.WHEEL_CIR,
                    -inches
                ),
                kPositionSlot,
                Constants.CTRE_TIMEOUT_MS
            )
        }

    @get:Synchronized @set:Synchronized
    override var rightDistance: Double
        get() = Utils.encoderTicksToInches(
            Constants.Drivetrain.TICKS_PER_ROTATION,
            Constants.Drivetrain.WHEEL_CIR,
            mRightMaster.getSelectedSensorPosition(kPositionSlot)
        )
        set(inches) {
            mRightMaster.setSelectedSensorPosition(
                Utils.inchesToEncoderTicks(
                    Constants.Drivetrain.TICKS_PER_ROTATION,
                    Constants.Drivetrain.WHEEL_CIR,
                    inches
                ),
                kPositionSlot,
                Constants.CTRE_TIMEOUT_MS
            )
        }

    override val leftVelocity get() = -Utils.encoderTicksPer100MsToInchesPerSecond(
            Constants.Drivetrain.TICKS_PER_ROTATION,
            Constants.Drivetrain.WHEEL_CIR,
            mLeftMaster.getSelectedSensorVelocity(kVelocitySlot)
        )

    override val rightVelocity get() = Utils.encoderTicksPer100MsToInchesPerSecond(
        Constants.Drivetrain.TICKS_PER_ROTATION,
        Constants.Drivetrain. WHEEL_CIR,
        mRightMaster.getSelectedSensorVelocity(kVelocitySlot)
    )

    // error methods
    override val leftDistanceError: Double
        get() = -Utils.encoderTicksToInches(
            Constants.Drivetrain.TICKS_PER_ROTATION,
            Constants.Drivetrain.WHEEL_CIR,
            mLeftMaster.getClosedLoopError(0)
        )

    override val rightDistanceError: Double
        get() = Utils.encoderTicksToInches(
            Constants.Drivetrain.TICKS_PER_ROTATION,
            Constants.Drivetrain.WHEEL_CIR,
            mRightMaster.getClosedLoopError(0)
        )

    override val leftVelocityError: Double
        get() = -Utils.encoderTicksPer100MsToInchesPerSecond(
            Constants.Drivetrain.TICKS_PER_ROTATION,
            Constants.Drivetrain. WHEEL_CIR,
            mLeftMaster.getClosedLoopError(0)
        )

    override val rightVelocityError: Double
        get() = Utils.encoderTicksPer100MsToInchesPerSecond(
            Constants.Drivetrain.TICKS_PER_ROTATION,
            Constants.Drivetrain. WHEEL_CIR,
            mRightMaster.getClosedLoopError(0)
        )

    override val turnError: Double
        get() = mRightMaster.getClosedLoopError(1).toDouble() // CHECK THIS NOT SURE IF RIGHT

    private fun configureForVelocity() {
        mLeftMaster.selectProfileSlot(kVelocitySlot, 0)
        mRightMaster.selectProfileSlot(kVelocitySlot, 0)
    }

    override fun setVelocity(left: Double, right: Double, leftVoltage: Double, rightVoltage: Double) {
        mDriveMode = DriveMode.VELOCITY
        mLeftMaster.set(
            ControlMode.Velocity,
            -Utils.inchesPerSecondToEncoderTicksPer100Ms(
                Constants.Drivetrain.TICKS_PER_ROTATION,
                Constants.Drivetrain.WHEEL_CIR,
                left
            ),
            DemandType.ArbitraryFeedForward,
            leftVoltage / kVolts
        )
        mRightMaster.set(
            ControlMode.Velocity,
            Utils.inchesPerSecondToEncoderTicksPer100Ms(
                Constants.Drivetrain.TICKS_PER_ROTATION,
                Constants.Drivetrain.WHEEL_CIR,
                right
            ),
            DemandType.ArbitraryFeedForward,
            rightVoltage / kVolts
        )
    }

    private fun configureForPosition() {
        mLeftMaster.selectProfileSlot(kPositionSlot, 0)
        mRightMaster.selectProfileSlot(kPositionSlot, 0)
    }

    override fun setPosition(leftDistance: Double, rightDistance: Double) {
        mDriveMode = DriveMode.POSITION
        // DO I NEED TO DO SOMETHING WITH CURRENT DISTANCE??
        mLeftMaster.set(
            ControlMode.Position,
            // check negative sign
            -Utils.inchesToEncoderTicks(
                Constants.Drivetrain.TICKS_PER_ROTATION,
                Constants.Drivetrain.WHEEL_CIR,
                this.leftDistance + leftDistance
            ).toDouble()
        )
        mRightMaster.set(
            ControlMode.Position,
            Utils.inchesToEncoderTicks(
                Constants.Drivetrain.TICKS_PER_ROTATION,
                Constants.Drivetrain.WHEEL_CIR,
                this.rightDistance + rightDistance
            ).toDouble()
        )
    }

    private fun configureForTurn() {
        mRightMaster.selectProfileSlot(kPositionSlot, 0)
        mRightMaster.selectProfileSlot(kTurnSlot, 1)
        mLeftMaster.follow(mRightMaster, FollowerType.AuxOutput1)
    }

    override fun setTurn(degrees: Double, type: TurnType) {
        mDriveMode = DriveMode.TURN
        @Suppress("MagicNumber")
        mRightMaster.set(
            ControlMode.PercentOutput, 0.0, // MAYBE DO SOMTHING HERE
            DemandType.AuxPID, mRightMaster.getSelectedSensorPosition(1) + (degrees * 10.0)
        )
    }

    private fun configureForPercent() {}

    override fun setPercent(left: Double, right: Double) {
        mDriveMode = DriveMode.PERCENT
        mLeftMaster.set(ControlMode.PercentOutput, left)
        mRightMaster.set(ControlMode.PercentOutput, right)
    }

    @Suppress("LongMethod")
    public fun loadGains() {
        // configure position slot
        mLeftMaster.config_kP(kPositionSlot, Constants.Drivetrain.POSITION_KP, Constants.CTRE_TIMEOUT_MS)
        mLeftMaster.config_kI(kPositionSlot, Constants.Drivetrain.POSITION_KI, Constants.CTRE_TIMEOUT_MS)
        mLeftMaster.config_kD(kPositionSlot, Constants.Drivetrain.POSITION_KD, Constants.CTRE_TIMEOUT_MS)
        mLeftMaster.config_kF(kPositionSlot, Constants.Drivetrain.POSITION_KF, Constants.CTRE_TIMEOUT_MS)
        mLeftMaster.config_IntegralZone(kPositionSlot, Constants.Drivetrain.IZONE, Constants.CTRE_TIMEOUT_MS)

        mRightMaster.config_kP(kPositionSlot, Constants.Drivetrain.POSITION_KP, Constants.CTRE_TIMEOUT_MS)
        mRightMaster.config_kI(kPositionSlot, Constants.Drivetrain.POSITION_KI, Constants.CTRE_TIMEOUT_MS)
        mRightMaster.config_kD(kPositionSlot, Constants.Drivetrain.POSITION_KD, Constants.CTRE_TIMEOUT_MS)
        mRightMaster.config_kF(kPositionSlot, Constants.Drivetrain.POSITION_KF, Constants.CTRE_TIMEOUT_MS)
        mRightMaster.config_IntegralZone(kPositionSlot, Constants.Drivetrain.IZONE, Constants.CTRE_TIMEOUT_MS)

        // configure velocity slot
        mLeftMaster.config_kP(kVelocitySlot, Constants.Drivetrain.VELOCITY_KP, Constants.CTRE_TIMEOUT_MS)
        mLeftMaster.config_kI(kVelocitySlot, Constants.Drivetrain.VELOCITY_KI, Constants.CTRE_TIMEOUT_MS)
        mLeftMaster.config_kD(kVelocitySlot, Constants.Drivetrain.VELOCITY_KD, Constants.CTRE_TIMEOUT_MS)
        mLeftMaster.config_kF(kVelocitySlot, Constants.Drivetrain.VELOCITY_KF, Constants.CTRE_TIMEOUT_MS)
        mLeftMaster.config_IntegralZone(kVelocitySlot, Constants.Drivetrain.IZONE, Constants.CTRE_TIMEOUT_MS)

        mRightMaster.config_kP(kVelocitySlot, Constants.Drivetrain.VELOCITY_KP, Constants.CTRE_TIMEOUT_MS)
        mRightMaster.config_kI(kVelocitySlot, Constants.Drivetrain.VELOCITY_KI, Constants.CTRE_TIMEOUT_MS)
        mRightMaster.config_kD(kVelocitySlot, Constants.Drivetrain.VELOCITY_KD, Constants.CTRE_TIMEOUT_MS)
        mRightMaster.config_kF(kVelocitySlot, Constants.Drivetrain.VELOCITY_KF, Constants.CTRE_TIMEOUT_MS)
        mRightMaster.config_IntegralZone(kVelocitySlot, Constants.Drivetrain.IZONE, Constants.CTRE_TIMEOUT_MS)

        // configure turn slot
        mLeftMaster.config_kP(kTurnSlot, Constants.Drivetrain.TURN_KP, Constants.CTRE_TIMEOUT_MS)
        mLeftMaster.config_kI(kTurnSlot, Constants.Drivetrain.TURN_KI, Constants.CTRE_TIMEOUT_MS)
        mLeftMaster.config_kD(kTurnSlot, Constants.Drivetrain.TURN_KD, Constants.CTRE_TIMEOUT_MS)
        mLeftMaster.config_kF(kTurnSlot, Constants.Drivetrain.TURN_KF, Constants.CTRE_TIMEOUT_MS)
        mLeftMaster.config_IntegralZone(kTurnSlot, Constants.Drivetrain.IZONE, Constants.CTRE_TIMEOUT_MS)

        mRightMaster.config_kP(kTurnSlot, Constants.Drivetrain.TURN_KP, Constants.CTRE_TIMEOUT_MS)
        mRightMaster.config_kI(kTurnSlot, Constants.Drivetrain.TURN_KI, Constants.CTRE_TIMEOUT_MS)
        mRightMaster.config_kD(kTurnSlot, Constants.Drivetrain.TURN_KD, Constants.CTRE_TIMEOUT_MS)
        mRightMaster.config_kF(kTurnSlot, Constants.Drivetrain.TURN_KF, Constants.CTRE_TIMEOUT_MS)
        @Suppress("MagicNumber")
        mRightMaster.config_IntegralZone(kTurnSlot, 400, Constants.CTRE_TIMEOUT_MS)
    }

    override fun registerLoops(looper: ILooper) = looper.register(mLoop)

    override fun zeroSensors() {
        leftDistance = 0.0
        rightDistance = 0.0
    }

    override fun stop() {
        setPercent(0.0)
        mLeftMaster.neutralOutput()
        mRightMaster.neutralOutput()
    }
}
