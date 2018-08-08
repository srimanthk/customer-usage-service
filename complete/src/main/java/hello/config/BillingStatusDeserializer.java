package hello.config;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import hello.model.BillingStatus;

import java.io.IOException;

class BillingStatusDeserializer extends JsonDeserializer<BillingStatus> {

    @Override
    public BillingStatus deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
            throws IOException {
        final String jsonValue = jsonParser.getValueAsString();
        return BillingStatus.parse(jsonValue);
    }
}