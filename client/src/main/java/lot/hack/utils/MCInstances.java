package lot.hack.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.Connection;

public interface MCInstances {
    public Minecraft mc = Minecraft.getInstance();

    default LocalPlayer localPlayer() {
        LocalPlayer localPlayer = mc.player;

        if (localPlayer == null) {
            throw new NullPointerException();
        }

        return localPlayer;
    }

    default ClientLevel clientLevel() {
        ClientLevel clientLevel = mc.level;

        if (clientLevel == null) {
            throw new NullPointerException();
        }

        return clientLevel;
    }

    default Connection connection() {
        ClientPacketListener connection = mc.getConnection();

        if (connection == null) {
            throw new NullPointerException();
        }

        return connection.getConnection();
    }

    default boolean isInGame() {
        return mc.level != null && mc.player != null;
    }
}
