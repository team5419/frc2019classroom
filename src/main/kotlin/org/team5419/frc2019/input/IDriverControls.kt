package org.team5419.frc2019.input

public interface IDriverControls {

    // vacuum

    public fun getGrab(): Boolean

    public fun getRealease(): Boolean

    // outher

    public fun getState(): String {
        return ""
    }
}
