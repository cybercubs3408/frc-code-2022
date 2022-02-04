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
  
  Intake intake = new Intake(intakeMotorID, intakeArmID);
  Hopper hopper = new Hopper(topHopperID, bottomHopperID);
  Drivetrain drivetrain = new Drivetrain(frontLeftID, frontRightID, backLeftID, backRightID);
  Lift lift = new Lift(inLeftLiftID, inRIghtLiftID, outLeftLiftID, outRightLiftID, outLeftRotateID, outRightRotateID);
  Limelight limelight = new Limelight(limelightHeight, targetHeight, limelightAngle);
  Shooter shooter = new Shooter(kP, kI, kD);
  Joystick leftJoystick = new Joystick(0);
  Joystick rightJoystick = new Joystick(1);
  Joystick XBOXController = new Joystick(2);

  Boolean armUp = true;

  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */
  @Override
  public void robotInit() {
    
    intake.setInversion(intakeMotorInverted, intakeArmInverted);
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

    drivetrain.stop();
    drivetrain.resetEncoders();
    shooter.stop();
    shooter.resetEncoders();

  }

  

  /** This function is called periodically during teleoperated mode. */
  @Override
  public void teleopPeriodic() {
    
    drivetrain.drive(leftJoystick, rightJoystick);

    //B button is pressed once --> arm up or down
    if (XBOXController.getRawButtonPressed(1)) {

      if (armUp) {

        intake.armDown();
        armUp = false;

      }

      else if (armUp == false) {

        intake.armUp();
        armUp = true;

      }
    }

    //L1 button is held --> spin intake
    if (XBOXController.getRawButton(4)) {

      intake.intakeIn(.5);

    }

    //R1 button is held --> spin outtake
    if (XBOXController.getRawButton(5)) {

      intake.intakeIn(.5);

    }

    //left joystick trigger --> first hopper motor in
    if (leftJoystick.getRawButton(0)) {

      hopper.hopperIn(.3);

    }

    //right jostick trigger --> beeg shoot button
    if (rightJoystick.getRawButton(0)) {

      drivetrain.prepareShoot(true, limelight);
      hopper.hopperIn(.3);
      hopper.hopperShoot(.3);

    }
    
    //X button on XBOX Controller --> outer hopper outtake
    if (XBOXController.getRawButton(2)) {

      hopper.hopperOut(.3);

    }

    //Y button on XBOX Controller --> inner hopper motor outtakes
    if (XBOXController.getRawButton(3)) {

      hopper.hopperSpit(.3);

    }
  }

  /** This function is called once each time the robot enters test mode. */
  @Override
  public void testInit() {
    
  }

  /** This function is called periodically during test mode. */
  @Override
  public void testPeriodic() {

  }
}
