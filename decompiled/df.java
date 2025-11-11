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

public class df implements ArgumentType<k> {
   private static final Collection<String> b = Arrays.asList("red", "green");
   public static final DynamicCommandExceptionType a = new DynamicCommandExceptionType(var0 -> new of("argument.color.invalid", var0));

   private df() {
   }

   public static df a() {
      return new df();
   }

   public static k a(CommandContext<db> var0, String var1) {
      return (k)_snowman.getArgument(_snowman, k.class);
   }

   public k a(StringReader var1) throws CommandSyntaxException {
      String _snowman = _snowman.readUnquotedString();
      k _snowmanx = k.b(_snowman);
      if (_snowmanx != null && !_snowmanx.c()) {
         return _snowmanx;
      } else {
         throw a.create(_snowman);
      }
   }

   public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> var1, SuggestionsBuilder var2) {
      return dd.b(k.a(true, false), _snowman);
   }

   public Collection<String> getExamples() {
      return b;
   }
}
