package com.renderer;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;

import java.util.HashMap;
import java.util.Map;

/**
 * Manage the texture library and provide an easy way to handle them
 */
public class SpriteStorage {

    //singleton instance
    private static SpriteStorage instance;

    //texture dictionary
    private Map<String, Texture> textures;

    /**
     * Private constructor, to make sure that will have only one instance of SpriteStorage.
     */
    private SpriteStorage() {
        //don't remove the private access modifier
        textures = new HashMap<String, Texture>();
    }

    /**
     * This function is to test SpriteStorage, don't call it.
     *
     * @param args Arguments
     */
    public static void main(String[] args) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        ApplicationAdapter emptyGame = new ApplicationAdapter() {
            @Override
            public void create() {
                super.create();
                SpriteStorage.getInstance().loadAssets();
            }
        };
        new LwjglApplication(emptyGame, config);
    }

    /**
     * Get singleton instance from SpriteStorage.
     *
     * @return Singleton instance.
     */
    public static SpriteStorage getInstance() {
        if (instance == null)
            instance = new SpriteStorage();
        return instance;
    }

    /**
     * Get a texture from the code.
     *
     * @param code Code from the dictionary.
     * @return Texture.
     */
    public Texture getTexture(String code) {
        return textures.get(code);
    }

    /**
     * Load the assets described in assets/assetsDictionary.json.
     */
    public void loadAssets() {

        FileHandle file = Gdx.files.internal("assetsDictionary.json");

        byte[] bites = file.readBytes();

        String text = new String(bites);

        JsonValue root = new JsonReader().parse(text);

        JsonValue data = root.get("Data");

        for (JsonValue json : data) {
            try {
                textures.put(json.get(0).asString(), new Texture(json.get(1).asString()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
