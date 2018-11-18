package frc.team5499.frc2018.classroom.subsystems

import com.ctre.phoenix.motorcontrol.ControlMode
import com.ctre.phoenix.motorcontrol.NeutralMode
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced
import com.ctre.phoenix.motorcontrol.can.TalonSRX
import com.ctre.phoenix.sensors.PigeonIMU

import org.team5499.frc2018.classroom.Constants
import org.team5499.frc2018.classroom.utils.Utils

object Drivetrain : Subsystem() {

    // HARDWARE INIT
    private val mLeftMaster = TalonSRX(Constants.Talons.LEFT_MASTER_PORT).apply {
        setInverted(false)
        setSensorPhase(false)
        setStatusFramePeriod(StatusFrameEnhanced.Status_3_Quadrature, Constants.Talons.TALON_UPDATE_PERIOD_MS, 0)
    }

    private val mLeftSlave = TalonSRX(Constants.Talons.LEFT_SLAVE_PORT).apply {
        setInverted(false)
        follow(mLeftMaster)
    }

    private val mRightMaster = TalonSRX(Constants.Talons.RIGHT_MASTER_PORT).apply {
        setInverted(true)
        setSensorPhase(false)
        setStatusFramePeriod(StatusFrameEnhanced.Status_3_Quadrature, Constants.Talons.TALON_UPDATE_PERIOD_MS, 0)
    }

    private val mRightSlave = TalonSRX(Constants.Talons.RIGHT_SLAVE_PORT).apply {
        setInverted(true)
        follow(mRightMaster)
    }

    private val mGyro = PigeonIMU(Constants.Gyro.GYRO_PORT_NUMBER)

    var isBrakeMode = false
        set(value) {
            if (value == field) return
            val mode = if (value) NeutralMode.Brake else NeutralMode.Coast
            mLeftMaster.setNeutralMode(mode)
            mLeftSlave.setNeutralMode(mode)
            mRightMaster.setNeutralMode(mode)
            mRightSlave.setNeutralMode(mode)
            field = value
        }
        get() = field

    // hardware functions

    var gyroAngle: Double
        get() {
            var ypr = doubleArrayOf(0.0, 0.0, 0.0)
            mGyro.getYawPitchRoll(ypr)
            return ypr[0]
        }
        set(value) {
            mGyro.setYaw(value, 0)
        }

    val gyroAngularVelocity: Double
        get() {
            var xyz = doubleArrayOf(0.0, 0.0, 0.0)
            mGyro.getRawGyro(xyz)
            return xyz[1]
        }

    fun zeroGyro() {
        gyroAngle = 0.0
    }

    var leftDistance: Double
        get() {
            return Utils.encoderTicksToInches(mLeftMaster.sensorCollection.quadraturePosition)
        }
        set(inches) {
            mLeftMaster.sensorCollection.setQuadraturePosition(Utils.inchesToEncoderTicks(inches), 0)
        }

    var rightDistance: Double
        get() {
            return Utils.encoderTicksToInches(mRightMaster.sensorCollection.quadraturePosition)
        }
        set(inches) {
            mRightMaster.sensorCollection.setQuadraturePosition(Utils.inchesToEncoderTicks(inches), 0)
        }

    val leftVelocity: Double
        get() = Utils.encoderTicksPer100MsToInchesPerSecond(mLeftMaster.sensorCollection.quadratureVelocity)

    val rightVelocity: Double
        get() = Utils.encoderTicksPer100MsToInchesPerSecond(mRightMaster.sensorCollection.quadratureVelocity)

    val leftVelocityError: Double
        get() = Utils.encoderTicksPer100MsToInchesPerSecond(mLeftMaster.getClosedLoopError(0))

    val rightVelocityError: Double
        get() = Utils.encoderTicksPer100MsToInchesPerSecond(mRightMaster.getClosedLoopError(0))

    val averageVelocityError: Double
        get() = (leftVelocityError + rightVelocityError) / 2.0

    val positionError: Double
        get() = Utils.encoderTicksToInches(mRightMaster.getClosedLoopError(0))

    val anglePositionError: Double
        get() = Utils.talonAngleToDegrees(mRightMaster.getClosedLoopError(1))

    val turnError: Double
        get() = Utils.talonAngleToDegrees(mRightMaster.getClosedLoopError(0))

    val turnFixedError: Double
        get() = Utils.encoderTicksToInches(mRightMaster.getClosedLoopError(1))

    // setup funcs
    private fun configForPercent() {
        mLeftMaster.apply {
            // follow(null)
            configNominalOutputForward(0.0, 0)
            configNominalOutputReverse(0.0, 0)
            configPeakOutputForward(+1.0, 0)
            configPeakOutputReverse(-1.0, 0)
            inverted = false
            setSensorPhase(false)
        }

        mLeftSlave.apply {
            mLeftSlave.inverted = false
        }

        mRightMaster.apply {
            configNominalOutputForward(0.0, 0)
            configNominalOutputReverse(0.0, 0)
            configPeakOutputForward(+1.0, 0)
            configPeakOutputReverse(-1.0, 0)
            inverted = true
            setSensorPhase(false)
        }

        mRightSlave.apply {
            inverted = true
        }
    }

    // drive funcs
    fun setPercent(left: Double, right: Double) {
        mLeftMaster.set(ControlMode.PercentOutput, left)
        mRightMaster.set(ControlMode.PercentOutput, right)
    }

    override fun update() {
    }

    override fun reset() {
        zeroGyro()
        leftDistance = 0.0
        rightDistance = 0.0
        isBrakeMode = false
    }

    override fun stop() {
        setPercent(0.0, 0.0)
        mLeftMaster.neutralOutput()
        mRightMaster.neutralOutput()
        isBrakeMode = false
    }
}
