import com.mojang.brigadier.exceptions.CommandSyntaxException;
import java.util.function.Function;
import javax.annotation.Nullable;

public class cdf extends ccj {
   private final nr[] a = new nr[]{oe.d, oe.d, oe.d, oe.d};
   private boolean b = true;
   private bfw c;
   private final afa[] g = new afa[4];
   private bkx h = bkx.p;

   public cdf() {
      super(cck.h);
   }

   @Override
   public md a(md var1) {
      super.a(_snowman);

      for (int _snowman = 0; _snowman < 4; _snowman++) {
         String _snowmanx = nr.a.a(this.a[_snowman]);
         _snowman.a("Text" + (_snowman + 1), _snowmanx);
      }

      _snowman.a("Color", this.h.c());
      return _snowman;
   }

   @Override
   public void a(ceh var1, md var2) {
      this.b = false;
      super.a(_snowman, _snowman);
      this.h = bkx.a(_snowman.l("Color"), bkx.p);

      for (int _snowman = 0; _snowman < 4; _snowman++) {
         String _snowmanx = _snowman.l("Text" + (_snowman + 1));
         nr _snowmanxx = nr.a.a(_snowmanx.isEmpty() ? "\"\"" : _snowmanx);
         if (this.d instanceof aag) {
            try {
               this.a[_snowman] = ns.a(this.a(null), _snowmanxx, null, 0);
            } catch (CommandSyntaxException var7) {
               this.a[_snowman] = _snowmanxx;
            }
         } else {
            this.a[_snowman] = _snowmanxx;
         }

         this.g[_snowman] = null;
      }
   }

   public nr a(int var1) {
      return this.a[_snowman];
   }

   public void a(int var1, nr var2) {
      this.a[_snowman] = _snowman;
      this.g[_snowman] = null;
   }

   @Nullable
   public afa a(int var1, Function<nr, afa> var2) {
      if (this.g[_snowman] == null && this.a[_snowman] != null) {
         this.g[_snowman] = _snowman.apply(this.a[_snowman]);
      }

      return this.g[_snowman];
   }

   @Nullable
   @Override
   public ow a() {
      return new ow(this.e, 9, this.b());
   }

   @Override
   public md b() {
      return this.a(new md());
   }

   @Override
   public boolean t() {
      return true;
   }

   public boolean d() {
      return this.b;
   }

   public void a(boolean var1) {
      this.b = _snowman;
      if (!_snowman) {
         this.c = null;
      }
   }

   public void a(bfw var1) {
      this.c = _snowman;
   }

   public bfw f() {
      return this.c;
   }

   public boolean b(bfw var1) {
      for (nr _snowman : this.a) {
         ob _snowmanx = _snowman == null ? null : _snowman.c();
         if (_snowmanx != null && _snowmanx.h() != null) {
            np _snowmanxx = _snowmanx.h();
            if (_snowmanxx.a() == np.a.c) {
               _snowman.ch().aD().a(this.a((aah)_snowman), _snowmanxx.b());
            }
         }
      }

      return true;
   }

   public db a(@Nullable aah var1) {
      String _snowman = _snowman == null ? "Sign" : _snowman.R().getString();
      nr _snowmanx = (nr)(_snowman == null ? new oe("Sign") : _snowman.d());
      return new db(da.a_, dcn.a(this.e), dcm.a, (aag)this.d, 2, _snowman, _snowmanx, this.d.l(), _snowman);
   }

   public bkx g() {
      return this.h;
   }

   public boolean a(bkx var1) {
      if (_snowman != this.g()) {
         this.h = _snowman;
         this.X_();
         this.d.a(this.o(), this.p(), this.p(), 3);
         return true;
      } else {
         return false;
      }
   }
}
