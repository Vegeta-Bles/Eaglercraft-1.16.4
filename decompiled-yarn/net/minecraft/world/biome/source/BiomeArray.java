package net.minecraft.world.biome.source;

import javax.annotation.Nullable;
import net.minecraft.util.collection.IndexedIterable;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.biome.Biome;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BiomeArray implements BiomeAccess.Storage {
   private static final Logger LOGGER = LogManager.getLogger();
   private static final int HORIZONTAL_SECTION_COUNT = (int)Math.round(Math.log(16.0) / Math.log(2.0)) - 2;
   private static final int VERTICAL_SECTION_COUNT = (int)Math.round(Math.log(256.0) / Math.log(2.0)) - 2;
   public static final int DEFAULT_LENGTH = 1 << HORIZONTAL_SECTION_COUNT + HORIZONTAL_SECTION_COUNT + VERTICAL_SECTION_COUNT;
   public static final int HORIZONTAL_BIT_MASK = (1 << HORIZONTAL_SECTION_COUNT) - 1;
   public static final int VERTICAL_BIT_MASK = (1 << VERTICAL_SECTION_COUNT) - 1;
   private final IndexedIterable<Biome> field_25831;
   private final Biome[] data;

   public BiomeArray(IndexedIterable<Biome> _snowman, Biome[] _snowman) {
      this.field_25831 = _snowman;
      this.data = _snowman;
   }

   private BiomeArray(IndexedIterable<Biome> _snowman) {
      this(_snowman, new Biome[DEFAULT_LENGTH]);
   }

   public BiomeArray(IndexedIterable<Biome> _snowman, int[] _snowman) {
      this(_snowman);

      for (int _snowmanxx = 0; _snowmanxx < this.data.length; _snowmanxx++) {
         int _snowmanxxx = _snowman[_snowmanxx];
         Biome _snowmanxxxx = _snowman.get(_snowmanxxx);
         if (_snowmanxxxx == null) {
            LOGGER.warn("Received invalid biome id: " + _snowmanxxx);
            this.data[_snowmanxx] = _snowman.get(0);
         } else {
            this.data[_snowmanxx] = _snowmanxxxx;
         }
      }
   }

   public BiomeArray(IndexedIterable<Biome> _snowman, ChunkPos _snowman, BiomeSource _snowman) {
      this(_snowman);
      int _snowmanxxx = _snowman.getStartX() >> 2;
      int _snowmanxxxx = _snowman.getStartZ() >> 2;

      for (int _snowmanxxxxx = 0; _snowmanxxxxx < this.data.length; _snowmanxxxxx++) {
         int _snowmanxxxxxx = _snowmanxxxxx & HORIZONTAL_BIT_MASK;
         int _snowmanxxxxxxx = _snowmanxxxxx >> HORIZONTAL_SECTION_COUNT + HORIZONTAL_SECTION_COUNT & VERTICAL_BIT_MASK;
         int _snowmanxxxxxxxx = _snowmanxxxxx >> HORIZONTAL_SECTION_COUNT & HORIZONTAL_BIT_MASK;
         this.data[_snowmanxxxxx] = _snowman.getBiomeForNoiseGen(_snowmanxxx + _snowmanxxxxxx, _snowmanxxxxxxx, _snowmanxxxx + _snowmanxxxxxxxx);
      }
   }

   public BiomeArray(IndexedIterable<Biome> _snowman, ChunkPos _snowman, BiomeSource _snowman, @Nullable int[] _snowman) {
      this(_snowman);
      int _snowmanxxxx = _snowman.getStartX() >> 2;
      int _snowmanxxxxx = _snowman.getStartZ() >> 2;
      if (_snowman != null) {
         for (int _snowmanxxxxxx = 0; _snowmanxxxxxx < _snowman.length; _snowmanxxxxxx++) {
            this.data[_snowmanxxxxxx] = _snowman.get(_snowman[_snowmanxxxxxx]);
            if (this.data[_snowmanxxxxxx] == null) {
               int _snowmanxxxxxxx = _snowmanxxxxxx & HORIZONTAL_BIT_MASK;
               int _snowmanxxxxxxxx = _snowmanxxxxxx >> HORIZONTAL_SECTION_COUNT + HORIZONTAL_SECTION_COUNT & VERTICAL_BIT_MASK;
               int _snowmanxxxxxxxxx = _snowmanxxxxxx >> HORIZONTAL_SECTION_COUNT & HORIZONTAL_BIT_MASK;
               this.data[_snowmanxxxxxx] = _snowman.getBiomeForNoiseGen(_snowmanxxxx + _snowmanxxxxxxx, _snowmanxxxxxxxx, _snowmanxxxxx + _snowmanxxxxxxxxx);
            }
         }
      } else {
         for (int _snowmanxxxxxxx = 0; _snowmanxxxxxxx < this.data.length; _snowmanxxxxxxx++) {
            int _snowmanxxxxxxxx = _snowmanxxxxxxx & HORIZONTAL_BIT_MASK;
            int _snowmanxxxxxxxxx = _snowmanxxxxxxx >> HORIZONTAL_SECTION_COUNT + HORIZONTAL_SECTION_COUNT & VERTICAL_BIT_MASK;
            int _snowmanxxxxxxxxxx = _snowmanxxxxxxx >> HORIZONTAL_SECTION_COUNT & HORIZONTAL_BIT_MASK;
            this.data[_snowmanxxxxxxx] = _snowman.getBiomeForNoiseGen(_snowmanxxxx + _snowmanxxxxxxxx, _snowmanxxxxxxxxx, _snowmanxxxxx + _snowmanxxxxxxxxxx);
         }
      }
   }

   public int[] toIntArray() {
      int[] _snowman = new int[this.data.length];

      for (int _snowmanx = 0; _snowmanx < this.data.length; _snowmanx++) {
         _snowman[_snowmanx] = this.field_25831.getRawId(this.data[_snowmanx]);
      }

      return _snowman;
   }

   @Override
   public Biome getBiomeForNoiseGen(int biomeX, int biomeY, int biomeZ) {
      int _snowman = biomeX & HORIZONTAL_BIT_MASK;
      int _snowmanx = MathHelper.clamp(biomeY, 0, VERTICAL_BIT_MASK);
      int _snowmanxx = biomeZ & HORIZONTAL_BIT_MASK;
      return this.data[_snowmanx << HORIZONTAL_SECTION_COUNT + HORIZONTAL_SECTION_COUNT | _snowmanxx << HORIZONTAL_SECTION_COUNT | _snowman];
   }
}
