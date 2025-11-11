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

public class el implements ArgumentType<em> {
   private static final Collection<String> b = Arrays.asList("0 0", "~ ~", "~1 ~-2", "^ ^", "^-1 ^0");
   public static final SimpleCommandExceptionType a = new SimpleCommandExceptionType(new of("argument.pos2d.incomplete"));

   public el() {
   }

   public static el a() {
      return new el();
   }

   public static zw a(CommandContext<db> var0, String var1) {
      fx _snowman = ((em)_snowman.getArgument(_snowman, em.class)).c((db)_snowman.getSource());
      return new zw(_snowman.u(), _snowman.w());
   }

   public em a(StringReader var1) throws CommandSyntaxException {
      int _snowman = _snowman.getCursor();
      if (!_snowman.canRead()) {
         throw a.createWithContext(_snowman);
      } else {
         es _snowmanx = es.a(_snowman);
         if (_snowman.canRead() && _snowman.peek() == ' ') {
            _snowman.skip();
            es _snowmanxx = es.a(_snowman);
            return new et(_snowmanx, new es(true, 0.0), _snowmanxx);
         } else {
            _snowman.setCursor(_snowman);
            throw a.createWithContext(_snowman);
         }
      }
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

         return dd.b(_snowman, _snowmanx, _snowman, dc.a(this::a));
      }
   }

   public Collection<String> getExamples() {
      return b;
   }
}
