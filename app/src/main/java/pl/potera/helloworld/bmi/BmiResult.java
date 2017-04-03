package pl.potera.helloworld.bmi;

import java.text.DecimalFormat;

import butterknife.ButterKnife;
import lombok.Getter;
import lombok.Setter;;

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
        System.out.println("############## COLOR: "+color);
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

    @Override
    public String toString() {
        DecimalFormat df = new DecimalFormat("#.##");
        return df.format(bmi);
    }
}
