package frc.robot;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.ControlMode;

public class Hopper {
    
    TalonSRX topHopperMotor, bottomHopperMotor;

    /**
     * Constructor class defining hopper motors
     * @param topHopperID ID of the top hopper motor
     * @param bottomHopperID ID of the bottom hopper motor
     */
    public Hopper (int topHopperID, int bottomHopperID) {

        topHopperMotor = new TalonSRX(topHopperID);
        bottomHopperMotor = new TalonSRX(bottomHopperID);

    }

    /**
     * Method to set the inversion statuses of the hopper motors
     * @param topHopperInverted Boolean for whether or not the top hopper motor is inverted
     * @param bottomHopperInverted Boolean for whether or not the bottom hopper motor is inverted
     */
    public void setInversion (boolean topHopperInverted, boolean bottomHopperInverted) {
        
        topHopperMotor.setInverted(topHopperInverted);
        bottomHopperMotor.setInverted(bottomHopperInverted);

    }

    /**
     * Method to control the intake of the hopper
     * @param power Undefined power level for the intake of the front hopper motor
     */
    public void hopperIn (double power) {

        topHopperMotor.set(ControlMode.PercentOutput, Math.abs(power));

    }
    /**
     * Method to control the outtake of the hopper
     * @param power2 Undefined power level for the outtake of the hopper motors
     */
    public void hopperOut (double power2) {

        topHopperMotor.set(ControlMode.PercentOutput, -Math.abs(power2));

    }

    /**
     * Method to control the intake of the inner motor
     * @param power Undefined intake power level of the inner motor
     */
    public void hopperShoot (double power) {

        bottomHopperMotor.set(ControlMode.PercentOutput, Math.abs(power));

    }

    /**
     * Method to control the outtake of the oute motor
     * @param power Undefined outtake power level of the inner motor
     */
    public void hopperSpit (double power) {

        bottomHopperMotor.set(ControlMode.PercentOutput, -Math.abs(power));

    }

    /**
     * Method to stop all hopper motors
     */
    public void stop () {

        topHopperMotor.set(ControlMode.PercentOutput, 0);
        bottomHopperMotor.set(ControlMode.PercentOutput, 0);

    }
}
