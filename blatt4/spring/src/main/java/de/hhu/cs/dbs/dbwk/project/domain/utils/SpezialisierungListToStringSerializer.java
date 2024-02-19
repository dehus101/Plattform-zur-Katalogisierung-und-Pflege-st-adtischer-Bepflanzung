package de.hhu.cs.dbs.dbwk.project.domain.utils;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import de.hhu.cs.dbs.dbwk.project.domain.model.Spezialisierung;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class SpezialisierungListToStringSerializer extends JsonSerializer<List<Spezialisierung>> {

    @Override
    public void serialize(List<Spezialisierung> spezialisierungen, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        String spezialgebiete = spezialisierungen.stream()
                .map(Spezialisierung::getSpezialgebiet)
                .collect(Collectors.joining(", "));
        jsonGenerator.writeObject(spezialgebiete);
    }
}
