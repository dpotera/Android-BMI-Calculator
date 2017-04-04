package pl.potera.bmiapp.utils;

public class PoundsConverter {
    private static final double CONVERT_KG = 0.45359237f;

    public static double toKg(double pounds){
        return pounds * CONVERT_KG;
    }
}
