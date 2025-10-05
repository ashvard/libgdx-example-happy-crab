package com.github.ashvard.gdx.happycrab.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.github.ashvard.gdx.happycrab.Resources;
import com.github.ashvard.gdx.happycrab.screen.level.level1.systems.components.ComponentListener;
import com.github.ashvard.gdx.happycrab.screen.level.level1.systems.components.HeroComponent;
import com.github.ashvard.gdx.simple.structure.GameSettings;
import com.github.ashvard.gdx.simple.structure.screen.AbstractGameScreen;


public class HUD extends AbstractGameScreen implements ComponentListener<HeroComponent> {

    private Stage stage;
    private Table rootTable;
    private Table table;

    private Label pearlsCountLabel;

    private Label textLabel;
    private Label.LabelStyle textLabelStyle;
    private BitmapFont font;

    private Group heroLifeGroup;

    private int pearlsCount;
    private int lifeCount;

    @Override
    public void show() {
        GameSettings gameSettings = getGameManager().gameSettings;
        stage = new Stage(new ExtendViewport(gameSettings.getVirtualScreenWidth(), gameSettings.getVirtualScreenHeight()));
        rootTable = createRootTable();

        heroLifeGroup = new Group();
        // Add widgets to the table here.
        table = createTable(heroLifeGroup);
        rootTable.add(table).top().left().padLeft(8).padTop(24).expand();

        //rootTable.setDebug(true); // This is optional, but enables debug lines for tables.
        rootTable.pack();

        stage.addActor(rootTable);
    }

    private Table createRootTable() {
        rootTable = new Table();
        rootTable.setFillParent(true);

        return rootTable;
    }

    private Table createTable(Group heroLifeGroup) {
        AssetManager assetManager = getGameManager().assetManager;
        TextureAtlas textureAtlas = assetManager.get(Resources.TextureAtlases.PACK);
        TextureAtlas.AtlasRegion pearlAtlasRegion = textureAtlas.findRegion(Resources.Images.PEARL);

        Image pearlsCountImage = new Image(pearlAtlasRegion);

        updateFont(getGameManager().gameSettings.getVirtualScreenWidth());
        textLabelStyle = createLabelStyle(font);
        pearlsCountLabel = new Label(0 + "", textLabelStyle);

        Table table = new Table();
        table.columnDefaults(1).width(50);
        table.add(heroLifeGroup).left().pad(2).colspan(2);
        table.row();
        table.add(pearlsCountImage).left().pad(4);
        table.add(pearlsCountLabel).left().fillX();

        //table.debug();
        table.pack();
        return table;
    }

    private void changeHeroLife(Group group, int lifeCount) {
        if (group.getChildren().size == lifeCount) {
            return;
        }

        AssetManager assetManager = getGameManager().assetManager;
        TextureAtlas textureAtlas = assetManager.get(Resources.TextureAtlases.PACK);
        group.clear();
        for (int i = 0; i < lifeCount; i++) {
            Image image = new Image(textureAtlas.findRegion(Resources.Images.LIFE_1));
            image.setPosition(i * 24, 0);
            group.addActor(image);
        }
    }

    @Override
    public void render(float delta) {
        pearlsCountLabel.setText(pearlsCount);
        changeHeroLife(heroLifeGroup, lifeCount);

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    @Override
    public void update(HeroComponent component) {
        pearlsCount = component.getPearlsCount();
        lifeCount = component.getLifeCount();
    }

    private void createOrUpdateFontAndLabels(String text, int virtualHeight) {
        updateFont(virtualHeight);
        updateLabelsStyle();
        createOrUpdateLabels(text);
    }

    private void updateFont(int virtualHeight) {
        int size = Gdx.graphics.getHeight() * 24 / virtualHeight;
        font = ScreenUtils.generateBitmapFont(Gdx.files.internal("fonts/unlearn/unlearn2.ttf"), Color.WHITE, size);
    }

    private Label.LabelStyle createLabelStyle(BitmapFont font) {
        Label.LabelStyle result = new Label.LabelStyle();
        result.font = font;
        result.fontColor = Color.WHITE;

        return result;
    }

    private void updateLabelsStyle() {
        if (textLabelStyle == null) {
            textLabelStyle = createLabelStyle(font);
        }
        textLabelStyle.font = font;
    }

    private void createOrUpdateLabels(String text) {
        if (textLabel == null) {
            textLabel = ScreenUtils.createLabel(text, textLabelStyle);
        }
        textLabel.setStyle(textLabelStyle);
    }

}
