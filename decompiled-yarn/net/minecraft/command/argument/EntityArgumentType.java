package net.minecraft.command.argument;

import com.google.common.collect.Iterables;
import com.google.gson.JsonObject;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import net.minecraft.command.CommandSource;
import net.minecraft.command.EntitySelector;
import net.minecraft.command.EntitySelectorReader;
import net.minecraft.command.argument.serialize.ArgumentSerializer;
import net.minecraft.entity.Entity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.TranslatableText;

public class EntityArgumentType implements ArgumentType<EntitySelector> {
   private static final Collection<String> EXAMPLES = Arrays.asList("Player", "0123", "@e", "@e[type=foo]", "dd12be42-52a9-4a91-a8a1-11c01849e498");
   public static final SimpleCommandExceptionType TOO_MANY_ENTITIES_EXCEPTION = new SimpleCommandExceptionType(new TranslatableText("argument.entity.toomany"));
   public static final SimpleCommandExceptionType TOO_MANY_PLAYERS_EXCEPTION = new SimpleCommandExceptionType(new TranslatableText("argument.player.toomany"));
   public static final SimpleCommandExceptionType PLAYER_SELECTOR_HAS_ENTITIES_EXCEPTION = new SimpleCommandExceptionType(
      new TranslatableText("argument.player.entities")
   );
   public static final SimpleCommandExceptionType ENTITY_NOT_FOUND_EXCEPTION = new SimpleCommandExceptionType(
      new TranslatableText("argument.entity.notfound.entity")
   );
   public static final SimpleCommandExceptionType PLAYER_NOT_FOUND_EXCEPTION = new SimpleCommandExceptionType(
      new TranslatableText("argument.entity.notfound.player")
   );
   public static final SimpleCommandExceptionType NOT_ALLOWED_EXCEPTION = new SimpleCommandExceptionType(
      new TranslatableText("argument.entity.selector.not_allowed")
   );
   private final boolean singleTarget;
   private final boolean playersOnly;

   protected EntityArgumentType(boolean singleTarget, boolean playersOnly) {
      this.singleTarget = singleTarget;
      this.playersOnly = playersOnly;
   }

   public static EntityArgumentType entity() {
      return new EntityArgumentType(true, false);
   }

   public static Entity getEntity(CommandContext<ServerCommandSource> context, String name) throws CommandSyntaxException {
      return ((EntitySelector)context.getArgument(name, EntitySelector.class)).getEntity((ServerCommandSource)context.getSource());
   }

   public static EntityArgumentType entities() {
      return new EntityArgumentType(false, false);
   }

   public static Collection<? extends Entity> getEntities(CommandContext<ServerCommandSource> context, String name) throws CommandSyntaxException {
      Collection<? extends Entity> _snowman = getOptionalEntities(context, name);
      if (_snowman.isEmpty()) {
         throw ENTITY_NOT_FOUND_EXCEPTION.create();
      } else {
         return _snowman;
      }
   }

   public static Collection<? extends Entity> getOptionalEntities(CommandContext<ServerCommandSource> _snowman, String _snowman) throws CommandSyntaxException {
      return ((EntitySelector)_snowman.getArgument(_snowman, EntitySelector.class)).getEntities((ServerCommandSource)_snowman.getSource());
   }

   public static Collection<ServerPlayerEntity> getOptionalPlayers(CommandContext<ServerCommandSource> _snowman, String _snowman) throws CommandSyntaxException {
      return ((EntitySelector)_snowman.getArgument(_snowman, EntitySelector.class)).getPlayers((ServerCommandSource)_snowman.getSource());
   }

   public static EntityArgumentType player() {
      return new EntityArgumentType(true, true);
   }

   public static ServerPlayerEntity getPlayer(CommandContext<ServerCommandSource> _snowman, String _snowman) throws CommandSyntaxException {
      return ((EntitySelector)_snowman.getArgument(_snowman, EntitySelector.class)).getPlayer((ServerCommandSource)_snowman.getSource());
   }

   public static EntityArgumentType players() {
      return new EntityArgumentType(false, true);
   }

   public static Collection<ServerPlayerEntity> getPlayers(CommandContext<ServerCommandSource> _snowman, String _snowman) throws CommandSyntaxException {
      List<ServerPlayerEntity> _snowmanxx = ((EntitySelector)_snowman.getArgument(_snowman, EntitySelector.class)).getPlayers((ServerCommandSource)_snowman.getSource());
      if (_snowmanxx.isEmpty()) {
         throw PLAYER_NOT_FOUND_EXCEPTION.create();
      } else {
         return _snowmanxx;
      }
   }

   public EntitySelector parse(StringReader _snowman) throws CommandSyntaxException {
      int _snowmanx = 0;
      EntitySelectorReader _snowmanxx = new EntitySelectorReader(_snowman);
      EntitySelector _snowmanxxx = _snowmanxx.read();
      if (_snowmanxxx.getLimit() > 1 && this.singleTarget) {
         if (this.playersOnly) {
            _snowman.setCursor(0);
            throw TOO_MANY_PLAYERS_EXCEPTION.createWithContext(_snowman);
         } else {
            _snowman.setCursor(0);
            throw TOO_MANY_ENTITIES_EXCEPTION.createWithContext(_snowman);
         }
      } else if (_snowmanxxx.includesNonPlayers() && this.playersOnly && !_snowmanxxx.isSenderOnly()) {
         _snowman.setCursor(0);
         throw PLAYER_SELECTOR_HAS_ENTITIES_EXCEPTION.createWithContext(_snowman);
      } else {
         return _snowmanxxx;
      }
   }

   public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> context, SuggestionsBuilder builder) {
      if (context.getSource() instanceof CommandSource) {
         StringReader _snowman = new StringReader(builder.getInput());
         _snowman.setCursor(builder.getStart());
         CommandSource _snowmanx = (CommandSource)context.getSource();
         EntitySelectorReader _snowmanxx = new EntitySelectorReader(_snowman, _snowmanx.hasPermissionLevel(2));

         try {
            _snowmanxx.read();
         } catch (CommandSyntaxException var7) {
         }

         return _snowmanxx.listSuggestions(builder, _snowmanxxx -> {
            Collection<String> _snowmanxxxx = _snowman.getPlayerNames();
            Iterable<String> _snowmanxx = (Iterable<String>)(this.playersOnly ? _snowmanxxxx : Iterables.concat(_snowmanxxxx, _snowman.getEntitySuggestions()));
            CommandSource.suggestMatching(_snowmanxx, _snowmanxxx);
         });
      } else {
         return Suggestions.empty();
      }
   }

   public Collection<String> getExamples() {
      return EXAMPLES;
   }

   public static class Serializer implements ArgumentSerializer<EntityArgumentType> {
      public Serializer() {
      }

      public void toPacket(EntityArgumentType _snowman, PacketByteBuf _snowman) {
         byte _snowmanxx = 0;
         if (_snowman.singleTarget) {
            _snowmanxx = (byte)(_snowmanxx | 1);
         }

         if (_snowman.playersOnly) {
            _snowmanxx = (byte)(_snowmanxx | 2);
         }

         _snowman.writeByte(_snowmanxx);
      }

      public EntityArgumentType fromPacket(PacketByteBuf _snowman) {
         byte _snowmanx = _snowman.readByte();
         return new EntityArgumentType((_snowmanx & 1) != 0, (_snowmanx & 2) != 0);
      }

      public void toJson(EntityArgumentType _snowman, JsonObject _snowman) {
         _snowman.addProperty("amount", _snowman.singleTarget ? "single" : "multiple");
         _snowman.addProperty("type", _snowman.playersOnly ? "players" : "entities");
      }
   }
}
