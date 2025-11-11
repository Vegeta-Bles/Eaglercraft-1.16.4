import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.mojang.serialization.JsonOps;
import java.util.Optional;
import javax.annotation.Nullable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class bw {
   private static final Logger b = LogManager.getLogger();
   public static final bw a = new bw(bz.c.e, bz.c.e, bz.c.e, null, null, null, null, bv.a, an.a, bl.a);
   private final bz.c c;
   private final bz.c d;
   private final bz.c e;
   @Nullable
   private final vj<bsv> f;
   @Nullable
   private final cla<?> g;
   @Nullable
   private final vj<brx> h;
   @Nullable
   private final Boolean i;
   private final bv j;
   private final an k;
   private final bl l;

   public bw(
      bz.c var1,
      bz.c var2,
      bz.c var3,
      @Nullable vj<bsv> var4,
      @Nullable cla<?> var5,
      @Nullable vj<brx> var6,
      @Nullable Boolean var7,
      bv var8,
      an var9,
      bl var10
   ) {
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
   }

   public static bw a(vj<bsv> var0) {
      return new bw(bz.c.e, bz.c.e, bz.c.e, _snowman, null, null, null, bv.a, an.a, bl.a);
   }

   public static bw b(vj<brx> var0) {
      return new bw(bz.c.e, bz.c.e, bz.c.e, null, null, _snowman, null, bv.a, an.a, bl.a);
   }

   public static bw a(cla<?> var0) {
      return new bw(bz.c.e, bz.c.e, bz.c.e, null, _snowman, null, null, bv.a, an.a, bl.a);
   }

   public boolean a(aag var1, double var2, double var4, double var6) {
      return this.a(_snowman, (float)_snowman, (float)_snowman, (float)_snowman);
   }

   public boolean a(aag var1, float var2, float var3, float var4) {
      if (!this.c.d(_snowman)) {
         return false;
      } else if (!this.d.d(_snowman)) {
         return false;
      } else if (!this.e.d(_snowman)) {
         return false;
      } else if (this.h != null && this.h != _snowman.Y()) {
         return false;
      } else {
         fx _snowman = new fx((double)_snowman, (double)_snowman, (double)_snowman);
         boolean _snowmanx = _snowman.p(_snowman);
         Optional<vj<bsv>> _snowmanxx = _snowman.r().b(gm.ay).c(_snowman.v(_snowman));
         if (!_snowmanxx.isPresent()) {
            return false;
         } else if (this.f == null || _snowmanx && this.f == _snowmanxx.get()) {
            if (this.g == null || _snowmanx && _snowman.a().a(_snowman, true, this.g).e()) {
               if (this.i == null || _snowmanx && this.i == buy.a(_snowman, _snowman)) {
                  if (!this.j.a(_snowman, _snowman)) {
                     return false;
                  } else {
                     return !this.k.a(_snowman, _snowman) ? false : this.l.a(_snowman, _snowman);
                  }
               } else {
                  return false;
               }
            } else {
               return false;
            }
         } else {
            return false;
         }
      }
   }

   public JsonElement a() {
      if (this == a) {
         return JsonNull.INSTANCE;
      } else {
         JsonObject _snowman = new JsonObject();
         if (!this.c.c() || !this.d.c() || !this.e.c()) {
            JsonObject _snowmanx = new JsonObject();
            _snowmanx.add("x", this.c.d());
            _snowmanx.add("y", this.d.d());
            _snowmanx.add("z", this.e.d());
            _snowman.add("position", _snowmanx);
         }

         if (this.h != null) {
            brx.f.encodeStart(JsonOps.INSTANCE, this.h).resultOrPartial(b::error).ifPresent(var1x -> _snowman.add("dimension", var1x));
         }

         if (this.g != null) {
            _snowman.addProperty("feature", this.g.i());
         }

         if (this.f != null) {
            _snowman.addProperty("biome", this.f.a().toString());
         }

         if (this.i != null) {
            _snowman.addProperty("smokey", this.i);
         }

         _snowman.add("light", this.j.a());
         _snowman.add("block", this.k.a());
         _snowman.add("fluid", this.l.a());
         return _snowman;
      }
   }

   public static bw a(@Nullable JsonElement var0) {
      if (_snowman != null && !_snowman.isJsonNull()) {
         JsonObject _snowman = afd.m(_snowman, "location");
         JsonObject _snowmanx = afd.a(_snowman, "position", new JsonObject());
         bz.c _snowmanxx = bz.c.a(_snowmanx.get("x"));
         bz.c _snowmanxxx = bz.c.a(_snowmanx.get("y"));
         bz.c _snowmanxxxx = bz.c.a(_snowmanx.get("z"));
         vj<brx> _snowmanxxxxx = _snowman.has("dimension")
            ? vk.a.parse(JsonOps.INSTANCE, _snowman.get("dimension")).resultOrPartial(b::error).map(var0x -> vj.a(gm.L, var0x)).orElse(null)
            : null;
         cla<?> _snowmanxxxxxx = _snowman.has("feature") ? (cla)cla.a.get(afd.h(_snowman, "feature")) : null;
         vj<bsv> _snowmanxxxxxxx = null;
         if (_snowman.has("biome")) {
            vk _snowmanxxxxxxxx = new vk(afd.h(_snowman, "biome"));
            _snowmanxxxxxxx = vj.a(gm.ay, _snowmanxxxxxxxx);
         }

         Boolean _snowmanxxxxxxxx = _snowman.has("smokey") ? _snowman.get("smokey").getAsBoolean() : null;
         bv _snowmanxxxxxxxxx = bv.a(_snowman.get("light"));
         an _snowmanxxxxxxxxxx = an.a(_snowman.get("block"));
         bl _snowmanxxxxxxxxxxx = bl.a(_snowman.get("fluid"));
         return new bw(_snowmanxx, _snowmanxxx, _snowmanxxxx, _snowmanxxxxxxx, _snowmanxxxxxx, _snowmanxxxxx, _snowmanxxxxxxxx, _snowmanxxxxxxxxx, _snowmanxxxxxxxxxx, _snowmanxxxxxxxxxxx);
      } else {
         return a;
      }
   }

   public static class a {
      private bz.c a = bz.c.e;
      private bz.c b = bz.c.e;
      private bz.c c = bz.c.e;
      @Nullable
      private vj<bsv> d;
      @Nullable
      private cla<?> e;
      @Nullable
      private vj<brx> f;
      @Nullable
      private Boolean g;
      private bv h = bv.a;
      private an i = an.a;
      private bl j = bl.a;

      public a() {
      }

      public static bw.a a() {
         return new bw.a();
      }

      public bw.a a(@Nullable vj<bsv> var1) {
         this.d = _snowman;
         return this;
      }

      public bw.a a(an var1) {
         this.i = _snowman;
         return this;
      }

      public bw.a a(Boolean var1) {
         this.g = _snowman;
         return this;
      }

      public bw b() {
         return new bw(this.a, this.b, this.c, this.d, this.e, this.f, this.g, this.h, this.i, this.j);
      }
   }
}
