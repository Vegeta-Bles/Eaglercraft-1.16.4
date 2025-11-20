package net.minecraft.command.argument;

import com.google.common.collect.Lists;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import net.minecraft.command.CommandSource;
import net.minecraft.scoreboard.ScoreboardCriterion;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.stat.Stat;
import net.minecraft.stat.StatType;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.registry.Registry;

public class ObjectiveCriteriaArgumentType implements ArgumentType<ScoreboardCriterion> {
   private static final Collection<String> EXAMPLES = Arrays.asList("foo", "foo.bar.baz", "minecraft:foo");
   public static final DynamicCommandExceptionType INVALID_CRITERIA_EXCEPTION = new DynamicCommandExceptionType(
      _snowman -> new TranslatableText("argument.criteria.invalid", _snowman)
   );

   private ObjectiveCriteriaArgumentType() {
   }

   public static ObjectiveCriteriaArgumentType objectiveCriteria() {
      return new ObjectiveCriteriaArgumentType();
   }

   public static ScoreboardCriterion getCriteria(CommandContext<ServerCommandSource> _snowman, String _snowman) {
      return (ScoreboardCriterion)_snowman.getArgument(_snowman, ScoreboardCriterion.class);
   }

   public ScoreboardCriterion parse(StringReader _snowman) throws CommandSyntaxException {
      int _snowmanx = _snowman.getCursor();

      while (_snowman.canRead() && _snowman.peek() != ' ') {
         _snowman.skip();
      }

      String _snowmanxx = _snowman.getString().substring(_snowmanx, _snowman.getCursor());
      return ScoreboardCriterion.createStatCriterion(_snowmanxx).orElseThrow(() -> {
         _snowman.setCursor(_snowman);
         return INVALID_CRITERIA_EXCEPTION.create(_snowman);
      });
   }

   public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> context, SuggestionsBuilder builder) {
      List<String> _snowman = Lists.newArrayList(ScoreboardCriterion.OBJECTIVES.keySet());

      for (StatType<?> _snowmanx : Registry.STAT_TYPE) {
         for (Object _snowmanxx : _snowmanx.getRegistry()) {
            String _snowmanxxx = this.getStatName(_snowmanx, _snowmanxx);
            _snowman.add(_snowmanxxx);
         }
      }

      return CommandSource.suggestMatching(_snowman, builder);
   }

   public <T> String getStatName(StatType<T> stat, Object value) {
      return Stat.getName(stat, (T)value);
   }

   public Collection<String> getExamples() {
      return EXAMPLES;
   }
}
