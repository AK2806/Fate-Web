package com.brightstar.trpgfate.application.character.stunt;

import com.alibaba.fastjson.JSON;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

@Component
public class PresetStuntPool {
    public List<PresetStuntInfo> getStunts() {
        return stunts;
    }

    private final List<PresetStuntInfo> stunts;

    public PresetStuntPool() {
        StringBuilder stringBuilder = new StringBuilder();
        try (InputStreamReader reader = new InputStreamReader(new ClassPathResource("preset_stunt.json").getInputStream())) {
            char[] charSeq = new char[2048];
            while (reader.read(charSeq, 0, 2048) == 2048) {
                stringBuilder.append(charSeq);
                Arrays.fill(charSeq, '\0');
            }
            stringBuilder.append(charSeq);
        } catch (IOException e) {
            e.printStackTrace();
        }
        stunts = JSON.parseArray(stringBuilder.toString(), PresetStuntInfo.class);
    }
}
