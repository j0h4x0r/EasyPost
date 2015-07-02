package client.databean;


import java.util.Date;

import comm.CommData;
import comm.DataType;

public class LetterCommData extends CommData {
	private static final long serialVersionUID = 1L;
	private String id;
	private Date time;
	private String letterOut;
	private String letterContent;
	
	public LetterCommData() {
		// TODO Auto-generated constructor stub
	}
	public LetterCommData(String id, DataType dataType) {
		this.id = id;
		this.type = dataType;
	}
	
	public Date getTime() {
		return time;
	}

	public String getLetterContent() {
		return letterContent;
	}
	public void setLetterContent(String letterContent) {
		this.letterContent = letterContent;
	}
	public String getLetterOut() {
		return letterOut;
	}
	public void setTime(Date time) {
		this.time = time;
	}

	public void setLetterOut(String letterOut) {
		this.letterOut = letterOut;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return this.id;
	}
}
