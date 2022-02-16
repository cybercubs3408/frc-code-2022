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


    //Minimum distance in inches that the shooter can shoot from
    final static double xMin = minimumDistance;
    //Corrsponding RPM value with the minimum distance
    final static double yMin = minimumRPM;

    //First preset distance value in inches
    final static double x1 = firstDistance;
    //Corresponding RPM value for distance #1
    final static double y1 = firstRPM;

    //Second preset distance value in inches
    final static double x2 = secondDistance;
    //Corresponding RPM value for distance #2
    final static double y2 = secondRPM;

    //Maximum distance in inches that the shooter can shoot from
    final static double xMax = maximumDistance;
    //Corrseponding RPM value with that distance
    final static double yMax = maximumRPM;


    /**
     * Constructor method for the shooter class
     * @param kP Proportional gain 
     * @param kI Integral gain
     * @param kD Derivitive (rate of change of something at a point) gain
     * @param rightShooterID ID of right controller controlling shooter
     * @param leftShooterID ID of left controller controlling  shoort
     * @param Iz Integration zone of PID controller
     * @param FF Feedforward value of PID controller
     * @param maxOutput Maximum output of PID Controller
     * @param minOutput Minimum output of PID controller
     */
    public Shooter(double kP, double kI, double kD, int rightShooterID, int leftShooterID, double Iz, double FF, double maxOutput, double minOutput, double maxRPM) {

        PIDController pid = new PIDController(kP, kI, kD);
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
     * @return RPM to be input into set PID coefficients in shooter.shoot
     */
    public double shooterRanges(double targetDistance) {

        double rpm;

        if (targetDistance > xMin && targetDistance < x1) {

            rpm = xMin + (targetDistance - xMin) * (y1 - yMin) / (x1 - xMin);

        }

        else if (targetDistance > x1 && targetDistance < x2) {

            rpm = x1 + (targetDistance - x1) * (y2 - y1) / (x2 - x1);

        }

        else if (targetDistance > x2 && targetDistance < xMax) {

            rpm = x2 + (targetDistance - x2) * (yMax - y2) / (xMax - x2);

        }

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
}
