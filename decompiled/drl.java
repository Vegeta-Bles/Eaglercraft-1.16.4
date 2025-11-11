import com.mojang.blaze3d.systems.RenderSystem;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.annotation.Nullable;

public abstract class drl extends drp {
   private Iterator<blx> i;
   private Set<blx> j;
   private bjr k;
   private blx l;
   private float m;

   public drl() {
   }

   @Override
   protected void a() {
      this.c.a(152, 182, 28, 18, a);
   }

   @Override
   public void a(@Nullable bjr var1) {
      super.a(_snowman);
      if (_snowman != null && _snowman.d < this.d.i()) {
         this.k = null;
      }
   }

   @Override
   public void a(boq<?> var1, List<bjr> var2) {
      bmb _snowman = _snowman.c();
      this.b.a(_snowman);
      this.b.a(bon.a(_snowman), _snowman.get(2).e, _snowman.get(2).f);
      gj<bon> _snowmanx = _snowman.a();
      this.k = _snowman.get(1);
      if (this.j == null) {
         this.j = this.b();
      }

      this.i = this.j.iterator();
      this.l = null;
      Iterator<bon> _snowmanxx = _snowmanx.iterator();

      for (int _snowmanxxx = 0; _snowmanxxx < 2; _snowmanxxx++) {
         if (!_snowmanxx.hasNext()) {
            return;
         }

         bon _snowmanxxxx = _snowmanxx.next();
         if (!_snowmanxxxx.d()) {
            bjr _snowmanxxxxx = _snowman.get(_snowmanxxx);
            this.b.a(_snowmanxxxx, _snowmanxxxxx.e, _snowmanxxxxx.f);
         }
      }
   }

   protected abstract Set<blx> b();

   @Override
   public void a(dfm var1, int var2, int var3, boolean var4, float var5) {
      super.a(_snowman, _snowman, _snowman, _snowman, _snowman);
      if (this.k != null) {
         if (!dot.x()) {
            this.m += _snowman;
         }

         int _snowman = this.k.e + _snowman;
         int _snowmanx = this.k.f + _snowman;
         dkw.a(_snowman, _snowman, _snowmanx, _snowman + 16, _snowmanx + 16, 822018048);
         this.e.ad().a(this.e.s, this.j().r(), _snowman, _snowmanx);
         RenderSystem.depthFunc(516);
         dkw.a(_snowman, _snowman, _snowmanx, _snowman + 16, _snowmanx + 16, 822083583);
         RenderSystem.depthFunc(515);
      }
   }

   private blx j() {
      if (this.l == null || this.m > 30.0F) {
         this.m = 0.0F;
         if (this.i == null || !this.i.hasNext()) {
            if (this.j == null) {
               this.j = this.b();
            }

            this.i = this.j.iterator();
         }

         this.l = this.i.next();
      }

      return this.l;
   }
}
