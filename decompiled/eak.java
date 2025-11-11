import com.google.common.collect.Lists;
import com.mojang.blaze3d.systems.RenderSystem;
import java.io.IOException;
import java.util.List;
import java.util.function.IntSupplier;

public class eak implements AutoCloseable {
   private final dzw c;
   public final deg a;
   public final deg b;
   private final List<IntSupplier> d = Lists.newArrayList();
   private final List<String> e = Lists.newArrayList();
   private final List<Integer> f = Lists.newArrayList();
   private final List<Integer> g = Lists.newArrayList();
   private b h;

   public eak(ach var1, String var2, deg var3, deg var4) throws IOException {
      this.c = new dzw(_snowman, _snowman);
      this.a = _snowman;
      this.b = _snowman;
   }

   @Override
   public void close() {
      this.c.close();
   }

   public void a(String var1, IntSupplier var2, int var3, int var4) {
      this.e.add(this.e.size(), _snowman);
      this.d.add(this.d.size(), _snowman);
      this.f.add(this.f.size(), _snowman);
      this.g.add(this.g.size(), _snowman);
   }

   public void a(b var1) {
      this.h = _snowman;
   }

   public void a(float var1) {
      this.a.e();
      float _snowman = (float)this.b.a;
      float _snowmanx = (float)this.b.b;
      RenderSystem.viewport(0, 0, (int)_snowman, (int)_snowmanx);
      this.c.a("DiffuseSampler", this.a::f);

      for (int _snowmanxx = 0; _snowmanxx < this.d.size(); _snowmanxx++) {
         this.c.a(this.e.get(_snowmanxx), this.d.get(_snowmanxx));
         this.c.b("AuxSize" + _snowmanxx).a((float)this.f.get(_snowmanxx).intValue(), (float)this.g.get(_snowmanxx).intValue());
      }

      this.c.b("ProjMat").a(this.h);
      this.c.b("InSize").a((float)this.a.a, (float)this.a.b);
      this.c.b("OutSize").a(_snowman, _snowmanx);
      this.c.b("Time").a(_snowman);
      djz _snowmanxx = djz.C();
      this.c.b("ScreenSize").a((float)_snowmanxx.aD().k(), (float)_snowmanxx.aD().l());
      this.c.f();
      this.b.b(djz.a);
      this.b.a(false);
      RenderSystem.depthFunc(519);
      dfh _snowmanxxx = dfo.a().c();
      _snowmanxxx.a(7, dfk.l);
      _snowmanxxx.a(0.0, 0.0, 500.0).a(255, 255, 255, 255).d();
      _snowmanxxx.a((double)_snowman, 0.0, 500.0).a(255, 255, 255, 255).d();
      _snowmanxxx.a((double)_snowman, (double)_snowmanx, 500.0).a(255, 255, 255, 255).d();
      _snowmanxxx.a(0.0, (double)_snowmanx, 500.0).a(255, 255, 255, 255).d();
      _snowmanxxx.c();
      dfi.a(_snowmanxxx);
      RenderSystem.depthFunc(515);
      this.c.e();
      this.b.e();
      this.a.d();

      for (Object _snowmanxxxx : this.d) {
         if (_snowmanxxxx instanceof deg) {
            ((deg)_snowmanxxxx).d();
         }
      }
   }

   public dzw b() {
      return this.c;
   }
}
