import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;
import java.util.List;
import java.util.Map;
import javax.annotation.Nullable;

public class bq {
   public static final bq a = new bq();
   @Nullable
   private final ael<blx> b;
   @Nullable
   private final blx c;
   private final bz.d d;
   private final bz.d e;
   private final bb[] f;
   private final bb[] g;
   @Nullable
   private final bnt h;
   private final cb i;

   public bq() {
      this.b = null;
      this.c = null;
      this.h = null;
      this.d = bz.d.e;
      this.e = bz.d.e;
      this.f = bb.b;
      this.g = bb.b;
      this.i = cb.a;
   }

   public bq(@Nullable ael<blx> var1, @Nullable blx var2, bz.d var3, bz.d var4, bb[] var5, bb[] var6, @Nullable bnt var7, cb var8) {
      this.b = _snowman;
      this.c = _snowman;
      this.d = _snowman;
      this.e = _snowman;
      this.f = _snowman;
      this.g = _snowman;
      this.h = _snowman;
      this.i = _snowman;
   }

   public boolean a(bmb var1) {
      if (this == a) {
         return true;
      } else if (this.b != null && !this.b.a(_snowman.b())) {
         return false;
      } else if (this.c != null && _snowman.b() != this.c) {
         return false;
      } else if (!this.d.d(_snowman.E())) {
         return false;
      } else if (!this.e.c() && !_snowman.e()) {
         return false;
      } else if (!this.e.d(_snowman.h() - _snowman.g())) {
         return false;
      } else if (!this.i.a(_snowman)) {
         return false;
      } else {
         if (this.f.length > 0) {
            Map<bps, Integer> _snowman = bpu.a(_snowman.q());

            for (bb _snowmanx : this.f) {
               if (!_snowmanx.a(_snowman)) {
                  return false;
               }
            }
         }

         if (this.g.length > 0) {
            Map<bps, Integer> _snowman = bpu.a(blf.d(_snowman));

            for (bb _snowmanxx : this.g) {
               if (!_snowmanxx.a(_snowman)) {
                  return false;
               }
            }
         }

         bnt _snowman = bnv.d(_snowman);
         return this.h == null || this.h == _snowman;
      }
   }

   public static bq a(@Nullable JsonElement var0) {
      if (_snowman != null && !_snowman.isJsonNull()) {
         JsonObject _snowman = afd.m(_snowman, "item");
         bz.d _snowmanx = bz.d.a(_snowman.get("count"));
         bz.d _snowmanxx = bz.d.a(_snowman.get("durability"));
         if (_snowman.has("data")) {
            throw new JsonParseException("Disallowed data tag found");
         } else {
            cb _snowmanxxx = cb.a(_snowman.get("nbt"));
            blx _snowmanxxxx = null;
            if (_snowman.has("item")) {
               vk _snowmanxxxxx = new vk(afd.h(_snowman, "item"));
               _snowmanxxxx = gm.T.b(_snowmanxxxxx).orElseThrow(() -> new JsonSyntaxException("Unknown item id '" + _snowman + "'"));
            }

            ael<blx> _snowmanxxxxx = null;
            if (_snowman.has("tag")) {
               vk _snowmanxxxxxx = new vk(afd.h(_snowman, "tag"));
               _snowmanxxxxx = aeh.a().b().a(_snowmanxxxxxx);
               if (_snowmanxxxxx == null) {
                  throw new JsonSyntaxException("Unknown item tag '" + _snowmanxxxxxx + "'");
               }
            }

            bnt _snowmanxxxxxx = null;
            if (_snowman.has("potion")) {
               vk _snowmanxxxxxxx = new vk(afd.h(_snowman, "potion"));
               _snowmanxxxxxx = gm.U.b(_snowmanxxxxxxx).orElseThrow(() -> new JsonSyntaxException("Unknown potion '" + _snowman + "'"));
            }

            bb[] _snowmanxxxxxxx = bb.b(_snowman.get("enchantments"));
            bb[] _snowmanxxxxxxxx = bb.b(_snowman.get("stored_enchantments"));
            return new bq(_snowmanxxxxx, _snowmanxxxx, _snowmanx, _snowmanxx, _snowmanxxxxxxx, _snowmanxxxxxxxx, _snowmanxxxxxx, _snowmanxxx);
         }
      } else {
         return a;
      }
   }

   public JsonElement a() {
      if (this == a) {
         return JsonNull.INSTANCE;
      } else {
         JsonObject _snowman = new JsonObject();
         if (this.c != null) {
            _snowman.addProperty("item", gm.T.b(this.c).toString());
         }

         if (this.b != null) {
            _snowman.addProperty("tag", aeh.a().b().b(this.b).toString());
         }

         _snowman.add("count", this.d.d());
         _snowman.add("durability", this.e.d());
         _snowman.add("nbt", this.i.a());
         if (this.f.length > 0) {
            JsonArray _snowmanx = new JsonArray();

            for (bb _snowmanxx : this.f) {
               _snowmanx.add(_snowmanxx.a());
            }

            _snowman.add("enchantments", _snowmanx);
         }

         if (this.g.length > 0) {
            JsonArray _snowmanx = new JsonArray();

            for (bb _snowmanxx : this.g) {
               _snowmanx.add(_snowmanxx.a());
            }

            _snowman.add("stored_enchantments", _snowmanx);
         }

         if (this.h != null) {
            _snowman.addProperty("potion", gm.U.b(this.h).toString());
         }

         return _snowman;
      }
   }

   public static bq[] b(@Nullable JsonElement var0) {
      if (_snowman != null && !_snowman.isJsonNull()) {
         JsonArray _snowman = afd.n(_snowman, "items");
         bq[] _snowmanx = new bq[_snowman.size()];

         for (int _snowmanxx = 0; _snowmanxx < _snowmanx.length; _snowmanxx++) {
            _snowmanx[_snowmanxx] = a(_snowman.get(_snowmanxx));
         }

         return _snowmanx;
      } else {
         return new bq[0];
      }
   }

   public static class a {
      private final List<bb> a = Lists.newArrayList();
      private final List<bb> b = Lists.newArrayList();
      @Nullable
      private blx c;
      @Nullable
      private ael<blx> d;
      private bz.d e;
      private bz.d f;
      @Nullable
      private bnt g;
      private cb h;

      private a() {
         this.e = bz.d.e;
         this.f = bz.d.e;
         this.h = cb.a;
      }

      public static bq.a a() {
         return new bq.a();
      }

      public bq.a a(brw var1) {
         this.c = _snowman.h();
         return this;
      }

      public bq.a a(ael<blx> var1) {
         this.d = _snowman;
         return this;
      }

      public bq.a a(md var1) {
         this.h = new cb(_snowman);
         return this;
      }

      public bq.a a(bb var1) {
         this.a.add(_snowman);
         return this;
      }

      public bq b() {
         return new bq(this.d, this.c, this.e, this.f, this.a.toArray(bb.b), this.b.toArray(bb.b), this.g, this.h);
      }
   }
}
