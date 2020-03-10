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
import edu.wpi.first.wpilibj.Timer;

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
  private int counter = 4;// 8
  private int counter1 = 24;
  // private int spinner=3;
  private boolean matchColor;
  private boolean[] array = new boolean[16];
  private boolean[] array1 = new boolean[16];
  private boolean[] array2= new boolean[16];

  // private ControlPanel b;

  private WPI_TalonSRX controlpanelMotor;

  public ControlPanel() {
    m_colorMatcher.addColorMatch(kBlueTarget);
    m_colorMatcher.addColorMatch(kGreenTarget);
    m_colorMatcher.addColorMatch(kRedTarget);
    m_colorMatcher.addColorMatch(kYellowTarget);

    controlpanelMotor = new WPI_TalonSRX(Constants.CONTROL_PANEL_MOTOR);

    controlpanelMotor.setInverted(true);
    controlpanelMotor.configFactoryDefault();// good job kayla!!!!
  }

  public void moveArm() {

  }

  public WPI_TalonSRX getMotor() {
    return controlpanelMotor;
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
    Color tempColor = ColorMatch.makeColor(0, 0, 0);
    Color tempColor2 = ColorMatch.makeColor(0, 0, 0);
    Color tempColor3 = ColorMatch.makeColor(0, 0, 0);
    //int i=2;
    System.out.println("current detected color " + this.getColor(detectedColor));
     Timer.delay(12.0);
     counter = 0;
    // if (match.color == targetColor && counter > 0) { // detects the colors while
    // spinning 
    // Timer.delay(5); //new
    // counter--;
    // System.out.println("counter =" + counter);
    // } else if (counter == 0) {
    // System.out.println("Counter got to 0");
    // }
    // System.out.println("done with spinning");

    /*if (match.color == kYellowTarget) {
      array[0] = true;
    }
    if (match.color == kBlueTarget) {
      array[1] = true;
    }
    if (match.color == kGreenTarget) {
      array[2] = true;
    }
    if (match.color == kRedTarget) {
      array[3] = true;
    }

    if (match.color == kYellowTarget && array[0] == true && array[1] == true && array[2] == true && array[3] == true ) {
      array[4] = true;
    }

    if (match.color == kBlueTarget && array[0] == true && array[1] == true && array[2] == true && array[3] == true ) {
      array[5] = true;
    }

    if (match.color == kGreenTarget && array[0] == true && array[1] == true && array[2] == true && array[3] == true) {
      array[6] = true;
    }

    if (match.color == kRedTarget && array[0] == true && array[1] == true && array[2] == true && array[3] == true) {
      array[7] = true;
      counter=0;
    }
   


  

    

    /*
     * if(match.color!=targetColor && spinner==3){ tempColor=match.color;
     * if(tempColor!=targetColor && match.color!=targetColor && spinner==3){
     * spinner=2; //2 System.out.println("we have spun 1/8"); counter=1; } }
     * if(match.color!=targetColor && match.color!=tempColor && spinner==2){
     * tempColor2=match.color; if(tempColor2!=targetColor &&
     * match.color!=targetColor && tempColor2!=tempColor&&spinner==2){
     * System.out.println("We have spun 2/8"); spinner=1; counter=0;
     * 
     * }
     * 
     * }
     * 
     * 
     * } // if(tempColor!=targetColor&& match.color!=targetColor){
     * //System.out.println("we have spun 1/8");
     */
  }

  public void matchColor(char FMS) { // for color match G
    String color = String.valueOf(FMS);
    System.out.println("At matchColor method");
    System.out.println("The FMS gave us: " + color);

    if (color.equals("G")) { // want to move wheels to yellow
      if (colorString.equals("yellow")) {
        System.out.println("We have a match!");
        matchColor = true;
      } else {
        getMotor().set(ControlMode.PercentOutput, 0.18);
        System.out.println("Currently searching for a match");
        matchColor = false;
      }
    }

    if (color.equals("R")) {// want to move wheels to blue
      if (colorString.equals("blue")) {
        System.out.println("We have a match!");
        matchColor = true;
      } else {
        getMotor().set(ControlMode.PercentOutput, 0.18);
        System.out.println("Currently searching for a match");
        matchColor = false;
      }
    }

    if (color.equals("Y")) { // want to move wheels to green
      if (colorString.equals("green")) {
        if(match.confidence<97.0){
          getMotor().set(ControlMode.PercentOutput, 0.18);
        }
        System.out.println("We have a match!");
        matchColor = true;
      } else {
        getMotor().set(ControlMode.PercentOutput, 0.18);
        System.out.println("Currently searching for a match");
        matchColor = false;
      }

      

      /*
       * if (color.equals("Y")) { // want to move wheels to green if
       * (colorString.equals("green")) { if(match.confidence<95.0){
       * getMotor().set(ControlMode.PercentOutput, 0.18); }
       * System.out.println("We have a match!"); matchColor=true; } else {
       * getMotor().set(ControlMode.PercentOutput, 0.18);
       * System.out.println("Currently searching for a match"); matchColor=false; }
       * 
       * }
       */

    }

    if (color.equals("B")) { // want to move wheels to red
      if (colorString.equals("red")) {
        matchColor = true;
        System.out.println("We have a match!");
      } else {
        getMotor().set(ControlMode.PercentOutput, 0.18);
        System.out.println("Currently searching for a match");
        matchColor = false;
      }

    }
    System.out.println("done with color match method");
  }

  public int getCounter() {
    return counter;
  }

  public boolean getMatch() {
    return matchColor;
  }

  public void Motor() {

    if (counter1 > 0) {
      controlpanelMotor.set(ControlMode.PercentOutput, 0.18);
      counter1--;
    } else {
      System.out.println("done");
    }
  }

  public int getCounter1() {
    return counter1;
  }

  public void Stop() { // in case of emergencies
    controlpanelMotor.stopMotor();
  }

}
