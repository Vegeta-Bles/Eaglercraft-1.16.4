import java.util.function.Predicate;

public class dcx implements dcs {
   protected static final dcs a = new dcx(false, -Double.MAX_VALUE, bmd.a, var0 -> false) {
      @Override
      public boolean a(ddh var1, fx var2, boolean var3) {
         return _snowman;
      }
   };
   private final boolean b;
   private final double c;
   private final blx d;
   private final Predicate<cuw> e;

   protected dcx(boolean var1, double var2, blx var4, Predicate<cuw> var5) {
      this.b = _snowman;
      this.c = _snowman;
      this.d = _snowman;
      this.e = _snowman;
   }

   @Deprecated
   protected dcx(aqa var1) {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if_ you have the rights to distribute it!)
      // java.lang.NullPointerException: Cannot invoke "org.jetbrains.java.decompiler.struct.gen.VarType.equals(Object)" because "curType" is null
      //   at org.jetbrains.java.decompiler.modules.decompiler.exps.NewExprent.setLambdaGenericTypes(NewExprent.java:668)
      //   at org.jetbrains.java.decompiler.modules.decompiler.exps.NewExprent.toJava(NewExprent.java:401)
      //   at org.jetbrains.java.decompiler.modules.decompiler.exps.FunctionExprent.wrapOperandString(FunctionExprent.java:745)
      //   at org.jetbrains.java.decompiler.modules.decompiler.exps.FunctionExprent.wrapOperandString(FunctionExprent.java:714)
      //   at org.jetbrains.java.decompiler.modules.decompiler.exps.FunctionExprent.toJava(FunctionExprent.java:625)
      //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.getCastedExprent(ExprProcessor.java:1018)
      //   at org.jetbrains.java.decompiler.modules.decompiler.exps.InvocationExprent.appendParamList(InvocationExprent.java:1153)
      //   at org.jetbrains.java.decompiler.modules.decompiler.exps.InvocationExprent.toJava(InvocationExprent.java:902)
      //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.listToJava(ExprProcessor.java:895)
      //   at org.jetbrains.java.decompiler.modules.decompiler.stats.BasicBlockStatement.toJava(BasicBlockStatement.java:90)
      //   at org.jetbrains.java.decompiler.modules.decompiler.stats.RootStatement.toJava(RootStatement.java:36)
      //   at org.jetbrains.java.decompiler.main.ClassWriter.writeMethod(ClassWriter.java:1283)
      //
      // Bytecode:
      // 00: aload 0
      // 01: aload 1
      // 02: invokevirtual aqa.by ()Z
      // 05: aload 1
      // 06: invokevirtual aqa.cE ()D
      // 09: aload 1
      // 0a: instanceof aqm
      // 0d: ifeq 1d
      // 10: aload 1
      // 11: checkcast aqm
      // 14: invokevirtual aqm.dD ()Lbmb;
      // 17: invokevirtual bmb.b ()Lblx;
      // 1a: goto 20
      // 1d: getstatic bmd.a Lblx;
      // 20: aload 1
      // 21: instanceof aqm
      // 24: ifeq 38
      // 27: aload 1
      // 28: checkcast aqm
      // 2b: dup
      // 2c: invokevirtual java/lang/Object.getClass ()Ljava/lang/Class;
      // 2f: pop
      // 30: invokedynamic test (Laqm;)Ljava/util/function/Predicate; bsm=java/lang/invoke/LambdaMetafactory.metafactory (Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/String;Ljava/lang/invoke/MethodType;Ljava/lang/invoke/MethodType;Ljava/lang/invoke/MethodHandle;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/CallSite; args=[ (Ljava/lang/Object;)Z, aqm.a (Lcuw;)Z, (Lcuw;)Z ]
      // 35: goto 3d
      // 38: invokedynamic test ()Ljava/util/function/Predicate; bsm=java/lang/invoke/LambdaMetafactory.metafactory (Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/String;Ljava/lang/invoke/MethodType;Ljava/lang/invoke/MethodType;Ljava/lang/invoke/MethodHandle;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/CallSite; args=[ (Ljava/lang/Object;)Z, dcx.a (Lcuw;)Z, (Lcuw;)Z ]
      // 3d: invokespecial dcx.<init> (ZDLblx;Ljava/util/function/Predicate;)V
      // 40: return
   }

   @Override
   public boolean a(blx var1) {
      return this.d == _snowman;
   }

   @Override
   public boolean a(cux var1, cuv var2) {
      return this.e.test(_snowman) && !_snowman.a().a(_snowman);
   }

   @Override
   public boolean b() {
      return this.b;
   }

   @Override
   public boolean a(ddh var1, fx var2, boolean var3) {
      return this.c > (double)_snowman.v() + _snowman.c(gc.a.b) - 1.0E-5F;
   }
}
