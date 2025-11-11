import it.unimi.dsi.fastutil.longs.LongSet;
import java.util.BitSet;
import java.util.Map;
import java.util.stream.Stream;
import javax.annotation.Nullable;

public class cgg extends cgp {
   private final cgh a;

   public cgg(cgh var1) {
      super(_snowman.g(), cgr.a);
      this.a = _snowman;
   }

   @Nullable
   @Override
   public ccj c(fx var1) {
      return this.a.c(_snowman);
   }

   @Nullable
   @Override
   public ceh d_(fx var1) {
      return this.a.d_(_snowman);
   }

   @Override
   public cux b(fx var1) {
      return this.a.b(_snowman);
   }

   @Override
   public int K() {
      return this.a.K();
   }

   @Nullable
   @Override
   public ceh a(fx var1, ceh var2, boolean var3) {
      return null;
   }

   @Override
   public void a(fx var1, ccj var2) {
   }

   @Override
   public void a(aqa var1) {
   }

   @Override
   public void a(cga var1) {
   }

   @Override
   public cgi[] d() {
      return this.a.d();
   }

   @Nullable
   @Override
   public cuo e() {
      return this.a.e();
   }

   @Override
   public void a(chn.a var1, long[] var2) {
   }

   private chn.a c(chn.a var1) {
      if (_snowman == chn.a.a) {
         return chn.a.b;
      } else {
         return _snowman == chn.a.c ? chn.a.d : _snowman;
      }
   }

   @Override
   public int a(chn.a var1, int var2, int var3) {
      return this.a.a(this.c(_snowman), _snowman, _snowman);
   }

   @Override
   public brd g() {
      return this.a.g();
   }

   @Override
   public void a(long var1) {
   }

   @Nullable
   @Override
   public crv<?> a(cla<?> var1) {
      return this.a.a(_snowman);
   }

   @Override
   public void a(cla<?> var1, crv<?> var2) {
   }

   @Override
   public Map<cla<?>, crv<?>> h() {
      return this.a.h();
   }

   @Override
   public void a(Map<cla<?>, crv<?>> var1) {
   }

   @Override
   public LongSet b(cla<?> var1) {
      return this.a.b(_snowman);
   }

   @Override
   public void a(cla<?> var1, long var2) {
   }

   @Override
   public Map<cla<?>, LongSet> v() {
      return this.a.v();
   }

   @Override
   public void b(Map<cla<?>, LongSet> var1) {
   }

   @Override
   public cfx i() {
      return this.a.i();
   }

   @Override
   public void a(boolean var1) {
   }

   @Override
   public boolean j() {
      return false;
   }

   @Override
   public cga k() {
      return this.a.k();
   }

   @Override
   public void d(fx var1) {
   }

   @Override
   public void e(fx var1) {
   }

   @Override
   public void a(md var1) {
   }

   @Nullable
   @Override
   public md i(fx var1) {
      return this.a.i(_snowman);
   }

   @Nullable
   @Override
   public md j(fx var1) {
      return this.a.j(_snowman);
   }

   @Override
   public void a(cfx var1) {
   }

   @Override
   public Stream<fx> m() {
      return this.a.m();
   }

   @Override
   public cgq<buo> s() {
      return new cgq<>(var0 -> var0.n().g(), this.g());
   }

   @Override
   public cgq<cuw> t() {
      return new cgq<>(var0 -> var0 == cuy.a, this.g());
   }

   @Override
   public BitSet a(chm.a var1) {
      throw (UnsupportedOperationException)x.c(new UnsupportedOperationException("Meaningless in this context"));
   }

   @Override
   public BitSet b(chm.a var1) {
      throw (UnsupportedOperationException)x.c(new UnsupportedOperationException("Meaningless in this context"));
   }

   public cgh u() {
      return this.a;
   }

   @Override
   public boolean r() {
      return this.a.r();
   }

   @Override
   public void b(boolean var1) {
      this.a.b(_snowman);
   }
}
