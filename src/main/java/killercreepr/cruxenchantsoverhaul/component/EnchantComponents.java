package killercreepr.cruxenchantsoverhaul.component;


import killercreepr.crux.api.component.DataComponentType;
import killercreepr.crux.api.component.parser.hybrid.PersistTextParser;
import killercreepr.crux.core.Crux;
import killercreepr.crux.core.registries.CruxRegistries;

import java.util.function.UnaryOperator;

public class EnchantComponents {
    public static void register(){}
    public static final DataComponentType<Integer> MAGIC_CAPACITY = register("magic_capacity", builder ->
        builder.persistTextParser(PersistTextParser.INTEGER.createInput(Crux.key("magic_capacity"))));

    public static final DataComponentType<Integer> ENCHANTS_MAX_LEVEL_ADDON = register("enchants_max_level_addon", builder ->
        builder.persistTextParser(PersistTextParser.INTEGER.createInput(Crux.key("enchants_max_level_addon"))));

    private static <T> DataComponentType<T> register(String id, UnaryOperator<DataComponentType.Builder<T>> builderOperator){
        return CruxRegistries.DATA_COMPONENT_TYPE.register(Crux.key(id), builderOperator.apply(DataComponentType.builder()).build());
    }
}
