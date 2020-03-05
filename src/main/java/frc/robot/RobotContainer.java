/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import frc.robot.subsystems.ControlPanel;
//import frc.robot.subsystems.DriveTrain;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj.Joystick;
import frc.robot.commands.ColorMatchCommand;
import frc.robot.commands.ColorTargetCommand;
import frc.robot.commands.ColorSpinCommand;
import frc.robot.commands.ControlArm;

/**
 * This class is where the bulk of the robot should be declared. Since
 * Command-based is a "declarative" paradigm, very little robot logic should
 * actually be handled in the {@link Robot} periodic methods (other than the
 * scheduler calls). Instead, the structure of the robot (including subsystems,
 * commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  //public DriveTrain m_drivetrain;
  private ControlPanel m_controlPanel;
  public Joystick driverController;
  private Command m_colormatchCommand;
  private Command m_colortargetCommand;
  private Command m_colorspinCommand;
  private Command m_controlarmCommand;
  public Joystick m_operatorJoystick;

  /**
   * The container for the robot. Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    //this.m_drivetrain = new DriveTrain();
    this.m_controlPanel = new ControlPanel();
    this.m_colormatchCommand = new ColorMatchCommand(m_controlPanel);
    this.m_colortargetCommand = (Command) new ColorTargetCommand(m_controlPanel);
    this.m_colorspinCommand= (Command) new ColorSpinCommand(m_controlPanel);
    this.m_controlarmCommand= (Command) new ControlArm(m_controlPanel);
    this.m_operatorJoystick = new Joystick(Constants.OPERATOR_JOYSTICK);
    this.driverController = new Joystick(Constants.JOYSTICK);

    // Configure the button bindings
    configureButtonBindings();
  }

  /**
   * Use this method to define your button->command mappings. Buttons can be
   * created by instantiating a {@link GenericHID} or one of its subclasses
   * ({@link edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then
   * passing it to a {@link edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
    JoystickButton wheelSpin = new JoystickButton(m_operatorJoystick,3); //spinning method
    JoystickButton targetDetector = new JoystickButton(m_operatorJoystick, 4); //target method
    //JoystickButton colorMatcher= new JoystickButton(m_operatorJoystick, 5);
    //JoystickButton arm= new JoyStickButton(m_operatorJoystick, 6);
    wheelSpin.whenPressed(this.m_colorspinCommand);
    targetDetector.whenPressed(this.m_colortargetCommand);
    //colorMatcher.whenPressed(this.m_colormatchCommand);
    //arm.whenPressed(this.m_controlarmCommand);


  }

  /*public Command getTeleopCommand() { // no clue what to return
    return null;

  }
  */
}
