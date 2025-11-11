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

public class dw implements ArgumentType<hf> {
   private static final Collection<String> b = Arrays.asList("foo", "foo:bar", "particle with options");
   public static final DynamicCommandExceptionType a = new DynamicCommandExceptionType(var0 -> new of("particle.notFound", var0));

   public dw() {
   }

   public static dw a() {
      return new dw();
   }

   public static hf a(CommandContext<db> var0, String var1) {
      return (hf)_snowman.getArgument(_snowman, hf.class);
   }

   public hf a(StringReader var1) throws CommandSyntaxException {
      return b(_snowman);
   }

   public Collection<String> getExamples() {
      return b;
   }

   public static hf b(StringReader var0) throws CommandSyntaxException {
      vk _snowman = vk.a(_snowman);
      hg<?> _snowmanx = gm.V.b(_snowman).orElseThrow(() -> a.create(_snowman));
      return a(_snowman, (hg<hf>)_snowmanx);
   }

   private static <T extends hf> T a(StringReader var0, hg<T> var1) throws CommandSyntaxException {
      return _snowman.d().b(_snowman, _snowman);
   }

   public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> var1, SuggestionsBuilder var2) {
      return dd.a(gm.V.c(), _snowman);
   }
}
