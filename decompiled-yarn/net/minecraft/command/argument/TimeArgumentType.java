package net.minecraft.command.argument;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.CompletableFuture;
import net.minecraft.command.CommandSource;
import net.minecraft.text.TranslatableText;

public class TimeArgumentType implements ArgumentType<Integer> {
   private static final Collection<String> EXAMPLES = Arrays.asList("0d", "0s", "0t", "0");
   private static final SimpleCommandExceptionType INVALID_UNIT_EXCEPTION = new SimpleCommandExceptionType(new TranslatableText("argument.time.invalid_unit"));
   private static final DynamicCommandExceptionType INVALID_COUNT_EXCEPTION = new DynamicCommandExceptionType(
      _snowman -> new TranslatableText("argument.time.invalid_tick_count", _snowman)
   );
   private static final Object2IntMap<String> UNITS = new Object2IntOpenHashMap();

   public TimeArgumentType() {
   }

   public static TimeArgumentType time() {
      return new TimeArgumentType();
   }

   public Integer parse(StringReader _snowman) throws CommandSyntaxException {
      float _snowmanx = _snowman.readFloat();
      String _snowmanxx = _snowman.readUnquotedString();
      int _snowmanxxx = UNITS.getOrDefault(_snowmanxx, 0);
      if (_snowmanxxx == 0) {
         throw INVALID_UNIT_EXCEPTION.create();
      } else {
         int _snowmanxxxx = Math.round(_snowmanx * (float)_snowmanxxx);
         if (_snowmanxxxx < 0) {
            throw INVALID_COUNT_EXCEPTION.create(_snowmanxxxx);
         } else {
            return _snowmanxxxx;
         }
      }
   }

   public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> context, SuggestionsBuilder builder) {
      StringReader _snowman = new StringReader(builder.getRemaining());

      try {
         _snowman.readFloat();
      } catch (CommandSyntaxException var5) {
         return builder.buildFuture();
      }

      return CommandSource.suggestMatching(UNITS.keySet(), builder.createOffset(builder.getStart() + _snowman.getCursor()));
   }

   public Collection<String> getExamples() {
      return EXAMPLES;
   }

   static {
      UNITS.put("d", 24000);
      UNITS.put("s", 20);
      UNITS.put("t", 1);
      UNITS.put("", 1);
   }
}
