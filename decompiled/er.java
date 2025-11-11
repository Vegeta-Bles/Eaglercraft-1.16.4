import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.CompletableFuture;

public class er implements ArgumentType<em> {
   private static final Collection<String> c = Arrays.asList("0 0 0", "~ ~ ~", "^ ^ ^", "^1 ^ ^-5", "0.1 -0.5 .9", "~0.5 ~1 ~-5");
   public static final SimpleCommandExceptionType a = new SimpleCommandExceptionType(new of("argument.pos3d.incomplete"));
   public static final SimpleCommandExceptionType b = new SimpleCommandExceptionType(new of("argument.pos.mixed"));
   private final boolean d;

   public er(boolean var1) {
      this.d = _snowman;
   }

   public static er a() {
      return new er(true);
   }

   public static er a(boolean var0) {
      return new er(_snowman);
   }

   public static dcn a(CommandContext<db> var0, String var1) throws CommandSyntaxException {
      return ((em)_snowman.getArgument(_snowman, em.class)).a((db)_snowman.getSource());
   }

   public static em b(CommandContext<db> var0, String var1) {
      return (em)_snowman.getArgument(_snowman, em.class);
   }

   public em a(StringReader var1) throws CommandSyntaxException {
      return (em)(_snowman.canRead() && _snowman.peek() == '^' ? en.a(_snowman) : et.a(_snowman, this.d));
   }

   public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> var1, SuggestionsBuilder var2) {
      if (!(_snowman.getSource() instanceof dd)) {
         return Suggestions.empty();
      } else {
         String _snowman = _snowman.getRemaining();
         Collection<dd.a> _snowmanx;
         if (!_snowman.isEmpty() && _snowman.charAt(0) == '^') {
            _snowmanx = Collections.singleton(dd.a.a);
         } else {
            _snowmanx = ((dd)_snowman.getSource()).t();
         }

         return dd.a(_snowman, _snowmanx, _snowman, dc.a(this::a));
      }
   }

   public Collection<String> getExamples() {
      return c;
   }
}
