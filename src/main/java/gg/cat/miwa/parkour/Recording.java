package gg.cat.miwa.parkour;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class Recording {
    private final List<Frame> frames;
    private static final Gson GSON = new Gson();

    public Recording(List<Frame> frames) {
        this.frames = new ArrayList<>(frames);
    }

    public List<Frame> getFrames() {
        return new ArrayList<>(frames);
    }

    public int size() {
        return frames.size();
    }

    public String toJson() {
        return GSON.toJson(this);
    }
}