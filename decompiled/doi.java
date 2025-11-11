import com.google.common.collect.Maps;
import java.util.Map;
import javax.annotation.Nullable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class doi {
   private static final Logger a = LogManager.getLogger();
   private static final Map<bje<?>, doi.a<?, ?>> b = Maps.newHashMap();

   public static <T extends bic> void a(@Nullable bje<T> var0, djz var1, int var2, nr var3) {
      if (_snowman == null) {
         a.warn("Trying to open invalid screen with name: {}", _snowman.getString());
      } else {
         doi.a<T, ?> _snowman = a(_snowman);
         if (_snowman == null) {
            a.warn("Failed to create screen for menu type: {}", gm.ac.b(_snowman));
         } else {
            _snowman.a(_snowman, _snowman, _snowman, _snowman);
         }
      }
   }

   @Nullable
   private static <T extends bic> doi.a<T, ?> a(bje<T> var0) {
      return (doi.a<T, ?>)b.get(_snowman);
   }

   private static <M extends bic, U extends dot & dqq<M>> void a(bje<? extends M> var0, doi.a<M, U> var1) {
      doi.a<?, ?> _snowman = b.put(_snowman, _snowman);
      if (_snowman != null) {
         throw new IllegalStateException("Duplicate registration for " + gm.ac.b(_snowman));
      }
   }

   public static boolean a() {
      boolean _snowman = false;

      for (bje<?> _snowmanx : gm.ac) {
         if (!b.containsKey(_snowmanx)) {
            a.debug("Menu {} has no matching screen", gm.ac.b(_snowmanx));
            _snowman = true;
         }
      }

      return _snowman;
   }

   static {
      a(bje.a, dpz::new);
      a(bje.b, dpz::new);
      a(bje.c, dpz::new);
      a(bje.d, dpz::new);
      a(bje.e, dpz::new);
      a(bje.f, dpz::new);
      a(bje.g, dqd::new);
      a(bje.h, dpr::new);
      a(bje.i, dps::new);
      a(bje.j, dpt::new);
      a(bje.k, dpw::new);
      a(bje.l, dqa::new);
      a(bje.m, dqg::new);
      a(bje.n, dqh::new);
      a(bje.o, dqi::new);
      a(bje.p, dqj::new);
      a(bje.q, dqo::new);
      a(bje.r, dqp::new);
      a(bje.s, dqr::new);
      a(bje.t, dqu::new);
      a(bje.u, dqw::new);
      a(bje.v, dqx::new);
      a(bje.w, dpx::new);
      a(bje.x, dqy::new);
   }

   interface a<T extends bic, U extends dot & dqq<T>> {
      default void a(nr var1, bje<T> var2, djz var3, int var4) {
         U _snowman = this.create(_snowman.a(_snowman, _snowman.s.bm), _snowman.s.bm, _snowman);
         _snowman.s.bp = _snowman.h();
         _snowman.a(_snowman);
      }

      U create(T var1, bfv var2, nr var3);
   }
}
