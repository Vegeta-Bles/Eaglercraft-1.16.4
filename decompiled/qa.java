import java.io.IOException;
import javax.annotation.Nullable;

public class qa implements oj<om> {
   protected int a;
   protected short b;
   protected short c;
   protected short d;
   protected byte e;
   protected byte f;
   protected boolean g;
   protected boolean h;
   protected boolean i;

   public static long a(double var0) {
      return afm.d(_snowman * 4096.0);
   }

   public static double a(long var0) {
      return (double)_snowman / 4096.0;
   }

   public dcn a(dcn var1) {
      double _snowman = this.b == 0 ? _snowman.b : a(a(_snowman.b) + (long)this.b);
      double _snowmanx = this.c == 0 ? _snowman.c : a(a(_snowman.c) + (long)this.c);
      double _snowmanxx = this.d == 0 ? _snowman.d : a(a(_snowman.d) + (long)this.d);
      return new dcn(_snowman, _snowmanx, _snowmanxx);
   }

   public static dcn a(long var0, long var2, long var4) {
      return new dcn((double)_snowman, (double)_snowman, (double)_snowman).a(2.4414062E-4F);
   }

   public qa() {
   }

   public qa(int var1) {
      this.a = _snowman;
   }

   @Override
   public void a(nf var1) throws IOException {
      this.a = _snowman.i();
   }

   @Override
   public void b(nf var1) throws IOException {
      _snowman.d(this.a);
   }

   public void a(om var1) {
      _snowman.a(this);
   }

   @Override
   public String toString() {
      return "Entity_" + super.toString();
   }

   @Nullable
   public aqa a(brx var1) {
      return _snowman.a(this.a);
   }

   public byte e() {
      return this.e;
   }

   public byte f() {
      return this.f;
   }

   public boolean g() {
      return this.h;
   }

   public boolean h() {
      return this.i;
   }

   public boolean i() {
      return this.g;
   }

   public static class a extends qa {
      public a() {
         this.i = true;
      }

      public a(int var1, short var2, short var3, short var4, boolean var5) {
         super(_snowman);
         this.b = _snowman;
         this.c = _snowman;
         this.d = _snowman;
         this.g = _snowman;
         this.i = true;
      }

      @Override
      public void a(nf var1) throws IOException {
         super.a(_snowman);
         this.b = _snowman.readShort();
         this.c = _snowman.readShort();
         this.d = _snowman.readShort();
         this.g = _snowman.readBoolean();
      }

      @Override
      public void b(nf var1) throws IOException {
         super.b(_snowman);
         _snowman.writeShort(this.b);
         _snowman.writeShort(this.c);
         _snowman.writeShort(this.d);
         _snowman.writeBoolean(this.g);
      }
   }

   public static class b extends qa {
      public b() {
         this.h = true;
         this.i = true;
      }

      public b(int var1, short var2, short var3, short var4, byte var5, byte var6, boolean var7) {
         super(_snowman);
         this.b = _snowman;
         this.c = _snowman;
         this.d = _snowman;
         this.e = _snowman;
         this.f = _snowman;
         this.g = _snowman;
         this.h = true;
         this.i = true;
      }

      @Override
      public void a(nf var1) throws IOException {
         super.a(_snowman);
         this.b = _snowman.readShort();
         this.c = _snowman.readShort();
         this.d = _snowman.readShort();
         this.e = _snowman.readByte();
         this.f = _snowman.readByte();
         this.g = _snowman.readBoolean();
      }

      @Override
      public void b(nf var1) throws IOException {
         super.b(_snowman);
         _snowman.writeShort(this.b);
         _snowman.writeShort(this.c);
         _snowman.writeShort(this.d);
         _snowman.writeByte(this.e);
         _snowman.writeByte(this.f);
         _snowman.writeBoolean(this.g);
      }
   }

   public static class c extends qa {
      public c() {
         this.h = true;
      }

      public c(int var1, byte var2, byte var3, boolean var4) {
         super(_snowman);
         this.e = _snowman;
         this.f = _snowman;
         this.h = true;
         this.g = _snowman;
      }

      @Override
      public void a(nf var1) throws IOException {
         super.a(_snowman);
         this.e = _snowman.readByte();
         this.f = _snowman.readByte();
         this.g = _snowman.readBoolean();
      }

      @Override
      public void b(nf var1) throws IOException {
         super.b(_snowman);
         _snowman.writeByte(this.e);
         _snowman.writeByte(this.f);
         _snowman.writeBoolean(this.g);
      }
   }
}
