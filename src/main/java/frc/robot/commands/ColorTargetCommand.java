/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import frc.robot.subsystems.ControlPanel;
import edu.wpi.first.wpilibj2.command.CommandBase;

/**
 * Add your docs here.
 */
public class ColorTargetCommand extends CommandBase {
    private ControlPanel colorTarget;

    public ColorTargetCommand(ControlPanel a) {
        colorTarget = a;
    }

    public void initialize() {
        this.colorTarget.Target();
    }

    public void execute() {
    }

    public void end(boolean interrupted) {

    }

    public boolean isFinished() {
        return false;
    }
}
