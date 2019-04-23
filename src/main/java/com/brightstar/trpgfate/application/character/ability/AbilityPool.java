package com.brightstar.trpgfate.application.character.ability;

import com.alibaba.fastjson.JSON;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

@Component
public class AbilityPool {
    public List<AbilityInfo> getAbilities() {
        return abilities;
    }

    private final List<AbilityInfo> abilities;

    public AbilityPool() {
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
        abilities = JSON.parseArray(stringBuilder.toString(), AbilityInfo.class);
    }
}
