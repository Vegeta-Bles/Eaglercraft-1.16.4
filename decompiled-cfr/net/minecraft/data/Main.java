/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  joptsimple.AbstractOptionSpec
 *  joptsimple.ArgumentAcceptingOptionSpec
 *  joptsimple.OptionParser
 *  joptsimple.OptionSet
 *  joptsimple.OptionSpec
 *  joptsimple.OptionSpecBuilder
 */
package net.minecraft.data;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.stream.Collectors;
import joptsimple.AbstractOptionSpec;
import joptsimple.ArgumentAcceptingOptionSpec;
import joptsimple.OptionParser;
import joptsimple.OptionSet;
import joptsimple.OptionSpec;
import joptsimple.OptionSpecBuilder;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.SnbtProvider;
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
    public static void main(String[] stringArray) throws IOException {
        OptionParser optionParser = new OptionParser();
        AbstractOptionSpec _snowman2 = optionParser.accepts("help", "Show the help menu").forHelp();
        OptionSpecBuilder _snowman3 = optionParser.accepts("server", "Include server generators");
        OptionSpecBuilder _snowman4 = optionParser.accepts("client", "Include client generators");
        OptionSpecBuilder _snowman5 = optionParser.accepts("dev", "Include development tools");
        OptionSpecBuilder _snowman6 = optionParser.accepts("reports", "Include data reports");
        OptionSpecBuilder _snowman7 = optionParser.accepts("validate", "Validate inputs");
        OptionSpecBuilder _snowman8 = optionParser.accepts("all", "Include all generators");
        ArgumentAcceptingOptionSpec _snowman9 = optionParser.accepts("output", "Output folder").withRequiredArg().defaultsTo((Object)"generated", (Object[])new String[0]);
        ArgumentAcceptingOptionSpec _snowman10 = optionParser.accepts("input", "Input folder").withRequiredArg();
        OptionSet _snowman11 = optionParser.parse(stringArray);
        if (_snowman11.has((OptionSpec)_snowman2) || !_snowman11.hasOptions()) {
            optionParser.printHelpOn((OutputStream)System.out);
            return;
        }
        Path _snowman12 = Paths.get((String)_snowman9.value(_snowman11), new String[0]);
        boolean _snowman13 = _snowman11.has((OptionSpec)_snowman8);
        boolean _snowman14 = _snowman13 || _snowman11.has((OptionSpec)_snowman4);
        boolean _snowman15 = _snowman13 || _snowman11.has((OptionSpec)_snowman3);
        boolean _snowman16 = _snowman13 || _snowman11.has((OptionSpec)_snowman5);
        boolean _snowman17 = _snowman13 || _snowman11.has((OptionSpec)_snowman6);
        boolean _snowman18 = _snowman13 || _snowman11.has((OptionSpec)_snowman7);
        DataGenerator _snowman19 = Main.create(_snowman12, _snowman11.valuesOf((OptionSpec)_snowman10).stream().map(string -> Paths.get(string, new String[0])).collect(Collectors.toList()), _snowman14, _snowman15, _snowman16, _snowman17, _snowman18);
        _snowman19.run();
    }

    public static DataGenerator create(Path output, Collection<Path> inputs, boolean includeClient, boolean includeServer, boolean includeDev, boolean includeReports, boolean validate) {
        DataGenerator dataGenerator = new DataGenerator(output, inputs);
        if (includeClient || includeServer) {
            dataGenerator.install(new SnbtProvider(dataGenerator).addWriter(new StructureValidatorProvider()));
        }
        if (includeClient) {
            dataGenerator.install(new BlockStateDefinitionProvider(dataGenerator));
        }
        if (includeServer) {
            dataGenerator.install(new FluidTagsProvider(dataGenerator));
            BlockTagsProvider blockTagsProvider = new BlockTagsProvider(dataGenerator);
            dataGenerator.install(blockTagsProvider);
            dataGenerator.install(new ItemTagsProvider(dataGenerator, blockTagsProvider));
            dataGenerator.install(new EntityTypeTagsProvider(dataGenerator));
            dataGenerator.install(new RecipesProvider(dataGenerator));
            dataGenerator.install(new AdvancementsProvider(dataGenerator));
            dataGenerator.install(new LootTablesProvider(dataGenerator));
        }
        if (includeDev) {
            dataGenerator.install(new NbtProvider(dataGenerator));
        }
        if (includeReports) {
            dataGenerator.install(new BlockListProvider(dataGenerator));
            dataGenerator.install(new ItemListProvider(dataGenerator));
            dataGenerator.install(new CommandSyntaxProvider(dataGenerator));
            dataGenerator.install(new BiomeListProvider(dataGenerator));
        }
        return dataGenerator;
    }
}

