package net.minecraft.client.render.model;

import it.unimi.dsi.fastutil.objects.Object2IntMap;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.block.BlockState;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.render.block.BlockModels;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.texture.TextureManager;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.fluid.FluidState;
import net.minecraft.resource.ResourceManager;
import net.minecraft.resource.SinglePreparationResourceReloadListener;
import net.minecraft.util.Identifier;
import net.minecraft.util.profiler.Profiler;

public class BakedModelManager extends SinglePreparationResourceReloadListener<ModelLoader> implements AutoCloseable {
   private Map<Identifier, BakedModel> models;
   @Nullable
   private SpriteAtlasManager atlasManager;
   private final BlockModels blockModelCache;
   private final TextureManager textureManager;
   private final BlockColors colorMap;
   private int mipmap;
   private BakedModel missingModel;
   private Object2IntMap<BlockState> stateLookup;

   public BakedModelManager(TextureManager textureManager, BlockColors colorMap, int mipmap) {
      this.textureManager = textureManager;
      this.colorMap = colorMap;
      this.mipmap = mipmap;
      this.blockModelCache = new BlockModels(this);
   }

   public BakedModel getModel(ModelIdentifier id) {
      return this.models.getOrDefault(id, this.missingModel);
   }

   public BakedModel getMissingModel() {
      return this.missingModel;
   }

   public BlockModels getBlockModels() {
      return this.blockModelCache;
   }

   protected ModelLoader prepare(ResourceManager _snowman, Profiler _snowman) {
      _snowman.startTick();
      ModelLoader _snowmanxx = new ModelLoader(_snowman, this.colorMap, _snowman, this.mipmap);
      _snowman.endTick();
      return _snowmanxx;
   }

   protected void apply(ModelLoader _snowman, ResourceManager _snowman, Profiler _snowman) {
      _snowman.startTick();
      _snowman.push("upload");
      if (this.atlasManager != null) {
         this.atlasManager.close();
      }

      this.atlasManager = _snowman.upload(this.textureManager, _snowman);
      this.models = _snowman.getBakedModelMap();
      this.stateLookup = _snowman.getStateLookup();
      this.missingModel = this.models.get(ModelLoader.MISSING);
      _snowman.swap("cache");
      this.blockModelCache.reload();
      _snowman.pop();
      _snowman.endTick();
   }

   public boolean shouldRerender(BlockState from, BlockState to) {
      if (from == to) {
         return false;
      } else {
         int _snowman = this.stateLookup.getInt(from);
         if (_snowman != -1) {
            int _snowmanx = this.stateLookup.getInt(to);
            if (_snowman == _snowmanx) {
               FluidState _snowmanxx = from.getFluidState();
               FluidState _snowmanxxx = to.getFluidState();
               return _snowmanxx != _snowmanxxx;
            }
         }

         return true;
      }
   }

   public SpriteAtlasTexture method_24153(Identifier _snowman) {
      return this.atlasManager.getAtlas(_snowman);
   }

   @Override
   public void close() {
      if (this.atlasManager != null) {
         this.atlasManager.close();
      }
   }

   public void resetMipmapLevels(int _snowman) {
      this.mipmap = _snowman;
   }
}
