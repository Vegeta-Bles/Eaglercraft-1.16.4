package net.minecraft.network.packet.s2c.play;

import java.io.IOException;
import java.util.UUID;
import net.minecraft.entity.boss.BossBar;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.text.Text;

public class BossBarS2CPacket implements Packet<ClientPlayPacketListener> {
   private UUID uuid;
   private BossBarS2CPacket.Type type;
   private Text name;
   private float percent;
   private BossBar.Color color;
   private BossBar.Style overlay;
   private boolean darkenSky;
   private boolean dragonMusic;
   private boolean thickenFog;

   public BossBarS2CPacket() {
   }

   public BossBarS2CPacket(BossBarS2CPacket.Type action, BossBar _snowman) {
      this.type = action;
      this.uuid = _snowman.getUuid();
      this.name = _snowman.getName();
      this.percent = _snowman.getPercent();
      this.color = _snowman.getColor();
      this.overlay = _snowman.getOverlay();
      this.darkenSky = _snowman.getDarkenSky();
      this.dragonMusic = _snowman.hasDragonMusic();
      this.thickenFog = _snowman.getThickenFog();
   }

   @Override
   public void read(PacketByteBuf buf) throws IOException {
      this.uuid = buf.readUuid();
      this.type = buf.readEnumConstant(BossBarS2CPacket.Type.class);
      switch (this.type) {
         case ADD:
            this.name = buf.readText();
            this.percent = buf.readFloat();
            this.color = buf.readEnumConstant(BossBar.Color.class);
            this.overlay = buf.readEnumConstant(BossBar.Style.class);
            this.setFlagBitfield(buf.readUnsignedByte());
         case REMOVE:
         default:
            break;
         case UPDATE_PCT:
            this.percent = buf.readFloat();
            break;
         case UPDATE_NAME:
            this.name = buf.readText();
            break;
         case UPDATE_STYLE:
            this.color = buf.readEnumConstant(BossBar.Color.class);
            this.overlay = buf.readEnumConstant(BossBar.Style.class);
            break;
         case UPDATE_PROPERTIES:
            this.setFlagBitfield(buf.readUnsignedByte());
      }
   }

   private void setFlagBitfield(int _snowman) {
      this.darkenSky = (_snowman & 1) > 0;
      this.dragonMusic = (_snowman & 2) > 0;
      this.thickenFog = (_snowman & 4) > 0;
   }

   @Override
   public void write(PacketByteBuf buf) throws IOException {
      buf.writeUuid(this.uuid);
      buf.writeEnumConstant(this.type);
      switch (this.type) {
         case ADD:
            buf.writeText(this.name);
            buf.writeFloat(this.percent);
            buf.writeEnumConstant(this.color);
            buf.writeEnumConstant(this.overlay);
            buf.writeByte(this.getFlagBitfield());
         case REMOVE:
         default:
            break;
         case UPDATE_PCT:
            buf.writeFloat(this.percent);
            break;
         case UPDATE_NAME:
            buf.writeText(this.name);
            break;
         case UPDATE_STYLE:
            buf.writeEnumConstant(this.color);
            buf.writeEnumConstant(this.overlay);
            break;
         case UPDATE_PROPERTIES:
            buf.writeByte(this.getFlagBitfield());
      }
   }

   private int getFlagBitfield() {
      int _snowman = 0;
      if (this.darkenSky) {
         _snowman |= 1;
      }

      if (this.dragonMusic) {
         _snowman |= 2;
      }

      if (this.thickenFog) {
         _snowman |= 4;
      }

      return _snowman;
   }

   public void apply(ClientPlayPacketListener _snowman) {
      _snowman.onBossBar(this);
   }

   public UUID getUuid() {
      return this.uuid;
   }

   public BossBarS2CPacket.Type getType() {
      return this.type;
   }

   public Text getName() {
      return this.name;
   }

   public float getPercent() {
      return this.percent;
   }

   public BossBar.Color getColor() {
      return this.color;
   }

   public BossBar.Style getOverlay() {
      return this.overlay;
   }

   public boolean shouldDarkenSky() {
      return this.darkenSky;
   }

   public boolean hasDragonMusic() {
      return this.dragonMusic;
   }

   public boolean shouldThickenFog() {
      return this.thickenFog;
   }

   public static enum Type {
      ADD,
      REMOVE,
      UPDATE_PCT,
      UPDATE_NAME,
      UPDATE_STYLE,
      UPDATE_PROPERTIES;

      private Type() {
      }
   }
}
