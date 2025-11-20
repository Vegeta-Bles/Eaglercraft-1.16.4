package net.minecraft.client.render.model;

import com.google.common.collect.Lists;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenCustomHashMap;
import java.util.BitSet;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.block.BlockState;
import net.minecraft.client.render.model.json.ModelOverrideList;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.texture.Sprite;
import net.minecraft.util.Util;
import net.minecraft.util.math.Direction;
import org.apache.commons.lang3.tuple.Pair;

public class MultipartBakedModel implements BakedModel {
   private final List<Pair<Predicate<BlockState>, BakedModel>> components;
   protected final boolean ambientOcclusion;
   protected final boolean depthGui;
   protected final boolean sideLit;
   protected final Sprite sprite;
   protected final ModelTransformation transformations;
   protected final ModelOverrideList itemPropertyOverrides;
   private final Map<BlockState, BitSet> stateCache = new Object2ObjectOpenCustomHashMap(Util.identityHashStrategy());

   public MultipartBakedModel(List<Pair<Predicate<BlockState>, BakedModel>> components) {
      this.components = components;
      BakedModel _snowman = (BakedModel)components.iterator().next().getRight();
      this.ambientOcclusion = _snowman.useAmbientOcclusion();
      this.depthGui = _snowman.hasDepth();
      this.sideLit = _snowman.isSideLit();
      this.sprite = _snowman.getSprite();
      this.transformations = _snowman.getTransformation();
      this.itemPropertyOverrides = _snowman.getOverrides();
   }

   @Override
   public List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction face, Random random) {
      if (state == null) {
         return Collections.emptyList();
      } else {
         BitSet _snowman = this.stateCache.get(state);
         if (_snowman == null) {
            _snowman = new BitSet();

            for (int _snowmanx = 0; _snowmanx < this.components.size(); _snowmanx++) {
               Pair<Predicate<BlockState>, BakedModel> _snowmanxx = this.components.get(_snowmanx);
               if (((Predicate)_snowmanxx.getLeft()).test(state)) {
                  _snowman.set(_snowmanx);
               }
            }

            this.stateCache.put(state, _snowman);
         }

         List<BakedQuad> _snowmanxx = Lists.newArrayList();
         long _snowmanxxx = random.nextLong();

         for (int _snowmanxxxx = 0; _snowmanxxxx < _snowman.length(); _snowmanxxxx++) {
            if (_snowman.get(_snowmanxxxx)) {
               _snowmanxx.addAll(((BakedModel)this.components.get(_snowmanxxxx).getRight()).getQuads(state, face, new Random(_snowmanxxx)));
            }
         }

         return _snowmanxx;
      }
   }

   @Override
   public boolean useAmbientOcclusion() {
      return this.ambientOcclusion;
   }

   @Override
   public boolean hasDepth() {
      return this.depthGui;
   }

   @Override
   public boolean isSideLit() {
      return this.sideLit;
   }

   @Override
   public boolean isBuiltin() {
      return false;
   }

   @Override
   public Sprite getSprite() {
      return this.sprite;
   }

   @Override
   public ModelTransformation getTransformation() {
      return this.transformations;
   }

   @Override
   public ModelOverrideList getOverrides() {
      return this.itemPropertyOverrides;
   }

   public static class Builder {
      private final List<Pair<Predicate<BlockState>, BakedModel>> components = Lists.newArrayList();

      public Builder() {
      }

      public void addComponent(Predicate<BlockState> predicate, BakedModel model) {
         this.components.add(Pair.of(predicate, model));
      }

      public BakedModel build() {
         return new MultipartBakedModel(this.components);
      }
   }
}
