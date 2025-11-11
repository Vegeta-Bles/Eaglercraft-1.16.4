import it.unimi.dsi.fastutil.longs.Long2ObjectLinkedOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import javax.annotation.Nullable;

public final class cgz implements AutoCloseable {
   private final Long2ObjectLinkedOpenHashMap<cgy> a = new Long2ObjectLinkedOpenHashMap();
   private final File b;
   private final boolean c;

   cgz(File var1, boolean var2) {
      this.b = _snowman;
      this.c = _snowman;
   }

   private cgy b(brd var1) throws IOException {
      long _snowman = brd.a(_snowman.h(), _snowman.i());
      cgy _snowmanx = (cgy)this.a.getAndMoveToFirst(_snowman);
      if (_snowmanx != null) {
         return _snowmanx;
      } else {
         if (this.a.size() >= 256) {
            ((cgy)this.a.removeLast()).close();
         }

         if (!this.b.exists()) {
            this.b.mkdirs();
         }

         File _snowmanxx = new File(this.b, "r." + _snowman.h() + "." + _snowman.i() + ".mca");
         cgy _snowmanxxx = new cgy(_snowmanxx, this.b, this.c);
         this.a.putAndMoveToFirst(_snowman, _snowmanxxx);
         return _snowmanxxx;
      }
   }

   @Nullable
   public md a(brd var1) throws IOException {
      cgy _snowman = this.b(_snowman);

      Object var5;
      try (DataInputStream _snowmanx = _snowman.a(_snowman)) {
         if (_snowmanx != null) {
            return mn.a((DataInput)_snowmanx);
         }

         var5 = null;
      }

      return (md)var5;
   }

   protected void a(brd var1, md var2) throws IOException {
      cgy _snowman = this.b(_snowman);

      try (DataOutputStream _snowmanx = _snowman.c(_snowman)) {
         mn.a(_snowman, (DataOutput)_snowmanx);
      }
   }

   @Override
   public void close() throws IOException {
      aey<IOException> _snowman = new aey<>();
      ObjectIterator var2 = this.a.values().iterator();

      while (var2.hasNext()) {
         cgy _snowmanx = (cgy)var2.next();

         try {
            _snowmanx.close();
         } catch (IOException var5) {
            _snowman.a(var5);
         }
      }

      _snowman.a();
   }

   public void a() throws IOException {
      ObjectIterator var1 = this.a.values().iterator();

      while (var1.hasNext()) {
         cgy _snowman = (cgy)var1.next();
         _snowman.a();
      }
   }
}
