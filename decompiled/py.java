import java.io.IOException;
import java.util.Collection;

public class py implements oj<om> {
   private int a;
   private byte b;
   private boolean c;
   private boolean d;
   private cxu[] e;
   private int f;
   private int g;
   private int h;
   private int i;
   private byte[] j;

   public py() {
   }

   public py(int var1, byte var2, boolean var3, boolean var4, Collection<cxu> var5, byte[] var6, int var7, int var8, int var9, int var10) {
      this.a = _snowman;
      this.b = _snowman;
      this.c = _snowman;
      this.d = _snowman;
      this.e = _snowman.toArray(new cxu[_snowman.size()]);
      this.f = _snowman;
      this.g = _snowman;
      this.h = _snowman;
      this.i = _snowman;
      this.j = new byte[_snowman * _snowman];

      for (int _snowman = 0; _snowman < _snowman; _snowman++) {
         for (int _snowmanx = 0; _snowmanx < _snowman; _snowmanx++) {
            this.j[_snowman + _snowmanx * _snowman] = _snowman[_snowman + _snowman + (_snowman + _snowmanx) * 128];
         }
      }
   }

   @Override
   public void a(nf var1) throws IOException {
      this.a = _snowman.i();
      this.b = _snowman.readByte();
      this.c = _snowman.readBoolean();
      this.d = _snowman.readBoolean();
      this.e = new cxu[_snowman.i()];

      for (int _snowman = 0; _snowman < this.e.length; _snowman++) {
         cxu.a _snowmanx = _snowman.a(cxu.a.class);
         this.e[_snowman] = new cxu(_snowmanx, _snowman.readByte(), _snowman.readByte(), (byte)(_snowman.readByte() & 15), _snowman.readBoolean() ? _snowman.h() : null);
      }

      this.h = _snowman.readUnsignedByte();
      if (this.h > 0) {
         this.i = _snowman.readUnsignedByte();
         this.f = _snowman.readUnsignedByte();
         this.g = _snowman.readUnsignedByte();
         this.j = _snowman.a();
      }
   }

   @Override
   public void b(nf var1) throws IOException {
      _snowman.d(this.a);
      _snowman.writeByte(this.b);
      _snowman.writeBoolean(this.c);
      _snowman.writeBoolean(this.d);
      _snowman.d(this.e.length);

      for (cxu _snowman : this.e) {
         _snowman.a(_snowman.b());
         _snowman.writeByte(_snowman.c());
         _snowman.writeByte(_snowman.d());
         _snowman.writeByte(_snowman.e() & 15);
         if (_snowman.g() != null) {
            _snowman.writeBoolean(true);
            _snowman.a(_snowman.g());
         } else {
            _snowman.writeBoolean(false);
         }
      }

      _snowman.writeByte(this.h);
      if (this.h > 0) {
         _snowman.writeByte(this.i);
         _snowman.writeByte(this.f);
         _snowman.writeByte(this.g);
         _snowman.a(this.j);
      }
   }

   public void a(om var1) {
      _snowman.a(this);
   }

   public int b() {
      return this.a;
   }

   public void a(cxx var1) {
      _snowman.f = this.b;
      _snowman.d = this.c;
      _snowman.h = this.d;
      _snowman.j.clear();

      for (int _snowman = 0; _snowman < this.e.length; _snowman++) {
         cxu _snowmanx = this.e[_snowman];
         _snowman.j.put("icon-" + _snowman, _snowmanx);
      }

      for (int _snowman = 0; _snowman < this.h; _snowman++) {
         for (int _snowmanx = 0; _snowmanx < this.i; _snowmanx++) {
            _snowman.g[this.f + _snowman + (this.g + _snowmanx) * 128] = this.j[_snowman + _snowmanx * this.h];
         }
      }
   }
}
