/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.util.Color;
import com.revrobotics.ColorSensorV3;
import com.revrobotics.ColorMatch;
import com.revrobotics.ColorMatchResult;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

/**
 * Add your docs here.
 */
public class ControlPanel extends SubsystemBase {
  private final I2C.Port i2cPort = I2C.Port.kOnboard;
  private final ColorSensorV3 m_colorSensor = new ColorSensorV3(i2cPort);
  private final ColorMatch m_colorMatcher = new ColorMatch();
  private final Color kBlueTarget = ColorMatch.makeColor(0.143, 0.427, 0.429);
  private final Color kGreenTarget = ColorMatch.makeColor(0.197, 0.561, 0.240);
  private final Color kRedTarget = ColorMatch.makeColor(0.561, 0.232, 0.114);
  private final Color kYellowTarget = ColorMatch.makeColor(0.361, 0.524, 0.113);

  private String getColorString;
  private Color targetColor = ColorMatch.makeColor(0, 0, 0);
  private String colorString;
  private ColorMatchResult match;
  private int targetCounter = 1;

  WPI_TalonSRX controlpanelMotor;

  public ControlPanel() {
    m_colorMatcher.addColorMatch(kBlueTarget);
    m_colorMatcher.addColorMatch(kGreenTarget);
    m_colorMatcher.addColorMatch(kRedTarget);
    m_colorMatcher.addColorMatch(kYellowTarget);

    controlpanelMotor = new WPI_TalonSRX(Constants.CONTROL_PANEL_MOTOR);

    controlpanelMotor.setInverted(true);
    controlpanelMotor.configFactoryDefault();
  }

  public void Target() { // Saves closest color/target color and should be called first and only once
    Color detectedColor1 = m_colorSensor.getColor();
    ColorMatchResult target = m_colorMatcher.matchClosestColor(detectedColor1);
    int i = 0;
    while (i < targetCounter) {
      if (target.color == kBlueTarget) { // detects colors as the wheel spins only runs once
        getColorString = "blue";
        targetColor = kBlueTarget;
        i++;
        break;
      } else if (target.color == kRedTarget) {
        getColorString = "red";
        targetColor = kRedTarget;
        i++;
        break;

      } else if (target.color == kGreenTarget) {
        getColorString = "green";
        targetColor = kGreenTarget;
        i++;
        break;

      } else if (target.color == kYellowTarget) {
        getColorString = "yellow";
        targetColor = kYellowTarget;
        i++;
        break;

      }
      System.out.println(getColorString);
    }

  }

  public void periodic() {
    Color detectedColor = m_colorSensor.getColor();
    match = m_colorMatcher.matchClosestColor(detectedColor);

    if (match.color == kBlueTarget) { // detects colors as the wheel spins
      colorString = "blue";
    } else if (match.color == kRedTarget) {
      colorString = "red";
    } else if (match.color == kGreenTarget) {
      colorString = "green";
    } else if (match.color == kYellowTarget) {
      colorString = "yellow";
    } else {
      colorString = "unknown";
    }

    SmartDashboard.putNumber("red", detectedColor.red);
    SmartDashboard.putNumber("green", detectedColor.green);
    SmartDashboard.putNumber("blue", detectedColor.blue);
    SmartDashboard.putNumber("Confidence", match.confidence);
    SmartDashboard.putString("Detected Color", colorString);

    int proximity = m_colorSensor.getProximity();
    SmartDashboard.putNumber("Proximity", proximity);

  }

  public void Spin() { // for color spin
    int counter = 8;
    while (counter > 0) {
      controlpanelMotor.set(ControlMode.PercentOutput, -0.1);
      periodic();
      if (match.color == targetColor) { // detects the colors while spinning
        counter--;
      }
    }
    controlpanelMotor.stopMotor();
  }

  public void matchColor(String color) { // for color match
    if (color == "green") { // want to move wheels to yellow
      if (colorString == "yellow") {
        controlpanelMotor.stopMotor();
      } else {
        while (colorString != "yellow") {
          controlpanelMotor.set(ControlMode.PercentOutput, -0.1);
          periodic();
        }
        controlpanelMotor.stopMotor();
      }
    }

    if (color == "red") {// want to move wheels to blue
      if (colorString == "blue") {
        controlpanelMotor.stopMotor();
      } else {
        while (colorString != "blue") {
          controlpanelMotor.set(ControlMode.PercentOutput, -0.1);
          periodic();
        }
        controlpanelMotor.stopMotor();
      }
    }

    if (color == "yellow") { // want to move wheels to green
      if (colorString == "green") {
        controlpanelMotor.stopMotor();
      } else {
        while (colorString != "green") {
          controlpanelMotor.set(ControlMode.PercentOutput, -0.1);
          periodic();
        }
        controlpanelMotor.stopMotor();
      }
    }

    if (color == "blue") { // want to move wheels to red
      if (colorString == "red") {
        controlpanelMotor.stopMotor();
      } else {
        while (colorString != "red") {
          controlpanelMotor.set(ControlMode.PercentOutput, -0.1);
          periodic();
        }
        controlpanelMotor.stopMotor();

      }
    }
  }
}
