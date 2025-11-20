package net.minecraft.loot.context;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import java.util.function.Consumer;
import javax.annotation.Nullable;
import net.minecraft.util.Identifier;

public class LootContextTypes {
   private static final BiMap<Identifier, LootContextType> MAP = HashBiMap.create();
   public static final LootContextType EMPTY = register("empty", builder -> {
   });
   public static final LootContextType CHEST = register(
      "chest", builder -> builder.require(LootContextParameters.ORIGIN).allow(LootContextParameters.THIS_ENTITY)
   );
   public static final LootContextType COMMAND = register("command", _snowman -> _snowman.require(LootContextParameters.ORIGIN).allow(LootContextParameters.THIS_ENTITY));
   public static final LootContextType SELECTOR = register("selector", _snowman -> _snowman.require(LootContextParameters.ORIGIN).require(LootContextParameters.THIS_ENTITY));
   public static final LootContextType FISHING = register(
      "fishing", builder -> builder.require(LootContextParameters.ORIGIN).require(LootContextParameters.TOOL).allow(LootContextParameters.THIS_ENTITY)
   );
   public static final LootContextType ENTITY = register(
      "entity",
      builder -> builder.require(LootContextParameters.THIS_ENTITY)
            .require(LootContextParameters.ORIGIN)
            .require(LootContextParameters.DAMAGE_SOURCE)
            .allow(LootContextParameters.KILLER_ENTITY)
            .allow(LootContextParameters.DIRECT_KILLER_ENTITY)
            .allow(LootContextParameters.LAST_DAMAGE_PLAYER)
   );
   public static final LootContextType GIFT = register(
      "gift", builder -> builder.require(LootContextParameters.ORIGIN).require(LootContextParameters.THIS_ENTITY)
   );
   public static final LootContextType BARTER = register("barter", _snowman -> _snowman.require(LootContextParameters.THIS_ENTITY));
   public static final LootContextType ADVANCEMENT_REWARD = register(
      "advancement_reward", builder -> builder.require(LootContextParameters.THIS_ENTITY).require(LootContextParameters.ORIGIN)
   );
   public static final LootContextType ADVANCEMENT_ENTITY = register(
      "advancement_entity", _snowman -> _snowman.require(LootContextParameters.THIS_ENTITY).require(LootContextParameters.ORIGIN)
   );
   public static final LootContextType GENERIC = register(
      "generic",
      builder -> builder.require(LootContextParameters.THIS_ENTITY)
            .require(LootContextParameters.LAST_DAMAGE_PLAYER)
            .require(LootContextParameters.DAMAGE_SOURCE)
            .require(LootContextParameters.KILLER_ENTITY)
            .require(LootContextParameters.DIRECT_KILLER_ENTITY)
            .require(LootContextParameters.ORIGIN)
            .require(LootContextParameters.BLOCK_STATE)
            .require(LootContextParameters.BLOCK_ENTITY)
            .require(LootContextParameters.TOOL)
            .require(LootContextParameters.EXPLOSION_RADIUS)
   );
   public static final LootContextType BLOCK = register(
      "block",
      builder -> builder.require(LootContextParameters.BLOCK_STATE)
            .require(LootContextParameters.ORIGIN)
            .require(LootContextParameters.TOOL)
            .allow(LootContextParameters.THIS_ENTITY)
            .allow(LootContextParameters.BLOCK_ENTITY)
            .allow(LootContextParameters.EXPLOSION_RADIUS)
   );

   private static LootContextType register(String name, Consumer<LootContextType.Builder> type) {
      LootContextType.Builder _snowman = new LootContextType.Builder();
      type.accept(_snowman);
      LootContextType _snowmanx = _snowman.build();
      Identifier _snowmanxx = new Identifier(name);
      LootContextType _snowmanxxx = (LootContextType)MAP.put(_snowmanxx, _snowmanx);
      if (_snowmanxxx != null) {
         throw new IllegalStateException("Loot table parameter set " + _snowmanxx + " is already registered");
      } else {
         return _snowmanx;
      }
   }

   @Nullable
   public static LootContextType get(Identifier id) {
      return (LootContextType)MAP.get(id);
   }

   @Nullable
   public static Identifier getId(LootContextType type) {
      return (Identifier)MAP.inverse().get(type);
   }
}
