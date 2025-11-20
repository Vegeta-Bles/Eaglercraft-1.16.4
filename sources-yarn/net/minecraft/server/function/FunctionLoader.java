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
      CompletableFuture<Map<Identifier, Tag.Builder>> completableFuture = this.tagLoader.prepareReload(manager, prepareExecutor);
      CompletableFuture<Map<Identifier, CompletableFuture<CommandFunction>>> completableFuture2 = CompletableFuture.<Collection<Identifier>>supplyAsync(
            () -> manager.findResources("functions", string -> string.endsWith(".mcfunction")), prepareExecutor
         )
         .thenCompose(collection -> {
            Map<Identifier, CompletableFuture<CommandFunction>> map = Maps.newHashMap();
            ServerCommandSource lv = new ServerCommandSource(CommandOutput.DUMMY, Vec3d.ZERO, Vec2f.ZERO, null, this.level, "", LiteralText.EMPTY, null, null);

            for (Identifier lv2 : collection) {
               String string = lv2.getPath();
               Identifier lv3 = new Identifier(lv2.getNamespace(), string.substring(PATH_PREFIX_LENGTH, string.length() - PATH_SUFFIX_LENGTH));
               map.put(lv3, CompletableFuture.supplyAsync(() -> {
                  List<String> list = readLines(manager, lv2);
                  return CommandFunction.create(lv3, this.commandDispatcher, lv, list);
               }, prepareExecutor));
            }

            CompletableFuture<?>[] completableFutures = map.values().toArray(new CompletableFuture[0]);
            return CompletableFuture.allOf(completableFutures).handle((void_, throwable) -> map);
         });
      return completableFuture.thenCombine(completableFuture2, Pair::of).thenCompose(synchronizer::whenPrepared).thenAcceptAsync(pair -> {
         Map<Identifier, CompletableFuture<CommandFunction>> map = (Map<Identifier, CompletableFuture<CommandFunction>>)pair.getSecond();
         Builder<Identifier, CommandFunction> builder = ImmutableMap.builder();
         map.forEach((arg, completableFuturex) -> completableFuturex.handle((arg2, throwable) -> {
               if (throwable != null) {
                  LOGGER.error("Failed to load function {}", arg, throwable);
               } else {
                  builder.put(arg, arg2);
               }

               return null;
            }).join());
         this.functions = builder.build();
         this.tags = this.tagLoader.applyReload((Map<Identifier, Tag.Builder>)pair.getFirst());
      }, applyExecutor);
   }

   private static List<String> readLines(ResourceManager resourceManager, Identifier id) {
      try (Resource lv = resourceManager.getResource(id)) {
         return IOUtils.readLines(lv.getInputStream(), StandardCharsets.UTF_8);
      } catch (IOException var16) {
         throw new CompletionException(var16);
      }
   }
}
