package net.minecraft.client.render.model.json;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import java.lang.reflect.Type;

public class ModelTransformation {
   public static final ModelTransformation NONE = new ModelTransformation();
   public final Transformation thirdPersonLeftHand;
   public final Transformation thirdPersonRightHand;
   public final Transformation firstPersonLeftHand;
   public final Transformation firstPersonRightHand;
   public final Transformation head;
   public final Transformation gui;
   public final Transformation ground;
   public final Transformation fixed;

   private ModelTransformation() {
      this(
         Transformation.IDENTITY,
         Transformation.IDENTITY,
         Transformation.IDENTITY,
         Transformation.IDENTITY,
         Transformation.IDENTITY,
         Transformation.IDENTITY,
         Transformation.IDENTITY,
         Transformation.IDENTITY
      );
   }

   public ModelTransformation(ModelTransformation other) {
      this.thirdPersonLeftHand = other.thirdPersonLeftHand;
      this.thirdPersonRightHand = other.thirdPersonRightHand;
      this.firstPersonLeftHand = other.firstPersonLeftHand;
      this.firstPersonRightHand = other.firstPersonRightHand;
      this.head = other.head;
      this.gui = other.gui;
      this.ground = other.ground;
      this.fixed = other.fixed;
   }

   public ModelTransformation(
      Transformation thirdPersonLeftHand,
      Transformation thirdPersonRightHand,
      Transformation firstPersonLeftHand,
      Transformation firstPersonRightHand,
      Transformation head,
      Transformation gui,
      Transformation ground,
      Transformation fixed
   ) {
      this.thirdPersonLeftHand = thirdPersonLeftHand;
      this.thirdPersonRightHand = thirdPersonRightHand;
      this.firstPersonLeftHand = firstPersonLeftHand;
      this.firstPersonRightHand = firstPersonRightHand;
      this.head = head;
      this.gui = gui;
      this.ground = ground;
      this.fixed = fixed;
   }

   public Transformation getTransformation(ModelTransformation.Mode renderMode) {
      switch (renderMode) {
         case THIRD_PERSON_LEFT_HAND:
            return this.thirdPersonLeftHand;
         case THIRD_PERSON_RIGHT_HAND:
            return this.thirdPersonRightHand;
         case FIRST_PERSON_LEFT_HAND:
            return this.firstPersonLeftHand;
         case FIRST_PERSON_RIGHT_HAND:
            return this.firstPersonRightHand;
         case HEAD:
            return this.head;
         case GUI:
            return this.gui;
         case GROUND:
            return this.ground;
         case FIXED:
            return this.fixed;
         default:
            return Transformation.IDENTITY;
      }
   }

   public boolean isTransformationDefined(ModelTransformation.Mode renderMode) {
      return this.getTransformation(renderMode) != Transformation.IDENTITY;
   }

   public static class Deserializer implements JsonDeserializer<ModelTransformation> {
      protected Deserializer() {
      }

      public ModelTransformation deserialize(JsonElement _snowman, Type _snowman, JsonDeserializationContext _snowman) throws JsonParseException {
         JsonObject _snowmanxxx = _snowman.getAsJsonObject();
         Transformation _snowmanxxxx = this.parseModelTransformation(_snowman, _snowmanxxx, "thirdperson_righthand");
         Transformation _snowmanxxxxx = this.parseModelTransformation(_snowman, _snowmanxxx, "thirdperson_lefthand");
         if (_snowmanxxxxx == Transformation.IDENTITY) {
            _snowmanxxxxx = _snowmanxxxx;
         }

         Transformation _snowmanxxxxxx = this.parseModelTransformation(_snowman, _snowmanxxx, "firstperson_righthand");
         Transformation _snowmanxxxxxxx = this.parseModelTransformation(_snowman, _snowmanxxx, "firstperson_lefthand");
         if (_snowmanxxxxxxx == Transformation.IDENTITY) {
            _snowmanxxxxxxx = _snowmanxxxxxx;
         }

         Transformation _snowmanxxxxxxxx = this.parseModelTransformation(_snowman, _snowmanxxx, "head");
         Transformation _snowmanxxxxxxxxx = this.parseModelTransformation(_snowman, _snowmanxxx, "gui");
         Transformation _snowmanxxxxxxxxxx = this.parseModelTransformation(_snowman, _snowmanxxx, "ground");
         Transformation _snowmanxxxxxxxxxxx = this.parseModelTransformation(_snowman, _snowmanxxx, "fixed");
         return new ModelTransformation(_snowmanxxxxx, _snowmanxxxx, _snowmanxxxxxxx, _snowmanxxxxxx, _snowmanxxxxxxxx, _snowmanxxxxxxxxx, _snowmanxxxxxxxxxx, _snowmanxxxxxxxxxxx);
      }

      private Transformation parseModelTransformation(JsonDeserializationContext ctx, JsonObject json, String key) {
         return json.has(key) ? (Transformation)ctx.deserialize(json.get(key), Transformation.class) : Transformation.IDENTITY;
      }
   }

   public static enum Mode {
      NONE,
      THIRD_PERSON_LEFT_HAND,
      THIRD_PERSON_RIGHT_HAND,
      FIRST_PERSON_LEFT_HAND,
      FIRST_PERSON_RIGHT_HAND,
      HEAD,
      GUI,
      GROUND,
      FIXED;

      private Mode() {
      }

      public boolean isFirstPerson() {
         return this == FIRST_PERSON_LEFT_HAND || this == FIRST_PERSON_RIGHT_HAND;
      }
   }
}
