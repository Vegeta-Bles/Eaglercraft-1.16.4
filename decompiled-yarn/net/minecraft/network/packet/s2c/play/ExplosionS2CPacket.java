package net.minecraft.network.packet.s2c.play;

import com.google.common.collect.Lists;
import java.io.IOException;
import java.util.List;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class ExplosionS2CPacket implements Packet<ClientPlayPacketListener> {
   private double x;
   private double y;
   private double z;
   private float radius;
   private List<BlockPos> affectedBlocks;
   private float playerVelocityX;
   private float playerVelocityY;
   private float playerVelocityZ;

   public ExplosionS2CPacket() {
   }

   public ExplosionS2CPacket(double x, double y, double z, float radius, List<BlockPos> affectedBlocks, Vec3d playerVelocity) {
      this.x = x;
      this.y = y;
      this.z = z;
      this.radius = radius;
      this.affectedBlocks = Lists.newArrayList(affectedBlocks);
      if (playerVelocity != null) {
         this.playerVelocityX = (float)playerVelocity.x;
         this.playerVelocityY = (float)playerVelocity.y;
         this.playerVelocityZ = (float)playerVelocity.z;
      }
   }

   @Override
   public void read(PacketByteBuf buf) throws IOException {
      this.x = (double)buf.readFloat();
      this.y = (double)buf.readFloat();
      this.z = (double)buf.readFloat();
      this.radius = buf.readFloat();
      int _snowman = buf.readInt();
      this.affectedBlocks = Lists.newArrayListWithCapacity(_snowman);
      int _snowmanx = MathHelper.floor(this.x);
      int _snowmanxx = MathHelper.floor(this.y);
      int _snowmanxxx = MathHelper.floor(this.z);

      for (int _snowmanxxxx = 0; _snowmanxxxx < _snowman; _snowmanxxxx++) {
         int _snowmanxxxxx = buf.readByte() + _snowmanx;
         int _snowmanxxxxxx = buf.readByte() + _snowmanxx;
         int _snowmanxxxxxxx = buf.readByte() + _snowmanxxx;
         this.affectedBlocks.add(new BlockPos(_snowmanxxxxx, _snowmanxxxxxx, _snowmanxxxxxxx));
      }

      this.playerVelocityX = buf.readFloat();
      this.playerVelocityY = buf.readFloat();
      this.playerVelocityZ = buf.readFloat();
   }

   @Override
   public void write(PacketByteBuf buf) throws IOException {
      buf.writeFloat((float)this.x);
      buf.writeFloat((float)this.y);
      buf.writeFloat((float)this.z);
      buf.writeFloat(this.radius);
      buf.writeInt(this.affectedBlocks.size());
      int _snowman = MathHelper.floor(this.x);
      int _snowmanx = MathHelper.floor(this.y);
      int _snowmanxx = MathHelper.floor(this.z);

      for (BlockPos _snowmanxxx : this.affectedBlocks) {
         int _snowmanxxxx = _snowmanxxx.getX() - _snowman;
         int _snowmanxxxxx = _snowmanxxx.getY() - _snowmanx;
         int _snowmanxxxxxx = _snowmanxxx.getZ() - _snowmanxx;
         buf.writeByte(_snowmanxxxx);
         buf.writeByte(_snowmanxxxxx);
         buf.writeByte(_snowmanxxxxxx);
      }

      buf.writeFloat(this.playerVelocityX);
      buf.writeFloat(this.playerVelocityY);
      buf.writeFloat(this.playerVelocityZ);
   }

   public void apply(ClientPlayPacketListener _snowman) {
      _snowman.onExplosion(this);
   }

   public float getPlayerVelocityX() {
      return this.playerVelocityX;
   }

   public float getPlayerVelocityY() {
      return this.playerVelocityY;
   }

   public float getPlayerVelocityZ() {
      return this.playerVelocityZ;
   }

   public double getX() {
      return this.x;
   }

   public double getY() {
      return this.y;
   }

   public double getZ() {
      return this.z;
   }

   public float getRadius() {
      return this.radius;
   }

   public List<BlockPos> getAffectedBlocks() {
      return this.affectedBlocks;
   }
}
