package org.team5499.frc2018.classroom

object Constants {
    object Talons {
        /**
        * these are the port numbers of the talons on the robot
        * the slave talons will be set up to follow what the master talons do
        */
        const val LEFT_MASTER_PORT: Int = 2
        const val LEFT_SLAVE_PORT: Int = 1
        const val RIGHT_MASTER_PORT: Int = 8
        const val RIGHT_SLAVE_PORT: Int = 9

        /**
        * this is the rate at which the talons send the current encoder reading to the RoboRIO
        * we set it to 5 milliseconds, because that is the amount of time between each loop of the periodic functions
        */
        const val TALON_UPDATE_PERIOD_MS: Int = 5
    }

    object Gyro {
        const val GYRO_PORT_NUMBER: Int = 10
        const val TURN_UNITS_PER_ROTATION: Int = 3600
        const val PIGEON_UNITS_PER_ROTATION: Int = 8192
    }

    object Input {
        const val DRIVER_PORT: Int = 1
        const val CODRIVER_PORT: Int = 2
    }

    const val ENCODER_TICKS_PER_ROTATION = 1024 // the number of encoder ticks per rotation of the wheels
    const val WHEEL_DIAMETER = 6.0 // inches
    const val WHEEL_CIRCUMFERENCE = WHEEL_DIAMETER * Math.PI // inches
    const val UPDATE_PERIOD = 0.005 // the periodic functions will run once every 0.005 seconds
}
