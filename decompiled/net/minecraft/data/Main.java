package net.minecraft.data;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.stream.Collectors;
import joptsimple.OptionParser;
import joptsimple.OptionSet;
import joptsimple.OptionSpec;

public class Main {
   public Main() {
   }

   public static void main(String[] var0) throws IOException {
      OptionParser _snowman = new OptionParser();
      OptionSpec<Void> _snowmanx = _snowman.accepts("help", "Show the help menu").forHelp();
      OptionSpec<Void> _snowmanxx = _snowman.accepts("server", "Include server generators");
      OptionSpec<Void> _snowmanxxx = _snowman.accepts("client", "Include client generators");
      OptionSpec<Void> _snowmanxxxx = _snowman.accepts("dev", "Include development tools");
      OptionSpec<Void> _snowmanxxxxx = _snowman.accepts("reports", "Include data reports");
      OptionSpec<Void> _snowmanxxxxxx = _snowman.accepts("validate", "Validate inputs");
      OptionSpec<Void> _snowmanxxxxxxx = _snowman.accepts("all", "Include all generators");
      OptionSpec<String> _snowmanxxxxxxxx = _snowman.accepts("output", "Output folder").withRequiredArg().defaultsTo("generated", new String[0]);
      OptionSpec<String> _snowmanxxxxxxxxx = _snowman.accepts("input", "Input folder").withRequiredArg();
      OptionSet _snowmanxxxxxxxxxx = _snowman.parse(_snowman);
      if (!_snowmanxxxxxxxxxx.has(_snowmanx) && _snowmanxxxxxxxxxx.hasOptions()) {
         Path _snowmanxxxxxxxxxxx = Paths.get((String)_snowmanxxxxxxxx.value(_snowmanxxxxxxxxxx));
         boolean _snowmanxxxxxxxxxxxx = _snowmanxxxxxxxxxx.has(_snowmanxxxxxxx);
         boolean _snowmanxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxx || _snowmanxxxxxxxxxx.has(_snowmanxxx);
         boolean _snowmanxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxx || _snowmanxxxxxxxxxx.has(_snowmanxx);
         boolean _snowmanxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxx || _snowmanxxxxxxxxxx.has(_snowmanxxxx);
         boolean _snowmanxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxx || _snowmanxxxxxxxxxx.has(_snowmanxxxxx);
         boolean _snowmanxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxx || _snowmanxxxxxxxxxx.has(_snowmanxxxxxx);
         hl _snowmanxxxxxxxxxxxxxxxxxx = a(
            _snowmanxxxxxxxxxxx,
            _snowmanxxxxxxxxxx.valuesOf(_snowmanxxxxxxxxx).stream().map(var0x -> Paths.get(var0x)).collect(Collectors.toList()),
            _snowmanxxxxxxxxxxxxx,
            _snowmanxxxxxxxxxxxxxx,
            _snowmanxxxxxxxxxxxxxxx,
            _snowmanxxxxxxxxxxxxxxxx,
            _snowmanxxxxxxxxxxxxxxxxx
         );
         _snowmanxxxxxxxxxxxxxxxxxx.c();
      } else {
         _snowman.printHelpOn(System.out);
      }
   }

   public static hl a(Path var0, Collection<Path> var1, boolean var2, boolean var3, boolean var4, boolean var5, boolean var6) {
      hl _snowman = new hl(_snowman, _snowman);
      if (_snowman || _snowman) {
         _snowman.a(new jp(_snowman).a(new jq()));
      }

      if (_snowman) {
         _snowman.a(new ik(_snowman));
      }

      if (_snowman) {
         _snowman.a(new ju(_snowman));
         js _snowmanx = new js(_snowman);
         _snowman.a(_snowmanx);
         _snowman.a(new jv(_snowman, _snowmanx));
         _snowman.a(new jt(_snowman));
         _snowman.a(new jg(_snowman));
         _snowman.a(new ho(_snowman));
         _snowman.a(new ie(_snowman));
      }

      if (_snowman) {
         _snowman.a(new jo(_snowman));
      }

      if (_snowman) {
         _snowman.a(new hv(_snowman));
         _snowman.a(new hx(_snowman));
         _snowman.a(new hw(_snowman));
         _snowman.a(new ks(_snowman));
      }

      return _snowman;
   }
}
