import it.unimi.dsi.fastutil.shorts.ShortIterator;
import it.unimi.dsi.fastutil.shorts.ShortSet;
import java.io.IOException;
import java.util.function.BiConsumer;

public class qr implements oj<om> {
   private gp a;
   private short[] b;
   private ceh[] c;
   private boolean d;

   public qr() {
   }

   public qr(gp var1, ShortSet var2, cgi var3, boolean var4) {
      this.a = _snowman;
      this.d = _snowman;
      this.a(_snowman.size());
      int _snowman = 0;

      for (ShortIterator var6 = _snowman.iterator(); var6.hasNext(); _snowman++) {
         short _snowmanx = (Short)var6.next();
         this.b[_snowman] = _snowmanx;
         this.c[_snowman] = _snowman.a(gp.a(_snowmanx), gp.b(_snowmanx), gp.c(_snowmanx));
      }
   }

   private void a(int var1) {
      this.b = new short[_snowman];
      this.c = new ceh[_snowman];
   }

   @Override
   public void a(nf var1) throws IOException {
      this.a = gp.a(_snowman.readLong());
      this.d = _snowman.readBoolean();
      int _snowman = _snowman.i();
      this.a(_snowman);

      for (int _snowmanx = 0; _snowmanx < this.b.length; _snowmanx++) {
         long _snowmanxx = _snowman.j();
         this.b[_snowmanx] = (short)((int)(_snowmanxx & 4095L));
         this.c[_snowmanx] = buo.m.a((int)(_snowmanxx >>> 12));
      }
   }

   @Override
   public void b(nf var1) throws IOException {
      _snowman.writeLong(this.a.s());
      _snowman.writeBoolean(this.d);
      _snowman.d(this.b.length);

      for (int _snowman = 0; _snowman < this.b.length; _snowman++) {
         _snowman.b((long)(buo.i(this.c[_snowman]) << 12 | this.b[_snowman]));
      }
   }

   public void a(om var1) {
      _snowman.a(this);
   }

   public void a(BiConsumer<fx, ceh> var1) {
      fx.a _snowman = new fx.a();

      for (int _snowmanx = 0; _snowmanx < this.b.length; _snowmanx++) {
         short _snowmanxx = this.b[_snowmanx];
         _snowman.d(this.a.d(_snowmanxx), this.a.e(_snowmanxx), this.a.f(_snowmanxx));
         _snowman.accept(_snowman, this.c[_snowmanx]);
      }
   }

   public boolean b() {
      return this.d;
   }
}
