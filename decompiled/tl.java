import java.io.IOException;
import javax.annotation.Nullable;

public class tl implements oj<sa> {
   private int a;
   private String b;
   private boolean c;

   public tl() {
   }

   public tl(int var1, String var2, boolean var3) {
      this.a = _snowman;
      this.b = _snowman;
      this.c = _snowman;
   }

   @Override
   public void a(nf var1) throws IOException {
      this.a = _snowman.i();
      this.b = _snowman.e(32767);
      this.c = _snowman.readBoolean();
   }

   @Override
   public void b(nf var1) throws IOException {
      _snowman.d(this.a);
      _snowman.a(this.b);
      _snowman.writeBoolean(this.c);
   }

   public void a(sa var1) {
      _snowman.a(this);
   }

   @Nullable
   public bqy a(brx var1) {
      aqa _snowman = _snowman.a(this.a);
      return _snowman instanceof bhr ? ((bhr)_snowman).u() : null;
   }

   public String b() {
      return this.b;
   }

   public boolean c() {
      return this.c;
   }
}
