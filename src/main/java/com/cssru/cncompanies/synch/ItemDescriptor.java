package com.cssru.cncompanies.synch;

import java.io.Serializable;


public class ItemDescriptor implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private long clientId;
	private long serverId;

	public ItemDescriptor() {
		clientId = 0L;
		serverId = 0L;
	}

	public ItemDescriptor(long clientId, long serverId) {
		this.clientId = clientId;
		this.serverId = serverId;
	}

	public long getClientId() {
		return clientId;
	}

	public long getServerId() {
		return serverId;
	}

	public void setClientId(long clientId) {
		this.clientId = clientId;
	}

	public void setServerId(long serverId) {
		this.serverId = serverId;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("clientId: ").append(clientId).append("\n")
		.append("serverId: ").append(serverId).append("\n");
		return sb.toString();
	}
}
