package frc.robot;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.CANSparkMax;
import com.ctre.phoenix.motorcontrol.ControlMode;
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
}
