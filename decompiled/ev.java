import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.datafixers.util.Either;
import com.mojang.datafixers.util.Pair;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

public class ev implements ArgumentType<ev.a> {
   private static final Collection<String> a = Arrays.asList("foo", "foo:bar", "#foo");
   private static final DynamicCommandExceptionType b = new DynamicCommandExceptionType(var0 -> new of("arguments.function.tag.unknown", var0));
   private static final DynamicCommandExceptionType c = new DynamicCommandExceptionType(var0 -> new of("arguments.function.unknown", var0));

   public ev() {
   }

   public static ev a() {
      return new ev();
   }

   public ev.a a(StringReader var1) throws CommandSyntaxException {
      if (_snowman.canRead() && _snowman.peek() == '#') {
         _snowman.skip();
         final vk _snowman = vk.a(_snowman);
         return new ev.a() {
            @Override
            public Collection<cy> a(CommandContext<db> var1) throws CommandSyntaxException {
               ael<cy> _snowman = ev.d(_snowman, _snowman);
               return _snowman.b();
            }

            @Override
            public Pair<vk, Either<cy, ael<cy>>> b(CommandContext<db> var1) throws CommandSyntaxException {
               return Pair.of(_snowman, Either.right(ev.d(_snowman, _snowman)));
            }
         };
      } else {
         final vk _snowman = vk.a(_snowman);
         return new ev.a() {
            @Override
            public Collection<cy> a(CommandContext<db> var1) throws CommandSyntaxException {
               return Collections.singleton(ev.c(_snowman, _snowman));
            }

            @Override
            public Pair<vk, Either<cy, ael<cy>>> b(CommandContext<db> var1) throws CommandSyntaxException {
               return Pair.of(_snowman, Either.left(ev.c(_snowman, _snowman)));
            }
         };
      }
   }

   private static cy c(CommandContext<db> var0, vk var1) throws CommandSyntaxException {
      return ((db)_snowman.getSource()).j().aB().a(_snowman).orElseThrow(() -> c.create(_snowman.toString()));
   }

   private static ael<cy> d(CommandContext<db> var0, vk var1) throws CommandSyntaxException {
      ael<cy> _snowman = ((db)_snowman.getSource()).j().aB().b(_snowman);
      if (_snowman == null) {
         throw b.create(_snowman.toString());
      } else {
         return _snowman;
      }
   }

   public static Collection<cy> a(CommandContext<db> var0, String var1) throws CommandSyntaxException {
      return ((ev.a)_snowman.getArgument(_snowman, ev.a.class)).a(_snowman);
   }

   public static Pair<vk, Either<cy, ael<cy>>> b(CommandContext<db> var0, String var1) throws CommandSyntaxException {
      return ((ev.a)_snowman.getArgument(_snowman, ev.a.class)).b(_snowman);
   }

   public Collection<String> getExamples() {
      return a;
   }

   public interface a {
      Collection<cy> a(CommandContext<db> var1) throws CommandSyntaxException;

      Pair<vk, Either<cy, ael<cy>>> b(CommandContext<db> var1) throws CommandSyntaxException;
   }
}
