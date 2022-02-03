package frc.robot;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.ControlMode;

public class Intake {
    
    TalonSRX intakeMotor;

    /**
     * Constructor class defining the motor for the robots intake system
     * @param intakeMotorID ID of the intake motor
     */
    public Intake (int intakeMotorID) {

        intakeMotor = new TalonSRX(intakeMotorID);

    }

    /**
     * Method to set the inversion status of the intake motor
     * @param intakeMotorInverted Boolean for the inversion status of the intake motor
     */
    public void setInversion (boolean intakeMotorInverted) {

        intakeMotor.setInverted(intakeMotorInverted);

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
     * Method to stop the intake motor
     */
    public void stop () {

        intakeMotor.set(ControlMode.PercentOutput, 0);

    }
}
