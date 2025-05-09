package com.github.ashvard.gdx.simple.animation.io;

import com.github.ashvard.gdx.simple.animation.io.dto.AnimationDto;
import org.junit.Assert;
import org.junit.Test;

import java.io.InputStream;

public class JsonConverterTest {

    private final JsonConverter converter = new JsonConverter();
    public static final String CRAB_ANIM = "animation/crab/crab.afsm";

    @Test
    public void testReadingJson() {
        InputStream resourceAsStream = this.getClass().getResourceAsStream("/" + CRAB_ANIM);
        String json = Utils.toString(resourceAsStream);
        AnimationDto from = converter.from(json);
        Assert.assertNotNull(from);
    }

    @Test
    public void testWritingJson() {
        InputStream resourceAsStream = this.getClass().getResourceAsStream("/" + CRAB_ANIM);
        String json = Utils.toString(resourceAsStream);
        AnimationDto from = converter.from(json);
        String to = converter.to(from);

        Assert.assertNotNull(to);
        System.out.println(to);
    }


}
