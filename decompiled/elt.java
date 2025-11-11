import it.unimi.dsi.fastutil.objects.Object2IntMap;
import java.util.Map;
import javax.annotation.Nullable;

public class elt extends ack<els> implements AutoCloseable {
   private Map<vk, elo> a;
   @Nullable
   private ejr b;
   private final eaw c;
   private final ekd d;
   private final dko e;
   private int f;
   private elo g;
   private Object2IntMap<ceh> h;

   public elt(ekd var1, dko var2, int var3) {
      this.d = _snowman;
      this.e = _snowman;
      this.f = _snowman;
      this.c = new eaw(this);
   }

   public elo a(elu var1) {
      return this.a.getOrDefault(_snowman, this.g);
   }

   public elo a() {
      return this.g;
   }

   public eaw b() {
      return this.c;
   }

   protected els a(ach var1, anw var2) {
      _snowman.a();
      els _snowman = new els(_snowman, this.e, _snowman, this.f);
      _snowman.b();
      return _snowman;
   }

   protected void a(els var1, ach var2, anw var3) {
      _snowman.a();
      _snowman.a("upload");
      if (this.b != null) {
         this.b.close();
      }

      this.b = _snowman.a(this.d, _snowman);
      this.a = _snowman.a();
      this.h = _snowman.b();
      this.g = this.a.get(els.l);
      _snowman.b("cache");
      this.c.b();
      _snowman.c();
      _snowman.b();
   }

   public boolean a(ceh var1, ceh var2) {
      if (_snowman == _snowman) {
         return false;
      } else {
         int _snowman = this.h.getInt(_snowman);
         if (_snowman != -1) {
            int _snowmanx = this.h.getInt(_snowman);
            if (_snowman == _snowmanx) {
               cux _snowmanxx = _snowman.m();
               cux _snowmanxxx = _snowman.m();
               return _snowmanxx != _snowmanxxx;
            }
         }

         return true;
      }
   }

   public ekb a(vk var1) {
      return this.b.a(_snowman);
   }

   @Override
   public void close() {
      if (this.b != null) {
         this.b.close();
      }
   }

   public void a(int var1) {
      this.f = _snowman;
   }
}
