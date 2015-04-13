package com.renderer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;

import java.util.HashMap;
import java.util.Map;

/**
 * Manage the texture library and provide an easy way to handle them
 */
public class SpriteStorage
{

    //singleton instance
    private static SpriteStorage instance;

    //texture dictionary
    private Map<String, Texture> textures;

    /**
     * Private constructor, to make sure that will have only one instance of SpriteStorage.
     */
    private SpriteStorage()
    {
        //don't remove the private access modifier
        textures = new HashMap<String, Texture>();
    }

    /**
     * Get singleton instance from SpriteStorage.
     *
     * @return Singleton instance.
     */
    public static SpriteStorage getInstance()
    {
        if (instance == null) instance = new SpriteStorage();
        return instance;
    }

    /**
     * Get a texture from the code.
     *
     * @param code Code from the dictionary.
     * @return Texture.
     */
    public Texture getTexture(String code)
    {
        if (textures.containsKey(code))
            return textures.get(code);
        else return null;
    }

    /**
     * Load the assets described in assets/assetsDictionary.json.
     */
    public void loadAssets()
    {

        FileHandle file = Gdx.files.internal("assetsDictionary.json");

        byte[] bites = file.readBytes();

        String text = new String(bites);

        JsonValue root = new JsonReader().parse(text);

        JsonValue data = root.get("Data");

        for (JsonValue json : data)
        {
            try
            {
                Texture found = new Texture(json.get(1).asString());
                textures.put(json.get(0).asString(), found);
            } catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }
}
