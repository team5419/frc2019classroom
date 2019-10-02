package org.team5419.frc2019

@SuppressWarnings("MagicNumber")
public object Constants {
    object DriveTrain {
        public const val LEFT_MASTER_TALON_PORT = 12
        public const val LEFT_SLAVE1_TALON_PORT = 2
        public const val LEFT_SLAVE2_TALON_PORT = 3

        public const val RIGHT_MASTER_TALON_PORT = 6
        public const val RIGHT_SLAVE1_TALON_PORT = 7
        public const val RIGHT_SLAVE2_TALON_PORT = 8

        public const val CHAIN_LIFT_PORT = 5
        public const val CHAIN_BOTTOM_PORT = 4

        public const val GEAR_REDUCTION = 38.0 / 24.0

        public const val TICKS_PER_ROTATION = 4096
    }

    object Input {
        public const val DRIVER_PORT = 0
    }
}
