public interface dcr {
   dcr a = (var0, var1) -> false;
   dcr b = (var0, var1) -> !var0 && !var1;
   dcr c = (var0, var1) -> var1 && !var0;
   dcr d = (var0, var1) -> !var0;
   dcr e = (var0, var1) -> var0 && !var1;
   dcr f = (var0, var1) -> !var1;
   dcr g = (var0, var1) -> var0 != var1;
   dcr h = (var0, var1) -> !var0 || !var1;
   dcr i = (var0, var1) -> var0 && var1;
   dcr j = (var0, var1) -> var0 == var1;
   dcr k = (var0, var1) -> var1;
   dcr l = (var0, var1) -> !var0 || var1;
   dcr m = (var0, var1) -> var0;
   dcr n = (var0, var1) -> var0 || !var1;
   dcr o = (var0, var1) -> var0 || var1;
   dcr p = (var0, var1) -> true;

   boolean apply(boolean var1, boolean var2);
}
