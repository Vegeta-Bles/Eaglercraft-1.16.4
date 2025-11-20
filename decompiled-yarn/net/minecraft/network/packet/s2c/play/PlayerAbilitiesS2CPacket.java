package net.minecraft.network.packet.s2c.play;

import java.io.IOException;
import net.minecraft.entity.player.PlayerAbilities;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.listener.ClientPlayPacketListener;

public class PlayerAbilitiesS2CPacket implements Packet<ClientPlayPacketListener> {
   private boolean invulnerable;
   private boolean flying;
   private boolean allowFlying;
   private boolean creativeMode;
   private float flySpeed;
   private float walkSpeed;

   public PlayerAbilitiesS2CPacket() {
   }

   public PlayerAbilitiesS2CPacket(PlayerAbilities _snowman) {
      this.invulnerable = _snowman.invulnerable;
      this.flying = _snowman.flying;
      this.allowFlying = _snowman.allowFlying;
      this.creativeMode = _snowman.creativeMode;
      this.flySpeed = _snowman.getFlySpeed();
      this.walkSpeed = _snowman.getWalkSpeed();
   }

   @Override
   public void read(PacketByteBuf buf) throws IOException {
      byte _snowman = buf.readByte();
      this.invulnerable = (_snowman & 1) != 0;
      this.flying = (_snowman & 2) != 0;
      this.allowFlying = (_snowman & 4) != 0;
      this.creativeMode = (_snowman & 8) != 0;
      this.flySpeed = buf.readFloat();
      this.walkSpeed = buf.readFloat();
   }

   @Override
   public void write(PacketByteBuf buf) throws IOException {
      byte _snowman = 0;
      if (this.invulnerable) {
         _snowman = (byte)(_snowman | 1);
      }

      if (this.flying) {
         _snowman = (byte)(_snowman | 2);
      }

      if (this.allowFlying) {
         _snowman = (byte)(_snowman | 4);
      }

      if (this.creativeMode) {
         _snowman = (byte)(_snowman | 8);
      }

      buf.writeByte(_snowman);
      buf.writeFloat(this.flySpeed);
      buf.writeFloat(this.walkSpeed);
   }

   public void apply(ClientPlayPacketListener _snowman) {
      _snowman.onPlayerAbilities(this);
   }

   public boolean isInvulnerable() {
      return this.invulnerable;
   }

   public boolean isFlying() {
      return this.flying;
   }

   public boolean allowFlying() {
      return this.allowFlying;
   }

   public boolean isCreativeMode() {
      return this.creativeMode;
   }

   public float getFlySpeed() {
      return this.flySpeed;
   }

   public float getWalkSpeed() {
      return this.walkSpeed;
   }
}
