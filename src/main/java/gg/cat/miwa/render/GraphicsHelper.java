package gg.cat.miwa.render;

public class GraphicsHelper {
    public static float lerp(float partials, float prevValue, float value) {
        return prevValue + (value - prevValue) * partials;
    }

    public static float lerpAngle(float partials, float prevValue, float value) {
        return prevValue + (float) shortAngleDist(prevValue, value) * partials;
    }

    private static double shortAngleDist(double prevValue, double value) {
        double max = 360.0;
        double da = (value - prevValue) % max;
        return 2 * da % max - da;
    }
}
