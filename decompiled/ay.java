import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import javax.annotation.Nullable;

public class ay {
   public static final ay a = new ay(bz.c.e, bz.c.e, bz.c.e, bz.c.e, bz.c.e);
   private final bz.c b;
   private final bz.c c;
   private final bz.c d;
   private final bz.c e;
   private final bz.c f;

   public ay(bz.c var1, bz.c var2, bz.c var3, bz.c var4, bz.c var5) {
      this.b = _snowman;
      this.c = _snowman;
      this.d = _snowman;
      this.e = _snowman;
      this.f = _snowman;
   }

   public static ay a(bz.c var0) {
      return new ay(bz.c.e, bz.c.e, bz.c.e, _snowman, bz.c.e);
   }

   public static ay b(bz.c var0) {
      return new ay(bz.c.e, _snowman, bz.c.e, bz.c.e, bz.c.e);
   }

   public boolean a(double var1, double var3, double var5, double var7, double var9, double var11) {
      float _snowman = (float)(_snowman - _snowman);
      float _snowmanx = (float)(_snowman - _snowman);
      float _snowmanxx = (float)(_snowman - _snowman);
      if (!this.b.d(afm.e(_snowman)) || !this.c.d(afm.e(_snowmanx)) || !this.d.d(afm.e(_snowmanxx))) {
         return false;
      } else {
         return !this.e.a((double)(_snowman * _snowman + _snowmanxx * _snowmanxx)) ? false : this.f.a((double)(_snowman * _snowman + _snowmanx * _snowmanx + _snowmanxx * _snowmanxx));
      }
   }

   public static ay a(@Nullable JsonElement var0) {
      if (_snowman != null && !_snowman.isJsonNull()) {
         JsonObject _snowman = afd.m(_snowman, "distance");
         bz.c _snowmanx = bz.c.a(_snowman.get("x"));
         bz.c _snowmanxx = bz.c.a(_snowman.get("y"));
         bz.c _snowmanxxx = bz.c.a(_snowman.get("z"));
         bz.c _snowmanxxxx = bz.c.a(_snowman.get("horizontal"));
         bz.c _snowmanxxxxx = bz.c.a(_snowman.get("absolute"));
         return new ay(_snowmanx, _snowmanxx, _snowmanxxx, _snowmanxxxx, _snowmanxxxxx);
      } else {
         return a;
      }
   }

   public JsonElement a() {
      if (this == a) {
         return JsonNull.INSTANCE;
      } else {
         JsonObject _snowman = new JsonObject();
         _snowman.add("x", this.b.d());
         _snowman.add("y", this.c.d());
         _snowman.add("z", this.d.d());
         _snowman.add("horizontal", this.e.d());
         _snowman.add("absolute", this.f.d());
         return _snowman;
      }
   }
}
