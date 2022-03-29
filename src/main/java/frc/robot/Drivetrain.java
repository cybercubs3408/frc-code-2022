package frc.robot;

import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.CANSparkMax;
import edu.wpi.first.wpilibj.Joystick;

public class Drivetrain {
    
    CANSparkMax frontLeft, frontRight, backLeft, backRight;
    RelativeEncoder frontLeftEncoder, frontRightEncoder, backLeftEncoder, backRightEncoder;

    /**
     * Drivetrain constructor class; defines drivetrain motors and encoders
     * @param frontLeftID ID of the front left motor in drivetrain
     * @param frontRightID ID of the front right motor in drivetrain
     * @param backLeftID ID of the back left motor in drivetrain
     * @param backRightID ID of the back right motor in drivetrain
     */
    public Drivetrain (int frontLeftID, int frontRightID, int backLeftID, int backRightID) {

        frontLeft = new CANSparkMax(frontLeftID, MotorType.kBrushless);
        frontRight = new CANSparkMax(frontRightID, MotorType.kBrushless);
        backLeft = new CANSparkMax(backLeftID, MotorType.kBrushless);
        backRight = new CANSparkMax(backRightID, MotorType.kBrushless);
        frontLeftEncoder = frontLeft.getEncoder();
        frontRightEncoder = frontRight.getEncoder();
        backLeftEncoder = backLeft.getEncoder();
        backRightEncoder = backRight.getEncoder();

    }

    /**
     * Sets the inversion statuses of the motors in the drivetrain
     * @param frontLeftInvert Boolean that will invert the direction of rotation of the front left motor if true
     * @param frontRightInvert Boolean that will invert the direction of rotation of the front right motor if true
     * @param backLeftInvert Boolean that will invert the direction of rotation of the back left motor if true
     * @param backRightInvert Boolean that will invert the direction of rotation of the back right motor if true
     */
    public void setInversion (boolean frontLeftInvert, boolean frontRightInvert, boolean backLeftInvert, boolean backRightInvert) {

        frontLeft.setInverted(frontLeftInvert);
        frontRight.setInverted(frontRightInvert);
        backLeft.setInverted(backLeftInvert);
        backRight.setInverted(backRightInvert);

    }   

    /**
     * Stops drivetrain motors
     */
    public void stop() {

        frontLeft.set(0);
        frontRight.set(0);
        backLeft.set(0);
        backRight.set(0);

    }

    /**
     * Resets encoder values to 0
     */
    public void resetEncoders() {

        frontLeftEncoder.setPosition(0);
        frontRightEncoder.setPosition(0);
        backLeftEncoder.setPosition(0);
        backRightEncoder.setPosition(0);

    }

    /**
     * Class to initiate driving
     * @param leftJoystick  Object of Joystick; controls left joystick
     * @param rightJoystick  Object of Joystick; controls right joystick
     */
    public void drive(Joystick leftJoystick, Joystick rightJoystick){
        
        double leftPower = leftJoystick.getRawAxis(1);
        double rightPower = rightJoystick.getRawAxis(1);

        // Alter decimal being multiplied to change the power as needed

        frontLeft.set(Math.abs(leftPower) * leftPower);
        backLeft.set(Math.abs(leftPower) * leftPower);
        frontRight.set(Math.abs(rightPower) * rightPower);
        backRight.set(Math.abs(rightPower) * rightPower);
    }

    /**
     * Method to aim the robot while preparing to shoot
     * @param direction String set to either "left" or "right" to indicate which direction to turn
     * @param power Power to control turning
     */
    public void autoAim(String direction, double power) {

        if (direction.equals("left")) {

            frontLeft.set(-power);
            backLeft.set(-power);
            frontRight.set(power);
            backRight.set(power);
            
        } 
        else if (direction.equals("right")) {

            frontLeft.set(power);
            backLeft.set(power);
            frontRight.set(-power);
            backRight.set(-power);

        }
    }

    /**
     * Method used to aim the robot before activting the shooter
     * @param smartDashboardDisplay Boolean as to whether or not smart dashboard values will be displayed, basically always true
     * @param limelight The limelight
     * Updates limelight variables, reads the x value of target and turns robot right or left until the x is close to zero (facing target)
     */
    public void prepareShoot (boolean smartDashboardDisplay, Limelight limelight) {
        
        limelight.updateLimelightVariables(smartDashboardDisplay);

        while (Math.abs(limelight.xOff) > 0.5) {

            limelight.updateLimelightVariables(smartDashboardDisplay);

            if (limelight.xOff < 0) {

                autoAim("left", 0.09);

            }

            else if (limelight.xOff > 0) {

                autoAim("right", 0.09);

            }

        }

        stop();
    }

    /**
     * Code for autonomous driving of the robot
     * While the robot is within the tarmac, drive off
     */
    public void autonomousDrive () {

        frontLeft.set(-0.1);
        frontRight.set(-0.14);
        backLeft.set(-0.1);
        backRight.set(-0.14);

    }

    // Same as before but forward instead of back
    public void autonomousDrive2 () {

        frontLeft.set(0.08);
        frontRight.set(0.1);
        backLeft.set(0.08);
        backRight.set(0.1);

    }
}
