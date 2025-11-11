import com.google.common.collect.ImmutableMap;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import java.util.Random;
import javax.annotation.Nullable;

public final class cux extends cej<cuw, cux> {
   public static final Codec<cux> a = a(gm.O, cuw::h).stable();

   public cux(cuw var1, ImmutableMap<cfj<?>, Comparable<?>> var2, MapCodec<cux> var3) {
      super(_snowman, _snowman, _snowman);
   }

   public cuw a() {
      return this.c;
   }

   public boolean b() {
      return this.a().c(this);
   }

   public boolean c() {
      return this.a().b();
   }

   public float a(brc var1, fx var2) {
      return this.a().a(this, _snowman, _snowman);
   }

   public float d() {
      return this.a().a(this);
   }

   public int e() {
      return this.a().d(this);
   }

   public boolean b(brc var1, fx var2) {
      for (int _snowman = -1; _snowman <= 1; _snowman++) {
         for (int _snowmanx = -1; _snowmanx <= 1; _snowmanx++) {
            fx _snowmanxx = _snowman.b(_snowman, 0, _snowmanx);
            cux _snowmanxxx = _snowman.b(_snowmanxx);
            if (!_snowmanxxx.a().a(this.a()) && !_snowman.d_(_snowmanxx).i(_snowman, _snowmanxx)) {
               return true;
            }
         }
      }

      return false;
   }

   public void a(brx var1, fx var2) {
      this.a().a(_snowman, _snowman, this);
   }

   public void a(brx var1, fx var2, Random var3) {
      this.a().a(_snowman, _snowman, this, _snowman);
   }

   public boolean f() {
      return this.a().j();
   }

   public void b(brx var1, fx var2, Random var3) {
      this.a().b(_snowman, _snowman, this, _snowman);
   }

   public dcn c(brc var1, fx var2) {
      return this.a().a(_snowman, _snowman, this);
   }

   public ceh g() {
      return this.a().b(this);
   }

   @Nullable
   public hf h() {
      return this.a().i();
   }

   public boolean a(ael<cuw> var1) {
      return this.a().a(_snowman);
   }

   public float i() {
      return this.a().c();
   }

   public boolean a(brc var1, fx var2, cuw var3, gc var4) {
      return this.a().a(this, _snowman, _snowman, _snowman, _snowman);
   }

   public ddh d(brc var1, fx var2) {
      return this.a().b(this, _snowman, _snowman);
   }
}
