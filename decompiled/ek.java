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

public class ek implements ArgumentType<em> {
   private static final Collection<String> c = Arrays.asList("0 0 0", "~ ~ ~", "^ ^ ^", "^1 ^ ^-5", "~0.5 ~1 ~-5");
   public static final SimpleCommandExceptionType a = new SimpleCommandExceptionType(new of("argument.pos.unloaded"));
   public static final SimpleCommandExceptionType b = new SimpleCommandExceptionType(new of("argument.pos.outofworld"));

   public ek() {
   }

   public static ek a() {
      return new ek();
   }

   public static fx a(CommandContext<db> var0, String var1) throws CommandSyntaxException {
      fx _snowman = ((em)_snowman.getArgument(_snowman, em.class)).c((db)_snowman.getSource());
      if (!((db)_snowman.getSource()).e().C(_snowman)) {
         throw a.create();
      } else {
         ((db)_snowman.getSource()).e();
         if (!aag.k(_snowman)) {
            throw b.create();
         } else {
            return _snowman;
         }
      }
   }

   public static fx b(CommandContext<db> var0, String var1) throws CommandSyntaxException {
      return ((em)_snowman.getArgument(_snowman, em.class)).c((db)_snowman.getSource());
   }

   public em a(StringReader var1) throws CommandSyntaxException {
      return (em)(_snowman.canRead() && _snowman.peek() == '^' ? en.a(_snowman) : et.a(_snowman));
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
            _snowmanx = ((dd)_snowman.getSource()).s();
         }

         return dd.a(_snowman, _snowmanx, _snowman, dc.a(this::a));
      }
   }

   public Collection<String> getExamples() {
      return c;
   }
}
