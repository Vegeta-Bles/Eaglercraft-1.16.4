package net.minecraft.client.render.item;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import java.util.Map.Entry;
import javax.annotation.Nullable;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.BakedModelManager;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;

public class ItemModels {
   public final Int2ObjectMap<ModelIdentifier> modelIds = new Int2ObjectOpenHashMap(256);
   private final Int2ObjectMap<BakedModel> models = new Int2ObjectOpenHashMap(256);
   private final BakedModelManager modelManager;

   public ItemModels(BakedModelManager modelManager) {
      this.modelManager = modelManager;
   }

   public Sprite getSprite(ItemConvertible _snowman) {
      return this.getSprite(new ItemStack(_snowman));
   }

   public Sprite getSprite(ItemStack stack) {
      BakedModel _snowman = this.getModel(stack);
      return _snowman == this.modelManager.getMissingModel() && stack.getItem() instanceof BlockItem
         ? this.modelManager.getBlockModels().getSprite(((BlockItem)stack.getItem()).getBlock().getDefaultState())
         : _snowman.getSprite();
   }

   public BakedModel getModel(ItemStack stack) {
      BakedModel _snowman = this.getModel(stack.getItem());
      return _snowman == null ? this.modelManager.getMissingModel() : _snowman;
   }

   @Nullable
   public BakedModel getModel(Item _snowman) {
      return (BakedModel)this.models.get(getModelId(_snowman));
   }

   private static int getModelId(Item _snowman) {
      return Item.getRawId(_snowman);
   }

   public void putModel(Item item, ModelIdentifier modelId) {
      this.modelIds.put(getModelId(item), modelId);
   }

   public BakedModelManager getModelManager() {
      return this.modelManager;
   }

   public void reloadModels() {
      this.models.clear();
      ObjectIterator var1 = this.modelIds.entrySet().iterator();

      while (var1.hasNext()) {
         Entry<Integer, ModelIdentifier> _snowman = (Entry<Integer, ModelIdentifier>)var1.next();
         this.models.put(_snowman.getKey(), this.modelManager.getModel(_snowman.getValue()));
      }
   }
}
