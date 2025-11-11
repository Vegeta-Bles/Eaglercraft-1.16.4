import com.google.common.collect.ImmutableSet;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import java.util.Locale;
import java.util.Set;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class dae extends dai {
   private static final Logger d = LogManager.getLogger();
   public static final cla<?> a = cla.p;
   public static final cxu.a b = cxu.a.i;
   private final cla<?> e;
   private final cxu.a f;
   private final byte g;
   private final int h;
   private final boolean i;

   private dae(dbo[] var1, cla<?> var2, cxu.a var3, byte var4, int var5, boolean var6) {
      super(_snowman);
      this.e = _snowman;
      this.f = _snowman;
      this.g = _snowman;
      this.h = _snowman;
      this.i = _snowman;
   }

   @Override
   public dak b() {
      return dal.k;
   }

   @Override
   public Set<daz<?>> a() {
      return ImmutableSet.of(dbc.f);
   }

   @Override
   public bmb a(bmb var1, cyv var2) {
      if (_snowman.b() != bmd.pc) {
         return _snowman;
      } else {
         dcn _snowman = _snowman.c(dbc.f);
         if (_snowman != null) {
            aag _snowmanx = _snowman.c();
            fx _snowmanxx = _snowmanx.a(this.e, new fx(_snowman), this.h, this.i);
            if (_snowmanxx != null) {
               bmb _snowmanxxx = bmh.a(_snowmanx, _snowmanxx.u(), _snowmanxx.w(), this.g, true, true);
               bmh.a(_snowmanx, _snowmanxxx);
               cxx.a(_snowmanxxx, _snowmanxx, "+", this.f);
               _snowmanxxx.a(new of("filled_map." + this.e.i().toLowerCase(Locale.ROOT)));
               return _snowmanxxx;
            }
         }

         return _snowman;
      }
   }

   public static dae.a c() {
      return new dae.a();
   }

   public static class a extends dai.a<dae.a> {
      private cla<?> a;
      private cxu.a b;
      private byte c;
      private int d;
      private boolean e;

      public a() {
         this.a = dae.a;
         this.b = dae.b;
         this.c = 2;
         this.d = 50;
         this.e = true;
      }

      protected dae.a a() {
         return this;
      }

      public dae.a a(cla<?> var1) {
         this.a = _snowman;
         return this;
      }

      public dae.a a(cxu.a var1) {
         this.b = _snowman;
         return this;
      }

      public dae.a a(byte var1) {
         this.c = _snowman;
         return this;
      }

      public dae.a a(boolean var1) {
         this.e = _snowman;
         return this;
      }

      @Override
      public daj b() {
         return new dae(this.g(), this.a, this.b, this.c, this.d, this.e);
      }
   }

   public static class b extends dai.c<dae> {
      public b() {
      }

      public void a(JsonObject var1, dae var2, JsonSerializationContext var3) {
         super.a(_snowman, _snowman, _snowman);
         if (!_snowman.e.equals(dae.a)) {
            _snowman.add("destination", _snowman.serialize(_snowman.e.i()));
         }

         if (_snowman.f != dae.b) {
            _snowman.add("decoration", _snowman.serialize(_snowman.f.toString().toLowerCase(Locale.ROOT)));
         }

         if (_snowman.g != 2) {
            _snowman.addProperty("zoom", _snowman.g);
         }

         if (_snowman.h != 50) {
            _snowman.addProperty("search_radius", _snowman.h);
         }

         if (!_snowman.i) {
            _snowman.addProperty("skip_existing_chunks", _snowman.i);
         }
      }

      public dae a(JsonObject var1, JsonDeserializationContext var2, dbo[] var3) {
         cla<?> _snowman = a(_snowman);
         String _snowmanx = _snowman.has("decoration") ? afd.h(_snowman, "decoration") : "mansion";
         cxu.a _snowmanxx = dae.b;

         try {
            _snowmanxx = cxu.a.valueOf(_snowmanx.toUpperCase(Locale.ROOT));
         } catch (IllegalArgumentException var10) {
            dae.d.error("Error while parsing loot table decoration entry. Found {}. Defaulting to " + dae.b, _snowmanx);
         }

         byte _snowmanxxx = afd.a(_snowman, "zoom", (byte)2);
         int _snowmanxxxx = afd.a(_snowman, "search_radius", 50);
         boolean _snowmanxxxxx = afd.a(_snowman, "skip_existing_chunks", true);
         return new dae(_snowman, _snowman, _snowmanxx, _snowmanxxx, _snowmanxxxx, _snowmanxxxxx);
      }

      private static cla<?> a(JsonObject var0) {
         if (_snowman.has("destination")) {
            String _snowman = afd.h(_snowman, "destination");
            cla<?> _snowmanx = (cla<?>)cla.a.get(_snowman.toLowerCase(Locale.ROOT));
            if (_snowmanx != null) {
               return _snowmanx;
            }
         }

         return dae.a;
      }
   }
}
