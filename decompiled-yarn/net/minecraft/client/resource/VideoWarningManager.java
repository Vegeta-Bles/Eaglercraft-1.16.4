package net.minecraft.client.resource;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.ImmutableMap.Builder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.mojang.blaze3d.platform.GlDebugInfo;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.Nullable;
import net.minecraft.resource.Resource;
import net.minecraft.resource.ResourceManager;
import net.minecraft.resource.SinglePreparationResourceReloadListener;
import net.minecraft.util.Identifier;
import net.minecraft.util.profiler.Profiler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class VideoWarningManager extends SinglePreparationResourceReloadListener<VideoWarningManager.WarningPatternLoader> {
   private static final Logger LOGGER = LogManager.getLogger();
   private static final Identifier GPU_WARNLIST_ID = new Identifier("gpu_warnlist.json");
   private ImmutableMap<String, String> warnings = ImmutableMap.of();
   private boolean warningScheduled;
   private boolean warned;
   private boolean cancelledAfterWarning;

   public VideoWarningManager() {
   }

   public boolean hasWarning() {
      return !this.warnings.isEmpty();
   }

   public boolean canWarn() {
      return this.hasWarning() && !this.warned;
   }

   public void scheduleWarning() {
      this.warningScheduled = true;
   }

   public void acceptAfterWarnings() {
      this.warned = true;
   }

   public void cancelAfterWarnings() {
      this.warned = true;
      this.cancelledAfterWarning = true;
   }

   public boolean shouldWarn() {
      return this.warningScheduled && !this.warned;
   }

   public boolean hasCancelledAfterWarning() {
      return this.cancelledAfterWarning;
   }

   public void reset() {
      this.warningScheduled = false;
      this.warned = false;
      this.cancelledAfterWarning = false;
   }

   @Nullable
   public String getRendererWarning() {
      return (String)this.warnings.get("renderer");
   }

   @Nullable
   public String getVersionWarning() {
      return (String)this.warnings.get("version");
   }

   @Nullable
   public String getVendorWarning() {
      return (String)this.warnings.get("vendor");
   }

   @Nullable
   public String method_30920() {
      StringBuilder _snowman = new StringBuilder();
      this.warnings.forEach((_snowmanx, _snowmanxx) -> _snowman.append(_snowmanx).append(": ").append(_snowmanxx));
      return _snowman.length() == 0 ? null : _snowman.toString();
   }

   protected VideoWarningManager.WarningPatternLoader prepare(ResourceManager _snowman, Profiler _snowman) {
      List<Pattern> _snowmanxx = Lists.newArrayList();
      List<Pattern> _snowmanxxx = Lists.newArrayList();
      List<Pattern> _snowmanxxxx = Lists.newArrayList();
      _snowman.startTick();
      JsonObject _snowmanxxxxx = loadWarnlist(_snowman, _snowman);
      if (_snowmanxxxxx != null) {
         _snowman.push("compile_regex");
         compilePatterns(_snowmanxxxxx.getAsJsonArray("renderer"), _snowmanxx);
         compilePatterns(_snowmanxxxxx.getAsJsonArray("version"), _snowmanxxx);
         compilePatterns(_snowmanxxxxx.getAsJsonArray("vendor"), _snowmanxxxx);
         _snowman.pop();
      }

      _snowman.endTick();
      return new VideoWarningManager.WarningPatternLoader(_snowmanxx, _snowmanxxx, _snowmanxxxx);
   }

   protected void apply(VideoWarningManager.WarningPatternLoader _snowman, ResourceManager _snowman, Profiler _snowman) {
      this.warnings = _snowman.buildWarnings();
   }

   private static void compilePatterns(JsonArray array, List<Pattern> patterns) {
      array.forEach(_snowmanx -> patterns.add(Pattern.compile(_snowmanx.getAsString(), 2)));
   }

   @Nullable
   private static JsonObject loadWarnlist(ResourceManager resourceManager, Profiler profiler) {
      profiler.push("parse_json");
      JsonObject _snowman = null;

      try (
         Resource _snowmanx = resourceManager.getResource(GPU_WARNLIST_ID);
         BufferedReader _snowmanxx = new BufferedReader(new InputStreamReader(_snowmanx.getInputStream(), StandardCharsets.UTF_8));
      ) {
         _snowman = new JsonParser().parse(_snowmanxx).getAsJsonObject();
      } catch (JsonSyntaxException | IOException var35) {
         LOGGER.warn("Failed to load GPU warnlist");
      }

      profiler.pop();
      return _snowman;
   }

   public static final class WarningPatternLoader {
      private final List<Pattern> rendererPatterns;
      private final List<Pattern> versionPatterns;
      private final List<Pattern> vendorPatterns;

      private WarningPatternLoader(List<Pattern> rendererPatterns, List<Pattern> versionPatterns, List<Pattern> vendorPatterns) {
         this.rendererPatterns = rendererPatterns;
         this.versionPatterns = versionPatterns;
         this.vendorPatterns = vendorPatterns;
      }

      private static String buildWarning(List<Pattern> warningPattern, String info) {
         List<String> _snowman = Lists.newArrayList();

         for (Pattern _snowmanx : warningPattern) {
            Matcher _snowmanxx = _snowmanx.matcher(info);

            while (_snowmanxx.find()) {
               _snowman.add(_snowmanxx.group());
            }
         }

         return String.join(", ", _snowman);
      }

      private ImmutableMap<String, String> buildWarnings() {
         Builder<String, String> _snowman = new Builder();
         String _snowmanx = buildWarning(this.rendererPatterns, GlDebugInfo.getRenderer());
         if (!_snowmanx.isEmpty()) {
            _snowman.put("renderer", _snowmanx);
         }

         String _snowmanxx = buildWarning(this.versionPatterns, GlDebugInfo.getVersion());
         if (!_snowmanxx.isEmpty()) {
            _snowman.put("version", _snowmanxx);
         }

         String _snowmanxxx = buildWarning(this.vendorPatterns, GlDebugInfo.getVendor());
         if (!_snowmanxxx.isEmpty()) {
            _snowman.put("vendor", _snowmanxxx);
         }

         return _snowman.build();
      }
   }
}
