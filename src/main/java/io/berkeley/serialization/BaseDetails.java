package io.berkeley.serialization;


import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.apache.commons.collections4.CollectionUtils;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;


@SuppressWarnings("UnusedDeclaration")
abstract public class BaseDetails<D extends BaseDetails, K> {

    //------------------------------------------------------------------------------------------------
    // Methods - Public - Static
    //------------------------------------------------------------------------------------------------

    public static <D extends BaseDetails, M extends Model> List<D> fromModels(Class<D> detailsType, Iterator<M> modelIterator) {
        List<D> detailsSet = Lists.newArrayList();
        if (modelIterator != null) {
            while (modelIterator.hasNext()) {
                try {
                    D details = detailsType.newInstance();
                    //noinspection unchecked
                    detailsSet.add((D) details.fromModel(modelIterator.next()));
                } catch (InstantiationException | IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return detailsSet;
    }


    public static <D extends BaseDetails, M extends Model> List<D> fromModels(Class<D> detailsType, Collection<M> models) {
        if (CollectionUtils.isNotEmpty(models)) {
            return fromModels(detailsType, models.iterator());
        }
        return Lists.newArrayList();
    }


    public static <D extends BaseDetails<D, K>, K, M extends Model<K>> Set<M> toModels(Class<M> modelType, List<D> detailsList, Set<M> modelSet) {
        return toModels(modelType, detailsList, modelSet, false);
    }


    public static <D extends BaseDetails<D, K>, K, M extends Model<K>> Set<M> toModels(Class<M> modelType, List<D> detailsList, Set<M> modelSet, boolean modify) {
        if (detailsList != null) {
            Set<M> newModelSet = Sets.newHashSet();
            Map<K, M> idToModel = Maps.newHashMap();
            if (modelSet != null) {
                for (M model : modelSet) {
                    idToModel.put(model.getId(), model);
                }
            }

            for (D details : detailsList) {
                M model;
                if (idToModel.containsKey(details.getId())) {
                    model = idToModel.get(details.getId());
                } else {
                    try {
                        model = modelType.newInstance();
                    } catch (InstantiationException | IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                }
                //noinspection unchecked
                newModelSet.add(details.toModel(model));
            }

            if (!modify || modelSet == null) {
                return newModelSet;
            } else {
                modelSet.clear();
                modelSet.addAll(newModelSet);
            }
        }

        return modelSet;
    }


    //------------------------------------------------------------------------------------------------
    // Methods - Public - Abstract
    //------------------------------------------------------------------------------------------------

    public abstract K getId();


    public abstract D fromModel(Model<K> model);


    public abstract <M extends Model<K>> M toModel(M model);
}
