package com.github.ashvard.gdx.ecs.simple.engine.debug;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.utils.Array;
import com.github.ashvard.gdx.ecs.simple.engine.DebugSystem;
import com.github.ashvard.gdx.ecs.simple.engine.EcsSystem;
import com.github.ashvard.gdx.ecs.simple.utils.InputProcessorWrapper;

/**
 * Created by user on 08.01.2019.
 */
public class EcsDebug {

    private boolean isDebugActive;
    private InputProcessorWrapper inputProcessorWrapper;

    private DebugService debugService;
    private DebugUI debugUI;

    public EcsDebug(Array<EcsSystem> systems) {
        debugService = new DebugService(systems);
        debugUI = new DebugUI(debugService);
        inputProcessorWrapper = new InputProcessorWrapper(
                debugUI.getInputProcessor(),
                new InputProcessorWrapper.Predicate() {
                    @Override
                    public boolean apply() {
                        return !isDebugActive;
                    }
                });
    }

    public void addSystem(DebugSystem system) {
        debugService.addSystem(system);
    }

    public void update(float delta) {
        debugUI.render(delta);
    }

    public boolean isDebugActive() {
        return isDebugActive;
    }

    public void setSystemDebugMode(boolean isActive) {
        this.isDebugActive = isActive;
    }

    public void setSystemDebugMode(Class<? extends EcsSystem> system, boolean active) {
        debugService.setDebugMode(system, active);
    }

    public InputProcessor getInputProcessor() {
        return inputProcessorWrapper;
    }

    public void resize(int width, int height) {
        debugUI.resize(width, height);
    }

}
