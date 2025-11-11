import com.google.common.collect.Lists;
import com.mojang.brigadier.ResultConsumer;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import java.util.Collection;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.function.BinaryOperator;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.server.MinecraftServer;

public class db implements dd {
   public static final SimpleCommandExceptionType a = new SimpleCommandExceptionType(new of("permissions.requires.player"));
   public static final SimpleCommandExceptionType b = new SimpleCommandExceptionType(new of("permissions.requires.entity"));
   private final da c;
   private final dcn d;
   private final aag e;
   private final int f;
   private final String g;
   private final nr h;
   private final MinecraftServer i;
   private final boolean j;
   @Nullable
   private final aqa k;
   private final ResultConsumer<db> l;
   private final dj.a m;
   private final dcm n;

   public db(da var1, dcn var2, dcm var3, aag var4, int var5, String var6, nr var7, MinecraftServer var8, @Nullable aqa var9) {
      this(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, false, (var0, var1x, var2x) -> {
      }, dj.a.a);
   }

   protected db(
      da var1,
      dcn var2,
      dcm var3,
      aag var4,
      int var5,
      String var6,
      nr var7,
      MinecraftServer var8,
      @Nullable aqa var9,
      boolean var10,
      ResultConsumer<db> var11,
      dj.a var12
   ) {
      this.c = _snowman;
      this.d = _snowman;
      this.e = _snowman;
      this.j = _snowman;
      this.k = _snowman;
      this.f = _snowman;
      this.g = _snowman;
      this.h = _snowman;
      this.i = _snowman;
      this.l = _snowman;
      this.m = _snowman;
      this.n = _snowman;
   }

   public db a(aqa var1) {
      return this.k == _snowman ? this : new db(this.c, this.d, this.n, this.e, this.f, _snowman.R().getString(), _snowman.d(), this.i, _snowman, this.j, this.l, this.m);
   }

   public db a(dcn var1) {
      return this.d.equals(_snowman) ? this : new db(this.c, _snowman, this.n, this.e, this.f, this.g, this.h, this.i, this.k, this.j, this.l, this.m);
   }

   public db a(dcm var1) {
      return this.n.c(_snowman) ? this : new db(this.c, this.d, _snowman, this.e, this.f, this.g, this.h, this.i, this.k, this.j, this.l, this.m);
   }

   public db a(ResultConsumer<db> var1) {
      return this.l.equals(_snowman) ? this : new db(this.c, this.d, this.n, this.e, this.f, this.g, this.h, this.i, this.k, this.j, _snowman, this.m);
   }

   public db a(ResultConsumer<db> var1, BinaryOperator<ResultConsumer<db>> var2) {
      ResultConsumer<db> _snowman = _snowman.apply(this.l, _snowman);
      return this.a(_snowman);
   }

   public db a() {
      return this.j ? this : new db(this.c, this.d, this.n, this.e, this.f, this.g, this.h, this.i, this.k, true, this.l, this.m);
   }

   public db a(int var1) {
      return _snowman == this.f ? this : new db(this.c, this.d, this.n, this.e, _snowman, this.g, this.h, this.i, this.k, this.j, this.l, this.m);
   }

   public db b(int var1) {
      return _snowman <= this.f ? this : new db(this.c, this.d, this.n, this.e, _snowman, this.g, this.h, this.i, this.k, this.j, this.l, this.m);
   }

   public db a(dj.a var1) {
      return _snowman == this.m ? this : new db(this.c, this.d, this.n, this.e, this.f, this.g, this.h, this.i, this.k, this.j, this.l, _snowman);
   }

   public db a(aag var1) {
      if (_snowman == this.e) {
         return this;
      } else {
         double _snowman = chd.a(this.e.k(), _snowman.k());
         dcn _snowmanx = new dcn(this.d.b * _snowman, this.d.c, this.d.d * _snowman);
         return new db(this.c, _snowmanx, this.n, _snowman, this.f, this.g, this.h, this.i, this.k, this.j, this.l, this.m);
      }
   }

   public db a(aqa var1, dj.a var2) throws CommandSyntaxException {
      return this.b(_snowman.a(_snowman));
   }

   public db b(dcn var1) throws CommandSyntaxException {
      dcn _snowman = this.m.a(this);
      double _snowmanx = _snowman.b - _snowman.b;
      double _snowmanxx = _snowman.c - _snowman.c;
      double _snowmanxxx = _snowman.d - _snowman.d;
      double _snowmanxxxx = (double)afm.a(_snowmanx * _snowmanx + _snowmanxxx * _snowmanxxx);
      float _snowmanxxxxx = afm.g((float)(-(afm.d(_snowmanxx, _snowmanxxxx) * 180.0F / (float)Math.PI)));
      float _snowmanxxxxxx = afm.g((float)(afm.d(_snowmanxxx, _snowmanx) * 180.0F / (float)Math.PI) - 90.0F);
      return this.a(new dcm(_snowmanxxxxx, _snowmanxxxxxx));
   }

   public nr b() {
      return this.h;
   }

   public String c() {
      return this.g;
   }

   @Override
   public boolean c(int var1) {
      return this.f >= _snowman;
   }

   public dcn d() {
      return this.d;
   }

   public aag e() {
      return this.e;
   }

   @Nullable
   public aqa f() {
      return this.k;
   }

   public aqa g() throws CommandSyntaxException {
      if (this.k == null) {
         throw b.create();
      } else {
         return this.k;
      }
   }

   public aah h() throws CommandSyntaxException {
      if (!(this.k instanceof aah)) {
         throw a.create();
      } else {
         return (aah)this.k;
      }
   }

   public dcm i() {
      return this.n;
   }

   public MinecraftServer j() {
      return this.i;
   }

   public dj.a k() {
      return this.m;
   }

   public void a(nr var1, boolean var2) {
      if (this.c.a() && !this.j) {
         this.c.a(_snowman, x.b);
      }

      if (_snowman && this.c.R_() && !this.j) {
         this.b(_snowman);
      }
   }

   private void b(nr var1) {
      nr _snowman = new of("chat.type.admin", this.b(), _snowman).a(new k[]{k.h, k.u});
      if (this.i.aL().b(brt.n)) {
         for (aah _snowmanx : this.i.ae().s()) {
            if (_snowmanx != this.c && this.i.ae().h(_snowmanx.eA())) {
               _snowmanx.a(_snowman, x.b);
            }
         }
      }

      if (this.c != this.i && this.i.aL().b(brt.k)) {
         this.i.a(_snowman, x.b);
      }
   }

   public void a(nr var1) {
      if (this.c.b() && !this.j) {
         this.c.a(new oe("").a(_snowman).a(k.m), x.b);
      }
   }

   public void a(CommandContext<db> var1, boolean var2, int var3) {
      if (this.l != null) {
         this.l.onCommandComplete(_snowman, _snowman, _snowman);
      }
   }

   @Override
   public Collection<String> l() {
      return Lists.newArrayList(this.i.K());
   }

   @Override
   public Collection<String> m() {
      return this.i.aH().f();
   }

   @Override
   public Collection<vk> n() {
      return gm.N.c();
   }

   @Override
   public Stream<vk> o() {
      return this.i.aF().d();
   }

   @Override
   public CompletableFuture<Suggestions> a(CommandContext<dd> var1, SuggestionsBuilder var2) {
      return null;
   }

   @Override
   public Set<vj<brx>> p() {
      return this.i.F();
   }

   @Override
   public gn q() {
      return this.i.aY();
   }
}
