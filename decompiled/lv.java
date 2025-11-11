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

public class lv implements ArgumentType<lu> {
   private static final Collection<String> a = Arrays.asList("techtests.piston", "techtests");

   public lv() {
   }

   public lu a(StringReader var1) throws CommandSyntaxException {
      String _snowman = _snowman.readUnquotedString();
      Optional<lu> _snowmanx = lh.d(_snowman);
      if (_snowmanx.isPresent()) {
         return _snowmanx.get();
      } else {
         Message _snowmanxx = new oe("No such test: " + _snowman);
         throw new CommandSyntaxException(new SimpleCommandExceptionType(_snowmanxx), _snowmanxx);
      }
   }

   public static lv a() {
      return new lv();
   }

   public static lu a(CommandContext<db> var0, String var1) {
      return (lu)_snowman.getArgument(_snowman, lu.class);
   }

   public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> var1, SuggestionsBuilder var2) {
      Stream<String> _snowman = lh.a().stream().map(lu::a);
      return dd.b(_snowman, _snowman);
   }

   public Collection<String> getExamples() {
      return a;
   }
}
