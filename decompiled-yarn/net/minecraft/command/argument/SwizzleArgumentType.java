package net.minecraft.command.argument;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import java.util.Arrays;
import java.util.Collection;
import java.util.EnumSet;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.math.Direction;

public class SwizzleArgumentType implements ArgumentType<EnumSet<Direction.Axis>> {
   private static final Collection<String> EXAMPLES = Arrays.asList("xyz", "x");
   private static final SimpleCommandExceptionType INVALID_SWIZZLE_EXCEPTION = new SimpleCommandExceptionType(new TranslatableText("arguments.swizzle.invalid"));

   public SwizzleArgumentType() {
   }

   public static SwizzleArgumentType swizzle() {
      return new SwizzleArgumentType();
   }

   public static EnumSet<Direction.Axis> getSwizzle(CommandContext<ServerCommandSource> _snowman, String _snowman) {
      return (EnumSet<Direction.Axis>)_snowman.getArgument(_snowman, EnumSet.class);
   }

   public EnumSet<Direction.Axis> parse(StringReader _snowman) throws CommandSyntaxException {
      EnumSet<Direction.Axis> _snowmanx = EnumSet.noneOf(Direction.Axis.class);

      while (_snowman.canRead() && _snowman.peek() != ' ') {
         char _snowmanxx = _snowman.read();
         Direction.Axis _snowmanxxx;
         switch (_snowmanxx) {
            case 'x':
               _snowmanxxx = Direction.Axis.X;
               break;
            case 'y':
               _snowmanxxx = Direction.Axis.Y;
               break;
            case 'z':
               _snowmanxxx = Direction.Axis.Z;
               break;
            default:
               throw INVALID_SWIZZLE_EXCEPTION.create();
         }

         if (_snowmanx.contains(_snowmanxxx)) {
            throw INVALID_SWIZZLE_EXCEPTION.create();
         }

         _snowmanx.add(_snowmanxxx);
      }

      return _snowmanx;
   }

   public Collection<String> getExamples() {
      return EXAMPLES;
   }
}
