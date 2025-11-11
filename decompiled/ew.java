import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.CompletableFuture;

public class ew implements ArgumentType<ex> {
   private static final Collection<String> a = Arrays.asList("stick", "minecraft:stick", "stick{foo=bar}");

   public ew() {
   }

   public static ew a() {
      return new ew();
   }

   public ex a(StringReader var1) throws CommandSyntaxException {
      ey _snowman = new ey(_snowman, false).h();
      return new ex(_snowman.b(), _snowman.c());
   }

   public static <S> ex a(CommandContext<S> var0, String var1) {
      return (ex)_snowman.getArgument(_snowman, ex.class);
   }

   public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> var1, SuggestionsBuilder var2) {
      StringReader _snowman = new StringReader(_snowman.getInput());
      _snowman.setCursor(_snowman.getStart());
      ey _snowmanx = new ey(_snowman, false);

      try {
         _snowmanx.h();
      } catch (CommandSyntaxException var6) {
      }

      return _snowmanx.a(_snowman, aeg.a());
   }

   public Collection<String> getExamples() {
      return a;
   }
}
