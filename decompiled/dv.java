import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.CompletableFuture;

public class dv implements ArgumentType<dv.a> {
   private static final Collection<String> a = Arrays.asList("=", ">", "<");
   private static final SimpleCommandExceptionType b = new SimpleCommandExceptionType(new of("arguments.operation.invalid"));
   private static final SimpleCommandExceptionType c = new SimpleCommandExceptionType(new of("arguments.operation.div0"));

   public dv() {
   }

   public static dv a() {
      return new dv();
   }

   public static dv.a a(CommandContext<db> var0, String var1) throws CommandSyntaxException {
      return (dv.a)_snowman.getArgument(_snowman, dv.a.class);
   }

   public dv.a a(StringReader var1) throws CommandSyntaxException {
      if (!_snowman.canRead()) {
         throw b.create();
      } else {
         int _snowman = _snowman.getCursor();

         while (_snowman.canRead() && _snowman.peek() != ' ') {
            _snowman.skip();
         }

         return a(_snowman.getString().substring(_snowman, _snowman.getCursor()));
      }
   }

   public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> var1, SuggestionsBuilder var2) {
      return dd.a(new String[]{"=", "+=", "-=", "*=", "/=", "%=", "<", ">", "><"}, _snowman);
   }

   public Collection<String> getExamples() {
      return a;
   }

   private static dv.a a(String var0) throws CommandSyntaxException {
      return (dv.a)(_snowman.equals("><") ? (var0x, var1) -> {
         int _snowman = var0x.b();
         var0x.c(var1.b());
         var1.c(_snowman);
      } : b(_snowman));
   }

   private static dv.b b(String var0) throws CommandSyntaxException {
      switch (_snowman) {
         case "=":
            return (var0x, var1) -> var1;
         case "+=":
            return (var0x, var1) -> var0x + var1;
         case "-=":
            return (var0x, var1) -> var0x - var1;
         case "*=":
            return (var0x, var1) -> var0x * var1;
         case "/=":
            return (var0x, var1) -> {
               if (var1 == 0) {
                  throw c.create();
               } else {
                  return afm.a(var0x, var1);
               }
            };
         case "%=":
            return (var0x, var1) -> {
               if (var1 == 0) {
                  throw c.create();
               } else {
                  return afm.b(var0x, var1);
               }
            };
         case "<":
            return Math::min;
         case ">":
            return Math::max;
         default:
            throw b.create();
      }
   }

   @FunctionalInterface
   public interface a {
      void apply(ddm var1, ddm var2) throws CommandSyntaxException;
   }

   @FunctionalInterface
   interface b extends dv.a {
      int apply(int var1, int var2) throws CommandSyntaxException;

      @Override
      default void apply(ddm var1, ddm var2) throws CommandSyntaxException {
         _snowman.c(this.apply(_snowman.b(), _snowman.b()));
      }
   }
}
