import com.google.common.collect.Lists;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import java.io.IOException;
import java.util.List;
import java.util.Map.Entry;
import javax.annotation.Nullable;

public class pt implements oj<om> {
   private int a;
   private int b;
   private int c;
   private md d;
   @Nullable
   private int[] e;
   private byte[] f;
   private List<md> g;
   private boolean h;

   public pt() {
   }

   public pt(cgh var1, int var2) {
      brd _snowman = _snowman.g();
      this.a = _snowman.b;
      this.b = _snowman.c;
      this.h = _snowman == 65535;
      this.d = new md();

      for (Entry<chn.a, chn> _snowmanx : _snowman.f()) {
         if (_snowmanx.getKey().c()) {
            this.d.a(_snowmanx.getKey().b(), new mk(_snowmanx.getValue().a()));
         }
      }

      if (this.h) {
         this.e = _snowman.i().a();
      }

      this.f = new byte[this.a(_snowman, _snowman)];
      this.c = this.a(new nf(this.j()), _snowman, _snowman);
      this.g = Lists.newArrayList();

      for (Entry<fx, ccj> _snowmanxx : _snowman.y().entrySet()) {
         fx _snowmanxxx = _snowmanxx.getKey();
         ccj _snowmanxxxx = _snowmanxx.getValue();
         int _snowmanxxxxx = _snowmanxxx.v() >> 4;
         if (this.f() || (_snowman & 1 << _snowmanxxxxx) != 0) {
            md _snowmanxxxxxx = _snowmanxxxx.b();
            this.g.add(_snowmanxxxxxx);
         }
      }
   }

   @Override
   public void a(nf var1) throws IOException {
      this.a = _snowman.readInt();
      this.b = _snowman.readInt();
      this.h = _snowman.readBoolean();
      this.c = _snowman.i();
      this.d = _snowman.l();
      if (this.h) {
         this.e = _snowman.c(cfx.a);
      }

      int _snowman = _snowman.i();
      if (_snowman > 2097152) {
         throw new RuntimeException("Chunk Packet trying to allocate too much memory on read.");
      } else {
         this.f = new byte[_snowman];
         _snowman.readBytes(this.f);
         int _snowmanx = _snowman.i();
         this.g = Lists.newArrayList();

         for (int _snowmanxx = 0; _snowmanxx < _snowmanx; _snowmanxx++) {
            this.g.add(_snowman.l());
         }
      }
   }

   @Override
   public void b(nf var1) throws IOException {
      _snowman.writeInt(this.a);
      _snowman.writeInt(this.b);
      _snowman.writeBoolean(this.h);
      _snowman.d(this.c);
      _snowman.a(this.d);
      if (this.e != null) {
         _snowman.a(this.e);
      }

      _snowman.d(this.f.length);
      _snowman.writeBytes(this.f);
      _snowman.d(this.g.size());

      for (md _snowman : this.g) {
         _snowman.a(_snowman);
      }
   }

   public void a(om var1) {
      _snowman.a(this);
   }

   public nf b() {
      return new nf(Unpooled.wrappedBuffer(this.f));
   }

   private ByteBuf j() {
      ByteBuf _snowman = Unpooled.wrappedBuffer(this.f);
      _snowman.writerIndex(0);
      return _snowman;
   }

   public int a(nf var1, cgh var2, int var3) {
      int _snowman = 0;
      cgi[] _snowmanx = _snowman.d();
      int _snowmanxx = 0;

      for (int _snowmanxxx = _snowmanx.length; _snowmanxx < _snowmanxxx; _snowmanxx++) {
         cgi _snowmanxxxx = _snowmanx[_snowmanxx];
         if (_snowmanxxxx != cgh.a && (!this.f() || !_snowmanxxxx.c()) && (_snowman & 1 << _snowmanxx) != 0) {
            _snowman |= 1 << _snowmanxx;
            _snowmanxxxx.b(_snowman);
         }
      }

      return _snowman;
   }

   protected int a(cgh var1, int var2) {
      int _snowman = 0;
      cgi[] _snowmanx = _snowman.d();
      int _snowmanxx = 0;

      for (int _snowmanxxx = _snowmanx.length; _snowmanxx < _snowmanxxx; _snowmanxx++) {
         cgi _snowmanxxxx = _snowmanx[_snowmanxx];
         if (_snowmanxxxx != cgh.a && (!this.f() || !_snowmanxxxx.c()) && (_snowman & 1 << _snowmanxx) != 0) {
            _snowman += _snowmanxxxx.j();
         }
      }

      return _snowman;
   }

   public int c() {
      return this.a;
   }

   public int d() {
      return this.b;
   }

   public int e() {
      return this.c;
   }

   public boolean f() {
      return this.h;
   }

   public md g() {
      return this.d;
   }

   public List<md> h() {
      return this.g;
   }

   @Nullable
   public int[] i() {
      return this.e;
   }
}
