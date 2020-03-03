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
  private Color detectedColor;
  //private ControlPanel b;

  WPI_TalonSRX controlpanelMotor;

  public ControlPanel() {
    m_colorMatcher.addColorMatch(kBlueTarget);
    m_colorMatcher.addColorMatch(kGreenTarget);
    m_colorMatcher.addColorMatch(kRedTarget);
    m_colorMatcher.addColorMatch(kYellowTarget);

    controlpanelMotor = new WPI_TalonSRX(Constants.CONTROL_PANEL_MOTOR);

    controlpanelMotor.setInverted(true);
    controlpanelMotor.configFactoryDefault();//good job kayla!!!!
  }

  public void runMotor(){
    
  }

  public void moveArm(){

  }

  public final Color Target() { // Saves closest color/target color and should be called first and only once
    Color detectedColor1 = m_colorSensor.getColor();
    ColorMatchResult target = m_colorMatcher.matchClosestColor(detectedColor1);
    int i = 0;
    System.out.println("At target");
    while (i < targetCounter) {
      if (target.color == kBlueTarget) { // detects colors as the wheel spins only runs once
        getColorString = "blue";
        targetColor = kBlueTarget;
        i++;

      } else if (target.color == kRedTarget) {
        getColorString = "red";
        targetColor = kRedTarget;
        i++;

      } else if (target.color == kGreenTarget) {
        getColorString = "green";
        targetColor = kGreenTarget;
        i++;

      } else if (target.color == kYellowTarget) {
        getColorString = "yellow";
        targetColor = kYellowTarget;
        i++;

      }
      System.out.println("Target color: " + getColorString);

    }
    return targetColor;
  }

  public void periodic() {
   detectedColor = m_colorSensor.getColor();
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

  public String getColor(Color match) {
    if (match == kBlueTarget) { // detects colors as the wheel spins
      colorString = "blue";
    } else if (match == kRedTarget) {
      colorString = "red";
    } else if (match == kGreenTarget) {
      colorString = "green";
    } else if (match == kYellowTarget) {
      colorString = "yellow";
    } else {
      colorString = "unknown";
    }

    return colorString;
  }

  public void Spin() { // for color spin
    int counter = 8;
    System.out.println("Beginning spin");

    //while (counter > 0) { // should only iterate 8 times
     // Color detectedColor = m_colorSensor.getColor(); //detecting current color under sensor
      //match = m_colorMatcher.matchClosestColor(detectedColor);
      System.out.println("current detected color" + this.getColor(detectedColor));
      // controlpanelMotor.set(ControlMode.PercentOutput, -0.1);
      if (match.color == targetColor) { // detects the colors while spinning
        counter--;
        System.out.println("counter =" + counter);
      }
      if (counter == 0){
        { System.out.println("Counter got to 0");
      }

    }
    System.out.println("done with spinning");
    // controlpanelMotor.stopMotor();
  }

  public void matchColor(String color) { // for color match
    System.out.println("At matchColor method");

    if (color == "green") { // want to move wheels to yellow
      if (colorString == "yellow") {
        // controlpanelMotor.stopMotor();
      } else {
        while (colorString != "yellow") {
          // controlpanelMotor.set(ControlMode.PercentOutput, -0.1);
        }
        // controlpanelMotor.stopMotor();
      }
    }

    else if (color == "red") {// want to move wheels to blue
      if (colorString == "blue") {
        // controlpanelMotor.stopMotor();
      } else {
        while (colorString != "blue") {
          // controlpanelMotor.set(ControlMode.PercentOutput, -0.1);
        }
        // controlpanelMotor.stopMotor();
      }
    }

    else if (color == "yellow") { // want to move wheels to green
      if (colorString == "green") {
        // controlpanelMotor.stopMotor();
      } else {
        while (colorString != "green") {
          // controlpanelMotor.set(ControlMode.PercentOutput, -0.1);
        }
        // controlpanelMotor.stopMotor();
      }
    }

    else if (color == "blue") { // want to move wheels to red
      if (colorString == "red") {
        // controlpanelMotor.stopMotor();
      } else {
        while (colorString != "red") {
          // controlpanelMotor.set(ControlMode.PercentOutput, -0.1);
        }
        // controlpanelMotor.stopMotor();

      }
    }
    System.out.println("done with color match method");
  }
}
