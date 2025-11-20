package net.minecraft.server.dedicated;

import java.nio.file.Path;
import java.util.function.UnaryOperator;
import net.minecraft.util.registry.DynamicRegistryManager;

public class ServerPropertiesLoader {
   private final Path path;
   private ServerPropertiesHandler propertiesHandler;

   public ServerPropertiesLoader(DynamicRegistryManager _snowman, Path _snowman) {
      this.path = _snowman;
      this.propertiesHandler = ServerPropertiesHandler.load(_snowman, _snowman);
   }

   public ServerPropertiesHandler getPropertiesHandler() {
      return this.propertiesHandler;
   }

   public void store() {
      this.propertiesHandler.saveProperties(this.path);
   }

   public ServerPropertiesLoader apply(UnaryOperator<ServerPropertiesHandler> _snowman) {
      (this.propertiesHandler = _snowman.apply(this.propertiesHandler)).saveProperties(this.path);
      return this;
   }
}
