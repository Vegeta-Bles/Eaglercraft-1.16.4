import com.google.common.collect.Maps;
import java.util.EnumMap;
import java.util.function.Supplier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class fw {
   private static final Logger c = LogManager.getLogger();
   public static final EnumMap<gc, f> a = x.a(Maps.newEnumMap(gc.class), var0 -> {
      var0.put(gc.d, f.a());
      var0.put(gc.f, new f(null, new d(new g(0.0F, 1.0F, 0.0F), 90.0F, true), null, null));
      var0.put(gc.e, new f(null, new d(new g(0.0F, 1.0F, 0.0F), -90.0F, true), null, null));
      var0.put(gc.c, new f(null, new d(new g(0.0F, 1.0F, 0.0F), 180.0F, true), null, null));
      var0.put(gc.b, new f(null, new d(new g(1.0F, 0.0F, 0.0F), -90.0F, true), null, null));
      var0.put(gc.a, new f(null, new d(new g(1.0F, 0.0F, 0.0F), 90.0F, true), null, null));
   });
   public static final EnumMap<gc, f> b = x.a(Maps.newEnumMap(gc.class), var0 -> {
      for (gc _snowman : gc.values()) {
         var0.put(_snowman, a.get(_snowman).b());
      }
   });

   public static f a(f var0) {
      b _snowman = b.b(0.5F, 0.5F, 0.5F);
      _snowman.a(_snowman.c());
      _snowman.a(b.b(-0.5F, -0.5F, -0.5F));
      return new f(_snowman);
   }

   public static f a(f var0, gc var1, Supplier<String> var2) {
      gc _snowman = gc.a(_snowman.c(), _snowman);
      f _snowmanx = _snowman.b();
      if (_snowmanx == null) {
         c.warn(_snowman.get());
         return new f(null, null, new g(0.0F, 0.0F, 0.0F), null);
      } else {
         f _snowmanxx = b.get(_snowman).a(_snowmanx).a(a.get(_snowman));
         return a(_snowmanxx);
      }
   }
}
