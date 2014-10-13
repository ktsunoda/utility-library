package io.berkeley.hibernate.interceptor;


import io.berkeley.hibernate.entity.TimestampedEntity;
import org.apache.commons.lang3.ArrayUtils;
import org.hibernate.EmptyInterceptor;
import org.hibernate.type.Type;

import java.io.Serializable;
import java.util.Date;


@SuppressWarnings("UnusedDeclaration")
public class TimestampInterceptor extends EmptyInterceptor {

    //-------------------------------------------------------------
    // Variables - Private - Static
    //-------------------------------------------------------------

    private static final long SERIAL_VERSION_UID = 1L;
    private static final String CREATED_DATE_PROPERTY_NAME = "createdDate";
    private static final String LAST_MODIFIED_DATE_PROPERTY_NAME = "lastModifiedDate";


    //-------------------------------------------------------------
    // Methods - Overrides
    //-------------------------------------------------------------

    @Override
    public boolean onFlushDirty(Object entity, Serializable id, Object[] currentState, Object[] previousState, String[] propertyNames, Type[] types) {
        boolean dirty = false;
        if (entity instanceof TimestampedEntity) {
            int index = ArrayUtils.indexOf(propertyNames, LAST_MODIFIED_DATE_PROPERTY_NAME);
            if (index != ArrayUtils.INDEX_NOT_FOUND) {
                currentState[index] = new Date();
                dirty = true;
            }
        }

        return dirty;
    }


    @Override
    public boolean onSave(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types) {
        boolean dirty = false;
        if (entity instanceof TimestampedEntity) {
            int index = ArrayUtils.indexOf(propertyNames, CREATED_DATE_PROPERTY_NAME);
            if (index != ArrayUtils.INDEX_NOT_FOUND) {
                state[index] = new Date();
                dirty = true;
            }

            index = ArrayUtils.indexOf(propertyNames, LAST_MODIFIED_DATE_PROPERTY_NAME);
            if (index != ArrayUtils.INDEX_NOT_FOUND) {
                state[index] = new Date();
                dirty = true;
            }
        }

        return dirty;
    }
}
