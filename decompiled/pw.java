import com.google.common.collect.Lists;
import java.io.IOException;
import java.util.List;

public class pw implements oj<om> {
   private int a;
   private int b;
   private int c;
   private int d;
   private int e;
   private int f;
   private List<byte[]> g;
   private List<byte[]> h;
   private boolean i;

   public pw() {
   }

   public pw(brd var1, cuo var2, boolean var3) {
      this.a = _snowman.b;
      this.b = _snowman.c;
      this.i = _snowman;
      this.g = Lists.newArrayList();
      this.h = Lists.newArrayList();

      for (int _snowman = 0; _snowman < 18; _snowman++) {
         cgb _snowmanx = _snowman.a(bsf.a).a(gp.a(_snowman, -1 + _snowman));
         cgb _snowmanxx = _snowman.a(bsf.b).a(gp.a(_snowman, -1 + _snowman));
         if (_snowmanx != null) {
            if (_snowmanx.c()) {
               this.e |= 1 << _snowman;
            } else {
               this.c |= 1 << _snowman;
               this.g.add((byte[])_snowmanx.a().clone());
            }
         }

         if (_snowmanxx != null) {
            if (_snowmanxx.c()) {
               this.f |= 1 << _snowman;
            } else {
               this.d |= 1 << _snowman;
               this.h.add((byte[])_snowmanxx.a().clone());
            }
         }
      }
   }

   public pw(brd var1, cuo var2, int var3, int var4, boolean var5) {
      this.a = _snowman.b;
      this.b = _snowman.c;
      this.i = _snowman;
      this.c = _snowman;
      this.d = _snowman;
      this.g = Lists.newArrayList();
      this.h = Lists.newArrayList();

      for (int _snowman = 0; _snowman < 18; _snowman++) {
         if ((this.c & 1 << _snowman) != 0) {
            cgb _snowmanx = _snowman.a(bsf.a).a(gp.a(_snowman, -1 + _snowman));
            if (_snowmanx != null && !_snowmanx.c()) {
               this.g.add((byte[])_snowmanx.a().clone());
            } else {
               this.c &= ~(1 << _snowman);
               if (_snowmanx != null) {
                  this.e |= 1 << _snowman;
               }
            }
         }

         if ((this.d & 1 << _snowman) != 0) {
            cgb _snowmanx = _snowman.a(bsf.b).a(gp.a(_snowman, -1 + _snowman));
            if (_snowmanx != null && !_snowmanx.c()) {
               this.h.add((byte[])_snowmanx.a().clone());
            } else {
               this.d &= ~(1 << _snowman);
               if (_snowmanx != null) {
                  this.f |= 1 << _snowman;
               }
            }
         }
      }
   }

   @Override
   public void a(nf var1) throws IOException {
      this.a = _snowman.i();
      this.b = _snowman.i();
      this.i = _snowman.readBoolean();
      this.c = _snowman.i();
      this.d = _snowman.i();
      this.e = _snowman.i();
      this.f = _snowman.i();
      this.g = Lists.newArrayList();

      for (int _snowman = 0; _snowman < 18; _snowman++) {
         if ((this.c & 1 << _snowman) != 0) {
            this.g.add(_snowman.b(2048));
         }
      }

      this.h = Lists.newArrayList();

      for (int _snowmanx = 0; _snowmanx < 18; _snowmanx++) {
         if ((this.d & 1 << _snowmanx) != 0) {
            this.h.add(_snowman.b(2048));
         }
      }
   }

   @Override
   public void b(nf var1) throws IOException {
      _snowman.d(this.a);
      _snowman.d(this.b);
      _snowman.writeBoolean(this.i);
      _snowman.d(this.c);
      _snowman.d(this.d);
      _snowman.d(this.e);
      _snowman.d(this.f);

      for (byte[] _snowman : this.g) {
         _snowman.a(_snowman);
      }

      for (byte[] _snowman : this.h) {
         _snowman.a(_snowman);
      }
   }

   public void a(om var1) {
      _snowman.a(this);
   }

   public int b() {
      return this.a;
   }

   public int c() {
      return this.b;
   }

   public int d() {
      return this.c;
   }

   public int e() {
      return this.e;
   }

   public List<byte[]> f() {
      return this.g;
   }

   public int g() {
      return this.d;
   }

   public int h() {
      return this.f;
   }

   public List<byte[]> i() {
      return this.h;
   }

   public boolean j() {
      return this.i;
   }
}
