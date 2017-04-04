package pl.potera.bmiapp.bmi;

public class BmiCalculator {
    private static final double MIN_MASS = 10;
    private static final double MAX_MASS = 250;
    private static final double CM_IN_M = 100;
    private static final double MIN_HEIGHT = 50f;
    private static final double MAX_HEIGHT = 250f;

    public boolean isMassValid(double mass) {
        return mass > MIN_MASS && mass < MAX_MASS;
    }

    public boolean isHeightValid(double height) {
        return height > MIN_HEIGHT && height < MAX_HEIGHT;
    }

    public BmiResult countBmi(double mass, double height) throws IllegalArgumentException{
        if (isMassValid(mass) && isHeightValid(height)){
            return new BmiResult(mass / (height / CM_IN_M) / (height / CM_IN_M));
        } else {
            throw new IllegalArgumentException("Invalid countBmi() arguments");
        }
    }


}
