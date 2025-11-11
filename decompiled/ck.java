import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.JsonObject;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;

public abstract class ck<T extends al> implements af<T> {
   private final Map<vt, Set<af.a<T>>> a = Maps.newIdentityHashMap();

   public ck() {
   }

   @Override
   public final void a(vt var1, af.a<T> var2) {
      this.a.computeIfAbsent(_snowman, var0 -> Sets.newHashSet()).add(_snowman);
   }

   @Override
   public final void b(vt var1, af.a<T> var2) {
      Set<af.a<T>> _snowman = this.a.get(_snowman);
      if (_snowman != null) {
         _snowman.remove(_snowman);
         if (_snowman.isEmpty()) {
            this.a.remove(_snowman);
         }
      }
   }

   @Override
   public final void a(vt var1) {
      this.a.remove(_snowman);
   }

   protected abstract T b(JsonObject var1, bg.b var2, ax var3);

   public final T b(JsonObject var1, ax var2) {
      bg.b _snowman = bg.b.a(_snowman, "player", _snowman);
      return this.b(_snowman, _snowman, _snowman);
   }

   protected void a(aah var1, Predicate<T> var2) {
      vt _snowman = _snowman.J();
      Set<af.a<T>> _snowmanx = this.a.get(_snowman);
      if (_snowmanx != null && !_snowmanx.isEmpty()) {
         cyv _snowmanxx = bg.b(_snowman, _snowman);
         List<af.a<T>> _snowmanxxx = null;

         for (af.a<T> _snowmanxxxx : _snowmanx) {
            T _snowmanxxxxx = _snowmanxxxx.a();
            if (_snowmanxxxxx.b().a(_snowmanxx) && _snowman.test(_snowmanxxxxx)) {
               if (_snowmanxxx == null) {
                  _snowmanxxx = Lists.newArrayList();
               }

               _snowmanxxx.add(_snowmanxxxx);
            }
         }

         if (_snowmanxxx != null) {
            for (af.a<T> _snowmanxxxxx : _snowmanxxx) {
               _snowmanxxxxx.a(_snowman);
            }
         }
      }
   }
}
