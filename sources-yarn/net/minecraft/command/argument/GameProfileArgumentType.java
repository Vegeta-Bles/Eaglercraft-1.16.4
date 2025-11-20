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

   public static Collection<GameProfile> getProfileArgument(CommandContext<ServerCommandSource> commandContext, String string) throws CommandSyntaxException {
      return ((GameProfileArgumentType.GameProfileArgument)commandContext.getArgument(string, GameProfileArgumentType.GameProfileArgument.class))
         .getNames((ServerCommandSource)commandContext.getSource());
   }

   public static GameProfileArgumentType gameProfile() {
      return new GameProfileArgumentType();
   }

   public GameProfileArgumentType.GameProfileArgument parse(StringReader stringReader) throws CommandSyntaxException {
      if (stringReader.canRead() && stringReader.peek() == '@') {
         EntitySelectorReader lv = new EntitySelectorReader(stringReader);
         EntitySelector lv2 = lv.read();
         if (lv2.includesNonPlayers()) {
            throw EntityArgumentType.PLAYER_SELECTOR_HAS_ENTITIES_EXCEPTION.create();
         } else {
            return new GameProfileArgumentType.SelectorBacked(lv2);
         }
      } else {
         int i = stringReader.getCursor();

         while (stringReader.canRead() && stringReader.peek() != ' ') {
            stringReader.skip();
         }

         String string = stringReader.getString().substring(i, stringReader.getCursor());
         return arg -> {
            GameProfile gameProfile = arg.getMinecraftServer().getUserCache().findByName(string);
            if (gameProfile == null) {
               throw UNKNOWN_PLAYER_EXCEPTION.create();
            } else {
               return Collections.singleton(gameProfile);
            }
         };
      }
   }

   public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> context, SuggestionsBuilder builder) {
      if (context.getSource() instanceof CommandSource) {
         StringReader stringReader = new StringReader(builder.getInput());
         stringReader.setCursor(builder.getStart());
         EntitySelectorReader lv = new EntitySelectorReader(stringReader);

         try {
            lv.read();
         } catch (CommandSyntaxException var6) {
         }

         return lv.listSuggestions(
            builder, suggestionsBuilder -> CommandSource.suggestMatching(((CommandSource)context.getSource()).getPlayerNames(), suggestionsBuilder)
         );
      } else {
         return Suggestions.empty();
      }
   }

   public Collection<String> getExamples() {
      return EXAMPLES;
   }

   @FunctionalInterface
   public interface GameProfileArgument {
      Collection<GameProfile> getNames(ServerCommandSource arg) throws CommandSyntaxException;
   }

   public static class SelectorBacked implements GameProfileArgumentType.GameProfileArgument {
      private final EntitySelector selector;

      public SelectorBacked(EntitySelector arg) {
         this.selector = arg;
      }

      @Override
      public Collection<GameProfile> getNames(ServerCommandSource arg) throws CommandSyntaxException {
         List<ServerPlayerEntity> list = this.selector.getPlayers(arg);
         if (list.isEmpty()) {
            throw EntityArgumentType.PLAYER_NOT_FOUND_EXCEPTION.create();
         } else {
            List<GameProfile> list2 = Lists.newArrayList();

            for (ServerPlayerEntity lv : list) {
               list2.add(lv.getGameProfile());
            }

            return list2;
         }
      }
   }
}
