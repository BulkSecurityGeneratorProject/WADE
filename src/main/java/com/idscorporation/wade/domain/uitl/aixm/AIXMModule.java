package com.idscorporation.wade.domain.uitl.aixm;

import aero.aixm.schema.x51.AbstractAIXMFeatureDocument;
import com.bedatadriven.jackson.datatype.jts.parsers.*;
import com.bedatadriven.jackson.datatype.jts.serialization.GeometryDeserializer;
import com.bedatadriven.jackson.datatype.jts.serialization.GeometrySerializer;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.vividsolutions.jts.geom.*;

/**
 * Created by m.antonini on 24/07/2017.
 */
public class AIXMModule extends SimpleModule {

    public AIXMModule() {
        super("AIXMModule", new Version(1, 0, 0, (String)null, "com.antonini.util.aixm", "jackson-datatype-aixm"));
        this.addSerializer(AbstractAIXMFeatureDocument.class, new AIXMFeatureSerializer());
        this.addDeserializer(AbstractAIXMFeatureDocument.class, new AIXMFeatureDeserializer());

    }

    public void setupModule(SetupContext context) {
        super.setupModule(context);
    }
}
