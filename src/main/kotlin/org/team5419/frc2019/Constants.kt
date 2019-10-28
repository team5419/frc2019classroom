package org.team5499.frc2019

@SuppressWarnings("MagicNumber")
public object Constants {

    public const val ROBOT_UPDATE_PERIOD = 0.02 // maybe change back to 0.005
    public const val TALON_UPDATE_PERIOD_MS = 10
    public const val TALON_PIDF_UPDATE_PERIOD_MS = 1

    public const val EPSILON = 1E-5

    object Drivetrain {
        // units
        public const val ENCODER_TICKS_PER_ROTATION = 4096
        public const val TURN_UNITS_PER_ROTATION = 3600 // for gyro
        public const val PIGEON_UNITS_PER_ROTATION = 8192

        // dimensions
        public const val WHEEL_BASE = 27.0 // inches
        public const val WHEEL_DIAMETER = 6.0 // inches
        public const val WHEEL_RADIUS = WHEEL_DIAMETER / 2.0
        public const val WHEEL_CIR = WHEEL_DIAMETER * Math.PI

        // pid thresholds
        public var MAX_VELOCITY  = 100.0 // inches per second
        public var MAX_ACCELERATION = 50.0 // inches per second^2
        public var ACCEPTABLE_VELOCITY_THRESHOLD = 3.0 // inches / s
        public var ACCEPTABLE_TURN_ERROR = 3.0 // degrees (?)
        public var ACCEPTABLE_DISTANCE_ERROR = 2.0 // inches

        // pid constants
        public var VEL_KP = 1.3
        public var VEL_KI = 0.0
        public var VEL_KD = 0.6
        public var VEL_KF = 0.6
        public var VEL_IZONE = 10
        public var VEL_MAX_OUTPUT = 1.0

        public var POS_KP = 0.8
        public var POS_KI = 0.0
        public var POS_KD = 0.2
        public var POS_KF = 0.0
        public var POS_IZONE = 10
        public var POS_MAX_OUTPUT = 0.5

        public var ANGLE_KP = 2.0
        public var ANGLE_KI = 0.0
        public var ANGLE_KD = 0.0
        public var ANGLE_KF = 0.0
        public var ANGLE_IZONE = 10
        public var ANGLE_MAX_OUTPUT = 1.0

        public var TURN_KP = 0.1
        public var TURN_KI = 0.0
        public var TURN_KD = 0.0
        public var TURN_KF = 0.0
        public var TURN_IZONE = 10
        public var TURN_MAX_OUTPUT = 1.0

        public var FIXED_KP = 0.0
        public var FIXED_KI = 0.0
        public var FIXED_KD = 0.0
        public var FIXED_KF = 0.0
        public var FIXED_IZONE = 10
        public var FIXED_MAX_OUTPUT = 0.5

        public var INVERT_FIXED_AUX_PIDF = true
        public var INVERT_ANGLE_AUX_PIDF = true
        public var INVERT_TURN_AUX_PIDF = false
    }

    object Auto {
        fun initProps() {
            println("init Auto")
        }
        public var AUTO_DELAY = 0.0 // seconds, set this from dashboard
        public var LOOKAHEAD_DISTANCE = 18.0
    }
}
