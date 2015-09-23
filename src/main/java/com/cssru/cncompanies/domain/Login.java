package com.cssru.cncompanies.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Type;

@Entity
@Table (name="logins")
public class Login {
	@Id 
	@Column (name="id")
	@GeneratedValue
	private Long id;
	
	@OneToOne
	@JoinColumn (name="human_id")
	private Human human;
	
	@Column (unique = true, length = 30)
	private String login;

	@Column (length = 255)
	private String password;

	@Column (length = 50)
	private String email;

	@Column (length = 50)
	private String confirmCode;
	
	@Column (nullable = false, columnDefinition = "TINYINT(1)")
	private Boolean locked;
	
	@Column (name="create_time")
	private Date createTime;

	@Type(type="com.cssru.cncompanies.domain.TarifType")
	@Column (name="tarif")
	private Tarif tarif;

	@Column (name="paid_till")
	private Date paidTill;

	@Column
	private Date lastSynch;

	public Login() {
		locked = false;
		tarif = new Tarif(); // TESTING by default
		paidTill = null;
	}
	
	public Login(Human human) {
		this();
		this.human = human;
	}
	
	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public Long getId() {
		return id;
	}

	public Human getHuman() {
		return human;
	}

	public String getPassword() {
		return password;
	}

	public String getEmail() {
		return email;
	}

	public String getConfirmCode() {
		return confirmCode;
	}

	public boolean isConfirmed() {
		return confirmCode == null || confirmCode.length() == 0; 
	}
	
	public Boolean getLocked() {
		return locked;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public Tarif getTarif() {
		return tarif;
	}

	public Date getPaidTill() {
		return paidTill;
	}

	public Date getLastSynch() {
		return lastSynch;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setHuman(Human human) {
		this.human = human;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setConfirmCode(String confirmCode) {
		this.confirmCode = confirmCode;
	}

	public void setLocked(Boolean locked) {
		this.locked = locked;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public void setTarif(Tarif newTarif) {
		if (newTarif.getTarif() == Tarif.TARIF_TESTING) return;
		long nowMillis = System.currentTimeMillis();
		long paidMillis = getPaidTill() == null ? 0L : getPaidTill().getTime();
		double alreadyPaidMillisCount = (nowMillis < paidMillis) ? (double)(paidMillis-nowMillis) : 0.0D;
		double monthPay = (double)tarif.getMonthPay();
		double millisInMonth = (365.0D / 12.0D)*24.0D*60.0D*60.0D*1000.0D;
		double millisCost = monthPay / millisInMonth;
		double money = millisCost * alreadyPaidMillisCount;
		double newMonthPay = (double)newTarif.getMonthPay();
		double newMillisCost = newMonthPay / millisInMonth;
		long newMillis = nowMillis + (long)(money / newMillisCost)+1;
		setPaidTill(new Date(newMillis));
		this.tarif = newTarif;
	}

	public void setPaidTill(Date paidTill) {
		this.paidTill = paidTill;
	}

	public void setLastSynch(Date lastSynch) {
		this.lastSynch = lastSynch;
	}

	public void addPayment(Integer value) {
		long nowMillis = System.currentTimeMillis();
		long paidMillis = getPaidTill().getTime();
		double alreadyPaidMillisCount = (nowMillis < paidMillis) ? (double)(paidMillis-nowMillis) : 0.0D;
		double monthPay = (double)tarif.getMonthPay();
		double millisInMonth = (365.0D / 12.0D)*24.0D*60.0D*60.0D*1000.0D;
		double millisCost = monthPay / millisInMonth;
		double money = millisCost * alreadyPaidMillisCount;
		money += value.doubleValue();
		long newMillis = nowMillis + (long)(money / millisCost)+1;
		setPaidTill(new Date(newMillis));
	}
}
