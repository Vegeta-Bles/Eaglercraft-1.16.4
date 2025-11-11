import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import java.util.Locale;
import java.util.function.Function;

public class zc implements yz {
   private static final SuggestionProvider<db> b = (var0, var1) -> dd.a(b(var0).a(), var1);
   public static final Function<String, za.c> a = var0 -> new za.c() {
         @Override
         public yz a(CommandContext<db> var1) {
            return new zc(zc.b(_snowman), dy.e(_snowman, _snowman));
         }

         @Override
         public ArgumentBuilder<db, ?> a(ArgumentBuilder<db, ?> var1, Function<ArgumentBuilder<db, ?>, ArgumentBuilder<db, ?>> var2) {
            return _snowman.then(dc.a("storage").then((ArgumentBuilder)_snowman.apply(dc.a(_snowman, dy.a()).suggests(zc.b))));
         }
      };
   private final cya c;
   private final vk d;

   private static cya b(CommandContext<db> var0) {
      return ((db)_snowman.getSource()).j().aI();
   }

   private zc(cya var1, vk var2) {
      this.c = _snowman;
      this.d = _snowman;
   }

   @Override
   public void a(md var1) {
      this.c.a(this.d, _snowman);
   }

   @Override
   public md a() {
      return this.c.a(this.d);
   }

   @Override
   public nr b() {
      return new of("commands.data.storage.modified", this.d);
   }

   @Override
   public nr a(mt var1) {
      return new of("commands.data.storage.query", this.d, _snowman.l());
   }

   @Override
   public nr a(dr.h var1, double var2, int var4) {
      return new of("commands.data.storage.get", _snowman, this.d, String.format(Locale.ROOT, "%.2f", _snowman), _snowman);
   }
}
