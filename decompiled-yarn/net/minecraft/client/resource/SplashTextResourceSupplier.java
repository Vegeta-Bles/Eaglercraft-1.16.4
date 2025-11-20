package net.minecraft.client.resource;

import com.google.common.collect.Lists;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.Session;
import net.minecraft.resource.Resource;
import net.minecraft.resource.ResourceManager;
import net.minecraft.resource.SinglePreparationResourceReloadListener;
import net.minecraft.util.Identifier;
import net.minecraft.util.profiler.Profiler;

public class SplashTextResourceSupplier extends SinglePreparationResourceReloadListener<List<String>> {
   private static final Identifier RESOURCE_ID = new Identifier("texts/splashes.txt");
   private static final Random RANDOM = new Random();
   private final List<String> splashTexts = Lists.newArrayList();
   private final Session field_18934;

   public SplashTextResourceSupplier(Session _snowman) {
      this.field_18934 = _snowman;
   }

   protected List<String> prepare(ResourceManager _snowman, Profiler _snowman) {
      try (
         Resource _snowmanxx = MinecraftClient.getInstance().getResourceManager().getResource(RESOURCE_ID);
         BufferedReader _snowmanxxx = new BufferedReader(new InputStreamReader(_snowmanxx.getInputStream(), StandardCharsets.UTF_8));
      ) {
         return _snowmanxxx.lines().map(String::trim).filter(_snowmanxxxx -> _snowmanxxxx.hashCode() != 125780783).collect(Collectors.toList());
      } catch (IOException var36) {
         return Collections.emptyList();
      }
   }

   protected void apply(List<String> _snowman, ResourceManager _snowman, Profiler _snowman) {
      this.splashTexts.clear();
      this.splashTexts.addAll(_snowman);
   }

   @Nullable
   public String get() {
      Calendar _snowman = Calendar.getInstance();
      _snowman.setTime(new Date());
      if (_snowman.get(2) + 1 == 12 && _snowman.get(5) == 24) {
         return "Merry X-mas!";
      } else if (_snowman.get(2) + 1 == 1 && _snowman.get(5) == 1) {
         return "Happy new year!";
      } else if (_snowman.get(2) + 1 == 10 && _snowman.get(5) == 31) {
         return "OOoooOOOoooo! Spooky!";
      } else if (this.splashTexts.isEmpty()) {
         return null;
      } else {
         return this.field_18934 != null && RANDOM.nextInt(this.splashTexts.size()) == 42
            ? this.field_18934.getUsername().toUpperCase(Locale.ROOT) + " IS YOU"
            : this.splashTexts.get(RANDOM.nextInt(this.splashTexts.size()));
      }
   }
}
