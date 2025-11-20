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
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;

public class ColorArgumentType implements ArgumentType<Formatting> {
   private static final Collection<String> EXAMPLES = Arrays.asList("red", "green");
   public static final DynamicCommandExceptionType INVALID_COLOR_EXCEPTION = new DynamicCommandExceptionType(
      _snowman -> new TranslatableText("argument.color.invalid", _snowman)
   );

   private ColorArgumentType() {
   }

   public static ColorArgumentType color() {
      return new ColorArgumentType();
   }

   public static Formatting getColor(CommandContext<ServerCommandSource> context, String name) {
      return (Formatting)context.getArgument(name, Formatting.class);
   }

   public Formatting parse(StringReader _snowman) throws CommandSyntaxException {
      String _snowmanx = _snowman.readUnquotedString();
      Formatting _snowmanxx = Formatting.byName(_snowmanx);
      if (_snowmanxx != null && !_snowmanxx.isModifier()) {
         return _snowmanxx;
      } else {
         throw INVALID_COLOR_EXCEPTION.create(_snowmanx);
      }
   }

   public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> context, SuggestionsBuilder builder) {
      return CommandSource.suggestMatching(Formatting.getNames(true, false), builder);
   }

   public Collection<String> getExamples() {
      return EXAMPLES;
   }
}
