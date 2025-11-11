import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import java.util.Arrays;
import java.util.Collection;
import java.util.EnumSet;

public class ep implements ArgumentType<EnumSet<gc.a>> {
   private static final Collection<String> a = Arrays.asList("xyz", "x");
   private static final SimpleCommandExceptionType b = new SimpleCommandExceptionType(new of("arguments.swizzle.invalid"));

   public ep() {
   }

   public static ep a() {
      return new ep();
   }

   public static EnumSet<gc.a> a(CommandContext<db> var0, String var1) {
      return (EnumSet<gc.a>)_snowman.getArgument(_snowman, EnumSet.class);
   }

   public EnumSet<gc.a> a(StringReader var1) throws CommandSyntaxException {
      EnumSet<gc.a> _snowman = EnumSet.noneOf(gc.a.class);

      while (_snowman.canRead() && _snowman.peek() != ' ') {
         char _snowmanx = _snowman.read();
         gc.a _snowmanxx;
         switch (_snowmanx) {
            case 'x':
               _snowmanxx = gc.a.a;
               break;
            case 'y':
               _snowmanxx = gc.a.b;
               break;
            case 'z':
               _snowmanxx = gc.a.c;
               break;
            default:
               throw b.create();
         }

         if (_snowman.contains(_snowmanxx)) {
            throw b.create();
         }

         _snowman.add(_snowmanxx);
      }

      return _snowman;
   }

   public Collection<String> getExamples() {
      return a;
   }
}
