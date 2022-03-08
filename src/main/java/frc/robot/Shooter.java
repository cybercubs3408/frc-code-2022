package frc.robot;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.SparkMaxRelativeEncoder;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import com.revrobotics.CANSparkMax.ControlType;

 
public class Shooter{

    double kP, kI, kD, kIz, kFF, kMaxOutput, kMinOutput, kMaxRPM;
    CANSparkMax rightShooterMotor, leftShooterMotor;
    SparkMaxPIDController shooterPID;
    RelativeEncoder rightShooterEncoder, leftShooterEncoder;


    //Index, undefined variable that will be used to iterate through arrays
    int index;
    //Variable for total length of arrays, currently set to 10 (so 11 array values)
    int tableList = 10;

    //Arrays for shooter input (distance) nd corresponding output (shooter power)
    //MAKE SURE VALUES ARE PUT IN IN THE SAME ORDER OR IT WILL NOT WORK
    double[] arrayInput = {};
    double[] arrayOutput = {};

    /**
     * Constructor method for the shooter class
     * @param kP Proportional gain 
     * @param kI Integral gain
     * @param kD Derivitive gain (not used)
     * @param rightShooterID ID of right controller controlling shooter
     * @param leftShooterID ID of left controller controlling  shoort
     * @param Iz Integration zone of PID controller
     * @param FF Feedforward value of PID controller
     * @param maxOutput Maximum output of PID Controller
     * @param minOutput Minimum output of PID controller
     */
    public Shooter(double kP, double kI, double kD, int rightShooterID, int leftShooterID, double Iz, double FF, double maxOutput, double minOutput, double maxRPM) {

        rightShooterMotor = new CANSparkMax(rightShooterID, MotorType.kBrushless);
        leftShooterMotor = new CANSparkMax(leftShooterID, MotorType.kBrushless);
        
        rightShooterMotor.follow(leftShooterMotor, true);
        
        leftShooterEncoder = leftShooterMotor.getEncoder();
        rightShooterEncoder = rightShooterMotor.getEncoder();

        shooterPID = leftShooterMotor.getPIDController();

        kIz = Iz;
        kFF = FF;
        kMaxOutput = maxOutput;
        kMinOutput = minOutput;
        kMaxRPM = maxRPM;

        shooterPID.setP(kP);
        shooterPID.setI(kI);
        shooterPID.setD(kD);
        shooterPID.setIZone(Iz);
        shooterPID.setFF(FF);
        shooterPID.setOutputRange(minOutput, maxOutput);
    }

    /**
     * Updates the PID coefficients 
     * @param smartDashboardDisplay Boolean on whether or not to display smart daashboard values
     */
    public void updatePIDCoefficients(boolean smartDashboardDisplay) {

        double p = SmartDashboard.getNumber("P Gain", 0);
        double i = SmartDashboard.getNumber("I Gain", 0);
        double d = SmartDashboard.getNumber("D Gain", 0);
        double iz = SmartDashboard.getNumber("I Zone", 0);
        double ff = SmartDashboard.getNumber("Feed Forward", 0);
        double max = SmartDashboard.getNumber("Max Output", 1);
        double min = SmartDashboard.getNumber("Min Output", -1);
        double target = SmartDashboard.getNumber("SetPoint", 5000);


        if (p != kP) {

            shooterPID.setP(p);
            kP = p;

        }

        if (i != kI) {

            shooterPID.setI(i);
            kI = i;

        }

        if (d != kD) {

            shooterPID.setD(d);
            kD = d;

        }

        if (iz != kIz) {

            shooterPID.setIZone(iz);
            kIz = iz;

        }

        if (ff != kFF) {

            shooterPID.setFF(ff); 
            kFF = ff;

        }

        if (min != kMinOutput || max != kMaxOutput) {

            shooterPID.setOutputRange(min, max);
            kMinOutput = min;
            kMaxOutput = max;

        }

        if (target != kMaxRPM) {

            kMaxRPM = target;

        }
    
        shooterPID.setReference(kMaxRPM, ControlType.kVelocity);

        if (smartDashboardDisplay) {
            
            SmartDashboard.putNumber("P Gain", kP);
            SmartDashboard.putNumber("I Gain", kI);
            SmartDashboard.putNumber("D Gain", kD);
            SmartDashboard.putNumber("I Zone", kIz);
            SmartDashboard.putNumber("Feed Forward", kFF);
            SmartDashboard.putNumber("Max Output", kMaxOutput);
            SmartDashboard.putNumber("Min Output", kMinOutput);
            SmartDashboard.putNumber("SetPoint", kMaxRPM);
            SmartDashboard.putNumber("ProcessVariable", leftShooterEncoder.getVelocity());

        }

    }

    /**
     * Method to redefine the PID coefficients and potentially display them to smartdashboard
     * @param smartDashboardDisplay Boolean as to whether or not to display to smart dashboard
     * @param ff Feed Forward value
     * @param p Proportional input variable
     * @param i Integral input variable
     * @param d Derivtive input variable (not used)
     * @param rpm RPM value for the shooter to reach
     */
    public void setPIDCoefficients(boolean smartDashboardDisplay, double ff, double p, double i, double d, double rpm) {

        if (smartDashboardDisplay) {
            
            SmartDashboard.putNumber("P Gain", p);
            SmartDashboard.putNumber("I Gain", i);
            SmartDashboard.putNumber("D Gain", d);
            SmartDashboard.putNumber("Feed Forward", ff);
            SmartDashboard.putNumber("SetPoint", rpm);

        }

    }
         
    /**
     * Method to set the inversions of the shooter motors
     * @param shooterRightInversion Boolean to say whether or not right shooter motor is inverted
     * @param shooterLeftInversion Boolean to say whether or not right shooter motor is inverted
     */
    public void setInversionStatus (boolean shooterRightInversion, boolean shooterLeftInversion) {

        rightShooterMotor.setInverted(shooterRightInversion);
        leftShooterMotor.setInverted(shooterLeftInversion);

    } 

    /**
     * Method that will take the current distance the robot is from the target and find the necessary RPM
     * @param targetDistance The distance between the limelight and the target
     * @return RPM to be input into setPIDcoefficients in shooter.shoot
     */
    public double shooterRanges(double targetDistance) {

        //defines rpm variable to be output and defines the index variable as 1 to start iteration
        double rpm;
        index = 0;

        //iterates through the array of distances and breaks the loop once the distance is less than an input value
        while (targetDistance > arrayInput[index] && index < tableList) {

            index ++;
        
        }

        //Fail-safe to prevent code from breaking (stops potential 0-1 below)
        if (index == 0) {

            index = 1;

        }

        //plugs the corresponding inputs and outputs into the equation for rpm output
        rpm = arrayInput[(index - 1)] + (targetDistance - arrayInput[(index - 1)]) * (arrayOutput[index] - arrayOutput[(index - 1)]) / (arrayInput[index] - arrayInput[(index - 1)]);
        return rpm;

    }

    /**
     * Method to actually shoot the shooter using PID values read from Smart Dashboard
     * @param smartDashboardDisplay Boolean on whether or not to display smartDashboard values
     * @param limelight The limelight on the robot, lets limelight function be used in shooter class
     */
    public void shoot (boolean smartDashboardDisplay, Limelight limelight) {

        double targetDistance = limelight.updateLimelightVariables(smartDashboardDisplay);
        double rpm = shooterRanges(targetDistance);
        setPIDCoefficients(smartDashboardDisplay, 0.00012, 0.0006, 0.0, 0.005, rpm);
        updatePIDCoefficients(smartDashboardDisplay);
        SmartDashboard.putNumber("RPM", leftShooterEncoder.getVelocity());

    }

    /**
     * Method to actually shoot the shooter using PID values during the autonomous period
     * @param smartDashboardDisplay Boolean on whether or not to display smartDashboard values
     * @param limelight The limelight on the robot, lets limelight function be used in shooter class
     */
    public boolean autoShoot (boolean smartDashboardDisplay, Limelight limelight) {

        double targetDistance = limelight.updateLimelightVariables(smartDashboardDisplay);
        double rpm = shooterRanges(targetDistance);
        setPIDCoefficients(smartDashboardDisplay, 0.00012, 0.0006, 0.0, 0.005, rpm);
        updatePIDCoefficients(smartDashboardDisplay);
        SmartDashboard.putNumber("RPM", leftShooterEncoder.getVelocity());

        if (leftShooterEncoder.getVelocity() == rpm) {

            return true;

        }

        else {

            return false;

        }
    }

    /**
     * Stops shooter motors
     */
    public void stop () {

        rightShooterMotor.set(0);
        leftShooterMotor.set(0);

    }

    /**
     * Resets shooter encoders
     */
    public void resetEncoders () {

        rightShooterEncoder.setPosition(0);
        leftShooterEncoder.setPosition(0);

    }

    public void shooterTest() {

        rightShooterMotor.set(.2);

    }

    public void shooterTest2() {

        leftShooterMotor.set(.2);

    }
}
