package org.team5419.frc2019

public object Constants {

    object RobotValues {
        public const val SPROCKET_DIAMETER = 1.23 // In Inches
        public const val TICKS_PER_ROTATION = 4096.0
        public const val GEAR_REDUCTION = 38.0 / 24.0
    }

    object Math {
        public const val PI = 3.141592
    }

    object PID {
        public const val E_KP = 0.14 // Proportional Elevator
        public const val E_KI = 0.0 // Integral Elevator
        public const val E_KD = 0.0 // Derivative Elevator
        public const val E_KF = 0.0 // [insert info here] Elevator

        public const val L_KP = 0.14 // Proportional Left Slave
        public const val L_KI = 0.0 // Integral Left Slave
        public const val L_KD = 0.0 // Derivative Left Slave
        public const val L_KF = 0.0 // [insert info here] Left Slave

        public const val R_KP = 0.14 // Proportional Right Slave
        public const val R_KI = 0.0 // Integral Right Slave
        public const val R_KD = 0.0 // Derivative Right Slave
        public const val R_KF = 0.0 // [insert info here] Right Slave
    }
}
