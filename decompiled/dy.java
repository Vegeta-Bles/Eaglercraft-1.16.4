import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import java.util.Arrays;
import java.util.Collection;

public class dy implements ArgumentType<vk> {
   private static final Collection<String> a = Arrays.asList("foo", "foo:bar", "012");
   private static final DynamicCommandExceptionType b = new DynamicCommandExceptionType(var0 -> new of("advancement.advancementNotFound", var0));
   private static final DynamicCommandExceptionType c = new DynamicCommandExceptionType(var0 -> new of("recipe.notFound", var0));
   private static final DynamicCommandExceptionType d = new DynamicCommandExceptionType(var0 -> new of("predicate.unknown", var0));
   private static final DynamicCommandExceptionType e = new DynamicCommandExceptionType(var0 -> new of("attribute.unknown", var0));

   public dy() {
   }

   public static dy a() {
      return new dy();
   }

   public static y a(CommandContext<db> var0, String var1) throws CommandSyntaxException {
      vk _snowman = (vk)_snowman.getArgument(_snowman, vk.class);
      y _snowmanx = ((db)_snowman.getSource()).j().aA().a(_snowman);
      if (_snowmanx == null) {
         throw b.create(_snowman);
      } else {
         return _snowmanx;
      }
   }

   public static boq<?> b(CommandContext<db> var0, String var1) throws CommandSyntaxException {
      bor _snowman = ((db)_snowman.getSource()).j().aF();
      vk _snowmanx = (vk)_snowman.getArgument(_snowman, vk.class);
      return (boq<?>)_snowman.a(_snowmanx).orElseThrow(() -> c.create(_snowman));
   }

   public static dbo c(CommandContext<db> var0, String var1) throws CommandSyntaxException {
      vk _snowman = (vk)_snowman.getArgument(_snowman, vk.class);
      cza _snowmanx = ((db)_snowman.getSource()).j().aK();
      dbo _snowmanxx = _snowmanx.a(_snowman);
      if (_snowmanxx == null) {
         throw d.create(_snowman);
      } else {
         return _snowmanxx;
      }
   }

   public static arg d(CommandContext<db> var0, String var1) throws CommandSyntaxException {
      vk _snowman = (vk)_snowman.getArgument(_snowman, vk.class);
      return gm.af.b(_snowman).orElseThrow(() -> e.create(_snowman));
   }

   public static vk e(CommandContext<db> var0, String var1) {
      return (vk)_snowman.getArgument(_snowman, vk.class);
   }

   public vk a(StringReader var1) throws CommandSyntaxException {
      return vk.a(_snowman);
   }

   public Collection<String> getExamples() {
      return a;
   }
}
