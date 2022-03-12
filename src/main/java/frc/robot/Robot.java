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
  
  Intake intake = new Intake(16, 11);
  Hopper hopper = new Hopper(15, 14);
  Timer timer = new Timer();

  Drivetrain drivetrain = new Drivetrain(6, 8, 7, 9);
  Lift lift = new Lift(12, 13, 2, 3);
  Limelight limelight = new Limelight(43.0, 104.0, 20.0);
  Shooter shooter = new Shooter(6e-5, 0, 0, 4, 5, 0, 0.000015, -1, 1, 6000);
  Joystick leftJoystick = new Joystick(0);
  Joystick rightJoystick = new Joystick(1);
  Joystick XBOXController = new Joystick(3);

  public double noPIDPower = .5;

  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */
  @Override
  public void robotInit() {
    
    intake.setInversion(false, true);
    hopper.setInversion(false, true);
    drivetrain.setInversion(true, false, true, false);
    lift.setInversionStatus(true, false, false, true);

  }

  /** This function is run once each time the robot enters autonomous mode. */
  @Override
  public void autonomousInit() {
    //if(myTimer)

    /** 
    try{

      Thread.sleep(7000);

    } 
    
    catch (InterruptedException e){

      return;

    }
    */
    
    timer.reset();

    timer.start();

    while (timer.get() < 1.5) {

      shooter.autoShoot(true, limelight);

    }

    while (timer.get() < 2.25) {
    
      hopper.hopperIn(.4);
      hopper.hopperShoot(.4);

    }

    while (timer.get() < 5) {

      shooter.stop();
      hopper.stop();

    }

    while (timer.get() < 7){

      drivetrain.resetEncoders();

      double autoDistance = drivetrain.frontRightEncoder.getPosition();

      while (autoDistance > -25.0) {
      
        drivetrain.autonomousDrive();
        drivetrain.frontRightEncoder.getPosition();
        autoDistance = drivetrain.frontRightEncoder.getPosition();

      }

      drivetrain.stop();

    }
    /** 
    drivetrain.resetEncoders();

    double autoDistance = drivetrain.frontRightEncoder.getPosition();

    //while (yOffset > -7.5) {
    while (autoDistance > -4.6) {
    
      drivetrain.autonomousDrive();
      drivetrain.frontRightEncoder.getPosition();
      autoDistance = drivetrain.frontRightEncoder.getPosition();

      //limelight.updateLimelightVariables(true);
      //yOffset = limelight.updateLimelightVariables(true);

    }
    */
    /** 
    drivetrain.stop();
    drivetrain.resetEncoders();
    limelight.updateLimelightVariables(true); 

    shooter.autoShoot(true, limelight);

    
    double rpmLoop = 0;

    while (rpmLoop <= 5300.0) {
      
      rpmLoop = Math.abs(shooter.leftShooterEncoder.getVelocity());

    } 
      
    hopper.hopperIn(.3);
    hopper.hopperShoot(.3);

    while (rpmLoop >= 5050.0) {

      rpmLoop = Math.abs(shooter.leftShooterEncoder.getVelocity());

    }    

    shooter.stop();
    hopper.stop();

    drivetrain.resetEncoders();

    double autoDistance = drivetrain.frontRightEncoder.getPosition();

    //while (yOffset > -7.5) {
    while (autoDistance > -25.0) {
    
      drivetrain.autonomousDrive();
      drivetrain.frontRightEncoder.getPosition();
      autoDistance = drivetrain.frontRightEncoder.getPosition();

      //limelight.updateLimelightVariables(true);
      //yOffset = limelight.updateLimelightVariables(true);

    }

    shooter.stop();
    hopper.stop();
    drivetrain.stop();
    */
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
    hopper.stop();
    intake.stop();

    shooter.setPIDCoefficients(true,  0.00012, 0.0006, 0.0, 0.005, 5600);

    CameraServer.startAutomaticCapture(0);

  }

  

  /** This function is called periodically during teleoperated mode. */
  @Override
  public void teleopPeriodic() {
    
    //Calls drivetrain drive method and drives based on pilot controller inputs
    drivetrain.drive(leftJoystick, rightJoystick);

    //Calls lift rotateLift method and rotates arm based on right joystick input; left = counter clockwise, right = clockwise
    lift.rotateLift(XBOXController);

    //Calls lift moveLift method and moves lift up or down based on left XBOX joystick input; up = up, down = down on lift
    lift.moveLift(XBOXController);


    //left joystick trigger pushed--> run shooter with no PID Control
    if (leftJoystick.getRawButtonPressed(1)) {

      shooter.updatePIDCoefficients(true);

    }
    

    //right joystick trigger pushed --> run shooter with PID Control
    if (rightJoystick.getRawButtonPressed(1)) {

      shooter.updatePIDCoefficients(true);
      //drivetrain.prepareShoot(true, limelight);
      //shooter.shoot(true, limelight);

    }

    //top, middle button on right joystick held --> intake to shooter (use after aiming/ready to shoot)
    if (rightJoystick.getRawButton(2) || leftJoystick.getRawButton(2)) {

      shooter.stop();

    }

    
    //ALL POSSIBLE WAYS TO MOVE INTAKE AND HOPPER
    //Push L2 on XBOX Controller, moves in intake and lower hopper
    if (XBOXController.getRawButton(7)) {

      intake.intakeIn(.3);
      hopper.hopperShoot(.3);

    }

    else if (XBOXController.getRawButtonReleased(7)) {

      intake.stop();
      hopper.stop();

    }

    //Push R2 on XBOX Controller, moves in both hopper motors
    if (XBOXController.getRawButton(8)) {

      hopper.hopperIn(.4);
      hopper.hopperShoot(.4);

    }

    else if (XBOXController.getRawButtonReleased(8)) {

      intake.stop();
      hopper.stop();

    }

    //A button on XBOX Controller --> front motor intake
    if (XBOXController.getRawButton(2)) {

      hopper.hopperShoot(0.3);

    }

    else if (XBOXController.getRawButtonReleased(2)) {

      hopper.stop();

    }

    //B button on XBOX Controller --> back motor intake
    if (XBOXController.getRawButton(3)) {

      hopper.hopperIn(0.3);

    }

    else if (XBOXController.getRawButtonReleased(3)) {

      hopper.stop();

    }

    //X button on XBOX Controller --> outer hopper outtake
    if (XBOXController.getRawButton(1)) {

      hopper.hopperOut(.3);

    }

    else if (XBOXController.getRawButtonReleased(1)) {

      hopper.stop();

    }

    //Y button on XBOX Controller --> inner hopper motor outtakes
    if (XBOXController.getRawButton(4)) {

      hopper.hopperSpit(0.3);

    }

    else if (XBOXController.getRawButtonReleased(4)) {

      hopper.stop();

    }

    //L1 button is pushed --> start flywheel
    if (XBOXController.getRawButton(5)) {

      shooter.updatePIDCoefficients(true);

    }

    //R1 button is pushed --> spin outtake
    if (XBOXController.getRawButton(6)) {

      shooter.stop();

    }

    else if (XBOXController.getRawButtonReleased(6)) {

      intake.stop();

    }
  
    //For all .getPOV's make sure to test if it is only while the button is held or not
    if (XBOXController.getPOV() == 0) {

      intake.moveArm(.30);

    }

    else if (XBOXController.getPOV() == 180) {

      intake.moveArm(-.30);

    }

    else {

      intake.stopArm();

    } 

    //left on DPAD disable shooter
    if (XBOXController.getPOV() == 270) {

      shooter.stop();

      }

    if (XBOXController.getRawButton(9)) {

      lift.outLeftRotate.set(.9);
      lift.outRightRotate.set(.9);

    }

    else if (XBOXController.getRawButtonReleased(9)) {

      lift.outLeftRotate.set(0);
      lift.outRightRotate.set(0);

    }

    if (XBOXController.getRawButton(10)) {

      lift.outLeftRotate.set(-.9);
      lift.outRightRotate.set(-.9);

    }

    else if (XBOXController.getRawButtonReleased(10)) {

      lift.outLeftRotate.set(0);
      lift.outRightRotate.set(0);

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

