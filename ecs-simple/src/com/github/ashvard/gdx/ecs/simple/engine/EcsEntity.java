package com.github.ashvard.gdx.ecs.simple.engine;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.ObjectMap;

public class EcsEntity {

    private final int id;
    private ObjectMap<Class, EcsComponent> components = new ObjectMap<Class, EcsComponent>();

    EcsEntity(int id) {
        this.id = id;
    }

    void addComponent(EcsComponent component) {
        if (components.containsKey(component.getClass())) {
            Gdx.app.log("warn", "Объект уже содержит компонент: " + component.getClass().getSimpleName());
            return;
        }
        components.put(component.getClass(), component);
    }

    <T extends EcsComponent> void deleteComponent(Class<T> clazz) {
        components.remove(clazz);
    }

    public <T extends EcsComponent> T getComponent(Class<T> clazz) {
        return (T) components.get(clazz);
    }

    public int getId() {
        return id;
    }

}
