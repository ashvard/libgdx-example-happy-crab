package com.github.ashvard.gdx.happycrab.screen.level.level1.script;

public interface GreenFishAnim {

    interface State {
        String SPIKE_IDLE = "spike_idle";
        String SWIMMING = "swimming";
        String TO_SPIKE = "to_spike";
        String FROM_SPIKE = "from_spike";
        String DEATH = "death";
        String IDLE = "idle";
    }

    interface Transition {
        String IS_IDLE = "is_idle";
        String IS_DEATH = "is_death";
        String IS_SWIMMING = "is_swimming";
        String IS_TO_SPIKE = "is_to_spike";
        String IS_TO_SPIKE_IDLE = "is_to_spike_idle";
        String IS_TO_SPIKE_FROM = "is_to_spike_from";
    }

}
