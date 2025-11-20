package net.minecraft.data.server;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Sets;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import net.minecraft.advancement.Advancement;
import net.minecraft.data.DataCache;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AdvancementsProvider implements DataProvider {
   private static final Logger LOGGER = LogManager.getLogger();
   private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
   private final DataGenerator root;
   private final List<Consumer<Consumer<Advancement>>> tabGenerators = ImmutableList.of(
      new EndTabAdvancementGenerator(),
      new HusbandryTabAdvancementGenerator(),
      new AdventureTabAdvancementGenerator(),
      new NetherTabAdvancementGenerator(),
      new StoryTabAdvancementGenerator()
   );

   public AdvancementsProvider(DataGenerator root) {
      this.root = root;
   }

   @Override
   public void run(DataCache cache) throws IOException {
      Path _snowman = this.root.getOutput();
      Set<Identifier> _snowmanx = Sets.newHashSet();
      Consumer<Advancement> _snowmanxx = _snowmanxxx -> {
         if (!_snowman.add(_snowmanxxx.getId())) {
            throw new IllegalStateException("Duplicate advancement " + _snowmanxxx.getId());
         } else {
            Path _snowmanxxx = getOutput(_snowman, _snowmanxxx);

            try {
               DataProvider.writeToPath(GSON, cache, _snowmanxxx.createTask().toJson(), _snowmanxxx);
            } catch (IOException var6x) {
               LOGGER.error("Couldn't save advancement {}", _snowmanxxx, var6x);
            }
         }
      };

      for (Consumer<Consumer<Advancement>> _snowmanxxx : this.tabGenerators) {
         _snowmanxxx.accept(_snowmanxx);
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
