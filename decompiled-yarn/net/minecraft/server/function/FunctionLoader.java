package net.minecraft.server.function;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.google.common.collect.ImmutableMap.Builder;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.datafixers.util.Pair;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.Executor;
import net.minecraft.resource.Resource;
import net.minecraft.resource.ResourceManager;
import net.minecraft.resource.ResourceReloadListener;
import net.minecraft.server.command.CommandOutput;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.tag.Tag;
import net.minecraft.tag.TagGroup;
import net.minecraft.tag.TagGroupLoader;
import net.minecraft.text.LiteralText;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.profiler.Profiler;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FunctionLoader implements ResourceReloadListener {
   private static final Logger LOGGER = LogManager.getLogger();
   private static final int PATH_PREFIX_LENGTH = "functions/".length();
   private static final int PATH_SUFFIX_LENGTH = ".mcfunction".length();
   private volatile Map<Identifier, CommandFunction> functions = ImmutableMap.of();
   private final TagGroupLoader<CommandFunction> tagLoader = new TagGroupLoader<>(this::get, "tags/functions", "function");
   private volatile TagGroup<CommandFunction> tags = TagGroup.createEmpty();
   private final int level;
   private final CommandDispatcher<ServerCommandSource> commandDispatcher;

   public Optional<CommandFunction> get(Identifier id) {
      return Optional.ofNullable(this.functions.get(id));
   }

   public Map<Identifier, CommandFunction> getFunctions() {
      return this.functions;
   }

   public TagGroup<CommandFunction> getTags() {
      return this.tags;
   }

   public Tag<CommandFunction> getOrCreateTag(Identifier id) {
      return this.tags.getTagOrEmpty(id);
   }

   public FunctionLoader(int level, CommandDispatcher<ServerCommandSource> commandDispatcher) {
      this.level = level;
      this.commandDispatcher = commandDispatcher;
   }

   @Override
   public CompletableFuture<Void> reload(
      ResourceReloadListener.Synchronizer synchronizer,
      ResourceManager manager,
      Profiler prepareProfiler,
      Profiler applyProfiler,
      Executor prepareExecutor,
      Executor applyExecutor
   ) {
      CompletableFuture<Map<Identifier, Tag.Builder>> _snowman = this.tagLoader.prepareReload(manager, prepareExecutor);
      CompletableFuture<Map<Identifier, CompletableFuture<CommandFunction>>> _snowmanx = CompletableFuture.<Collection<Identifier>>supplyAsync(
            () -> manager.findResources("functions", _snowmanxx -> _snowmanxx.endsWith(".mcfunction")), prepareExecutor
         )
         .thenCompose(_snowmanxx -> {
            Map<Identifier, CompletableFuture<CommandFunction>> _snowmanxx = Maps.newHashMap();
            ServerCommandSource _snowmanx = new ServerCommandSource(CommandOutput.DUMMY, Vec3d.ZERO, Vec2f.ZERO, null, this.level, "", LiteralText.EMPTY, null, null);

            for (Identifier _snowmanxxx : _snowmanxx) {
               String _snowmanxxxx = _snowmanxxx.getPath();
               Identifier _snowmanxxxxx = new Identifier(_snowmanxxx.getNamespace(), _snowmanxxxx.substring(PATH_PREFIX_LENGTH, _snowmanxxxx.length() - PATH_SUFFIX_LENGTH));
               _snowmanxx.put(_snowmanxxxxx, CompletableFuture.supplyAsync(() -> {
                  List<String> _snowmanxxxxxx = readLines(manager, _snowman);
                  return CommandFunction.create(_snowman, this.commandDispatcher, _snowman, _snowmanxxxxxx);
               }, prepareExecutor));
            }

            CompletableFuture<?>[] _snowmanxxx = _snowmanxx.values().toArray(new CompletableFuture[0]);
            return CompletableFuture.allOf(_snowmanxxx).handle((_snowmanxxxxx, _snowmanxxxxx) -> _snowman);
         });
      return _snowman.thenCombine(_snowmanx, Pair::of).thenCompose(synchronizer::whenPrepared).thenAcceptAsync(_snowmanxx -> {
         Map<Identifier, CompletableFuture<CommandFunction>> _snowmanx = (Map<Identifier, CompletableFuture<CommandFunction>>)_snowmanxx.getSecond();
         Builder<Identifier, CommandFunction> _snowmanxx = ImmutableMap.builder();
         _snowmanx.forEach((_snowmanxxxxx, _snowmanxxx) -> _snowmanxxx.handle((_snowmanxxxxxxx, _snowmanxxxxxxxx) -> {
               if (_snowmanxxxxxxxx != null) {
                  LOGGER.error("Failed to load function {}", _snowmanxxx, _snowmanxxxxxxxx);
               } else {
                  _snowman.put(_snowmanxxx, _snowmanxxxxxxx);
               }

               return null;
            }).join());
         this.functions = _snowmanxx.build();
         this.tags = this.tagLoader.applyReload((Map<Identifier, Tag.Builder>)_snowmanxx.getFirst());
      }, applyExecutor);
   }

   private static List<String> readLines(ResourceManager resourceManager, Identifier id) {
      try (Resource _snowman = resourceManager.getResource(id)) {
         return IOUtils.readLines(_snowman.getInputStream(), StandardCharsets.UTF_8);
      } catch (IOException var16) {
         throw new CompletionException(var16);
      }
   }
}
