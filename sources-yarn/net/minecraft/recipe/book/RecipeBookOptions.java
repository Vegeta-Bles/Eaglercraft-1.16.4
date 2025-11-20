package net.minecraft.recipe.book;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.mojang.datafixers.util.Pair;
import java.util.Map;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Util;

public final class RecipeBookOptions {
   private static final Map<RecipeBookCategory, Pair<String, String>> CATEGORY_OPTION_NAMES = ImmutableMap.of(
      RecipeBookCategory.CRAFTING,
      Pair.of("isGuiOpen", "isFilteringCraftable"),
      RecipeBookCategory.FURNACE,
      Pair.of("isFurnaceGuiOpen", "isFurnaceFilteringCraftable"),
      RecipeBookCategory.BLAST_FURNACE,
      Pair.of("isBlastingFurnaceGuiOpen", "isBlastingFurnaceFilteringCraftable"),
      RecipeBookCategory.SMOKER,
      Pair.of("isSmokerGuiOpen", "isSmokerFilteringCraftable")
   );
   private final Map<RecipeBookCategory, RecipeBookOptions.CategoryOption> categoryOptions;

   private RecipeBookOptions(Map<RecipeBookCategory, RecipeBookOptions.CategoryOption> categoryOptions) {
      this.categoryOptions = categoryOptions;
   }

   public RecipeBookOptions() {
      this(Util.make(Maps.newEnumMap(RecipeBookCategory.class), enumMap -> {
         for (RecipeBookCategory lv : RecipeBookCategory.values()) {
            enumMap.put(lv, new RecipeBookOptions.CategoryOption(false, false));
         }
      }));
   }

   @Environment(EnvType.CLIENT)
   public boolean isGuiOpen(RecipeBookCategory category) {
      return this.categoryOptions.get(category).guiOpen;
   }

   public void setGuiOpen(RecipeBookCategory category, boolean open) {
      this.categoryOptions.get(category).guiOpen = open;
   }

   @Environment(EnvType.CLIENT)
   public boolean isFilteringCraftable(RecipeBookCategory category) {
      return this.categoryOptions.get(category).filteringCraftable;
   }

   public void setFilteringCraftable(RecipeBookCategory category, boolean filtering) {
      this.categoryOptions.get(category).filteringCraftable = filtering;
   }

   public static RecipeBookOptions fromPacket(PacketByteBuf buf) {
      Map<RecipeBookCategory, RecipeBookOptions.CategoryOption> map = Maps.newEnumMap(RecipeBookCategory.class);

      for (RecipeBookCategory lv : RecipeBookCategory.values()) {
         boolean bl = buf.readBoolean();
         boolean bl2 = buf.readBoolean();
         map.put(lv, new RecipeBookOptions.CategoryOption(bl, bl2));
      }

      return new RecipeBookOptions(map);
   }

   public void toPacket(PacketByteBuf buf) {
      for (RecipeBookCategory lv : RecipeBookCategory.values()) {
         RecipeBookOptions.CategoryOption lv2 = this.categoryOptions.get(lv);
         if (lv2 == null) {
            buf.writeBoolean(false);
            buf.writeBoolean(false);
         } else {
            buf.writeBoolean(lv2.guiOpen);
            buf.writeBoolean(lv2.filteringCraftable);
         }
      }
   }

   public static RecipeBookOptions fromTag(CompoundTag tag) {
      Map<RecipeBookCategory, RecipeBookOptions.CategoryOption> map = Maps.newEnumMap(RecipeBookCategory.class);
      CATEGORY_OPTION_NAMES.forEach((arg2, pair) -> {
         boolean bl = tag.getBoolean((String)pair.getFirst());
         boolean bl2 = tag.getBoolean((String)pair.getSecond());
         map.put(arg2, new RecipeBookOptions.CategoryOption(bl, bl2));
      });
      return new RecipeBookOptions(map);
   }

   public void toTag(CompoundTag tag) {
      CATEGORY_OPTION_NAMES.forEach((arg2, pair) -> {
         RecipeBookOptions.CategoryOption lv = this.categoryOptions.get(arg2);
         tag.putBoolean((String)pair.getFirst(), lv.guiOpen);
         tag.putBoolean((String)pair.getSecond(), lv.filteringCraftable);
      });
   }

   public RecipeBookOptions copy() {
      Map<RecipeBookCategory, RecipeBookOptions.CategoryOption> map = Maps.newEnumMap(RecipeBookCategory.class);

      for (RecipeBookCategory lv : RecipeBookCategory.values()) {
         RecipeBookOptions.CategoryOption lv2 = this.categoryOptions.get(lv);
         map.put(lv, lv2.copy());
      }

      return new RecipeBookOptions(map);
   }

   public void copyFrom(RecipeBookOptions other) {
      this.categoryOptions.clear();

      for (RecipeBookCategory lv : RecipeBookCategory.values()) {
         RecipeBookOptions.CategoryOption lv2 = other.categoryOptions.get(lv);
         this.categoryOptions.put(lv, lv2.copy());
      }
   }

   @Override
   public boolean equals(Object object) {
      return this == object || object instanceof RecipeBookOptions && this.categoryOptions.equals(((RecipeBookOptions)object).categoryOptions);
   }

   @Override
   public int hashCode() {
      return this.categoryOptions.hashCode();
   }

   static final class CategoryOption {
      private boolean guiOpen;
      private boolean filteringCraftable;

      public CategoryOption(boolean guiOpen, boolean filteringCraftable) {
         this.guiOpen = guiOpen;
         this.filteringCraftable = filteringCraftable;
      }

      public RecipeBookOptions.CategoryOption copy() {
         return new RecipeBookOptions.CategoryOption(this.guiOpen, this.filteringCraftable);
      }

      @Override
      public boolean equals(Object object) {
         if (this == object) {
            return true;
         } else if (!(object instanceof RecipeBookOptions.CategoryOption)) {
            return false;
         } else {
            RecipeBookOptions.CategoryOption lv = (RecipeBookOptions.CategoryOption)object;
            return this.guiOpen == lv.guiOpen && this.filteringCraftable == lv.filteringCraftable;
         }
      }

      @Override
      public int hashCode() {
         int i = this.guiOpen ? 1 : 0;
         return 31 * i + (this.filteringCraftable ? 1 : 0);
      }

      @Override
      public String toString() {
         return "[open=" + this.guiOpen + ", filtering=" + this.filteringCraftable + ']';
      }
   }
}
