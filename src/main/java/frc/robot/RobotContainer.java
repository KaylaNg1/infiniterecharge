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
import frc.robot.subsystems.DriveTrain;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj.Joystick;
import frc.robot.commands.ColorMatchCommand;
import frc.robot.commands.ColorSpinCommand;

/**
 * This class is where the bulk of the robot should be declared. Since
 * Command-based is a "declarative" paradigm, very little robot logic should
 * actually be handled in the {@link Robot} periodic methods (other than the
 * scheduler calls). Instead, the structure of the robot (including subsystems,
 * commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  public DriveTrain m_drivetrain;
  private ControlPanel m_controlPanel;
  public Joystick driverController = new Joystick(Constants.OI_DRIVER_CONTOROLLER);
  private ColorMatchCommand m_colormatchCommand;
  private Command m_colorspinCommand;
  public Joystick m_driverController;

  /**
   * The container for the robot. Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    this.m_drivetrain = new DriveTrain();
    this.m_controlPanel = new ControlPanel();
    this.m_colormatchCommand = new ColorMatchCommand(m_controlPanel);
    this.m_colorspinCommand = (Command) new ColorSpinCommand(m_controlPanel);
    this.m_driverController = new Joystick(Constants.JOYSTICK);

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
    // JoystickButton wheelStop= new JoystickButton(m_driverController, 3);
    JoystickButton wheelSpin = new JoystickButton(m_driverController, 4);
    // wheelSpin.whileHeld(this.m_colorspinCommand);

  }

  public Command getTeleopCommand() { // no clue what to return
    return null;

  }
}
