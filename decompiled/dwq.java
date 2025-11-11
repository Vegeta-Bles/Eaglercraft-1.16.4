import com.google.common.collect.Maps;
import java.util.Map;
import java.util.Map.Entry;
import javax.annotation.Nullable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class dwq {
   private static final Logger a = LogManager.getLogger();
   private final djz b;
   private final z c = new z();
   private final Map<y, aa> d = Maps.newHashMap();
   @Nullable
   private dwq.a e;
   @Nullable
   private y f;

   public dwq(djz var1) {
      this.b = _snowman;
   }

   public void a(rt var1) {
      if (_snowman.e()) {
         this.c.a();
         this.d.clear();
      }

      this.c.a(_snowman.c());
      this.c.a(_snowman.b());

      for (Entry<vk, aa> _snowman : _snowman.d().entrySet()) {
         y _snowmanx = this.c.a(_snowman.getKey());
         if (_snowmanx != null) {
            aa _snowmanxx = _snowman.getValue();
            _snowmanxx.a(_snowmanx.f(), _snowmanx.i());
            this.d.put(_snowmanx, _snowmanxx);
            if (this.e != null) {
               this.e.a(_snowmanx, _snowmanxx);
            }

            if (!_snowman.e() && _snowmanxx.a() && _snowmanx.c() != null && _snowmanx.c().h()) {
               this.b.an().a(new dmn(_snowmanx));
            }
         } else {
            a.warn("Server informed client about progress for unknown advancement {}", _snowman.getKey());
         }
      }
   }

   public z a() {
      return this.c;
   }

   public void a(@Nullable y var1, boolean var2) {
      dwu _snowman = this.b.w();
      if (_snowman != null && _snowman != null && _snowman) {
         _snowman.a(tg.a(_snowman));
      }

      if (this.f != _snowman) {
         this.f = _snowman;
         if (this.e != null) {
            this.e.e(_snowman);
         }
      }
   }

   public void a(@Nullable dwq.a var1) {
      this.e = _snowman;
      this.c.a(_snowman);
      if (_snowman != null) {
         for (Entry<y, aa> _snowman : this.d.entrySet()) {
            _snowman.a(_snowman.getKey(), _snowman.getValue());
         }

         _snowman.e(this.f);
      }
   }

   public interface a extends z.a {
      void a(y var1, aa var2);

      void e(@Nullable y var1);
   }
}
