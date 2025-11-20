package net.minecraft.command.argument;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.CompletableFuture;
import net.minecraft.command.CommandSource;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.scoreboard.ScoreboardObjective;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.TranslatableText;

public class ObjectiveArgumentType implements ArgumentType<String> {
   private static final Collection<String> EXAMPLES = Arrays.asList("foo", "*", "012");
   private static final DynamicCommandExceptionType UNKNOWN_OBJECTIVE_EXCEPTION = new DynamicCommandExceptionType(
      object -> new TranslatableText("arguments.objective.notFound", object)
   );
   private static final DynamicCommandExceptionType READONLY_OBJECTIVE_EXCEPTION = new DynamicCommandExceptionType(
      object -> new TranslatableText("arguments.objective.readonly", object)
   );
   public static final DynamicCommandExceptionType LONG_NAME_EXCEPTION = new DynamicCommandExceptionType(
      object -> new TranslatableText("commands.scoreboard.objectives.add.longName", object)
   );

   public ObjectiveArgumentType() {
   }

   public static ObjectiveArgumentType objective() {
      return new ObjectiveArgumentType();
   }

   public static ScoreboardObjective getObjective(CommandContext<ServerCommandSource> context, String name) throws CommandSyntaxException {
      String string2 = (String)context.getArgument(name, String.class);
      Scoreboard lv = ((ServerCommandSource)context.getSource()).getMinecraftServer().getScoreboard();
      ScoreboardObjective lv2 = lv.getNullableObjective(string2);
      if (lv2 == null) {
         throw UNKNOWN_OBJECTIVE_EXCEPTION.create(string2);
      } else {
         return lv2;
      }
   }

   public static ScoreboardObjective getWritableObjective(CommandContext<ServerCommandSource> context, String name) throws CommandSyntaxException {
      ScoreboardObjective lv = getObjective(context, name);
      if (lv.getCriterion().isReadOnly()) {
         throw READONLY_OBJECTIVE_EXCEPTION.create(lv.getName());
      } else {
         return lv;
      }
   }

   public String parse(StringReader stringReader) throws CommandSyntaxException {
      String string = stringReader.readUnquotedString();
      if (string.length() > 16) {
         throw LONG_NAME_EXCEPTION.create(16);
      } else {
         return string;
      }
   }

   public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> context, SuggestionsBuilder builder) {
      if (context.getSource() instanceof ServerCommandSource) {
         return CommandSource.suggestMatching(((ServerCommandSource)context.getSource()).getMinecraftServer().getScoreboard().getObjectiveNames(), builder);
      } else if (context.getSource() instanceof CommandSource) {
         CommandSource lv = (CommandSource)context.getSource();
         return lv.getCompletions(context, builder);
      } else {
         return Suggestions.empty();
      }
   }

   public Collection<String> getExamples() {
      return EXAMPLES;
   }
}
