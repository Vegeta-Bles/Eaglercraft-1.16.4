package net.minecraft.recipe.book;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.mojang.datafixers.util.Pair;
import java.util.Map;
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
      this(Util.make(Maps.newEnumMap(RecipeBookCategory.class), _snowman -> {
         for (RecipeBookCategory _snowmanx : RecipeBookCategory.values()) {
            _snowman.put(_snowmanx, new RecipeBookOptions.CategoryOption(false, false));
         }
      }));
   }

   public boolean isGuiOpen(RecipeBookCategory category) {
      return this.categoryOptions.get(category).guiOpen;
   }

   public void setGuiOpen(RecipeBookCategory category, boolean open) {
      this.categoryOptions.get(category).guiOpen = open;
   }

   public boolean isFilteringCraftable(RecipeBookCategory category) {
      return this.categoryOptions.get(category).filteringCraftable;
   }

   public void setFilteringCraftable(RecipeBookCategory category, boolean filtering) {
      this.categoryOptions.get(category).filteringCraftable = filtering;
   }

   public static RecipeBookOptions fromPacket(PacketByteBuf buf) {
      Map<RecipeBookCategory, RecipeBookOptions.CategoryOption> _snowman = Maps.newEnumMap(RecipeBookCategory.class);

      for (RecipeBookCategory _snowmanx : RecipeBookCategory.values()) {
         boolean _snowmanxx = buf.readBoolean();
         boolean _snowmanxxx = buf.readBoolean();
         _snowman.put(_snowmanx, new RecipeBookOptions.CategoryOption(_snowmanxx, _snowmanxxx));
      }

      return new RecipeBookOptions(_snowman);
   }

   public void toPacket(PacketByteBuf buf) {
      for (RecipeBookCategory _snowman : RecipeBookCategory.values()) {
         RecipeBookOptions.CategoryOption _snowmanx = this.categoryOptions.get(_snowman);
         if (_snowmanx == null) {
            buf.writeBoolean(false);
            buf.writeBoolean(false);
         } else {
            buf.writeBoolean(_snowmanx.guiOpen);
            buf.writeBoolean(_snowmanx.filteringCraftable);
         }
      }
   }

   public static RecipeBookOptions fromTag(CompoundTag tag) {
      Map<RecipeBookCategory, RecipeBookOptions.CategoryOption> _snowman = Maps.newEnumMap(RecipeBookCategory.class);
      CATEGORY_OPTION_NAMES.forEach((_snowmanxx, _snowmanxxx) -> {
         boolean _snowmanx = tag.getBoolean((String)_snowmanxxx.getFirst());
         boolean _snowmanx = tag.getBoolean((String)_snowmanxxx.getSecond());
         _snowman.put(_snowmanxx, new RecipeBookOptions.CategoryOption(_snowmanx, _snowmanx));
      });
      return new RecipeBookOptions(_snowman);
   }

   public void toTag(CompoundTag tag) {
      CATEGORY_OPTION_NAMES.forEach((_snowmanx, _snowmanxx) -> {
         RecipeBookOptions.CategoryOption _snowmanxxx = this.categoryOptions.get(_snowmanx);
         tag.putBoolean((String)_snowmanxx.getFirst(), _snowmanxxx.guiOpen);
         tag.putBoolean((String)_snowmanxx.getSecond(), _snowmanxxx.filteringCraftable);
      });
   }

   public RecipeBookOptions copy() {
      Map<RecipeBookCategory, RecipeBookOptions.CategoryOption> _snowman = Maps.newEnumMap(RecipeBookCategory.class);

      for (RecipeBookCategory _snowmanx : RecipeBookCategory.values()) {
         RecipeBookOptions.CategoryOption _snowmanxx = this.categoryOptions.get(_snowmanx);
         _snowman.put(_snowmanx, _snowmanxx.copy());
      }

      return new RecipeBookOptions(_snowman);
   }

   public void copyFrom(RecipeBookOptions other) {
      this.categoryOptions.clear();

      for (RecipeBookCategory _snowman : RecipeBookCategory.values()) {
         RecipeBookOptions.CategoryOption _snowmanx = other.categoryOptions.get(_snowman);
         this.categoryOptions.put(_snowman, _snowmanx.copy());
      }
   }

   @Override
   public boolean equals(Object _snowman) {
      return this == _snowman || _snowman instanceof RecipeBookOptions && this.categoryOptions.equals(((RecipeBookOptions)_snowman).categoryOptions);
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
      public boolean equals(Object _snowman) {
         if (this == _snowman) {
            return true;
         } else if (!(_snowman instanceof RecipeBookOptions.CategoryOption)) {
            return false;
         } else {
            RecipeBookOptions.CategoryOption _snowmanx = (RecipeBookOptions.CategoryOption)_snowman;
            return this.guiOpen == _snowmanx.guiOpen && this.filteringCraftable == _snowmanx.filteringCraftable;
         }
      }

      @Override
      public int hashCode() {
         int _snowman = this.guiOpen ? 1 : 0;
         return 31 * _snowman + (this.filteringCraftable ? 1 : 0);
      }

      @Override
      public String toString() {
         return "[open=" + this.guiOpen + ", filtering=" + this.filteringCraftable + ']';
      }
   }
}
