// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.math.trajectory.TrajectoryUtil;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import java.io.IOException;
import java.nio.file.Path;

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
  Lift lift = new Lift(12, 13);
  Limelight limelight = new Limelight(43.0, 104.0, 20.0);
  Shooter shooter = new Shooter(6e-5, 0, 0, 4, 5, 0, 0.000015, -1, 1, 6000);
  Joystick leftJoystick = new Joystick(0);
  Joystick rightJoystick = new Joystick(1);
  Joystick XBOXController = new Joystick(3);
  String trajectoryJSON = "paths/output/Gamer.wpilib.json";  // Path to get to gamer.wpilib.json . Might not work as intended
  Trajectory trajectory = new Trajectory();

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
    lift.setInversionStatus(true, false);
 
//Below is Pathweaver code that probably doesnt work. Please approach with caution

    try {
      Path trajectoryPath = Filesystem.getDeployDirectory().toPath().resolve(trajectoryJSON);
      trajectory = TrajectoryUtil.fromPathweaverJson(trajectoryPath);
   } catch (IOException ex) {
      DriverStation.reportError("Unable to open trajectory: " + trajectoryJSON, ex.getStackTrace());
   }

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

    /*while (timer.get() < 1.5) {

      shooter.autoShoot(true, limelight);

    }

    while (timer.get() < 4) {
    
      hopper.hopperIn(.4);
      hopper.hopperShoot(.4);

    }
    

    while (timer.get() < 4.5) {

      shooter.stop();
      hopper.stop();

    }
    */

    while (timer.get() < .5) {

      intake.moveArm(0.3);

    }

    while (timer.get() < 1.0) {

      intake.stop();
      hopper.hopperShoot(.3);
      intake.intakeIn(.3);

    }

    while (timer.get() < 3.0){

      drivetrain.resetEncoders();

      double autoDistance = drivetrain.frontRightEncoder.getPosition();

      while (autoDistance > -30.0) {
      
        drivetrain.autonomousDrive();
        drivetrain.frontRightEncoder.getPosition();
        autoDistance = drivetrain.frontRightEncoder.getPosition();

      }

      drivetrain.stop();
      intake.stop();
      hopper.stop();

      drivetrain.resetEncoders();

      autoDistance = drivetrain.frontRightEncoder.getPosition();

      while (autoDistance < 10.0) {

        drivetrain.autonomousDrive2();
        drivetrain.frontRightEncoder.getPosition();
        autoDistance = drivetrain.frontRightEncoder.getPosition();

      }

      drivetrain.stop();

    }

    timer.reset();
    timer.start();

    while (timer.get() < 3.5) {
  
      shooter.shoot(false, limelight);
  
    }
  
    while (timer.get() < 4.5) {
      
      hopper.hopperIn(.4);
  
    }
  
    while (timer.get() < 5.5) {
  
      hopper.hopperIn(.4);
      hopper.hopperShoot(.4);
  
    }

    while (timer.get() < 8) {

      shooter.stop();
      hopper.stop();

    }

    drivetrain.stop();
    shooter.stop();
    hopper.stop();
    intake.stop();

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

    shooter.setPIDCoefficients(true,  0.00012, 1, 0.0, 0.00, 4500);

  }

  

  /** This function is called periodically during teleoperated mode. */
  @Override
  public void teleopPeriodic() {
    
    //Calls drivetrain drive method and drives based on pilot controller inputs
    drivetrain.drive(leftJoystick, rightJoystick);

    //Calls lift moveLift method and moves lift up or down based on left XBOX joystick input; up = up, down = down on lift
    lift.moveLift(XBOXController);


    //left joystick trigger pushed--> run shooter with no PID Control
    if (leftJoystick.getRawButtonPressed(1)) {

      //shooter.updatePIDCoefficients(true);
      shooter.shoot(true, limelight);

    }
    

    //right joystick trigger pushed --> run shooter with no PID Control
    if (rightJoystick.getRawButtonPressed(1)) {

      //shooter.updatePIDCoefficients(true);
      //drivetrain.prepareShoot(true, limelight);
      shooter.shoot(true, limelight);

    }

    //top, middle button on right joystick held --> intake to shooter (use after aiming/ready to shoot)
    if (rightJoystick.getRawButton(2) || leftJoystick.getRawButton(2)) {

      shooter.stop();

    }

    
    //ALL POSSIBLE WAYS TO MOVE INTAKE AND HOPPER!
    //Push L2 on XBOX Controller, moves in intake and lower hopper
    if (XBOXController.getRawButton(7)) {

      intake.intakeIn(.4);
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
      intake.intakeOut(0.3);

    }

    else if (XBOXController.getRawButtonReleased(4)) {

      hopper.stop();

    }

    //L1 button is pushed --> start flywheel
    if (XBOXController.getRawButton(5)) {

      shooter.shoot(true, limelight);
      //shooter.updatePIDCoefficients(true);

    }

    ///// EXPERIMENTAL CODE TO ADJUST POWER WHILE MOVING
    /**while (XBOXController.getRawButton(5)) {

      shooter.shoot(true, limelight);

    }*/

    //R1 button is pushed --> stop shooter
    if (XBOXController.getRawButton(6)) {

      shooter.stop();

    }

    else if (XBOXController.getRawButtonReleased(6)) {

      intake.stop();

    }
  
    //For all .getPOV's make sure to test if it is only while the button is held or not
    if (XBOXController.getPOV() == 0) {

      intake.moveArm(-.45);

    }

    else if (XBOXController.getPOV() == 180) {

      intake.moveArm(.30);

    }

    else {

      intake.stopArm();

    } 

    //left on DPAD, test limelight shooter
    if (XBOXController.getPOV() == 270) {

      shooter.shoot(true, limelight);
      //shooter.noPIDShoot(.95);

    }

    if (leftJoystick.getRawButton(8)) {

      shooter.changeK(1.0);

    }

    if (leftJoystick.getRawButton(9)) {

      shooter.changeK(.9);

    }

    if (leftJoystick.getRawButton(10)) {

      shooter.changeK(.8);

    }

    if (leftJoystick.getRawButton(14)) {

      shooter.changeK(1.0);

    }

    if (leftJoystick.getRawButton(15)) {

      shooter.changeK(1.2);

    }

    if (leftJoystick.getRawButton(16)) {

      shooter.changeK(1.3);

    }

    if (leftJoystick.getRawButton(5)) {

      SmartDashboard.putNumber("currentRpm", shooter.leftShooterEncoder.getVelocity());

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

