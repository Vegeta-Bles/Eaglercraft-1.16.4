package net.minecraft.world.level.storage;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.registry.DynamicRegistryManager;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.source.BiomeArray;
import net.minecraft.world.biome.source.BiomeSource;
import net.minecraft.world.chunk.ChunkNibbleArray;

public class AlphaChunkIo {
   public static AlphaChunkIo.AlphaChunk readAlphaChunk(CompoundTag tag) {
      int _snowman = tag.getInt("xPos");
      int _snowmanx = tag.getInt("zPos");
      AlphaChunkIo.AlphaChunk _snowmanxx = new AlphaChunkIo.AlphaChunk(_snowman, _snowmanx);
      _snowmanxx.blocks = tag.getByteArray("Blocks");
      _snowmanxx.data = new AlphaChunkDataArray(tag.getByteArray("Data"), 7);
      _snowmanxx.skyLight = new AlphaChunkDataArray(tag.getByteArray("SkyLight"), 7);
      _snowmanxx.blockLight = new AlphaChunkDataArray(tag.getByteArray("BlockLight"), 7);
      _snowmanxx.heightMap = tag.getByteArray("HeightMap");
      _snowmanxx.terrainPopulated = tag.getBoolean("TerrainPopulated");
      _snowmanxx.entities = tag.getList("Entities", 10);
      _snowmanxx.blockEntities = tag.getList("TileEntities", 10);
      _snowmanxx.blockTicks = tag.getList("TileTicks", 10);

      try {
         _snowmanxx.lastUpdate = tag.getLong("LastUpdate");
      } catch (ClassCastException var5) {
         _snowmanxx.lastUpdate = (long)tag.getInt("LastUpdate");
      }

      return _snowmanxx;
   }

   public static void convertAlphaChunk(DynamicRegistryManager.Impl _snowman, AlphaChunkIo.AlphaChunk _snowman, CompoundTag _snowman, BiomeSource _snowman) {
      _snowman.putInt("xPos", _snowman.x);
      _snowman.putInt("zPos", _snowman.z);
      _snowman.putLong("LastUpdate", _snowman.lastUpdate);
      int[] _snowmanxxxx = new int[_snowman.heightMap.length];

      for (int _snowmanxxxxx = 0; _snowmanxxxxx < _snowman.heightMap.length; _snowmanxxxxx++) {
         _snowmanxxxx[_snowmanxxxxx] = _snowman.heightMap[_snowmanxxxxx];
      }

      _snowman.putIntArray("HeightMap", _snowmanxxxx);
      _snowman.putBoolean("TerrainPopulated", _snowman.terrainPopulated);
      ListTag _snowmanxxxxx = new ListTag();

      for (int _snowmanxxxxxx = 0; _snowmanxxxxxx < 8; _snowmanxxxxxx++) {
         boolean _snowmanxxxxxxx = true;

         for (int _snowmanxxxxxxxx = 0; _snowmanxxxxxxxx < 16 && _snowmanxxxxxxx; _snowmanxxxxxxxx++) {
            for (int _snowmanxxxxxxxxx = 0; _snowmanxxxxxxxxx < 16 && _snowmanxxxxxxx; _snowmanxxxxxxxxx++) {
               for (int _snowmanxxxxxxxxxx = 0; _snowmanxxxxxxxxxx < 16; _snowmanxxxxxxxxxx++) {
                  int _snowmanxxxxxxxxxxx = _snowmanxxxxxxxx << 11 | _snowmanxxxxxxxxxx << 7 | _snowmanxxxxxxxxx + (_snowmanxxxxxx << 4);
                  int _snowmanxxxxxxxxxxxx = _snowman.blocks[_snowmanxxxxxxxxxxx];
                  if (_snowmanxxxxxxxxxxxx != 0) {
                     _snowmanxxxxxxx = false;
                     break;
                  }
               }
            }
         }

         if (!_snowmanxxxxxxx) {
            byte[] _snowmanxxxxxxxx = new byte[4096];
            ChunkNibbleArray _snowmanxxxxxxxxx = new ChunkNibbleArray();
            ChunkNibbleArray _snowmanxxxxxxxxxxx = new ChunkNibbleArray();
            ChunkNibbleArray _snowmanxxxxxxxxxxxx = new ChunkNibbleArray();

            for (int _snowmanxxxxxxxxxxxxx = 0; _snowmanxxxxxxxxxxxxx < 16; _snowmanxxxxxxxxxxxxx++) {
               for (int _snowmanxxxxxxxxxxxxxx = 0; _snowmanxxxxxxxxxxxxxx < 16; _snowmanxxxxxxxxxxxxxx++) {
                  for (int _snowmanxxxxxxxxxxxxxxx = 0; _snowmanxxxxxxxxxxxxxxx < 16; _snowmanxxxxxxxxxxxxxxx++) {
                     int _snowmanxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxx << 11 | _snowmanxxxxxxxxxxxxxxx << 7 | _snowmanxxxxxxxxxxxxxx + (_snowmanxxxxxx << 4);
                     int _snowmanxxxxxxxxxxxxxxxxx = _snowman.blocks[_snowmanxxxxxxxxxxxxxxxx];
                     _snowmanxxxxxxxx[_snowmanxxxxxxxxxxxxxx << 8 | _snowmanxxxxxxxxxxxxxxx << 4 | _snowmanxxxxxxxxxxxxx] = (byte)(_snowmanxxxxxxxxxxxxxxxxx & 0xFF);
                     _snowmanxxxxxxxxx.set(
                        _snowmanxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxx, _snowman.data.get(_snowmanxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxx + (_snowmanxxxxxx << 4), _snowmanxxxxxxxxxxxxxxx)
                     );
                     _snowmanxxxxxxxxxxx.set(
                        _snowmanxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxx, _snowman.skyLight.get(_snowmanxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxx + (_snowmanxxxxxx << 4), _snowmanxxxxxxxxxxxxxxx)
                     );
                     _snowmanxxxxxxxxxxxx.set(
                        _snowmanxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxx, _snowman.blockLight.get(_snowmanxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxx + (_snowmanxxxxxx << 4), _snowmanxxxxxxxxxxxxxxx)
                     );
                  }
               }
            }

            CompoundTag _snowmanxxxxxxxxxxxxx = new CompoundTag();
            _snowmanxxxxxxxxxxxxx.putByte("Y", (byte)(_snowmanxxxxxx & 0xFF));
            _snowmanxxxxxxxxxxxxx.putByteArray("Blocks", _snowmanxxxxxxxx);
            _snowmanxxxxxxxxxxxxx.putByteArray("Data", _snowmanxxxxxxxxx.asByteArray());
            _snowmanxxxxxxxxxxxxx.putByteArray("SkyLight", _snowmanxxxxxxxxxxx.asByteArray());
            _snowmanxxxxxxxxxxxxx.putByteArray("BlockLight", _snowmanxxxxxxxxxxxx.asByteArray());
            _snowmanxxxxx.add(_snowmanxxxxxxxxxxxxx);
         }
      }

      _snowman.put("Sections", _snowmanxxxxx);
      _snowman.putIntArray("Biomes", new BiomeArray(_snowman.get(Registry.BIOME_KEY), new ChunkPos(_snowman.x, _snowman.z), _snowman).toIntArray());
      _snowman.put("Entities", _snowman.entities);
      _snowman.put("TileEntities", _snowman.blockEntities);
      if (_snowman.blockTicks != null) {
         _snowman.put("TileTicks", _snowman.blockTicks);
      }

      _snowman.putBoolean("convertedFromAlphaFormat", true);
   }

   public static class AlphaChunk {
      public long lastUpdate;
      public boolean terrainPopulated;
      public byte[] heightMap;
      public AlphaChunkDataArray blockLight;
      public AlphaChunkDataArray skyLight;
      public AlphaChunkDataArray data;
      public byte[] blocks;
      public ListTag entities;
      public ListTag blockEntities;
      public ListTag blockTicks;
      public final int x;
      public final int z;

      public AlphaChunk(int x, int z) {
         this.x = x;
         this.z = z;
      }
   }
}
