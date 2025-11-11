import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import java.util.Locale;
import java.util.UUID;
import java.util.function.Function;

public class zb implements yz {
   private static final SimpleCommandExceptionType b = new SimpleCommandExceptionType(new of("commands.data.entity.invalid"));
   public static final Function<String, za.c> a = var0 -> new za.c() {
         @Override
         public yz a(CommandContext<db> var1) throws CommandSyntaxException {
            return new zb(dk.a(_snowman, _snowman));
         }

         @Override
         public ArgumentBuilder<db, ?> a(ArgumentBuilder<db, ?> var1, Function<ArgumentBuilder<db, ?>, ArgumentBuilder<db, ?>> var2) {
            return _snowman.then(dc.a("entity").then((ArgumentBuilder)_snowman.apply(dc.a(_snowman, dk.a()))));
         }
      };
   private final aqa c;

   public zb(aqa var1) {
      this.c = _snowman;
   }

   @Override
   public void a(md var1) throws CommandSyntaxException {
      if (this.c instanceof bfw) {
         throw b.create();
      } else {
         UUID _snowman = this.c.bS();
         this.c.f(_snowman);
         this.c.a_(_snowman);
      }
   }

   @Override
   public md a() {
      return cb.b(this.c);
   }

   @Override
   public nr b() {
      return new of("commands.data.entity.modified", this.c.d());
   }

   @Override
   public nr a(mt var1) {
      return new of("commands.data.entity.query", this.c.d(), _snowman.l());
   }

   @Override
   public nr a(dr.h var1, double var2, int var4) {
      return new of("commands.data.entity.get", _snowman, this.c.d(), String.format(Locale.ROOT, "%.2f", _snowman), _snowman);
   }
}
