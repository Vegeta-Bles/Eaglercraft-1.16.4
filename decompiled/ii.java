import com.google.common.collect.ImmutableList;
import com.google.gson.JsonElement;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import javax.annotation.Nullable;

public class ii {
   private final Consumer<il> a;
   private final BiConsumer<vk, Supplier<JsonElement>> b;
   private final Consumer<blx> c;

   public ii(Consumer<il> var1, BiConsumer<vk, Supplier<JsonElement>> var2, Consumer<blx> var3) {
      this.a = _snowman;
      this.b = _snowman;
      this.c = _snowman;
   }

   private void a(buo var1) {
      this.c.accept(_snowman.h());
   }

   private void c(buo var1, vk var2) {
      this.b.accept(iw.a(_snowman.h()), new iv(_snowman));
   }

   private void a(blx var1, vk var2) {
      this.b.accept(iw.a(_snowman), new iv(_snowman));
   }

   private void a(blx var1) {
      iy.aK.a(iw.a(_snowman), iz.b(_snowman), this.b);
   }

   private void b(buo var1) {
      blx _snowman = _snowman.h();
      if (_snowman != bmd.a) {
         iy.aK.a(iw.a(_snowman), iz.B(_snowman), this.b);
      }
   }

   private void a(buo var1, String var2) {
      blx _snowman = _snowman.h();
      iy.aK.a(iw.a(_snowman), iz.j(iz.a(_snowman, _snowman)), this.b);
   }

   private static ip b() {
      return ip.a(cex.O).a(gc.f, ir.a().a(is.b, is.a.b)).a(gc.d, ir.a().a(is.b, is.a.c)).a(gc.e, ir.a().a(is.b, is.a.d)).a(gc.c, ir.a());
   }

   private static ip c() {
      return ip.a(cex.O).a(gc.d, ir.a()).a(gc.e, ir.a().a(is.b, is.a.b)).a(gc.c, ir.a().a(is.b, is.a.c)).a(gc.f, ir.a().a(is.b, is.a.d));
   }

   private static ip d() {
      return ip.a(cex.O).a(gc.f, ir.a()).a(gc.d, ir.a().a(is.b, is.a.b)).a(gc.e, ir.a().a(is.b, is.a.c)).a(gc.c, ir.a().a(is.b, is.a.d));
   }

   private static ip e() {
      return ip.a(cex.M)
         .a(gc.a, ir.a().a(is.a, is.a.b))
         .a(gc.b, ir.a().a(is.a, is.a.d))
         .a(gc.c, ir.a())
         .a(gc.d, ir.a().a(is.b, is.a.c))
         .a(gc.e, ir.a().a(is.b, is.a.d))
         .a(gc.f, ir.a().a(is.b, is.a.b));
   }

   private static io d(buo var0, vk var1) {
      return io.a(_snowman, a(_snowman));
   }

   private static ir[] a(vk var0) {
      return new ir[]{ir.a().a(is.c, _snowman), ir.a().a(is.c, _snowman).a(is.b, is.a.b), ir.a().a(is.c, _snowman).a(is.b, is.a.c), ir.a().a(is.c, _snowman).a(is.b, is.a.d)};
   }

   private static io e(buo var0, vk var1, vk var2) {
      return io.a(_snowman, ir.a().a(is.c, _snowman), ir.a().a(is.c, _snowman), ir.a().a(is.c, _snowman).a(is.b, is.a.c), ir.a().a(is.c, _snowman).a(is.b, is.a.c));
   }

   private static ip a(cey var0, vk var1, vk var2) {
      return ip.a(_snowman).a(true, ir.a().a(is.c, _snowman)).a(false, ir.a().a(is.c, _snowman));
   }

   private void c(buo var1) {
      vk _snowman = jb.a.a(_snowman, this.b);
      vk _snowmanx = jb.b.a(_snowman, this.b);
      this.a.accept(e(_snowman, _snowman, _snowmanx));
   }

   private void d(buo var1) {
      vk _snowman = jb.a.a(_snowman, this.b);
      this.a.accept(d(_snowman, _snowman));
   }

   private static il f(buo var0, vk var1, vk var2) {
      return io.a(_snowman)
         .a(ip.a(cex.w).a(false, ir.a().a(is.c, _snowman)).a(true, ir.a().a(is.c, _snowman)))
         .a(
            ip.a(cex.Q, cex.O)
               .a(cet.a, gc.f, ir.a().a(is.b, is.a.b))
               .a(cet.a, gc.e, ir.a().a(is.b, is.a.d))
               .a(cet.a, gc.d, ir.a().a(is.b, is.a.c))
               .a(cet.a, gc.c, ir.a())
               .a(cet.b, gc.f, ir.a().a(is.b, is.a.b).a(is.a, is.a.b).a(is.d, true))
               .a(cet.b, gc.e, ir.a().a(is.b, is.a.d).a(is.a, is.a.b).a(is.d, true))
               .a(cet.b, gc.d, ir.a().a(is.b, is.a.c).a(is.a, is.a.b).a(is.d, true))
               .a(cet.b, gc.c, ir.a().a(is.a, is.a.b).a(is.d, true))
               .a(cet.c, gc.f, ir.a().a(is.b, is.a.d).a(is.a, is.a.c))
               .a(cet.c, gc.e, ir.a().a(is.b, is.a.b).a(is.a, is.a.c))
               .a(cet.c, gc.d, ir.a().a(is.a, is.a.c))
               .a(cet.c, gc.c, ir.a().a(is.b, is.a.c).a(is.a, is.a.c))
         );
   }

   private static ip.d<gc, cfd, cfc, Boolean> a(ip.d<gc, cfd, cfc, Boolean> var0, cfd var1, vk var2, vk var3) {
      return _snowman.a(gc.f, _snowman, cfc.a, false, ir.a().a(is.c, _snowman))
         .a(gc.d, _snowman, cfc.a, false, ir.a().a(is.c, _snowman).a(is.b, is.a.b))
         .a(gc.e, _snowman, cfc.a, false, ir.a().a(is.c, _snowman).a(is.b, is.a.c))
         .a(gc.c, _snowman, cfc.a, false, ir.a().a(is.c, _snowman).a(is.b, is.a.d))
         .a(gc.f, _snowman, cfc.b, false, ir.a().a(is.c, _snowman))
         .a(gc.d, _snowman, cfc.b, false, ir.a().a(is.c, _snowman).a(is.b, is.a.b))
         .a(gc.e, _snowman, cfc.b, false, ir.a().a(is.c, _snowman).a(is.b, is.a.c))
         .a(gc.c, _snowman, cfc.b, false, ir.a().a(is.c, _snowman).a(is.b, is.a.d))
         .a(gc.f, _snowman, cfc.a, true, ir.a().a(is.c, _snowman).a(is.b, is.a.b))
         .a(gc.d, _snowman, cfc.a, true, ir.a().a(is.c, _snowman).a(is.b, is.a.c))
         .a(gc.e, _snowman, cfc.a, true, ir.a().a(is.c, _snowman).a(is.b, is.a.d))
         .a(gc.c, _snowman, cfc.a, true, ir.a().a(is.c, _snowman))
         .a(gc.f, _snowman, cfc.b, true, ir.a().a(is.c, _snowman).a(is.b, is.a.d))
         .a(gc.d, _snowman, cfc.b, true, ir.a().a(is.c, _snowman))
         .a(gc.e, _snowman, cfc.b, true, ir.a().a(is.c, _snowman).a(is.b, is.a.b))
         .a(gc.c, _snowman, cfc.b, true, ir.a().a(is.c, _snowman).a(is.b, is.a.c));
   }

   private static il b(buo var0, vk var1, vk var2, vk var3, vk var4) {
      return io.a(_snowman).a(a(a(ip.a(cex.O, cex.aa, cex.aH, cex.u), cfd.b, _snowman, _snowman), cfd.a, _snowman, _snowman));
   }

   private static il g(buo var0, vk var1, vk var2) {
      return in.a(_snowman)
         .a(ir.a().a(is.c, _snowman))
         .a(im.a().a(cex.I, true), ir.a().a(is.c, _snowman).a(is.d, true))
         .a(im.a().a(cex.J, true), ir.a().a(is.c, _snowman).a(is.b, is.a.b).a(is.d, true))
         .a(im.a().a(cex.K, true), ir.a().a(is.c, _snowman).a(is.b, is.a.c).a(is.d, true))
         .a(im.a().a(cex.L, true), ir.a().a(is.c, _snowman).a(is.b, is.a.d).a(is.d, true));
   }

   private static il d(buo var0, vk var1, vk var2, vk var3) {
      return in.a(_snowman)
         .a(im.a().a(cex.G, true), ir.a().a(is.c, _snowman))
         .a(im.a().a(cex.T, cfp.b), ir.a().a(is.c, _snowman).a(is.d, true))
         .a(im.a().a(cex.S, cfp.b), ir.a().a(is.c, _snowman).a(is.b, is.a.b).a(is.d, true))
         .a(im.a().a(cex.U, cfp.b), ir.a().a(is.c, _snowman).a(is.b, is.a.c).a(is.d, true))
         .a(im.a().a(cex.V, cfp.b), ir.a().a(is.c, _snowman).a(is.b, is.a.d).a(is.d, true))
         .a(im.a().a(cex.T, cfp.c), ir.a().a(is.c, _snowman).a(is.d, true))
         .a(im.a().a(cex.S, cfp.c), ir.a().a(is.c, _snowman).a(is.b, is.a.b).a(is.d, true))
         .a(im.a().a(cex.U, cfp.c), ir.a().a(is.c, _snowman).a(is.b, is.a.c).a(is.d, true))
         .a(im.a().a(cex.V, cfp.c), ir.a().a(is.c, _snowman).a(is.b, is.a.d).a(is.d, true));
   }

   private static il c(buo var0, vk var1, vk var2, vk var3, vk var4) {
      return io.a(_snowman, ir.a().a(is.d, true))
         .a(c())
         .a(
            ip.a(cex.q, cex.u)
               .a(false, false, ir.a().a(is.c, _snowman))
               .a(true, false, ir.a().a(is.c, _snowman))
               .a(false, true, ir.a().a(is.c, _snowman))
               .a(true, true, ir.a().a(is.c, _snowman))
         );
   }

   private static il e(buo var0, vk var1, vk var2, vk var3) {
      return io.a(_snowman)
         .a(
            ip.a(cex.O, cex.ab, cex.aL)
               .a(gc.f, cff.b, cfn.a, ir.a().a(is.c, _snowman))
               .a(gc.e, cff.b, cfn.a, ir.a().a(is.c, _snowman).a(is.b, is.a.c).a(is.d, true))
               .a(gc.d, cff.b, cfn.a, ir.a().a(is.c, _snowman).a(is.b, is.a.b).a(is.d, true))
               .a(gc.c, cff.b, cfn.a, ir.a().a(is.c, _snowman).a(is.b, is.a.d).a(is.d, true))
               .a(gc.f, cff.b, cfn.e, ir.a().a(is.c, _snowman))
               .a(gc.e, cff.b, cfn.e, ir.a().a(is.c, _snowman).a(is.b, is.a.c).a(is.d, true))
               .a(gc.d, cff.b, cfn.e, ir.a().a(is.c, _snowman).a(is.b, is.a.b).a(is.d, true))
               .a(gc.c, cff.b, cfn.e, ir.a().a(is.c, _snowman).a(is.b, is.a.d).a(is.d, true))
               .a(gc.f, cff.b, cfn.d, ir.a().a(is.c, _snowman).a(is.b, is.a.d).a(is.d, true))
               .a(gc.e, cff.b, cfn.d, ir.a().a(is.c, _snowman).a(is.b, is.a.b).a(is.d, true))
               .a(gc.d, cff.b, cfn.d, ir.a().a(is.c, _snowman))
               .a(gc.c, cff.b, cfn.d, ir.a().a(is.c, _snowman).a(is.b, is.a.c).a(is.d, true))
               .a(gc.f, cff.b, cfn.c, ir.a().a(is.c, _snowman))
               .a(gc.e, cff.b, cfn.c, ir.a().a(is.c, _snowman).a(is.b, is.a.c).a(is.d, true))
               .a(gc.d, cff.b, cfn.c, ir.a().a(is.c, _snowman).a(is.b, is.a.b).a(is.d, true))
               .a(gc.c, cff.b, cfn.c, ir.a().a(is.c, _snowman).a(is.b, is.a.d).a(is.d, true))
               .a(gc.f, cff.b, cfn.b, ir.a().a(is.c, _snowman).a(is.b, is.a.d).a(is.d, true))
               .a(gc.e, cff.b, cfn.b, ir.a().a(is.c, _snowman).a(is.b, is.a.b).a(is.d, true))
               .a(gc.d, cff.b, cfn.b, ir.a().a(is.c, _snowman))
               .a(gc.c, cff.b, cfn.b, ir.a().a(is.c, _snowman).a(is.b, is.a.c).a(is.d, true))
               .a(gc.f, cff.a, cfn.a, ir.a().a(is.c, _snowman).a(is.a, is.a.c).a(is.d, true))
               .a(gc.e, cff.a, cfn.a, ir.a().a(is.c, _snowman).a(is.a, is.a.c).a(is.b, is.a.c).a(is.d, true))
               .a(gc.d, cff.a, cfn.a, ir.a().a(is.c, _snowman).a(is.a, is.a.c).a(is.b, is.a.b).a(is.d, true))
               .a(gc.c, cff.a, cfn.a, ir.a().a(is.c, _snowman).a(is.a, is.a.c).a(is.b, is.a.d).a(is.d, true))
               .a(gc.f, cff.a, cfn.e, ir.a().a(is.c, _snowman).a(is.a, is.a.c).a(is.b, is.a.b).a(is.d, true))
               .a(gc.e, cff.a, cfn.e, ir.a().a(is.c, _snowman).a(is.a, is.a.c).a(is.b, is.a.d).a(is.d, true))
               .a(gc.d, cff.a, cfn.e, ir.a().a(is.c, _snowman).a(is.a, is.a.c).a(is.b, is.a.c).a(is.d, true))
               .a(gc.c, cff.a, cfn.e, ir.a().a(is.c, _snowman).a(is.a, is.a.c).a(is.d, true))
               .a(gc.f, cff.a, cfn.d, ir.a().a(is.c, _snowman).a(is.a, is.a.c).a(is.d, true))
               .a(gc.e, cff.a, cfn.d, ir.a().a(is.c, _snowman).a(is.a, is.a.c).a(is.b, is.a.c).a(is.d, true))
               .a(gc.d, cff.a, cfn.d, ir.a().a(is.c, _snowman).a(is.a, is.a.c).a(is.b, is.a.b).a(is.d, true))
               .a(gc.c, cff.a, cfn.d, ir.a().a(is.c, _snowman).a(is.a, is.a.c).a(is.b, is.a.d).a(is.d, true))
               .a(gc.f, cff.a, cfn.c, ir.a().a(is.c, _snowman).a(is.a, is.a.c).a(is.b, is.a.b).a(is.d, true))
               .a(gc.e, cff.a, cfn.c, ir.a().a(is.c, _snowman).a(is.a, is.a.c).a(is.b, is.a.d).a(is.d, true))
               .a(gc.d, cff.a, cfn.c, ir.a().a(is.c, _snowman).a(is.a, is.a.c).a(is.b, is.a.c).a(is.d, true))
               .a(gc.c, cff.a, cfn.c, ir.a().a(is.c, _snowman).a(is.a, is.a.c).a(is.d, true))
               .a(gc.f, cff.a, cfn.b, ir.a().a(is.c, _snowman).a(is.a, is.a.c).a(is.d, true))
               .a(gc.e, cff.a, cfn.b, ir.a().a(is.c, _snowman).a(is.a, is.a.c).a(is.b, is.a.c).a(is.d, true))
               .a(gc.d, cff.a, cfn.b, ir.a().a(is.c, _snowman).a(is.a, is.a.c).a(is.b, is.a.b).a(is.d, true))
               .a(gc.c, cff.a, cfn.b, ir.a().a(is.c, _snowman).a(is.a, is.a.c).a(is.b, is.a.d).a(is.d, true))
         );
   }

   private static il f(buo var0, vk var1, vk var2, vk var3) {
      return io.a(_snowman)
         .a(
            ip.a(cex.O, cex.ab, cex.u)
               .a(gc.c, cff.b, false, ir.a().a(is.c, _snowman))
               .a(gc.d, cff.b, false, ir.a().a(is.c, _snowman).a(is.b, is.a.c))
               .a(gc.f, cff.b, false, ir.a().a(is.c, _snowman).a(is.b, is.a.b))
               .a(gc.e, cff.b, false, ir.a().a(is.c, _snowman).a(is.b, is.a.d))
               .a(gc.c, cff.a, false, ir.a().a(is.c, _snowman))
               .a(gc.d, cff.a, false, ir.a().a(is.c, _snowman).a(is.b, is.a.c))
               .a(gc.f, cff.a, false, ir.a().a(is.c, _snowman).a(is.b, is.a.b))
               .a(gc.e, cff.a, false, ir.a().a(is.c, _snowman).a(is.b, is.a.d))
               .a(gc.c, cff.b, true, ir.a().a(is.c, _snowman))
               .a(gc.d, cff.b, true, ir.a().a(is.c, _snowman).a(is.b, is.a.c))
               .a(gc.f, cff.b, true, ir.a().a(is.c, _snowman).a(is.b, is.a.b))
               .a(gc.e, cff.b, true, ir.a().a(is.c, _snowman).a(is.b, is.a.d))
               .a(gc.c, cff.a, true, ir.a().a(is.c, _snowman).a(is.a, is.a.c).a(is.b, is.a.c))
               .a(gc.d, cff.a, true, ir.a().a(is.c, _snowman).a(is.a, is.a.c).a(is.b, is.a.a))
               .a(gc.f, cff.a, true, ir.a().a(is.c, _snowman).a(is.a, is.a.c).a(is.b, is.a.d))
               .a(gc.e, cff.a, true, ir.a().a(is.c, _snowman).a(is.a, is.a.c).a(is.b, is.a.b))
         );
   }

   private static il g(buo var0, vk var1, vk var2, vk var3) {
      return io.a(_snowman)
         .a(
            ip.a(cex.O, cex.ab, cex.u)
               .a(gc.c, cff.b, false, ir.a().a(is.c, _snowman))
               .a(gc.d, cff.b, false, ir.a().a(is.c, _snowman))
               .a(gc.f, cff.b, false, ir.a().a(is.c, _snowman))
               .a(gc.e, cff.b, false, ir.a().a(is.c, _snowman))
               .a(gc.c, cff.a, false, ir.a().a(is.c, _snowman))
               .a(gc.d, cff.a, false, ir.a().a(is.c, _snowman))
               .a(gc.f, cff.a, false, ir.a().a(is.c, _snowman))
               .a(gc.e, cff.a, false, ir.a().a(is.c, _snowman))
               .a(gc.c, cff.b, true, ir.a().a(is.c, _snowman))
               .a(gc.d, cff.b, true, ir.a().a(is.c, _snowman).a(is.b, is.a.c))
               .a(gc.f, cff.b, true, ir.a().a(is.c, _snowman).a(is.b, is.a.b))
               .a(gc.e, cff.b, true, ir.a().a(is.c, _snowman).a(is.b, is.a.d))
               .a(gc.c, cff.a, true, ir.a().a(is.c, _snowman))
               .a(gc.d, cff.a, true, ir.a().a(is.c, _snowman).a(is.b, is.a.c))
               .a(gc.f, cff.a, true, ir.a().a(is.c, _snowman).a(is.b, is.a.b))
               .a(gc.e, cff.a, true, ir.a().a(is.c, _snowman).a(is.b, is.a.d))
         );
   }

   private static io e(buo var0, vk var1) {
      return io.a(_snowman, ir.a().a(is.c, _snowman));
   }

   private static ip f() {
      return ip.a(cex.F).a(gc.a.b, ir.a()).a(gc.a.c, ir.a().a(is.a, is.a.b)).a(gc.a.a, ir.a().a(is.a, is.a.b).a(is.b, is.a.b));
   }

   private static il f(buo var0, vk var1) {
      return io.a(_snowman, ir.a().a(is.c, _snowman)).a(f());
   }

   private void g(buo var1, vk var2) {
      this.a.accept(f(_snowman, _snowman));
   }

   private void a(buo var1, jb.a var2) {
      vk _snowman = _snowman.a(_snowman, this.b);
      this.a.accept(f(_snowman, _snowman));
   }

   private void b(buo var1, jb.a var2) {
      vk _snowman = _snowman.a(_snowman, this.b);
      this.a.accept(io.a(_snowman, ir.a().a(is.c, _snowman)).a(b()));
   }

   private static il h(buo var0, vk var1, vk var2) {
      return io.a(_snowman)
         .a(ip.a(cex.F).a(gc.a.b, ir.a().a(is.c, _snowman)).a(gc.a.c, ir.a().a(is.c, _snowman).a(is.a, is.a.b)).a(gc.a.a, ir.a().a(is.c, _snowman).a(is.a, is.a.b).a(is.b, is.a.b)));
   }

   private void a(buo var1, jb.a var2, jb.a var3) {
      vk _snowman = _snowman.a(_snowman, this.b);
      vk _snowmanx = _snowman.a(_snowman, this.b);
      this.a.accept(h(_snowman, _snowman, _snowmanx));
   }

   private vk a(buo var1, String var2, ix var3, Function<vk, iz> var4) {
      return _snowman.a(_snowman, _snowman, _snowman.apply(iz.a(_snowman, _snowman)), this.b);
   }

   private static il i(buo var0, vk var1, vk var2) {
      return io.a(_snowman).a(a(cex.w, _snowman, _snowman));
   }

   private static il h(buo var0, vk var1, vk var2, vk var3) {
      return io.a(_snowman).a(ip.a(cex.aK).a(cfm.b, ir.a().a(is.c, _snowman)).a(cfm.a, ir.a().a(is.c, _snowman)).a(cfm.c, ir.a().a(is.c, _snowman)));
   }

   private void e(buo var1) {
      this.c(_snowman, jb.a);
   }

   private void c(buo var1, jb.a var2) {
      this.a.accept(e(_snowman, _snowman.a(_snowman, this.b)));
   }

   private void a(buo var1, iz var2, ix var3) {
      vk _snowman = _snowman.a(_snowman, _snowman, this.b);
      this.a.accept(e(_snowman, _snowman));
   }

   private ii.b a(buo var1, jb var2) {
      return new ii.b(_snowman.b()).a(_snowman, _snowman.a());
   }

   private ii.b d(buo var1, jb.a var2) {
      jb _snowman = _snowman.get(_snowman);
      return new ii.b(_snowman.b()).a(_snowman, _snowman.a());
   }

   private ii.b f(buo var1) {
      return this.d(_snowman, jb.a);
   }

   private ii.b a(iz var1) {
      return new ii.b(_snowman);
   }

   private void g(buo var1) {
      iz _snowman = iz.p(_snowman);
      vk _snowmanx = iy.o.a(_snowman, _snowman, this.b);
      vk _snowmanxx = iy.p.a(_snowman, _snowman, this.b);
      vk _snowmanxxx = iy.q.a(_snowman, _snowman, this.b);
      vk _snowmanxxxx = iy.r.a(_snowman, _snowman, this.b);
      this.a(_snowman.h());
      this.a.accept(b(_snowman, _snowmanx, _snowmanxx, _snowmanxxx, _snowmanxxxx));
   }

   private void h(buo var1) {
      iz _snowman = iz.b(_snowman);
      vk _snowmanx = iy.P.a(_snowman, _snowman, this.b);
      vk _snowmanxx = iy.Q.a(_snowman, _snowman, this.b);
      vk _snowmanxxx = iy.R.a(_snowman, _snowman, this.b);
      this.a.accept(f(_snowman, _snowmanx, _snowmanxx, _snowmanxxx));
      this.c(_snowman, _snowmanxx);
   }

   private void i(buo var1) {
      iz _snowman = iz.b(_snowman);
      vk _snowmanx = iy.M.a(_snowman, _snowman, this.b);
      vk _snowmanxx = iy.N.a(_snowman, _snowman, this.b);
      vk _snowmanxxx = iy.O.a(_snowman, _snowman, this.b);
      this.a.accept(g(_snowman, _snowmanx, _snowmanxx, _snowmanxxx));
      this.c(_snowman, _snowmanxx);
   }

   private ii.d j(buo var1) {
      return new ii.d(iz.l(_snowman));
   }

   private void k(buo var1) {
      this.a(_snowman, _snowman);
   }

   private void a(buo var1, buo var2) {
      this.a.accept(e(_snowman, iw.a(_snowman)));
   }

   private void a(buo var1, ii.c var2) {
      this.b(_snowman);
      this.b(_snowman, _snowman);
   }

   private void a(buo var1, ii.c var2, iz var3) {
      this.b(_snowman);
      this.b(_snowman, _snowman, _snowman);
   }

   private void b(buo var1, ii.c var2) {
      iz _snowman = iz.c(_snowman);
      this.b(_snowman, _snowman, _snowman);
   }

   private void b(buo var1, ii.c var2, iz var3) {
      vk _snowman = _snowman.a().a(_snowman, _snowman, this.b);
      this.a.accept(e(_snowman, _snowman));
   }

   private void a(buo var1, buo var2, ii.c var3) {
      this.a(_snowman, _snowman);
      iz _snowman = iz.d(_snowman);
      vk _snowmanx = _snowman.b().a(_snowman, _snowman, this.b);
      this.a.accept(e(_snowman, _snowmanx));
   }

   private void b(buo var1, buo var2) {
      jb _snowman = jb.k.get(_snowman);
      vk _snowmanx = _snowman.a(_snowman, this.b);
      this.a.accept(e(_snowman, _snowmanx));
      vk _snowmanxx = iy.ac.a(_snowman, _snowman.b(), this.b);
      this.a.accept(io.a(_snowman, ir.a().a(is.c, _snowmanxx)).a(b()));
      this.b(_snowman);
   }

   private void c(buo var1, buo var2) {
      this.a(_snowman.h());
      iz _snowman = iz.g(_snowman);
      iz _snowmanx = iz.a(_snowman, _snowman);
      vk _snowmanxx = iy.ao.a(_snowman, _snowmanx, this.b);
      this.a
         .accept(
            io.a(_snowman, ir.a().a(is.c, _snowmanxx))
               .a(ip.a(cex.O).a(gc.e, ir.a()).a(gc.d, ir.a().a(is.b, is.a.d)).a(gc.c, ir.a().a(is.b, is.a.b)).a(gc.f, ir.a().a(is.b, is.a.c)))
         );
      this.a.accept(io.a(_snowman).a(ip.a(cex.ai).a(var3x -> ir.a().a(is.c, iy.an[var3x].a(_snowman, _snowman, this.b)))));
   }

   private void a(buo var1, buo var2, buo var3, buo var4, buo var5, buo var6, buo var7, buo var8) {
      this.a(_snowman, ii.c.b);
      this.a(_snowman, ii.c.b);
      this.e(_snowman);
      this.e(_snowman);
      this.b(_snowman, _snowman);
      this.b(_snowman, _snowman);
   }

   private void c(buo var1, ii.c var2) {
      this.a(_snowman, "_top");
      vk _snowman = this.a(_snowman, "_top", _snowman.a(), iz::c);
      vk _snowmanx = this.a(_snowman, "_bottom", _snowman.a(), iz::c);
      this.j(_snowman, _snowman, _snowmanx);
   }

   private void g() {
      this.a(bup.gU, "_front");
      vk _snowman = iw.a(bup.gU, "_top");
      vk _snowmanx = this.a(bup.gU, "_bottom", ii.c.b.a(), iz::c);
      this.j(bup.gU, _snowman, _snowmanx);
   }

   private void h() {
      vk _snowman = this.a(bup.aV, "_top", iy.aE, iz::a);
      vk _snowmanx = this.a(bup.aV, "_bottom", iy.aE, iz::a);
      this.j(bup.aV, _snowman, _snowmanx);
   }

   private void j(buo var1, vk var2, vk var3) {
      this.a.accept(io.a(_snowman).a(ip.a(cex.aa).a(cfd.b, ir.a().a(is.c, _snowman)).a(cfd.a, ir.a().a(is.c, _snowman))));
   }

   private void l(buo var1) {
      iz _snowman = iz.e(_snowman);
      iz _snowmanx = iz.e(iz.a(_snowman, "_corner"));
      vk _snowmanxx = iy.W.a(_snowman, _snowman, this.b);
      vk _snowmanxxx = iy.X.a(_snowman, _snowmanx, this.b);
      vk _snowmanxxxx = iy.Y.a(_snowman, _snowman, this.b);
      vk _snowmanxxxxx = iy.Z.a(_snowman, _snowman, this.b);
      this.b(_snowman);
      this.a
         .accept(
            io.a(_snowman)
               .a(
                  ip.a(cex.ac)
                     .a(cfk.a, ir.a().a(is.c, _snowmanxx))
                     .a(cfk.b, ir.a().a(is.c, _snowmanxx).a(is.b, is.a.b))
                     .a(cfk.c, ir.a().a(is.c, _snowmanxxxx).a(is.b, is.a.b))
                     .a(cfk.d, ir.a().a(is.c, _snowmanxxxxx).a(is.b, is.a.b))
                     .a(cfk.e, ir.a().a(is.c, _snowmanxxxx))
                     .a(cfk.f, ir.a().a(is.c, _snowmanxxxxx))
                     .a(cfk.g, ir.a().a(is.c, _snowmanxxx))
                     .a(cfk.h, ir.a().a(is.c, _snowmanxxx).a(is.b, is.a.b))
                     .a(cfk.i, ir.a().a(is.c, _snowmanxxx).a(is.b, is.a.c))
                     .a(cfk.j, ir.a().a(is.c, _snowmanxxx).a(is.b, is.a.d))
               )
         );
   }

   private void m(buo var1) {
      vk _snowman = this.a(_snowman, "", iy.W, iz::e);
      vk _snowmanx = this.a(_snowman, "", iy.Y, iz::e);
      vk _snowmanxx = this.a(_snowman, "", iy.Z, iz::e);
      vk _snowmanxxx = this.a(_snowman, "_on", iy.W, iz::e);
      vk _snowmanxxxx = this.a(_snowman, "_on", iy.Y, iz::e);
      vk _snowmanxxxxx = this.a(_snowman, "_on", iy.Z, iz::e);
      ip _snowmanxxxxxx = ip.a(cex.w, cex.ad).a((var6x, var7x) -> {
         switch (var7x) {
            case a:
               return ir.a().a(is.c, var6x ? _snowman : _snowman);
            case b:
               return ir.a().a(is.c, var6x ? _snowman : _snowman).a(is.b, is.a.b);
            case c:
               return ir.a().a(is.c, var6x ? _snowman : _snowman).a(is.b, is.a.b);
            case d:
               return ir.a().a(is.c, var6x ? _snowman : _snowman).a(is.b, is.a.b);
            case e:
               return ir.a().a(is.c, var6x ? _snowman : _snowman);
            case f:
               return ir.a().a(is.c, var6x ? _snowman : _snowman);
            default:
               throw new UnsupportedOperationException("Fix you generator!");
         }
      });
      this.b(_snowman);
      this.a.accept(io.a(_snowman).a(_snowmanxxxxxx));
   }

   private ii.a a(vk var1, buo var2) {
      return new ii.a(_snowman, _snowman);
   }

   private ii.a d(buo var1, buo var2) {
      return new ii.a(iw.a(_snowman), _snowman);
   }

   private void a(buo var1, blx var2) {
      vk _snowman = iy.F.a(_snowman, iz.a(_snowman), this.b);
      this.a.accept(e(_snowman, _snowman));
   }

   private void h(buo var1, vk var2) {
      vk _snowman = iy.F.a(_snowman, iz.h(_snowman), this.b);
      this.a.accept(e(_snowman, _snowman));
   }

   private void e(buo var1, buo var2) {
      this.c(_snowman, jb.a);
      vk _snowman = jb.i.get(_snowman).a(_snowman, this.b);
      this.a.accept(e(_snowman, _snowman));
   }

   private void a(jb.a var1, buo... var2) {
      for (buo _snowman : _snowman) {
         vk _snowmanx = _snowman.a(_snowman, this.b);
         this.a.accept(d(_snowman, _snowmanx));
      }
   }

   private void b(jb.a var1, buo... var2) {
      for (buo _snowman : _snowman) {
         vk _snowmanx = _snowman.a(_snowman, this.b);
         this.a.accept(io.a(_snowman, ir.a().a(is.c, _snowmanx)).a(c()));
      }
   }

   private void f(buo var1, buo var2) {
      this.e(_snowman);
      iz _snowman = iz.b(_snowman, _snowman);
      vk _snowmanx = iy.ai.a(_snowman, _snowman, this.b);
      vk _snowmanxx = iy.aj.a(_snowman, _snowman, this.b);
      vk _snowmanxxx = iy.ak.a(_snowman, _snowman, this.b);
      vk _snowmanxxxx = iy.ag.a(_snowman, _snowman, this.b);
      vk _snowmanxxxxx = iy.ah.a(_snowman, _snowman, this.b);
      blx _snowmanxxxxxx = _snowman.h();
      iy.aK.a(iw.a(_snowmanxxxxxx), iz.B(_snowman), this.b);
      this.a
         .accept(
            in.a(_snowman)
               .a(ir.a().a(is.c, _snowmanx))
               .a(im.a().a(cex.I, true), ir.a().a(is.c, _snowmanxx))
               .a(im.a().a(cex.J, true), ir.a().a(is.c, _snowmanxx).a(is.b, is.a.b))
               .a(im.a().a(cex.K, true), ir.a().a(is.c, _snowmanxxx))
               .a(im.a().a(cex.L, true), ir.a().a(is.c, _snowmanxxx).a(is.b, is.a.b))
               .a(im.a().a(cex.I, false), ir.a().a(is.c, _snowmanxxxx))
               .a(im.a().a(cex.J, false), ir.a().a(is.c, _snowmanxxxxx))
               .a(im.a().a(cex.K, false), ir.a().a(is.c, _snowmanxxxxx).a(is.b, is.a.b))
               .a(im.a().a(cex.L, false), ir.a().a(is.c, _snowmanxxxx).a(is.b, is.a.d))
         );
   }

   private void n(buo var1) {
      iz _snowman = iz.v(_snowman);
      vk _snowmanx = iy.al.a(_snowman, _snowman, this.b);
      vk _snowmanxx = this.a(_snowman, "_conditional", iy.al, var1x -> _snowman.c(ja.i, var1x));
      this.a.accept(io.a(_snowman).a(a(cex.c, _snowmanxx, _snowmanx)).a(e()));
   }

   private void o(buo var1) {
      vk _snowman = jb.m.a(_snowman, this.b);
      this.a.accept(e(_snowman, _snowman).a(c()));
   }

   private List<ir> a(int var1) {
      String _snowman = "_age" + _snowman;
      return IntStream.range(1, 5).mapToObj(var1x -> ir.a().a(is.c, iw.a(bup.kY, var1x + _snowman))).collect(Collectors.toList());
   }

   private void i() {
      this.a(bup.kY);
      this.a
         .accept(
            in.a(bup.kY)
               .a(im.a().a(cex.ae, 0), this.a(0))
               .a(im.a().a(cex.ae, 1), this.a(1))
               .a(im.a().a(cex.aN, ceu.b), ir.a().a(is.c, iw.a(bup.kY, "_small_leaves")))
               .a(im.a().a(cex.aN, ceu.c), ir.a().a(is.c, iw.a(bup.kY, "_large_leaves")))
         );
   }

   private ip j() {
      return ip.a(cex.M)
         .a(gc.a, ir.a().a(is.a, is.a.c))
         .a(gc.b, ir.a())
         .a(gc.c, ir.a().a(is.a, is.a.b))
         .a(gc.d, ir.a().a(is.a, is.a.b).a(is.b, is.a.c))
         .a(gc.e, ir.a().a(is.a, is.a.b).a(is.b, is.a.d))
         .a(gc.f, ir.a().a(is.a, is.a.b).a(is.b, is.a.b));
   }

   private void k() {
      vk _snowman = iz.a(bup.lS, "_top_open");
      this.a
         .accept(
            io.a(bup.lS)
               .a(this.j())
               .a(
                  ip.a(cex.u)
                     .a(false, ir.a().a(is.c, jb.e.a(bup.lS, this.b)))
                     .a(true, ir.a().a(is.c, jb.e.get(bup.lS).a(var1x -> var1x.a(ja.f, _snowman)).a(bup.lS, "_open", this.b)))
               )
         );
   }

   private static <T extends Comparable<T>> ip a(cfj<T> var0, T var1, vk var2, vk var3) {
      ir _snowman = ir.a().a(is.c, _snowman);
      ir _snowmanx = ir.a().a(is.c, _snowman);
      return ip.a(_snowman).a(var3x -> {
         boolean _snowmanxx = var3x.compareTo(_snowman) >= 0;
         return _snowmanxx ? _snowman : _snowman;
      });
   }

   private void a(buo var1, Function<buo, iz> var2) {
      iz _snowman = _snowman.apply(_snowman).b(ja.i, ja.c);
      iz _snowmanx = _snowman.c(ja.g, iz.a(_snowman, "_front_honey"));
      vk _snowmanxx = iy.j.a(_snowman, _snowman, this.b);
      vk _snowmanxxx = iy.j.a(_snowman, "_honey", _snowmanx, this.b);
      this.a.accept(io.a(_snowman).a(b()).a(a(cex.au, 5, _snowmanxxx, _snowmanxx)));
   }

   private void a(buo var1, cfj<Integer> var2, int... var3) {
      if (_snowman.a().size() != _snowman.length) {
         throw new IllegalArgumentException();
      } else {
         Int2ObjectMap<vk> _snowman = new Int2ObjectOpenHashMap();
         ip _snowmanx = ip.a(_snowman).a(var4x -> {
            int _snowmanxx = _snowman[var4x];
            vk _snowmanx = (vk)_snowman.computeIfAbsent(_snowmanxx, var3x -> this.a(_snowman, "_stage" + _snowman, iy.ap, iz::g));
            return ir.a().a(is.c, _snowmanx);
         });
         this.a(_snowman.h());
         this.a.accept(io.a(_snowman).a(_snowmanx));
      }
   }

   private void l() {
      vk _snowman = iw.a(bup.mb, "_floor");
      vk _snowmanx = iw.a(bup.mb, "_ceiling");
      vk _snowmanxx = iw.a(bup.mb, "_wall");
      vk _snowmanxxx = iw.a(bup.mb, "_between_walls");
      this.a(bmd.rj);
      this.a
         .accept(
            io.a(bup.mb)
               .a(
                  ip.a(cex.O, cex.R)
                     .a(gc.c, cew.a, ir.a().a(is.c, _snowman))
                     .a(gc.d, cew.a, ir.a().a(is.c, _snowman).a(is.b, is.a.c))
                     .a(gc.f, cew.a, ir.a().a(is.c, _snowman).a(is.b, is.a.b))
                     .a(gc.e, cew.a, ir.a().a(is.c, _snowman).a(is.b, is.a.d))
                     .a(gc.c, cew.b, ir.a().a(is.c, _snowmanx))
                     .a(gc.d, cew.b, ir.a().a(is.c, _snowmanx).a(is.b, is.a.c))
                     .a(gc.f, cew.b, ir.a().a(is.c, _snowmanx).a(is.b, is.a.b))
                     .a(gc.e, cew.b, ir.a().a(is.c, _snowmanx).a(is.b, is.a.d))
                     .a(gc.c, cew.c, ir.a().a(is.c, _snowmanxx).a(is.b, is.a.d))
                     .a(gc.d, cew.c, ir.a().a(is.c, _snowmanxx).a(is.b, is.a.b))
                     .a(gc.f, cew.c, ir.a().a(is.c, _snowmanxx))
                     .a(gc.e, cew.c, ir.a().a(is.c, _snowmanxx).a(is.b, is.a.c))
                     .a(gc.d, cew.d, ir.a().a(is.c, _snowmanxxx).a(is.b, is.a.b))
                     .a(gc.c, cew.d, ir.a().a(is.c, _snowmanxxx).a(is.b, is.a.d))
                     .a(gc.f, cew.d, ir.a().a(is.c, _snowmanxxx))
                     .a(gc.e, cew.d, ir.a().a(is.c, _snowmanxxx).a(is.b, is.a.c))
               )
         );
   }

   private void m() {
      this.a
         .accept(
            io.a(bup.lX, ir.a().a(is.c, iw.a(bup.lX)))
               .a(
                  ip.a(cex.Q, cex.O)
                     .a(cet.a, gc.c, ir.a())
                     .a(cet.a, gc.f, ir.a().a(is.b, is.a.b))
                     .a(cet.a, gc.d, ir.a().a(is.b, is.a.c))
                     .a(cet.a, gc.e, ir.a().a(is.b, is.a.d))
                     .a(cet.b, gc.c, ir.a().a(is.a, is.a.b))
                     .a(cet.b, gc.f, ir.a().a(is.a, is.a.b).a(is.b, is.a.b))
                     .a(cet.b, gc.d, ir.a().a(is.a, is.a.b).a(is.b, is.a.c))
                     .a(cet.b, gc.e, ir.a().a(is.a, is.a.b).a(is.b, is.a.d))
                     .a(cet.c, gc.d, ir.a().a(is.a, is.a.c))
                     .a(cet.c, gc.e, ir.a().a(is.a, is.a.c).a(is.b, is.a.b))
                     .a(cet.c, gc.c, ir.a().a(is.a, is.a.c).a(is.b, is.a.c))
                     .a(cet.c, gc.f, ir.a().a(is.a, is.a.c).a(is.b, is.a.d))
               )
         );
   }

   private void e(buo var1, jb.a var2) {
      vk _snowman = _snowman.a(_snowman, this.b);
      vk _snowmanx = iz.a(_snowman, "_front_on");
      vk _snowmanxx = _snowman.get(_snowman).a(var1x -> var1x.a(ja.g, _snowman)).a(_snowman, "_on", this.b);
      this.a.accept(io.a(_snowman).a(a(cex.r, _snowmanxx, _snowman)).a(b()));
   }

   private void a(buo... var1) {
      vk _snowman = iw.a("campfire_off");

      for (buo _snowmanx : _snowman) {
         vk _snowmanxx = iy.aw.a(_snowmanx, iz.A(_snowmanx), this.b);
         this.a(_snowmanx.h());
         this.a.accept(io.a(_snowmanx).a(a(cex.r, _snowmanxx, _snowman)).a(c()));
      }
   }

   private void n() {
      iz _snowman = iz.a(iz.C(bup.bI), iz.C(bup.n));
      vk _snowmanx = iy.e.a(bup.bI, _snowman, this.b);
      this.a.accept(e(bup.bI, _snowmanx));
   }

   private void o() {
      this.a(bmd.lP);
      this.a
         .accept(
            in.a(bup.bS)
               .a(
                  im.b(
                     im.a().a(cex.X, cfl.c).a(cex.W, cfl.c).a(cex.Y, cfl.c).a(cex.Z, cfl.c),
                     im.a().a(cex.X, cfl.b, cfl.a).a(cex.W, cfl.b, cfl.a),
                     im.a().a(cex.W, cfl.b, cfl.a).a(cex.Y, cfl.b, cfl.a),
                     im.a().a(cex.Y, cfl.b, cfl.a).a(cex.Z, cfl.b, cfl.a),
                     im.a().a(cex.Z, cfl.b, cfl.a).a(cex.X, cfl.b, cfl.a)
                  ),
                  ir.a().a(is.c, iw.a("redstone_dust_dot"))
               )
               .a(im.a().a(cex.X, cfl.b, cfl.a), ir.a().a(is.c, iw.a("redstone_dust_side0")))
               .a(im.a().a(cex.Y, cfl.b, cfl.a), ir.a().a(is.c, iw.a("redstone_dust_side_alt0")))
               .a(im.a().a(cex.W, cfl.b, cfl.a), ir.a().a(is.c, iw.a("redstone_dust_side_alt1")).a(is.b, is.a.d))
               .a(im.a().a(cex.Z, cfl.b, cfl.a), ir.a().a(is.c, iw.a("redstone_dust_side1")).a(is.b, is.a.d))
               .a(im.a().a(cex.X, cfl.a), ir.a().a(is.c, iw.a("redstone_dust_up")))
               .a(im.a().a(cex.W, cfl.a), ir.a().a(is.c, iw.a("redstone_dust_up")).a(is.b, is.a.b))
               .a(im.a().a(cex.Y, cfl.a), ir.a().a(is.c, iw.a("redstone_dust_up")).a(is.b, is.a.c))
               .a(im.a().a(cex.Z, cfl.a), ir.a().a(is.c, iw.a("redstone_dust_up")).a(is.b, is.a.d))
         );
   }

   private void p() {
      this.a(bmd.jV);
      this.a
         .accept(
            io.a(bup.fu)
               .a(c())
               .a(
                  ip.a(cex.aG, cex.w)
                     .a(cfa.a, false, ir.a().a(is.c, iw.a(bup.fu)))
                     .a(cfa.a, true, ir.a().a(is.c, iw.a(bup.fu, "_on")))
                     .a(cfa.b, false, ir.a().a(is.c, iw.a(bup.fu, "_subtract")))
                     .a(cfa.b, true, ir.a().a(is.c, iw.a(bup.fu, "_on_subtract")))
               )
         );
   }

   private void q() {
      iz _snowman = iz.a(bup.id);
      iz _snowmanx = iz.a(iz.a(bup.hR, "_side"), _snowman.a(ja.f));
      vk _snowmanxx = iy.G.a(bup.hR, _snowmanx, this.b);
      vk _snowmanxxx = iy.H.a(bup.hR, _snowmanx, this.b);
      vk _snowmanxxxx = iy.e.b(bup.hR, "_double", _snowmanx, this.b);
      this.a.accept(h(bup.hR, _snowmanxx, _snowmanxxx, _snowmanxxxx));
      this.a.accept(e(bup.id, iy.c.a(bup.id, _snowman, this.b)));
   }

   private void r() {
      this.a(bmd.nB);
      this.a
         .accept(
            in.a(bup.ea)
               .a(ir.a().a(is.c, iz.C(bup.ea)))
               .a(im.a().a(cex.k, true), ir.a().a(is.c, iz.a(bup.ea, "_bottle0")))
               .a(im.a().a(cex.l, true), ir.a().a(is.c, iz.a(bup.ea, "_bottle1")))
               .a(im.a().a(cex.m, true), ir.a().a(is.c, iz.a(bup.ea, "_bottle2")))
               .a(im.a().a(cex.k, false), ir.a().a(is.c, iz.a(bup.ea, "_empty0")))
               .a(im.a().a(cex.l, false), ir.a().a(is.c, iz.a(bup.ea, "_empty1")))
               .a(im.a().a(cex.m, false), ir.a().a(is.c, iz.a(bup.ea, "_empty2")))
         );
   }

   private void p(buo var1) {
      vk _snowman = iy.aJ.a(_snowman, iz.b(_snowman), this.b);
      vk _snowmanx = iw.a("mushroom_block_inside");
      this.a
         .accept(
            in.a(_snowman)
               .a(im.a().a(cex.I, true), ir.a().a(is.c, _snowman))
               .a(im.a().a(cex.J, true), ir.a().a(is.c, _snowman).a(is.b, is.a.b).a(is.d, true))
               .a(im.a().a(cex.K, true), ir.a().a(is.c, _snowman).a(is.b, is.a.c).a(is.d, true))
               .a(im.a().a(cex.L, true), ir.a().a(is.c, _snowman).a(is.b, is.a.d).a(is.d, true))
               .a(im.a().a(cex.G, true), ir.a().a(is.c, _snowman).a(is.a, is.a.d).a(is.d, true))
               .a(im.a().a(cex.H, true), ir.a().a(is.c, _snowman).a(is.a, is.a.b).a(is.d, true))
               .a(im.a().a(cex.I, false), ir.a().a(is.c, _snowmanx))
               .a(im.a().a(cex.J, false), ir.a().a(is.c, _snowmanx).a(is.b, is.a.b).a(is.d, false))
               .a(im.a().a(cex.K, false), ir.a().a(is.c, _snowmanx).a(is.b, is.a.c).a(is.d, false))
               .a(im.a().a(cex.L, false), ir.a().a(is.c, _snowmanx).a(is.b, is.a.d).a(is.d, false))
               .a(im.a().a(cex.G, false), ir.a().a(is.c, _snowmanx).a(is.a, is.a.d).a(is.d, false))
               .a(im.a().a(cex.H, false), ir.a().a(is.c, _snowmanx).a(is.a, is.a.b).a(is.d, false))
         );
      this.c(_snowman, jb.a.a(_snowman, "_inventory", this.b));
   }

   private void s() {
      this.a(bmd.mN);
      this.a
         .accept(
            io.a(bup.cW)
               .a(
                  ip.a(cex.al)
                     .a(0, ir.a().a(is.c, iw.a(bup.cW)))
                     .a(1, ir.a().a(is.c, iw.a(bup.cW, "_slice1")))
                     .a(2, ir.a().a(is.c, iw.a(bup.cW, "_slice2")))
                     .a(3, ir.a().a(is.c, iw.a(bup.cW, "_slice3")))
                     .a(4, ir.a().a(is.c, iw.a(bup.cW, "_slice4")))
                     .a(5, ir.a().a(is.c, iw.a(bup.cW, "_slice5")))
                     .a(6, ir.a().a(is.c, iw.a(bup.cW, "_slice6")))
               )
         );
   }

   private void t() {
      iz _snowman = new iz()
         .a(ja.c, iz.a(bup.lV, "_side3"))
         .a(ja.o, iz.C(bup.s))
         .a(ja.n, iz.a(bup.lV, "_top"))
         .a(ja.j, iz.a(bup.lV, "_side3"))
         .a(ja.l, iz.a(bup.lV, "_side3"))
         .a(ja.k, iz.a(bup.lV, "_side1"))
         .a(ja.m, iz.a(bup.lV, "_side2"));
      this.a.accept(e(bup.lV, iy.a.a(bup.lV, _snowman, this.b)));
   }

   private void u() {
      iz _snowman = new iz()
         .a(ja.c, iz.a(bup.lZ, "_front"))
         .a(ja.o, iz.a(bup.lZ, "_bottom"))
         .a(ja.n, iz.a(bup.lZ, "_top"))
         .a(ja.j, iz.a(bup.lZ, "_front"))
         .a(ja.k, iz.a(bup.lZ, "_front"))
         .a(ja.l, iz.a(bup.lZ, "_side"))
         .a(ja.m, iz.a(bup.lZ, "_side"));
      this.a.accept(e(bup.lZ, iy.a.a(bup.lZ, _snowman, this.b)));
   }

   private void a(buo var1, buo var2, BiFunction<buo, buo, iz> var3) {
      iz _snowman = _snowman.apply(_snowman, _snowman);
      this.a.accept(e(_snowman, iy.a.a(_snowman, _snowman, this.b)));
   }

   private void v() {
      iz _snowman = iz.j(bup.cK);
      this.a.accept(e(bup.cK, iw.a(bup.cK)));
      this.a(bup.cU, _snowman);
      this.a(bup.cV, _snowman);
   }

   private void a(buo var1, iz var2) {
      vk _snowman = iy.i.a(_snowman, _snowman.c(ja.g, iz.C(_snowman)), this.b);
      this.a.accept(io.a(_snowman, ir.a().a(is.c, _snowman)).a(b()));
   }

   private void w() {
      this.a(bmd.nC);
      this.a
         .accept(
            io.a(bup.eb)
               .a(
                  ip.a(cex.ar)
                     .a(0, ir.a().a(is.c, iw.a(bup.eb)))
                     .a(1, ir.a().a(is.c, iw.a(bup.eb, "_level1")))
                     .a(2, ir.a().a(is.c, iw.a(bup.eb, "_level2")))
                     .a(3, ir.a().a(is.c, iw.a(bup.eb, "_level3")))
               )
         );
   }

   private void g(buo var1, buo var2) {
      iz _snowman = new iz().a(ja.d, iz.a(_snowman, "_top")).a(ja.i, iz.C(_snowman));
      this.a(_snowman, _snowman, iy.e);
   }

   private void x() {
      iz _snowman = iz.b(bup.iy);
      vk _snowmanx = iy.ae.a(bup.iy, _snowman, this.b);
      vk _snowmanxx = this.a(bup.iy, "_dead", iy.ae, var1x -> _snowman.c(ja.b, var1x));
      this.a.accept(io.a(bup.iy).a(a(cex.ah, 5, _snowmanxx, _snowmanx)));
   }

   private void q(buo var1) {
      iz _snowman = new iz().a(ja.f, iz.a(bup.bY, "_top")).a(ja.i, iz.a(bup.bY, "_side")).a(ja.g, iz.a(_snowman, "_front"));
      iz _snowmanx = new iz().a(ja.i, iz.a(bup.bY, "_top")).a(ja.g, iz.a(_snowman, "_front_vertical"));
      vk _snowmanxx = iy.i.a(_snowman, _snowman, this.b);
      vk _snowmanxxx = iy.k.a(_snowman, _snowmanx, this.b);
      this.a
         .accept(
            io.a(_snowman)
               .a(
                  ip.a(cex.M)
                     .a(gc.a, ir.a().a(is.c, _snowmanxxx).a(is.a, is.a.c))
                     .a(gc.b, ir.a().a(is.c, _snowmanxxx))
                     .a(gc.c, ir.a().a(is.c, _snowmanxx))
                     .a(gc.f, ir.a().a(is.c, _snowmanxx).a(is.b, is.a.b))
                     .a(gc.d, ir.a().a(is.c, _snowmanxx).a(is.b, is.a.c))
                     .a(gc.e, ir.a().a(is.c, _snowmanxx).a(is.b, is.a.d))
               )
         );
   }

   private void y() {
      vk _snowman = iw.a(bup.ed);
      vk _snowmanx = iw.a(bup.ed, "_filled");
      this.a.accept(io.a(bup.ed).a(ip.a(cex.h).a(false, ir.a().a(is.c, _snowman)).a(true, ir.a().a(is.c, _snowmanx))).a(c()));
   }

   private void z() {
      vk _snowman = iw.a(bup.ix, "_side");
      vk _snowmanx = iw.a(bup.ix, "_noside");
      vk _snowmanxx = iw.a(bup.ix, "_noside1");
      vk _snowmanxxx = iw.a(bup.ix, "_noside2");
      vk _snowmanxxxx = iw.a(bup.ix, "_noside3");
      this.a
         .accept(
            in.a(bup.ix)
               .a(im.a().a(cex.I, true), ir.a().a(is.c, _snowman))
               .a(im.a().a(cex.J, true), ir.a().a(is.c, _snowman).a(is.b, is.a.b).a(is.d, true))
               .a(im.a().a(cex.K, true), ir.a().a(is.c, _snowman).a(is.b, is.a.c).a(is.d, true))
               .a(im.a().a(cex.L, true), ir.a().a(is.c, _snowman).a(is.b, is.a.d).a(is.d, true))
               .a(im.a().a(cex.G, true), ir.a().a(is.c, _snowman).a(is.a, is.a.d).a(is.d, true))
               .a(im.a().a(cex.H, true), ir.a().a(is.c, _snowman).a(is.a, is.a.b).a(is.d, true))
               .a(im.a().a(cex.I, false), ir.a().a(is.c, _snowmanx).a(is.e, 2), ir.a().a(is.c, _snowmanxx), ir.a().a(is.c, _snowmanxxx), ir.a().a(is.c, _snowmanxxxx))
               .a(
                  im.a().a(cex.J, false),
                  ir.a().a(is.c, _snowmanxx).a(is.b, is.a.b).a(is.d, true),
                  ir.a().a(is.c, _snowmanxxx).a(is.b, is.a.b).a(is.d, true),
                  ir.a().a(is.c, _snowmanxxxx).a(is.b, is.a.b).a(is.d, true),
                  ir.a().a(is.c, _snowmanx).a(is.e, 2).a(is.b, is.a.b).a(is.d, true)
               )
               .a(
                  im.a().a(cex.K, false),
                  ir.a().a(is.c, _snowmanxxx).a(is.b, is.a.c).a(is.d, true),
                  ir.a().a(is.c, _snowmanxxxx).a(is.b, is.a.c).a(is.d, true),
                  ir.a().a(is.c, _snowmanx).a(is.e, 2).a(is.b, is.a.c).a(is.d, true),
                  ir.a().a(is.c, _snowmanxx).a(is.b, is.a.c).a(is.d, true)
               )
               .a(
                  im.a().a(cex.L, false),
                  ir.a().a(is.c, _snowmanxxxx).a(is.b, is.a.d).a(is.d, true),
                  ir.a().a(is.c, _snowmanx).a(is.e, 2).a(is.b, is.a.d).a(is.d, true),
                  ir.a().a(is.c, _snowmanxx).a(is.b, is.a.d).a(is.d, true),
                  ir.a().a(is.c, _snowmanxxx).a(is.b, is.a.d).a(is.d, true)
               )
               .a(
                  im.a().a(cex.G, false),
                  ir.a().a(is.c, _snowmanx).a(is.e, 2).a(is.a, is.a.d).a(is.d, true),
                  ir.a().a(is.c, _snowmanxxxx).a(is.a, is.a.d).a(is.d, true),
                  ir.a().a(is.c, _snowmanxx).a(is.a, is.a.d).a(is.d, true),
                  ir.a().a(is.c, _snowmanxxx).a(is.a, is.a.d).a(is.d, true)
               )
               .a(
                  im.a().a(cex.H, false),
                  ir.a().a(is.c, _snowmanxxxx).a(is.a, is.a.b).a(is.d, true),
                  ir.a().a(is.c, _snowmanxxx).a(is.a, is.a.b).a(is.d, true),
                  ir.a().a(is.c, _snowmanxx).a(is.a, is.a.b).a(is.d, true),
                  ir.a().a(is.c, _snowmanx).a(is.e, 2).a(is.a, is.a.b).a(is.d, true)
               )
         );
   }

   private void A() {
      this.a
         .accept(
            in.a(bup.na)
               .a(ir.a().a(is.c, iz.C(bup.na)))
               .a(im.a().a(cex.as, 1), ir.a().a(is.c, iz.a(bup.na, "_contents1")))
               .a(im.a().a(cex.as, 2), ir.a().a(is.c, iz.a(bup.na, "_contents2")))
               .a(im.a().a(cex.as, 3), ir.a().a(is.c, iz.a(bup.na, "_contents3")))
               .a(im.a().a(cex.as, 4), ir.a().a(is.c, iz.a(bup.na, "_contents4")))
               .a(im.a().a(cex.as, 5), ir.a().a(is.c, iz.a(bup.na, "_contents5")))
               .a(im.a().a(cex.as, 6), ir.a().a(is.c, iz.a(bup.na, "_contents6")))
               .a(im.a().a(cex.as, 7), ir.a().a(is.c, iz.a(bup.na, "_contents7")))
               .a(im.a().a(cex.as, 8), ir.a().a(is.c, iz.a(bup.na, "_contents_ready")))
         );
   }

   private void r(buo var1) {
      iz _snowman = new iz().a(ja.e, iz.C(bup.cL)).a(ja.f, iz.C(_snowman)).a(ja.i, iz.a(_snowman, "_side"));
      this.a.accept(e(_snowman, iy.h.a(_snowman, _snowman, this.b)));
   }

   private void B() {
      vk _snowman = iz.a(bup.fv, "_side");
      iz _snowmanx = new iz().a(ja.f, iz.a(bup.fv, "_top")).a(ja.i, _snowman);
      iz _snowmanxx = new iz().a(ja.f, iz.a(bup.fv, "_inverted_top")).a(ja.i, _snowman);
      this.a
         .accept(
            io.a(bup.fv)
               .a(ip.a(cex.p).a(false, ir.a().a(is.c, iy.af.a(bup.fv, _snowmanx, this.b))).a(true, ir.a().a(is.c, iy.af.a(iw.a(bup.fv, "_inverted"), _snowmanxx, this.b))))
         );
   }

   private void s(buo var1) {
      this.a.accept(io.a(_snowman, ir.a().a(is.c, iw.a(_snowman))).a(this.j()));
   }

   private void C() {
      iz _snowman = new iz().a(ja.B, iz.C(bup.j)).a(ja.f, iz.C(bup.bX));
      iz _snowmanx = new iz().a(ja.B, iz.C(bup.j)).a(ja.f, iz.a(bup.bX, "_moist"));
      vk _snowmanxx = iy.aq.a(bup.bX, _snowman, this.b);
      vk _snowmanxxx = iy.aq.a(iz.a(bup.bX, "_moist"), _snowmanx, this.b);
      this.a.accept(io.a(bup.bX).a(a(cex.aw, 7, _snowmanxxx, _snowmanxx)));
   }

   private List<vk> t(buo var1) {
      vk _snowman = iy.ar.a(iw.a(_snowman, "_floor0"), iz.r(_snowman), this.b);
      vk _snowmanx = iy.ar.a(iw.a(_snowman, "_floor1"), iz.s(_snowman), this.b);
      return ImmutableList.of(_snowman, _snowmanx);
   }

   private List<vk> u(buo var1) {
      vk _snowman = iy.as.a(iw.a(_snowman, "_side0"), iz.r(_snowman), this.b);
      vk _snowmanx = iy.as.a(iw.a(_snowman, "_side1"), iz.s(_snowman), this.b);
      vk _snowmanxx = iy.at.a(iw.a(_snowman, "_side_alt0"), iz.r(_snowman), this.b);
      vk _snowmanxxx = iy.at.a(iw.a(_snowman, "_side_alt1"), iz.s(_snowman), this.b);
      return ImmutableList.of(_snowman, _snowmanx, _snowmanxx, _snowmanxxx);
   }

   private List<vk> v(buo var1) {
      vk _snowman = iy.au.a(iw.a(_snowman, "_up0"), iz.r(_snowman), this.b);
      vk _snowmanx = iy.au.a(iw.a(_snowman, "_up1"), iz.s(_snowman), this.b);
      vk _snowmanxx = iy.av.a(iw.a(_snowman, "_up_alt0"), iz.r(_snowman), this.b);
      vk _snowmanxxx = iy.av.a(iw.a(_snowman, "_up_alt1"), iz.s(_snowman), this.b);
      return ImmutableList.of(_snowman, _snowmanx, _snowmanxx, _snowmanxxx);
   }

   private static List<ir> a(List<vk> var0, UnaryOperator<ir> var1) {
      return _snowman.stream().map(var0x -> ir.a().a(is.c, var0x)).map(_snowman).collect(Collectors.toList());
   }

   private void D() {
      im _snowman = im.a().a(cex.I, false).a(cex.J, false).a(cex.K, false).a(cex.L, false).a(cex.G, false);
      List<vk> _snowmanx = this.t(bup.bN);
      List<vk> _snowmanxx = this.u(bup.bN);
      List<vk> _snowmanxxx = this.v(bup.bN);
      this.a
         .accept(
            in.a(bup.bN)
               .a(_snowman, a(_snowmanx, var0 -> var0))
               .a(im.b(im.a().a(cex.I, true), _snowman), a(_snowmanxx, var0 -> var0))
               .a(im.b(im.a().a(cex.J, true), _snowman), a(_snowmanxx, var0 -> var0.a(is.b, is.a.b)))
               .a(im.b(im.a().a(cex.K, true), _snowman), a(_snowmanxx, var0 -> var0.a(is.b, is.a.c)))
               .a(im.b(im.a().a(cex.L, true), _snowman), a(_snowmanxx, var0 -> var0.a(is.b, is.a.d)))
               .a(im.a().a(cex.G, true), a(_snowmanxxx, var0 -> var0))
         );
   }

   private void E() {
      List<vk> _snowman = this.t(bup.bO);
      List<vk> _snowmanx = this.u(bup.bO);
      this.a
         .accept(
            in.a(bup.bO)
               .a(a(_snowman, var0 -> var0))
               .a(a(_snowmanx, var0 -> var0))
               .a(a(_snowmanx, var0 -> var0.a(is.b, is.a.b)))
               .a(a(_snowmanx, var0 -> var0.a(is.b, is.a.c)))
               .a(a(_snowmanx, var0 -> var0.a(is.b, is.a.d)))
         );
   }

   private void w(buo var1) {
      vk _snowman = jb.o.a(_snowman, this.b);
      vk _snowmanx = jb.p.a(_snowman, this.b);
      this.a(_snowman.h());
      this.a.accept(io.a(_snowman).a(a(cex.j, _snowmanx, _snowman)));
   }

   private void F() {
      this.a
         .accept(
            io.a(bup.iI)
               .a(
                  ip.a(cex.ag)
                     .a(0, ir.a().a(is.c, this.a(bup.iI, "_0", iy.c, iz::b)))
                     .a(1, ir.a().a(is.c, this.a(bup.iI, "_1", iy.c, iz::b)))
                     .a(2, ir.a().a(is.c, this.a(bup.iI, "_2", iy.c, iz::b)))
                     .a(3, ir.a().a(is.c, this.a(bup.iI, "_3", iy.c, iz::b)))
               )
         );
   }

   private void G() {
      vk _snowman = iz.C(bup.j);
      iz _snowmanx = new iz().a(ja.e, _snowman).b(ja.e, ja.c).a(ja.f, iz.a(bup.i, "_top")).a(ja.i, iz.a(bup.i, "_snow"));
      ir _snowmanxx = ir.a().a(is.c, iy.h.a(bup.i, "_snow", _snowmanx, this.b));
      this.a(bup.i, iw.a(bup.i), _snowmanxx);
      vk _snowmanxxx = jb.e.get(bup.dT).a(var1x -> var1x.a(ja.e, _snowman)).a(bup.dT, this.b);
      this.a(bup.dT, _snowmanxxx, _snowmanxx);
      vk _snowmanxxxx = jb.e.get(bup.l).a(var1x -> var1x.a(ja.e, _snowman)).a(bup.l, this.b);
      this.a(bup.l, _snowmanxxxx, _snowmanxx);
   }

   private void a(buo var1, vk var2, ir var3) {
      List<ir> _snowman = Arrays.asList(a(_snowman));
      this.a.accept(io.a(_snowman).a(ip.a(cex.z).a(true, _snowman).a(false, _snowman)));
   }

   private void H() {
      this.a(bmd.ms);
      this.a
         .accept(
            io.a(bup.eh)
               .a(
                  ip.a(cex.af)
                     .a(0, ir.a().a(is.c, iw.a(bup.eh, "_stage0")))
                     .a(1, ir.a().a(is.c, iw.a(bup.eh, "_stage1")))
                     .a(2, ir.a().a(is.c, iw.a(bup.eh, "_stage2")))
               )
               .a(c())
         );
   }

   private void I() {
      this.a.accept(d(bup.iE, iw.a(bup.iE)));
   }

   private void h(buo var1, buo var2) {
      iz _snowman = iz.b(_snowman);
      vk _snowmanx = iy.D.a(_snowman, _snowman, this.b);
      vk _snowmanxx = iy.E.a(_snowman, _snowman, this.b);
      this.a.accept(io.a(_snowman).a(a(cex.az, 1, _snowmanxx, _snowmanx)));
   }

   private void J() {
      vk _snowman = iw.a(bup.fy);
      vk _snowmanx = iw.a(bup.fy, "_side");
      this.a(bmd.fl);
      this.a
         .accept(
            io.a(bup.fy)
               .a(
                  ip.a(cex.N)
                     .a(gc.a, ir.a().a(is.c, _snowman))
                     .a(gc.c, ir.a().a(is.c, _snowmanx))
                     .a(gc.f, ir.a().a(is.c, _snowmanx).a(is.b, is.a.b))
                     .a(gc.d, ir.a().a(is.c, _snowmanx).a(is.b, is.a.c))
                     .a(gc.e, ir.a().a(is.c, _snowmanx).a(is.b, is.a.d))
               )
         );
   }

   private void i(buo var1, buo var2) {
      vk _snowman = iw.a(_snowman);
      this.a.accept(io.a(_snowman, ir.a().a(is.c, _snowman)));
      this.c(_snowman, _snowman);
   }

   private void K() {
      vk _snowman = iw.a(bup.dH, "_post_ends");
      vk _snowmanx = iw.a(bup.dH, "_post");
      vk _snowmanxx = iw.a(bup.dH, "_cap");
      vk _snowmanxxx = iw.a(bup.dH, "_cap_alt");
      vk _snowmanxxxx = iw.a(bup.dH, "_side");
      vk _snowmanxxxxx = iw.a(bup.dH, "_side_alt");
      this.a
         .accept(
            in.a(bup.dH)
               .a(ir.a().a(is.c, _snowman))
               .a(im.a().a(cex.I, false).a(cex.J, false).a(cex.K, false).a(cex.L, false), ir.a().a(is.c, _snowmanx))
               .a(im.a().a(cex.I, true).a(cex.J, false).a(cex.K, false).a(cex.L, false), ir.a().a(is.c, _snowmanxx))
               .a(im.a().a(cex.I, false).a(cex.J, true).a(cex.K, false).a(cex.L, false), ir.a().a(is.c, _snowmanxx).a(is.b, is.a.b))
               .a(im.a().a(cex.I, false).a(cex.J, false).a(cex.K, true).a(cex.L, false), ir.a().a(is.c, _snowmanxxx))
               .a(im.a().a(cex.I, false).a(cex.J, false).a(cex.K, false).a(cex.L, true), ir.a().a(is.c, _snowmanxxx).a(is.b, is.a.b))
               .a(im.a().a(cex.I, true), ir.a().a(is.c, _snowmanxxxx))
               .a(im.a().a(cex.J, true), ir.a().a(is.c, _snowmanxxxx).a(is.b, is.a.b))
               .a(im.a().a(cex.K, true), ir.a().a(is.c, _snowmanxxxxx))
               .a(im.a().a(cex.L, true), ir.a().a(is.c, _snowmanxxxxx).a(is.b, is.a.b))
         );
      this.b(bup.dH);
   }

   private void x(buo var1) {
      this.a.accept(io.a(_snowman, ir.a().a(is.c, iw.a(_snowman))).a(b()));
   }

   private void L() {
      vk _snowman = iw.a(bup.cp);
      vk _snowmanx = iw.a(bup.cp, "_on");
      this.b(bup.cp);
      this.a
         .accept(
            io.a(bup.cp)
               .a(a(cex.w, _snowman, _snowmanx))
               .a(
                  ip.a(cex.Q, cex.O)
                     .a(cet.c, gc.c, ir.a().a(is.a, is.a.c).a(is.b, is.a.c))
                     .a(cet.c, gc.f, ir.a().a(is.a, is.a.c).a(is.b, is.a.d))
                     .a(cet.c, gc.d, ir.a().a(is.a, is.a.c))
                     .a(cet.c, gc.e, ir.a().a(is.a, is.a.c).a(is.b, is.a.b))
                     .a(cet.a, gc.c, ir.a())
                     .a(cet.a, gc.f, ir.a().a(is.b, is.a.b))
                     .a(cet.a, gc.d, ir.a().a(is.b, is.a.c))
                     .a(cet.a, gc.e, ir.a().a(is.b, is.a.d))
                     .a(cet.b, gc.c, ir.a().a(is.a, is.a.b))
                     .a(cet.b, gc.f, ir.a().a(is.a, is.a.b).a(is.b, is.a.b))
                     .a(cet.b, gc.d, ir.a().a(is.a, is.a.b).a(is.b, is.a.c))
                     .a(cet.b, gc.e, ir.a().a(is.a, is.a.b).a(is.b, is.a.d))
               )
         );
   }

   private void M() {
      this.b(bup.dU);
      this.a.accept(d(bup.dU, iw.a(bup.dU)));
   }

   private void N() {
      this.a.accept(io.a(bup.cT).a(ip.a(cex.E).a(gc.a.a, ir.a().a(is.c, iw.a(bup.cT, "_ns"))).a(gc.a.c, ir.a().a(is.c, iw.a(bup.cT, "_ew")))));
   }

   private void O() {
      vk _snowman = jb.a.a(bup.cL, this.b);
      this.a
         .accept(
            io.a(
               bup.cL,
               ir.a().a(is.c, _snowman),
               ir.a().a(is.c, _snowman).a(is.a, is.a.b),
               ir.a().a(is.c, _snowman).a(is.a, is.a.c),
               ir.a().a(is.c, _snowman).a(is.a, is.a.d),
               ir.a().a(is.c, _snowman).a(is.b, is.a.b),
               ir.a().a(is.c, _snowman).a(is.b, is.a.b).a(is.a, is.a.b),
               ir.a().a(is.c, _snowman).a(is.b, is.a.b).a(is.a, is.a.c),
               ir.a().a(is.c, _snowman).a(is.b, is.a.b).a(is.a, is.a.d),
               ir.a().a(is.c, _snowman).a(is.b, is.a.c),
               ir.a().a(is.c, _snowman).a(is.b, is.a.c).a(is.a, is.a.b),
               ir.a().a(is.c, _snowman).a(is.b, is.a.c).a(is.a, is.a.c),
               ir.a().a(is.c, _snowman).a(is.b, is.a.c).a(is.a, is.a.d),
               ir.a().a(is.c, _snowman).a(is.b, is.a.d),
               ir.a().a(is.c, _snowman).a(is.b, is.a.d).a(is.a, is.a.b),
               ir.a().a(is.c, _snowman).a(is.b, is.a.d).a(is.a, is.a.c),
               ir.a().a(is.c, _snowman).a(is.b, is.a.d).a(is.a, is.a.d)
            )
         );
   }

   private void P() {
      vk _snowman = iw.a(bup.iO);
      vk _snowmanx = iw.a(bup.iO, "_on");
      this.a.accept(io.a(bup.iO).a(a(cex.w, _snowmanx, _snowman)).a(e()));
   }

   private void Q() {
      iz _snowman = new iz().a(ja.e, iz.a(bup.aW, "_bottom")).a(ja.i, iz.a(bup.aW, "_side"));
      vk _snowmanx = iz.a(bup.aW, "_top_sticky");
      vk _snowmanxx = iz.a(bup.aW, "_top");
      iz _snowmanxxx = _snowman.c(ja.E, _snowmanx);
      iz _snowmanxxxx = _snowman.c(ja.E, _snowmanxx);
      vk _snowmanxxxxx = iw.a(bup.aW, "_base");
      this.a(bup.aW, _snowmanxxxxx, _snowmanxxxx);
      this.a(bup.aP, _snowmanxxxxx, _snowmanxxx);
      vk _snowmanxxxxxx = iy.h.a(bup.aW, "_inventory", _snowman.c(ja.f, _snowmanxx), this.b);
      vk _snowmanxxxxxxx = iy.h.a(bup.aP, "_inventory", _snowman.c(ja.f, _snowmanx), this.b);
      this.c(bup.aW, _snowmanxxxxxx);
      this.c(bup.aP, _snowmanxxxxxxx);
   }

   private void a(buo var1, vk var2, iz var3) {
      vk _snowman = iy.aB.a(_snowman, _snowman, this.b);
      this.a.accept(io.a(_snowman).a(a(cex.g, _snowman, _snowman)).a(e()));
   }

   private void R() {
      iz _snowman = new iz().a(ja.F, iz.a(bup.aW, "_top")).a(ja.i, iz.a(bup.aW, "_side"));
      iz _snowmanx = _snowman.c(ja.E, iz.a(bup.aW, "_top_sticky"));
      iz _snowmanxx = _snowman.c(ja.E, iz.a(bup.aW, "_top"));
      this.a
         .accept(
            io.a(bup.aX)
               .a(
                  ip.a(cex.x, cex.aJ)
                     .a(false, cfi.a, ir.a().a(is.c, iy.aC.a(bup.aW, "_head", _snowmanxx, this.b)))
                     .a(false, cfi.b, ir.a().a(is.c, iy.aC.a(bup.aW, "_head_sticky", _snowmanx, this.b)))
                     .a(true, cfi.a, ir.a().a(is.c, iy.aD.a(bup.aW, "_head_short", _snowmanxx, this.b)))
                     .a(true, cfi.b, ir.a().a(is.c, iy.aD.a(bup.aW, "_head_short_sticky", _snowmanx, this.b)))
               )
               .a(e())
         );
   }

   private void S() {
      vk _snowman = iw.a(bup.lQ, "_stable");
      vk _snowmanx = iw.a(bup.lQ, "_unstable");
      this.c(bup.lQ, _snowman);
      this.a.accept(io.a(bup.lQ).a(a(cex.b, _snowmanx, _snowman)));
   }

   private void T() {
      vk _snowman = jb.a.a(bup.eg, this.b);
      vk _snowmanx = this.a(bup.eg, "_on", iy.c, iz::b);
      this.a.accept(io.a(bup.eg).a(a(cex.r, _snowmanx, _snowman)));
   }

   private void j(buo var1, buo var2) {
      iz _snowman = iz.u(_snowman);
      this.a.accept(e(_snowman, iy.az.a(_snowman, _snowman, this.b)));
      this.a.accept(io.a(_snowman, ir.a().a(is.c, iy.aA.a(_snowman, _snowman, this.b))).a(d()));
      this.b(_snowman);
      this.a(_snowman);
   }

   private void U() {
      iz _snowman = iz.u(bup.cz);
      iz _snowmanx = iz.i(iz.a(bup.cz, "_off"));
      vk _snowmanxx = iy.az.a(bup.cz, _snowman, this.b);
      vk _snowmanxxx = iy.az.a(bup.cz, "_off", _snowmanx, this.b);
      this.a.accept(io.a(bup.cz).a(a(cex.r, _snowmanxx, _snowmanxxx)));
      vk _snowmanxxxx = iy.aA.a(bup.cA, _snowman, this.b);
      vk _snowmanxxxxx = iy.aA.a(bup.cA, "_off", _snowmanx, this.b);
      this.a.accept(io.a(bup.cA).a(a(cex.r, _snowmanxxxx, _snowmanxxxxx)).a(d()));
      this.b(bup.cz);
      this.a(bup.cA);
   }

   private void V() {
      this.a(bmd.jU);
      this.a.accept(io.a(bup.cX).a(ip.a(cex.am, cex.s, cex.w).a((var0, var1, var2) -> {
         StringBuilder _snowman = new StringBuilder();
         _snowman.append('_').append(var0).append("tick");
         if (var2) {
            _snowman.append("_on");
         }

         if (var1) {
            _snowman.append("_locked");
         }

         return ir.a().a(is.c, iz.a(bup.cX, _snowman.toString()));
      })).a(c()));
   }

   private void W() {
      this.a(bmd.aP);
      this.a
         .accept(
            io.a(bup.kU)
               .a(
                  ip.a(cex.ay, cex.C)
                     .a(1, false, Arrays.asList(a(iw.a("dead_sea_pickle"))))
                     .a(2, false, Arrays.asList(a(iw.a("two_dead_sea_pickles"))))
                     .a(3, false, Arrays.asList(a(iw.a("three_dead_sea_pickles"))))
                     .a(4, false, Arrays.asList(a(iw.a("four_dead_sea_pickles"))))
                     .a(1, true, Arrays.asList(a(iw.a("sea_pickle"))))
                     .a(2, true, Arrays.asList(a(iw.a("two_sea_pickles"))))
                     .a(3, true, Arrays.asList(a(iw.a("three_sea_pickles"))))
                     .a(4, true, Arrays.asList(a(iw.a("four_sea_pickles"))))
               )
         );
   }

   private void X() {
      iz _snowman = iz.a(bup.cC);
      vk _snowmanx = iy.c.a(bup.cE, _snowman, this.b);
      this.a.accept(io.a(bup.cC).a(ip.a(cex.aq).a(var1x -> ir.a().a(is.c, var1x < 8 ? iw.a(bup.cC, "_height" + var1x * 2) : _snowman))));
      this.c(bup.cC, iw.a(bup.cC, "_height2"));
      this.a.accept(e(bup.cE, _snowmanx));
   }

   private void Y() {
      this.a.accept(io.a(bup.ma, ir.a().a(is.c, iw.a(bup.ma))).a(b()));
   }

   private void Z() {
      vk _snowman = jb.a.a(bup.mY, this.b);
      this.c(bup.mY, _snowman);
      this.a.accept(io.a(bup.mY).a(ip.a(cex.aM).a(var1x -> ir.a().a(is.c, this.a(bup.mY, "_" + var1x.a(), iy.c, iz::b)))));
   }

   private void aa() {
      this.a(bmd.rm);
      this.a.accept(io.a(bup.mg).a(ip.a(cex.ag).a(var1 -> ir.a().a(is.c, this.a(bup.mg, "_stage" + var1, iy.S, iz::c)))));
   }

   private void ab() {
      this.a(bmd.kS);
      this.a
         .accept(
            io.a(bup.em)
               .a(
                  ip.a(cex.a, cex.J, cex.I, cex.K, cex.L)
                     .a(false, false, false, false, false, ir.a().a(is.c, iw.a(bup.em, "_ns")))
                     .a(false, true, false, false, false, ir.a().a(is.c, iw.a(bup.em, "_n")).a(is.b, is.a.b))
                     .a(false, false, true, false, false, ir.a().a(is.c, iw.a(bup.em, "_n")))
                     .a(false, false, false, true, false, ir.a().a(is.c, iw.a(bup.em, "_n")).a(is.b, is.a.c))
                     .a(false, false, false, false, true, ir.a().a(is.c, iw.a(bup.em, "_n")).a(is.b, is.a.d))
                     .a(false, true, true, false, false, ir.a().a(is.c, iw.a(bup.em, "_ne")))
                     .a(false, true, false, true, false, ir.a().a(is.c, iw.a(bup.em, "_ne")).a(is.b, is.a.b))
                     .a(false, false, false, true, true, ir.a().a(is.c, iw.a(bup.em, "_ne")).a(is.b, is.a.c))
                     .a(false, false, true, false, true, ir.a().a(is.c, iw.a(bup.em, "_ne")).a(is.b, is.a.d))
                     .a(false, false, true, true, false, ir.a().a(is.c, iw.a(bup.em, "_ns")))
                     .a(false, true, false, false, true, ir.a().a(is.c, iw.a(bup.em, "_ns")).a(is.b, is.a.b))
                     .a(false, true, true, true, false, ir.a().a(is.c, iw.a(bup.em, "_nse")))
                     .a(false, true, false, true, true, ir.a().a(is.c, iw.a(bup.em, "_nse")).a(is.b, is.a.b))
                     .a(false, false, true, true, true, ir.a().a(is.c, iw.a(bup.em, "_nse")).a(is.b, is.a.c))
                     .a(false, true, true, false, true, ir.a().a(is.c, iw.a(bup.em, "_nse")).a(is.b, is.a.d))
                     .a(false, true, true, true, true, ir.a().a(is.c, iw.a(bup.em, "_nsew")))
                     .a(true, false, false, false, false, ir.a().a(is.c, iw.a(bup.em, "_attached_ns")))
                     .a(true, false, true, false, false, ir.a().a(is.c, iw.a(bup.em, "_attached_n")))
                     .a(true, false, false, true, false, ir.a().a(is.c, iw.a(bup.em, "_attached_n")).a(is.b, is.a.c))
                     .a(true, true, false, false, false, ir.a().a(is.c, iw.a(bup.em, "_attached_n")).a(is.b, is.a.b))
                     .a(true, false, false, false, true, ir.a().a(is.c, iw.a(bup.em, "_attached_n")).a(is.b, is.a.d))
                     .a(true, true, true, false, false, ir.a().a(is.c, iw.a(bup.em, "_attached_ne")))
                     .a(true, true, false, true, false, ir.a().a(is.c, iw.a(bup.em, "_attached_ne")).a(is.b, is.a.b))
                     .a(true, false, false, true, true, ir.a().a(is.c, iw.a(bup.em, "_attached_ne")).a(is.b, is.a.c))
                     .a(true, false, true, false, true, ir.a().a(is.c, iw.a(bup.em, "_attached_ne")).a(is.b, is.a.d))
                     .a(true, false, true, true, false, ir.a().a(is.c, iw.a(bup.em, "_attached_ns")))
                     .a(true, true, false, false, true, ir.a().a(is.c, iw.a(bup.em, "_attached_ns")).a(is.b, is.a.b))
                     .a(true, true, true, true, false, ir.a().a(is.c, iw.a(bup.em, "_attached_nse")))
                     .a(true, true, false, true, true, ir.a().a(is.c, iw.a(bup.em, "_attached_nse")).a(is.b, is.a.b))
                     .a(true, false, true, true, true, ir.a().a(is.c, iw.a(bup.em, "_attached_nse")).a(is.b, is.a.c))
                     .a(true, true, true, false, true, ir.a().a(is.c, iw.a(bup.em, "_attached_nse")).a(is.b, is.a.d))
                     .a(true, true, true, true, true, ir.a().a(is.c, iw.a(bup.em, "_attached_nsew")))
               )
         );
   }

   private void ac() {
      this.b(bup.el);
      this.a.accept(io.a(bup.el).a(ip.a(cex.a, cex.w).a((var0, var1) -> ir.a().a(is.c, iz.a(bup.el, (var0 ? "_attached" : "") + (var1 ? "_on" : ""))))).a(b()));
   }

   private vk a(int var1, String var2, iz var3) {
      switch (_snowman) {
         case 1:
            return iy.aF.a(iw.a(_snowman + "turtle_egg"), _snowman, this.b);
         case 2:
            return iy.aG.a(iw.a("two_" + _snowman + "turtle_eggs"), _snowman, this.b);
         case 3:
            return iy.aH.a(iw.a("three_" + _snowman + "turtle_eggs"), _snowman, this.b);
         case 4:
            return iy.aI.a(iw.a("four_" + _snowman + "turtle_eggs"), _snowman, this.b);
         default:
            throw new UnsupportedOperationException();
      }
   }

   private vk a(Integer var1, Integer var2) {
      switch (_snowman) {
         case 0:
            return this.a(_snowman.intValue(), "", iz.b(iz.C(bup.kf)));
         case 1:
            return this.a(_snowman.intValue(), "slightly_cracked_", iz.b(iz.a(bup.kf, "_slightly_cracked")));
         case 2:
            return this.a(_snowman.intValue(), "very_cracked_", iz.b(iz.a(bup.kf, "_very_cracked")));
         default:
            throw new UnsupportedOperationException();
      }
   }

   private void ad() {
      this.a(bmd.iC);
      this.a.accept(io.a(bup.kf).a(ip.a(cex.ao, cex.ap).b((var1, var2) -> Arrays.asList(a(this.a(var1, var2))))));
   }

   private void ae() {
      this.b(bup.dP);
      this.a
         .accept(
            io.a(bup.dP)
               .a(
                  ip.a(cex.J, cex.I, cex.K, cex.G, cex.L)
                     .a(false, false, false, false, false, ir.a().a(is.c, iw.a(bup.dP, "_1")))
                     .a(false, false, true, false, false, ir.a().a(is.c, iw.a(bup.dP, "_1")))
                     .a(false, false, false, false, true, ir.a().a(is.c, iw.a(bup.dP, "_1")).a(is.b, is.a.b))
                     .a(false, true, false, false, false, ir.a().a(is.c, iw.a(bup.dP, "_1")).a(is.b, is.a.c))
                     .a(true, false, false, false, false, ir.a().a(is.c, iw.a(bup.dP, "_1")).a(is.b, is.a.d))
                     .a(true, true, false, false, false, ir.a().a(is.c, iw.a(bup.dP, "_2")))
                     .a(true, false, true, false, false, ir.a().a(is.c, iw.a(bup.dP, "_2")).a(is.b, is.a.b))
                     .a(false, false, true, false, true, ir.a().a(is.c, iw.a(bup.dP, "_2")).a(is.b, is.a.c))
                     .a(false, true, false, false, true, ir.a().a(is.c, iw.a(bup.dP, "_2")).a(is.b, is.a.d))
                     .a(true, false, false, false, true, ir.a().a(is.c, iw.a(bup.dP, "_2_opposite")))
                     .a(false, true, true, false, false, ir.a().a(is.c, iw.a(bup.dP, "_2_opposite")).a(is.b, is.a.b))
                     .a(true, true, true, false, false, ir.a().a(is.c, iw.a(bup.dP, "_3")))
                     .a(true, false, true, false, true, ir.a().a(is.c, iw.a(bup.dP, "_3")).a(is.b, is.a.b))
                     .a(false, true, true, false, true, ir.a().a(is.c, iw.a(bup.dP, "_3")).a(is.b, is.a.c))
                     .a(true, true, false, false, true, ir.a().a(is.c, iw.a(bup.dP, "_3")).a(is.b, is.a.d))
                     .a(true, true, true, false, true, ir.a().a(is.c, iw.a(bup.dP, "_4")))
                     .a(false, false, false, true, false, ir.a().a(is.c, iw.a(bup.dP, "_u")))
                     .a(false, false, true, true, false, ir.a().a(is.c, iw.a(bup.dP, "_1u")))
                     .a(false, false, false, true, true, ir.a().a(is.c, iw.a(bup.dP, "_1u")).a(is.b, is.a.b))
                     .a(false, true, false, true, false, ir.a().a(is.c, iw.a(bup.dP, "_1u")).a(is.b, is.a.c))
                     .a(true, false, false, true, false, ir.a().a(is.c, iw.a(bup.dP, "_1u")).a(is.b, is.a.d))
                     .a(true, true, false, true, false, ir.a().a(is.c, iw.a(bup.dP, "_2u")))
                     .a(true, false, true, true, false, ir.a().a(is.c, iw.a(bup.dP, "_2u")).a(is.b, is.a.b))
                     .a(false, false, true, true, true, ir.a().a(is.c, iw.a(bup.dP, "_2u")).a(is.b, is.a.c))
                     .a(false, true, false, true, true, ir.a().a(is.c, iw.a(bup.dP, "_2u")).a(is.b, is.a.d))
                     .a(true, false, false, true, true, ir.a().a(is.c, iw.a(bup.dP, "_2u_opposite")))
                     .a(false, true, true, true, false, ir.a().a(is.c, iw.a(bup.dP, "_2u_opposite")).a(is.b, is.a.b))
                     .a(true, true, true, true, false, ir.a().a(is.c, iw.a(bup.dP, "_3u")))
                     .a(true, false, true, true, true, ir.a().a(is.c, iw.a(bup.dP, "_3u")).a(is.b, is.a.b))
                     .a(false, true, true, true, true, ir.a().a(is.c, iw.a(bup.dP, "_3u")).a(is.b, is.a.c))
                     .a(true, true, false, true, true, ir.a().a(is.c, iw.a(bup.dP, "_3u")).a(is.b, is.a.d))
                     .a(true, true, true, true, true, ir.a().a(is.c, iw.a(bup.dP, "_4u")))
               )
         );
   }

   private void af() {
      this.a.accept(e(bup.iJ, iy.c.a(bup.iJ, iz.b(iw.a("magma")), this.b)));
   }

   private void y(buo var1) {
      this.c(_snowman, jb.l);
      iy.aN.a(iw.a(_snowman.h()), iz.q(_snowman), this.b);
   }

   private void b(buo var1, buo var2, ii.c var3) {
      this.b(_snowman, _snowman);
      this.b(_snowman, _snowman);
   }

   private void k(buo var1, buo var2) {
      iy.aO.a(iw.a(_snowman.h()), iz.q(_snowman), this.b);
   }

   private void ag() {
      vk _snowman = iw.a(bup.b);
      vk _snowmanx = iw.a(bup.b, "_mirrored");
      this.a.accept(e(bup.dy, _snowman, _snowmanx));
      this.c(bup.dy, _snowman);
   }

   private void l(buo var1, buo var2) {
      this.a(_snowman, ii.c.b);
      iz _snowman = iz.d(iz.a(_snowman, "_pot"));
      vk _snowmanx = ii.c.b.b().a(_snowman, _snowman, this.b);
      this.a.accept(e(_snowman, _snowmanx));
   }

   private void ah() {
      vk _snowman = iz.a(bup.nj, "_bottom");
      vk _snowmanx = iz.a(bup.nj, "_top_off");
      vk _snowmanxx = iz.a(bup.nj, "_top");
      vk[] _snowmanxxx = new vk[5];

      for (int _snowmanxxxx = 0; _snowmanxxxx < 5; _snowmanxxxx++) {
         iz _snowmanxxxxx = new iz().a(ja.e, _snowman).a(ja.f, _snowmanxxxx == 0 ? _snowmanx : _snowmanxx).a(ja.i, iz.a(bup.nj, "_side" + _snowmanxxxx));
         _snowmanxxx[_snowmanxxxx] = iy.h.a(bup.nj, "_" + _snowmanxxxx, _snowmanxxxxx, this.b);
      }

      this.a.accept(io.a(bup.nj).a(ip.a(cex.aC).a(var1x -> ir.a().a(is.c, _snowman[var1x]))));
      this.a(bmd.rN, _snowmanxxx[0]);
   }

   private ir a(ge var1, ir var2) {
      switch (_snowman) {
         case b:
            return _snowman.a(is.a, is.a.b);
         case c:
            return _snowman.a(is.a, is.a.b).a(is.b, is.a.c);
         case d:
            return _snowman.a(is.a, is.a.b).a(is.b, is.a.d);
         case a:
            return _snowman.a(is.a, is.a.b).a(is.b, is.a.b);
         case f:
            return _snowman.a(is.a, is.a.d).a(is.b, is.a.c);
         case g:
            return _snowman.a(is.a, is.a.d);
         case h:
            return _snowman.a(is.a, is.a.d).a(is.b, is.a.b);
         case e:
            return _snowman.a(is.a, is.a.d).a(is.b, is.a.d);
         case k:
            return _snowman;
         case l:
            return _snowman.a(is.b, is.a.c);
         case i:
            return _snowman.a(is.b, is.a.d);
         case j:
            return _snowman.a(is.b, is.a.b);
         default:
            throw new UnsupportedOperationException("Rotation " + _snowman + " can't be expressed with existing x and y values");
      }
   }

   private void ai() {
      vk _snowman = iz.a(bup.mZ, "_top");
      vk _snowmanx = iz.a(bup.mZ, "_bottom");
      vk _snowmanxx = iz.a(bup.mZ, "_side");
      vk _snowmanxxx = iz.a(bup.mZ, "_lock");
      iz _snowmanxxxx = new iz().a(ja.o, _snowmanxx).a(ja.m, _snowmanxx).a(ja.l, _snowmanxx).a(ja.c, _snowman).a(ja.j, _snowman).a(ja.k, _snowmanx).a(ja.n, _snowmanxxx);
      vk _snowmanxxxxx = iy.b.a(bup.mZ, _snowmanxxxx, this.b);
      this.a.accept(io.a(bup.mZ, ir.a().a(is.c, _snowmanxxxxx)).a(ip.a(cex.P).a(var1x -> this.a(var1x, ir.a()))));
   }

   public void a() {
      this.k(bup.a);
      this.a(bup.lb, bup.a);
      this.a(bup.la, bup.a);
      this.k(bup.es);
      this.k(bup.cF);
      this.a(bup.lc, bup.A);
      this.k(bup.ef);
      this.k(bup.ke);
      this.k(bup.dZ);
      this.k(bup.ev);
      this.a(bmd.oX);
      this.k(bup.ne);
      this.k(bup.A);
      this.k(bup.B);
      this.k(bup.gn);
      this.a(bmd.dO);
      this.k(bup.kZ);
      this.k(bup.eT);
      this.a(bup.go, bmd.fJ);
      this.a(bmd.fJ);
      this.a(bup.iN, bmd.hn);
      this.a(bmd.hn);
      this.h(bup.bo, iz.a(bup.aW, "_side"));
      this.c(bup.H, jb.a);
      this.c(bup.gS, jb.a);
      this.c(bup.bT, jb.a);
      this.c(bup.bU, jb.a);
      this.c(bup.ej, jb.a);
      this.c(bup.en, jb.a);
      this.c(bup.F, jb.a);
      this.c(bup.I, jb.a);
      this.c(bup.bE, jb.a);
      this.c(bup.G, jb.a);
      this.c(bup.bF, jb.a);
      this.c(bup.nh, jb.c);
      this.c(bup.ng, jb.a);
      this.c(bup.aq, jb.a);
      this.c(bup.ar, jb.a);
      this.c(bup.fx, jb.a);
      this.c(bup.cy, jb.a);
      this.c(bup.fw, jb.a);
      this.c(bup.nA, jb.a);
      this.c(bup.kV, jb.a);
      this.c(bup.nG, jb.a);
      this.c(bup.cG, jb.a);
      this.c(bup.k, jb.a);
      this.c(bup.nH, jb.a);
      this.c(bup.dw, jb.a);
      this.c(bup.ni, jb.a);
      this.c(bup.ee, jb.a);
      this.c(bup.cS, jb.a);
      this.c(bup.E, jb.a);
      this.c(bup.nf, jb.a);
      this.c(bup.cD, jb.a);
      this.c(bup.cI, jb.f);
      this.c(bup.no, jb.c);
      this.c(bup.dK, jb.c);
      this.c(bup.iK, jb.a);
      this.c(bup.aw, jb.a);
      this.c(bup.gT, jb.a);
      this.c(bup.bK, jb.a);
      this.c(bup.nI, jb.a);
      this.c(bup.gz, jb.a);
      this.c(bup.mw, jb.a);
      this.c(bup.cM, jb.a);
      this.c(bup.cN, jb.a);
      this.c(bup.bP, jb.a);
      this.c(bup.an, jb.a);
      this.c(bup.aU, jb.q);
      this.a(bmd.aO);
      this.c(bup.bH, jb.e);
      this.c(bup.nb, jb.c);
      this.c(bup.mn, jb.a);
      this.c(bup.ao, jb.a);
      this.c(bup.nv, jb.a);
      this.c(bup.fA, jb.c.a(var0 -> var0.a(ja.i, iz.C(bup.fA))));
      this.c(bup.dx, jb.a);
      this.g(bup.au, bup.at);
      this.g(bup.hH, bup.hG);
      this.c(bup.nw, jb.a);
      this.h(bup.fs, bup.bE);
      this.h(bup.ft, bup.bF);
      this.n();
      this.r();
      this.s();
      this.a(bup.me, bup.mf);
      this.t();
      this.w();
      this.x();
      this.z();
      this.A();
      this.B();
      this.y();
      this.s(bup.iw);
      this.C();
      this.D();
      this.E();
      this.F();
      this.G();
      this.H();
      this.I();
      this.m();
      this.J();
      this.K();
      this.L();
      this.M();
      this.N();
      this.O();
      this.P();
      this.Q();
      this.R();
      this.S();
      this.U();
      this.T();
      this.V();
      this.W();
      this.u();
      this.X();
      this.Y();
      this.Z();
      this.aa();
      this.ab();
      this.ac();
      this.ad();
      this.ae();
      this.af();
      this.ai();
      this.x(bup.cg);
      this.b(bup.cg);
      this.x(bup.lY);
      this.j(bup.bL, bup.bM);
      this.j(bup.cQ, bup.cR);
      this.a(bup.bV, bup.n, iz::c);
      this.a(bup.lW, bup.p, iz::d);
      this.r(bup.mu);
      this.r(bup.ml);
      this.q(bup.as);
      this.q(bup.fE);
      this.w(bup.mc);
      this.w(bup.md);
      this.g(bup.dI, iw.a(bup.dI));
      this.a(bup.cO, jb.c);
      this.a(bup.cP, jb.c);
      this.a(bup.iM, jb.c);
      this.d(bup.j);
      this.d(bup.C);
      this.d(bup.D);
      this.c(bup.z);
      this.a(bup.gA, jb.c, jb.d);
      this.a(bup.iA, jb.r, jb.s);
      this.a(bup.fB, jb.r, jb.s);
      this.b(bup.lR, jb.h);
      this.v();
      this.a(bup.nc, iz::w);
      this.a(bup.nd, iz::y);
      this.a(bup.iD, cex.ag, 0, 1, 2, 3);
      this.a(bup.eU, cex.ai, 0, 0, 1, 1, 2, 2, 2, 3);
      this.a(bup.dY, cex.ag, 0, 1, 1, 2);
      this.a(bup.eV, cex.ai, 0, 0, 1, 1, 2, 2, 2, 3);
      this.a(bup.bW, cex.ai, 0, 1, 2, 3, 4, 5, 6, 7);
      this.a(iw.a("banner"), bup.n)
         .a(iy.aP, bup.ha, bup.hb, bup.hc, bup.hd, bup.he, bup.hf, bup.hg, bup.hh, bup.hi, bup.hj, bup.hk, bup.hl, bup.hm, bup.hn, bup.ho, bup.hp)
         .b(bup.hq, bup.hr, bup.hs, bup.ht, bup.hu, bup.hv, bup.hw, bup.hx, bup.hy, bup.hz, bup.hA, bup.hB, bup.hC, bup.hD, bup.hE, bup.hF);
      this.a(iw.a("bed"), bup.n)
         .b(bup.ax, bup.ay, bup.az, bup.aA, bup.aB, bup.aC, bup.aD, bup.aE, bup.aF, bup.aG, bup.aH, bup.aI, bup.aJ, bup.aK, bup.aL, bup.aM);
      this.k(bup.ax, bup.aY);
      this.k(bup.ay, bup.aZ);
      this.k(bup.az, bup.ba);
      this.k(bup.aA, bup.bb);
      this.k(bup.aB, bup.bc);
      this.k(bup.aC, bup.bd);
      this.k(bup.aD, bup.be);
      this.k(bup.aE, bup.bf);
      this.k(bup.aF, bup.bg);
      this.k(bup.aG, bup.bh);
      this.k(bup.aH, bup.bi);
      this.k(bup.aI, bup.bj);
      this.k(bup.aJ, bup.bk);
      this.k(bup.aK, bup.bl);
      this.k(bup.aL, bup.bm);
      this.k(bup.aM, bup.bn);
      this.a(iw.a("skull"), bup.cM).a(iy.aQ, bup.fk, bup.fi, bup.fg, bup.fc, bup.fe).a(bup.fm).b(bup.fl, bup.fn, bup.fj, bup.fh, bup.fd, bup.ff);
      this.y(bup.iP);
      this.y(bup.iQ);
      this.y(bup.iR);
      this.y(bup.iS);
      this.y(bup.iT);
      this.y(bup.iU);
      this.y(bup.iV);
      this.y(bup.iW);
      this.y(bup.iX);
      this.y(bup.iY);
      this.y(bup.iZ);
      this.y(bup.ja);
      this.y(bup.jb);
      this.y(bup.jc);
      this.y(bup.jd);
      this.y(bup.je);
      this.y(bup.jf);
      this.c(bup.kW, jb.l);
      this.a(bup.kW);
      this.a(iw.a("chest"), bup.n).b(bup.bR, bup.fr);
      this.a(iw.a("ender_chest"), bup.bK).b(bup.ek);
      this.d(bup.ec, bup.bK).a(bup.ec, bup.iF);
      this.e(bup.jw);
      this.e(bup.jx);
      this.e(bup.jy);
      this.e(bup.jz);
      this.e(bup.jA);
      this.e(bup.jB);
      this.e(bup.jC);
      this.e(bup.jD);
      this.e(bup.jE);
      this.e(bup.jF);
      this.e(bup.jG);
      this.e(bup.jH);
      this.e(bup.jI);
      this.e(bup.jJ);
      this.e(bup.jK);
      this.e(bup.jL);
      this.a(jb.a, bup.jM, bup.jN, bup.jO, bup.jP, bup.jQ, bup.jR, bup.jS, bup.jT, bup.jU, bup.jV, bup.jW, bup.jX, bup.jY, bup.jZ, bup.ka, bup.kb);
      this.e(bup.gR);
      this.e(bup.fF);
      this.e(bup.fG);
      this.e(bup.fH);
      this.e(bup.fI);
      this.e(bup.fJ);
      this.e(bup.fK);
      this.e(bup.fL);
      this.e(bup.fM);
      this.e(bup.fN);
      this.e(bup.fO);
      this.e(bup.fP);
      this.e(bup.fQ);
      this.e(bup.fR);
      this.e(bup.fS);
      this.e(bup.fT);
      this.e(bup.fU);
      this.f(bup.ap, bup.dJ);
      this.f(bup.cY, bup.fV);
      this.f(bup.cZ, bup.fW);
      this.f(bup.da, bup.fX);
      this.f(bup.db, bup.fY);
      this.f(bup.dc, bup.fZ);
      this.f(bup.dd, bup.ga);
      this.f(bup.de, bup.gb);
      this.f(bup.df, bup.gc);
      this.f(bup.dg, bup.gd);
      this.f(bup.dh, bup.ge);
      this.f(bup.di, bup.gf);
      this.f(bup.dj, bup.gg);
      this.f(bup.dk, bup.gh);
      this.f(bup.dl, bup.gi);
      this.f(bup.dm, bup.gj);
      this.f(bup.dn, bup.gk);
      this.b(jb.j, bup.jg, bup.jh, bup.ji, bup.jj, bup.jk, bup.jl, bup.jm, bup.jn, bup.jo, bup.jp, bup.jq, bup.jr, bup.js, bup.jt, bup.ju, bup.jv);
      this.e(bup.aY, bup.gB);
      this.e(bup.aZ, bup.gC);
      this.e(bup.ba, bup.gD);
      this.e(bup.bb, bup.gE);
      this.e(bup.bc, bup.gF);
      this.e(bup.bd, bup.gG);
      this.e(bup.be, bup.gH);
      this.e(bup.bf, bup.gI);
      this.e(bup.bg, bup.gJ);
      this.e(bup.bh, bup.gK);
      this.e(bup.bi, bup.gL);
      this.e(bup.bj, bup.gM);
      this.e(bup.bk, bup.gN);
      this.e(bup.bl, bup.gO);
      this.e(bup.bm, bup.gP);
      this.e(bup.bn, bup.gQ);
      this.a(bup.aS, bup.eC, ii.c.a);
      this.a(bup.bp, bup.eD, ii.c.b);
      this.a(bup.bq, bup.eE, ii.c.b);
      this.a(bup.br, bup.eF, ii.c.b);
      this.a(bup.bs, bup.eG, ii.c.b);
      this.a(bup.bt, bup.eH, ii.c.b);
      this.a(bup.bu, bup.eI, ii.c.b);
      this.a(bup.bv, bup.eJ, ii.c.b);
      this.a(bup.bw, bup.eK, ii.c.b);
      this.a(bup.bx, bup.eL, ii.c.b);
      this.a(bup.by, bup.eM, ii.c.b);
      this.a(bup.bz, bup.eN, ii.c.b);
      this.a(bup.bB, bup.eO, ii.c.b);
      this.a(bup.bA, bup.eP, ii.c.b);
      this.a(bup.bD, bup.eQ, ii.c.b);
      this.a(bup.bC, bup.eR, ii.c.b);
      this.a(bup.aT, bup.eS, ii.c.b);
      this.p(bup.dE);
      this.p(bup.dF);
      this.p(bup.dG);
      this.a(bup.aR, ii.c.a);
      this.b(bup.cH, ii.c.a);
      this.a(bmd.bD);
      this.b(bup.kc, bup.kd, ii.c.a);
      this.a(bmd.bE);
      this.a(bup.kd);
      this.b(bup.mx, bup.my, ii.c.b);
      this.b(bup.mz, bup.mA, ii.c.b);
      this.a(bup.mx, "_plant");
      this.a(bup.my);
      this.a(bup.mz, "_plant");
      this.a(bup.mA);
      this.a(bup.kX, ii.c.a, iz.c(iz.a(bup.kY, "_stage0")));
      this.i();
      this.a(bup.aQ, ii.c.b);
      this.c(bup.gV, ii.c.b);
      this.c(bup.gW, ii.c.b);
      this.c(bup.gX, ii.c.b);
      this.c(bup.gY, ii.c.a);
      this.c(bup.gZ, ii.c.a);
      this.g();
      this.h();
      this.a(bup.kv, bup.kq, bup.kl, bup.kg, bup.kF, bup.kA, bup.kP, bup.kK);
      this.a(bup.kw, bup.kr, bup.km, bup.kh, bup.kG, bup.kB, bup.kQ, bup.kL);
      this.a(bup.kx, bup.ks, bup.kn, bup.ki, bup.kH, bup.kC, bup.kR, bup.kM);
      this.a(bup.ky, bup.kt, bup.ko, bup.kj, bup.kI, bup.kD, bup.kS, bup.kN);
      this.a(bup.kz, bup.ku, bup.kp, bup.kk, bup.kJ, bup.kE, bup.kT, bup.kO);
      this.c(bup.dO, bup.dM);
      this.c(bup.dN, bup.dL);
      this.f(bup.r).a(bup.fa).c(bup.ip).d(bup.ik).e(bup.cw).a(bup.cc, bup.cm).f(bup.hO).g(bup.gl);
      this.g(bup.iu);
      this.h(bup.ds);
      this.j(bup.N).c(bup.N).a(bup.Z);
      this.j(bup.S).c(bup.S).a(bup.af);
      this.a(bup.x, bup.eA, ii.c.b);
      this.c(bup.al, jb.n);
      this.f(bup.p).a(bup.eY).c(bup.in).d(bup.ii).e(bup.cu).a(bup.cb, bup.cl).f(bup.hM).g(bup.ep);
      this.g(bup.is);
      this.h(bup.dq);
      this.j(bup.L).c(bup.L).a(bup.X);
      this.j(bup.Q).c(bup.Q).a(bup.ad);
      this.a(bup.v, bup.ey, ii.c.b);
      this.c(bup.aj, jb.n);
      this.f(bup.n).a(bup.eW).c(bup.cJ).d(bup.dQ).e(bup.cs).a(bup.bZ, bup.cj).f(bup.hK).f(bup.hU).g(bup.bQ);
      this.g(bup.cf);
      this.i(bup.do_);
      this.j(bup.J).c(bup.J).a(bup.V);
      this.j(bup.U).c(bup.U).a(bup.ab);
      this.a(bup.t, bup.ew, ii.c.b);
      this.c(bup.ah, jb.n);
      this.f(bup.o).a(bup.eX).c(bup.im).d(bup.ih).e(bup.ct).a(bup.ca, bup.ck).f(bup.hL).g(bup.eo);
      this.g(bup.ir);
      this.h(bup.dp);
      this.j(bup.K).c(bup.K).a(bup.W);
      this.j(bup.P).c(bup.P).a(bup.ac);
      this.a(bup.u, bup.ex, ii.c.b);
      this.c(bup.ai, jb.n);
      this.f(bup.s).a(bup.fb).c(bup.iq).d(bup.il).e(bup.cx).a(bup.ce, bup.co).f(bup.hP).g(bup.gm);
      this.g(bup.iv);
      this.i(bup.dt);
      this.j(bup.O).c(bup.O).a(bup.aa);
      this.j(bup.T).c(bup.T).a(bup.ag);
      this.a(bup.y, bup.eB, ii.c.b);
      this.c(bup.am, jb.n);
      this.f(bup.q).a(bup.eZ).c(bup.io).d(bup.ij).e(bup.cv).a(bup.cd, bup.cn).f(bup.hN).g(bup.eq);
      this.g(bup.it);
      this.h(bup.dr);
      this.j(bup.M).c(bup.M).a(bup.Y);
      this.j(bup.R).c(bup.R).a(bup.ae);
      this.a(bup.w, bup.ez, ii.c.b);
      this.c(bup.ak, jb.n);
      this.f(bup.mC).a(bup.mQ).c(bup.mI).d(bup.mM).e(bup.mG).a(bup.mU, bup.mW).f(bup.mE).g(bup.mO);
      this.g(bup.mS);
      this.h(bup.mK);
      this.j(bup.mq).b(bup.mq).a(bup.ms);
      this.j(bup.mr).b(bup.mr).a(bup.mt);
      this.a(bup.mv, bup.nk, ii.c.b);
      this.l(bup.mB, bup.nm);
      this.f(bup.mD).a(bup.mR).c(bup.mJ).d(bup.mN).e(bup.mH).a(bup.mV, bup.mX).f(bup.mF).g(bup.mP);
      this.g(bup.mT);
      this.h(bup.mL);
      this.j(bup.mh).b(bup.mh).a(bup.mj);
      this.j(bup.mi).b(bup.mi).a(bup.mk);
      this.a(bup.mm, bup.nl, ii.c.b);
      this.l(bup.mo, bup.nn);
      this.b(bup.mp, ii.c.b);
      this.a(bmd.bA);
      this.a(iz.a(bup.b)).a(var1 -> {
         vk _snowman = iy.c.a(bup.b, var1, this.b);
         vk _snowmanx = iy.d.a(bup.b, var1, this.b);
         this.a.accept(e(bup.b, _snowman, _snowmanx));
         return _snowman;
      }).f(bup.hQ).e(bup.cq).a(bup.cB).g(bup.lj);
      this.g(bup.cr);
      this.i(bup.gp);
      this.f(bup.du).b(bup.lJ).g(bup.dS).f(bup.hX);
      this.f(bup.dv).b(bup.lH).g(bup.lf).f(bup.lt);
      this.f(bup.m).b(bup.et).g(bup.ci).f(bup.hV);
      this.f(bup.bJ).b(bup.eu).g(bup.lh).f(bup.lv);
      this.f(bup.gq).b(bup.lF).g(bup.gt).f(bup.gw);
      this.f(bup.gr).g(bup.gu).f(bup.gx);
      this.f(bup.gs).g(bup.gv).f(bup.gy);
      this.d(bup.at, jb.t).b(bup.lN).g(bup.ei).f(bup.hS);
      this.a(bup.ie, jb.a(iz.a(bup.at, "_top"))).f(bup.lx).g(bup.lk);
      this.a(bup.av, jb.c.get(bup.at).a(var0 -> var0.a(ja.i, iz.C(bup.av)))).f(bup.hT);
      this.d(bup.hG, jb.t).b(bup.lG).g(bup.hJ).f(bup.ia);
      this.a(bup.ig, jb.a(iz.a(bup.hG, "_top"))).f(bup.ls).g(bup.le);
      this.a(bup.hI, jb.c.get(bup.hG).a(var0 -> var0.a(ja.i, iz.C(bup.hI)))).f(bup.ib);
      this.f(bup.bG).b(bup.lE).g(bup.dR).f(bup.hW);
      this.f(bup.dV).c(bup.dW).b(bup.lK).g(bup.dX).f(bup.hY);
      this.f(bup.iz).g(bup.iB).f(bup.ic);
      this.f(bup.e).b(bup.lP).g(bup.lq).f(bup.lD);
      this.f(bup.f).g(bup.lg).f(bup.lu);
      this.f(bup.c).b(bup.lI).g(bup.lm).f(bup.lz);
      this.f(bup.d).g(bup.ld).f(bup.lr);
      this.f(bup.g).b(bup.lL).g(bup.ln).f(bup.lA);
      this.f(bup.h).g(bup.lp).f(bup.lC);
      this.f(bup.iC).b(bup.lO).g(bup.li).f(bup.lw);
      this.d(bup.fz, jb.c).g(bup.fC).f(bup.hZ);
      this.a(bup.if_, jb.a(iz.a(bup.fz, "_bottom"))).g(bup.ll).f(bup.ly);
      this.f(bup.iL).f(bup.lB).g(bup.lo).b(bup.lM);
      this.d(bup.np, jb.u).b(bup.nr).g(bup.nq).f(bup.ns);
      this.f(bup.nu).b(bup.nz).g(bup.ny).f(bup.nx);
      this.f(bup.nt).b(bup.nF).e(bup.nD).a(bup.nE).g(bup.nB).f(bup.nC);
      this.q();
      this.l(bup.ch);
      this.m(bup.aN);
      this.m(bup.aO);
      this.m(bup.fD);
      this.p();
      this.n(bup.er);
      this.n(bup.iG);
      this.n(bup.iH);
      this.o(bup.fo);
      this.o(bup.fp);
      this.o(bup.fq);
      this.k();
      this.l();
      this.e(bup.bY, jb.g);
      this.e(bup.lU, jb.g);
      this.e(bup.lT, jb.h);
      this.o();
      this.ah();
      this.i(bup.dx, bup.dD);
      this.i(bup.m, bup.dz);
      this.i(bup.dw, bup.dC);
      this.i(bup.dv, bup.dB);
      this.ag();
      this.i(bup.du, bup.dA);
      bna.f().forEach(var1 -> this.a(var1, iw.b("template_spawn_egg")));
   }

   class a {
      private final vk b;

      public a(vk var2, buo var3) {
         this.b = iy.F.a(_snowman, iz.q(_snowman), ii.this.b);
      }

      public ii.a a(buo... var1) {
         for (buo _snowman : _snowman) {
            ii.this.a.accept(ii.e(_snowman, this.b));
         }

         return this;
      }

      public ii.a b(buo... var1) {
         for (buo _snowman : _snowman) {
            ii.this.a(_snowman);
         }

         return this.a(_snowman);
      }

      public ii.a a(ix var1, buo... var2) {
         for (buo _snowman : _snowman) {
            _snowman.a(iw.a(_snowman.h()), iz.q(_snowman), ii.this.b);
         }

         return this.a(_snowman);
      }
   }

   class b {
      private final iz b;
      @Nullable
      private vk c;

      public b(iz var2) {
         this.b = _snowman;
      }

      public ii.b a(buo var1, ix var2) {
         this.c = _snowman.a(_snowman, this.b, ii.this.b);
         ii.this.a.accept(ii.e(_snowman, this.c));
         return this;
      }

      public ii.b a(Function<iz, vk> var1) {
         this.c = _snowman.apply(this.b);
         return this;
      }

      public ii.b a(buo var1) {
         vk _snowman = iy.l.a(_snowman, this.b, ii.this.b);
         vk _snowmanx = iy.m.a(_snowman, this.b, ii.this.b);
         ii.this.a.accept(ii.f(_snowman, _snowman, _snowmanx));
         vk _snowmanxx = iy.n.a(_snowman, this.b, ii.this.b);
         ii.this.c(_snowman, _snowmanxx);
         return this;
      }

      public ii.b b(buo var1) {
         vk _snowman = iy.v.a(_snowman, this.b, ii.this.b);
         vk _snowmanx = iy.w.a(_snowman, this.b, ii.this.b);
         vk _snowmanxx = iy.x.a(_snowman, this.b, ii.this.b);
         ii.this.a.accept(ii.d(_snowman, _snowman, _snowmanx, _snowmanxx));
         vk _snowmanxxx = iy.y.a(_snowman, this.b, ii.this.b);
         ii.this.c(_snowman, _snowmanxxx);
         return this;
      }

      public ii.b c(buo var1) {
         vk _snowman = iy.s.a(_snowman, this.b, ii.this.b);
         vk _snowmanx = iy.t.a(_snowman, this.b, ii.this.b);
         ii.this.a.accept(ii.g(_snowman, _snowman, _snowmanx));
         vk _snowmanxx = iy.u.a(_snowman, this.b, ii.this.b);
         ii.this.c(_snowman, _snowmanxx);
         return this;
      }

      public ii.b d(buo var1) {
         vk _snowman = iy.A.a(_snowman, this.b, ii.this.b);
         vk _snowmanx = iy.z.a(_snowman, this.b, ii.this.b);
         vk _snowmanxx = iy.C.a(_snowman, this.b, ii.this.b);
         vk _snowmanxxx = iy.B.a(_snowman, this.b, ii.this.b);
         ii.this.a.accept(ii.c(_snowman, _snowman, _snowmanx, _snowmanxx, _snowmanxxx));
         return this;
      }

      public ii.b e(buo var1) {
         vk _snowman = iy.D.a(_snowman, this.b, ii.this.b);
         vk _snowmanx = iy.E.a(_snowman, this.b, ii.this.b);
         ii.this.a.accept(ii.i(_snowman, _snowman, _snowmanx));
         return this;
      }

      public ii.b a(buo var1, buo var2) {
         vk _snowman = iy.F.a(_snowman, this.b, ii.this.b);
         ii.this.a.accept(ii.e(_snowman, _snowman));
         ii.this.a.accept(ii.e(_snowman, _snowman));
         ii.this.a(_snowman.h());
         ii.this.a(_snowman);
         return this;
      }

      public ii.b f(buo var1) {
         if (this.c == null) {
            throw new IllegalStateException("Full block not generated yet");
         } else {
            vk _snowman = iy.G.a(_snowman, this.b, ii.this.b);
            vk _snowmanx = iy.H.a(_snowman, this.b, ii.this.b);
            ii.this.a.accept(ii.h(_snowman, _snowman, _snowmanx, this.c));
            return this;
         }
      }

      public ii.b g(buo var1) {
         vk _snowman = iy.K.a(_snowman, this.b, ii.this.b);
         vk _snowmanx = iy.J.a(_snowman, this.b, ii.this.b);
         vk _snowmanxx = iy.L.a(_snowman, this.b, ii.this.b);
         ii.this.a.accept(ii.e(_snowman, _snowman, _snowmanx, _snowmanxx));
         return this;
      }
   }

   static enum c {
      a,
      b;

      private c() {
      }

      public ix a() {
         return this == a ? iy.T : iy.S;
      }

      public ix b() {
         return this == a ? iy.V : iy.U;
      }
   }

   class d {
      private final iz b;

      public d(iz var2) {
         this.b = _snowman;
      }

      public ii.d a(buo var1) {
         iz _snowman = this.b.c(ja.d, this.b.a(ja.i));
         vk _snowmanx = iy.e.a(_snowman, _snowman, ii.this.b);
         ii.this.a.accept(ii.f(_snowman, _snowmanx));
         return this;
      }

      public ii.d b(buo var1) {
         vk _snowman = iy.e.a(_snowman, this.b, ii.this.b);
         ii.this.a.accept(ii.f(_snowman, _snowman));
         return this;
      }

      public ii.d c(buo var1) {
         vk _snowman = iy.e.a(_snowman, this.b, ii.this.b);
         vk _snowmanx = iy.f.a(_snowman, this.b, ii.this.b);
         ii.this.a.accept(ii.h(_snowman, _snowman, _snowmanx));
         return this;
      }
   }
}
