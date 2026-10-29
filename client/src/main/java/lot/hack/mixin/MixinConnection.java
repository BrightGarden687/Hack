package lot.hack.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import io.netty.channel.ChannelFutureListener;
import lot.hack.event.events.game.EventPacket;
import lot.hack.packet.PacketManager;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.Connection;

@Mixin(Connection.class)
public class MixinConnection {
    @Inject(
        method = "send(Lnet/minecraft/network/protocol/Packet;Lio/netty/channel/ChannelFutureListener;)V", 
        at = @At("HEAD"), 
        cancellable = true
    )
    private void handlePacket(Packet<?> packet, ChannelFutureListener listener, CallbackInfo ci) {
        EventPacket.Send.INSTANCE.setPacket(packet);
        EventPacket.Send.INSTANCE.call();

        if (PacketManager.INSTANCE.should()) {
            if (!EventPacket.Send.INSTANCE.isCancelled())
            PacketManager.INSTANCE.handlePacket(packet);

            ci.cancel();
        } else if (EventPacket.Send.INSTANCE.isCancelled()) {
            ci.cancel();
        }
    }
}
