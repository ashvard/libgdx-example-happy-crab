package com.github.ashvard.gdx.ecs.simple.engine.debug;


import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.OrderedMap;
import com.github.ashvard.gdx.ecs.simple.engine.DebugSystem;
import com.github.ashvard.gdx.ecs.simple.engine.EcsSystem;

import static java.lang.String.format;


/**
 * Сервис, выводящий на экран отладочную информацию.
 */
public class DebugService {

    private OrderedMap<Class<? extends EcsSystem>, EcsSystem> systems = new OrderedMap<Class<? extends EcsSystem>, EcsSystem>();
    private Array<com.github.ashvard.gdx.ecs.simple.engine.DebugSystem> systemsList = new Array<com.github.ashvard.gdx.ecs.simple.engine.DebugSystem>();

    public DebugService(Array<EcsSystem> systems) {
        for (com.github.ashvard.gdx.ecs.simple.engine.DebugSystem debugSystem : filterDebugSystems(systems)) {
            addSystem(debugSystem);
        }
    }

    public void addSystem(com.github.ashvard.gdx.ecs.simple.engine.DebugSystem system) {
        systems.put(system.getClass(), system);
        systemsList.add(system);
        setDebugMode(system.getClass(), true);
    }

    public void setDebugMode(Class<? extends EcsSystem> system, boolean active) {
        EcsSystem ecsSystem = systems.get(system);
        if (ecsSystem == null) throw new IllegalArgumentException(format("Система '%s' не найдена", system));
        if (ecsSystem instanceof com.github.ashvard.gdx.ecs.simple.engine.DebugSystem) {
            ((com.github.ashvard.gdx.ecs.simple.engine.DebugSystem) ecsSystem).setDebugMode(active);
        } else {
            System.out.println(format("Система '%s' не поддерживает режим отладки", system)); //todo логгер впилить
        }
    }

    public Array<com.github.ashvard.gdx.ecs.simple.engine.DebugSystem> getSystems() {
        return systemsList;
    }

    public boolean isSystemActive(Class<? extends EcsSystem> ecsSystem) {
        if (!com.github.ashvard.gdx.ecs.simple.engine.DebugSystem.class.isAssignableFrom(ecsSystem)) {
            System.out.println(format("Система '%s' не поддерживает режим отладки, не может быть активна", ecsSystem)); //todo логгер впилить
            return false;
        }
        return ((com.github.ashvard.gdx.ecs.simple.engine.DebugSystem)systems.get(ecsSystem)).isDebugMode();
    }

    private Array<com.github.ashvard.gdx.ecs.simple.engine.DebugSystem> filterDebugSystems(Array<EcsSystem> systems) {
        Array<com.github.ashvard.gdx.ecs.simple.engine.DebugSystem> result = new Array<com.github.ashvard.gdx.ecs.simple.engine.DebugSystem>();
        for (EcsSystem system : systems) {
            if (system instanceof com.github.ashvard.gdx.ecs.simple.engine.DebugSystem) result.add((DebugSystem) system);
        }
        return result;
    }

}
