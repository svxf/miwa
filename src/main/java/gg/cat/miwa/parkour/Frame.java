package gg.cat.miwa.parkour;

public class Frame {
    public final boolean w, a, s, d, jump, sprint, sneak;
    public final float yaw, pitch;

    public Frame(boolean w, boolean a, boolean s, boolean d, boolean jump, boolean sprint, boolean sneak, float yaw, float pitch) {
        this.w = w;
        this.a = a;
        this.s = s;
        this.d = d;
        this.jump = jump;
        this.sprint = sprint;
        this.sneak = sneak;
        this.yaw = yaw;
        this.pitch = pitch;
    }
}