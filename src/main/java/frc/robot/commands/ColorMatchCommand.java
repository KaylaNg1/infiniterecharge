/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ControlPanel;

public class ColorMatchCommand extends CommandBase {
  ControlPanel colormatch;
  String color = "green";// color being sent as parameter must always be in lowercase!

  public ColorMatchCommand(ControlPanel color) {
    this.colormatch = color;
  }

  public void initialize() {
  }

  public void execute() {
    this.colormatch.matchColor(color);
  }

  public void end(boolean interrupted) {

  }

  public boolean isFinished() {
    return false;
  }

}
