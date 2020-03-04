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
  private int counter = 8;
  private int counter1 = 24;
  // private ControlPanel b;

  public WPI_TalonSRX controlpanelMotor;

  public ControlPanel() {
    m_colorMatcher.addColorMatch(kBlueTarget);
    m_colorMatcher.addColorMatch(kGreenTarget);
    m_colorMatcher.addColorMatch(kRedTarget);
    m_colorMatcher.addColorMatch(kYellowTarget);

    controlpanelMotor = new WPI_TalonSRX(Constants.CONTROL_PANEL_MOTOR);

    controlpanelMotor.setInverted(true);
    controlpanelMotor.configFactoryDefault();// good job kayla!!!!
  }

  public void runMotor() {

  }

  public void moveArm() {

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

  public String getColor(Color detectedColor1) {
    ColorMatchResult match1 = m_colorMatcher.matchClosestColor(detectedColor1);
    if (match1.color == kBlueTarget) { // detects colors as the wheel spins
      colorString = "blue";
    } else if (match1.color == kRedTarget) {
      colorString = "red";
    } else if (match1.color == kGreenTarget) {
      colorString = "green";
    } else if (match1.color == kYellowTarget) {
      colorString = "yellow";
    } else {
      colorString = "unknown";
    }

    return colorString;
  }

  public void Spin() { // for color spin
    System.out.println("Beginning spin");
    System.out.println("current detected color " + this.getColor(detectedColor));
    controlpanelMotor.set(ControlMode.PercentOutput, 0.18);
    if (match.color == targetColor && counter > 0) { // detects the colors while spinning
      counter--;
      System.out.println("counter =" + counter);
    } else if (counter == 0) {
      System.out.println("Counter got to 0");
    }
    System.out.println("done with spinning");
  }

  public void matchColor(char FMS) { // for color match
    String color = "" + FMS;
    System.out.println("At matchColor method");
    System.out.println("The FMS gave us: " + color);
    if (color == "G") { // want to move wheels to yellow
      if (colorString == "Y") {
        // controlpanelMotor.stopMotor();
        System.out.println("We have a match!");
      } else {
        System.out.println("Currently searching for a match");
        // controlpanelMotor.set(ControlMode.PercentOutput, -0.1);
      }
      // controlpanelMotor.stopMotor();
    }

    else if (color == "R") {// want to move wheels to blue
      if (colorString == "B") {
        // controlpanelMotor.stopMotor();
        System.out.println("We have a match!");
      } else if (colorString != "B") {
        System.out.println("Currently searching for a match");
        // controlpanelMotor.set(ControlMode.PercentOutput, -0.1);

        // controlpanelMotor.stopMotor();
      }
    }

    else if (color == "Y") { // want to move wheels to green
      if (colorString == "G") {
        // controlpanelMotor.stopMotor();
        System.out.println("We have a match!");
      } else if (colorString != "G") {
        System.out.println("Currently searching for a match");
        // controlpanelMotor.set(ControlMode.PercentOutput, -0.1);
      }
      // controlpanelMotor.stopMotor();

    }

    else if (color == "B") { // want to move wheels to red
      if (colorString == "R") {
        // controlpanelMotor.stopMotor();
        System.out.println("We have a match!");
      } else if (colorString != "R") {
        // controlpanelMotor.set(ControlMode.PercentOutput, -0.1);
        System.out.println("Currently searching for a match");
      }
      // controlpanelMotor.stopMotor();

    }
    System.out.println("done with color match method");
  }

  public int getCounter() {
    return counter;
  }

  public void Motor(){
  
    if(counter1>0){
      controlpanelMotor.set(ControlMode.PercentOutput, 0.18);
      counter1--;
    }
    else{
      System.out.println("done");
    }
  }

  public int getCounter1(){
    return counter1;
  }
}
