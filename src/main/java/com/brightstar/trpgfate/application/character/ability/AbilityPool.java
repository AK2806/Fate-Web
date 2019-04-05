package com.brightstar.trpgfate.application.character.ability;

import com.alibaba.fastjson.JSON;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

public class AbilityPool {
    public static List<AbilityInfo> getAbilities() {
        return abilities;
    }

    private static final List<AbilityInfo> abilities;

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
        abilities = JSON.parseArray(stringBuilder.toString(), AbilityInfo.class);
    }
}
