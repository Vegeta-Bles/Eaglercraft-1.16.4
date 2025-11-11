import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

public class vz implements AutoCloseable {
   private static final CompletableFuture<afx> a = CompletableFuture.completedFuture(afx.a);
   private final acf b = new acm(abk.b);
   private final dc c;
   private final bor d = new bor();
   private final aep e = new aep();
   private final cza f = new cza();
   private final cyz g = new cyz(this.f);
   private final vv h = new vv(this.f);
   private final vw i;

   public vz(dc.a var1, int var2) {
      this.c = new dc(_snowman);
      this.i = new vw(_snowman, this.c.a());
      this.b.a(this.e);
      this.b.a(this.f);
      this.b.a(this.d);
      this.b.a(this.g);
      this.b.a(this.i);
      this.b.a(this.h);
   }

   public vw a() {
      return this.i;
   }

   public cza b() {
      return this.f;
   }

   public cyz c() {
      return this.g;
   }

   public aen d() {
      return this.e.a();
   }

   public bor e() {
      return this.d;
   }

   public dc f() {
      return this.c;
   }

   public vv g() {
      return this.h;
   }

   public ach h() {
      return this.b;
   }

   public static CompletableFuture<vz> a(List<abj> var0, dc.a var1, int var2, Executor var3, Executor var4) {
      vz _snowman = new vz(_snowman, _snowman);
      CompletableFuture<afx> _snowmanx = _snowman.b.a(_snowman, _snowman, _snowman, a);
      return _snowmanx.whenComplete((var1x, var2x) -> {
         if (var2x != null) {
            _snowman.close();
         }
      }).thenApply(var1x -> _snowman);
   }

   public void i() {
      this.e.a().e();
   }

   @Override
   public void close() {
      this.b.close();
   }
}
