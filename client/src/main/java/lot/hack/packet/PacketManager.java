package lot.hack.packet;

import java.util.ArrayList;
import java.util.List;

import lot.hack.common.Initializable;
import lot.hack.utils.MCInstances;
import net.minecraft.network.protocol.Packet;

public class PacketManager implements Initializable, MCInstances {
    public static final PacketManager INSTANCE = new PacketManager();

    private final List<TimePacket> packets = new ArrayList<>();

    public void handlePacket(Packet<?> packet) {
        packets.add(new TimePacket(packet, System.currentTimeMillis()));
    }

    public boolean should() {
        return mc.getConnection() != null && mc.getConnection().getConnection() != null;
    }

    public void update() {
        packets.removeIf(TP -> {
            if (TP.reachedMS() > 1000) {
                connection().send(TP.packet(), null, false);
                return true;
            }

            return false;
        });
    }

    public void reset() {
        packets.clear();
    }
}
