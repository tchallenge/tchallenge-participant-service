package ru.tchallenge.pilot.service.utility.data;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.google.common.base.Objects;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.io.IOException;

@JsonDeserialize(using = Id.Deserializer.class)
@JsonSerialize(using = Id.Serializer.class)
public final class Id {

    public static final class Deserializer extends StdDeserializer<Id> {

        public Deserializer() {
            super(Id.class);
        }

        @Override
        public Id deserialize(JsonParser parser, DeserializationContext context) throws IOException {
            return new Id(parser.getValueAsString());
        }
    }

    public static final class Serializer extends StdSerializer<Id> {

        public Serializer() {
            super(Id.class);
        }

        @Override
        public void serialize(Id value, JsonGenerator gen, SerializerProvider provider) throws IOException {
            gen.writeString(value != null ? value.toHex() : null);
        }
    }

    private final ObjectId objectId;

    public Id() {
        this(new ObjectId());
    }

    public Id(final String hex) {
        this(new ObjectId(hex));
    }

    public Id(final ObjectId objectId) {
        if (objectId == null) {
            throw new NullPointerException("Object ID is missing");
        }
        this.objectId = objectId;
    }

    public Document toFilter() {
        return new DocumentWrapper(this).getDocument();
    }

    public String toHex() {
        return objectId.toHexString();
    }

    public ObjectId toObjectId() {
        return objectId;
    }

    @Override
    public boolean equals(final Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        final Id another = (Id) object;
        return Objects.equal(objectId, another.objectId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(objectId);
    }
}
