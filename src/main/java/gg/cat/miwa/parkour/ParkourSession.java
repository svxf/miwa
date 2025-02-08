package gg.cat.miwa.parkour;

public interface ParkourSession {

    public ParkourSession onRecord();

    public ParkourSession onPlay();

    public ParkourSession onOverride();

    public void onClientTick();

    public void onRenderTick();

    public boolean isActive();

    public void cleanUp();
}