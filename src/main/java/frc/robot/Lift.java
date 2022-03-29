package frc.robot;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.wpilibj.Joystick;

public class Lift {
    
    TalonSRX inLeftLift, inRightLift;
    
    /**
     * Constructs a Lift Object in the new robot class
     * @param inLeftLiftID The ID of the left motor that controls regular lift
     * @param inRightLiftID The ID of the right motor that controls the regular lift
     */

    public Lift (int inLeftLiftID, int inRIghtLiftID) {

        inLeftLift = new TalonSRX(inLeftLiftID);
        inRightLift = new TalonSRX(inRIghtLiftID);

    } 

    /**
     * Sets the inversion status of each motor 
     * @param inLeftLiftInvert Boolean for inversion status of the left motor controlling regular lift
     * @param inRightLiftInvert Boolean for inversion status of the right motor controlling regular lift
     */ 

    public void setInversionStatus(boolean inLeftLiftInvert, boolean inRightLiftInvert) {

        inLeftLift.setInverted(inLeftLiftInvert);
        inRightLift.setInverted(inRightLiftInvert);

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
     * Stops all lift motors
     */
    public void stop() {

        inLeftLift.set(ControlMode.PercentOutput, 0);
        inRightLift.set(ControlMode.PercentOutput, 0);
        
    }
}
