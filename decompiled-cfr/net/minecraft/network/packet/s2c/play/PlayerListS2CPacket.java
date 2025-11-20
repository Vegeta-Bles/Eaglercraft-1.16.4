/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.base.MoreObjects
 *  com.google.common.collect.Lists
 *  com.mojang.authlib.GameProfile
 *  com.mojang.authlib.properties.Property
 *  javax.annotation.Nullable
 */
package net.minecraft.network.packet.s2c.play;

import com.google.common.base.MoreObjects;
import com.google.common.collect.Lists;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import java.io.IOException;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.world.GameMode;

public class PlayerListS2CPacket
implements Packet<ClientPlayPacketListener> {
    private Action action;
    private final List<Entry> entries = Lists.newArrayList();

    public PlayerListS2CPacket() {
    }

    public PlayerListS2CPacket(Action action, ServerPlayerEntity ... players) {
        this.action = action;
        for (ServerPlayerEntity serverPlayerEntity : players) {
            this.entries.add(new Entry(serverPlayerEntity.getGameProfile(), serverPlayerEntity.pingMilliseconds, serverPlayerEntity.interactionManager.getGameMode(), serverPlayerEntity.getPlayerListName()));
        }
    }

    public PlayerListS2CPacket(Action action, Iterable<ServerPlayerEntity> iterable) {
        this.action = action;
        for (ServerPlayerEntity serverPlayerEntity : iterable) {
            this.entries.add(new Entry(serverPlayerEntity.getGameProfile(), serverPlayerEntity.pingMilliseconds, serverPlayerEntity.interactionManager.getGameMode(), serverPlayerEntity.getPlayerListName()));
        }
    }

    @Override
    public void read(PacketByteBuf buf) throws IOException {
        this.action = buf.readEnumConstant(Action.class);
        int n = buf.readVarInt();
        for (_snowman = 0; _snowman < n; ++_snowman) {
            GameProfile gameProfile = null;
            int _snowman2 = 0;
            GameMode _snowman3 = null;
            Text _snowman4 = null;
            switch (this.action) {
                case ADD_PLAYER: {
                    gameProfile = new GameProfile(buf.readUuid(), buf.readString(16));
                    int n2 = buf.readVarInt();
                    for (_snowman = 0; _snowman < n2; ++_snowman) {
                        String string = buf.readString(Short.MAX_VALUE);
                        _snowman = buf.readString(Short.MAX_VALUE);
                        if (buf.readBoolean()) {
                            gameProfile.getProperties().put((Object)string, (Object)new Property(string, _snowman, buf.readString(Short.MAX_VALUE)));
                            continue;
                        }
                        gameProfile.getProperties().put((Object)string, (Object)new Property(string, _snowman));
                    }
                    _snowman3 = GameMode.byId(buf.readVarInt());
                    _snowman2 = buf.readVarInt();
                    if (!buf.readBoolean()) break;
                    _snowman4 = buf.readText();
                    break;
                }
                case UPDATE_GAME_MODE: {
                    gameProfile = new GameProfile(buf.readUuid(), null);
                    _snowman3 = GameMode.byId(buf.readVarInt());
                    break;
                }
                case UPDATE_LATENCY: {
                    gameProfile = new GameProfile(buf.readUuid(), null);
                    _snowman2 = buf.readVarInt();
                    break;
                }
                case UPDATE_DISPLAY_NAME: {
                    gameProfile = new GameProfile(buf.readUuid(), null);
                    if (!buf.readBoolean()) break;
                    _snowman4 = buf.readText();
                    break;
                }
                case REMOVE_PLAYER: {
                    gameProfile = new GameProfile(buf.readUuid(), null);
                }
            }
            this.entries.add(new Entry(gameProfile, _snowman2, _snowman3, _snowman4));
        }
    }

    @Override
    public void write(PacketByteBuf buf) throws IOException {
        buf.writeEnumConstant(this.action);
        buf.writeVarInt(this.entries.size());
        for (Entry entry2 : this.entries) {
            Entry entry2;
            switch (this.action) {
                case ADD_PLAYER: {
                    buf.writeUuid(entry2.getProfile().getId());
                    buf.writeString(entry2.getProfile().getName());
                    buf.writeVarInt(entry2.getProfile().getProperties().size());
                    for (Property property : entry2.getProfile().getProperties().values()) {
                        buf.writeString(property.getName());
                        buf.writeString(property.getValue());
                        if (property.hasSignature()) {
                            buf.writeBoolean(true);
                            buf.writeString(property.getSignature());
                            continue;
                        }
                        buf.writeBoolean(false);
                    }
                    buf.writeVarInt(entry2.getGameMode().getId());
                    buf.writeVarInt(entry2.getLatency());
                    if (entry2.getDisplayName() == null) {
                        buf.writeBoolean(false);
                        break;
                    }
                    buf.writeBoolean(true);
                    buf.writeText(entry2.getDisplayName());
                    break;
                }
                case UPDATE_GAME_MODE: {
                    buf.writeUuid(entry2.getProfile().getId());
                    buf.writeVarInt(entry2.getGameMode().getId());
                    break;
                }
                case UPDATE_LATENCY: {
                    buf.writeUuid(entry2.getProfile().getId());
                    buf.writeVarInt(entry2.getLatency());
                    break;
                }
                case UPDATE_DISPLAY_NAME: {
                    buf.writeUuid(entry2.getProfile().getId());
                    if (entry2.getDisplayName() == null) {
                        buf.writeBoolean(false);
                        break;
                    }
                    buf.writeBoolean(true);
                    buf.writeText(entry2.getDisplayName());
                    break;
                }
                case REMOVE_PLAYER: {
                    buf.writeUuid(entry2.getProfile().getId());
                }
            }
        }
    }

    @Override
    public void apply(ClientPlayPacketListener clientPlayPacketListener) {
        clientPlayPacketListener.onPlayerList(this);
    }

    public List<Entry> getEntries() {
        return this.entries;
    }

    public Action getAction() {
        return this.action;
    }

    public String toString() {
        return MoreObjects.toStringHelper((Object)this).add("action", (Object)this.action).add("entries", this.entries).toString();
    }

    public class Entry {
        private final int latency;
        private final GameMode gameMode;
        private final GameProfile profile;
        private final Text displayName;

        public Entry(GameProfile profile, int latency, @Nullable GameMode gameMode, @Nullable Text displayName) {
            this.profile = profile;
            this.latency = latency;
            this.gameMode = gameMode;
            this.displayName = displayName;
        }

        public GameProfile getProfile() {
            return this.profile;
        }

        public int getLatency() {
            return this.latency;
        }

        public GameMode getGameMode() {
            return this.gameMode;
        }

        @Nullable
        public Text getDisplayName() {
            return this.displayName;
        }

        public String toString() {
            return MoreObjects.toStringHelper((Object)this).add("latency", this.latency).add("gameMode", (Object)this.gameMode).add("profile", (Object)this.profile).add("displayName", this.displayName == null ? null : Text.Serializer.toJson(this.displayName)).toString();
        }
    }

    public static enum Action {
        ADD_PLAYER,
        UPDATE_GAME_MODE,
        UPDATE_LATENCY,
        UPDATE_DISPLAY_NAME,
        REMOVE_PLAYER;

    }
}

