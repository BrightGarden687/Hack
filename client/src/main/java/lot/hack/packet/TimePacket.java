package lot.hack.packet;

import net.minecraft.network.protocol.Packet;

public record TimePacket(Packet<?> packet, long time) {
    public long reachedMS() {
        return System.currentTimeMillis() - time;
    }
}
