import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import javax.annotation.Nullable;
import net.minecraft.server.MinecraftServer;

public class cyv {
   private final Random a;
   private final float b;
   private final aag c;
   private final Function<vk, cyy> d;
   private final Set<cyy> e = Sets.newLinkedHashSet();
   private final Function<vk, dbo> f;
   private final Set<dbo> g = Sets.newLinkedHashSet();
   private final Map<daz<?>, Object> h;
   private final Map<vk, cyv.b> i;

   private cyv(Random var1, float var2, aag var3, Function<vk, cyy> var4, Function<vk, dbo> var5, Map<daz<?>, Object> var6, Map<vk, cyv.b> var7) {
      this.a = _snowman;
      this.b = _snowman;
      this.c = _snowman;
      this.d = _snowman;
      this.f = _snowman;
      this.h = ImmutableMap.copyOf(_snowman);
      this.i = ImmutableMap.copyOf(_snowman);
   }

   public boolean a(daz<?> var1) {
      return this.h.containsKey(_snowman);
   }

   public void a(vk var1, Consumer<bmb> var2) {
      cyv.b _snowman = this.i.get(_snowman);
      if (_snowman != null) {
         _snowman.add(this, _snowman);
      }
   }

   @Nullable
   public <T> T c(daz<T> var1) {
      return (T)this.h.get(_snowman);
   }

   public boolean a(cyy var1) {
      return this.e.add(_snowman);
   }

   public void b(cyy var1) {
      this.e.remove(_snowman);
   }

   public boolean a(dbo var1) {
      return this.g.add(_snowman);
   }

   public void b(dbo var1) {
      this.g.remove(_snowman);
   }

   public cyy a(vk var1) {
      return this.d.apply(_snowman);
   }

   public dbo b(vk var1) {
      return this.f.apply(_snowman);
   }

   public Random a() {
      return this.a;
   }

   public float b() {
      return this.b;
   }

   public aag c() {
      return this.c;
   }

   public static class a {
      private final aag a;
      private final Map<daz<?>, Object> b = Maps.newIdentityHashMap();
      private final Map<vk, cyv.b> c = Maps.newHashMap();
      private Random d;
      private float e;

      public a(aag var1) {
         this.a = _snowman;
      }

      public cyv.a a(Random var1) {
         this.d = _snowman;
         return this;
      }

      public cyv.a a(long var1) {
         if (_snowman != 0L) {
            this.d = new Random(_snowman);
         }

         return this;
      }

      public cyv.a a(long var1, Random var3) {
         if (_snowman == 0L) {
            this.d = _snowman;
         } else {
            this.d = new Random(_snowman);
         }

         return this;
      }

      public cyv.a a(float var1) {
         this.e = _snowman;
         return this;
      }

      public <T> cyv.a a(daz<T> var1, T var2) {
         this.b.put(_snowman, _snowman);
         return this;
      }

      public <T> cyv.a b(daz<T> var1, @Nullable T var2) {
         if (_snowman == null) {
            this.b.remove(_snowman);
         } else {
            this.b.put(_snowman, _snowman);
         }

         return this;
      }

      public cyv.a a(vk var1, cyv.b var2) {
         cyv.b _snowman = this.c.put(_snowman, _snowman);
         if (_snowman != null) {
            throw new IllegalStateException("Duplicated dynamic drop '" + this.c + "'");
         } else {
            return this;
         }
      }

      public aag a() {
         return this.a;
      }

      public <T> T a(daz<T> var1) {
         T _snowman = (T)this.b.get(_snowman);
         if (_snowman == null) {
            throw new IllegalArgumentException("No parameter " + _snowman);
         } else {
            return _snowman;
         }
      }

      @Nullable
      public <T> T b(daz<T> var1) {
         return (T)this.b.get(_snowman);
      }

      public cyv a(dba var1) {
         Set<daz<?>> _snowman = Sets.difference(this.b.keySet(), _snowman.b());
         if (!_snowman.isEmpty()) {
            throw new IllegalArgumentException("Parameters not allowed in this parameter set: " + _snowman);
         } else {
            Set<daz<?>> _snowmanx = Sets.difference(_snowman.a(), this.b.keySet());
            if (!_snowmanx.isEmpty()) {
               throw new IllegalArgumentException("Missing required parameters: " + _snowmanx);
            } else {
               Random _snowmanxx = this.d;
               if (_snowmanxx == null) {
                  _snowmanxx = new Random();
               }

               MinecraftServer _snowmanxxx = this.a.l();
               return new cyv(_snowmanxx, this.e, this.a, _snowmanxxx.aJ()::a, _snowmanxxx.aK()::a, this.b, this.c);
            }
         }
      }
   }

   @FunctionalInterface
   public interface b {
      void add(cyv var1, Consumer<bmb> var2);
   }

   public static enum c {
      a("this", dbc.a),
      b("killer", dbc.d),
      c("direct_killer", dbc.e),
      d("killer_player", dbc.b);

      private final String e;
      private final daz<? extends aqa> f;

      private c(String var3, daz<? extends aqa> var4) {
         this.e = _snowman;
         this.f = _snowman;
      }

      public daz<? extends aqa> a() {
         return this.f;
      }

      public static cyv.c a(String var0) {
         for (cyv.c _snowman : values()) {
            if (_snowman.e.equals(_snowman)) {
               return _snowman;
            }
         }

         throw new IllegalArgumentException("Invalid entity target " + _snowman);
      }

      public static class a extends TypeAdapter<cyv.c> {
         public a() {
         }

         public void a(JsonWriter var1, cyv.c var2) throws IOException {
            _snowman.value(_snowman.e);
         }

         public cyv.c a(JsonReader var1) throws IOException {
            return cyv.c.a(_snowman.nextString());
         }
      }
   }
}
