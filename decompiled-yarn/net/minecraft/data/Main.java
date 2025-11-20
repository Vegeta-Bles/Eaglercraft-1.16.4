package net.minecraft.data;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.stream.Collectors;
import joptsimple.OptionParser;
import joptsimple.OptionSet;
import joptsimple.OptionSpec;
import net.minecraft.data.client.BlockStateDefinitionProvider;
import net.minecraft.data.dev.NbtProvider;
import net.minecraft.data.report.BiomeListProvider;
import net.minecraft.data.report.BlockListProvider;
import net.minecraft.data.report.CommandSyntaxProvider;
import net.minecraft.data.report.ItemListProvider;
import net.minecraft.data.server.AdvancementsProvider;
import net.minecraft.data.server.BlockTagsProvider;
import net.minecraft.data.server.EntityTypeTagsProvider;
import net.minecraft.data.server.FluidTagsProvider;
import net.minecraft.data.server.ItemTagsProvider;
import net.minecraft.data.server.LootTablesProvider;
import net.minecraft.data.server.RecipesProvider;
import net.minecraft.data.validate.StructureValidatorProvider;

public class Main {
   public Main() {
   }

   public static void main(String[] _snowman) throws IOException {
      OptionParser _snowmanx = new OptionParser();
      OptionSpec<Void> _snowmanxx = _snowmanx.accepts("help", "Show the help menu").forHelp();
      OptionSpec<Void> _snowmanxxx = _snowmanx.accepts("server", "Include server generators");
      OptionSpec<Void> _snowmanxxxx = _snowmanx.accepts("client", "Include client generators");
      OptionSpec<Void> _snowmanxxxxx = _snowmanx.accepts("dev", "Include development tools");
      OptionSpec<Void> _snowmanxxxxxx = _snowmanx.accepts("reports", "Include data reports");
      OptionSpec<Void> _snowmanxxxxxxx = _snowmanx.accepts("validate", "Validate inputs");
      OptionSpec<Void> _snowmanxxxxxxxx = _snowmanx.accepts("all", "Include all generators");
      OptionSpec<String> _snowmanxxxxxxxxx = _snowmanx.accepts("output", "Output folder").withRequiredArg().defaultsTo("generated", new String[0]);
      OptionSpec<String> _snowmanxxxxxxxxxx = _snowmanx.accepts("input", "Input folder").withRequiredArg();
      OptionSet _snowmanxxxxxxxxxxx = _snowmanx.parse(_snowman);
      if (!_snowmanxxxxxxxxxxx.has(_snowmanxx) && _snowmanxxxxxxxxxxx.hasOptions()) {
         Path _snowmanxxxxxxxxxxxx = Paths.get((String)_snowmanxxxxxxxxx.value(_snowmanxxxxxxxxxxx));
         boolean _snowmanxxxxxxxxxxxxx = _snowmanxxxxxxxxxxx.has(_snowmanxxxxxxxx);
         boolean _snowmanxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxx || _snowmanxxxxxxxxxxx.has(_snowmanxxxx);
         boolean _snowmanxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxx || _snowmanxxxxxxxxxxx.has(_snowmanxxx);
         boolean _snowmanxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxx || _snowmanxxxxxxxxxxx.has(_snowmanxxxxx);
         boolean _snowmanxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxx || _snowmanxxxxxxxxxxx.has(_snowmanxxxxxx);
         boolean _snowmanxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxx || _snowmanxxxxxxxxxxx.has(_snowmanxxxxxxx);
         DataGenerator _snowmanxxxxxxxxxxxxxxxxxxx = create(
            _snowmanxxxxxxxxxxxx,
            _snowmanxxxxxxxxxxx.valuesOf(_snowmanxxxxxxxxxx).stream().map(_snowmanxxxxxxxxxxxxxxxxxxxx -> Paths.get(_snowmanxxxxxxxxxxxxxxxxxxxx)).collect(Collectors.toList()),
            _snowmanxxxxxxxxxxxxxx,
            _snowmanxxxxxxxxxxxxxxx,
            _snowmanxxxxxxxxxxxxxxxx,
            _snowmanxxxxxxxxxxxxxxxxx,
            _snowmanxxxxxxxxxxxxxxxxxx
         );
         _snowmanxxxxxxxxxxxxxxxxxxx.run();
      } else {
         _snowmanx.printHelpOn(System.out);
      }
   }

   public static DataGenerator create(
      Path output, Collection<Path> inputs, boolean includeClient, boolean includeServer, boolean includeDev, boolean includeReports, boolean validate
   ) {
      DataGenerator _snowman = new DataGenerator(output, inputs);
      if (includeClient || includeServer) {
         _snowman.install(new SnbtProvider(_snowman).addWriter(new StructureValidatorProvider()));
      }

      if (includeClient) {
         _snowman.install(new BlockStateDefinitionProvider(_snowman));
      }

      if (includeServer) {
         _snowman.install(new FluidTagsProvider(_snowman));
         BlockTagsProvider _snowmanx = new BlockTagsProvider(_snowman);
         _snowman.install(_snowmanx);
         _snowman.install(new ItemTagsProvider(_snowman, _snowmanx));
         _snowman.install(new EntityTypeTagsProvider(_snowman));
         _snowman.install(new RecipesProvider(_snowman));
         _snowman.install(new AdvancementsProvider(_snowman));
         _snowman.install(new LootTablesProvider(_snowman));
      }

      if (includeDev) {
         _snowman.install(new NbtProvider(_snowman));
      }

      if (includeReports) {
         _snowman.install(new BlockListProvider(_snowman));
         _snowman.install(new ItemListProvider(_snowman));
         _snowman.install(new CommandSyntaxProvider(_snowman));
         _snowman.install(new BiomeListProvider(_snowman));
      }

      return _snowman;
   }
}
