/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableList
 *  com.google.common.collect.Sets
 *  com.google.gson.Gson
 *  com.google.gson.GsonBuilder
 *  com.google.gson.JsonElement
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 */
package net.minecraft.data.server;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Sets;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.List;
import java.util.function.Consumer;
import net.minecraft.advancement.Advancement;
import net.minecraft.data.DataCache;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.server.AdventureTabAdvancementGenerator;
import net.minecraft.data.server.EndTabAdvancementGenerator;
import net.minecraft.data.server.HusbandryTabAdvancementGenerator;
import net.minecraft.data.server.NetherTabAdvancementGenerator;
import net.minecraft.data.server.StoryTabAdvancementGenerator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AdvancementsProvider
implements DataProvider {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private final DataGenerator root;
    private final List<Consumer<Consumer<Advancement>>> tabGenerators = ImmutableList.of((Object)new EndTabAdvancementGenerator(), (Object)new HusbandryTabAdvancementGenerator(), (Object)new AdventureTabAdvancementGenerator(), (Object)new NetherTabAdvancementGenerator(), (Object)new StoryTabAdvancementGenerator());

    public AdvancementsProvider(DataGenerator root) {
        this.root = root;
    }

    @Override
    public void run(DataCache cache) throws IOException {
        Path path = this.root.getOutput();
        HashSet _snowman2 = Sets.newHashSet();
        Consumer<Advancement> _snowman3 = advancement -> {
            if (!_snowman2.add(advancement.getId())) {
                throw new IllegalStateException("Duplicate advancement " + advancement.getId());
            }
            Path path2 = AdvancementsProvider.getOutput(path, advancement);
            try {
                DataProvider.writeToPath(GSON, cache, (JsonElement)advancement.createTask().toJson(), path2);
            }
            catch (IOException _snowman2) {
                LOGGER.error("Couldn't save advancement {}", (Object)path2, (Object)_snowman2);
            }
        };
        for (Consumer<Consumer<Advancement>> consumer : this.tabGenerators) {
            consumer.accept(_snowman3);
        }
    }

    private static Path getOutput(Path rootOutput, Advancement advancement) {
        return rootOutput.resolve("data/" + advancement.getId().getNamespace() + "/advancements/" + advancement.getId().getPath() + ".json");
    }

    @Override
    public String getName() {
        return "Advancements";
    }
}

