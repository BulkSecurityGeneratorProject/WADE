package com.idscorporation.wade.domain.uitl.aixm;

/**
 * Created by m.antonini on 24/07/2017.
 */

import aero.aixm.schema.x51.AbstractAIXMFeatureDocument;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import org.apache.xmlbeans.XmlException;
import org.json.JSONException;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import com.idscorporation.wade.util.json.XML2JSON;

/**
 * Custom Jackson deserializer for transforming a JSON object to a AbstractAIXMFeatureType object.
 */
public class AIXMFeatureDeserializer extends JsonDeserializer<AbstractAIXMFeatureDocument> {

    public AIXMFeatureDeserializer() {}

    public AbstractAIXMFeatureDocument deserialize(JsonParser jp, DeserializationContext ctxt)
        throws IOException {
        ObjectCodec oc = jp.getCodec();
        JsonNode root = null;
        if (oc!=null)
            root = (JsonNode)oc.readTree(jp);
        if (root!=null) {
            try {
                // JSON
                String json = root.toString();
                // From JSON to XML
                String newXml = XML2JSON.toXML(json);
                // From XML to AbstractAIXMFeature
                AbstractAIXMFeatureDocument abstractAIXMFeatureDocument = AbstractAIXMFeatureDocument.Factory.parse(new ByteArrayInputStream(newXml.getBytes()));
                return abstractAIXMFeatureDocument;
            } catch (XmlException xmlException) {

            }
        }
        return null;
    }
}
