package com.taodaye.entity;

public class SearchResult {

	private int error;
	private int counts;
	private Content[] content;
	public int getError() {
		return error;
	}
	public void setError(int error) {
		this.error = error;
	}
	public int getCounts() {
		return counts;
	}
	public void setCounts(int counts) {
		this.counts = counts;
	}
		public Content[] getContent() {
		return content;
	}
	public void setContent(Content[] content) {
		this.content = content;
	}
		@Override
	public String toString() {
		// TODO Auto-generated method stub
		StringBuffer strBuffer = new StringBuffer();
		strBuffer.append(getError()).append(getCounts());
		if(this.content.length>0){
			for(Content con:this.content){
				strBuffer.append(con);
			}
		}
		return strBuffer.toString();
	}
	
	
}
