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
   public static final SuggestionProvider<ServerCommandSource> SUGGESTION_PROVIDER = (commandContext, suggestionsBuilder) -> {
      StringReader stringReader = new StringReader(suggestionsBuilder.getInput());
      stringReader.setCursor(suggestionsBuilder.getStart());
      EntitySelectorReader lv = new EntitySelectorReader(stringReader);

      try {
         lv.read();
      } catch (CommandSyntaxException var5) {
      }

      return lv.listSuggestions(
         suggestionsBuilder,
         suggestionsBuilderx -> CommandSource.suggestMatching(((ServerCommandSource)commandContext.getSource()).getPlayerNames(), suggestionsBuilderx)
      );
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
      Collection<String> collection = ((ScoreHolderArgumentType.ScoreHolder)context.getArgument(name, ScoreHolderArgumentType.ScoreHolder.class))
         .getNames((ServerCommandSource)context.getSource(), players);
      if (collection.isEmpty()) {
         throw EntityArgumentType.ENTITY_NOT_FOUND_EXCEPTION.create();
      } else {
         return collection;
      }
   }

   public static ScoreHolderArgumentType scoreHolder() {
      return new ScoreHolderArgumentType(false);
   }

   public static ScoreHolderArgumentType scoreHolders() {
      return new ScoreHolderArgumentType(true);
   }

   public ScoreHolderArgumentType.ScoreHolder parse(StringReader stringReader) throws CommandSyntaxException {
      if (stringReader.canRead() && stringReader.peek() == '@') {
         EntitySelectorReader lv = new EntitySelectorReader(stringReader);
         EntitySelector lv2 = lv.read();
         if (!this.multiple && lv2.getLimit() > 1) {
            throw EntityArgumentType.TOO_MANY_ENTITIES_EXCEPTION.create();
         } else {
            return new ScoreHolderArgumentType.SelectorScoreHolder(lv2);
         }
      } else {
         int i = stringReader.getCursor();

         while (stringReader.canRead() && stringReader.peek() != ' ') {
            stringReader.skip();
         }

         String string = stringReader.getString().substring(i, stringReader.getCursor());
         if (string.equals("*")) {
            return (arg, supplier) -> {
               Collection<String> collectionx = supplier.get();
               if (collectionx.isEmpty()) {
                  throw EMPTY_SCORE_HOLDER_EXCEPTION.create();
               } else {
                  return collectionx;
               }
            };
         } else {
            Collection<String> collection = Collections.singleton(string);
            return (arg, supplier) -> collection;
         }
      }
   }

   public Collection<String> getExamples() {
      return EXAMPLES;
   }

   @FunctionalInterface
   public interface ScoreHolder {
      Collection<String> getNames(ServerCommandSource source, Supplier<Collection<String>> supplier) throws CommandSyntaxException;
   }

   public static class SelectorScoreHolder implements ScoreHolderArgumentType.ScoreHolder {
      private final EntitySelector selector;

      public SelectorScoreHolder(EntitySelector arg) {
         this.selector = arg;
      }

      @Override
      public Collection<String> getNames(ServerCommandSource arg, Supplier<Collection<String>> supplier) throws CommandSyntaxException {
         List<? extends Entity> list = this.selector.getEntities(arg);
         if (list.isEmpty()) {
            throw EntityArgumentType.ENTITY_NOT_FOUND_EXCEPTION.create();
         } else {
            List<String> list2 = Lists.newArrayList();

            for (Entity lv : list) {
               list2.add(lv.getEntityName());
            }

            return list2;
         }
      }
   }

   public static class Serializer implements ArgumentSerializer<ScoreHolderArgumentType> {
      public Serializer() {
      }

      public void toPacket(ScoreHolderArgumentType arg, PacketByteBuf arg2) {
         byte b = 0;
         if (arg.multiple) {
            b = (byte)(b | 1);
         }

         arg2.writeByte(b);
      }

      public ScoreHolderArgumentType fromPacket(PacketByteBuf arg) {
         byte b = arg.readByte();
         boolean bl = (b & 1) != 0;
         return new ScoreHolderArgumentType(bl);
      }

      public void toJson(ScoreHolderArgumentType arg, JsonObject jsonObject) {
         jsonObject.addProperty("amount", arg.multiple ? "multiple" : "single");
      }
   }
}
