import java.io.IOException;

public class rv implements oj<om> {
   private int a;
   private byte b;
   private byte c;
   private int d;
   private byte e;

   public rv() {
   }

   public rv(int var1, apu var2) {
      this.a = _snowman;
      this.b = (byte)(aps.a(_snowman.a()) & 0xFF);
      this.c = (byte)(_snowman.c() & 0xFF);
      if (_snowman.b() > 32767) {
         this.d = 32767;
      } else {
         this.d = _snowman.b();
      }

      this.e = 0;
      if (_snowman.d()) {
         this.e = (byte)(this.e | 1);
      }

      if (_snowman.e()) {
         this.e = (byte)(this.e | 2);
      }

      if (_snowman.f()) {
         this.e = (byte)(this.e | 4);
      }
   }

   @Override
   public void a(nf var1) throws IOException {
      this.a = _snowman.i();
      this.b = _snowman.readByte();
      this.c = _snowman.readByte();
      this.d = _snowman.i();
      this.e = _snowman.readByte();
   }

   @Override
   public void b(nf var1) throws IOException {
      _snowman.d(this.a);
      _snowman.writeByte(this.b);
      _snowman.writeByte(this.c);
      _snowman.d(this.d);
      _snowman.writeByte(this.e);
   }

   public boolean b() {
      return this.d == 32767;
   }

   public void a(om var1) {
      _snowman.a(this);
   }

   public int c() {
      return this.a;
   }

   public byte d() {
      return this.b;
   }

   public byte e() {
      return this.c;
   }

   public int f() {
      return this.d;
   }

   public boolean g() {
      return (this.e & 2) == 2;
   }

   public boolean h() {
      return (this.e & 1) == 1;
   }

   public boolean i() {
      return (this.e & 4) == 4;
   }
}
