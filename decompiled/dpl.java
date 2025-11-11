public class dpl extends dol {
   public djw c;
   public long p;
   private dpk q;
   private dlj r;

   public dpl(dot var1, dkd var2) {
      super(_snowman, _snowman, new of("controls.title"));
   }

   @Override
   protected void b() {
      this.a(new dlj(this.k / 2 - 155, 18, 150, 20, new of("options.mouse_settings"), var1 -> this.i.a(new doj(this, this.b))));
      this.a(dkc.E.a(this.b, this.k / 2 - 155 + 160, 18, 150));
      this.q = new dpk(this, this.i);
      this.e.add(this.q);
      this.r = this.a(new dlj(this.k / 2 - 155, this.l - 29, 150, 20, new of("controls.resetAll"), var1 -> {
         for (djw _snowman : this.b.aF) {
            _snowman.b(_snowman.h());
         }

         djw.c();
      }));
      this.a(new dlj(this.k / 2 - 155 + 160, this.l - 29, 150, 20, nq.c, var1 -> this.i.a(this.a)));
   }

   @Override
   public boolean a(double var1, double var3, int var5) {
      if (this.c != null) {
         this.b.a(this.c, deo.b.c.a(_snowman));
         this.c = null;
         djw.c();
         return true;
      } else {
         return super.a(_snowman, _snowman, _snowman);
      }
   }

   @Override
   public boolean a(int var1, int var2, int var3) {
      if (this.c != null) {
         if (_snowman == 256) {
            this.b.a(this.c, deo.a);
         } else {
            this.b.a(this.c, deo.a(_snowman, _snowman));
         }

         this.c = null;
         this.p = x.b();
         djw.c();
         return true;
      } else {
         return super.a(_snowman, _snowman, _snowman);
      }
   }

   @Override
   public void a(dfm var1, int var2, int var3, float var4) {
      this.a(_snowman);
      this.q.a(_snowman, _snowman, _snowman, _snowman);
      a(_snowman, this.o, this.d, this.k / 2, 8, 16777215);
      boolean _snowman = false;

      for (djw _snowmanx : this.b.aF) {
         if (!_snowmanx.k()) {
            _snowman = true;
            break;
         }
      }

      this.r.o = _snowman;
      super.a(_snowman, _snowman, _snowman, _snowman);
   }
}
