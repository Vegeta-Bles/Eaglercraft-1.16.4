package net.minecraft.command.argument;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import java.util.Arrays;
import java.util.Collection;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.TranslatableText;

public class RotationArgumentType implements ArgumentType<PosArgument> {
   private static final Collection<String> EXAMPLES = Arrays.asList("0 0", "~ ~", "~-5 ~5");
   public static final SimpleCommandExceptionType INCOMPLETE_ROTATION_EXCEPTION = new SimpleCommandExceptionType(
      new TranslatableText("argument.rotation.incomplete")
   );

   public RotationArgumentType() {
   }

   public static RotationArgumentType rotation() {
      return new RotationArgumentType();
   }

   public static PosArgument getRotation(CommandContext<ServerCommandSource> context, String name) {
      return (PosArgument)context.getArgument(name, PosArgument.class);
   }

   public PosArgument parse(StringReader _snowman) throws CommandSyntaxException {
      int _snowmanx = _snowman.getCursor();
      if (!_snowman.canRead()) {
         throw INCOMPLETE_ROTATION_EXCEPTION.createWithContext(_snowman);
      } else {
         CoordinateArgument _snowmanxx = CoordinateArgument.parse(_snowman, false);
         if (_snowman.canRead() && _snowman.peek() == ' ') {
            _snowman.skip();
            CoordinateArgument _snowmanxxx = CoordinateArgument.parse(_snowman, false);
            return new DefaultPosArgument(_snowmanxxx, _snowmanxx, new CoordinateArgument(true, 0.0));
         } else {
            _snowman.setCursor(_snowmanx);
            throw INCOMPLETE_ROTATION_EXCEPTION.createWithContext(_snowman);
         }
      }
   }

   public Collection<String> getExamples() {
      return EXAMPLES;
   }
}
