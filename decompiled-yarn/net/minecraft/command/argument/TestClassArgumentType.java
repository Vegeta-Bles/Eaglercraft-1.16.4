package net.minecraft.command.argument;

import com.mojang.brigadier.Message;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.CompletableFuture;
import net.minecraft.command.CommandSource;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.test.TestFunctions;
import net.minecraft.text.LiteralText;

public class TestClassArgumentType implements ArgumentType<String> {
   private static final Collection<String> EXAMPLES = Arrays.asList("techtests", "mobtests");

   public TestClassArgumentType() {
   }

   public String parse(StringReader _snowman) throws CommandSyntaxException {
      String _snowmanx = _snowman.readUnquotedString();
      if (TestFunctions.testClassExists(_snowmanx)) {
         return _snowmanx;
      } else {
         Message _snowmanxx = new LiteralText("No such test class: " + _snowmanx);
         throw new CommandSyntaxException(new SimpleCommandExceptionType(_snowmanxx), _snowmanxx);
      }
   }

   public static TestClassArgumentType testClass() {
      return new TestClassArgumentType();
   }

   public static String getTestClass(CommandContext<ServerCommandSource> context, String name) {
      return (String)context.getArgument(name, String.class);
   }

   public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> _snowman, SuggestionsBuilder _snowman) {
      return CommandSource.suggestMatching(TestFunctions.getTestClasses().stream(), _snowman);
   }

   public Collection<String> getExamples() {
      return EXAMPLES;
   }
}
