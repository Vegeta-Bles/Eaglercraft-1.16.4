import com.google.common.collect.Lists;
import com.mojang.blaze3d.systems.RenderSystem;
import java.util.List;

public class dmo implements dmq {
   private static final nr c = new of("recipe.toast.title");
   private static final nr d = new of("recipe.toast.description");
   private final List<boq<?>> e = Lists.newArrayList();
   private long f;
   private boolean g;

   public dmo(boq<?> var1) {
      this.e.add(_snowman);
   }

   @Override
   public dmq.a a(dfm var1, dmr var2, long var3) {
      if (this.g) {
         this.f = _snowman;
         this.g = false;
      }

      if (this.e.isEmpty()) {
         return dmq.a.b;
      } else {
         _snowman.b().M().a(a);
         RenderSystem.color3f(1.0F, 1.0F, 1.0F);
         _snowman.b(_snowman, 0, 0, 0, 32, this.a(), this.d());
         _snowman.b().g.b(_snowman, c, 30.0F, 7.0F, -11534256);
         _snowman.b().g.b(_snowman, d, 30.0F, 18.0F, -16777216);
         boq<?> _snowman = this.e.get((int)(_snowman / Math.max(1L, 5000L / (long)this.e.size()) % (long)this.e.size()));
         bmb _snowmanx = _snowman.h();
         RenderSystem.pushMatrix();
         RenderSystem.scalef(0.6F, 0.6F, 1.0F);
         _snowman.b().ad().c(_snowmanx, 3, 3);
         RenderSystem.popMatrix();
         _snowman.b().ad().c(_snowman.c(), 8, 8);
         return _snowman - this.f >= 5000L ? dmq.a.b : dmq.a.a;
      }
   }

   private void a(boq<?> var1) {
      this.e.add(_snowman);
      this.g = true;
   }

   public static void a(dmr var0, boq<?> var1) {
      dmo _snowman = _snowman.a(dmo.class, b);
      if (_snowman == null) {
         _snowman.a(new dmo(_snowman));
      } else {
         _snowman.a(_snowman);
      }
   }
}
