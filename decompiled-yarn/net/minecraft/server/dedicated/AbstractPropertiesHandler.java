package net.minecraft.server.dedicated;

import com.google.common.base.MoreObjects;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
import java.util.Properties;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;
import javax.annotation.Nullable;
import net.minecraft.util.registry.DynamicRegistryManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class AbstractPropertiesHandler<T extends AbstractPropertiesHandler<T>> {
   private static final Logger LOGGER = LogManager.getLogger();
   private final Properties properties;

   public AbstractPropertiesHandler(Properties properties) {
      this.properties = properties;
   }

   public static Properties loadProperties(Path path) {
      Properties _snowman = new Properties();

      try (InputStream _snowmanx = Files.newInputStream(path)) {
         _snowman.load(_snowmanx);
      } catch (IOException var15) {
         LOGGER.error("Failed to load properties from file: " + path);
      }

      return _snowman;
   }

   public void saveProperties(Path path) {
      try (OutputStream _snowman = Files.newOutputStream(path)) {
         this.properties.store(_snowman, "Minecraft server properties");
      } catch (IOException var15) {
         LOGGER.error("Failed to store properties to file: " + path);
      }
   }

   private static <V extends Number> Function<String, V> wrapNumberParser(Function<String, V> parser) {
      return _snowmanx -> {
         try {
            return parser.apply(_snowmanx);
         } catch (NumberFormatException var3) {
            return null;
         }
      };
   }

   protected static <V> Function<String, V> combineParser(IntFunction<V> intParser, Function<String, V> fallbackParser) {
      return _snowmanxx -> {
         try {
            return intParser.apply(Integer.parseInt(_snowmanxx));
         } catch (NumberFormatException var4) {
            return fallbackParser.apply(_snowmanxx);
         }
      };
   }

   @Nullable
   private String getStringValue(String key) {
      return (String)this.properties.get(key);
   }

   @Nullable
   protected <V> V getDeprecated(String key, Function<String, V> stringifier) {
      String _snowman = this.getStringValue(key);
      if (_snowman == null) {
         return null;
      } else {
         this.properties.remove(key);
         return stringifier.apply(_snowman);
      }
   }

   protected <V> V get(String key, Function<String, V> parser, Function<V, String> stringifier, V fallback) {
      String _snowman = this.getStringValue(key);
      V _snowmanx = (V)MoreObjects.firstNonNull(_snowman != null ? parser.apply(_snowman) : null, fallback);
      this.properties.put(key, stringifier.apply(_snowmanx));
      return _snowmanx;
   }

   protected <V> AbstractPropertiesHandler<T>.PropertyAccessor<V> accessor(String key, Function<String, V> parser, Function<V, String> stringifier, V fallback) {
      String _snowman = this.getStringValue(key);
      V _snowmanx = (V)MoreObjects.firstNonNull(_snowman != null ? parser.apply(_snowman) : null, fallback);
      this.properties.put(key, stringifier.apply(_snowmanx));
      return new AbstractPropertiesHandler.PropertyAccessor<>(key, _snowmanx, stringifier);
   }

   protected <V> V get(String key, Function<String, V> parser, UnaryOperator<V> parsedTransformer, Function<V, String> stringifier, V fallback) {
      return this.get(key, _snowmanxx -> {
         V _snowmanxxx = parser.apply(_snowmanxx);
         return _snowmanxxx != null ? parsedTransformer.apply(_snowmanxxx) : null;
      }, stringifier, fallback);
   }

   protected <V> V get(String key, Function<String, V> parser, V fallback) {
      return this.get(key, parser, Objects::toString, fallback);
   }

   protected <V> AbstractPropertiesHandler<T>.PropertyAccessor<V> accessor(String key, Function<String, V> parser, V fallback) {
      return this.accessor(key, parser, Objects::toString, fallback);
   }

   protected String getString(String key, String fallback) {
      return this.get(key, Function.identity(), Function.identity(), fallback);
   }

   @Nullable
   protected String getDeprecatedString(String key) {
      return this.getDeprecated(key, Function.identity());
   }

   protected int getInt(String key, int fallback) {
      return this.get(key, wrapNumberParser(Integer::parseInt), fallback);
   }

   protected AbstractPropertiesHandler<T>.PropertyAccessor<Integer> intAccessor(String key, int fallback) {
      return this.accessor(key, wrapNumberParser(Integer::parseInt), fallback);
   }

   protected int transformedParseInt(String key, UnaryOperator<Integer> transformer, int fallback) {
      return this.get(key, wrapNumberParser(Integer::parseInt), transformer, Objects::toString, fallback);
   }

   protected long parseLong(String key, long fallback) {
      return this.get(key, wrapNumberParser(Long::parseLong), fallback);
   }

   protected boolean parseBoolean(String key, boolean fallback) {
      return this.get(key, Boolean::valueOf, fallback);
   }

   protected AbstractPropertiesHandler<T>.PropertyAccessor<Boolean> booleanAccessor(String key, boolean fallback) {
      return this.accessor(key, Boolean::valueOf, fallback);
   }

   @Nullable
   protected Boolean getDeprecatedBoolean(String key) {
      return this.getDeprecated(key, Boolean::valueOf);
   }

   protected Properties copyProperties() {
      Properties _snowman = new Properties();
      _snowman.putAll(this.properties);
      return _snowman;
   }

   protected abstract T create(DynamicRegistryManager var1, Properties var2);

   public class PropertyAccessor<V> implements Supplier<V> {
      private final String key;
      private final V value;
      private final Function<V, String> stringifier;

      private PropertyAccessor(String key, V value, Function<V, String> stringifier) {
         this.key = key;
         this.value = value;
         this.stringifier = stringifier;
      }

      @Override
      public V get() {
         return this.value;
      }

      public T set(DynamicRegistryManager _snowman, V _snowman) {
         Properties _snowmanxx = AbstractPropertiesHandler.this.copyProperties();
         _snowmanxx.put(this.key, this.stringifier.apply(_snowman));
         return AbstractPropertiesHandler.this.create(_snowman, _snowmanxx);
      }
   }
}
