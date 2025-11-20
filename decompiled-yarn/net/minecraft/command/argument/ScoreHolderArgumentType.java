package net.minecraft.command.argument;

import com.google.common.collect.Lists;
import com.google.gson.JsonObject;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;
import net.minecraft.command.CommandSource;
import net.minecraft.command.EntitySelector;
import net.minecraft.command.EntitySelectorReader;
import net.minecraft.command.argument.serialize.ArgumentSerializer;
import net.minecraft.entity.Entity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.TranslatableText;

public class ScoreHolderArgumentType implements ArgumentType<ScoreHolderArgumentType.ScoreHolder> {
   public static final SuggestionProvider<ServerCommandSource> SUGGESTION_PROVIDER = (_snowman, _snowmanx) -> {
      StringReader _snowmanxx = new StringReader(_snowmanx.getInput());
      _snowmanxx.setCursor(_snowmanx.getStart());
      EntitySelectorReader _snowmanxxx = new EntitySelectorReader(_snowmanxx);

      try {
         _snowmanxxx.read();
      } catch (CommandSyntaxException var5) {
      }

      return _snowmanxxx.listSuggestions(_snowmanx, _snowmanxxxx -> CommandSource.suggestMatching(((ServerCommandSource)_snowman.getSource()).getPlayerNames(), _snowmanxxxx));
   };
   private static final Collection<String> EXAMPLES = Arrays.asList("Player", "0123", "*", "@e");
   private static final SimpleCommandExceptionType EMPTY_SCORE_HOLDER_EXCEPTION = new SimpleCommandExceptionType(
      new TranslatableText("argument.scoreHolder.empty")
   );
   private final boolean multiple;

   public ScoreHolderArgumentType(boolean multiple) {
      this.multiple = multiple;
   }

   public static String getScoreHolder(CommandContext<ServerCommandSource> context, String name) throws CommandSyntaxException {
      return getScoreHolders(context, name).iterator().next();
   }

   public static Collection<String> getScoreHolders(CommandContext<ServerCommandSource> context, String name) throws CommandSyntaxException {
      return getScoreHolders(context, name, Collections::emptyList);
   }

   public static Collection<String> getScoreboardScoreHolders(CommandContext<ServerCommandSource> context, String name) throws CommandSyntaxException {
      return getScoreHolders(context, name, ((ServerCommandSource)context.getSource()).getMinecraftServer().getScoreboard()::getKnownPlayers);
   }

   public static Collection<String> getScoreHolders(CommandContext<ServerCommandSource> context, String name, Supplier<Collection<String>> players) throws CommandSyntaxException {
      Collection<String> _snowman = ((ScoreHolderArgumentType.ScoreHolder)context.getArgument(name, ScoreHolderArgumentType.ScoreHolder.class))
         .getNames((ServerCommandSource)context.getSource(), players);
      if (_snowman.isEmpty()) {
         throw EntityArgumentType.ENTITY_NOT_FOUND_EXCEPTION.create();
      } else {
         return _snowman;
      }
   }

   public static ScoreHolderArgumentType scoreHolder() {
      return new ScoreHolderArgumentType(false);
   }

   public static ScoreHolderArgumentType scoreHolders() {
      return new ScoreHolderArgumentType(true);
   }

   public ScoreHolderArgumentType.ScoreHolder parse(StringReader _snowman) throws CommandSyntaxException {
      if (_snowman.canRead() && _snowman.peek() == '@') {
         EntitySelectorReader _snowmanx = new EntitySelectorReader(_snowman);
         EntitySelector _snowmanxx = _snowmanx.read();
         if (!this.multiple && _snowmanxx.getLimit() > 1) {
            throw EntityArgumentType.TOO_MANY_ENTITIES_EXCEPTION.create();
         } else {
            return new ScoreHolderArgumentType.SelectorScoreHolder(_snowmanxx);
         }
      } else {
         int _snowmanx = _snowman.getCursor();

         while (_snowman.canRead() && _snowman.peek() != ' ') {
            _snowman.skip();
         }

         String _snowmanxx = _snowman.getString().substring(_snowmanx, _snowman.getCursor());
         if (_snowmanxx.equals("*")) {
            return (_snowmanxxx, _snowmanxxxx) -> {
               Collection<String> _snowmanxxxxx = (Collection<String>)_snowmanxxxx.get();
               if (_snowmanxxxxx.isEmpty()) {
                  throw EMPTY_SCORE_HOLDER_EXCEPTION.create();
               } else {
                  return _snowmanxxxxx;
               }
            };
         } else {
            Collection<String> _snowmanxxx = Collections.singleton(_snowmanxx);
            return (_snowmanxxxx, _snowmanxxxxx) -> _snowman;
         }
      }
   }

   public Collection<String> getExamples() {
      return EXAMPLES;
   }

   @FunctionalInterface
   public interface ScoreHolder {
      Collection<String> getNames(ServerCommandSource source, Supplier<Collection<String>> var2) throws CommandSyntaxException;
   }

   public static class SelectorScoreHolder implements ScoreHolderArgumentType.ScoreHolder {
      private final EntitySelector selector;

      public SelectorScoreHolder(EntitySelector _snowman) {
         this.selector = _snowman;
      }

      @Override
      public Collection<String> getNames(ServerCommandSource _snowman, Supplier<Collection<String>> _snowman) throws CommandSyntaxException {
         List<? extends Entity> _snowmanxx = this.selector.getEntities(_snowman);
         if (_snowmanxx.isEmpty()) {
            throw EntityArgumentType.ENTITY_NOT_FOUND_EXCEPTION.create();
         } else {
            List<String> _snowmanxxx = Lists.newArrayList();

            for (Entity _snowmanxxxx : _snowmanxx) {
               _snowmanxxx.add(_snowmanxxxx.getEntityName());
            }

            return _snowmanxxx;
         }
      }
   }

   public static class Serializer implements ArgumentSerializer<ScoreHolderArgumentType> {
      public Serializer() {
      }

      public void toPacket(ScoreHolderArgumentType _snowman, PacketByteBuf _snowman) {
         byte _snowmanxx = 0;
         if (_snowman.multiple) {
            _snowmanxx = (byte)(_snowmanxx | 1);
         }

         _snowman.writeByte(_snowmanxx);
      }

      public ScoreHolderArgumentType fromPacket(PacketByteBuf _snowman) {
         byte _snowmanx = _snowman.readByte();
         boolean _snowmanxx = (_snowmanx & 1) != 0;
         return new ScoreHolderArgumentType(_snowmanxx);
      }

      public void toJson(ScoreHolderArgumentType _snowman, JsonObject _snowman) {
         _snowman.addProperty("amount", _snowman.multiple ? "multiple" : "single");
      }
   }
}
