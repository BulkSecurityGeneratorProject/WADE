package com.idscorporation.wade.domain.uitl.aixm;

/**
 * Created by m.antonini on 24/07/2017.
 */

import aero.aixm.schema.x51.AbstractAIXMFeatureDocument;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ValueNode;
import com.fasterxml.jackson.databind.util.RawValue;
import org.apache.commons.lang3.StringEscapeUtils;
import org.json.JSONException;

import java.io.IOException;

import com.idscorporation.wade.util.json.XML2JSON;

/**
 * Custom Jackson serializer for transforming a AbstractAIXMFeatureType object to JSON.
 */
public class AIXMFeatureSerializer extends JsonSerializer<AbstractAIXMFeatureDocument> {

    public AIXMFeatureSerializer() {

    }
    @Override
    public void serialize(AbstractAIXMFeatureDocument value, JsonGenerator generator,
                          SerializerProvider serializerProvider)
        throws IOException {
        if (value!=null) {
            // From AbstractAIXMFeature to XML
            String xml = value.toString();
            // From XML to JSON
            String json = XML2JSON.toJSON(xml);
            // write JSON
            generator.writeRawValue(json, 0, json.length());
        }
    }

}
