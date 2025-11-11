import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import java.util.function.Predicate;
import javax.annotation.Nullable;

public class bg {
   public static final bg a = new bg(bh.a, ay.a, bw.a, ca.a, cb.a, be.a, bd.a, cg.a, bj.a, null, null);
   private final bh b;
   private final ay c;
   private final bw d;
   private final ca e;
   private final cb f;
   private final be g;
   private final bd h;
   private final cg i;
   private final bj j;
   private final bg k;
   private final bg l;
   @Nullable
   private final String m;
   @Nullable
   private final vk n;

   private bg(bh var1, ay var2, bw var3, ca var4, cb var5, be var6, bd var7, cg var8, bj var9, @Nullable String var10, @Nullable vk var11) {
      this.b = _snowman;
      this.c = _snowman;
      this.d = _snowman;
      this.e = _snowman;
      this.f = _snowman;
      this.g = _snowman;
      this.h = _snowman;
      this.i = _snowman;
      this.j = _snowman;
      this.k = this;
      this.l = this;
      this.m = _snowman;
      this.n = _snowman;
   }

   private bg(bh var1, ay var2, bw var3, ca var4, cb var5, be var6, bd var7, cg var8, bj var9, bg var10, bg var11, @Nullable String var12, @Nullable vk var13) {
      this.b = _snowman;
      this.c = _snowman;
      this.d = _snowman;
      this.e = _snowman;
      this.f = _snowman;
      this.g = _snowman;
      this.h = _snowman;
      this.i = _snowman;
      this.j = _snowman;
      this.k = _snowman;
      this.l = _snowman;
      this.m = _snowman;
      this.n = _snowman;
   }

   public boolean a(aah var1, @Nullable aqa var2) {
      return this.a(_snowman.u(), _snowman.cA(), _snowman);
   }

   public boolean a(aag var1, @Nullable dcn var2, @Nullable aqa var3) {
      if (this == a) {
         return true;
      } else if (_snowman == null) {
         return false;
      } else if (!this.b.a(_snowman.X())) {
         return false;
      } else {
         if (_snowman == null) {
            if (this.c != ay.a) {
               return false;
            }
         } else if (!this.c.a(_snowman.b, _snowman.c, _snowman.d, _snowman.cD(), _snowman.cE(), _snowman.cH())) {
            return false;
         }

         if (!this.d.a(_snowman, _snowman.cD(), _snowman.cE(), _snowman.cH())) {
            return false;
         } else if (!this.e.a(_snowman)) {
            return false;
         } else if (!this.f.a(_snowman)) {
            return false;
         } else if (!this.g.a(_snowman)) {
            return false;
         } else if (!this.h.a(_snowman)) {
            return false;
         } else if (!this.i.a(_snowman)) {
            return false;
         } else if (!this.j.a(_snowman)) {
            return false;
         } else if (!this.k.a(_snowman, _snowman, _snowman.ct())) {
            return false;
         } else if (!this.l.a(_snowman, _snowman, _snowman instanceof aqn ? ((aqn)_snowman).A() : null)) {
            return false;
         } else {
            if (this.m != null) {
               ddp _snowman = _snowman.bG();
               if (_snowman == null || !this.m.equals(_snowman.b())) {
                  return false;
               }
            }

            return this.n == null || _snowman instanceof bab && ((bab)_snowman).eU().equals(this.n);
         }
      }
   }

   public static bg a(@Nullable JsonElement var0) {
      if (_snowman != null && !_snowman.isJsonNull()) {
         JsonObject _snowman = afd.m(_snowman, "entity");
         bh _snowmanx = bh.a(_snowman.get("type"));
         ay _snowmanxx = ay.a(_snowman.get("distance"));
         bw _snowmanxxx = bw.a(_snowman.get("location"));
         ca _snowmanxxxx = ca.a(_snowman.get("effects"));
         cb _snowmanxxxxx = cb.a(_snowman.get("nbt"));
         be _snowmanxxxxxx = be.a(_snowman.get("flags"));
         bd _snowmanxxxxxxx = bd.a(_snowman.get("equipment"));
         cg _snowmanxxxxxxxx = cg.a(_snowman.get("player"));
         bj _snowmanxxxxxxxxx = bj.a(_snowman.get("fishing_hook"));
         bg _snowmanxxxxxxxxxx = a(_snowman.get("vehicle"));
         bg _snowmanxxxxxxxxxxx = a(_snowman.get("targeted_entity"));
         String _snowmanxxxxxxxxxxxx = afd.a(_snowman, "team", null);
         vk _snowmanxxxxxxxxxxxxx = _snowman.has("catType") ? new vk(afd.h(_snowman, "catType")) : null;
         return new bg.a()
            .a(_snowmanx)
            .a(_snowmanxx)
            .a(_snowmanxxx)
            .a(_snowmanxxxx)
            .a(_snowmanxxxxx)
            .a(_snowmanxxxxxx)
            .a(_snowmanxxxxxxx)
            .a(_snowmanxxxxxxxx)
            .a(_snowmanxxxxxxxxx)
            .a(_snowmanxxxxxxxxxxxx)
            .a(_snowmanxxxxxxxxxx)
            .b(_snowmanxxxxxxxxxxx)
            .b(_snowmanxxxxxxxxxxxxx)
            .b();
      } else {
         return a;
      }
   }

   public JsonElement a() {
      if (this == a) {
         return JsonNull.INSTANCE;
      } else {
         JsonObject _snowman = new JsonObject();
         _snowman.add("type", this.b.a());
         _snowman.add("distance", this.c.a());
         _snowman.add("location", this.d.a());
         _snowman.add("effects", this.e.b());
         _snowman.add("nbt", this.f.a());
         _snowman.add("flags", this.g.a());
         _snowman.add("equipment", this.h.a());
         _snowman.add("player", this.i.a());
         _snowman.add("fishing_hook", this.j.a());
         _snowman.add("vehicle", this.k.a());
         _snowman.add("targeted_entity", this.l.a());
         _snowman.addProperty("team", this.m);
         if (this.n != null) {
            _snowman.addProperty("catType", this.n.toString());
         }

         return _snowman;
      }
   }

   public static cyv b(aah var0, aqa var1) {
      return new cyv.a(_snowman.u()).a(dbc.a, _snowman).a(dbc.f, _snowman.cA()).a(_snowman.cY()).a(dbb.j);
   }

   public static class a {
      private bh a;
      private ay b;
      private bw c;
      private ca d;
      private cb e;
      private be f;
      private bd g;
      private cg h;
      private bj i;
      private bg j;
      private bg k;
      private String l;
      private vk m;

      public a() {
         this.a = bh.a;
         this.b = ay.a;
         this.c = bw.a;
         this.d = ca.a;
         this.e = cb.a;
         this.f = be.a;
         this.g = bd.a;
         this.h = cg.a;
         this.i = bj.a;
         this.j = bg.a;
         this.k = bg.a;
      }

      public static bg.a a() {
         return new bg.a();
      }

      public bg.a a(aqe<?> var1) {
         this.a = bh.b(_snowman);
         return this;
      }

      public bg.a a(ael<aqe<?>> var1) {
         this.a = bh.a(_snowman);
         return this;
      }

      public bg.a a(vk var1) {
         this.m = _snowman;
         return this;
      }

      public bg.a a(bh var1) {
         this.a = _snowman;
         return this;
      }

      public bg.a a(ay var1) {
         this.b = _snowman;
         return this;
      }

      public bg.a a(bw var1) {
         this.c = _snowman;
         return this;
      }

      public bg.a a(ca var1) {
         this.d = _snowman;
         return this;
      }

      public bg.a a(cb var1) {
         this.e = _snowman;
         return this;
      }

      public bg.a a(be var1) {
         this.f = _snowman;
         return this;
      }

      public bg.a a(bd var1) {
         this.g = _snowman;
         return this;
      }

      public bg.a a(cg var1) {
         this.h = _snowman;
         return this;
      }

      public bg.a a(bj var1) {
         this.i = _snowman;
         return this;
      }

      public bg.a a(bg var1) {
         this.j = _snowman;
         return this;
      }

      public bg.a b(bg var1) {
         this.k = _snowman;
         return this;
      }

      public bg.a a(@Nullable String var1) {
         this.l = _snowman;
         return this;
      }

      public bg.a b(@Nullable vk var1) {
         this.m = _snowman;
         return this;
      }

      public bg b() {
         return new bg(this.a, this.b, this.c, this.d, this.e, this.f, this.g, this.h, this.i, this.j, this.k, this.l, this.m);
      }
   }

   public static class b {
      public static final bg.b a = new bg.b(new dbo[0]);
      private final dbo[] b;
      private final Predicate<cyv> c;

      private b(dbo[] var1) {
         this.b = _snowman;
         this.c = dbq.a(_snowman);
      }

      public static bg.b a(dbo... var0) {
         return new bg.b(_snowman);
      }

      public static bg.b a(JsonObject var0, String var1, ax var2) {
         JsonElement _snowman = _snowman.get(_snowman);
         return a(_snowman, _snowman, _snowman);
      }

      public static bg.b[] b(JsonObject var0, String var1, ax var2) {
         JsonElement _snowman = _snowman.get(_snowman);
         if (_snowman != null && !_snowman.isJsonNull()) {
            JsonArray _snowmanx = afd.n(_snowman, _snowman);
            bg.b[] _snowmanxx = new bg.b[_snowmanx.size()];

            for (int _snowmanxxx = 0; _snowmanxxx < _snowmanx.size(); _snowmanxxx++) {
               _snowmanxx[_snowmanxxx] = a(_snowman + "[" + _snowmanxxx + "]", _snowman, _snowmanx.get(_snowmanxxx));
            }

            return _snowmanxx;
         } else {
            return new bg.b[0];
         }
      }

      private static bg.b a(String var0, ax var1, @Nullable JsonElement var2) {
         if (_snowman != null && _snowman.isJsonArray()) {
            dbo[] _snowman = _snowman.a(_snowman.getAsJsonArray(), _snowman.a().toString() + "/" + _snowman, dbb.j);
            return new bg.b(_snowman);
         } else {
            bg _snowman = bg.a(_snowman);
            return a(_snowman);
         }
      }

      public static bg.b a(bg var0) {
         if (_snowman == bg.a) {
            return a;
         } else {
            dbo _snowman = dbr.a(cyv.c.a, _snowman).build();
            return new bg.b(new dbo[]{_snowman});
         }
      }

      public boolean a(cyv var1) {
         return this.c.test(_snowman);
      }

      public JsonElement a(ci var1) {
         return (JsonElement)(this.b.length == 0 ? JsonNull.INSTANCE : _snowman.a(this.b));
      }

      public static JsonElement a(bg.b[] var0, ci var1) {
         if (_snowman.length == 0) {
            return JsonNull.INSTANCE;
         } else {
            JsonArray _snowman = new JsonArray();

            for (bg.b _snowmanx : _snowman) {
               _snowman.add(_snowmanx.a(_snowman));
            }

            return _snowman;
         }
      }
   }
}
