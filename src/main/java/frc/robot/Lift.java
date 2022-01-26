package frc.robot;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.revrobotics.CANSparkMax;
import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.wpilibj.Joystick;

public class Lift {
    
    TalonSRX inLeftLift, inRightLift, outLeftLift, outRightLift, outLeftRotate, outRightRotate;
    
    /**
     * Constructs a Lift Object in the new robot class
     * @param inLeftLiftID The ID of the left motor that controls regular lift
     * @param inRightLiftID The ID of the right motor that controls the regular lift
     * @param outLeftLiftID The ID of the left motor that controls the extension of the outer lift
     * @param outRightLiftID The ID of the right motor that controls the extension of the outer lift
     * @param outLeftRotateID The ID of the left motor that rotates the outer lift
     * @param outRightRotateID The ID of the right motor that rotates the outer lift
     */

    public Lift (int inLeftLiftID, int inRIghtLiftID, int outLeftLiftID, int outRightLiftID, int outLeftRotateID, int outRightRotateID) {

        inLeftLift = new TalonSRX(inLeftLiftID);
        inRightLift = new TalonSRX(inRIghtLiftID);
        outLeftLift = new TalonSRX(outLeftLiftID);
        outRightLift = new TalonSRX(outRightLiftID);
        outLeftRotate = new TalonSRX(outLeftRotateID);
        outRightRotate = new TalonSRX(outRightRotateID);

    } 

    /**
     * Sets the inversion status of each motor 
     * @param inLeftLiftInvert Boolean for inversion status of the left motor controlling regular lift
     * @param inRightLiftInvert Boolean for inversion status of the right motor controlling regular lift
     * @param outLeftLiftInvert Boolean for inversion status of the left motor controlling outer lift
     * @param outRightLiftInvert Boolean for inversion status of the right motor controlling outer lift
     * @param outLeftRotateInvert Boolean for inversion status of the left motor controlling rotating lift
     * @param outRightRotateInvert Boolean for inversion status of the right motor controlling rotating lift
     */ 

    public void setInversionStatus(boolean inLeftLiftInvert, boolean outLeftLiftInvert, boolean inRightLiftInvert, boolean outRightLiftInvert, boolean outLeftRotateInvert, boolean outRightRotateInvert) {

        inLeftLift.setInverted(inLeftLiftInvert);
        inRightLift.setInverted(inRightLiftInvert);
        outLeftLift.setInverted(outLeftLiftInvert);
        outRightLift.setInverted(outRightLiftInvert);
        outLeftRotate.setInverted(outLeftRotateInvert);
        outRightRotate.setInverted(outRightRotateInvert);

    }

    /** 
     * Moves the inner lift up
     * @param power The unspecified power needed to move the hopper up
     */
    public void inLiftUp(double power) {

        inLeftLift.set(ControlMode.PercentOutput, Math.abs(power));
        inRightLift.set(ControlMode.PercentOutput, Math.abs(power));

    }

    /**
     * Moves the inner lift down 
     * @param power The unspecificed power (equal to moving up) to move the hopper down
     */
    public void inLiftDown(double power) {

        inLeftLift.set(ControlMode.PercentOutput, -Math.abs(power));
        inRightLift.set(ControlMode.PercentOutput,-Math.abs(power));

    }

    /**
     * Extends the outer lift
     * @param power2 The unspecified power to extend the outer lift
     */
    public void outLiftExtend(double power2) {

        outLeftLift.set(ControlMode.PercentOutput, Math.abs(power2));
        outRightLift.set(ControlMode.PercentOutput, Math.abs(power2));

    }

    /**
     * Retracts the outer lift
     * @param power2 The unspecified power (equal to the power used to extend) to retract the outer lift
     */
    public void outLiftRetract(double power2) {

        outLeftLift.set(ControlMode.PercentOutput, -Math.abs(power2));
        outRightLift.set(ControlMode.PercentOutput, -Math.abs(power2));

    }

    /**
     * Rotates the outer lift clockwise
     * @param power3 The unspecified power used to rotate the outer lift
     */
    public void rotateLiftClock(double power3) {

        outLeftRotate.set(ControlMode.PercentOutput, Math.abs(power3));
        outRightRotate.set(ControlMode.PercentOutput, Math.abs(power3));

    }
    /**
     * Rotates the outer lift counter clockwise
     * @param power3 The unspecified power used to rotate the outer lift in negative direction
     */
    public void rotateLiftCntrClock(double power3){
        
        outLeftRotate.set(ControlMode.PercentOutput, -Math.abs(power3));
        outRightRotate.set(ControlMode.PercentOutput, -Math.abs(power3));

    }

    /**
     * Stops all lift motors
     */
    public void stop() {

        inLeftLift.set(ControlMode.PercentOutput, 0);
        inRightLift.set(ControlMode.PercentOutput, 0);
        outLeftLift.set(ControlMode.PercentOutput, 0);
        outRightLift.set(ControlMode.PercentOutput, 0);
        outLeftRotate.set(ControlMode.PercentOutput, 0);
        outRightRotate.set(ControlMode.PercentOutput, 0);
        
    }
}
