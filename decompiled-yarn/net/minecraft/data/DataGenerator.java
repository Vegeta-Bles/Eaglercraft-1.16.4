package net.minecraft.data;

import com.google.common.base.Stopwatch;
import com.google.common.collect.Lists;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;
import net.minecraft.Bootstrap;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DataGenerator {
   private static final Logger LOGGER = LogManager.getLogger();
   private final Collection<Path> inputs;
   private final Path output;
   private final List<DataProvider> providers = Lists.newArrayList();

   public DataGenerator(Path output, Collection<Path> _snowman) {
      this.output = output;
      this.inputs = _snowman;
   }

   public Collection<Path> getInputs() {
      return this.inputs;
   }

   public Path getOutput() {
      return this.output;
   }

   public void run() throws IOException {
      DataCache _snowman = new DataCache(this.output, "cache");
      _snowman.ignore(this.getOutput().resolve("version.json"));
      Stopwatch _snowmanx = Stopwatch.createStarted();
      Stopwatch _snowmanxx = Stopwatch.createUnstarted();

      for (DataProvider _snowmanxxx : this.providers) {
         LOGGER.info("Starting provider: {}", _snowmanxxx.getName());
         _snowmanxx.start();
         _snowmanxxx.run(_snowman);
         _snowmanxx.stop();
         LOGGER.info("{} finished after {} ms", _snowmanxxx.getName(), _snowmanxx.elapsed(TimeUnit.MILLISECONDS));
         _snowmanxx.reset();
      }

      LOGGER.info("All providers took: {} ms", _snowmanx.elapsed(TimeUnit.MILLISECONDS));
      _snowman.write();
   }

   public void install(DataProvider _snowman) {
      this.providers.add(_snowman);
   }

   static {
      Bootstrap.initialize();
   }
}
