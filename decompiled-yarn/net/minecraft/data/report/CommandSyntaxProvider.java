package net.minecraft.data.report;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mojang.brigadier.CommandDispatcher;
import java.io.IOException;
import java.nio.file.Path;
import net.minecraft.command.argument.ArgumentTypes;
import net.minecraft.data.DataCache;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;

public class CommandSyntaxProvider implements DataProvider {
   private static final Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
   private final DataGenerator root;

   public CommandSyntaxProvider(DataGenerator _snowman) {
      this.root = _snowman;
   }

   @Override
   public void run(DataCache cache) throws IOException {
      Path _snowman = this.root.getOutput().resolve("reports/commands.json");
      CommandDispatcher<ServerCommandSource> _snowmanx = new CommandManager(CommandManager.RegistrationEnvironment.ALL).getDispatcher();
      DataProvider.writeToPath(GSON, cache, ArgumentTypes.toJson(_snowmanx, _snowmanx.getRoot()), _snowman);
   }

   @Override
   public String getName() {
      return "Command Syntax";
   }
}
