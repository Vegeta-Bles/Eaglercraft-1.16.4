import com.google.common.collect.Lists;
import com.mojang.datafixers.util.Pair;
import java.util.List;
import java.util.function.Supplier;
import javax.annotation.Nullable;

public class cca extends ccj implements aoy {
   @Nullable
   private nr a;
   @Nullable
   private bkx b = bkx.a;
   @Nullable
   private mj c;
   private boolean g;
   @Nullable
   private List<Pair<ccb, bkx>> h;

   public cca() {
      super(cck.s);
   }

   public cca(bkx var1) {
      this();
      this.b = _snowman;
   }

   @Nullable
   public static mj a(bmb var0) {
      mj _snowman = null;
      md _snowmanx = _snowman.b("BlockEntityTag");
      if (_snowmanx != null && _snowmanx.c("Patterns", 9)) {
         _snowman = _snowmanx.d("Patterns", 10).d();
      }

      return _snowman;
   }

   public void a(bmb var1, bkx var2) {
      this.c = a(_snowman);
      this.b = _snowman;
      this.h = null;
      this.g = true;
      this.a = _snowman.t() ? _snowman.r() : null;
   }

   @Override
   public nr R() {
      return (nr)(this.a != null ? this.a : new of("block.minecraft.banner"));
   }

   @Nullable
   @Override
   public nr T() {
      return this.a;
   }

   public void a(nr var1) {
      this.a = _snowman;
   }

   @Override
   public md a(md var1) {
      super.a(_snowman);
      if (this.c != null) {
         _snowman.a("Patterns", this.c);
      }

      if (this.a != null) {
         _snowman.a("CustomName", nr.a.a(this.a));
      }

      return _snowman;
   }

   @Override
   public void a(ceh var1, md var2) {
      super.a(_snowman, _snowman);
      if (_snowman.c("CustomName", 8)) {
         this.a = nr.a.a(_snowman.l("CustomName"));
      }

      if (this.n()) {
         this.b = ((btm)this.p().b()).b();
      } else {
         this.b = null;
      }

      this.c = _snowman.d("Patterns", 10);
      this.h = null;
      this.g = true;
   }

   @Nullable
   @Override
   public ow a() {
      return new ow(this.e, 6, this.b());
   }

   @Override
   public md b() {
      return this.a(new md());
   }

   public static int b(bmb var0) {
      md _snowman = _snowman.b("BlockEntityTag");
      return _snowman != null && _snowman.e("Patterns") ? _snowman.d("Patterns", 10).size() : 0;
   }

   public List<Pair<ccb, bkx>> c() {
      if (this.h == null && this.g) {
         this.h = a(this.a(this::p), this.c);
      }

      return this.h;
   }

   public static List<Pair<ccb, bkx>> a(bkx var0, @Nullable mj var1) {
      List<Pair<ccb, bkx>> _snowman = Lists.newArrayList();
      _snowman.add(Pair.of(ccb.a, _snowman));
      if (_snowman != null) {
         for (int _snowmanx = 0; _snowmanx < _snowman.size(); _snowmanx++) {
            md _snowmanxx = _snowman.a(_snowmanx);
            ccb _snowmanxxx = ccb.a(_snowmanxx.l("Pattern"));
            if (_snowmanxxx != null) {
               int _snowmanxxxx = _snowmanxx.h("Color");
               _snowman.add(Pair.of(_snowmanxxx, bkx.a(_snowmanxxxx)));
            }
         }
      }

      return _snowman;
   }

   public static void c(bmb var0) {
      md _snowman = _snowman.b("BlockEntityTag");
      if (_snowman != null && _snowman.c("Patterns", 9)) {
         mj _snowmanx = _snowman.d("Patterns", 10);
         if (!_snowmanx.isEmpty()) {
            _snowmanx.c(_snowmanx.size() - 1);
            if (_snowmanx.isEmpty()) {
               _snowman.c("BlockEntityTag");
            }
         }
      }
   }

   public bmb a(ceh var1) {
      bmb _snowman = new bmb(btw.a(this.a(() -> _snowman)));
      if (this.c != null && !this.c.isEmpty()) {
         _snowman.a("BlockEntityTag").a("Patterns", this.c.d());
      }

      if (this.a != null) {
         _snowman.a(this.a);
      }

      return _snowman;
   }

   public bkx a(Supplier<ceh> var1) {
      if (this.b == null) {
         this.b = ((btm)_snowman.get().b()).b();
      }

      return this.b;
   }
}
