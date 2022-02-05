package frc.robot;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.wpilibj.Joystick;


public class Intake {
    
    TalonSRX intakeMotor, intakeArm;

    /**
     * Constructor class defining the motor for the robots intake system
     * @param intakeMotorID ID of the intake motor
     * @param intakermID ID of the motor for the intake arm
     */
    public Intake (int intakeMotorID, int intakeArmID) {

        intakeMotor = new TalonSRX(intakeMotorID);
        intakeArm = new TalonSRX(intakeArmID);

    }

    /**
     * Method to set the inversion status of the intake motor
     * @param intakeMotorInverted Boolean for the inversion status of the intake motor
     * @param intakeArmInverted Boolean for the inversion status of the intake arm motor
     */
    public void setInversion (boolean intakeMotorInverted, boolean intakeArmInverted) {

        intakeMotor.setInverted(intakeMotorInverted);
        intakeArm.setInverted(intakeArmInverted);

    }

    /**
     * Method to run the intake 
     * @param power Unspecified power to run the intake arm motor
     */
    public void intakeIn (double power) {

        intakeMotor.set(ControlMode.PercentOutput, Math.abs(power));

    }

    /**
     * Method to run the intake in reverse
     * @param power2 Unspecified negative power for the intake arm motor
     */
    public void intakeOut (double power2) {

        intakeMotor.set(ControlMode.PercentOutput, -Math.abs(power2));

    }

    /**
     * Code to move the intake arm down
     * @param joystick Joystick ssociated with the power of the intake
     * Move joystick to the left, arm goes down; move joystick right, arm goes up
     */
    public void moveArm (Joystick joystick) {

        double intakePower = joystick.getRawAxis(0);
        intakeArm.set(ControlMode.PercentOutput, (.25 * intakePower));

    }

    /**
     * Method to stop the intake motor
     */
    public void stop () {

        intakeMotor.set(ControlMode.PercentOutput, 0);

    }
}
