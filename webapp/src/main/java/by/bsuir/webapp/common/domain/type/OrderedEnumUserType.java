package by.bsuir.webapp.common.domain.type;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

public class OrderedEnumUserType<E extends Enum> extends AbstractEnumUserType<E, Integer> implements Serializable {

    public OrderedEnumUserType() {
        super(Types.INTEGER);
    }

    @Override
    public Integer get(ResultSet rs, String name) throws SQLException {
        return rs.getInt(name);
    }

    @Override
    public void set(PreparedStatement st, Integer value, int index) throws SQLException {
        st.setInt(index, value);
    }

    @Override
    protected Integer enumToValue(E e) {
        return e instanceof OrderedEnum ? ((OrderedEnum) e).getOrder() : e.ordinal();
    }

}
