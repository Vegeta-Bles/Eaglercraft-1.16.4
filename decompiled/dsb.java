import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.mojang.blaze3d.systems.RenderSystem;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.UUID;
import javax.annotation.Nullable;

public class dsb extends dlo<drz> {
   private final dsc a;
   private final djz o;
   private final List<drz> p = Lists.newArrayList();
   @Nullable
   private String q;

   public dsb(dsc var1, djz var2, int var3, int var4, int var5, int var6, int var7) {
      super(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
      this.a = _snowman;
      this.o = _snowman;
      this.b(false);
      this.c(false);
   }

   @Override
   public void a(dfm var1, int var2, int var3, float var4) {
      double _snowman = this.o.aD().s();
      RenderSystem.enableScissor(
         (int)((double)this.q() * _snowman),
         (int)((double)(this.e - this.j) * _snowman),
         (int)((double)(this.e() + 6) * _snowman),
         (int)((double)(this.e - (this.e - this.j) - this.i - 4) * _snowman)
      );
      super.a(_snowman, _snowman, _snowman, _snowman);
      RenderSystem.disableScissor();
   }

   public void a(Collection<UUID> var1, double var2) {
      this.p.clear();

      for (UUID _snowman : _snowman) {
         dwx _snowmanx = this.o.s.e.a(_snowman);
         if (_snowmanx != null) {
            this.p.add(new drz(this.o, this.a, _snowmanx.a().getId(), _snowmanx.a().getName(), _snowmanx::g));
         }
      }

      this.g();
      this.p.sort((var0, var1x) -> var0.b().compareToIgnoreCase(var1x.b()));
      this.a(this.p);
      this.a(_snowman);
   }

   private void g() {
      if (this.q != null) {
         this.p.removeIf(var1 -> !var1.b().toLowerCase(Locale.ROOT).contains(this.q));
         this.a(this.p);
      }
   }

   public void a(String var1) {
      this.q = _snowman;
   }

   public boolean f() {
      return this.p.isEmpty();
   }

   public void a(dwx var1, dsc.a var2) {
      UUID _snowman = _snowman.a().getId();

      for (drz _snowmanx : this.p) {
         if (_snowmanx.c().equals(_snowman)) {
            _snowmanx.c(false);
            return;
         }
      }

      if ((_snowman == dsc.a.a || this.o.aB().c(_snowman)) && (Strings.isNullOrEmpty(this.q) || _snowman.a().getName().toLowerCase(Locale.ROOT).contains(this.q))) {
         drz _snowmanxx = new drz(this.o, this.a, _snowman.a().getId(), _snowman.a().getName(), _snowman::g);
         this.b(_snowmanxx);
         this.p.add(_snowmanxx);
      }
   }

   public void a(UUID var1) {
      for (drz _snowman : this.p) {
         if (_snowman.c().equals(_snowman)) {
            _snowman.c(true);
            return;
         }
      }
   }
}
