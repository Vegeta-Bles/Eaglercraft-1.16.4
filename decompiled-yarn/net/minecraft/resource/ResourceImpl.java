package net.minecraft.resource;

import com.google.gson.JsonObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import javax.annotation.Nullable;
import net.minecraft.resource.metadata.ResourceMetadataReader;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import org.apache.commons.io.IOUtils;

public class ResourceImpl implements Resource {
   private final String packName;
   private final Identifier id;
   private final InputStream inputStream;
   private final InputStream metaInputStream;
   private boolean readMetadata;
   private JsonObject metadata;

   public ResourceImpl(String packName, Identifier id, InputStream inputStream, @Nullable InputStream metaInputStream) {
      this.packName = packName;
      this.id = id;
      this.inputStream = inputStream;
      this.metaInputStream = metaInputStream;
   }

   @Override
   public Identifier getId() {
      return this.id;
   }

   @Override
   public InputStream getInputStream() {
      return this.inputStream;
   }

   public boolean hasMetadata() {
      return this.metaInputStream != null;
   }

   @Nullable
   @Override
   public <T> T getMetadata(ResourceMetadataReader<T> metaReader) {
      if (!this.hasMetadata()) {
         return null;
      } else {
         if (this.metadata == null && !this.readMetadata) {
            this.readMetadata = true;
            BufferedReader _snowman = null;

            try {
               _snowman = new BufferedReader(new InputStreamReader(this.metaInputStream, StandardCharsets.UTF_8));
               this.metadata = JsonHelper.deserialize(_snowman);
            } finally {
               IOUtils.closeQuietly(_snowman);
            }
         }

         if (this.metadata == null) {
            return null;
         } else {
            String _snowman = metaReader.getKey();
            return this.metadata.has(_snowman) ? metaReader.fromJson(JsonHelper.getObject(this.metadata, _snowman)) : null;
         }
      }
   }

   @Override
   public String getResourcePackName() {
      return this.packName;
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (!(o instanceof ResourceImpl)) {
         return false;
      } else {
         ResourceImpl _snowman = (ResourceImpl)o;
         if (this.id != null ? this.id.equals(_snowman.id) : _snowman.id == null) {
            return this.packName != null ? this.packName.equals(_snowman.packName) : _snowman.packName == null;
         } else {
            return false;
         }
      }
   }

   @Override
   public int hashCode() {
      int _snowman = this.packName != null ? this.packName.hashCode() : 0;
      return 31 * _snowman + (this.id != null ? this.id.hashCode() : 0);
   }

   @Override
   public void close() throws IOException {
      this.inputStream.close();
      if (this.metaInputStream != null) {
         this.metaInputStream.close();
      }
   }
}
