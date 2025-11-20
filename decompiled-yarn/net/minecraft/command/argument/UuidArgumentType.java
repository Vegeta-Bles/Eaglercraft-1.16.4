package net.minecraft.command.argument;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import java.util.Arrays;
import java.util.Collection;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.TranslatableText;

public class UuidArgumentType implements ArgumentType<UUID> {
   public static final SimpleCommandExceptionType INVALID_UUID = new SimpleCommandExceptionType(new TranslatableText("argument.uuid.invalid"));
   private static final Collection<String> EXAMPLES = Arrays.asList("dd12be42-52a9-4a91-a8a1-11c01849e498");
   private static final Pattern VALID_CHARACTERS = Pattern.compile("^([-A-Fa-f0-9]+)");

   public UuidArgumentType() {
   }

   public static UUID getUuid(CommandContext<ServerCommandSource> context, String name) {
      return (UUID)context.getArgument(name, UUID.class);
   }

   public static UuidArgumentType uuid() {
      return new UuidArgumentType();
   }

   public UUID parse(StringReader _snowman) throws CommandSyntaxException {
      String _snowmanx = _snowman.getRemaining();
      Matcher _snowmanxx = VALID_CHARACTERS.matcher(_snowmanx);
      if (_snowmanxx.find()) {
         String _snowmanxxx = _snowmanxx.group(1);

         try {
            UUID _snowmanxxxx = UUID.fromString(_snowmanxxx);
            _snowman.setCursor(_snowman.getCursor() + _snowmanxxx.length());
            return _snowmanxxxx;
         } catch (IllegalArgumentException var6) {
         }
      }

      throw INVALID_UUID.create();
   }

   public Collection<String> getExamples() {
      return EXAMPLES;
   }
}
