import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.google.gson.JsonObject;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class bs extends ck<bs.a> {
   private static final vk a = new vk("killed_by_crossbow");

   public bs() {
   }

   @Override
   public vk a() {
      return a;
   }

   public bs.a a(JsonObject var1, bg.b var2, ax var3) {
      bg.b[] _snowman = bg.b.b(_snowman, "victims", _snowman);
      bz.d _snowmanx = bz.d.a(_snowman.get("unique_entity_types"));
      return new bs.a(_snowman, _snowman, _snowmanx);
   }

   public void a(aah var1, Collection<aqa> var2) {
      List<cyv> _snowman = Lists.newArrayList();
      Set<aqe<?>> _snowmanx = Sets.newHashSet();

      for (aqa _snowmanxx : _snowman) {
         _snowmanx.add(_snowmanxx.X());
         _snowman.add(bg.b(_snowman, _snowmanxx));
      }

      this.a(_snowman, var2x -> var2x.a(_snowman, _snowman.size()));
   }

   public static class a extends al {
      private final bg.b[] a;
      private final bz.d b;

      public a(bg.b var1, bg.b[] var2, bz.d var3) {
         super(bs.a, _snowman);
         this.a = _snowman;
         this.b = _snowman;
      }

      public static bs.a a(bg.a... var0) {
         bg.b[] _snowman = new bg.b[_snowman.length];

         for (int _snowmanx = 0; _snowmanx < _snowman.length; _snowmanx++) {
            bg.a _snowmanxx = _snowman[_snowmanx];
            _snowman[_snowmanx] = bg.b.a(_snowmanxx.b());
         }

         return new bs.a(bg.b.a, _snowman, bz.d.e);
      }

      public static bs.a a(bz.d var0) {
         bg.b[] _snowman = new bg.b[0];
         return new bs.a(bg.b.a, _snowman, _snowman);
      }

      public boolean a(Collection<cyv> var1, int var2) {
         if (this.a.length > 0) {
            List<cyv> _snowman = Lists.newArrayList(_snowman);

            for (bg.b _snowmanx : this.a) {
               boolean _snowmanxx = false;
               Iterator<cyv> _snowmanxxx = _snowman.iterator();

               while (_snowmanxxx.hasNext()) {
                  cyv _snowmanxxxx = _snowmanxxx.next();
                  if (_snowmanx.a(_snowmanxxxx)) {
                     _snowmanxxx.remove();
                     _snowmanxx = true;
                     break;
                  }
               }

               if (!_snowmanxx) {
                  return false;
               }
            }
         }

         return this.b.d(_snowman);
      }

      @Override
      public JsonObject a(ci var1) {
         JsonObject _snowman = super.a(_snowman);
         _snowman.add("victims", bg.b.a(this.a, _snowman));
         _snowman.add("unique_entity_types", this.b.d());
         return _snowman;
      }
   }
}
