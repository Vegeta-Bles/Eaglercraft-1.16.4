package net.minecraft.client.render.block;

import com.google.common.collect.Maps;
import java.util.Map;
import java.util.Map.Entry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.BakedModelManager;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.state.property.Property;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class BlockModels {
   private final Map<BlockState, BakedModel> models = Maps.newIdentityHashMap();
   private final BakedModelManager modelManager;

   public BlockModels(BakedModelManager _snowman) {
      this.modelManager = _snowman;
   }

   public Sprite getSprite(BlockState _snowman) {
      return this.getModel(_snowman).getSprite();
   }

   public BakedModel getModel(BlockState _snowman) {
      BakedModel _snowmanx = this.models.get(_snowman);
      if (_snowmanx == null) {
         _snowmanx = this.modelManager.getMissingModel();
      }

      return _snowmanx;
   }

   public BakedModelManager getModelManager() {
      return this.modelManager;
   }

   public void reload() {
      this.models.clear();

      for (Block _snowman : Registry.BLOCK) {
         _snowman.getStateManager().getStates().forEach(_snowmanx -> {
            BakedModel var10000 = this.models.put(_snowmanx, this.modelManager.getModel(getModelId(_snowmanx)));
         });
      }
   }

   public static ModelIdentifier getModelId(BlockState _snowman) {
      return getModelId(Registry.BLOCK.getId(_snowman.getBlock()), _snowman);
   }

   public static ModelIdentifier getModelId(Identifier _snowman, BlockState _snowman) {
      return new ModelIdentifier(_snowman, propertyMapToString(_snowman.getEntries()));
   }

   public static String propertyMapToString(Map<Property<?>, Comparable<?>> _snowman) {
      StringBuilder _snowmanx = new StringBuilder();

      for (Entry<Property<?>, Comparable<?>> _snowmanxx : _snowman.entrySet()) {
         if (_snowmanx.length() != 0) {
            _snowmanx.append(',');
         }

         Property<?> _snowmanxxx = _snowmanxx.getKey();
         _snowmanx.append(_snowmanxxx.getName());
         _snowmanx.append('=');
         _snowmanx.append(propertyValueToString(_snowmanxxx, _snowmanxx.getValue()));
      }

      return _snowmanx.toString();
   }

   private static <T extends Comparable<T>> String propertyValueToString(Property<T> _snowman, Comparable<?> _snowman) {
      return _snowman.name((T)_snowman);
   }
}
