import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import java.util.Arrays;
import java.util.Collection;

public class eo implements ArgumentType<em> {
   private static final Collection<String> b = Arrays.asList("0 0", "~ ~", "~-5 ~5");
   public static final SimpleCommandExceptionType a = new SimpleCommandExceptionType(new of("argument.rotation.incomplete"));

   public eo() {
   }

   public static eo a() {
      return new eo();
   }

   public static em a(CommandContext<db> var0, String var1) {
      return (em)_snowman.getArgument(_snowman, em.class);
   }

   public em a(StringReader var1) throws CommandSyntaxException {
      int _snowman = _snowman.getCursor();
      if (!_snowman.canRead()) {
         throw a.createWithContext(_snowman);
      } else {
         es _snowmanx = es.a(_snowman, false);
         if (_snowman.canRead() && _snowman.peek() == ' ') {
            _snowman.skip();
            es _snowmanxx = es.a(_snowman, false);
            return new et(_snowmanxx, _snowmanx, new es(true, 0.0));
         } else {
            _snowman.setCursor(_snowman);
            throw a.createWithContext(_snowman);
         }
      }
   }

   public Collection<String> getExamples() {
      return b;
   }
}
