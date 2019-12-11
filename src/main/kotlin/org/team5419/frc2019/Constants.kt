package org.team5419.frc2019

public object Constants {

    object RobotValues {
        public const val SPROCKET_DIAMETER = 1.23 // In Inches
        public const val TICKS_PER_ROTATION = 4096.0
        public const val GEAR_REDUCTION = 38.0 / 24.0
    }

    object Ports {
        // Left Motors
        public const val LEFT_MASTER = 12
        public const val LEFT_SLAVE_1 = 2
        public const val LEFT_SLAVE_2 = 3

        // Right Motors
        public const val RIGHT_MASTER = 6
        public const val RIGHT_SLAVE_1 = 7
        public const val RIGHT_SLAVE_2 = 8

        // Elevator Motors
        public const val CHAIN_LIFT = 5
        public const val CHAIN_BOTTOM = 4
    }

    object Math {
        public const val PI = 3.141592
    }

    object PID {
        public const val E_KP = 0.14 // Proportional Elevator
        public const val E_KI = 0.0 // Integral Elevator
        public const val E_KD = 0.0 // Derivative Elevator
        public const val E_KF = 0.0 // [insert info here] Elevator

        public const val DT_KP = 0.14 // Proportional DT
        public const val DT_KI = 0.0 // Integral DT
        public const val DT_KD = 0.0 // Derivative DT
        public const val DT_KF = 0.0 // [insert info here] DT
    }
}
