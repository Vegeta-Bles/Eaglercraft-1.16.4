import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import java.util.List;

public class bn extends ck<bn.a> {
   private static final vk a = new vk("inventory_changed");

   public bn() {
   }

   @Override
   public vk a() {
      return a;
   }

   public bn.a a(JsonObject var1, bg.b var2, ax var3) {
      JsonObject _snowman = afd.a(_snowman, "slots", new JsonObject());
      bz.d _snowmanx = bz.d.a(_snowman.get("occupied"));
      bz.d _snowmanxx = bz.d.a(_snowman.get("full"));
      bz.d _snowmanxxx = bz.d.a(_snowman.get("empty"));
      bq[] _snowmanxxxx = bq.b(_snowman.get("items"));
      return new bn.a(_snowman, _snowmanx, _snowmanxx, _snowmanxxx, _snowmanxxxx);
   }

   public void a(aah var1, bfv var2, bmb var3) {
      int _snowman = 0;
      int _snowmanx = 0;
      int _snowmanxx = 0;

      for (int _snowmanxxx = 0; _snowmanxxx < _snowman.Z_(); _snowmanxxx++) {
         bmb _snowmanxxxx = _snowman.a(_snowmanxxx);
         if (_snowmanxxxx.a()) {
            _snowmanx++;
         } else {
            _snowmanxx++;
            if (_snowmanxxxx.E() >= _snowmanxxxx.c()) {
               _snowman++;
            }
         }
      }

      this.a(_snowman, _snowman, _snowman, _snowman, _snowmanx, _snowmanxx);
   }

   private void a(aah var1, bfv var2, bmb var3, int var4, int var5, int var6) {
      this.a(_snowman, var5x -> var5x.a(_snowman, _snowman, _snowman, _snowman, _snowman));
   }

   public static class a extends al {
      private final bz.d a;
      private final bz.d b;
      private final bz.d c;
      private final bq[] d;

      public a(bg.b var1, bz.d var2, bz.d var3, bz.d var4, bq[] var5) {
         super(bn.a, _snowman);
         this.a = _snowman;
         this.b = _snowman;
         this.c = _snowman;
         this.d = _snowman;
      }

      public static bn.a a(bq... var0) {
         return new bn.a(bg.b.a, bz.d.e, bz.d.e, bz.d.e, _snowman);
      }

      public static bn.a a(brw... var0) {
         bq[] _snowman = new bq[_snowman.length];

         for (int _snowmanx = 0; _snowmanx < _snowman.length; _snowmanx++) {
            _snowman[_snowmanx] = new bq(null, _snowman[_snowmanx].h(), bz.d.e, bz.d.e, bb.b, bb.b, null, cb.a);
         }

         return a(_snowman);
      }

      @Override
      public JsonObject a(ci var1) {
         JsonObject _snowman = super.a(_snowman);
         if (!this.a.c() || !this.b.c() || !this.c.c()) {
            JsonObject _snowmanx = new JsonObject();
            _snowmanx.add("occupied", this.a.d());
            _snowmanx.add("full", this.b.d());
            _snowmanx.add("empty", this.c.d());
            _snowman.add("slots", _snowmanx);
         }

         if (this.d.length > 0) {
            JsonArray _snowmanx = new JsonArray();

            for (bq _snowmanxx : this.d) {
               _snowmanx.add(_snowmanxx.a());
            }

            _snowman.add("items", _snowmanx);
         }

         return _snowman;
      }

      public boolean a(bfv var1, bmb var2, int var3, int var4, int var5) {
         if (!this.b.d(_snowman)) {
            return false;
         } else if (!this.c.d(_snowman)) {
            return false;
         } else if (!this.a.d(_snowman)) {
            return false;
         } else {
            int _snowman = this.d.length;
            if (_snowman == 0) {
               return true;
            } else if (_snowman != 1) {
               List<bq> _snowmanx = new ObjectArrayList(this.d);
               int _snowmanxx = _snowman.Z_();

               for (int _snowmanxxx = 0; _snowmanxxx < _snowmanxx; _snowmanxxx++) {
                  if (_snowmanx.isEmpty()) {
                     return true;
                  }

                  bmb _snowmanxxxx = _snowman.a(_snowmanxxx);
                  if (!_snowmanxxxx.a()) {
                     _snowmanx.removeIf(var1x -> var1x.a(_snowman));
                  }
               }

               return _snowmanx.isEmpty();
            } else {
               return !_snowman.a() && this.d[0].a(_snowman);
            }
         }
      }
   }
}
