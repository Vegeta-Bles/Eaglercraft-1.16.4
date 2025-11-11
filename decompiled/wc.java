import com.google.common.collect.Sets;
import java.util.Collection;
import java.util.Set;
import java.util.UUID;

public class wc extends aad {
   private final vk h;
   private final Set<UUID> i = Sets.newHashSet();
   private int j;
   private int k = 100;

   public wc(vk var1, nr var2) {
      super(_snowman, aok.a.g, aok.b.a);
      this.h = _snowman;
      this.a(0.0F);
   }

   public vk a() {
      return this.h;
   }

   @Override
   public void a(aah var1) {
      super.a(_snowman);
      this.i.add(_snowman.bS());
   }

   public void a(UUID var1) {
      this.i.add(_snowman);
   }

   @Override
   public void b(aah var1) {
      super.b(_snowman);
      this.i.remove(_snowman.bS());
   }

   @Override
   public void b() {
      super.b();
      this.i.clear();
   }

   public int c() {
      return this.j;
   }

   public int d() {
      return this.k;
   }

   public void a(int var1) {
      this.j = _snowman;
      this.a(afm.a((float)_snowman / (float)this.k, 0.0F, 1.0F));
   }

   public void b(int var1) {
      this.k = _snowman;
      this.a(afm.a((float)this.j / (float)_snowman, 0.0F, 1.0F));
   }

   public final nr e() {
      return ns.a(this.j()).a(var1 -> var1.a(this.l().a()).a(new nv(nv.a.a, new oe(this.a().toString()))).a(this.a().toString()));
   }

   public boolean a(Collection<aah> var1) {
      Set<UUID> _snowman = Sets.newHashSet();
      Set<aah> _snowmanx = Sets.newHashSet();

      for (UUID _snowmanxx : this.i) {
         boolean _snowmanxxx = false;

         for (aah _snowmanxxxx : _snowman) {
            if (_snowmanxxxx.bS().equals(_snowmanxx)) {
               _snowmanxxx = true;
               break;
            }
         }

         if (!_snowmanxxx) {
            _snowman.add(_snowmanxx);
         }
      }

      for (aah _snowmanxx : _snowman) {
         boolean _snowmanxxx = false;

         for (UUID _snowmanxxxxx : this.i) {
            if (_snowmanxx.bS().equals(_snowmanxxxxx)) {
               _snowmanxxx = true;
               break;
            }
         }

         if (!_snowmanxxx) {
            _snowmanx.add(_snowmanxx);
         }
      }

      for (UUID _snowmanxx : _snowman) {
         for (aah _snowmanxxx : this.h()) {
            if (_snowmanxxx.bS().equals(_snowmanxx)) {
               this.b(_snowmanxxx);
               break;
            }
         }

         this.i.remove(_snowmanxx);
      }

      for (aah _snowmanxx : _snowmanx) {
         this.a(_snowmanxx);
      }

      return !_snowman.isEmpty() || !_snowmanx.isEmpty();
   }

   public md f() {
      md _snowman = new md();
      _snowman.a("Name", nr.a.a(this.a));
      _snowman.a("Visible", this.g());
      _snowman.b("Value", this.j);
      _snowman.b("Max", this.k);
      _snowman.a("Color", this.l().b());
      _snowman.a("Overlay", this.m().a());
      _snowman.a("DarkenScreen", this.n());
      _snowman.a("PlayBossMusic", this.o());
      _snowman.a("CreateWorldFog", this.p());
      mj _snowmanx = new mj();

      for (UUID _snowmanxx : this.i) {
         _snowmanx.add(mp.a(_snowmanxx));
      }

      _snowman.a("Players", _snowmanx);
      return _snowman;
   }

   public static wc a(md var0, vk var1) {
      wc _snowman = new wc(_snowman, nr.a.a(_snowman.l("Name")));
      _snowman.d(_snowman.q("Visible"));
      _snowman.a(_snowman.h("Value"));
      _snowman.b(_snowman.h("Max"));
      _snowman.a(aok.a.a(_snowman.l("Color")));
      _snowman.a(aok.b.a(_snowman.l("Overlay")));
      _snowman.a(_snowman.q("DarkenScreen"));
      _snowman.b(_snowman.q("PlayBossMusic"));
      _snowman.c(_snowman.q("CreateWorldFog"));
      mj _snowmanx = _snowman.d("Players", 11);

      for (int _snowmanxx = 0; _snowmanxx < _snowmanx.size(); _snowmanxx++) {
         _snowman.a(mp.a(_snowmanx.k(_snowmanxx)));
      }

      return _snowman;
   }

   public void c(aah var1) {
      if (this.i.contains(_snowman.bS())) {
         this.a(_snowman);
      }
   }

   public void d(aah var1) {
      super.b(_snowman);
   }
}
