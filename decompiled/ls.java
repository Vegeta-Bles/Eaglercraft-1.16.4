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

public class ls implements ArgumentType<String> {
   private static final Collection<String> a = Arrays.asList("techtests", "mobtests");

   public ls() {
   }

   public String a(StringReader var1) throws CommandSyntaxException {
      String _snowman = _snowman.readUnquotedString();
      if (lh.b(_snowman)) {
         return _snowman;
      } else {
         Message _snowmanx = new oe("No such test class: " + _snowman);
         throw new CommandSyntaxException(new SimpleCommandExceptionType(_snowmanx), _snowmanx);
      }
   }

   public static ls a() {
      return new ls();
   }

   public static String a(CommandContext<db> var0, String var1) {
      return (String)_snowman.getArgument(_snowman, String.class);
   }

   public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> var1, SuggestionsBuilder var2) {
      return dd.b(lh.b().stream(), _snowman);
   }

   public Collection<String> getExamples() {
      return a;
   }
}
