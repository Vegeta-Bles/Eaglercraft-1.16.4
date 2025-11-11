import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import com.mojang.datafixers.DataFixer;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.Lifecycle;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.server.MinecraftServer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class cyl implements cym, cyn {
   private static final Logger a = LogManager.getLogger();
   private bsa b;
   private final chw c;
   private final Lifecycle d;
   private int e;
   private int f;
   private int g;
   private float h;
   private long i;
   private long j;
   @Nullable
   private final DataFixer k;
   private final int l;
   private boolean m;
   @Nullable
   private md n;
   private final int o;
   private int p;
   private boolean q;
   private int r;
   private boolean s;
   private int t;
   private boolean u;
   private boolean v;
   private cfu.c w;
   private md x;
   @Nullable
   private md y;
   private int z;
   private int A;
   @Nullable
   private UUID B;
   private final Set<String> C;
   private boolean D;
   private final dcf<MinecraftServer> E;

   private cyl(
      @Nullable DataFixer var1,
      int var2,
      @Nullable md var3,
      boolean var4,
      int var5,
      int var6,
      int var7,
      float var8,
      long var9,
      long var11,
      int var13,
      int var14,
      int var15,
      boolean var16,
      int var17,
      boolean var18,
      boolean var19,
      boolean var20,
      cfu.c var21,
      int var22,
      int var23,
      @Nullable UUID var24,
      LinkedHashSet<String> var25,
      dcf<MinecraftServer> var26,
      @Nullable md var27,
      md var28,
      bsa var29,
      chw var30,
      Lifecycle var31
   ) {
      this.k = _snowman;
      this.D = _snowman;
      this.e = _snowman;
      this.f = _snowman;
      this.g = _snowman;
      this.h = _snowman;
      this.i = _snowman;
      this.j = _snowman;
      this.o = _snowman;
      this.p = _snowman;
      this.r = _snowman;
      this.q = _snowman;
      this.t = _snowman;
      this.s = _snowman;
      this.u = _snowman;
      this.v = _snowman;
      this.w = _snowman;
      this.z = _snowman;
      this.A = _snowman;
      this.B = _snowman;
      this.C = _snowman;
      this.n = _snowman;
      this.l = _snowman;
      this.E = _snowman;
      this.y = _snowman;
      this.x = _snowman;
      this.b = _snowman;
      this.c = _snowman;
      this.d = _snowman;
   }

   public cyl(bsa var1, chw var2, Lifecycle var3) {
      this(
         null,
         w.a().getWorldVersion(),
         null,
         false,
         0,
         0,
         0,
         0.0F,
         0L,
         0L,
         19133,
         0,
         0,
         false,
         0,
         false,
         false,
         false,
         cfu.c,
         0,
         0,
         null,
         Sets.newLinkedHashSet(),
         new dcf<>(dce.a),
         null,
         new md(),
         _snowman.h(),
         _snowman,
         _snowman
      );
   }

   public static cyl a(Dynamic<mt> var0, DataFixer var1, int var2, @Nullable md var3, bsa var4, cyi var5, chw var6, Lifecycle var7) {
      long _snowman = _snowman.get("Time").asLong(0L);
      md _snowmanx = (md)_snowman.get("DragonFight")
         .result()
         .<mt>map(Dynamic::getValue)
         .orElseGet(() -> (mt)_snowman.get("DimensionData").get("1").get("DragonFight").orElseEmptyMap().getValue());
      return new cyl(
         _snowman,
         _snowman,
         _snowman,
         _snowman.get("WasModded").asBoolean(false),
         _snowman.get("SpawnX").asInt(0),
         _snowman.get("SpawnY").asInt(0),
         _snowman.get("SpawnZ").asInt(0),
         _snowman.get("SpawnAngle").asFloat(0.0F),
         _snowman,
         _snowman.get("DayTime").asLong(_snowman),
         _snowman.a(),
         _snowman.get("clearWeatherTime").asInt(0),
         _snowman.get("rainTime").asInt(0),
         _snowman.get("raining").asBoolean(false),
         _snowman.get("thunderTime").asInt(0),
         _snowman.get("thundering").asBoolean(false),
         _snowman.get("initialized").asBoolean(true),
         _snowman.get("DifficultyLocked").asBoolean(false),
         cfu.c.a(_snowman, cfu.c),
         _snowman.get("WanderingTraderSpawnDelay").asInt(0),
         _snowman.get("WanderingTraderSpawnChance").asInt(0),
         (UUID)_snowman.get("WanderingTraderId").read(gq.a).result().orElse(null),
         _snowman.get("ServerBrands").asStream().flatMap(var0x -> x.a(var0x.asString().result())).collect(Collectors.toCollection(Sets::newLinkedHashSet)),
         new dcf<>(dce.a, _snowman.get("ScheduledEvents").asStream()),
         (md)_snowman.get("CustomBossEvents").orElseEmptyMap().getValue(),
         _snowmanx,
         _snowman,
         _snowman,
         _snowman
      );
   }

   @Override
   public md a(gn var1, @Nullable md var2) {
      this.J();
      if (_snowman == null) {
         _snowman = this.n;
      }

      md _snowman = new md();
      this.a(_snowman, _snowman, _snowman);
      return _snowman;
   }

   private void a(gn var1, md var2, @Nullable md var3) {
      mj _snowman = new mj();
      this.C.stream().map(ms::a).forEach(_snowman::add);
      _snowman.a("ServerBrands", _snowman);
      _snowman.a("WasModded", this.D);
      md _snowmanx = new md();
      _snowmanx.a("Name", w.a().getName());
      _snowmanx.b("Id", w.a().getWorldVersion());
      _snowmanx.a("Snapshot", !w.a().isStable());
      _snowman.a("Version", _snowmanx);
      _snowman.b("DataVersion", w.a().getWorldVersion());
      vi<mt> _snowmanxx = vi.a(mo.a, _snowman);
      chw.a.encodeStart(_snowmanxx, this.c).resultOrPartial(x.a("WorldGenSettings: ", a::error)).ifPresent(var1x -> _snowman.a("WorldGenSettings", var1x));
      _snowman.b("GameType", this.b.b().a());
      _snowman.b("SpawnX", this.e);
      _snowman.b("SpawnY", this.f);
      _snowman.b("SpawnZ", this.g);
      _snowman.a("SpawnAngle", this.h);
      _snowman.a("Time", this.i);
      _snowman.a("DayTime", this.j);
      _snowman.a("LastPlayed", x.d());
      _snowman.a("LevelName", this.b.a());
      _snowman.b("version", 19133);
      _snowman.b("clearWeatherTime", this.p);
      _snowman.b("rainTime", this.r);
      _snowman.a("raining", this.q);
      _snowman.b("thunderTime", this.t);
      _snowman.a("thundering", this.s);
      _snowman.a("hardcore", this.b.c());
      _snowman.a("allowCommands", this.b.e());
      _snowman.a("initialized", this.u);
      this.w.a(_snowman);
      _snowman.a("Difficulty", (byte)this.b.d().a());
      _snowman.a("DifficultyLocked", this.v);
      _snowman.a("GameRules", this.b.f().a());
      _snowman.a("DragonFight", this.x);
      if (_snowman != null) {
         _snowman.a("Player", _snowman);
      }

      brk.b.encodeStart(mo.a, this.b.g()).result().ifPresent(var1x -> _snowman.a("DataPacks", var1x));
      if (this.y != null) {
         _snowman.a("CustomBossEvents", this.y);
      }

      _snowman.a("ScheduledEvents", this.E.b());
      _snowman.b("WanderingTraderSpawnDelay", this.z);
      _snowman.b("WanderingTraderSpawnChance", this.A);
      if (this.B != null) {
         _snowman.a("WanderingTraderId", this.B);
      }
   }

   @Override
   public int a() {
      return this.e;
   }

   @Override
   public int b() {
      return this.f;
   }

   @Override
   public int c() {
      return this.g;
   }

   @Override
   public float d() {
      return this.h;
   }

   @Override
   public long e() {
      return this.i;
   }

   @Override
   public long f() {
      return this.j;
   }

   private void J() {
      if (!this.m && this.n != null) {
         if (this.l < w.a().getWorldVersion()) {
            if (this.k == null) {
               throw (NullPointerException)x.c(new NullPointerException("Fixer Upper not set inside LevelData, and the player tag is not upgraded."));
            }

            this.n = mp.a(this.k, aga.b, this.n, this.l);
         }

         this.m = true;
      }
   }

   @Override
   public md y() {
      this.J();
      return this.n;
   }

   @Override
   public void b(int var1) {
      this.e = _snowman;
   }

   @Override
   public void c(int var1) {
      this.f = _snowman;
   }

   @Override
   public void d(int var1) {
      this.g = _snowman;
   }

   @Override
   public void a(float var1) {
      this.h = _snowman;
   }

   @Override
   public void a(long var1) {
      this.i = _snowman;
   }

   @Override
   public void b(long var1) {
      this.j = _snowman;
   }

   @Override
   public void a(fx var1, float var2) {
      this.e = _snowman.u();
      this.f = _snowman.v();
      this.g = _snowman.w();
      this.h = _snowman;
   }

   @Override
   public String g() {
      return this.b.a();
   }

   @Override
   public int z() {
      return this.o;
   }

   @Override
   public int h() {
      return this.p;
   }

   @Override
   public void a(int var1) {
      this.p = _snowman;
   }

   @Override
   public boolean i() {
      return this.s;
   }

   @Override
   public void a(boolean var1) {
      this.s = _snowman;
   }

   @Override
   public int j() {
      return this.t;
   }

   @Override
   public void e(int var1) {
      this.t = _snowman;
   }

   @Override
   public boolean k() {
      return this.q;
   }

   @Override
   public void b(boolean var1) {
      this.q = _snowman;
   }

   @Override
   public int l() {
      return this.r;
   }

   @Override
   public void f(int var1) {
      this.r = _snowman;
   }

   @Override
   public bru m() {
      return this.b.b();
   }

   @Override
   public void a(bru var1) {
      this.b = this.b.a(_snowman);
   }

   @Override
   public boolean n() {
      return this.b.c();
   }

   @Override
   public boolean o() {
      return this.b.e();
   }

   @Override
   public boolean p() {
      return this.u;
   }

   @Override
   public void c(boolean var1) {
      this.u = _snowman;
   }

   @Override
   public brt q() {
      return this.b.f();
   }

   @Override
   public cfu.c r() {
      return this.w;
   }

   @Override
   public void a(cfu.c var1) {
      this.w = _snowman;
   }

   @Override
   public aor s() {
      return this.b.d();
   }

   @Override
   public void a(aor var1) {
      this.b = this.b.a(_snowman);
   }

   @Override
   public boolean t() {
      return this.v;
   }

   @Override
   public void d(boolean var1) {
      this.v = _snowman;
   }

   @Override
   public dcf<MinecraftServer> u() {
      return this.E;
   }

   @Override
   public void a(m var1) {
      cym.super.a(_snowman);
      cyn.super.a(_snowman);
   }

   @Override
   public chw A() {
      return this.c;
   }

   @Override
   public Lifecycle B() {
      return this.d;
   }

   @Override
   public md C() {
      return this.x;
   }

   @Override
   public void a(md var1) {
      this.x = _snowman;
   }

   @Override
   public brk D() {
      return this.b.g();
   }

   @Override
   public void a(brk var1) {
      this.b = this.b.a(_snowman);
   }

   @Nullable
   @Override
   public md E() {
      return this.y;
   }

   @Override
   public void b(@Nullable md var1) {
      this.y = _snowman;
   }

   @Override
   public int v() {
      return this.z;
   }

   @Override
   public void g(int var1) {
      this.z = _snowman;
   }

   @Override
   public int w() {
      return this.A;
   }

   @Override
   public void h(int var1) {
      this.A = _snowman;
   }

   @Override
   public void a(UUID var1) {
      this.B = _snowman;
   }

   @Override
   public void a(String var1, boolean var2) {
      this.C.add(_snowman);
      this.D |= _snowman;
   }

   @Override
   public boolean F() {
      return this.D;
   }

   @Override
   public Set<String> G() {
      return ImmutableSet.copyOf(this.C);
   }

   @Override
   public cym H() {
      return this;
   }

   @Override
   public bsa I() {
      return this.b.h();
   }
}
