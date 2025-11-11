import com.google.common.collect.Lists;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class du implements ArgumentType<ddq> {
   private static final Collection<String> b = Arrays.asList("foo", "foo.bar.baz", "minecraft:foo");
   public static final DynamicCommandExceptionType a = new DynamicCommandExceptionType(var0 -> new of("argument.criteria.invalid", var0));

   private du() {
   }

   public static du a() {
      return new du();
   }

   public static ddq a(CommandContext<db> var0, String var1) {
      return (ddq)_snowman.getArgument(_snowman, ddq.class);
   }

   public ddq a(StringReader var1) throws CommandSyntaxException {
      int _snowman = _snowman.getCursor();

      while (_snowman.canRead() && _snowman.peek() != ' ') {
         _snowman.skip();
      }

      String _snowmanx = _snowman.getString().substring(_snowman, _snowman.getCursor());
      return ddq.a(_snowmanx).orElseThrow(() -> {
         _snowman.setCursor(_snowman);
         return a.create(_snowman);
      });
   }

   public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> var1, SuggestionsBuilder var2) {
      List<String> _snowman = Lists.newArrayList(ddq.a.keySet());

      for (adz<?> _snowmanx : gm.ag) {
         for (Object _snowmanxx : _snowmanx.a()) {
            String _snowmanxxx = this.a(_snowmanx, _snowmanxx);
            _snowman.add(_snowmanxxx);
         }
      }

      return dd.b(_snowman, _snowman);
   }

   public <T> String a(adz<T> var1, Object var2) {
      return adx.a(_snowman, (T)_snowman);
   }

   public Collection<String> getExamples() {
      return b;
   }
}
