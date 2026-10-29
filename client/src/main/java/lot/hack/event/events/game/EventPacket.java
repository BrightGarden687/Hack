package lot.hack.event.events.game;

import lot.hack.event.CancellableEvent;
import net.minecraft.network.protocol.Packet;

public interface EventPacket {
    public class Send extends CancellableEvent {
        public static final Send INSTANCE = new Send();

        private Packet<?> packet;

        private Send() {}

        public Packet<?> getPacket() {
            return packet;
        }

        public void setPacket(Packet<?> packet) {
            this.packet = packet;
        }
    }
}
