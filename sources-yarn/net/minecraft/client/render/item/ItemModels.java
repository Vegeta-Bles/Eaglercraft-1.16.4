package net.minecraft.client.render.item;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import java.util.Map.Entry;
import javax.annotation.Nullable;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.BakedModelManager;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;

@Environment(EnvType.CLIENT)
public class ItemModels {
   public final Int2ObjectMap<ModelIdentifier> modelIds = new Int2ObjectOpenHashMap(256);
   private final Int2ObjectMap<BakedModel> models = new Int2ObjectOpenHashMap(256);
   private final BakedModelManager modelManager;

   public ItemModels(BakedModelManager modelManager) {
      this.modelManager = modelManager;
   }

   public Sprite getSprite(ItemConvertible arg) {
      return this.getSprite(new ItemStack(arg));
   }

   public Sprite getSprite(ItemStack stack) {
      BakedModel lv = this.getModel(stack);
      return lv == this.modelManager.getMissingModel() && stack.getItem() instanceof BlockItem
         ? this.modelManager.getBlockModels().getSprite(((BlockItem)stack.getItem()).getBlock().getDefaultState())
         : lv.getSprite();
   }

   public BakedModel getModel(ItemStack stack) {
      BakedModel lv = this.getModel(stack.getItem());
      return lv == null ? this.modelManager.getMissingModel() : lv;
   }

   @Nullable
   public BakedModel getModel(Item arg) {
      return (BakedModel)this.models.get(getModelId(arg));
   }

   private static int getModelId(Item arg) {
      return Item.getRawId(arg);
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
         Entry<Integer, ModelIdentifier> entry = (Entry<Integer, ModelIdentifier>)var1.next();
         this.models.put(entry.getKey(), this.modelManager.getModel(entry.getValue()));
      }
   }
}
