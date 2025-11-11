import com.mojang.brigadier.exceptions.BuiltInExceptionProvider;
import com.mojang.brigadier.exceptions.Dynamic2CommandExceptionType;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;

public class cx implements BuiltInExceptionProvider {
   private static final Dynamic2CommandExceptionType a = new Dynamic2CommandExceptionType((var0, var1) -> new of("argument.double.low", var1, var0));
   private static final Dynamic2CommandExceptionType b = new Dynamic2CommandExceptionType((var0, var1) -> new of("argument.double.big", var1, var0));
   private static final Dynamic2CommandExceptionType c = new Dynamic2CommandExceptionType((var0, var1) -> new of("argument.float.low", var1, var0));
   private static final Dynamic2CommandExceptionType d = new Dynamic2CommandExceptionType((var0, var1) -> new of("argument.float.big", var1, var0));
   private static final Dynamic2CommandExceptionType e = new Dynamic2CommandExceptionType((var0, var1) -> new of("argument.integer.low", var1, var0));
   private static final Dynamic2CommandExceptionType f = new Dynamic2CommandExceptionType((var0, var1) -> new of("argument.integer.big", var1, var0));
   private static final Dynamic2CommandExceptionType g = new Dynamic2CommandExceptionType((var0, var1) -> new of("argument.long.low", var1, var0));
   private static final Dynamic2CommandExceptionType h = new Dynamic2CommandExceptionType((var0, var1) -> new of("argument.long.big", var1, var0));
   private static final DynamicCommandExceptionType i = new DynamicCommandExceptionType(var0 -> new of("argument.literal.incorrect", var0));
   private static final SimpleCommandExceptionType j = new SimpleCommandExceptionType(new of("parsing.quote.expected.start"));
   private static final SimpleCommandExceptionType k = new SimpleCommandExceptionType(new of("parsing.quote.expected.end"));
   private static final DynamicCommandExceptionType l = new DynamicCommandExceptionType(var0 -> new of("parsing.quote.escape", var0));
   private static final DynamicCommandExceptionType m = new DynamicCommandExceptionType(var0 -> new of("parsing.bool.invalid", var0));
   private static final DynamicCommandExceptionType n = new DynamicCommandExceptionType(var0 -> new of("parsing.int.invalid", var0));
   private static final SimpleCommandExceptionType o = new SimpleCommandExceptionType(new of("parsing.int.expected"));
   private static final DynamicCommandExceptionType p = new DynamicCommandExceptionType(var0 -> new of("parsing.long.invalid", var0));
   private static final SimpleCommandExceptionType q = new SimpleCommandExceptionType(new of("parsing.long.expected"));
   private static final DynamicCommandExceptionType r = new DynamicCommandExceptionType(var0 -> new of("parsing.double.invalid", var0));
   private static final SimpleCommandExceptionType s = new SimpleCommandExceptionType(new of("parsing.double.expected"));
   private static final DynamicCommandExceptionType t = new DynamicCommandExceptionType(var0 -> new of("parsing.float.invalid", var0));
   private static final SimpleCommandExceptionType u = new SimpleCommandExceptionType(new of("parsing.float.expected"));
   private static final SimpleCommandExceptionType v = new SimpleCommandExceptionType(new of("parsing.bool.expected"));
   private static final DynamicCommandExceptionType w = new DynamicCommandExceptionType(var0 -> new of("parsing.expected", var0));
   private static final SimpleCommandExceptionType x = new SimpleCommandExceptionType(new of("command.unknown.command"));
   private static final SimpleCommandExceptionType y = new SimpleCommandExceptionType(new of("command.unknown.argument"));
   private static final SimpleCommandExceptionType z = new SimpleCommandExceptionType(new of("command.expected.separator"));
   private static final DynamicCommandExceptionType A = new DynamicCommandExceptionType(var0 -> new of("command.exception", var0));

   public cx() {
   }

   public Dynamic2CommandExceptionType doubleTooLow() {
      return a;
   }

   public Dynamic2CommandExceptionType doubleTooHigh() {
      return b;
   }

   public Dynamic2CommandExceptionType floatTooLow() {
      return c;
   }

   public Dynamic2CommandExceptionType floatTooHigh() {
      return d;
   }

   public Dynamic2CommandExceptionType integerTooLow() {
      return e;
   }

   public Dynamic2CommandExceptionType integerTooHigh() {
      return f;
   }

   public Dynamic2CommandExceptionType longTooLow() {
      return g;
   }

   public Dynamic2CommandExceptionType longTooHigh() {
      return h;
   }

   public DynamicCommandExceptionType literalIncorrect() {
      return i;
   }

   public SimpleCommandExceptionType readerExpectedStartOfQuote() {
      return j;
   }

   public SimpleCommandExceptionType readerExpectedEndOfQuote() {
      return k;
   }

   public DynamicCommandExceptionType readerInvalidEscape() {
      return l;
   }

   public DynamicCommandExceptionType readerInvalidBool() {
      return m;
   }

   public DynamicCommandExceptionType readerInvalidInt() {
      return n;
   }

   public SimpleCommandExceptionType readerExpectedInt() {
      return o;
   }

   public DynamicCommandExceptionType readerInvalidLong() {
      return p;
   }

   public SimpleCommandExceptionType readerExpectedLong() {
      return q;
   }

   public DynamicCommandExceptionType readerInvalidDouble() {
      return r;
   }

   public SimpleCommandExceptionType readerExpectedDouble() {
      return s;
   }

   public DynamicCommandExceptionType readerInvalidFloat() {
      return t;
   }

   public SimpleCommandExceptionType readerExpectedFloat() {
      return u;
   }

   public SimpleCommandExceptionType readerExpectedBool() {
      return v;
   }

   public DynamicCommandExceptionType readerExpectedSymbol() {
      return w;
   }

   public SimpleCommandExceptionType dispatcherUnknownCommand() {
      return x;
   }

   public SimpleCommandExceptionType dispatcherUnknownArgument() {
      return y;
   }

   public SimpleCommandExceptionType dispatcherExpectedArgumentSeparator() {
      return z;
   }

   public DynamicCommandExceptionType dispatcherParseException() {
      return A;
   }
}
