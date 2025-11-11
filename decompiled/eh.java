import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.CompletableFuture;

public class eh implements ArgumentType<ef> {
   private static final Collection<String> a = Arrays.asList("stone", "minecraft:stone", "stone[foo=bar]", "foo{bar=baz}");

   public eh() {
   }

   public static eh a() {
      return new eh();
   }

   public ef a(StringReader var1) throws CommandSyntaxException {
      ei _snowman = new ei(_snowman, false).a(true);
      return new ef(_snowman.b(), _snowman.a().keySet(), _snowman.c());
   }

   public static ef a(CommandContext<db> var0, String var1) {
      return (ef)_snowman.getArgument(_snowman, ef.class);
   }

   public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> var1, SuggestionsBuilder var2) {
      StringReader _snowman = new StringReader(_snowman.getInput());
      _snowman.setCursor(_snowman.getStart());
      ei _snowmanx = new ei(_snowman, false);

      try {
         _snowmanx.a(true);
      } catch (CommandSyntaxException var6) {
      }

      return _snowmanx.a(_snowman, aed.a());
   }

   public Collection<String> getExamples() {
      return a;
   }
}
