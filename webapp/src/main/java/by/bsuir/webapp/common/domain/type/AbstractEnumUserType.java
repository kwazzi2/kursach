package by.bsuir.webapp.common.domain.type;

import org.hibernate.MappingException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.usertype.ParameterizedType;
import org.hibernate.usertype.UserType;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public abstract class AbstractEnumUserType<E extends Enum, V extends Serializable> implements UserType, ParameterizedType,
        Serializable {
    private final Map<V, E> constantsByValue = new HashMap<>();
    private final Map<E, V> constantsByEnum = new HashMap<>();

    private Class<E> enumClass;
    private final int sqlType;

    protected AbstractEnumUserType(int sqlType) {
        this.sqlType = sqlType;
    }

    @SuppressWarnings({"unchecked"})
    @Override
    public void setParameterValues(Properties parameters) {
        final String enumClassName = parameters.getProperty("enumClass");
        if (enumClassName == null) {
            throw new MappingException("enumClassName parameter not specified");
        }
        try {
            Class<E> localEnumClass = (Class<E>) Class.forName(enumClassName);
            setEnumClass(localEnumClass);
        } catch (ClassNotFoundException e) {
            throw new MappingException("enumClass " + enumClassName + " not found", e);
        } catch (ClassCastException e) {
            throw new MappingException("enumClass " + enumClassName + " not enum class", e);
        }
    }

    public void setEnumClass(Class<E> enumClass) {
        this.enumClass = enumClass;
        constantsByValue.clear();
        for (E e : enumClass.getEnumConstants()) {
            final V value = enumToValue(e);
            constantsByValue.put(value, e);
            constantsByEnum.put(e, value);
        }
    }

    public Class<E> getEnumClass() {
        return enumClass;
    }

    public int getSqlType() {
        return sqlType;
    }

    @Override
    public Object nullSafeGet(ResultSet rs, String[] names, SharedSessionContractImplementor session, Object owner) throws SQLException {
        final V value = get(rs, names[0]);
        return value == null || rs.wasNull() ? null : getEnum(value);
    }

    @SuppressWarnings({"unchecked"})
    @Override
    public void nullSafeSet(PreparedStatement st, Object value, int index, SharedSessionContractImplementor session) throws SQLException {
        if (value == null) {
            st.setNull(index, getSqlType());
        } else {
            final V v = getValue((E) value);
            set(st, v, index);
        }
    }

    @Override
    public Class<E> returnedClass() {
        return getEnumClass();
    }

    @Override
    public int[] sqlTypes() {
        return new int[]{sqlType};
    }

    @Override
    public Object deepCopy(Object value) {
        return value;
    }

    @Override
    public Object replace(Object original, Object target, Object owner) {
        return original;
    }

    @Override
    public boolean isMutable() {
        return false;
    }

    @Override
    public Serializable disassemble(Object value) {
        return (Enum) value;
    }

    @Override
    public Object assemble(Serializable cached, Object owner) {
        return cached;
    }

    @Override
    public boolean equals(Object x, Object y) {
        return x == y;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return this == obj;
    }

    @Override
    public int hashCode(Object x) {
        return x.hashCode();
    }

    protected E getEnum(V order) {
        return constantsByValue.get(order);
    }

    protected V getValue(E e) {
        return constantsByEnum.get(e);
    }

    /**
     * Возвращает набор данных из бд
     *
     * @param rs   таблица с данными
     * @param name имя
     * @return набор данных
     * @throws SQLException
     */
    public abstract V get(ResultSet rs, String name) throws SQLException;

    /**
     * Устанавливает значение
     *
     * @param st    сущность PreparedStatement
     * @param value устанавливаемое значение
     * @param index индекс
     * @throws SQLException
     */
    public abstract void set(PreparedStatement st, V value, int index) throws SQLException;

    protected abstract V enumToValue(E e);
}
