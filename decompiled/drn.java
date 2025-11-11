import com.google.common.collect.Lists;
import com.mojang.blaze3d.systems.RenderSystem;
import java.util.List;
import javax.annotation.Nullable;

public class drn {
   private boq<?> a;
   private final List<drn.a> b = Lists.newArrayList();
   private float c;

   public drn() {
   }

   public void a() {
      this.a = null;
      this.b.clear();
      this.c = 0.0F;
   }

   public void a(bon var1, int var2, int var3) {
      this.b.add(new drn.a(_snowman, _snowman, _snowman));
   }

   public drn.a a(int var1) {
      return this.b.get(_snowman);
   }

   public int b() {
      return this.b.size();
   }

   @Nullable
   public boq<?> c() {
      return this.a;
   }

   public void a(boq<?> var1) {
      this.a = _snowman;
   }

   public void a(dfm var1, djz var2, int var3, int var4, boolean var5, float var6) {
      if (!dot.x()) {
         this.c += _snowman;
      }

      for (int _snowman = 0; _snowman < this.b.size(); _snowman++) {
         drn.a _snowmanx = this.b.get(_snowman);
         int _snowmanxx = _snowmanx.a() + _snowman;
         int _snowmanxxx = _snowmanx.b() + _snowman;
         if (_snowman == 0 && _snowman) {
            dkw.a(_snowman, _snowmanxx - 4, _snowmanxxx - 4, _snowmanxx + 20, _snowmanxxx + 20, 822018048);
         } else {
            dkw.a(_snowman, _snowmanxx, _snowmanxxx, _snowmanxx + 16, _snowmanxxx + 16, 822018048);
         }

         bmb _snowmanxxxx = _snowmanx.c();
         efo _snowmanxxxxx = _snowman.ad();
         _snowmanxxxxx.c(_snowmanxxxx, _snowmanxx, _snowmanxxx);
         RenderSystem.depthFunc(516);
         dkw.a(_snowman, _snowmanxx, _snowmanxxx, _snowmanxx + 16, _snowmanxxx + 16, 822083583);
         RenderSystem.depthFunc(515);
         if (_snowman == 0) {
            _snowmanxxxxx.a(_snowman.g, _snowmanxxxx, _snowmanxx, _snowmanxxx);
         }
      }
   }

   public class a {
      private final bon b;
      private final int c;
      private final int d;

      public a(bon var2, int var3, int var4) {
         this.b = _snowman;
         this.c = _snowman;
         this.d = _snowman;
      }

      public int a() {
         return this.c;
      }

      public int b() {
         return this.d;
      }

      public bmb c() {
         bmb[] _snowman = this.b.a();
         return _snowman[afm.d(drn.this.c / 30.0F) % _snowman.length];
      }
   }
}
