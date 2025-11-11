import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Supplier;

public class dab extends dai {
   private final dab.c a;
   private final List<dab.b> b;
   private static final Function<aqa, mt> d = cb::b;
   private static final Function<ccj, mt> e = var0 -> var0.a(new md());

   private dab(dbo[] var1, dab.c var2, List<dab.b> var3) {
      super(_snowman);
      this.a = _snowman;
      this.b = ImmutableList.copyOf(_snowman);
   }

   @Override
   public dak b() {
      return dal.u;
   }

   private static dr.h b(String var0) {
      try {
         return new dr().a(new StringReader(_snowman));
      } catch (CommandSyntaxException var2) {
         throw new IllegalArgumentException("Failed to parse path " + _snowman, var2);
      }
   }

   @Override
   public Set<daz<?>> a() {
      return ImmutableSet.of(this.a.f);
   }

   @Override
   public bmb a(bmb var1, cyv var2) {
      mt _snowman = this.a.g.apply(_snowman);
      if (_snowman != null) {
         this.b.forEach(var2x -> var2x.a(_snowman::p, _snowman));
      }

      return _snowman;
   }

   public static dab.a a(dab.c var0) {
      return new dab.a(_snowman);
   }

   public static class a extends dai.a<dab.a> {
      private final dab.c a;
      private final List<dab.b> b = Lists.newArrayList();

      private a(dab.c var1) {
         this.a = _snowman;
      }

      public dab.a a(String var1, String var2, dab.d var3) {
         this.b.add(new dab.b(_snowman, _snowman, _snowman));
         return this;
      }

      public dab.a a(String var1, String var2) {
         return this.a(_snowman, _snowman, dab.d.a);
      }

      protected dab.a a() {
         return this;
      }

      @Override
      public daj b() {
         return new dab(this.g(), this.a, this.b);
      }
   }

   static class b {
      private final String a;
      private final dr.h b;
      private final String c;
      private final dr.h d;
      private final dab.d e;

      private b(String var1, String var2, dab.d var3) {
         this.a = _snowman;
         this.b = dab.b(_snowman);
         this.c = _snowman;
         this.d = dab.b(_snowman);
         this.e = _snowman;
      }

      public void a(Supplier<mt> var1, mt var2) {
         try {
            List<mt> _snowman = this.b.a(_snowman);
            if (!_snowman.isEmpty()) {
               this.e.a(_snowman.get(), this.d, _snowman);
            }
         } catch (CommandSyntaxException var4) {
         }
      }

      public JsonObject a() {
         JsonObject _snowman = new JsonObject();
         _snowman.addProperty("source", this.a);
         _snowman.addProperty("target", this.c);
         _snowman.addProperty("op", this.e.d);
         return _snowman;
      }

      public static dab.b a(JsonObject var0) {
         String _snowman = afd.h(_snowman, "source");
         String _snowmanx = afd.h(_snowman, "target");
         dab.d _snowmanxx = dab.d.a(afd.h(_snowman, "op"));
         return new dab.b(_snowman, _snowmanx, _snowmanxx);
      }
   }

   public static enum c {
      a("this", dbc.a, dab.d),
      b("killer", dbc.d, dab.d),
      c("killer_player", dbc.b, dab.d),
      d("block_entity", dbc.h, dab.e);

      public final String e;
      public final daz<?> f;
      public final Function<cyv, mt> g;

      private <T> c(String var3, daz<T> var4, Function<? super T, mt> var5) {
         this.e = _snowman;
         this.f = _snowman;
         this.g = var2x -> {
            T _snowman = var2x.c(_snowman);
            return _snowman != null ? _snowman.apply(_snowman) : null;
         };
      }

      public static dab.c a(String var0) {
         for (dab.c _snowman : values()) {
            if (_snowman.e.equals(_snowman)) {
               return _snowman;
            }
         }

         throw new IllegalArgumentException("Invalid tag source " + _snowman);
      }
   }

   public static enum d {
      a("replace") {
         @Override
         public void a(mt var1, dr.h var2, List<mt> var3) throws CommandSyntaxException {
            _snowman.b(_snowman, ((mt)Iterables.getLast(_snowman))::c);
         }
      },
      b("append") {
         @Override
         public void a(mt var1, dr.h var2, List<mt> var3) throws CommandSyntaxException {
            List<mt> _snowman = _snowman.a(_snowman, mj::new);
            _snowman.forEach(var1x -> {
               if (var1x instanceof mj) {
                  _snowman.forEach(var1xx -> ((mj)var1x).add(var1xx.c()));
               }
            });
         }
      },
      c("merge") {
         @Override
         public void a(mt var1, dr.h var2, List<mt> var3) throws CommandSyntaxException {
            List<mt> _snowman = _snowman.a(_snowman, md::new);
            _snowman.forEach(var1x -> {
               if (var1x instanceof md) {
                  _snowman.forEach(var1xx -> {
                     if (var1xx instanceof md) {
                        ((md)var1x).a((md)var1xx);
                     }
                  });
               }
            });
         }
      };

      private final String d;

      public abstract void a(mt var1, dr.h var2, List<mt> var3) throws CommandSyntaxException;

      private d(String var3) {
         this.d = _snowman;
      }

      public static dab.d a(String var0) {
         for (dab.d _snowman : values()) {
            if (_snowman.d.equals(_snowman)) {
               return _snowman;
            }
         }

         throw new IllegalArgumentException("Invalid merge strategy" + _snowman);
      }
   }

   public static class e extends dai.c<dab> {
      public e() {
      }

      public void a(JsonObject var1, dab var2, JsonSerializationContext var3) {
         super.a(_snowman, _snowman, _snowman);
         _snowman.addProperty("source", _snowman.a.e);
         JsonArray _snowman = new JsonArray();
         _snowman.b.stream().map(dab.b::a).forEach(_snowman::add);
         _snowman.add("ops", _snowman);
      }

      public dab a(JsonObject var1, JsonDeserializationContext var2, dbo[] var3) {
         dab.c _snowman = dab.c.a(afd.h(_snowman, "source"));
         List<dab.b> _snowmanx = Lists.newArrayList();

         for (JsonElement _snowmanxx : afd.u(_snowman, "ops")) {
            JsonObject _snowmanxxx = afd.m(_snowmanxx, "op");
            _snowmanx.add(dab.b.a(_snowmanxxx));
         }

         return new dab(_snowman, _snowman, _snowmanx);
      }
   }
}
