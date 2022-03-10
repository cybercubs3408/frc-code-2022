package frc.robot;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.wpilibj.Joystick;

public class Lift {
    
    TalonSRX inLeftLift, inRightLift;
    CANSparkMax outLeftRotate, outRightRotate;
    
    /**
     * Constructs a Lift Object in the new robot class
     * @param inLeftLiftID The ID of the left motor that controls regular lift
     * @param inRightLiftID The ID of the right motor that controls the regular lift
     * @param outLeftRotateID The ID of the left motor that rotates the outer lift
     * @param outRightRotateID The ID of the right motor that rotates the outer lift
     */

    public Lift (int inLeftLiftID, int inRIghtLiftID, int outLeftRotateID, int outRightRotateID) {

        inLeftLift = new TalonSRX(inLeftLiftID);
        inRightLift = new TalonSRX(inRIghtLiftID);
        outLeftRotate = new CANSparkMax(outLeftRotateID, MotorType.kBrushless);
        outRightRotate = new CANSparkMax(outRightRotateID, MotorType.kBrushless);

    } 

    /**
     * Sets the inversion status of each motor 
     * @param inLeftLiftInvert Boolean for inversion status of the left motor controlling regular lift
     * @param inRightLiftInvert Boolean for inversion status of the right motor controlling regular lift
     * @param outLeftRotateInvert Boolean for inversion status of the left motor controlling rotating lift
     * @param outRightRotateInvert Boolean for inversion status of the right motor controlling rotating lift
     */ 

    public void setInversionStatus(boolean inLeftLiftInvert, boolean inRightLiftInvert, boolean outLeftRotateInvert, boolean outRightRotateInvert) {

        inLeftLift.setInverted(inLeftLiftInvert);
        inRightLift.setInverted(inRightLiftInvert);
        outLeftRotate.setInverted(outLeftRotateInvert);
        outRightRotate.setInverted(outRightRotateInvert);

    }

    /** 
     * Moves the lift based on joystick input
     * @param joystick Input joystick to read for power of the lift; left joystick on XBOX controller
     * Move joystick up or down to move lift up or down
     */
    public void moveLift(Joystick joystick) {

        double power = (.3 * joystick.getRawAxis(1));
        inLeftLift.set(ControlMode.PercentOutput, -power);
        inRightLift.set(ControlMode.PercentOutput, -power);

    }

    /**
     * Rotates the lift arm based on joystick input
     * @param joystick Input joystick to read for power of the rotating arm; right joystick on XBOX controller
     * Move right joystick right for clockwise rotation and left for counter clockwise
     */
    public void rotateLift(Joystick joystick) {

        double power = .7 * joystick.getRawAxis(2);
        outLeftRotate.set(power);
        outRightRotate.set(power);

    }

    /**
     * Stops all lift motors
     */
    public void stop() {

        inLeftLift.set(ControlMode.PercentOutput, 0);
        inRightLift.set(ControlMode.PercentOutput, 0);
        outLeftRotate.set(0);
        outRightRotate.set(0);
        
    }
}
