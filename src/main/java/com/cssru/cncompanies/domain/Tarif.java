package com.cssru.cncompanies.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Tarif implements Serializable {
	private static final long serialVersionUID = 1L;
	public static final int TARIF_TESTING = 0;
	public static final int TARIF_x5 = 1;
	public static final int TARIF_x10 = 2;
	public static final int TARIF_x20 = 3;
	public static final int TARIF_x50 = 4;
	public static final int TARIF_x100 = 5;
	public static final int TARIF_x200 = 6;
	public static final int TARIF_x500 = 7;
	public static final int TARIF_x1000 = 8;

	private static Integer[] tarifCodes = {TARIF_TESTING, TARIF_x5, TARIF_x10, TARIF_x20, TARIF_x50,
		TARIF_x100, TARIF_x200, TARIF_x500, TARIF_x1000};

	private static List<Tarif> possibleTarifs = null;

	private int tarif;

	public Tarif() {
		this.tarif = TARIF_TESTING;
	}

	public Tarif(Integer tarif) {
		this.tarif = tarif.intValue();
	}

	public int getTarif() {
		return tarif;
	}

	public void setTarif(Integer tarif) {
		this.tarif = tarif.intValue();
	}

	public String getTarifName() {
		switch (tarif) {
		case TARIF_TESTING: return "Ознакомительный";
		case TARIF_x5: return "x5";
		case TARIF_x10: return "x10";
		case TARIF_x20: return "x20";
		case TARIF_x50: return "x50";
		case TARIF_x100: return "x100";
		case TARIF_x200: return "x200";
		case TARIF_x500: return "x500";
		case TARIF_x1000: return "x1000";
		}
		return "Неизвестный";
	}

	public int getMaximumEmployees() {
		switch (tarif) {
		case TARIF_TESTING: return 3;
		case TARIF_x5: return 5;
		case TARIF_x10: return 10;
		case TARIF_x20: return 20;
		case TARIF_x50: return 50;
		case TARIF_x100: return 100;
		case TARIF_x200: return 200;
		case TARIF_x500: return 500;
		case TARIF_x1000: return 1000;
		}
		return 0;
	}

	public int getMonthPay() {
		switch (tarif) {
		case TARIF_TESTING: return 0;
		case TARIF_x5: return 50;
		case TARIF_x10: return 90;
		case TARIF_x20: return 180;
		case TARIF_x50: return 450;
		case TARIF_x100: return 900;
		case TARIF_x200: return 1800;
		case TARIF_x500: return 4500;
		case TARIF_x1000: return 9000;
		}
		return 0;
	}

	public boolean isFree() {
		return tarif == TARIF_TESTING;
	}

	public static List<Tarif> getPossibleTarifs() {
		if (possibleTarifs == null) {
			possibleTarifs = new ArrayList<Tarif>(tarifCodes.length);
			for (Integer code:tarifCodes) {
				possibleTarifs.add(new Tarif(code));
			}
		}
		return possibleTarifs;
	}

	public static boolean isTarifPossible(Tarif t) {
		return getPossibleTarifs().contains(t);
	}
	
	@Override
	public boolean equals(Object o) {
		if (o == null) return false;
		if (!(o instanceof Tarif)) return false;
		Tarif t = (Tarif)o;
		return tarif == t.getTarif();
	}
}
