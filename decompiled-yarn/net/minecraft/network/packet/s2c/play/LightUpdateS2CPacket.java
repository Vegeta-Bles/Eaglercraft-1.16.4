package net.minecraft.network.packet.s2c.play;

import com.google.common.collect.Lists;
import java.io.IOException;
import java.util.List;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.ChunkSectionPos;
import net.minecraft.world.LightType;
import net.minecraft.world.chunk.ChunkNibbleArray;
import net.minecraft.world.chunk.light.LightingProvider;

public class LightUpdateS2CPacket implements Packet<ClientPlayPacketListener> {
   private int chunkX;
   private int chunkZ;
   private int skyLightMask;
   private int blockLightMask;
   private int filledSkyLightMask;
   private int filledBlockLightMask;
   private List<byte[]> skyLightUpdates;
   private List<byte[]> blockLightUpdates;
   private boolean field_25659;

   public LightUpdateS2CPacket() {
   }

   public LightUpdateS2CPacket(ChunkPos _snowman, LightingProvider _snowman, boolean _snowman) {
      this.chunkX = _snowman.x;
      this.chunkZ = _snowman.z;
      this.field_25659 = _snowman;
      this.skyLightUpdates = Lists.newArrayList();
      this.blockLightUpdates = Lists.newArrayList();

      for (int _snowmanxxx = 0; _snowmanxxx < 18; _snowmanxxx++) {
         ChunkNibbleArray _snowmanxxxx = _snowman.get(LightType.SKY).getLightSection(ChunkSectionPos.from(_snowman, -1 + _snowmanxxx));
         ChunkNibbleArray _snowmanxxxxx = _snowman.get(LightType.BLOCK).getLightSection(ChunkSectionPos.from(_snowman, -1 + _snowmanxxx));
         if (_snowmanxxxx != null) {
            if (_snowmanxxxx.isUninitialized()) {
               this.filledSkyLightMask |= 1 << _snowmanxxx;
            } else {
               this.skyLightMask |= 1 << _snowmanxxx;
               this.skyLightUpdates.add((byte[])_snowmanxxxx.asByteArray().clone());
            }
         }

         if (_snowmanxxxxx != null) {
            if (_snowmanxxxxx.isUninitialized()) {
               this.filledBlockLightMask |= 1 << _snowmanxxx;
            } else {
               this.blockLightMask |= 1 << _snowmanxxx;
               this.blockLightUpdates.add((byte[])_snowmanxxxxx.asByteArray().clone());
            }
         }
      }
   }

   public LightUpdateS2CPacket(ChunkPos pos, LightingProvider lightProvider, int skyLightMask, int blockLightMask, boolean _snowman) {
      this.chunkX = pos.x;
      this.chunkZ = pos.z;
      this.field_25659 = _snowman;
      this.skyLightMask = skyLightMask;
      this.blockLightMask = blockLightMask;
      this.skyLightUpdates = Lists.newArrayList();
      this.blockLightUpdates = Lists.newArrayList();

      for (int _snowmanx = 0; _snowmanx < 18; _snowmanx++) {
         if ((this.skyLightMask & 1 << _snowmanx) != 0) {
            ChunkNibbleArray _snowmanxx = lightProvider.get(LightType.SKY).getLightSection(ChunkSectionPos.from(pos, -1 + _snowmanx));
            if (_snowmanxx != null && !_snowmanxx.isUninitialized()) {
               this.skyLightUpdates.add((byte[])_snowmanxx.asByteArray().clone());
            } else {
               this.skyLightMask &= ~(1 << _snowmanx);
               if (_snowmanxx != null) {
                  this.filledSkyLightMask |= 1 << _snowmanx;
               }
            }
         }

         if ((this.blockLightMask & 1 << _snowmanx) != 0) {
            ChunkNibbleArray _snowmanxx = lightProvider.get(LightType.BLOCK).getLightSection(ChunkSectionPos.from(pos, -1 + _snowmanx));
            if (_snowmanxx != null && !_snowmanxx.isUninitialized()) {
               this.blockLightUpdates.add((byte[])_snowmanxx.asByteArray().clone());
            } else {
               this.blockLightMask &= ~(1 << _snowmanx);
               if (_snowmanxx != null) {
                  this.filledBlockLightMask |= 1 << _snowmanx;
               }
            }
         }
      }
   }

   @Override
   public void read(PacketByteBuf buf) throws IOException {
      this.chunkX = buf.readVarInt();
      this.chunkZ = buf.readVarInt();
      this.field_25659 = buf.readBoolean();
      this.skyLightMask = buf.readVarInt();
      this.blockLightMask = buf.readVarInt();
      this.filledSkyLightMask = buf.readVarInt();
      this.filledBlockLightMask = buf.readVarInt();
      this.skyLightUpdates = Lists.newArrayList();

      for (int _snowman = 0; _snowman < 18; _snowman++) {
         if ((this.skyLightMask & 1 << _snowman) != 0) {
            this.skyLightUpdates.add(buf.readByteArray(2048));
         }
      }

      this.blockLightUpdates = Lists.newArrayList();

      for (int _snowmanx = 0; _snowmanx < 18; _snowmanx++) {
         if ((this.blockLightMask & 1 << _snowmanx) != 0) {
            this.blockLightUpdates.add(buf.readByteArray(2048));
         }
      }
   }

   @Override
   public void write(PacketByteBuf buf) throws IOException {
      buf.writeVarInt(this.chunkX);
      buf.writeVarInt(this.chunkZ);
      buf.writeBoolean(this.field_25659);
      buf.writeVarInt(this.skyLightMask);
      buf.writeVarInt(this.blockLightMask);
      buf.writeVarInt(this.filledSkyLightMask);
      buf.writeVarInt(this.filledBlockLightMask);

      for (byte[] _snowman : this.skyLightUpdates) {
         buf.writeByteArray(_snowman);
      }

      for (byte[] _snowman : this.blockLightUpdates) {
         buf.writeByteArray(_snowman);
      }
   }

   public void apply(ClientPlayPacketListener _snowman) {
      _snowman.onLightUpdate(this);
   }

   public int getChunkX() {
      return this.chunkX;
   }

   public int getChunkZ() {
      return this.chunkZ;
   }

   public int getSkyLightMask() {
      return this.skyLightMask;
   }

   public int getFilledSkyLightMask() {
      return this.filledSkyLightMask;
   }

   public List<byte[]> getSkyLightUpdates() {
      return this.skyLightUpdates;
   }

   public int getBlockLightMask() {
      return this.blockLightMask;
   }

   public int getFilledBlockLightMask() {
      return this.filledBlockLightMask;
   }

   public List<byte[]> getBlockLightUpdates() {
      return this.blockLightUpdates;
   }

   public boolean method_30006() {
      return this.field_25659;
   }
}
