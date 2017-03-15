package pl.potera.helloworld;

/**
 * Created by dominik on 15.03.17.
 */

public interface CountBmi {
    boolean isMassValid(float mass);
    boolean isHeightValid(float height);
    float countBmi(float mass, float height);
}
