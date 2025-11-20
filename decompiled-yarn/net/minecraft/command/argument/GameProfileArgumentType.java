package net.minecraft.command.argument;

import com.google.common.collect.Lists;
import com.mojang.authlib.GameProfile;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import net.minecraft.command.CommandSource;
import net.minecraft.command.EntitySelector;
import net.minecraft.command.EntitySelectorReader;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.TranslatableText;

public class GameProfileArgumentType implements ArgumentType<GameProfileArgumentType.GameProfileArgument> {
   private static final Collection<String> EXAMPLES = Arrays.asList("Player", "0123", "dd12be42-52a9-4a91-a8a1-11c01849e498", "@e");
   public static final SimpleCommandExceptionType UNKNOWN_PLAYER_EXCEPTION = new SimpleCommandExceptionType(new TranslatableText("argument.player.unknown"));

   public GameProfileArgumentType() {
   }

   public static Collection<GameProfile> getProfileArgument(CommandContext<ServerCommandSource> _snowman, String _snowman) throws CommandSyntaxException {
      return ((GameProfileArgumentType.GameProfileArgument)_snowman.getArgument(_snowman, GameProfileArgumentType.GameProfileArgument.class))
         .getNames((ServerCommandSource)_snowman.getSource());
   }

   public static GameProfileArgumentType gameProfile() {
      return new GameProfileArgumentType();
   }

   public GameProfileArgumentType.GameProfileArgument parse(StringReader _snowman) throws CommandSyntaxException {
      if (_snowman.canRead() && _snowman.peek() == '@') {
         EntitySelectorReader _snowmanx = new EntitySelectorReader(_snowman);
         EntitySelector _snowmanxx = _snowmanx.read();
         if (_snowmanxx.includesNonPlayers()) {
            throw EntityArgumentType.PLAYER_SELECTOR_HAS_ENTITIES_EXCEPTION.create();
         } else {
            return new GameProfileArgumentType.SelectorBacked(_snowmanxx);
         }
      } else {
         int _snowmanx = _snowman.getCursor();

         while (_snowman.canRead() && _snowman.peek() != ' ') {
            _snowman.skip();
         }

         String _snowmanxx = _snowman.getString().substring(_snowmanx, _snowman.getCursor());
         return _snowmanxxx -> {
            GameProfile _snowmanxxx = _snowmanxxx.getMinecraftServer().getUserCache().findByName(_snowman);
            if (_snowmanxxx == null) {
               throw UNKNOWN_PLAYER_EXCEPTION.create();
            } else {
               return Collections.singleton(_snowmanxxx);
            }
         };
      }
   }

   public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> context, SuggestionsBuilder builder) {
      if (context.getSource() instanceof CommandSource) {
         StringReader _snowman = new StringReader(builder.getInput());
         _snowman.setCursor(builder.getStart());
         EntitySelectorReader _snowmanx = new EntitySelectorReader(_snowman);

         try {
            _snowmanx.read();
         } catch (CommandSyntaxException var6) {
         }

         return _snowmanx.listSuggestions(builder, _snowmanxx -> CommandSource.suggestMatching(((CommandSource)context.getSource()).getPlayerNames(), _snowmanxx));
      } else {
         return Suggestions.empty();
      }
   }

   public Collection<String> getExamples() {
      return EXAMPLES;
   }

   @FunctionalInterface
   public interface GameProfileArgument {
      Collection<GameProfile> getNames(ServerCommandSource var1) throws CommandSyntaxException;
   }

   public static class SelectorBacked implements GameProfileArgumentType.GameProfileArgument {
      private final EntitySelector selector;

      public SelectorBacked(EntitySelector _snowman) {
         this.selector = _snowman;
      }

      @Override
      public Collection<GameProfile> getNames(ServerCommandSource _snowman) throws CommandSyntaxException {
         List<ServerPlayerEntity> _snowmanx = this.selector.getPlayers(_snowman);
         if (_snowmanx.isEmpty()) {
            throw EntityArgumentType.PLAYER_NOT_FOUND_EXCEPTION.create();
         } else {
            List<GameProfile> _snowmanxx = Lists.newArrayList();

            for (ServerPlayerEntity _snowmanxxx : _snowmanx) {
               _snowmanxx.add(_snowmanxxx.getGameProfile());
            }

            return _snowmanxx;
         }
      }
   }
}
