package comm;

import java.io.Serializable;

public abstract class CommData implements Serializable {

	private static final long serialVersionUID = 1L;
	protected DataType type;
	protected String username;

	public DataType getType() {
		return type;
	}

	public void setType(DataType type) {
		this.type = type;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}
