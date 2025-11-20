package net.minecraft.text;

import com.google.common.collect.Lists;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.Nullable;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.Entity;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.util.Language;

public class TranslatableText extends BaseText implements ParsableText {
   private static final Object[] EMPTY_ARGUMENTS = new Object[0];
   private static final StringVisitable LITERAL_PERCENT_SIGN = StringVisitable.plain("%");
   private static final StringVisitable NULL_ARGUMENT = StringVisitable.plain("null");
   private final String key;
   private final Object[] args;
   @Nullable
   private Language languageCache;
   private final List<StringVisitable> translations = Lists.newArrayList();
   private static final Pattern ARG_FORMAT = Pattern.compile("%(?:(\\d+)\\$)?([A-Za-z%]|$)");

   public TranslatableText(String key) {
      this.key = key;
      this.args = EMPTY_ARGUMENTS;
   }

   public TranslatableText(String key, Object... args) {
      this.key = key;
      this.args = args;
   }

   private void updateTranslations() {
      Language lv = Language.getInstance();
      if (lv != this.languageCache) {
         this.languageCache = lv;
         this.translations.clear();
         String string = lv.get(this.key);

         try {
            this.setTranslation(string);
         } catch (TranslationException var4) {
            this.translations.clear();
            this.translations.add(StringVisitable.plain(string));
         }
      }
   }

   private void setTranslation(String translation) {
      Matcher matcher = ARG_FORMAT.matcher(translation);

      try {
         int i = 0;
         int j = 0;

         while (matcher.find(j)) {
            int k = matcher.start();
            int l = matcher.end();
            if (k > j) {
               String string2 = translation.substring(j, k);
               if (string2.indexOf(37) != -1) {
                  throw new IllegalArgumentException();
               }

               this.translations.add(StringVisitable.plain(string2));
            }

            String string3 = matcher.group(2);
            String string4 = translation.substring(k, l);
            if ("%".equals(string3) && "%%".equals(string4)) {
               this.translations.add(LITERAL_PERCENT_SIGN);
            } else {
               if (!"s".equals(string3)) {
                  throw new TranslationException(this, "Unsupported format: '" + string4 + "'");
               }

               String string5 = matcher.group(1);
               int m = string5 != null ? Integer.parseInt(string5) - 1 : i++;
               if (m < this.args.length) {
                  this.translations.add(this.getArg(m));
               }
            }

            j = l;
         }

         if (j < translation.length()) {
            String string6 = translation.substring(j);
            if (string6.indexOf(37) != -1) {
               throw new IllegalArgumentException();
            }

            this.translations.add(StringVisitable.plain(string6));
         }
      } catch (IllegalArgumentException var11) {
         throw new TranslationException(this, var11);
      }
   }

   private StringVisitable getArg(int index) {
      if (index >= this.args.length) {
         throw new TranslationException(this, index);
      } else {
         Object object = this.args[index];
         if (object instanceof Text) {
            return (Text)object;
         } else {
            return object == null ? NULL_ARGUMENT : StringVisitable.plain(object.toString());
         }
      }
   }

   public TranslatableText copy() {
      return new TranslatableText(this.key, this.args);
   }

   @Environment(EnvType.CLIENT)
   @Override
   public <T> Optional<T> visitSelf(StringVisitable.StyledVisitor<T> visitor, Style style) {
      this.updateTranslations();

      for (StringVisitable lv : this.translations) {
         Optional<T> optional = lv.visit(visitor, style);
         if (optional.isPresent()) {
            return optional;
         }
      }

      return Optional.empty();
   }

   @Override
   public <T> Optional<T> visitSelf(StringVisitable.Visitor<T> visitor) {
      this.updateTranslations();

      for (StringVisitable lv : this.translations) {
         Optional<T> optional = lv.visit(visitor);
         if (optional.isPresent()) {
            return optional;
         }
      }

      return Optional.empty();
   }

   @Override
   public MutableText parse(@Nullable ServerCommandSource source, @Nullable Entity sender, int depth) throws CommandSyntaxException {
      Object[] objects = new Object[this.args.length];

      for (int j = 0; j < objects.length; j++) {
         Object object = this.args[j];
         if (object instanceof Text) {
            objects[j] = Texts.parse(source, (Text)object, sender, depth);
         } else {
            objects[j] = object;
         }
      }

      return new TranslatableText(this.key, objects);
   }

   @Override
   public boolean equals(Object object) {
      if (this == object) {
         return true;
      } else if (!(object instanceof TranslatableText)) {
         return false;
      } else {
         TranslatableText lv = (TranslatableText)object;
         return Arrays.equals(this.args, lv.args) && this.key.equals(lv.key) && super.equals(object);
      }
   }

   @Override
   public int hashCode() {
      int i = super.hashCode();
      i = 31 * i + this.key.hashCode();
      return 31 * i + Arrays.hashCode(this.args);
   }

   @Override
   public String toString() {
      return "TranslatableComponent{key='"
         + this.key
         + '\''
         + ", args="
         + Arrays.toString(this.args)
         + ", siblings="
         + this.siblings
         + ", style="
         + this.getStyle()
         + '}';
   }

   public String getKey() {
      return this.key;
   }

   public Object[] getArgs() {
      return this.args;
   }
}
