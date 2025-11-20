package net.minecraft.structure.pool;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrays;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import net.minecraft.structure.StructureManager;
import net.minecraft.structure.processor.GravityStructureProcessor;
import net.minecraft.structure.processor.StructureProcessor;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Identifier;
import net.minecraft.util.StringIdentifiable;
import net.minecraft.util.Util;
import net.minecraft.util.dynamic.RegistryElementCodec;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.Heightmap;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class StructurePool {
   private static final Logger LOGGER = LogManager.getLogger();
   public static final Codec<StructurePool> CODEC = RecordCodecBuilder.create(
      _snowman -> _snowman.group(
               Identifier.CODEC.fieldOf("name").forGetter(StructurePool::getId),
               Identifier.CODEC.fieldOf("fallback").forGetter(StructurePool::getTerminatorsId),
               Codec.mapPair(StructurePoolElement.CODEC.fieldOf("element"), Codec.INT.fieldOf("weight"))
                  .codec()
                  .listOf()
                  .promotePartial(Util.method_29188("Pool element: ", LOGGER::error))
                  .fieldOf("elements")
                  .forGetter(_snowmanx -> _snowmanx.elementCounts)
            )
            .apply(_snowman, StructurePool::new)
   );
   public static final Codec<Supplier<StructurePool>> REGISTRY_CODEC = RegistryElementCodec.of(Registry.TEMPLATE_POOL_WORLDGEN, CODEC);
   private final Identifier id;
   private final List<Pair<StructurePoolElement, Integer>> elementCounts;
   private final List<StructurePoolElement> elements;
   private final Identifier terminatorsId;
   private int highestY = Integer.MIN_VALUE;

   public StructurePool(Identifier _snowman, Identifier _snowman, List<Pair<StructurePoolElement, Integer>> _snowman) {
      this.id = _snowman;
      this.elementCounts = _snowman;
      this.elements = Lists.newArrayList();

      for (Pair<StructurePoolElement, Integer> _snowmanxxx : _snowman) {
         StructurePoolElement _snowmanxxxx = (StructurePoolElement)_snowmanxxx.getFirst();

         for (int _snowmanxxxxx = 0; _snowmanxxxxx < _snowmanxxx.getSecond(); _snowmanxxxxx++) {
            this.elements.add(_snowmanxxxx);
         }
      }

      this.terminatorsId = _snowman;
   }

   public StructurePool(
      Identifier _snowman, Identifier _snowman, List<Pair<Function<StructurePool.Projection, ? extends StructurePoolElement>, Integer>> _snowman, StructurePool.Projection _snowman
   ) {
      this.id = _snowman;
      this.elementCounts = Lists.newArrayList();
      this.elements = Lists.newArrayList();

      for (Pair<Function<StructurePool.Projection, ? extends StructurePoolElement>, Integer> _snowmanxxxx : _snowman) {
         StructurePoolElement _snowmanxxxxx = (StructurePoolElement)((Function)_snowmanxxxx.getFirst()).apply(_snowman);
         this.elementCounts.add(Pair.of(_snowmanxxxxx, _snowmanxxxx.getSecond()));

         for (int _snowmanxxxxxx = 0; _snowmanxxxxxx < _snowmanxxxx.getSecond(); _snowmanxxxxxx++) {
            this.elements.add(_snowmanxxxxx);
         }
      }

      this.terminatorsId = _snowman;
   }

   public int getHighestY(StructureManager _snowman) {
      if (this.highestY == Integer.MIN_VALUE) {
         this.highestY = this.elements.stream().mapToInt(_snowmanxx -> _snowmanxx.getBoundingBox(_snowman, BlockPos.ORIGIN, BlockRotation.NONE).getBlockCountY()).max().orElse(0);
      }

      return this.highestY;
   }

   public Identifier getTerminatorsId() {
      return this.terminatorsId;
   }

   public StructurePoolElement getRandomElement(Random random) {
      return this.elements.get(random.nextInt(this.elements.size()));
   }

   public List<StructurePoolElement> getElementIndicesInRandomOrder(Random random) {
      return ImmutableList.copyOf(ObjectArrays.shuffle(this.elements.toArray(new StructurePoolElement[0]), random));
   }

   public Identifier getId() {
      return this.id;
   }

   public int getElementCount() {
      return this.elements.size();
   }

   public static enum Projection implements StringIdentifiable {
      TERRAIN_MATCHING("terrain_matching", ImmutableList.of(new GravityStructureProcessor(Heightmap.Type.WORLD_SURFACE_WG, -1))),
      RIGID("rigid", ImmutableList.of());

      public static final Codec<StructurePool.Projection> field_24956 = StringIdentifiable.createCodec(
         StructurePool.Projection::values, StructurePool.Projection::getById
      );
      private static final Map<String, StructurePool.Projection> PROJECTIONS_BY_ID = Arrays.stream(values())
         .collect(Collectors.toMap(StructurePool.Projection::getId, _snowman -> (StructurePool.Projection)_snowman));
      private final String id;
      private final ImmutableList<StructureProcessor> processors;

      private Projection(String var3, ImmutableList<StructureProcessor> var4) {
         this.id = _snowman;
         this.processors = _snowman;
      }

      public String getId() {
         return this.id;
      }

      public static StructurePool.Projection getById(String id) {
         return PROJECTIONS_BY_ID.get(id);
      }

      public ImmutableList<StructureProcessor> getProcessors() {
         return this.processors;
      }

      @Override
      public String asString() {
         return this.id;
      }
   }
}
