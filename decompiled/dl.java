import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import java.util.Arrays;
import java.util.Collection;

public class dl implements ArgumentType<vk> {
   private static final Collection<String> b = Arrays.asList("minecraft:pig", "cow");
   public static final DynamicCommandExceptionType a = new DynamicCommandExceptionType(var0 -> new of("entity.notFound", var0));

   public dl() {
   }

   public static dl a() {
      return new dl();
   }

   public static vk a(CommandContext<db> var0, String var1) throws CommandSyntaxException {
      return a((vk)_snowman.getArgument(_snowman, vk.class));
   }

   private static vk a(vk var0) throws CommandSyntaxException {
      gm.S.b(_snowman).filter(aqe::b).orElseThrow(() -> a.create(_snowman));
      return _snowman;
   }

   public vk a(StringReader var1) throws CommandSyntaxException {
      return a(vk.a(_snowman));
   }

   public Collection<String> getExamples() {
      return b;
   }
}
