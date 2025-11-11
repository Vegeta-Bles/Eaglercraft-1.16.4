import com.google.common.collect.Maps;
import java.util.Map;
import java.util.Map.Entry;

public class eaw {
   private final Map<ceh, elo> a = Maps.newIdentityHashMap();
   private final elt b;

   public eaw(elt var1) {
      this.b = _snowman;
   }

   public ekc a(ceh var1) {
      return this.b(_snowman).e();
   }

   public elo b(ceh var1) {
      elo _snowman = this.a.get(_snowman);
      if (_snowman == null) {
         _snowman = this.b.a();
      }

      return _snowman;
   }

   public elt a() {
      return this.b;
   }

   public void b() {
      this.a.clear();

      for (buo _snowman : gm.Q) {
         _snowman.m().a().forEach(var1 -> {
            elo var10000 = this.a.put(var1, this.b.a(c(var1)));
         });
      }
   }

   public static elu c(ceh var0) {
      return a(gm.Q.b(_snowman.b()), _snowman);
   }

   public static elu a(vk var0, ceh var1) {
      return new elu(_snowman, a(_snowman.s()));
   }

   public static String a(Map<cfj<?>, Comparable<?>> var0) {
      StringBuilder _snowman = new StringBuilder();

      for (Entry<cfj<?>, Comparable<?>> _snowmanx : _snowman.entrySet()) {
         if (_snowman.length() != 0) {
            _snowman.append(',');
         }

         cfj<?> _snowmanxx = _snowmanx.getKey();
         _snowman.append(_snowmanxx.f());
         _snowman.append('=');
         _snowman.append(a(_snowmanxx, _snowmanx.getValue()));
      }

      return _snowman.toString();
   }

   private static <T extends Comparable<T>> String a(cfj<T> var0, Comparable<?> var1) {
      return _snowman.a((T)_snowman);
   }
}
