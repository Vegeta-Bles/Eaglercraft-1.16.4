package net.minecraft.command.argument;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.tree.ArgumentCommandNode;
import com.mojang.brigadier.tree.CommandNode;
import com.mojang.brigadier.tree.LiteralCommandNode;
import com.mojang.brigadier.tree.RootCommandNode;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.SharedConstants;
import net.minecraft.command.argument.serialize.ArgumentSerializer;
import net.minecraft.command.argument.serialize.ConstantArgumentSerializer;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ArgumentTypes {
   private static final Logger LOGGER = LogManager.getLogger();
   private static final Map<Class<?>, ArgumentTypes.Entry<?>> classMap = Maps.newHashMap();
   private static final Map<Identifier, ArgumentTypes.Entry<?>> idMap = Maps.newHashMap();

   public static <T extends ArgumentType<?>> void register(String id, Class<T> _snowman, ArgumentSerializer<T> _snowman) {
      Identifier _snowmanxx = new Identifier(id);
      if (classMap.containsKey(_snowman)) {
         throw new IllegalArgumentException("Class " + _snowman.getName() + " already has a serializer!");
      } else if (idMap.containsKey(_snowmanxx)) {
         throw new IllegalArgumentException("'" + _snowmanxx + "' is already a registered serializer!");
      } else {
         ArgumentTypes.Entry<T> _snowmanxxx = new ArgumentTypes.Entry<>(_snowman, _snowman, _snowmanxx);
         classMap.put(_snowman, _snowmanxxx);
         idMap.put(_snowmanxx, _snowmanxxx);
      }
   }

   public static void register() {
      BrigadierArgumentTypes.register();
      register("entity", EntityArgumentType.class, new EntityArgumentType.Serializer());
      register("game_profile", GameProfileArgumentType.class, new ConstantArgumentSerializer(GameProfileArgumentType::gameProfile));
      register("block_pos", BlockPosArgumentType.class, new ConstantArgumentSerializer(BlockPosArgumentType::blockPos));
      register("column_pos", ColumnPosArgumentType.class, new ConstantArgumentSerializer(ColumnPosArgumentType::columnPos));
      register("vec3", Vec3ArgumentType.class, new ConstantArgumentSerializer(Vec3ArgumentType::vec3));
      register("vec2", Vec2ArgumentType.class, new ConstantArgumentSerializer(Vec2ArgumentType::vec2));
      register("block_state", BlockStateArgumentType.class, new ConstantArgumentSerializer(BlockStateArgumentType::blockState));
      register("block_predicate", BlockPredicateArgumentType.class, new ConstantArgumentSerializer(BlockPredicateArgumentType::blockPredicate));
      register("item_stack", ItemStackArgumentType.class, new ConstantArgumentSerializer(ItemStackArgumentType::itemStack));
      register("item_predicate", ItemPredicateArgumentType.class, new ConstantArgumentSerializer(ItemPredicateArgumentType::itemPredicate));
      register("color", ColorArgumentType.class, new ConstantArgumentSerializer(ColorArgumentType::color));
      register("component", TextArgumentType.class, new ConstantArgumentSerializer(TextArgumentType::text));
      register("message", MessageArgumentType.class, new ConstantArgumentSerializer(MessageArgumentType::message));
      register("nbt_compound_tag", NbtCompoundTagArgumentType.class, new ConstantArgumentSerializer(NbtCompoundTagArgumentType::nbtCompound));
      register("nbt_tag", NbtTagArgumentType.class, new ConstantArgumentSerializer(NbtTagArgumentType::nbtTag));
      register("nbt_path", NbtPathArgumentType.class, new ConstantArgumentSerializer(NbtPathArgumentType::nbtPath));
      register("objective", ObjectiveArgumentType.class, new ConstantArgumentSerializer(ObjectiveArgumentType::objective));
      register("objective_criteria", ObjectiveCriteriaArgumentType.class, new ConstantArgumentSerializer(ObjectiveCriteriaArgumentType::objectiveCriteria));
      register("operation", OperationArgumentType.class, new ConstantArgumentSerializer(OperationArgumentType::operation));
      register("particle", ParticleArgumentType.class, new ConstantArgumentSerializer(ParticleArgumentType::particle));
      register("angle", AngleArgumentType.class, new ConstantArgumentSerializer(AngleArgumentType::angle));
      register("rotation", RotationArgumentType.class, new ConstantArgumentSerializer(RotationArgumentType::rotation));
      register("scoreboard_slot", ScoreboardSlotArgumentType.class, new ConstantArgumentSerializer(ScoreboardSlotArgumentType::scoreboardSlot));
      register("score_holder", ScoreHolderArgumentType.class, new ScoreHolderArgumentType.Serializer());
      register("swizzle", SwizzleArgumentType.class, new ConstantArgumentSerializer(SwizzleArgumentType::swizzle));
      register("team", TeamArgumentType.class, new ConstantArgumentSerializer(TeamArgumentType::team));
      register("item_slot", ItemSlotArgumentType.class, new ConstantArgumentSerializer(ItemSlotArgumentType::itemSlot));
      register("resource_location", IdentifierArgumentType.class, new ConstantArgumentSerializer(IdentifierArgumentType::identifier));
      register("mob_effect", MobEffectArgumentType.class, new ConstantArgumentSerializer(MobEffectArgumentType::mobEffect));
      register("function", FunctionArgumentType.class, new ConstantArgumentSerializer(FunctionArgumentType::function));
      register("entity_anchor", EntityAnchorArgumentType.class, new ConstantArgumentSerializer(EntityAnchorArgumentType::entityAnchor));
      register("int_range", NumberRangeArgumentType.IntRangeArgumentType.class, new ConstantArgumentSerializer(NumberRangeArgumentType::numberRange));
      register("float_range", NumberRangeArgumentType.FloatRangeArgumentType.class, new ConstantArgumentSerializer(NumberRangeArgumentType::method_30918));
      register("item_enchantment", ItemEnchantmentArgumentType.class, new ConstantArgumentSerializer(ItemEnchantmentArgumentType::itemEnchantment));
      register("entity_summon", EntitySummonArgumentType.class, new ConstantArgumentSerializer(EntitySummonArgumentType::entitySummon));
      register("dimension", DimensionArgumentType.class, new ConstantArgumentSerializer(DimensionArgumentType::dimension));
      register("time", TimeArgumentType.class, new ConstantArgumentSerializer(TimeArgumentType::time));
      register("uuid", UuidArgumentType.class, new ConstantArgumentSerializer(UuidArgumentType::uuid));
      if (SharedConstants.isDevelopment) {
         register("test_argument", TestFunctionArgumentType.class, new ConstantArgumentSerializer(TestFunctionArgumentType::testFunction));
         register("test_class", TestClassArgumentType.class, new ConstantArgumentSerializer(TestClassArgumentType::testClass));
      }
   }

   @Nullable
   private static ArgumentTypes.Entry<?> byId(Identifier _snowman) {
      return idMap.get(_snowman);
   }

   @Nullable
   private static ArgumentTypes.Entry<?> byClass(ArgumentType<?> _snowman) {
      return classMap.get(_snowman.getClass());
   }

   public static <T extends ArgumentType<?>> void toPacket(PacketByteBuf _snowman, T _snowman) {
      ArgumentTypes.Entry<T> _snowmanxx = (ArgumentTypes.Entry<T>)byClass(_snowman);
      if (_snowmanxx == null) {
         LOGGER.error("Could not serialize {} ({}) - will not be sent to client!", _snowman, _snowman.getClass());
         _snowman.writeIdentifier(new Identifier(""));
      } else {
         _snowman.writeIdentifier(_snowmanxx.id);
         _snowmanxx.serializer.toPacket(_snowman, _snowman);
      }
   }

   @Nullable
   public static ArgumentType<?> fromPacket(PacketByteBuf buf) {
      Identifier _snowman = buf.readIdentifier();
      ArgumentTypes.Entry<?> _snowmanx = byId(_snowman);
      if (_snowmanx == null) {
         LOGGER.error("Could not deserialize {}", _snowman);
         return null;
      } else {
         return _snowmanx.serializer.fromPacket(buf);
      }
   }

   private static <T extends ArgumentType<?>> void toJson(JsonObject _snowman, T _snowman) {
      ArgumentTypes.Entry<T> _snowmanxx = (ArgumentTypes.Entry<T>)byClass(_snowman);
      if (_snowmanxx == null) {
         LOGGER.error("Could not serialize argument {} ({})!", _snowman, _snowman.getClass());
         _snowman.addProperty("type", "unknown");
      } else {
         _snowman.addProperty("type", "argument");
         _snowman.addProperty("parser", _snowmanxx.id.toString());
         JsonObject _snowmanxxx = new JsonObject();
         _snowmanxx.serializer.toJson(_snowman, _snowmanxxx);
         if (_snowmanxxx.size() > 0) {
            _snowman.add("properties", _snowmanxxx);
         }
      }
   }

   public static <S> JsonObject toJson(CommandDispatcher<S> _snowman, CommandNode<S> _snowman) {
      JsonObject _snowmanxx = new JsonObject();
      if (_snowman instanceof RootCommandNode) {
         _snowmanxx.addProperty("type", "root");
      } else if (_snowman instanceof LiteralCommandNode) {
         _snowmanxx.addProperty("type", "literal");
      } else if (_snowman instanceof ArgumentCommandNode) {
         toJson(_snowmanxx, ((ArgumentCommandNode)_snowman).getType());
      } else {
         LOGGER.error("Could not serialize node {} ({})!", _snowman, _snowman.getClass());
         _snowmanxx.addProperty("type", "unknown");
      }

      JsonObject _snowmanxxx = new JsonObject();

      for (CommandNode<S> _snowmanxxxx : _snowman.getChildren()) {
         _snowmanxxx.add(_snowmanxxxx.getName(), toJson(_snowman, _snowmanxxxx));
      }

      if (_snowmanxxx.size() > 0) {
         _snowmanxx.add("children", _snowmanxxx);
      }

      if (_snowman.getCommand() != null) {
         _snowmanxx.addProperty("executable", true);
      }

      if (_snowman.getRedirect() != null) {
         Collection<String> _snowmanxxxx = _snowman.getPath(_snowman.getRedirect());
         if (!_snowmanxxxx.isEmpty()) {
            JsonArray _snowmanxxxxx = new JsonArray();

            for (String _snowmanxxxxxx : _snowmanxxxx) {
               _snowmanxxxxx.add(_snowmanxxxxxx);
            }

            _snowmanxx.add("redirect", _snowmanxxxxx);
         }
      }

      return _snowmanxx;
   }

   public static boolean hasClass(ArgumentType<?> _snowman) {
      return byClass(_snowman) != null;
   }

   public static <T> Set<ArgumentType<?>> getAllArgumentTypes(CommandNode<T> node) {
      Set<CommandNode<T>> _snowman = Sets.newIdentityHashSet();
      Set<ArgumentType<?>> _snowmanx = Sets.newHashSet();
      getAllArgumentTypes(node, _snowmanx, _snowman);
      return _snowmanx;
   }

   private static <T> void getAllArgumentTypes(CommandNode<T> node, Set<ArgumentType<?>> argumentTypes, Set<CommandNode<T>> ignoredNodes) {
      if (ignoredNodes.add(node)) {
         if (node instanceof ArgumentCommandNode) {
            argumentTypes.add(((ArgumentCommandNode)node).getType());
         }

         node.getChildren().forEach(nodex -> getAllArgumentTypes(nodex, argumentTypes, ignoredNodes));
         CommandNode<T> _snowman = node.getRedirect();
         if (_snowman != null) {
            getAllArgumentTypes(_snowman, argumentTypes, ignoredNodes);
         }
      }
   }

   static class Entry<T extends ArgumentType<?>> {
      public final Class<T> argClass;
      public final ArgumentSerializer<T> serializer;
      public final Identifier id;

      private Entry(Class<T> argumentClass, ArgumentSerializer<T> serializer, Identifier id) {
         this.argClass = argumentClass;
         this.serializer = serializer;
         this.id = id;
      }
   }
}
