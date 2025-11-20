package net.minecraft.text;

import com.google.common.collect.ImmutableList;
import java.util.List;
import java.util.Optional;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.util.Unit;

public interface StringVisitable {
   Optional<Unit> TERMINATE_VISIT = Optional.of(Unit.INSTANCE);
   StringVisitable EMPTY = new StringVisitable() {
      @Override
      public <T> Optional<T> visit(StringVisitable.Visitor<T> visitor) {
         return Optional.empty();
      }

      @Environment(EnvType.CLIENT)
      @Override
      public <T> Optional<T> visit(StringVisitable.StyledVisitor<T> styledVisitor, Style style) {
         return Optional.empty();
      }
   };

   <T> Optional<T> visit(StringVisitable.Visitor<T> visitor);

   @Environment(EnvType.CLIENT)
   <T> Optional<T> visit(StringVisitable.StyledVisitor<T> styledVisitor, Style style);

   static StringVisitable plain(final String string) {
      return new StringVisitable() {
         @Override
         public <T> Optional<T> visit(StringVisitable.Visitor<T> visitor) {
            return visitor.accept(string);
         }

         @Environment(EnvType.CLIENT)
         @Override
         public <T> Optional<T> visit(StringVisitable.StyledVisitor<T> styledVisitor, Style style) {
            return styledVisitor.accept(style, string);
         }
      };
   }

   @Environment(EnvType.CLIENT)
   static StringVisitable styled(final String string, final Style style) {
      return new StringVisitable() {
         @Override
         public <T> Optional<T> visit(StringVisitable.Visitor<T> visitor) {
            return visitor.accept(string);
         }

         @Override
         public <T> Optional<T> visit(StringVisitable.StyledVisitor<T> styledVisitor, Style stylex) {
            return styledVisitor.accept(style.withParent(style), string);
         }
      };
   }

   @Environment(EnvType.CLIENT)
   static StringVisitable concat(StringVisitable... visitables) {
      return concat(ImmutableList.copyOf(visitables));
   }

   @Environment(EnvType.CLIENT)
   static StringVisitable concat(final List<StringVisitable> visitables) {
      return new StringVisitable() {
         @Override
         public <T> Optional<T> visit(StringVisitable.Visitor<T> visitor) {
            for (StringVisitable lv : visitables) {
               Optional<T> optional = lv.visit(visitor);
               if (optional.isPresent()) {
                  return optional;
               }
            }

            return Optional.empty();
         }

         @Override
         public <T> Optional<T> visit(StringVisitable.StyledVisitor<T> styledVisitor, Style style) {
            for (StringVisitable lv : visitables) {
               Optional<T> optional = lv.visit(styledVisitor, style);
               if (optional.isPresent()) {
                  return optional;
               }
            }

            return Optional.empty();
         }
      };
   }

   default String getString() {
      StringBuilder stringBuilder = new StringBuilder();
      this.visit(string -> {
         stringBuilder.append(string);
         return Optional.empty();
      });
      return stringBuilder.toString();
   }

   @Environment(EnvType.CLIENT)
   public interface StyledVisitor<T> {
      Optional<T> accept(Style style, String asString);
   }

   public interface Visitor<T> {
      Optional<T> accept(String asString);
   }
}
