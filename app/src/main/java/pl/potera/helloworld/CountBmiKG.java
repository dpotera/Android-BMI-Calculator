package pl.potera.helloworld;

/**
 * Created by dominik on 15.03.17.
 */

public class CountBmiKG implements CountBmi {
    private static final float MIN_MASS = 10;
    private static final float MAX_MASS = 250;
    private static final float MIN_HEIGHT = 0.5f;
    private static final float MAX_HEIGHT = 2.5f;

    public CountBmiKG() {
    }

    @Override
    public boolean isMassValid(float mass) {
        return mass > MIN_MASS && mass < MAX_MASS;
    }

    @Override
    public boolean isHeightValid(float height) {
        return height > MIN_HEIGHT && height < MAX_HEIGHT;
    }

    @Override
    public float countBmi(float mass, float height) throws IllegalArgumentException{
        if (isMassValid(mass) && isHeightValid(height)){
            return mass / (height * height);
        } else {
            throw new IllegalArgumentException("Invalid countBmi() arguments");
        }
    }
}
