import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import java.util.Arrays;
import java.util.Collection;

public class ds implements ArgumentType<mt> {
   private static final Collection<String> a = Arrays.asList("0", "0b", "0l", "0.0", "\"foo\"", "{foo=bar}", "[0]");

   private ds() {
   }

   public static ds a() {
      return new ds();
   }

   public static <S> mt a(CommandContext<S> var0, String var1) {
      return (mt)_snowman.getArgument(_snowman, mt.class);
   }

   public mt a(StringReader var1) throws CommandSyntaxException {
      return new mu(_snowman).d();
   }

   public Collection<String> getExamples() {
      return a;
   }
}
