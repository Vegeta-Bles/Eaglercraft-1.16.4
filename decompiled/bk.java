import com.google.gson.JsonObject;
import java.util.Collection;

public class bk extends ck<bk.a> {
   private static final vk a = new vk("fishing_rod_hooked");

   public bk() {
   }

   @Override
   public vk a() {
      return a;
   }

   public bk.a a(JsonObject var1, bg.b var2, ax var3) {
      bq _snowman = bq.a(_snowman.get("rod"));
      bg.b _snowmanx = bg.b.a(_snowman, "entity", _snowman);
      bq _snowmanxx = bq.a(_snowman.get("item"));
      return new bk.a(_snowman, _snowman, _snowmanx, _snowmanxx);
   }

   public void a(aah var1, bmb var2, bgi var3, Collection<bmb> var4) {
      cyv _snowman = bg.b(_snowman, (aqa)(_snowman.k() != null ? _snowman.k() : _snowman));
      this.a(_snowman, var3x -> var3x.a(_snowman, _snowman, _snowman));
   }

   public static class a extends al {
      private final bq a;
      private final bg.b b;
      private final bq c;

      public a(bg.b var1, bq var2, bg.b var3, bq var4) {
         super(bk.a, _snowman);
         this.a = _snowman;
         this.b = _snowman;
         this.c = _snowman;
      }

      public static bk.a a(bq var0, bg var1, bq var2) {
         return new bk.a(bg.b.a, _snowman, bg.b.a(_snowman), _snowman);
      }

      public boolean a(bmb var1, cyv var2, Collection<bmb> var3) {
         if (!this.a.a(_snowman)) {
            return false;
         } else if (!this.b.a(_snowman)) {
            return false;
         } else {
            if (this.c != bq.a) {
               boolean _snowman = false;
               aqa _snowmanx = _snowman.c(dbc.a);
               if (_snowmanx instanceof bcv) {
                  bcv _snowmanxx = (bcv)_snowmanx;
                  if (this.c.a(_snowmanxx.g())) {
                     _snowman = true;
                  }
               }

               for (bmb _snowmanxx : _snowman) {
                  if (this.c.a(_snowmanxx)) {
                     _snowman = true;
                     break;
                  }
               }

               if (!_snowman) {
                  return false;
               }
            }

            return true;
         }
      }

      @Override
      public JsonObject a(ci var1) {
         JsonObject _snowman = super.a(_snowman);
         _snowman.add("rod", this.a.a());
         _snowman.add("entity", this.b.a(_snowman));
         _snowman.add("item", this.c.a());
         return _snowman;
      }
   }
}
