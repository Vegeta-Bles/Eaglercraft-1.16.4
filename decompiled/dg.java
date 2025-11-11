import com.google.gson.JsonParseException;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import java.util.Arrays;
import java.util.Collection;

public class dg implements ArgumentType<nr> {
   private static final Collection<String> b = Arrays.asList("\"hello world\"", "\"\"", "\"{\"text\":\"hello world\"}", "[\"\"]");
   public static final DynamicCommandExceptionType a = new DynamicCommandExceptionType(var0 -> new of("argument.component.invalid", var0));

   private dg() {
   }

   public static nr a(CommandContext<db> var0, String var1) {
      return (nr)_snowman.getArgument(_snowman, nr.class);
   }

   public static dg a() {
      return new dg();
   }

   public nr a(StringReader var1) throws CommandSyntaxException {
      try {
         nr _snowman = nr.a.a(_snowman);
         if (_snowman == null) {
            throw a.createWithContext(_snowman, "empty");
         } else {
            return _snowman;
         }
      } catch (JsonParseException var4) {
         String _snowmanx = var4.getCause() != null ? var4.getCause().getMessage() : var4.getMessage();
         throw a.createWithContext(_snowman, _snowmanx);
      }
   }

   public Collection<String> getExamples() {
      return b;
   }
}
