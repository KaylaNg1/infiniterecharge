/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ControlPanel;
import edu.wpi.first.wpilibj.DriverStation;


public class ColorMatchCommand extends CommandBase {

  ControlPanel colormatch;
  String color;
  String gameData;
  char letter;
  

  public ColorMatchCommand(ControlPanel color) {
    this.colormatch = color;
    gameData= DriverStation.getInstance().getGameSpecificMessage();
    if(gameData.length()>0){
      switch (gameData.charAt(0))
      {
        case 'B' :
          break;
        case 'G' :
          break;
        case 'R' :
          break;
        case 'Y' :
          break;
        default :
          break;
      }
    }
      letter=gameData.charAt(0);
  }

  public void initialize() {
    
  }

  public void execute() {
    this.colormatch.matchColor(letter);
  }

  public void interrupted() {

  }

  public boolean isFinished() {
    return colormatch.getMatch();
  }

  public void end(boolean interrupted) {
    colormatch.Stop();

  }


}
