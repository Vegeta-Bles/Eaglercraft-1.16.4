package net.minecraft.loot.function;

import com.google.common.collect.ImmutableSet;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import java.util.Locale;
import java.util.Set;
import net.minecraft.item.FilledMapItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.map.MapIcon;
import net.minecraft.item.map.MapState;
import net.minecraft.loot.condition.LootCondition;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextParameter;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.gen.feature.StructureFeature;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ExplorationMapLootFunction extends ConditionalLootFunction {
   private static final Logger LOGGER = LogManager.getLogger();
   public static final StructureFeature<?> field_25032 = StructureFeature.BURIED_TREASURE;
   public static final MapIcon.Type DEFAULT_DECORATION = MapIcon.Type.MANSION;
   private final StructureFeature<?> destination;
   private final MapIcon.Type decoration;
   private final byte zoom;
   private final int searchRadius;
   private final boolean skipExistingChunks;

   private ExplorationMapLootFunction(
      LootCondition[] conditions, StructureFeature<?> _snowman, MapIcon.Type decoration, byte zoom, int searchRadius, boolean skipExistingChunks
   ) {
      super(conditions);
      this.destination = _snowman;
      this.decoration = decoration;
      this.zoom = zoom;
      this.searchRadius = searchRadius;
      this.skipExistingChunks = skipExistingChunks;
   }

   @Override
   public LootFunctionType getType() {
      return LootFunctionTypes.EXPLORATION_MAP;
   }

   @Override
   public Set<LootContextParameter<?>> getRequiredParameters() {
      return ImmutableSet.of(LootContextParameters.ORIGIN);
   }

   @Override
   public ItemStack process(ItemStack stack, LootContext context) {
      if (stack.getItem() != Items.MAP) {
         return stack;
      } else {
         Vec3d _snowman = context.get(LootContextParameters.ORIGIN);
         if (_snowman != null) {
            ServerWorld _snowmanx = context.getWorld();
            BlockPos _snowmanxx = _snowmanx.locateStructure(this.destination, new BlockPos(_snowman), this.searchRadius, this.skipExistingChunks);
            if (_snowmanxx != null) {
               ItemStack _snowmanxxx = FilledMapItem.createMap(_snowmanx, _snowmanxx.getX(), _snowmanxx.getZ(), this.zoom, true, true);
               FilledMapItem.fillExplorationMap(_snowmanx, _snowmanxxx);
               MapState.addDecorationsTag(_snowmanxxx, _snowmanxx, "+", this.decoration);
               _snowmanxxx.setCustomName(new TranslatableText("filled_map." + this.destination.getName().toLowerCase(Locale.ROOT)));
               return _snowmanxxx;
            }
         }

         return stack;
      }
   }

   public static ExplorationMapLootFunction.Builder create() {
      return new ExplorationMapLootFunction.Builder();
   }

   public static class Builder extends ConditionalLootFunction.Builder<ExplorationMapLootFunction.Builder> {
      private StructureFeature<?> destination = ExplorationMapLootFunction.field_25032;
      private MapIcon.Type decoration = ExplorationMapLootFunction.DEFAULT_DECORATION;
      private byte zoom = 2;
      private int searchRadius = 50;
      private boolean skipExistingChunks = true;

      public Builder() {
      }

      protected ExplorationMapLootFunction.Builder getThisBuilder() {
         return this;
      }

      public ExplorationMapLootFunction.Builder withDestination(StructureFeature<?> _snowman) {
         this.destination = _snowman;
         return this;
      }

      public ExplorationMapLootFunction.Builder withDecoration(MapIcon.Type decoration) {
         this.decoration = decoration;
         return this;
      }

      public ExplorationMapLootFunction.Builder withZoom(byte zoom) {
         this.zoom = zoom;
         return this;
      }

      public ExplorationMapLootFunction.Builder withSkipExistingChunks(boolean skipExistingChunks) {
         this.skipExistingChunks = skipExistingChunks;
         return this;
      }

      @Override
      public LootFunction build() {
         return new ExplorationMapLootFunction(this.getConditions(), this.destination, this.decoration, this.zoom, this.searchRadius, this.skipExistingChunks);
      }
   }

   public static class Serializer extends ConditionalLootFunction.Serializer<ExplorationMapLootFunction> {
      public Serializer() {
      }

      public void toJson(JsonObject _snowman, ExplorationMapLootFunction _snowman, JsonSerializationContext _snowman) {
         super.toJson(_snowman, _snowman, _snowman);
         if (!_snowman.destination.equals(ExplorationMapLootFunction.field_25032)) {
            _snowman.add("destination", _snowman.serialize(_snowman.destination.getName()));
         }

         if (_snowman.decoration != ExplorationMapLootFunction.DEFAULT_DECORATION) {
            _snowman.add("decoration", _snowman.serialize(_snowman.decoration.toString().toLowerCase(Locale.ROOT)));
         }

         if (_snowman.zoom != 2) {
            _snowman.addProperty("zoom", _snowman.zoom);
         }

         if (_snowman.searchRadius != 50) {
            _snowman.addProperty("search_radius", _snowman.searchRadius);
         }

         if (!_snowman.skipExistingChunks) {
            _snowman.addProperty("skip_existing_chunks", _snowman.skipExistingChunks);
         }
      }

      public ExplorationMapLootFunction fromJson(JsonObject _snowman, JsonDeserializationContext _snowman, LootCondition[] _snowman) {
         StructureFeature<?> _snowmanxxx = method_29039(_snowman);
         String _snowmanxxxx = _snowman.has("decoration") ? JsonHelper.getString(_snowman, "decoration") : "mansion";
         MapIcon.Type _snowmanxxxxx = ExplorationMapLootFunction.DEFAULT_DECORATION;

         try {
            _snowmanxxxxx = MapIcon.Type.valueOf(_snowmanxxxx.toUpperCase(Locale.ROOT));
         } catch (IllegalArgumentException var10) {
            ExplorationMapLootFunction.LOGGER
               .error("Error while parsing loot table decoration entry. Found {}. Defaulting to " + ExplorationMapLootFunction.DEFAULT_DECORATION, _snowmanxxxx);
         }

         byte _snowmanxxxxxx = JsonHelper.getByte(_snowman, "zoom", (byte)2);
         int _snowmanxxxxxxx = JsonHelper.getInt(_snowman, "search_radius", 50);
         boolean _snowmanxxxxxxxx = JsonHelper.getBoolean(_snowman, "skip_existing_chunks", true);
         return new ExplorationMapLootFunction(_snowman, _snowmanxxx, _snowmanxxxxx, _snowmanxxxxxx, _snowmanxxxxxxx, _snowmanxxxxxxxx);
      }

      private static StructureFeature<?> method_29039(JsonObject _snowman) {
         if (_snowman.has("destination")) {
            String _snowmanx = JsonHelper.getString(_snowman, "destination");
            StructureFeature<?> _snowmanxx = (StructureFeature<?>)StructureFeature.STRUCTURES.get(_snowmanx.toLowerCase(Locale.ROOT));
            if (_snowmanxx != null) {
               return _snowmanxx;
            }
         }

         return ExplorationMapLootFunction.field_25032;
      }
   }
}
