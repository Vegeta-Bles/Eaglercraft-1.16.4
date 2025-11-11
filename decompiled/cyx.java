import com.google.common.collect.Lists;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Random;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Predicate;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.mutable.MutableInt;

public class cyx {
   private final czq[] a;
   private final dbo[] b;
   private final Predicate<cyv> c;
   private final daj[] d;
   private final BiFunction<bmb, cyv, bmb> e;
   private final czb f;
   private final czd g;

   private cyx(czq[] var1, dbo[] var2, daj[] var3, czb var4, czd var5) {
      this.a = _snowman;
      this.b = _snowman;
      this.c = dbq.a(_snowman);
      this.d = _snowman;
      this.e = dal.a(_snowman);
      this.f = _snowman;
      this.g = _snowman;
   }

   private void b(Consumer<bmb> var1, cyv var2) {
      Random _snowman = _snowman.a();
      List<czp> _snowmanx = Lists.newArrayList();
      MutableInt _snowmanxx = new MutableInt();

      for (czq _snowmanxxx : this.a) {
         _snowmanxxx.expand(_snowman, var3x -> {
            int _snowmanxxxx = var3x.a(_snowman.b());
            if (_snowmanxxxx > 0) {
               _snowman.add(var3x);
               _snowman.add(_snowmanxxxx);
            }
         });
      }

      int _snowmanxxx = _snowmanx.size();
      if (_snowmanxx.intValue() != 0 && _snowmanxxx != 0) {
         if (_snowmanxxx == 1) {
            _snowmanx.get(0).a(_snowman, _snowman);
         } else {
            int _snowmanxxxx = _snowman.nextInt(_snowmanxx.intValue());

            for (czp _snowmanxxxxx : _snowmanx) {
               _snowmanxxxx -= _snowmanxxxxx.a(_snowman.b());
               if (_snowmanxxxx < 0) {
                  _snowmanxxxxx.a(_snowman, _snowman);
                  return;
               }
            }
         }
      }
   }

   public void a(Consumer<bmb> var1, cyv var2) {
      if (this.c.test(_snowman)) {
         Consumer<bmb> _snowman = daj.a(this.e, _snowman, _snowman);
         Random _snowmanx = _snowman.a();
         int _snowmanxx = this.f.a(_snowmanx) + afm.d(this.g.b(_snowmanx) * _snowman.b());

         for (int _snowmanxxx = 0; _snowmanxxx < _snowmanxx; _snowmanxxx++) {
            this.b(_snowman, _snowman);
         }
      }
   }

   public void a(czg var1) {
      for (int _snowman = 0; _snowman < this.b.length; _snowman++) {
         this.b[_snowman].a(_snowman.b(".condition[" + _snowman + "]"));
      }

      for (int _snowman = 0; _snowman < this.d.length; _snowman++) {
         this.d[_snowman].a(_snowman.b(".functions[" + _snowman + "]"));
      }

      for (int _snowman = 0; _snowman < this.a.length; _snowman++) {
         this.a[_snowman].a(_snowman.b(".entries[" + _snowman + "]"));
      }
   }

   public static cyx.a a() {
      return new cyx.a();
   }

   public static class a implements dag<cyx.a>, dbh<cyx.a> {
      private final List<czq> a = Lists.newArrayList();
      private final List<dbo> b = Lists.newArrayList();
      private final List<daj> c = Lists.newArrayList();
      private czb d = new czd(1.0F);
      private czd e = new czd(0.0F, 0.0F);

      public a() {
      }

      public cyx.a a(czb var1) {
         this.d = _snowman;
         return this;
      }

      public cyx.a a() {
         return this;
      }

      public cyx.a a(czq.a<?> var1) {
         this.a.add(_snowman.b());
         return this;
      }

      public cyx.a a(dbo.a var1) {
         this.b.add(_snowman.build());
         return this;
      }

      public cyx.a a(daj.a var1) {
         this.c.add(_snowman.b());
         return this;
      }

      public cyx b() {
         if (this.d == null) {
            throw new IllegalArgumentException("Rolls not set");
         } else {
            return new cyx(this.a.toArray(new czq[0]), this.b.toArray(new dbo[0]), this.c.toArray(new daj[0]), this.d, this.e);
         }
      }
   }

   public static class b implements JsonDeserializer<cyx>, JsonSerializer<cyx> {
      public b() {
      }

      public cyx a(JsonElement var1, Type var2, JsonDeserializationContext var3) throws JsonParseException {
         JsonObject _snowman = afd.m(_snowman, "loot pool");
         czq[] _snowmanx = afd.a(_snowman, "entries", _snowman, czq[].class);
         dbo[] _snowmanxx = afd.a(_snowman, "conditions", new dbo[0], _snowman, dbo[].class);
         daj[] _snowmanxxx = afd.a(_snowman, "functions", new daj[0], _snowman, daj[].class);
         czb _snowmanxxxx = czc.a(_snowman.get("rolls"), _snowman);
         czd _snowmanxxxxx = afd.a(_snowman, "bonus_rolls", new czd(0.0F, 0.0F), _snowman, czd.class);
         return new cyx(_snowmanx, _snowmanxx, _snowmanxxx, _snowmanxxxx, _snowmanxxxxx);
      }

      public JsonElement a(cyx var1, Type var2, JsonSerializationContext var3) {
         JsonObject _snowman = new JsonObject();
         _snowman.add("rolls", czc.a(_snowman.f, _snowman));
         _snowman.add("entries", _snowman.serialize(_snowman.a));
         if (_snowman.g.b() != 0.0F && _snowman.g.c() != 0.0F) {
            _snowman.add("bonus_rolls", _snowman.serialize(_snowman.g));
         }

         if (!ArrayUtils.isEmpty(_snowman.b)) {
            _snowman.add("conditions", _snowman.serialize(_snowman.b));
         }

         if (!ArrayUtils.isEmpty(_snowman.d)) {
            _snowman.add("functions", _snowman.serialize(_snowman.d));
         }

         return _snowman;
      }
   }
}
