// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import com.revrobotics.CANSparkMax;

/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the manifest file in the resource
 * directory.
 * 
 * Calls all of the constructor classes with appropriate input values
 */
public class Robot extends TimedRobot {
  
  //Intake intake = new Intake(intakeMotorID, intakeArmID);
  //Hopper hopper = new Hopper(1, 2);
  Drivetrain drivetrain = new Drivetrain(6, 8, 7, 9);
  //Lift lift = new Lift(inLeftLiftID, inRIghtLiftID, outLeftRotateID, outRightRotateID);
  //Limelight limelight = new Limelight(33.5, 104.0, 3.0);
  //Shooter shooter = new Shooter(kP, kI, kD);
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
    
    //intake.setInversion(intakeMotorInverted, intakeArmInverted);
    //hopper.setInversion(false, true);
    drivetrain.setInversion(true, false, true, false);
    //lift.setInversionStatus(inLeftLiftInvert, inRightLiftInvert, outLeftRotateInvert, outRightRotateInvert);
    //shooter.setInversionStatus(shooterRightInversion, shooterLeftInversion);

  }

  /** This function is run once each time the robot enters autonomous mode. */
  @Override
  public void autonomousInit() {

    drivetrain.stop();
    drivetrain.resetEncoders();
    //limelight.updateLimelightVariables(true);

  }

  /** This function is called periodically during autonomous. */
  @Override
  public void autonomousPeriodic() {
    
    //double targetDistance = limelight.updateLimelightVariables(false);
    //drivetrain.autonomousDrive(targetDistance, 150.0);

    //Code for autonomous shooting, should start after driving backward; test if shooter gets to speed before hopper starts
    //if (targetDistance >= 150.0) {
      //shooter.shoot(true, limelight);
      //drivetrain.prepareShoot(false, limelight);

      /**if (shooter.autoShoot() == true){ //should work for autonomous period shooting
       * 
      hopper.hopperIn(.2);
      hopper.hopperShoot(.2);

      }
      */
    //}
  }

  /** This function is called once each time the robot enters teleoperated mode. */
  @Override
  public void teleopInit() {

    drivetrain.stop();
    drivetrain.resetEncoders();
    //shooter.stop();
    //shooter.resetEncoders();

    CameraServer.startAutomaticCapture(0);
    CameraServer.startAutomaticCapture(1);

  }

  

  /** This function is called periodically during teleoperated mode. */
  @Override
  public void teleopPeriodic() {
    
    //Calls drivetrain drive method and drives based on pilot controller inputs
    drivetrain.drive(leftJoystick, rightJoystick);

    //Calls lift rotateLift method and rotates arm based on right joystick input; left = counter clockwise, right = clockwise
    //lift.rotateLift(XBOXJoystick);

    //Calls lift moveLift method and moves lift up or down based on left XBOX joystick input; up = up, down = down on lift
    //lift.moveLift(XBOXJoystick);

    //L1 button is held --> spin intake
    if (XBOXController.getRawButtonPressed(5)) {

      //intake.intakeIn(.5);

    }
    else if (XBOXController.getRawButtonReleased(5)){

      //intake.stop();

    }

    //R1 button is held --> spin outtake
    if (XBOXController.getRawButtonPressed(6)) {

      //intake.intakeIn(.5);

    }
    else if (XBOXController.getRawButtonReleased(6)){

      //intake.stop();

    }

    //left joystick trigger held--> first hopper motor in
    /*if (leftJoystick.getRawButtonPressed(1)) {

      hopper.hopperIn(.3);

    }
    else if (leftJoystick.getRawButtonReleased(1)) {

      hopper.stop();

    }*/

    //right joystick trigger held --> aims robot and starts spinning flyehweel
    /*if (rightJoystick.getRawButtonPressed(1)) {

      drivetrain.prepareShoot(true, limelight);
      //shooter.shoot(true, limelight);

    }*/

    //top, middle button on right joystick held --> intake to shooter (use after aiming/ready to shoot)
    /*if (rightJoystick.getRawButton(2)) {

      hopper.hopperIn(.2);
      hopper.hopperShoot(.3);

    }*/

    /*else if (rightJoystick.getRawButtonReleased(2)) {

      hopper.stop();
      //shooter.stop();

    }
    

    //X button on XBOX Controller --> outer hopper outtake
    if (XBOXController.getRawButton(3)) {

      hopper.hopperOut(.3);

      
    }
    else if (XBOXController.getRawButtonReleased(3)) {

      hopper.stop();

    }


    //Y button on XBOX Controller --> inner hopper motor outtakes
    if (XBOXController.getRawButton(4)) {

      hopper.hopperSpit(.3);

    }
      
    else if (XBOXController.getRawButtonReleased(4)) {

        hopper.stop();

    }

    //For all .getPOV's make sure to test if it is only while the button is held or not
    if (XBOXController.getPOV() == 0) {

      //intake.moveArm(.25);

    }

    if (XBOXController.getPOV() == 180) {

      //intake.moveArm(-.25);

    }*/
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
