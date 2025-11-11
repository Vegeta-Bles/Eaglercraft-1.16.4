import com.google.common.collect.Queues;
import com.mojang.blaze3d.systems.RenderSystem;
import java.util.Arrays;
import java.util.Deque;
import javax.annotation.Nullable;

public class dmr extends dkw {
   private final djz a;
   private final dmr.a<?>[] b = new dmr.a[5];
   private final Deque<dmq> c = Queues.newArrayDeque();

   public dmr(djz var1) {
      this.a = _snowman;
   }

   public void a(dfm var1) {
      if (!this.a.k.aI) {
         for (int _snowman = 0; _snowman < this.b.length; _snowman++) {
            dmr.a<?> _snowmanx = this.b[_snowman];
            if (_snowmanx != null && _snowmanx.a(this.a.aD().o(), _snowman, _snowman)) {
               this.b[_snowman] = null;
            }

            if (this.b[_snowman] == null && !this.c.isEmpty()) {
               this.b[_snowman] = new dmr.a(this.c.removeFirst());
            }
         }
      }
   }

   @Nullable
   public <T extends dmq> T a(Class<? extends T> var1, Object var2) {
      for (dmr.a<?> _snowman : this.b) {
         if (_snowman != null && _snowman.isAssignableFrom(_snowman.a().getClass()) && _snowman.a().c().equals(_snowman)) {
            return (T)_snowman.a();
         }
      }

      for (dmq _snowmanx : this.c) {
         if (_snowman.isAssignableFrom(_snowmanx.getClass()) && _snowmanx.c().equals(_snowman)) {
            return (T)_snowmanx;
         }
      }

      return null;
   }

   public void a() {
      Arrays.fill(this.b, null);
      this.c.clear();
   }

   public void a(dmq var1) {
      this.c.add(_snowman);
   }

   public djz b() {
      return this.a;
   }

   class a<T extends dmq> {
      private final T b;
      private long c = -1L;
      private long d = -1L;
      private dmq.a e = dmq.a.a;

      private a(T var2) {
         this.b = _snowman;
      }

      public T a() {
         return this.b;
      }

      private float a(long var1) {
         float _snowman = afm.a((float)(_snowman - this.c) / 600.0F, 0.0F, 1.0F);
         _snowman *= _snowman;
         return this.e == dmq.a.b ? 1.0F - _snowman : _snowman;
      }

      public boolean a(int var1, int var2, dfm var3) {
         long _snowman = x.b();
         if (this.c == -1L) {
            this.c = _snowman;
            this.e.a(dmr.this.a.W());
         }

         if (this.e == dmq.a.a && _snowman - this.c <= 600L) {
            this.d = _snowman;
         }

         RenderSystem.pushMatrix();
         RenderSystem.translatef((float)_snowman - (float)this.b.a() * this.a(_snowman), (float)(_snowman * this.b.d()), (float)(800 + _snowman));
         dmq.a _snowmanx = this.b.a(_snowman, dmr.this, _snowman - this.d);
         RenderSystem.popMatrix();
         if (_snowmanx != this.e) {
            this.c = _snowman - (long)((int)((1.0F - this.a(_snowman)) * 600.0F));
            this.e = _snowmanx;
            this.e.a(dmr.this.a.W());
         }

         return this.e == dmq.a.b && _snowman - this.c > 600L;
      }
   }
}
