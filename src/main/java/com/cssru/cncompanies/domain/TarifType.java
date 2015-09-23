package com.cssru.cncompanies.domain;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.usertype.UserType;

public class TarifType implements UserType {

	public TarifType() {}

	@Override
	public Object assemble(Serializable cached, Object owner)
			throws HibernateException {
		return cached;
	}

	@Override
	public Object deepCopy(Object o) throws HibernateException {
		if (o == null) return null;
		if (! (o instanceof Tarif))
			throw new UnsupportedOperationException("Cannot convert "+o.getClass());
		return new Tarif(((Tarif)o).getTarif());
	}

	@Override
	public Serializable disassemble(Object value) throws HibernateException {
		if (! (value instanceof Integer))
			throw new UnsupportedOperationException("Cannot convert "+value.getClass());
		return new Tarif(((Integer)value).intValue());
	}

	@Override
	public boolean equals(Object o1, Object o2) throws HibernateException {
		if (o1 == null || o2 == null) return false;
		if (!(o1 instanceof Tarif && o2 instanceof Tarif)) return false;
		Tarif t1 = (Tarif)o1;
		Tarif t2 = (Tarif)o2;
		return t1.getTarif() == t2.getTarif();
	}

	@Override
	public int hashCode(Object value) throws HibernateException {
		return value.hashCode();
	}

	@Override
	public boolean isMutable() {
		return true;
	}

	@Override
	public Object nullSafeGet(ResultSet rs, String[] names,
			SessionImplementor implementor, Object owner) throws HibernateException,
			SQLException {
		final Integer value = rs.getInt(names[0]);
		return new Tarif(value);
	}

	@Override
	public void nullSafeSet(PreparedStatement st, Object value, int index,
			SessionImplementor si) throws HibernateException, SQLException {
		if (value == null) {
			st.setNull(index, Types.INTEGER);
			return;
		}
		st.setInt(index, ((Tarif)value).getTarif());
	}

	@Override
	public Object replace(Object original, Object target, Object owner)
			throws HibernateException {
		return original;
	}

	@Override
	public Class<?> returnedClass() {
		return Tarif.class;
	}

	@Override
	public int[] sqlTypes() {
		return new int[] {Types.INTEGER};
	}

}
