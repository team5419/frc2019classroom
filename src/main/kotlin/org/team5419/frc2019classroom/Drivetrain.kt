package org.team5419.frc2019classroom

import com.ctre.phoenix.sensors.PigeonIMU
import com.ctre.phoenix.motorcontrol.ControlMode
import com.ctre.phoenix.motorcontrol.NeutralMode
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced
import com.ctre.phoenix.motorcontrol.DemandType
import com.ctre.phoenix.motorcontrol.FeedbackDevice
import com.ctre.phoenix.motorcontrol.SensorTerm
import com.ctre.phoenix.motorcontrol.RemoteSensorSource
import com.ctre.phoenix.motorcontrol.FollowerType
import com.ctre.phoenix.motorcontrol.InvertType
import com.ctre.phoenix.ParamEnum
import com.ctre.phoenix.motorcontrol.StatusFrame

import org.team5419.frc2019classroom.Constants

import org.team5419.fault.subsystems.Subsystem
import org.team5419.fault.hardware.ctre.BerkeliumSPX
import org.team5419.fault.hardware.ctre.BerkeliumSRX
import org.team5419.fault.hardware.Limelight

import org.team5419.fault.math.Position
import org.team5419.fault.math.geometry.Vector2
import org.team5419.fault.math.geometry.Rotation2d
import org.team5419.fault.math.geometry.Pose2d
import org.team5419.fault.math.MathMisc
import org.team5419.fault.util.Utils
import org.team5419.fault.input.DriveSignal

@Suppress("LargeClass", "TooManyFunctions")
public class Drivetrain(
    leftMaster: BerkeliumSPX,
    leftSlave1: BerkeliumSPX,
    leftSlave2: BerkeliumSPX,
    rightMaster: BerkeliumSPX,
    rightSlave1: BerkeliumSPX,
    rightSlave2: BerkeliumSPX,
    gyro: PigeonIMU
) : Subsystem() {

    private enum class DriveMode {
        OPEN_LOOP,
        POSITION,
        VELOCITY,
        TURN
    }

    // hardware
    private val mLeftMaster: BerkeliumSPX
    private val mLeftSlave1: BerkeliumSPX
    private val mLeftSlave2: BerkeliumSPX

    private val mRightMaster: BerkeliumSPX
    private val mRightSlave1: BerkeliumSPX
    private val mRightSlave2: BerkeliumSPX

    private val mGyro: PigeonIMU

    public var brakeMode: Boolean = false
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

    private val mPosition = Position()

    public fun setPosition(vector: Vector2) {
        mPosition.positionVector = vector
    }

    private var mGyroOffset: Rotation2d = Rotation2d.identity
        set(value) { field = value }

    public var heading: Rotation2d
        get() {
            return Rotation2d.fromDegrees(mGyro.getFusedHeading()).rotateBy(mGyroOffset)
            // return Rotation2d.fromDegrees(mGyro.getFusedHeading())
        }
        set(value) {
            println("SET HEADING: ${heading.degrees}")
            mGyroOffset = value.rotateBy(Rotation2d.fromDegrees(mGyro.getFusedHeading()).inverse())
            // mGyro.setFusedHeading(value.degrees, 0)
            println("Gyro offset: ${mGyroOffset.degrees}")
        }

    public val angularVelocity: Double
        get() {
            val xyz = doubleArrayOf(0.0, 0.0, 0.0)
            mGyro.getRawGyro(xyz)
            return xyz[1]
        }

    public val pose: Pose2d
        get() = Pose2d(mPosition.positionVector, heading)

    public var leftDistance: Double
        get() {
            return -Utils.encoderTicksToInches(
                Constants.Drivetrain.ENCODER_TICKS_PER_ROTATION,
                Constants.Drivetrain.WHEEL_CIR,
                mLeftMaster.sensorCollection.quadraturePosition
            )
        }
        set(inches) {
            mLeftMaster.sensorCollection.setQuadraturePosition(
                Utils.inchesToEncoderTicks(Constants.Drivetrain.ENCODER_TICKS_PER_ROTATION,
                Constants.Drivetrain.WHEEL_CIR,
                inches), 0)
        }

    public var rightDistance: Double
        get() {
            return Utils.encoderTicksToInches(
                Constants.Drivetrain.ENCODER_TICKS_PER_ROTATION,
                Constants.Drivetrain.WHEEL_CIR,
                mRightMaster.sensorCollection.quadraturePosition
            )
        }
        set(inches) {
            mRightMaster.getSensorCollection().setQuadraturePosition(
                Utils.inchesToEncoderTicks(
                    Constants.Drivetrain.ENCODER_TICKS_PER_ROTATION,
                    Constants.Drivetrain.WHEEL_CIR,
                    inches
                ), 0)
        }

    public val leftVelocity: Double
        get() = -Utils.encoderTicksPer100MsToInchesPerSecond(
            Constants.Drivetrain.ENCODER_TICKS_PER_ROTATION,
            Constants.Drivetrain.WHEEL_CIR,
            mLeftMaster.sensorCollection.quadratureVelocity
        )

    public val rightVelocity: Double
        get() = Utils.encoderTicksPer100MsToInchesPerSecond(
            Constants.Drivetrain.ENCODER_TICKS_PER_ROTATION,
            Constants.Drivetrain.WHEEL_CIR,
            mRightMaster.sensorCollection.quadratureVelocity
        )

    public val averageVelocity: Double
        get() = (leftVelocity + rightVelocity) / 2.0

    public val positionError: Double
        get() = Utils.encoderTicksToInches(
            Constants.Drivetrain.ENCODER_TICKS_PER_ROTATION,
            Constants.Drivetrain.WHEEL_CIR,
            mRightMaster.getClosedLoopError(0)
        )

    public val leftVelocityError: Double
        get() = Utils.encoderTicksPer100MsToInchesPerSecond(
            Constants.Drivetrain.ENCODER_TICKS_PER_ROTATION,
            Constants.Drivetrain.WHEEL_CIR,
            mLeftMaster.getClosedLoopError(0)
        )

    public val rightVelocityError: Double
        get() = Utils.encoderTicksPer100MsToInchesPerSecond(
            Constants.Drivetrain.ENCODER_TICKS_PER_ROTATION,
            Constants.Drivetrain.WHEEL_CIR,
            mRightMaster.getClosedLoopError(0)
        )

    public val averageVelocityError: Double
        get() = (leftVelocityError + rightVelocityError) / 2.0

    public val anglePositionError: Double
        get() = Utils.talonAngleToDegrees(
            Constants.Drivetrain.TURN_UNITS_PER_ROTATION,
            mRightMaster.getClosedLoopError(1)
        )

    public val turnError: Double
        get() = Utils.talonAngleToDegrees(
            Constants.Drivetrain.TURN_UNITS_PER_ROTATION,
            mRightMaster.getClosedLoopError(0)
        )

    public val turnFixedError: Double
        get() = Utils.encoderTicksToInches(
            Constants.Drivetrain.ENCODER_TICKS_PER_ROTATION,
            Constants.Drivetrain.WHEEL_CIR,
            mRightMaster.getClosedLoopError(1)
        )

    init {
        // initialze hardware
        mLeftMaster = leftMaster.apply {
            setInverted(false)
            setSensorPhase(true)
            setStatusFramePeriod(
                StatusFrameEnhanced.Status_3_Quadrature,
                Constants.TALON_UPDATE_PERIOD_MS,
                0
            )
        }
        mLeftSlave1 = leftSlave1.apply {
            follow(mLeftMaster)
            setInverted(InvertType.FollowMaster)
        }
        mLeftSlave2 = leftSlave2.apply {
            follow(mLeftMaster)
            setInverted(InvertType.FollowMaster)
        }

        mRightMaster = rightMaster.apply {
            setInverted(true)
            setSensorPhase(true)
            setStatusFramePeriod(
                StatusFrameEnhanced.Status_3_Quadrature,
                Constants.TALON_UPDATE_PERIOD_MS,
                0
            )
        }
        mRightSlave1 = rightSlave1.apply {
            follow(mRightMaster)
            setInverted(InvertType.FollowMaster)
        }
        mRightSlave2 = rightSlave2.apply {
            follow(mRightMaster)
            setInverted(InvertType.FollowMaster)
        }

        mGyro = gyro
    }


    public fun setPercent(signal: DriveSignal) {
        setPercent(signal.left, signal.right, signal.brakeMode)
    }

    public fun setPercent(left: Double, right: Double, brakeMode: Boolean = false) {
        // mDriveMode = DriveMode.OPEN_LOOP
        mLeftMaster.set(ControlMode.PercentOutput, left)
        mRightMaster.set(ControlMode.PercentOutput, right)
        this.brakeMode = brakeMode
    }

    public fun setPosition(distance: Double) {
        // mDriveMode = DriveMode.POSITION
        val absDistance = Utils.inchesToEncoderTicks(
            Constants.Drivetrain.ENCODER_TICKS_PER_ROTATION,
            Constants.Drivetrain.WHEEL_CIR,
            ((leftDistance + rightDistance) / 2.0) + distance
        )
        val angleTarget = mRightMaster.getSelectedSensorPosition(1)
        mRightMaster.set(ControlMode.MotionMagic, absDistance.toDouble(), DemandType.AuxPID, angleTarget.toDouble())
    }

    public fun setTurn(angle: Double) {
        // mDriveMode = DriveMode.TURN
        val fixedDistance = Utils.inchesToEncoderTicks(
            Constants.Drivetrain.ENCODER_TICKS_PER_ROTATION,
            Constants.Drivetrain.WHEEL_CIR,
            (leftDistance + rightDistance) / 2.0
        )
        val angleTarget = 0.0 // mRightMaster.getSelectedSensorPosition(1) +
            // Utils.degreesToTalonAngle(Constants.Drivetrain.TURN_UNITS_PER_ROTATION, angle - mGyroOffset.degrees)
        mRightMaster.set(ControlMode.Position, angleTarget.toDouble(), DemandType.AuxPID, fixedDistance.toDouble())
    }

    private fun setVelocity(leftSpeed: Double, rightSpeed: Double) {
        // mDriveMode = DriveMode.VELOCITY
        val left = MathMisc.limit(leftSpeed, Constants.Drivetrain.MAX_VELOCITY)
        val right = MathMisc.limit(rightSpeed, Constants.Drivetrain.MAX_VELOCITY)
        mLeftMaster.set(ControlMode.Velocity,
            Utils.inchesPerSecondToEncoderTicksPer100Ms(
                Constants.Drivetrain.ENCODER_TICKS_PER_ROTATION,
                Constants.Drivetrain.WHEEL_CIR,
                left)
            )
        mRightMaster.set(ControlMode.Velocity,
            Utils.inchesPerSecondToEncoderTicksPer100Ms(
                Constants.Drivetrain.ENCODER_TICKS_PER_ROTATION,
                Constants.Drivetrain.WHEEL_CIR,
                right
            )
        )
    }

    public override fun update() {
        mPosition.update(leftDistance, rightDistance, heading.degrees)
        println("turn error: $turnError")
        println("fused: ${mGyro.getFusedHeading()}")
        println("selected position ${mRightMaster.getSelectedSensorPosition(1)}")
    }

    public override fun stop() {
        leftDistance = 0.0
        rightDistance = 0.0
        mPosition.reset()
        brakeMode = false
    }

    public override fun reset() {
        setPercent(0.0, 0.0)
        stop()
        mLeftMaster.neutralOutput()
        mRightMaster.neutralOutput()
        brakeMode = false
    }

    companion object {
        private const val kPrimaryPIDSlot = 0
        private const val kSecondaryPIDSlot = 1
    }
}
