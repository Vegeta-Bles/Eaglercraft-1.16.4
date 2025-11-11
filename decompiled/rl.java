import java.io.IOException;
import javax.annotation.Nullable;

public class rl implements oj<om> {
   private rl.a a;
   private nr b;
   private int c;
   private int d;
   private int e;

   public rl() {
   }

   public rl(rl.a var1, nr var2) {
      this(_snowman, _snowman, -1, -1, -1);
   }

   public rl(int var1, int var2, int var3) {
      this(rl.a.d, null, _snowman, _snowman, _snowman);
   }

   public rl(rl.a var1, @Nullable nr var2, int var3, int var4, int var5) {
      this.a = _snowman;
      this.b = _snowman;
      this.c = _snowman;
      this.d = _snowman;
      this.e = _snowman;
   }

   @Override
   public void a(nf var1) throws IOException {
      this.a = _snowman.a(rl.a.class);
      if (this.a == rl.a.a || this.a == rl.a.b || this.a == rl.a.c) {
         this.b = _snowman.h();
      }

      if (this.a == rl.a.d) {
         this.c = _snowman.readInt();
         this.d = _snowman.readInt();
         this.e = _snowman.readInt();
      }
   }

   @Override
   public void b(nf var1) throws IOException {
      _snowman.a(this.a);
      if (this.a == rl.a.a || this.a == rl.a.b || this.a == rl.a.c) {
         _snowman.a(this.b);
      }

      if (this.a == rl.a.d) {
         _snowman.writeInt(this.c);
         _snowman.writeInt(this.d);
         _snowman.writeInt(this.e);
      }
   }

   public void a(om var1) {
      _snowman.a(this);
   }

   public rl.a b() {
      return this.a;
   }

   public nr c() {
      return this.b;
   }

   public int d() {
      return this.c;
   }

   public int e() {
      return this.d;
   }

   public int f() {
      return this.e;
   }

   public static enum a {
      a,
      b,
      c,
      d,
      e,
      f;

      private a() {
      }
   }
}
