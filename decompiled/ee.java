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

public class ee implements ArgumentType<UUID> {
   public static final SimpleCommandExceptionType a = new SimpleCommandExceptionType(new of("argument.uuid.invalid"));
   private static final Collection<String> b = Arrays.asList("dd12be42-52a9-4a91-a8a1-11c01849e498");
   private static final Pattern c = Pattern.compile("^([-A-Fa-f0-9]+)");

   public ee() {
   }

   public static UUID a(CommandContext<db> var0, String var1) {
      return (UUID)_snowman.getArgument(_snowman, UUID.class);
   }

   public static ee a() {
      return new ee();
   }

   public UUID a(StringReader var1) throws CommandSyntaxException {
      String _snowman = _snowman.getRemaining();
      Matcher _snowmanx = c.matcher(_snowman);
      if (_snowmanx.find()) {
         String _snowmanxx = _snowmanx.group(1);

         try {
            UUID _snowmanxxx = UUID.fromString(_snowmanxx);
            _snowman.setCursor(_snowman.getCursor() + _snowmanxx.length());
            return _snowmanxxx;
         } catch (IllegalArgumentException var6) {
         }
      }

      throw a.create();
   }

   public Collection<String> getExamples() {
      return b;
   }
}
