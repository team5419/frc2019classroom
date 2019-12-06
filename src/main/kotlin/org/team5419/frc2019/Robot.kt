package org.team5419.frc2019

import edu.wpi.first.wpilibj.TimedRobot
import org.team5419.fault.hardware.LazyTalonSRX
import org.team5419.fault.hardware.LazyVictorSPX
import edu.wpi.first.wpilibj.XboxController
import edu.wpi.first.wpilibj.GenericHID.Hand
import com.ctre.phoenix.motorcontrol.ControlMode
import com.ctre.phoenix.motorcontrol.FeedbackDevice
import edu.wpi.first.wpilibj.Timer

@SuppressWarnings("MagicNumber", "LongMethod")
class Robot : TimedRobot() {
    // Hardware
    private val mLeftMaster: LazyTalonSRX
    private val mLeftSlave1: LazyVictorSPX
    private val mLeftSlave2: LazyVictorSPX

    private val xbox: XboxController
    private val mRightMaster: LazyTalonSRX
    private val mRightSlave1: LazyVictorSPX
    private val mRightSlave2: LazyVictorSPX

    private val mElvMaster: LazyTalonSRX
    private val mElvSlave: LazyTalonSRX
    private val mRollingwheels: LazyTalonSRX
    private var prevError: Double = 0.0
    private var totalError: Double = 0.0
    private var error = 0.0
    private var percentageOutPut = 0.0
    public var mTimer = Timer()
    public var timeploop: Double = 1.0
    public var targetnum: Double = -20000.0;
    // subsystems

    init {
        mLeftMaster = LazyTalonSRX(12)
        mLeftSlave1 = LazyVictorSPX(2)
        mLeftSlave2 = LazyVictorSPX(3)

        mRightMaster = LazyTalonSRX(6) // 8
        mRightSlave1 = LazyVictorSPX(7) // 34
        mRightSlave2 = LazyVictorSPX(8) // 34
        xbox = XboxController(0)

        mElvMaster = LazyTalonSRX(4)
        mElvSlave = LazyTalonSRX(5)
        mRollingwheels = LazyTalonSRX(9)
        // feedback
        mElvMaster.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative)
    }

    override fun robotInit() { // constructor
        println("Hello World from Kotlin!")
        mTimer.start()
    }
    override fun robotPeriodic() { // loop
    }

    fun pidOut() {
        // p
        var elvPos = mElvMaster.getSelectedSensorPosition() // gets position
        var targetpos = targetnum // (__/ 4096) * 3.14159 * 1.23// 100 //target pos

        error = elvPos - targetpos // diffrence

        percentageOutPut = error * Constants.p // + dr
        println("after P: " +percentageOutPut);
        // //D
        var dr = (error - prevError) / timeploop // * Constants.d // =0
        var vel = dr * Constants.d
        percentageOutPut = percentageOutPut + vel
        println("after D: "+percentageOutPut);
        // // I
        percentageOutPut += totalError * Constants.i
        if (percentageOutPut >= 0.8) { // limit "makes sure it doesnt go to fast"
            percentageOutPut = 0.8
        }
        //println((error / 4096)* 3.14159 * 1.23)
        println(percentageOutPut);
        elvmovement(percentageOutPut);
    }
    fun elvmovement(percentageOutPut: Double) {
        mElvMaster.set(ControlMode.PercentOutput, percentageOutPut) // -xbox.getY(Hand.kRight)
        mElvSlave.set(ControlMode.PercentOutput, percentageOutPut) // (-xbox.getY(Hand.kRight))
    }
    fun endresult(error: Double) { // end process of loop
        totalError += error
        prevError = error
    }
    fun bodymovement() {
        mLeftMaster.set(ControlMode.PercentOutput, (xbox.getY(Hand.kLeft) + xbox.getX(Hand.kLeft)))
        mLeftSlave1.set(ControlMode.PercentOutput, (xbox.getY(Hand.kLeft) + xbox.getX(Hand.kLeft)))
        mLeftSlave2.set(ControlMode.PercentOutput, (xbox.getY(Hand.kLeft) + xbox.getX(Hand.kLeft)))
        mRightMaster.set(ControlMode.PercentOutput, (-xbox.getY(Hand.kLeft) + xbox.getX(Hand.kLeft)))
        mRightSlave1.set(ControlMode.PercentOutput, (-xbox.getY(Hand.kLeft) + xbox.getX(Hand.kLeft)))
        mRightSlave2.set(ControlMode.PercentOutput, (-xbox.getY(Hand.kLeft) + xbox.getX(Hand.kLeft)))
    }

    override fun teleopPeriodic() {

        timeploop = mTimer.get()
        mTimer.reset()

        pidOut()

        bodymovement()
        targetnum = targetnum - ((xbox.getY(Hand.kRight))*100)

        mRollingwheels.set(ControlMode.PercentOutput, (xbox.getX(Hand.kRight)))
        

        endresult(error)
    }
}
