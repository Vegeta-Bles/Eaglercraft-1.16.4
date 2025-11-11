import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import java.util.Arrays;
import java.util.Collection;

public interface dx<T extends bz<?>> extends ArgumentType<T> {
   static dx.b a() {
      return new dx.b();
   }

   static dx.a b() {
      return new dx.a();
   }

   public static class a implements dx<bz.c> {
      private static final Collection<String> a = Arrays.asList("0..5.2", "0", "-5.4", "-100.76..", "..100");

      public a() {
      }

      public bz.c a(StringReader var1) throws CommandSyntaxException {
         return bz.c.a(_snowman);
      }

      public Collection<String> getExamples() {
         return a;
      }
   }

   public static class b implements dx<bz.d> {
      private static final Collection<String> a = Arrays.asList("0..5", "0", "-5", "-100..", "..100");

      public b() {
      }

      public static bz.d a(CommandContext<db> var0, String var1) {
         return (bz.d)_snowman.getArgument(_snowman, bz.d.class);
      }

      public bz.d a(StringReader var1) throws CommandSyntaxException {
         return bz.d.a(_snowman);
      }

      public Collection<String> getExamples() {
         return a;
      }
   }
}
