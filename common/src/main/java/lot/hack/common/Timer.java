package lot.hack.common;

public class Timer {
    private long lastMS, lastNS;

    public long getLastMS() {
        return lastMS;
    }

    public long getLastNS() {
        return lastNS;
    }

    public double reachedS() {
        return reachedNS() / 1_000_000_000.0;
    }

    public long reachedMS() {
        return System.currentTimeMillis() - lastMS;
    }

    public long reachedNS() {
        return System.nanoTime() - lastNS;
    }

    public boolean reachedMS(long time) {
        return reachedMS() >= time;
    }

    public boolean reachedNS(long time) {
        return reachedNS() >= time;
    }

    public void reset() {
        lastNS = System.nanoTime();
        lastMS = System.currentTimeMillis();
    }
}
