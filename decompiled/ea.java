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

public class ea implements ArgumentType<Integer> {
   private static final Collection<String> b = Arrays.asList("sidebar", "foo.bar");
   public static final DynamicCommandExceptionType a = new DynamicCommandExceptionType(var0 -> new of("argument.scoreboardDisplaySlot.invalid", var0));

   private ea() {
   }

   public static ea a() {
      return new ea();
   }

   public static int a(CommandContext<db> var0, String var1) {
      return (Integer)_snowman.getArgument(_snowman, Integer.class);
   }

   public Integer a(StringReader var1) throws CommandSyntaxException {
      String _snowman = _snowman.readUnquotedString();
      int _snowmanx = ddn.j(_snowman);
      if (_snowmanx == -1) {
         throw a.create(_snowman);
      } else {
         return _snowmanx;
      }
   }

   public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> var1, SuggestionsBuilder var2) {
      return dd.a(ddn.h(), _snowman);
   }

   public Collection<String> getExamples() {
      return b;
   }
}
