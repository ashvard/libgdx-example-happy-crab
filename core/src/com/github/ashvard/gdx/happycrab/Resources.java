package com.github.ashvard.gdx.happycrab;

public interface Resources {

    interface HyperLap2D {
        String PROJECT = "project.dt";
    }

    interface Animations {
        String CRAB = "crab";
        String CRAB_ANIM_FSM = "animation/crab/crab.afsm";

        String GREEN_FISH = "green_fish";
        String GREEN_FISH_FSM = "animation/green_fish/green_fish.afsm";
    }

    interface Backgrounds {
        String BACKGROUND_PNG = "background.png"; //TODO удалено, поправить в первых уровнях
    }

    interface Shaders {
        String BLINK_SHADER = "shaders/blink_shader.vert";
    }

    interface TextureAtlases {
        String PACK = "orig/pack.atlas";
    }

    interface Images {
        String PEARL = "pearl";

        String LIFE_1 = "life1";
    }

}
