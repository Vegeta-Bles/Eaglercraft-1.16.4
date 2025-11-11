import com.google.common.base.MoreObjects;
import com.google.common.collect.Maps;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.MinecraftProfileTexture.Type;
import java.util.Map;
import javax.annotation.Nullable;

public class dwx {
   private final GameProfile a;
   private final Map<Type, vk> b = Maps.newEnumMap(Type.class);
   private bru c;
   private int d;
   private boolean e;
   @Nullable
   private String f;
   @Nullable
   private nr g;
   private int h;
   private int i;
   private long j;
   private long k;
   private long l;

   public dwx(qi.b var1) {
      this.a = _snowman.a();
      this.c = _snowman.c();
      this.d = _snowman.b();
      this.g = _snowman.d();
   }

   public GameProfile a() {
      return this.a;
   }

   @Nullable
   public bru b() {
      return this.c;
   }

   protected void a(bru var1) {
      this.c = _snowman;
   }

   public int c() {
      return this.d;
   }

   protected void a(int var1) {
      this.d = _snowman;
   }

   public boolean e() {
      return this.g() != null;
   }

   public String f() {
      return this.f == null ? ekj.b(this.a.getId()) : this.f;
   }

   public vk g() {
      this.k();
      return (vk)MoreObjects.firstNonNull(this.b.get(Type.SKIN), ekj.a(this.a.getId()));
   }

   @Nullable
   public vk h() {
      this.k();
      return this.b.get(Type.CAPE);
   }

   @Nullable
   public vk i() {
      this.k();
      return this.b.get(Type.ELYTRA);
   }

   @Nullable
   public ddl j() {
      return djz.C().r.G().i(this.a().getName());
   }

   protected void k() {
      synchronized (this) {
         if (!this.e) {
            this.e = true;
            djz.C().Z().a(this.a, (var1, var2, var3) -> {
               this.b.put(var1, var2);
               if (var1 == Type.SKIN) {
                  this.f = var3.getMetadata("model");
                  if (this.f == null) {
                     this.f = "default";
                  }
               }
            }, true);
         }
      }
   }

   public void a(@Nullable nr var1) {
      this.g = _snowman;
   }

   @Nullable
   public nr l() {
      return this.g;
   }

   public int m() {
      return this.h;
   }

   public void b(int var1) {
      this.h = _snowman;
   }

   public int n() {
      return this.i;
   }

   public void c(int var1) {
      this.i = _snowman;
   }

   public long o() {
      return this.j;
   }

   public void a(long var1) {
      this.j = _snowman;
   }

   public long p() {
      return this.k;
   }

   public void b(long var1) {
      this.k = _snowman;
   }

   public long q() {
      return this.l;
   }

   public void c(long var1) {
      this.l = _snowman;
   }
}
