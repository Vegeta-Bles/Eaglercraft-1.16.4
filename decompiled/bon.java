import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntComparators;
import it.unimi.dsi.fastutil.ints.IntList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import javax.annotation.Nullable;

public final class bon implements Predicate<bmb> {
   public static final bon a = new bon(Stream.empty());
   private final bon.c[] b;
   private bmb[] c;
   private IntList d;

   private bon(Stream<? extends bon.c> var1) {
      this.b = _snowman.toArray(bon.c[]::new);
   }

   public bmb[] a() {
      this.f();
      return this.c;
   }

   private void f() {
      if (this.c == null) {
         this.c = Arrays.stream(this.b).flatMap(var0 -> var0.a().stream()).distinct().toArray(bmb[]::new);
      }
   }

   public boolean a(@Nullable bmb var1) {
      if (_snowman == null) {
         return false;
      } else {
         this.f();
         if (this.c.length == 0) {
            return _snowman.a();
         } else {
            for (bmb _snowman : this.c) {
               if (_snowman.b() == _snowman.b()) {
                  return true;
               }
            }

            return false;
         }
      }
   }

   public IntList b() {
      if (this.d == null) {
         this.f();
         this.d = new IntArrayList(this.c.length);

         for (bmb _snowman : this.c) {
            this.d.add(bfy.c(_snowman));
         }

         this.d.sort(IntComparators.NATURAL_COMPARATOR);
      }

      return this.d;
   }

   public void a(nf var1) {
      this.f();
      _snowman.d(this.c.length);

      for (int _snowman = 0; _snowman < this.c.length; _snowman++) {
         _snowman.a(this.c[_snowman]);
      }
   }

   public JsonElement c() {
      if (this.b.length == 1) {
         return this.b[0].b();
      } else {
         JsonArray _snowman = new JsonArray();

         for (bon.c _snowmanx : this.b) {
            _snowman.add(_snowmanx.b());
         }

         return _snowman;
      }
   }

   public boolean d() {
      return this.b.length == 0 && (this.c == null || this.c.length == 0) && (this.d == null || this.d.isEmpty());
   }

   private static bon b(Stream<? extends bon.c> var0) {
      bon _snowman = new bon(_snowman);
      return _snowman.b.length == 0 ? a : _snowman;
   }

   public static bon a(brw... var0) {
      return a(Arrays.stream(_snowman).map(bmb::new));
   }

   public static bon a(bmb... var0) {
      return a(Arrays.stream(_snowman));
   }

   public static bon a(Stream<bmb> var0) {
      return b(_snowman.filter(var0x -> !var0x.a()).map(var0x -> new bon.a(var0x)));
   }

   public static bon a(ael<blx> var0) {
      return b(Stream.of(new bon.b(_snowman)));
   }

   public static bon b(nf var0) {
      int _snowman = _snowman.i();
      return b(Stream.<bon.c>generate(() -> new bon.a(_snowman.n())).limit((long)_snowman));
   }

   public static bon a(@Nullable JsonElement var0) {
      if (_snowman == null || _snowman.isJsonNull()) {
         throw new JsonSyntaxException("Item cannot be null");
      } else if (_snowman.isJsonObject()) {
         return b(Stream.of(a(_snowman.getAsJsonObject())));
      } else if (_snowman.isJsonArray()) {
         JsonArray _snowman = _snowman.getAsJsonArray();
         if (_snowman.size() == 0) {
            throw new JsonSyntaxException("Item array cannot be empty, at least one item must be defined");
         } else {
            return b(StreamSupport.<JsonElement>stream(_snowman.spliterator(), false).map(var0x -> a(afd.m(var0x, "item"))));
         }
      } else {
         throw new JsonSyntaxException("Expected item to be object or array of objects");
      }
   }

   private static bon.c a(JsonObject var0) {
      if (_snowman.has("item") && _snowman.has("tag")) {
         throw new JsonParseException("An ingredient entry is either a tag or an item, not both");
      } else if (_snowman.has("item")) {
         vk _snowman = new vk(afd.h(_snowman, "item"));
         blx _snowmanx = gm.T.b(_snowman).orElseThrow(() -> new JsonSyntaxException("Unknown item '" + _snowman + "'"));
         return new bon.a(new bmb(_snowmanx));
      } else if (_snowman.has("tag")) {
         vk _snowman = new vk(afd.h(_snowman, "tag"));
         ael<blx> _snowmanx = aeh.a().b().a(_snowman);
         if (_snowmanx == null) {
            throw new JsonSyntaxException("Unknown item tag '" + _snowman + "'");
         } else {
            return new bon.b(_snowmanx);
         }
      } else {
         throw new JsonParseException("An ingredient entry needs either a tag or an item");
      }
   }

   static class a implements bon.c {
      private final bmb a;

      private a(bmb var1) {
         this.a = _snowman;
      }

      @Override
      public Collection<bmb> a() {
         return Collections.singleton(this.a);
      }

      @Override
      public JsonObject b() {
         JsonObject _snowman = new JsonObject();
         _snowman.addProperty("item", gm.T.b(this.a.b()).toString());
         return _snowman;
      }
   }

   static class b implements bon.c {
      private final ael<blx> a;

      private b(ael<blx> var1) {
         this.a = _snowman;
      }

      @Override
      public Collection<bmb> a() {
         List<bmb> _snowman = Lists.newArrayList();

         for (blx _snowmanx : this.a.b()) {
            _snowman.add(new bmb(_snowmanx));
         }

         return _snowman;
      }

      @Override
      public JsonObject b() {
         JsonObject _snowman = new JsonObject();
         _snowman.addProperty("tag", aeh.a().b().b(this.a).toString());
         return _snowman;
      }
   }

   interface c {
      Collection<bmb> a();

      JsonObject b();
   }
}
