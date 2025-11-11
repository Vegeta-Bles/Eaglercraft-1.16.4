import com.google.common.collect.Lists;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

public class ru implements oj<om> {
   private int a;
   private final List<ru.a> b = Lists.newArrayList();

   public ru() {
   }

   public ru(int var1, Collection<arh> var2) {
      this.a = _snowman;

      for (arh _snowman : _snowman) {
         this.b.add(new ru.a(_snowman.a(), _snowman.b(), _snowman.c()));
      }
   }

   @Override
   public void a(nf var1) throws IOException {
      this.a = _snowman.i();
      int _snowman = _snowman.readInt();

      for (int _snowmanx = 0; _snowmanx < _snowman; _snowmanx++) {
         vk _snowmanxx = _snowman.p();
         arg _snowmanxxx = gm.af.a(_snowmanxx);
         double _snowmanxxxx = _snowman.readDouble();
         List<arj> _snowmanxxxxx = Lists.newArrayList();
         int _snowmanxxxxxx = _snowman.i();

         for (int _snowmanxxxxxxx = 0; _snowmanxxxxxxx < _snowmanxxxxxx; _snowmanxxxxxxx++) {
            UUID _snowmanxxxxxxxx = _snowman.k();
            _snowmanxxxxx.add(new arj(_snowmanxxxxxxxx, "Unknown synced attribute modifier", _snowman.readDouble(), arj.a.a(_snowman.readByte())));
         }

         this.b.add(new ru.a(_snowmanxxx, _snowmanxxxx, _snowmanxxxxx));
      }
   }

   @Override
   public void b(nf var1) throws IOException {
      _snowman.d(this.a);
      _snowman.writeInt(this.b.size());

      for (ru.a _snowman : this.b) {
         _snowman.a(gm.af.b(_snowman.a()));
         _snowman.writeDouble(_snowman.b());
         _snowman.d(_snowman.c().size());

         for (arj _snowmanx : _snowman.c()) {
            _snowman.a(_snowmanx.a());
            _snowman.writeDouble(_snowmanx.d());
            _snowman.writeByte(_snowmanx.c().a());
         }
      }
   }

   public void a(om var1) {
      _snowman.a(this);
   }

   public int b() {
      return this.a;
   }

   public List<ru.a> c() {
      return this.b;
   }

   public class a {
      private final arg b;
      private final double c;
      private final Collection<arj> d;

      public a(arg var2, double var3, Collection<arj> var5) {
         this.b = _snowman;
         this.c = _snowman;
         this.d = _snowman;
      }

      public arg a() {
         return this.b;
      }

      public double b() {
         return this.c;
      }

      public Collection<arj> c() {
         return this.d;
      }
   }
}
