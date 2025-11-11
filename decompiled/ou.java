import java.io.IOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ou implements oj<om> {
   private static final Logger b = LogManager.getLogger();
   private fx c;
   private ceh d;
   sz.a a;
   private boolean e;

   public ou() {
   }

   public ou(fx var1, ceh var2, sz.a var3, boolean var4, String var5) {
      this.c = _snowman.h();
      this.d = _snowman;
      this.a = _snowman;
      this.e = _snowman;
   }

   @Override
   public void a(nf var1) throws IOException {
      this.c = _snowman.e();
      this.d = buo.m.a(_snowman.i());
      this.a = _snowman.a(sz.a.class);
      this.e = _snowman.readBoolean();
   }

   @Override
   public void b(nf var1) throws IOException {
      _snowman.a(this.c);
      _snowman.d(buo.i(this.d));
      _snowman.a(this.a);
      _snowman.writeBoolean(this.e);
   }

   public void a(om var1) {
      _snowman.a(this);
   }

   public ceh b() {
      return this.d;
   }

   public fx c() {
      return this.c;
   }

   public boolean d() {
      return this.e;
   }

   public sz.a e() {
      return this.a;
   }
}
