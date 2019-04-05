package com.brightstar.trpgfate.application.character.stunt;

import com.alibaba.fastjson.JSON;
import com.brightstar.trpgfate.application.character.ability.AbilityPool;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

public class PresetStuntPool {
    public static List<StuntInfo> getStunts() {
        return stunts;
    }

    private static final List<StuntInfo> stunts;

    static {
        StringBuilder stringBuilder = new StringBuilder();
        try (InputStreamReader reader = new InputStreamReader(new ClassPathResource("ability.json").getInputStream())) {
            char[] charSeq = new char[2048];
            while (reader.read(charSeq, 0, 2048) == 2048) {
                stringBuilder.append(charSeq);
                Arrays.fill(charSeq, '\0');
            }
            stringBuilder.append(charSeq);
        } catch (IOException e) {
            e.printStackTrace();
        }
        stunts = JSON.parseArray(stringBuilder.toString(), StuntInfo.class);
    }
}
