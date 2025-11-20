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
      _snowman -> new TranslatableText("arguments.objective.notFound", _snowman)
   );
   private static final DynamicCommandExceptionType READONLY_OBJECTIVE_EXCEPTION = new DynamicCommandExceptionType(
      _snowman -> new TranslatableText("arguments.objective.readonly", _snowman)
   );
   public static final DynamicCommandExceptionType LONG_NAME_EXCEPTION = new DynamicCommandExceptionType(
      _snowman -> new TranslatableText("commands.scoreboard.objectives.add.longName", _snowman)
   );

   public ObjectiveArgumentType() {
   }

   public static ObjectiveArgumentType objective() {
      return new ObjectiveArgumentType();
   }

   public static ScoreboardObjective getObjective(CommandContext<ServerCommandSource> context, String name) throws CommandSyntaxException {
      String _snowman = (String)context.getArgument(name, String.class);
      Scoreboard _snowmanx = ((ServerCommandSource)context.getSource()).getMinecraftServer().getScoreboard();
      ScoreboardObjective _snowmanxx = _snowmanx.getNullableObjective(_snowman);
      if (_snowmanxx == null) {
         throw UNKNOWN_OBJECTIVE_EXCEPTION.create(_snowman);
      } else {
         return _snowmanxx;
      }
   }

   public static ScoreboardObjective getWritableObjective(CommandContext<ServerCommandSource> context, String name) throws CommandSyntaxException {
      ScoreboardObjective _snowman = getObjective(context, name);
      if (_snowman.getCriterion().isReadOnly()) {
         throw READONLY_OBJECTIVE_EXCEPTION.create(_snowman.getName());
      } else {
         return _snowman;
      }
   }

   public String parse(StringReader _snowman) throws CommandSyntaxException {
      String _snowmanx = _snowman.readUnquotedString();
      if (_snowmanx.length() > 16) {
         throw LONG_NAME_EXCEPTION.create(16);
      } else {
         return _snowmanx;
      }
   }

   public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> context, SuggestionsBuilder builder) {
      if (context.getSource() instanceof ServerCommandSource) {
         return CommandSource.suggestMatching(((ServerCommandSource)context.getSource()).getMinecraftServer().getScoreboard().getObjectiveNames(), builder);
      } else if (context.getSource() instanceof CommandSource) {
         CommandSource _snowman = (CommandSource)context.getSource();
         return _snowman.getCompletions(context, builder);
      } else {
         return Suggestions.empty();
      }
   }

   public Collection<String> getExamples() {
      return EXAMPLES;
   }
}
