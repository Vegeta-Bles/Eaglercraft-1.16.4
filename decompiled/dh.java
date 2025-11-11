import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import java.util.Arrays;
import java.util.Collection;

public class dh implements ArgumentType<md> {
   private static final Collection<String> a = Arrays.asList("{}", "{foo=bar}");

   private dh() {
   }

   public static dh a() {
      return new dh();
   }

   public static <S> md a(CommandContext<S> var0, String var1) {
      return (md)_snowman.getArgument(_snowman, md.class);
   }

   public md a(StringReader var1) throws CommandSyntaxException {
      return new mu(_snowman).f();
   }

   public Collection<String> getExamples() {
      return a;
   }
}
