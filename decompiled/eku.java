import java.util.stream.Stream;

public abstract class eku extends ack<ekb.a> implements AutoCloseable {
   private final ekb a;
   private final String b;

   public eku(ekd var1, vk var2, String var3) {
      this.b = _snowman;
      this.a = new ekb(_snowman);
      _snowman.a(this.a.g(), this.a);
   }

   protected abstract Stream<vk> a();

   protected ekc a(vk var1) {
      return this.a.a(this.b(_snowman));
   }

   private vk b(vk var1) {
      return new vk(_snowman.b(), this.b + "/" + _snowman.a());
   }

   protected ekb.a a(ach var1, anw var2) {
      _snowman.a();
      _snowman.a("stitching");
      ekb.a _snowman = this.a.a(_snowman, this.a().map(this::b), _snowman, 0);
      _snowman.c();
      _snowman.b();
      return _snowman;
   }

   protected void a(ekb.a var1, ach var2, anw var3) {
      _snowman.a();
      _snowman.a("upload");
      this.a.a(_snowman);
      _snowman.c();
      _snowman.b();
   }

   @Override
   public void close() {
      this.a.f();
   }
}
