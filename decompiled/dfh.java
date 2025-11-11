import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.primitives.Floats;
import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.ints.IntArrays;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.util.BitSet;
import java.util.List;
import javax.annotation.Nullable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class dfh extends dfl implements dfj {
   private static final Logger g = LogManager.getLogger();
   private ByteBuffer h;
   private final List<dfh.a> i = Lists.newArrayList();
   private int j = 0;
   private int k = 0;
   private int l = 0;
   private int m = 0;
   private int n;
   @Nullable
   private dfs o;
   private int p;
   private int q;
   private dfr r;
   private boolean s;
   private boolean t;
   private boolean u;

   public dfh(int var1) {
      this.h = deq.a(_snowman * 4);
   }

   protected void a() {
      this.c(this.r.b());
   }

   private void c(int var1) {
      if (this.l + _snowman > this.h.capacity()) {
         int _snowman = this.h.capacity();
         int _snowmanx = _snowman + d(_snowman);
         g.debug("Needed to grow BufferBuilder buffer: Old size {} bytes, new size {} bytes.", _snowman, _snowmanx);
         ByteBuffer _snowmanxx = deq.a(_snowmanx);
         ((Buffer)this.h).position(0);
         _snowmanxx.put(this.h);
         ((Buffer)_snowmanxx).rewind();
         this.h = _snowmanxx;
      }
   }

   private static int d(int var0) {
      int _snowman = 2097152;
      if (_snowman == 0) {
         return _snowman;
      } else {
         if (_snowman < 0) {
            _snowman *= -1;
         }

         int _snowmanx = _snowman % _snowman;
         return _snowmanx == 0 ? _snowman : _snowman + _snowman - _snowmanx;
      }
   }

   public void a(float var1, float var2, float var3) {
      ((Buffer)this.h).clear();
      FloatBuffer _snowman = this.h.asFloatBuffer();
      int _snowmanx = this.n / 4;
      float[] _snowmanxx = new float[_snowmanx];

      for (int _snowmanxxx = 0; _snowmanxxx < _snowmanx; _snowmanxxx++) {
         _snowmanxx[_snowmanxxx] = a(_snowman, _snowman, _snowman, _snowman, this.r.a(), this.k / 4 + _snowmanxxx * this.r.b());
      }

      int[] _snowmanxxx = new int[_snowmanx];
      int _snowmanxxxx = 0;

      while (_snowmanxxxx < _snowmanxxx.length) {
         _snowmanxxx[_snowmanxxxx] = _snowmanxxxx++;
      }

      IntArrays.mergeSort(_snowmanxxx, (var1x, var2x) -> Floats.compare(_snowman[var2x], _snowman[var1x]));
      BitSet _snowmanxxxxx = new BitSet();
      FloatBuffer _snowmanxxxxxx = deq.b(this.r.a() * 4);

      for (int _snowmanxxxxxxx = _snowmanxxxxx.nextClearBit(0); _snowmanxxxxxxx < _snowmanxxx.length; _snowmanxxxxxxx = _snowmanxxxxx.nextClearBit(_snowmanxxxxxxx + 1)) {
         int _snowmanxxxxxxxx = _snowmanxxx[_snowmanxxxxxxx];
         if (_snowmanxxxxxxxx != _snowmanxxxxxxx) {
            this.a(_snowman, _snowmanxxxxxxxx);
            ((Buffer)_snowmanxxxxxx).clear();
            _snowmanxxxxxx.put(_snowman);
            int _snowmanxxxxxxxxx = _snowmanxxxxxxxx;

            for (int _snowmanxxxxxxxxxx = _snowmanxxx[_snowmanxxxxxxxx]; _snowmanxxxxxxxxx != _snowmanxxxxxxx; _snowmanxxxxxxxxxx = _snowmanxxx[_snowmanxxxxxxxxxx]) {
               this.a(_snowman, _snowmanxxxxxxxxxx);
               FloatBuffer _snowmanxxxxxxxxxxx = _snowman.slice();
               this.a(_snowman, _snowmanxxxxxxxxx);
               _snowman.put(_snowmanxxxxxxxxxxx);
               _snowmanxxxxx.set(_snowmanxxxxxxxxx);
               _snowmanxxxxxxxxx = _snowmanxxxxxxxxxx;
            }

            this.a(_snowman, _snowmanxxxxxxx);
            ((Buffer)_snowmanxxxxxx).flip();
            _snowman.put(_snowmanxxxxxx);
         }

         _snowmanxxxxx.set(_snowmanxxxxxxx);
      }
   }

   private void a(FloatBuffer var1, int var2) {
      int _snowman = this.r.a() * 4;
      ((Buffer)_snowman).limit(this.k / 4 + (_snowman + 1) * _snowman);
      ((Buffer)_snowman).position(this.k / 4 + _snowman * _snowman);
   }

   public dfh.b b() {
      ((Buffer)this.h).limit(this.l);
      ((Buffer)this.h).position(this.k);
      ByteBuffer _snowman = ByteBuffer.allocate(this.n * this.r.b());
      _snowman.put(this.h);
      ((Buffer)this.h).clear();
      return new dfh.b(_snowman, this.r);
   }

   private static float a(FloatBuffer var0, float var1, float var2, float var3, int var4, int var5) {
      float _snowman = _snowman.get(_snowman + _snowman * 0 + 0);
      float _snowmanx = _snowman.get(_snowman + _snowman * 0 + 1);
      float _snowmanxx = _snowman.get(_snowman + _snowman * 0 + 2);
      float _snowmanxxx = _snowman.get(_snowman + _snowman * 1 + 0);
      float _snowmanxxxx = _snowman.get(_snowman + _snowman * 1 + 1);
      float _snowmanxxxxx = _snowman.get(_snowman + _snowman * 1 + 2);
      float _snowmanxxxxxx = _snowman.get(_snowman + _snowman * 2 + 0);
      float _snowmanxxxxxxx = _snowman.get(_snowman + _snowman * 2 + 1);
      float _snowmanxxxxxxxx = _snowman.get(_snowman + _snowman * 2 + 2);
      float _snowmanxxxxxxxxx = _snowman.get(_snowman + _snowman * 3 + 0);
      float _snowmanxxxxxxxxxx = _snowman.get(_snowman + _snowman * 3 + 1);
      float _snowmanxxxxxxxxxxx = _snowman.get(_snowman + _snowman * 3 + 2);
      float _snowmanxxxxxxxxxxxx = (_snowman + _snowmanxxx + _snowmanxxxxxx + _snowmanxxxxxxxxx) * 0.25F - _snowman;
      float _snowmanxxxxxxxxxxxxx = (_snowmanx + _snowmanxxxx + _snowmanxxxxxxx + _snowmanxxxxxxxxxx) * 0.25F - _snowman;
      float _snowmanxxxxxxxxxxxxxx = (_snowmanxx + _snowmanxxxxx + _snowmanxxxxxxxx + _snowmanxxxxxxxxxxx) * 0.25F - _snowman;
      return _snowmanxxxxxxxxxxxx * _snowmanxxxxxxxxxxxx + _snowmanxxxxxxxxxxxxx * _snowmanxxxxxxxxxxxxx + _snowmanxxxxxxxxxxxxxx * _snowmanxxxxxxxxxxxxxx;
   }

   public void a(dfh.b var1) {
      ((Buffer)_snowman.a).clear();
      int _snowman = _snowman.a.capacity();
      this.c(_snowman);
      ((Buffer)this.h).limit(this.h.capacity());
      ((Buffer)this.h).position(this.k);
      this.h.put(_snowman.a);
      ((Buffer)this.h).clear();
      dfr _snowmanx = _snowman.b;
      this.a(_snowmanx);
      this.n = _snowman / _snowmanx.b();
      this.l = this.k + this.n * _snowmanx.b();
   }

   public void a(int var1, dfr var2) {
      if (this.u) {
         throw new IllegalStateException("Already building!");
      } else {
         this.u = true;
         this.q = _snowman;
         this.a(_snowman);
         this.o = (dfs)_snowman.c().get(0);
         this.p = 0;
         ((Buffer)this.h).clear();
      }
   }

   private void a(dfr var1) {
      if (this.r != _snowman) {
         this.r = _snowman;
         boolean _snowman = _snowman == dfk.i;
         boolean _snowmanx = _snowman == dfk.h;
         this.s = _snowman || _snowmanx;
         this.t = _snowman;
      }
   }

   public void c() {
      if (!this.u) {
         throw new IllegalStateException("Not building!");
      } else {
         this.u = false;
         this.i.add(new dfh.a(this.r, this.n, this.q));
         this.k = this.k + this.n * this.r.b();
         this.n = 0;
         this.o = null;
         this.p = 0;
      }
   }

   @Override
   public void a(int var1, byte var2) {
      this.h.put(this.l + _snowman, _snowman);
   }

   @Override
   public void a(int var1, short var2) {
      this.h.putShort(this.l + _snowman, _snowman);
   }

   @Override
   public void a(int var1, float var2) {
      this.h.putFloat(this.l + _snowman, _snowman);
   }

   @Override
   public void d() {
      if (this.p != 0) {
         throw new IllegalStateException("Not filled all elements of the vertex");
      } else {
         this.n++;
         this.a();
      }
   }

   @Override
   public void e() {
      ImmutableList<dfs> _snowman = this.r.c();
      this.p = (this.p + 1) % _snowman.size();
      this.l = this.l + this.o.d();
      dfs _snowmanx = (dfs)_snowman.get(this.p);
      this.o = _snowmanx;
      if (_snowmanx.b() == dfs.b.e) {
         this.e();
      }

      if (this.a && this.o.b() == dfs.b.c) {
         dfj.super.a(this.b, this.c, this.d, this.e);
      }
   }

   @Override
   public dfq a(int var1, int var2, int var3, int var4) {
      if (this.a) {
         throw new IllegalStateException();
      } else {
         return dfj.super.a(_snowman, _snowman, _snowman, _snowman);
      }
   }

   @Override
   public void a(
      float var1,
      float var2,
      float var3,
      float var4,
      float var5,
      float var6,
      float var7,
      float var8,
      float var9,
      int var10,
      int var11,
      float var12,
      float var13,
      float var14
   ) {
      if (this.a) {
         throw new IllegalStateException();
      } else if (this.s) {
         this.a(0, _snowman);
         this.a(4, _snowman);
         this.a(8, _snowman);
         this.a(12, (byte)((int)(_snowman * 255.0F)));
         this.a(13, (byte)((int)(_snowman * 255.0F)));
         this.a(14, (byte)((int)(_snowman * 255.0F)));
         this.a(15, (byte)((int)(_snowman * 255.0F)));
         this.a(16, _snowman);
         this.a(20, _snowman);
         int _snowman;
         if (this.t) {
            this.a(24, (short)(_snowman & 65535));
            this.a(26, (short)(_snowman >> 16 & 65535));
            _snowman = 28;
         } else {
            _snowman = 24;
         }

         this.a(_snowman + 0, (short)(_snowman & 65535));
         this.a(_snowman + 2, (short)(_snowman >> 16 & 65535));
         this.a(_snowman + 4, dfj.a(_snowman));
         this.a(_snowman + 5, dfj.a(_snowman));
         this.a(_snowman + 6, dfj.a(_snowman));
         this.l += _snowman + 8;
         this.d();
      } else {
         super.a(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
      }
   }

   public Pair<dfh.a, ByteBuffer> f() {
      dfh.a _snowman = this.i.get(this.j++);
      ((Buffer)this.h).position(this.m);
      this.m = this.m + _snowman.b() * _snowman.a().b();
      ((Buffer)this.h).limit(this.m);
      if (this.j == this.i.size() && this.n == 0) {
         this.g();
      }

      ByteBuffer _snowmanx = this.h.slice();
      ((Buffer)this.h).clear();
      return Pair.of(_snowman, _snowmanx);
   }

   public void g() {
      if (this.k != this.m) {
         g.warn("Bytes mismatch " + this.k + " " + this.m);
      }

      this.h();
   }

   public void h() {
      this.k = 0;
      this.m = 0;
      this.l = 0;
      this.i.clear();
      this.j = 0;
   }

   @Override
   public dfs i() {
      if (this.o == null) {
         throw new IllegalStateException("BufferBuilder not started");
      } else {
         return this.o;
      }
   }

   public boolean j() {
      return this.u;
   }

   public static final class a {
      private final dfr a;
      private final int b;
      private final int c;

      private a(dfr var1, int var2, int var3) {
         this.a = _snowman;
         this.b = _snowman;
         this.c = _snowman;
      }

      public dfr a() {
         return this.a;
      }

      public int b() {
         return this.b;
      }

      public int c() {
         return this.c;
      }
   }

   public static class b {
      private final ByteBuffer a;
      private final dfr b;

      private b(ByteBuffer var1, dfr var2) {
         this.a = _snowman;
         this.b = _snowman;
      }
   }
}
