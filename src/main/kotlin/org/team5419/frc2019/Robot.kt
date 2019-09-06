package org.team5419.frc2019

import edu.wpi.first.wpilibj.TimedRobot
import org.team5419.fault.hardware.LazyTalonSRX
import org.team5419.fault.hardware.LazyVictorSPX
import org.team5419.frc2019.subsystems.DriveTrain
class Robot : TimedRobot() {
    //Hardware
    private val mLeftMaster: LazyTalonSRX
    private val mLeftSlave1: LazyVictorSPX
    private val mLeftSlave2: LazyVictorSPX


    private val mRightMaster: LazyTalonSRX
    private val mRightSlave1: LazyVictorSPX
    private val mRightSlave2: LazyVictorSPX
    //subsystems
    private val mDriveTrain: DriveTrain
    init{
        mLeftMaster = LazyTalonSRX(6)
        mLeftSlave1 = LazyVictorSPX(7)
        mLeftSlave2 = LazyVictorSPX(8)

        mRightMaster = LazyTalonSRX(12) //8
        mRightSlave1 = LazyVictorSPX(2) //34
        mRightSlave2 = LazyVictorSPX(34) //34

        mDriveTrain = DriveTrain(
            mLeftMaster,
            mLeftSlave1,
            mLeftSlave2,

            mRightMaster,
            mRightSlave1,
            mRightSlave2



        )
    }



    override fun robotInit() { //constructor
        println("Hello World from Kotlin!")
    }
    override fun robotPeriodic() { //loop

    }

}