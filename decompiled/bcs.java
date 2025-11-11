import com.google.common.collect.Lists;
import java.util.Iterator;
import java.util.List;
import javax.annotation.Nullable;

public class bcs extends bco {
   public bcr e;

   public bcs(aqe<? extends bcs> var1, brx var2) {
      super(_snowman, _snowman);
   }

   public bcs(brx var1, fx var2, gc var3) {
      super(aqe.ad, _snowman, _snowman);
      List<bcr> _snowman = Lists.newArrayList();
      int _snowmanx = 0;

      for (bcr _snowmanxx : gm.X) {
         this.e = _snowmanxx;
         this.a(_snowman);
         if (this.h()) {
            _snowman.add(_snowmanxx);
            int _snowmanxxx = _snowmanxx.a() * _snowmanxx.b();
            if (_snowmanxxx > _snowmanx) {
               _snowmanx = _snowmanxxx;
            }
         }
      }

      if (!_snowman.isEmpty()) {
         Iterator<bcr> _snowmanxxx = _snowman.iterator();

         while (_snowmanxxx.hasNext()) {
            bcr _snowmanxxxx = _snowmanxxx.next();
            if (_snowmanxxxx.a() * _snowmanxxxx.b() < _snowmanx) {
               _snowmanxxx.remove();
            }
         }

         this.e = _snowman.get(this.J.nextInt(_snowman.size()));
      }

      this.a(_snowman);
   }

   public bcs(brx var1, fx var2, gc var3, bcr var4) {
      this(_snowman, _snowman, _snowman);
      this.e = _snowman;
      this.a(_snowman);
   }

   @Override
   public void b(md var1) {
      _snowman.a("Motive", gm.X.b(this.e).toString());
      _snowman.a("Facing", (byte)this.d.d());
      super.b(_snowman);
   }

   @Override
   public void a(md var1) {
      this.e = gm.X.a(vk.a(_snowman.l("Motive")));
      this.d = gc.b(_snowman.f("Facing"));
      super.a(_snowman);
      this.a(this.d);
   }

   @Override
   public int i() {
      return this.e == null ? 1 : this.e.a();
   }

   @Override
   public int k() {
      return this.e == null ? 1 : this.e.b();
   }

   @Override
   public void a(@Nullable aqa var1) {
      if (this.l.V().b(brt.g)) {
         this.a(adq.jI, 1.0F, 1.0F);
         if (_snowman instanceof bfw) {
            bfw _snowman = (bfw)_snowman;
            if (_snowman.bC.d) {
               return;
            }
         }

         this.a(bmd.lz);
      }
   }

   @Override
   public void m() {
      this.a(adq.jJ, 1.0F, 1.0F);
   }

   @Override
   public void b(double var1, double var3, double var5, float var7, float var8) {
      this.d(_snowman, _snowman, _snowman);
   }

   @Override
   public void a(double var1, double var3, double var5, float var7, float var8, int var9, boolean var10) {
      fx _snowman = this.c.a(_snowman - this.cD(), _snowman - this.cE(), _snowman - this.cH());
      this.d((double)_snowman.u(), (double)_snowman.v(), (double)_snowman.w());
   }

   @Override
   public oj<?> P() {
      return new oq(this);
   }
}
