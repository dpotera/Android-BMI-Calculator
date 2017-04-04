package pl.potera.bmiapp.bmi;

import java.text.DecimalFormat;

import lombok.Getter;
import lombok.Setter;

public class BmiResult {
    private static double UNDERWEIGHT = 18.5f;
    private static double OVERWEIGHT = 25f;
    private static double OBESITY = 30f;

    @Getter
    private double bmi;
    @Getter
    private BmiStatus status;
    @Getter
    @Setter
    private int color;

    public BmiResult(double bmi) {
        this.bmi = bmi;
        this.status = rateBmi(bmi);
    }

    private static BmiStatus rateBmi(double bmi){
        if(bmi < UNDERWEIGHT){
            return BmiStatus.UNDERWEIGHT;
        } else if(bmi >= UNDERWEIGHT && bmi < OVERWEIGHT){
            return BmiStatus.NORMAL;
        } else if(bmi >= OVERWEIGHT && bmi < OBESITY){
            return BmiStatus.OVERWEIGHT;
        } else {
            return BmiStatus.OBESITY;
        }
    }

    public String getResultMessage(){
        return "My BMI is: "+toString()+". "+getStatusMessage();
    }

    private String getStatusMessage(){
        switch (status){
            case NORMAL: return "I'm normal! :)";
            case UNDERWEIGHT: return "I'm thin :|";
            case OVERWEIGHT: return "I need diet :|";
            case OBESITY: return "I'm very fat! :(";
            default: return "";
        }
    }

    @Override
    public String toString() {
        DecimalFormat df = new DecimalFormat("#.##");
        return df.format(bmi);
    }
}
