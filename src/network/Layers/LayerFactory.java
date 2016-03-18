package network.Layers;

/**
 * Created by EmilSebastian on 18-03-2016.
 */
public class LayerFactory {

    public static ILayer createLayer(LayerTypes type){
        switch (type){
            case ONE_DIMENTIONAL:
                return new OneDimentionalLayer();
            case TWO_DIMENTIONAL:
                return null;
        }
        return null;
    }

}
