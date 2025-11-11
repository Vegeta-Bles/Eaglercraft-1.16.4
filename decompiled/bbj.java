import java.util.EnumSet;
import javax.annotation.Nullable;

public class bbj extends bbe {
   private int bw = 47999;

   public bbj(aqe<? extends bbj> var1, brx var2) {
      super(_snowman, _snowman);
   }

   @Override
   public boolean fu() {
      return true;
   }

   @Override
   protected bbe fz() {
      return aqe.aL.a(this.l);
   }

   @Override
   public void b(md var1) {
      super.b(_snowman);
      _snowman.b("DespawnDelay", this.bw);
   }

   @Override
   public void a(md var1) {
      super.a(_snowman);
      if (_snowman.c("DespawnDelay", 99)) {
         this.bw = _snowman.h("DespawnDelay");
      }
   }

   @Override
   protected void o() {
      super.o();
      this.bk.a(1, new awp(this, 2.0));
      this.bl.a(1, new bbj.a(this));
   }

   @Override
   protected void h(bfw var1) {
      aqa _snowman = this.eC();
      if (!(_snowman instanceof bfp)) {
         super.h(_snowman);
      }
   }

   @Override
   public void k() {
      super.k();
      if (!this.l.v) {
         this.fE();
      }
   }

   private void fE() {
      if (this.fF()) {
         this.bw = this.fG() ? ((bfp)this.eC()).eX() - 1 : this.bw - 1;
         if (this.bw <= 0) {
            this.a(true, false);
            this.ad();
         }
      }
   }

   private boolean fF() {
      return !this.eW() && !this.fH() && !this.cq();
   }

   private boolean fG() {
      return this.eC() instanceof bfp;
   }

   private boolean fH() {
      return this.eB() && !this.fG();
   }

   @Nullable
   @Override
   public arc a(bsk var1, aos var2, aqp var3, @Nullable arc var4, @Nullable md var5) {
      if (_snowman == aqp.h) {
         this.c_(0);
      }

      if (_snowman == null) {
         _snowman = new apy.a(false);
      }

      return super.a(_snowman, _snowman, _snowman, _snowman, _snowman);
   }

   public class a extends axx {
      private final bbe b;
      private aqm c;
      private int d;

      public a(bbe var2) {
         super(_snowman, false);
         this.b = _snowman;
         this.a(EnumSet.of(avv.a.d));
      }

      @Override
      public boolean a() {
         if (!this.b.eB()) {
            return false;
         } else {
            aqa _snowman = this.b.eC();
            if (!(_snowman instanceof bfp)) {
               return false;
            } else {
               bfp _snowmanx = (bfp)_snowman;
               this.c = _snowmanx.cZ();
               int _snowmanxx = _snowmanx.da();
               return _snowmanxx != this.d && this.a(this.c, azg.a);
            }
         }
      }

      @Override
      public void c() {
         this.e.h(this.c);
         aqa _snowman = this.b.eC();
         if (_snowman instanceof bfp) {
            this.d = ((bfp)_snowman).da();
         }

         super.c();
      }
   }
}
