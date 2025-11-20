package net.minecraft.command.argument;

import com.google.gson.JsonParseException;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import java.util.Arrays;
import java.util.Collection;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;

public class TextArgumentType implements ArgumentType<Text> {
   private static final Collection<String> EXAMPLES = Arrays.asList("\"hello world\"", "\"\"", "\"{\"text\":\"hello world\"}", "[\"\"]");
   public static final DynamicCommandExceptionType INVALID_COMPONENT_EXCEPTION = new DynamicCommandExceptionType(
      _snowman -> new TranslatableText("argument.component.invalid", _snowman)
   );

   private TextArgumentType() {
   }

   public static Text getTextArgument(CommandContext<ServerCommandSource> context, String name) {
      return (Text)context.getArgument(name, Text.class);
   }

   public static TextArgumentType text() {
      return new TextArgumentType();
   }

   public Text parse(StringReader _snowman) throws CommandSyntaxException {
      try {
         Text _snowmanx = Text.Serializer.fromJson(_snowman);
         if (_snowmanx == null) {
            throw INVALID_COMPONENT_EXCEPTION.createWithContext(_snowman, "empty");
         } else {
            return _snowmanx;
         }
      } catch (JsonParseException var4) {
         String _snowmanxx = var4.getCause() != null ? var4.getCause().getMessage() : var4.getMessage();
         throw INVALID_COMPONENT_EXCEPTION.createWithContext(_snowman, _snowmanxx);
      }
   }

   public Collection<String> getExamples() {
      return EXAMPLES;
   }
}
