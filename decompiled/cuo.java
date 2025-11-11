import javax.annotation.Nullable;

public class cuo implements cup {
   @Nullable
   private final cul<?, ?> a;
   @Nullable
   private final cul<?, ?> b;

   public cuo(cgj var1, boolean var2, boolean var3) {
      this.a = _snowman ? new cug(_snowman) : null;
      this.b = _snowman ? new cuq(_snowman) : null;
   }

   public void a(fx var1) {
      if (this.a != null) {
         this.a.a(_snowman);
      }

      if (this.b != null) {
         this.b.a(_snowman);
      }
   }

   public void a(fx var1, int var2) {
      if (this.a != null) {
         this.a.a(_snowman, _snowman);
      }
   }

   public boolean a() {
      return this.b != null && this.b.a() ? true : this.a != null && this.a.a();
   }

   public int a(int var1, boolean var2, boolean var3) {
      if (this.a != null && this.b != null) {
         int _snowman = _snowman / 2;
         int _snowmanx = this.a.a(_snowman, _snowman, _snowman);
         int _snowmanxx = _snowman - _snowman + _snowmanx;
         int _snowmanxxx = this.b.a(_snowmanxx, _snowman, _snowman);
         return _snowmanx == 0 && _snowmanxxx > 0 ? this.a.a(_snowmanxxx, _snowman, _snowman) : _snowmanxxx;
      } else if (this.a != null) {
         return this.a.a(_snowman, _snowman, _snowman);
      } else {
         return this.b != null ? this.b.a(_snowman, _snowman, _snowman) : _snowman;
      }
   }

   @Override
   public void a(gp var1, boolean var2) {
      if (this.a != null) {
         this.a.a(_snowman, _snowman);
      }

      if (this.b != null) {
         this.b.a(_snowman, _snowman);
      }
   }

   public void a(brd var1, boolean var2) {
      if (this.a != null) {
         this.a.a(_snowman, _snowman);
      }

      if (this.b != null) {
         this.b.a(_snowman, _snowman);
      }
   }

   public cum a(bsf var1) {
      if (_snowman == bsf.b) {
         return (cum)(this.a == null ? cum.a.a : this.a);
      } else {
         return (cum)(this.b == null ? cum.a.a : this.b);
      }
   }

   public String a(bsf var1, gp var2) {
      if (_snowman == bsf.b) {
         if (this.a != null) {
            return this.a.b(_snowman.s());
         }
      } else if (this.b != null) {
         return this.b.b(_snowman.s());
      }

      return "n/a";
   }

   public void a(bsf var1, gp var2, @Nullable cgb var3, boolean var4) {
      if (_snowman == bsf.b) {
         if (this.a != null) {
            this.a.a(_snowman.s(), _snowman, _snowman);
         }
      } else if (this.b != null) {
         this.b.a(_snowman.s(), _snowman, _snowman);
      }
   }

   public void b(brd var1, boolean var2) {
      if (this.a != null) {
         this.a.b(_snowman, _snowman);
      }

      if (this.b != null) {
         this.b.b(_snowman, _snowman);
      }
   }

   public int b(fx var1, int var2) {
      int _snowman = this.b == null ? 0 : this.b.b(_snowman) - _snowman;
      int _snowmanx = this.a == null ? 0 : this.a.b(_snowman);
      return Math.max(_snowmanx, _snowman);
   }
}
