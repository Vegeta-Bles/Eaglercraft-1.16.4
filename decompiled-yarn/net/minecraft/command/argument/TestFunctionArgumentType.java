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
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;
import net.minecraft.command.CommandSource;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.test.TestFunction;
import net.minecraft.test.TestFunctions;
import net.minecraft.text.LiteralText;

public class TestFunctionArgumentType implements ArgumentType<TestFunction> {
   private static final Collection<String> EXAMPLES = Arrays.asList("techtests.piston", "techtests");

   public TestFunctionArgumentType() {
   }

   public TestFunction parse(StringReader _snowman) throws CommandSyntaxException {
      String _snowmanx = _snowman.readUnquotedString();
      Optional<TestFunction> _snowmanxx = TestFunctions.getTestFunction(_snowmanx);
      if (_snowmanxx.isPresent()) {
         return _snowmanxx.get();
      } else {
         Message _snowmanxxx = new LiteralText("No such test: " + _snowmanx);
         throw new CommandSyntaxException(new SimpleCommandExceptionType(_snowmanxxx), _snowmanxxx);
      }
   }

   public static TestFunctionArgumentType testFunction() {
      return new TestFunctionArgumentType();
   }

   public static TestFunction getFunction(CommandContext<ServerCommandSource> context, String name) {
      return (TestFunction)context.getArgument(name, TestFunction.class);
   }

   public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> _snowman, SuggestionsBuilder _snowman) {
      Stream<String> _snowmanxx = TestFunctions.getTestFunctions().stream().map(TestFunction::getStructurePath);
      return CommandSource.suggestMatching(_snowmanxx, _snowman);
   }

   public Collection<String> getExamples() {
      return EXAMPLES;
   }
}
