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
        public const val E_PROPORTIONAL = 0.07 // Proportional Elevator
        public const val E_INTEGRAL = 0.000001 // Integral Elevator
        public const val E_DERIVATIVE = 0.01 // Derivative Elevator
    }
}
