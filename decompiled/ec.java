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

public class ec implements ArgumentType<String> {
   private static final Collection<String> a = Arrays.asList("foo", "123");
   private static final DynamicCommandExceptionType b = new DynamicCommandExceptionType(var0 -> new of("team.notFound", var0));

   public ec() {
   }

   public static ec a() {
      return new ec();
   }

   public static ddl a(CommandContext<db> var0, String var1) throws CommandSyntaxException {
      String _snowman = (String)_snowman.getArgument(_snowman, String.class);
      ddn _snowmanx = ((db)_snowman.getSource()).j().aH();
      ddl _snowmanxx = _snowmanx.f(_snowman);
      if (_snowmanxx == null) {
         throw b.create(_snowman);
      } else {
         return _snowmanxx;
      }
   }

   public String a(StringReader var1) throws CommandSyntaxException {
      return _snowman.readUnquotedString();
   }

   public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> var1, SuggestionsBuilder var2) {
      return _snowman.getSource() instanceof dd ? dd.b(((dd)_snowman.getSource()).m(), _snowman) : Suggestions.empty();
   }

   public Collection<String> getExamples() {
      return a;
   }
}
