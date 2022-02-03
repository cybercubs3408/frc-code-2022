// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.PWMSparkMax;

/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the manifest file in the resource
 * directory.
 * 
 * Calls all of the constructor classes with appropriate input values
 */
public class Robot extends TimedRobot {
  
  Intake intake = new Intake(intakeMotorID);
  Hopper hopper = new Hopper(topHopperID, bottomHopperID);
  Drivetrain drivetrain = new Drivetrain(frontLeftID, frontRightID, backLeftID, backRightID);
  Lift lift = new Lift(inLeftLiftID, inRIghtLiftID, outLeftLiftID, outRightLiftID, outLeftRotateID, outRightRotateID);
  Limelight limelight = new Limelight(limelightHeight, targetHeight, limelightAngle);
  Shooter shooter = new Shooter(kP, kI, kD);

  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */
  @Override
  public void robotInit() {
    
    intake.setInversion(intakeMotorInverted);
    hopper.setInversion(topHopperInverted, bottomHopperInverted);
    drivetrain.setInversion(frontLeftInvert, frontRightInvert, backLeftInvert, backRightInvert);
    lift.setInversionStatus(inLeftLiftInvert, outLeftLiftInvert, inRightLiftInvert, outRightLiftInvert, outLeftRotateInvert, outRightRotateInvert);
    shooter.setInversionStatus(shooterRightInversion, shooterLeftInversion);

  }

  /** This function is run once each time the robot enters autonomous mode. */
  @Override
  public void autonomousInit() {
    
  }

  /** This function is called periodically during autonomous. */
  @Override
  public void autonomousPeriodic() {

  }

  /** This function is called once each time the robot enters teleoperated mode. */
  @Override
  public void teleopInit() {
    
  }

  

  /** This function is called periodically during teleoperated mode. */
  @Override
  public void teleopPeriodic() {
    
    drivetrain.stop();
    drivetrain.resetEncoders();
    
  }

  /** This function is called once each time the robot enters test mode. */
  @Override
  public void testInit() {}

  /** This function is called periodically during test mode. */
  @Override
  public void testPeriodic() {}
}
