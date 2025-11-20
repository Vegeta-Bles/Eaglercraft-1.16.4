package net.minecraft.text;

import com.google.common.collect.Lists;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.Nullable;
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
      Language _snowman = Language.getInstance();
      if (_snowman != this.languageCache) {
         this.languageCache = _snowman;
         this.translations.clear();
         String _snowmanx = _snowman.get(this.key);

         try {
            this.setTranslation(_snowmanx);
         } catch (TranslationException var4) {
            this.translations.clear();
            this.translations.add(StringVisitable.plain(_snowmanx));
         }
      }
   }

   private void setTranslation(String translation) {
      Matcher _snowman = ARG_FORMAT.matcher(translation);

      try {
         int _snowmanx = 0;
         int _snowmanxx = 0;

         while (_snowman.find(_snowmanxx)) {
            int _snowmanxxx = _snowman.start();
            int _snowmanxxxx = _snowman.end();
            if (_snowmanxxx > _snowmanxx) {
               String _snowmanxxxxx = translation.substring(_snowmanxx, _snowmanxxx);
               if (_snowmanxxxxx.indexOf(37) != -1) {
                  throw new IllegalArgumentException();
               }

               this.translations.add(StringVisitable.plain(_snowmanxxxxx));
            }

            String _snowmanxxxxx = _snowman.group(2);
            String _snowmanxxxxxx = translation.substring(_snowmanxxx, _snowmanxxxx);
            if ("%".equals(_snowmanxxxxx) && "%%".equals(_snowmanxxxxxx)) {
               this.translations.add(LITERAL_PERCENT_SIGN);
            } else {
               if (!"s".equals(_snowmanxxxxx)) {
                  throw new TranslationException(this, "Unsupported format: '" + _snowmanxxxxxx + "'");
               }

               String _snowmanxxxxxxx = _snowman.group(1);
               int _snowmanxxxxxxxx = _snowmanxxxxxxx != null ? Integer.parseInt(_snowmanxxxxxxx) - 1 : _snowmanx++;
               if (_snowmanxxxxxxxx < this.args.length) {
                  this.translations.add(this.getArg(_snowmanxxxxxxxx));
               }
            }

            _snowmanxx = _snowmanxxxx;
         }

         if (_snowmanxx < translation.length()) {
            String _snowmanxxxxx = translation.substring(_snowmanxx);
            if (_snowmanxxxxx.indexOf(37) != -1) {
               throw new IllegalArgumentException();
            }

            this.translations.add(StringVisitable.plain(_snowmanxxxxx));
         }
      } catch (IllegalArgumentException var11) {
         throw new TranslationException(this, var11);
      }
   }

   private StringVisitable getArg(int index) {
      if (index >= this.args.length) {
         throw new TranslationException(this, index);
      } else {
         Object _snowman = this.args[index];
         if (_snowman instanceof Text) {
            return (Text)_snowman;
         } else {
            return _snowman == null ? NULL_ARGUMENT : StringVisitable.plain(_snowman.toString());
         }
      }
   }

   public TranslatableText copy() {
      return new TranslatableText(this.key, this.args);
   }

   @Override
   public <T> Optional<T> visitSelf(StringVisitable.StyledVisitor<T> visitor, Style style) {
      this.updateTranslations();

      for (StringVisitable _snowman : this.translations) {
         Optional<T> _snowmanx = _snowman.visit(visitor, style);
         if (_snowmanx.isPresent()) {
            return _snowmanx;
         }
      }

      return Optional.empty();
   }

   @Override
   public <T> Optional<T> visitSelf(StringVisitable.Visitor<T> visitor) {
      this.updateTranslations();

      for (StringVisitable _snowman : this.translations) {
         Optional<T> _snowmanx = _snowman.visit(visitor);
         if (_snowmanx.isPresent()) {
            return _snowmanx;
         }
      }

      return Optional.empty();
   }

   @Override
   public MutableText parse(@Nullable ServerCommandSource source, @Nullable Entity sender, int depth) throws CommandSyntaxException {
      Object[] _snowman = new Object[this.args.length];

      for (int _snowmanx = 0; _snowmanx < _snowman.length; _snowmanx++) {
         Object _snowmanxx = this.args[_snowmanx];
         if (_snowmanxx instanceof Text) {
            _snowman[_snowmanx] = Texts.parse(source, (Text)_snowmanxx, sender, depth);
         } else {
            _snowman[_snowmanx] = _snowmanxx;
         }
      }

      return new TranslatableText(this.key, _snowman);
   }

   @Override
   public boolean equals(Object _snowman) {
      if (this == _snowman) {
         return true;
      } else if (!(_snowman instanceof TranslatableText)) {
         return false;
      } else {
         TranslatableText _snowmanx = (TranslatableText)_snowman;
         return Arrays.equals(this.args, _snowmanx.args) && this.key.equals(_snowmanx.key) && super.equals(_snowman);
      }
   }

   @Override
   public int hashCode() {
      int _snowman = super.hashCode();
      _snowman = 31 * _snowman + this.key.hashCode();
      return 31 * _snowman + Arrays.hashCode(this.args);
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
