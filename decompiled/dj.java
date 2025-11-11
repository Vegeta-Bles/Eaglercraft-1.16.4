import com.google.common.collect.Maps;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiFunction;
import javax.annotation.Nullable;

public class dj implements ArgumentType<dj.a> {
   private static final Collection<String> a = Arrays.asList("eyes", "feet");
   private static final DynamicCommandExceptionType b = new DynamicCommandExceptionType(var0 -> new of("argument.anchor.invalid", var0));

   public dj() {
   }

   public static dj.a a(CommandContext<db> var0, String var1) {
      return (dj.a)_snowman.getArgument(_snowman, dj.a.class);
   }

   public static dj a() {
      return new dj();
   }

   public dj.a a(StringReader var1) throws CommandSyntaxException {
      int _snowman = _snowman.getCursor();
      String _snowmanx = _snowman.readUnquotedString();
      dj.a _snowmanxx = dj.a.a(_snowmanx);
      if (_snowmanxx == null) {
         _snowman.setCursor(_snowman);
         throw b.createWithContext(_snowman, _snowmanx);
      } else {
         return _snowmanxx;
      }
   }

   public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> var1, SuggestionsBuilder var2) {
      return dd.b(dj.a.c.keySet(), _snowman);
   }

   public Collection<String> getExamples() {
      return a;
   }

   public static enum a {
      a("feet", (var0, var1) -> var0),
      b("eyes", (var0, var1) -> new dcn(var0.b, var0.c + (double)var1.ce(), var0.d));

      private static final Map<String, dj.a> c = x.a(Maps.newHashMap(), var0 -> {
         for (dj.a _snowman : values()) {
            var0.put(_snowman.d, _snowman);
         }
      });
      private final String d;
      private final BiFunction<dcn, aqa, dcn> e;

      private a(String var3, BiFunction<dcn, aqa, dcn> var4) {
         this.d = _snowman;
         this.e = _snowman;
      }

      @Nullable
      public static dj.a a(String var0) {
         return c.get(_snowman);
      }

      public dcn a(aqa var1) {
         return this.e.apply(_snowman.cA(), _snowman);
      }

      public dcn a(db var1) {
         aqa _snowman = _snowman.f();
         return _snowman == null ? _snowman.d() : this.e.apply(_snowman.d(), _snowman);
      }
   }
}
