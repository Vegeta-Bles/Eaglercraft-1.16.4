package net.minecraft.util;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import java.lang.reflect.Type;
import javax.annotation.Nullable;
import net.minecraft.text.TranslatableText;
import org.apache.commons.lang3.StringUtils;

public class Identifier implements Comparable<Identifier> {
   public static final Codec<Identifier> CODEC = Codec.STRING.comapFlatMap(Identifier::method_29186, Identifier::toString).stable();
   private static final SimpleCommandExceptionType COMMAND_EXCEPTION = new SimpleCommandExceptionType(new TranslatableText("argument.id.invalid"));
   protected final String namespace;
   protected final String path;

   protected Identifier(String[] id) {
      this.namespace = StringUtils.isEmpty(id[0]) ? "minecraft" : id[0];
      this.path = id[1];
      if (!isNamespaceValid(this.namespace)) {
         throw new InvalidIdentifierException("Non [a-z0-9_.-] character in namespace of location: " + this.namespace + ':' + this.path);
      } else if (!isPathValid(this.path)) {
         throw new InvalidIdentifierException("Non [a-z0-9/._-] character in path of location: " + this.namespace + ':' + this.path);
      }
   }

   public Identifier(String id) {
      this(split(id, ':'));
   }

   public Identifier(String namespace, String path) {
      this(new String[]{namespace, path});
   }

   public static Identifier splitOn(String id, char delimiter) {
      return new Identifier(split(id, delimiter));
   }

   @Nullable
   public static Identifier tryParse(String id) {
      try {
         return new Identifier(id);
      } catch (InvalidIdentifierException var2) {
         return null;
      }
   }

   protected static String[] split(String id, char delimiter) {
      String[] _snowman = new String[]{"minecraft", id};
      int _snowmanx = id.indexOf(delimiter);
      if (_snowmanx >= 0) {
         _snowman[1] = id.substring(_snowmanx + 1, id.length());
         if (_snowmanx >= 1) {
            _snowman[0] = id.substring(0, _snowmanx);
         }
      }

      return _snowman;
   }

   private static DataResult<Identifier> method_29186(String _snowman) {
      try {
         return DataResult.success(new Identifier(_snowman));
      } catch (InvalidIdentifierException var2) {
         return DataResult.error("Not a valid resource location: " + _snowman + " " + var2.getMessage());
      }
   }

   public String getPath() {
      return this.path;
   }

   public String getNamespace() {
      return this.namespace;
   }

   @Override
   public String toString() {
      return this.namespace + ':' + this.path;
   }

   @Override
   public boolean equals(Object _snowman) {
      if (this == _snowman) {
         return true;
      } else if (!(_snowman instanceof Identifier)) {
         return false;
      } else {
         Identifier _snowmanx = (Identifier)_snowman;
         return this.namespace.equals(_snowmanx.namespace) && this.path.equals(_snowmanx.path);
      }
   }

   @Override
   public int hashCode() {
      return 31 * this.namespace.hashCode() + this.path.hashCode();
   }

   public int compareTo(Identifier _snowman) {
      int _snowmanx = this.path.compareTo(_snowman.path);
      if (_snowmanx == 0) {
         _snowmanx = this.namespace.compareTo(_snowman.namespace);
      }

      return _snowmanx;
   }

   public static Identifier fromCommandInput(StringReader reader) throws CommandSyntaxException {
      int _snowman = reader.getCursor();

      while (reader.canRead() && isCharValid(reader.peek())) {
         reader.skip();
      }

      String _snowmanx = reader.getString().substring(_snowman, reader.getCursor());

      try {
         return new Identifier(_snowmanx);
      } catch (InvalidIdentifierException var4) {
         reader.setCursor(_snowman);
         throw COMMAND_EXCEPTION.createWithContext(reader);
      }
   }

   public static boolean isCharValid(char c) {
      return c >= '0' && c <= '9' || c >= 'a' && c <= 'z' || c == '_' || c == ':' || c == '/' || c == '.' || c == '-';
   }

   private static boolean isPathValid(String path) {
      for (int _snowman = 0; _snowman < path.length(); _snowman++) {
         if (!isPathCharacterValid(path.charAt(_snowman))) {
            return false;
         }
      }

      return true;
   }

   private static boolean isNamespaceValid(String namespace) {
      for (int _snowman = 0; _snowman < namespace.length(); _snowman++) {
         if (!isNamespaceCharacterValid(namespace.charAt(_snowman))) {
            return false;
         }
      }

      return true;
   }

   public static boolean isPathCharacterValid(char _snowman) {
      return _snowman == '_' || _snowman == '-' || _snowman >= 'a' && _snowman <= 'z' || _snowman >= '0' && _snowman <= '9' || _snowman == '/' || _snowman == '.';
   }

   private static boolean isNamespaceCharacterValid(char _snowman) {
      return _snowman == '_' || _snowman == '-' || _snowman >= 'a' && _snowman <= 'z' || _snowman >= '0' && _snowman <= '9' || _snowman == '.';
   }

   public static boolean isValid(String id) {
      String[] _snowman = split(id, ':');
      return isNamespaceValid(StringUtils.isEmpty(_snowman[0]) ? "minecraft" : _snowman[0]) && isPathValid(_snowman[1]);
   }

   public static class Serializer implements JsonDeserializer<Identifier>, com.google.gson.JsonSerializer<Identifier> {
      public Serializer() {
      }

      public Identifier deserialize(JsonElement _snowman, Type _snowman, JsonDeserializationContext _snowman) throws JsonParseException {
         return new Identifier(JsonHelper.asString(_snowman, "location"));
      }

      public JsonElement serialize(Identifier _snowman, Type _snowman, JsonSerializationContext _snowman) {
         return new JsonPrimitive(_snowman.toString());
      }
   }
}
