package io.berkeley.hibernate.id;


import com.google.common.io.BaseEncoding;
import com.google.common.primitives.Bytes;
import com.google.common.primitives.Longs;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.id.IdentifierGenerator;

import java.io.Serializable;
import java.util.UUID;


@SuppressWarnings("UnusedDeclaration")
public class BIDIdentifierGenerator
        implements IdentifierGenerator {

    //-------------------------------------------------------------
    // Variables - Private - Static
    //-------------------------------------------------------------

    private static BaseEncoding BASE64_URL_ENCODING = BaseEncoding.base64Url().omitPadding();


    //-------------------------------------------------------------
    // Implementations - IdentifierGenerator
    //-------------------------------------------------------------

    public Serializable generate(SessionImplementor session, Object object)
            throws HibernateException {
        UUID uuid = UUID.randomUUID();

        return BASE64_URL_ENCODING.encode(Bytes.concat(Longs.toByteArray(uuid.getMostSignificantBits()), Longs.toByteArray(uuid.getLeastSignificantBits()))).toLowerCase();
    }
}
