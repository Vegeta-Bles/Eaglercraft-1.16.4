import com.google.common.collect.Lists;
import com.mojang.brigadier.CommandDispatcher;
import java.util.ArrayDeque;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import net.minecraft.server.MinecraftServer;

public class vx {
   private static final vk a = new vk("tick");
   private static final vk b = new vk("load");
   private final MinecraftServer c;
   private boolean d;
   private final ArrayDeque<vx.a> e = new ArrayDeque<>();
   private final List<vx.a> f = Lists.newArrayList();
   private final List<cy> g = Lists.newArrayList();
   private boolean h;
   private vw i;

   public vx(MinecraftServer var1, vw var2) {
      this.c = _snowman;
      this.i = _snowman;
      this.b(_snowman);
   }

   public int b() {
      return this.c.aL().c(brt.v);
   }

   public CommandDispatcher<db> c() {
      return this.c.aD().a();
   }

   public void d() {
      this.a(this.g, a);
      if (this.h) {
         this.h = false;
         Collection<cy> _snowman = this.i.b().b(b).b();
         this.a(_snowman, b);
      }
   }

   private void a(Collection<cy> var1, vk var2) {
      this.c.aQ().a(_snowman::toString);

      for (cy _snowman : _snowman) {
         this.a(_snowman, this.e());
      }

      this.c.aQ().c();
   }

   public int a(cy var1, db var2) {
      int _snowman = this.b();
      if (this.d) {
         if (this.e.size() + this.f.size() < _snowman) {
            this.f.add(new vx.a(this, _snowman, new cy.d(_snowman)));
         }

         return 0;
      } else {
         int var16;
         try {
            this.d = true;
            int _snowmanx = 0;
            cy.c[] _snowmanxx = _snowman.b();

            for (int _snowmanxxx = _snowmanxx.length - 1; _snowmanxxx >= 0; _snowmanxxx--) {
               this.e.push(new vx.a(this, _snowman, _snowmanxx[_snowmanxxx]));
            }

            do {
               if (this.e.isEmpty()) {
                  return _snowmanx;
               }

               try {
                  vx.a _snowmanxxx = this.e.removeFirst();
                  this.c.aQ().a(_snowmanxxx::toString);
                  _snowmanxxx.a(this.e, _snowman);
                  if (!this.f.isEmpty()) {
                     Lists.reverse(this.f).forEach(this.e::addFirst);
                     this.f.clear();
                  }
               } finally {
                  this.c.aQ().c();
               }
            } while (++_snowmanx < _snowman);

            var16 = _snowmanx;
         } finally {
            this.e.clear();
            this.f.clear();
            this.d = false;
         }

         return var16;
      }
   }

   public void a(vw var1) {
      this.i = _snowman;
      this.b(_snowman);
   }

   private void b(vw var1) {
      this.g.clear();
      this.g.addAll(_snowman.b().b(a).b());
      this.h = true;
   }

   public db e() {
      return this.c.aE().a(2).a();
   }

   public Optional<cy> a(vk var1) {
      return this.i.a(_snowman);
   }

   public ael<cy> b(vk var1) {
      return this.i.b(_snowman);
   }

   public Iterable<vk> f() {
      return this.i.a().keySet();
   }

   public Iterable<vk> g() {
      return this.i.b().b();
   }

   public static class a {
      private final vx a;
      private final db b;
      private final cy.c c;

      public a(vx var1, db var2, cy.c var3) {
         this.a = _snowman;
         this.b = _snowman;
         this.c = _snowman;
      }

      public void a(ArrayDeque<vx.a> var1, int var2) {
         try {
            this.c.a(this.a, this.b, _snowman, _snowman);
         } catch (Throwable var4) {
         }
      }

      @Override
      public String toString() {
         return this.c.toString();
      }
   }
}
