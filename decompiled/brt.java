import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.serialization.DynamicLike;
import java.util.Comparator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import net.minecraft.server.MinecraftServer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class brt {
   private static final Logger H = LogManager.getLogger();
   private static final Map<brt.e<?>, brt.f<?>> I = Maps.newTreeMap(Comparator.comparing(var0 -> var0.a));
   public static final brt.e<brt.a> a = a("doFireTick", brt.b.e, brt.a.b(true));
   public static final brt.e<brt.a> b = a("mobGriefing", brt.b.b, brt.a.b(true));
   public static final brt.e<brt.a> c = a("keepInventory", brt.b.a, brt.a.b(false));
   public static final brt.e<brt.a> d = a("doMobSpawning", brt.b.c, brt.a.b(true));
   public static final brt.e<brt.a> e = a("doMobLoot", brt.b.d, brt.a.b(true));
   public static final brt.e<brt.a> f = a("doTileDrops", brt.b.d, brt.a.b(true));
   public static final brt.e<brt.a> g = a("doEntityDrops", brt.b.d, brt.a.b(true));
   public static final brt.e<brt.a> h = a("commandBlockOutput", brt.b.f, brt.a.b(true));
   public static final brt.e<brt.a> i = a("naturalRegeneration", brt.b.a, brt.a.b(true));
   public static final brt.e<brt.a> j = a("doDaylightCycle", brt.b.e, brt.a.b(true));
   public static final brt.e<brt.a> k = a("logAdminCommands", brt.b.f, brt.a.b(true));
   public static final brt.e<brt.a> l = a("showDeathMessages", brt.b.f, brt.a.b(true));
   public static final brt.e<brt.d> m = a("randomTickSpeed", brt.b.e, brt.d.b(3));
   public static final brt.e<brt.a> n = a("sendCommandFeedback", brt.b.f, brt.a.b(true));
   public static final brt.e<brt.a> o = a("reducedDebugInfo", brt.b.g, brt.a.b(false, (var0, var1) -> {
      byte _snowman = (byte)(var1.a() ? 22 : 23);

      for (aah _snowmanx : var0.ae().s()) {
         _snowmanx.b.a(new pn(_snowmanx, _snowman));
      }
   }));
   public static final brt.e<brt.a> p = a("spectatorsGenerateChunks", brt.b.a, brt.a.b(true));
   public static final brt.e<brt.d> q = a("spawnRadius", brt.b.a, brt.d.b(10));
   public static final brt.e<brt.a> r = a("disableElytraMovementCheck", brt.b.a, brt.a.b(false));
   public static final brt.e<brt.d> s = a("maxEntityCramming", brt.b.b, brt.d.b(24));
   public static final brt.e<brt.a> t = a("doWeatherCycle", brt.b.e, brt.a.b(true));
   public static final brt.e<brt.a> u = a("doLimitedCrafting", brt.b.a, brt.a.b(false));
   public static final brt.e<brt.d> v = a("maxCommandChainLength", brt.b.g, brt.d.b(65536));
   public static final brt.e<brt.a> w = a("announceAdvancements", brt.b.f, brt.a.b(true));
   public static final brt.e<brt.a> x = a("disableRaids", brt.b.b, brt.a.b(false));
   public static final brt.e<brt.a> y = a("doInsomnia", brt.b.c, brt.a.b(true));
   public static final brt.e<brt.a> z = a("doImmediateRespawn", brt.b.a, brt.a.b(false, (var0, var1) -> {
      for (aah _snowman : var0.ae().s()) {
         _snowman.b.a(new pq(pq.l, var1.a() ? 1.0F : 0.0F));
      }
   }));
   public static final brt.e<brt.a> A = a("drowningDamage", brt.b.a, brt.a.b(true));
   public static final brt.e<brt.a> B = a("fallDamage", brt.b.a, brt.a.b(true));
   public static final brt.e<brt.a> C = a("fireDamage", brt.b.a, brt.a.b(true));
   public static final brt.e<brt.a> D = a("doPatrolSpawning", brt.b.c, brt.a.b(true));
   public static final brt.e<brt.a> E = a("doTraderSpawning", brt.b.c, brt.a.b(true));
   public static final brt.e<brt.a> F = a("forgiveDeadPlayers", brt.b.b, brt.a.b(true));
   public static final brt.e<brt.a> G = a("universalAnger", brt.b.b, brt.a.b(false));
   private final Map<brt.e<?>, brt.g<?>> J;

   private static <T extends brt.g<T>> brt.e<T> a(String var0, brt.b var1, brt.f<T> var2) {
      brt.e<T> _snowman = new brt.e<>(_snowman, _snowman);
      brt.f<?> _snowmanx = I.put(_snowman, _snowman);
      if (_snowmanx != null) {
         throw new IllegalStateException("Duplicate game rule registration for " + _snowman);
      } else {
         return _snowman;
      }
   }

   public brt(DynamicLike<?> var1) {
      this();
      this.a(_snowman);
   }

   public brt() {
      this.J = I.entrySet().stream().collect(ImmutableMap.toImmutableMap(Entry::getKey, var0 -> ((brt.f)var0.getValue()).a()));
   }

   private brt(Map<brt.e<?>, brt.g<?>> var1) {
      this.J = _snowman;
   }

   public <T extends brt.g<T>> T a(brt.e<T> var1) {
      return (T)this.J.get(_snowman);
   }

   public md a() {
      md _snowman = new md();
      this.J.forEach((var1x, var2) -> _snowman.a(var1x.a, var2.b()));
      return _snowman;
   }

   private void a(DynamicLike<?> var1) {
      this.J.forEach((var1x, var2) -> _snowman.get(var1x.a).asString().result().ifPresent(var2::a));
   }

   public brt b() {
      return new brt(this.J.entrySet().stream().collect(ImmutableMap.toImmutableMap(Entry::getKey, var0 -> ((brt.g)var0.getValue()).f())));
   }

   public static void a(brt.c var0) {
      I.forEach((var1, var2) -> a(_snowman, (brt.e<?>)var1, (brt.f<?>)var2));
   }

   private static <T extends brt.g<T>> void a(brt.c var0, brt.e<?> var1, brt.f<?> var2) {
      _snowman.a(_snowman, _snowman);
      _snowman.a(_snowman, _snowman);
   }

   public void a(brt var1, @Nullable MinecraftServer var2) {
      _snowman.J.keySet().forEach(var3 -> this.a((brt.e<?>)var3, _snowman, _snowman));
   }

   private <T extends brt.g<T>> void a(brt.e<T> var1, brt var2, @Nullable MinecraftServer var3) {
      T _snowman = _snowman.a(_snowman);
      this.<T>a(_snowman).a(_snowman, _snowman);
   }

   public boolean b(brt.e<brt.a> var1) {
      return this.a(_snowman).a();
   }

   public int c(brt.e<brt.d> var1) {
      return this.a(_snowman).a();
   }

   public static class a extends brt.g<brt.a> {
      private boolean b;

      private static brt.f<brt.a> b(boolean var0, BiConsumer<MinecraftServer, brt.a> var1) {
         return new brt.f<>(BoolArgumentType::bool, var1x -> new brt.a(var1x, _snowman), _snowman, brt.c::b);
      }

      private static brt.f<brt.a> b(boolean var0) {
         return b(_snowman, (var0x, var1) -> {
         });
      }

      public a(brt.f<brt.a> var1, boolean var2) {
         super(_snowman);
         this.b = _snowman;
      }

      @Override
      protected void a(CommandContext<db> var1, String var2) {
         this.b = BoolArgumentType.getBool(_snowman, _snowman);
      }

      public boolean a() {
         return this.b;
      }

      public void a(boolean var1, @Nullable MinecraftServer var2) {
         this.b = _snowman;
         this.a(_snowman);
      }

      @Override
      public String b() {
         return Boolean.toString(this.b);
      }

      @Override
      protected void a(String var1) {
         this.b = Boolean.parseBoolean(_snowman);
      }

      @Override
      public int c() {
         return this.b ? 1 : 0;
      }

      protected brt.a d() {
         return this;
      }

      protected brt.a e() {
         return new brt.a(this.a, this.b);
      }

      public void a(brt.a var1, @Nullable MinecraftServer var2) {
         this.b = _snowman.b;
         this.a(_snowman);
      }
   }

   public static enum b {
      a("gamerule.category.player"),
      b("gamerule.category.mobs"),
      c("gamerule.category.spawning"),
      d("gamerule.category.drops"),
      e("gamerule.category.updates"),
      f("gamerule.category.chat"),
      g("gamerule.category.misc");

      private final String h;

      private b(String var3) {
         this.h = _snowman;
      }

      public String a() {
         return this.h;
      }
   }

   public interface c {
      default <T extends brt.g<T>> void a(brt.e<T> var1, brt.f<T> var2) {
      }

      default void b(brt.e<brt.a> var1, brt.f<brt.a> var2) {
      }

      default void c(brt.e<brt.d> var1, brt.f<brt.d> var2) {
      }
   }

   public static class d extends brt.g<brt.d> {
      private int b;

      private static brt.f<brt.d> a(int var0, BiConsumer<MinecraftServer, brt.d> var1) {
         return new brt.f<>(IntegerArgumentType::integer, var1x -> new brt.d(var1x, _snowman), _snowman, brt.c::c);
      }

      private static brt.f<brt.d> b(int var0) {
         return a(_snowman, (var0x, var1) -> {
         });
      }

      public d(brt.f<brt.d> var1, int var2) {
         super(_snowman);
         this.b = _snowman;
      }

      @Override
      protected void a(CommandContext<db> var1, String var2) {
         this.b = IntegerArgumentType.getInteger(_snowman, _snowman);
      }

      public int a() {
         return this.b;
      }

      @Override
      public String b() {
         return Integer.toString(this.b);
      }

      @Override
      protected void a(String var1) {
         this.b = c(_snowman);
      }

      public boolean b(String var1) {
         try {
            this.b = Integer.parseInt(_snowman);
            return true;
         } catch (NumberFormatException var3) {
            return false;
         }
      }

      private static int c(String var0) {
         if (!_snowman.isEmpty()) {
            try {
               return Integer.parseInt(_snowman);
            } catch (NumberFormatException var2) {
               brt.H.warn("Failed to parse integer {}", _snowman);
            }
         }

         return 0;
      }

      @Override
      public int c() {
         return this.b;
      }

      protected brt.d d() {
         return this;
      }

      protected brt.d e() {
         return new brt.d(this.a, this.b);
      }

      public void a(brt.d var1, @Nullable MinecraftServer var2) {
         this.b = _snowman.b;
         this.a(_snowman);
      }
   }

   public static final class e<T extends brt.g<T>> {
      private final String a;
      private final brt.b b;

      public e(String var1, brt.b var2) {
         this.a = _snowman;
         this.b = _snowman;
      }

      @Override
      public String toString() {
         return this.a;
      }

      @Override
      public boolean equals(Object var1) {
         return this == _snowman ? true : _snowman instanceof brt.e && ((brt.e)_snowman).a.equals(this.a);
      }

      @Override
      public int hashCode() {
         return this.a.hashCode();
      }

      public String a() {
         return this.a;
      }

      public String b() {
         return "gamerule." + this.a;
      }

      public brt.b c() {
         return this.b;
      }
   }

   public static class f<T extends brt.g<T>> {
      private final Supplier<ArgumentType<?>> a;
      private final Function<brt.f<T>, T> b;
      private final BiConsumer<MinecraftServer, T> c;
      private final brt.h<T> d;

      private f(Supplier<ArgumentType<?>> var1, Function<brt.f<T>, T> var2, BiConsumer<MinecraftServer, T> var3, brt.h<T> var4) {
         this.a = _snowman;
         this.b = _snowman;
         this.c = _snowman;
         this.d = _snowman;
      }

      public RequiredArgumentBuilder<db, ?> a(String var1) {
         return dc.a(_snowman, (ArgumentType<T>)this.a.get());
      }

      public T a() {
         return this.b.apply(this);
      }

      public void a(brt.c var1, brt.e<T> var2) {
         this.d.call(_snowman, _snowman, this);
      }
   }

   public abstract static class g<T extends brt.g<T>> {
      protected final brt.f<T> a;

      public g(brt.f<T> var1) {
         this.a = _snowman;
      }

      protected abstract void a(CommandContext<db> var1, String var2);

      public void b(CommandContext<db> var1, String var2) {
         this.a(_snowman, _snowman);
         this.a(((db)_snowman.getSource()).j());
      }

      protected void a(@Nullable MinecraftServer var1) {
         if (_snowman != null) {
            this.a.c.accept(_snowman, this.g());
         }
      }

      protected abstract void a(String var1);

      public abstract String b();

      @Override
      public String toString() {
         return this.b();
      }

      public abstract int c();

      protected abstract T g();

      protected abstract T f();

      public abstract void a(T var1, @Nullable MinecraftServer var2);
   }

   interface h<T extends brt.g<T>> {
      void call(brt.c var1, brt.e<T> var2, brt.f<T> var3);
   }
}
