import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import java.util.function.Predicate;
import javax.annotation.Nullable;

public class ye {
   private static final SimpleCommandExceptionType a = new SimpleCommandExceptionType(new of("commands.setblock.failed"));

   public static void a(CommandDispatcher<db> var0) {
      _snowman.register(
         (LiteralArgumentBuilder)((LiteralArgumentBuilder)dc.a("setblock").requires(var0x -> var0x.c(2)))
            .then(
               dc.a("pos", ek.a())
                  .then(
                     ((RequiredArgumentBuilder)((RequiredArgumentBuilder)((RequiredArgumentBuilder)dc.a("block", eh.a())
                                 .executes(var0x -> a((db)var0x.getSource(), ek.a(var0x, "pos"), eh.a(var0x, "block"), ye.b.a, null)))
                              .then(dc.a("destroy").executes(var0x -> a((db)var0x.getSource(), ek.a(var0x, "pos"), eh.a(var0x, "block"), ye.b.b, null))))
                           .then(
                              dc.a("keep")
                                 .executes(
                                    var0x -> a((db)var0x.getSource(), ek.a(var0x, "pos"), eh.a(var0x, "block"), ye.b.a, var0xx -> var0xx.c().w(var0xx.d()))
                                 )
                           ))
                        .then(dc.a("replace").executes(var0x -> a((db)var0x.getSource(), ek.a(var0x, "pos"), eh.a(var0x, "block"), ye.b.a, null)))
                  )
            )
      );
   }

   private static int a(db var0, fx var1, ef var2, ye.b var3, @Nullable Predicate<cel> var4) throws CommandSyntaxException {
      aag _snowman = _snowman.e();
      if (_snowman != null && !_snowman.test(new cel(_snowman, _snowman, true))) {
         throw a.create();
      } else {
         boolean _snowmanx;
         if (_snowman == ye.b.b) {
            _snowman.b(_snowman, true);
            _snowmanx = !_snowman.a().g() || !_snowman.d_(_snowman).g();
         } else {
            ccj _snowmanxx = _snowman.c(_snowman);
            aol.a(_snowmanxx);
            _snowmanx = true;
         }

         if (_snowmanx && !_snowman.a(_snowman, _snowman, 2)) {
            throw a.create();
         } else {
            _snowman.a(_snowman, _snowman.a().b());
            _snowman.a(new of("commands.setblock.success", _snowman.u(), _snowman.v(), _snowman.w()), true);
            return 1;
         }
      }
   }

   public interface a {
      @Nullable
      ef filter(cra var1, fx var2, ef var3, aag var4);
   }

   public static enum b {
      a,
      b;

      private b() {
      }
   }
}
