package cz.spacks.worms.model.map;


import cz.spacks.worms.controller.properties.CollisionState;
import cz.spacks.worms.model.materials.MaterialEnum;
import cz.spacks.worms.model.objects.ItemsCount;

import java.awt.*;
import java.util.List;


public class Material {

    public MaterialEnum type;
    public Color color;
    public CollisionState state;
    public int transparency;
    public List<ItemsCount> minedItems;

    public Material(MaterialEnum type, Color color, CollisionState state, int transparency, List<ItemsCount> minedItems) {
        this.type = type;
        this.color = color;
        this.state = state;
        this.transparency = transparency;
        this.minedItems = minedItems;
    }
}