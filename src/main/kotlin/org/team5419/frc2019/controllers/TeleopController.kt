package org.team5419.frc2019.controllers

import org.team5419.frc2019.input.RemoteDriver
import org.team5419.frc2019.subsystems.Vacuum
import org.team5419.frc2019.subsystems.SubsystemsManager

import org.team5419.fault.Controller

public class TeleopController(
    subsystemsManager: SubsystemsManager,
    remoteDriver: RemoteDriver
) : Controller() {
    private val mSubsystemsManager: SubsystemsManager
    private val mRemoteDriver: RemoteDriver

    init {
        mRemoteDriver = remoteDriver
        mSubsystemsManager = subsystemsManager
    }

    public override fun start() {
    }

    public override fun update() {
        if (mRemoteDriver.getGrab()) {
            mSubsystemsManager.get<Vacuum>().grab()
        }

        if (mRemoteDriver.getRealease()) {
            mSubsystemsManager.get<Vacuum>().release()
        }
    }

    public override fun reset() {
    }
}
