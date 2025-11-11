import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import java.util.Locale;
import java.util.function.Function;

public class yy implements yz {
   private static final SimpleCommandExceptionType b = new SimpleCommandExceptionType(new of("commands.data.block.invalid"));
   public static final Function<String, za.c> a = var0 -> new za.c() {
         @Override
         public yz a(CommandContext<db> var1) throws CommandSyntaxException {
            fx _snowman = ek.a(_snowman, _snowman + "Pos");
            ccj _snowmanx = ((db)_snowman.getSource()).e().c(_snowman);
            if (_snowmanx == null) {
               throw yy.b.create();
            } else {
               return new yy(_snowmanx, _snowman);
            }
         }

         @Override
         public ArgumentBuilder<db, ?> a(ArgumentBuilder<db, ?> var1, Function<ArgumentBuilder<db, ?>, ArgumentBuilder<db, ?>> var2) {
            return _snowman.then(dc.a("block").then((ArgumentBuilder)_snowman.apply(dc.a(_snowman + "Pos", ek.a()))));
         }
      };
   private final ccj c;
   private final fx d;

   public yy(ccj var1, fx var2) {
      this.c = _snowman;
      this.d = _snowman;
   }

   @Override
   public void a(md var1) {
      _snowman.b("x", this.d.u());
      _snowman.b("y", this.d.v());
      _snowman.b("z", this.d.w());
      ceh _snowman = this.c.v().d_(this.d);
      this.c.a(_snowman, _snowman);
      this.c.X_();
      this.c.v().a(this.d, _snowman, _snowman, 3);
   }

   @Override
   public md a() {
      return this.c.a(new md());
   }

   @Override
   public nr b() {
      return new of("commands.data.block.modified", this.d.u(), this.d.v(), this.d.w());
   }

   @Override
   public nr a(mt var1) {
      return new of("commands.data.block.query", this.d.u(), this.d.v(), this.d.w(), _snowman.l());
   }

   @Override
   public nr a(dr.h var1, double var2, int var4) {
      return new of("commands.data.block.get", _snowman, this.d.u(), this.d.v(), this.d.w(), String.format(Locale.ROOT, "%.2f", _snowman), _snowman);
   }
}
