import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import java.util.Arrays;
import java.util.Collection;

public class de implements ArgumentType<de.a> {
   private static final Collection<String> b = Arrays.asList("0", "~", "~-5");
   public static final SimpleCommandExceptionType a = new SimpleCommandExceptionType(new of("argument.angle.incomplete"));

   public de() {
   }

   public static de a() {
      return new de();
   }

   public static float a(CommandContext<db> var0, String var1) {
      return ((de.a)_snowman.getArgument(_snowman, de.a.class)).a((db)_snowman.getSource());
   }

   public de.a a(StringReader var1) throws CommandSyntaxException {
      if (!_snowman.canRead()) {
         throw a.createWithContext(_snowman);
      } else {
         boolean _snowman = es.b(_snowman);
         float _snowmanx = _snowman.canRead() && _snowman.peek() != ' ' ? _snowman.readFloat() : 0.0F;
         return new de.a(_snowmanx, _snowman);
      }
   }

   public Collection<String> getExamples() {
      return b;
   }

   public static final class a {
      private final float a;
      private final boolean b;

      private a(float var1, boolean var2) {
         this.a = _snowman;
         this.b = _snowman;
      }

      public float a(db var1) {
         return afm.g(this.b ? this.a + _snowman.i().j : this.a);
      }
   }
}
