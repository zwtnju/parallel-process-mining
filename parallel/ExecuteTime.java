package cn.edu.nju.software.parallel;

public class ExecuteTime {
	
	//private long decomposeTime = 0;
	private long groupTime = 0;
	private long mineTime = 0;
	private long clearTime = 0;
	private long mergeTime = 0;
	
//	public long getDecomposeTime() {
//		return decomposeTime;
//	}
//	public void setDecomposeTime(long decomposeTime) {
//		this.decomposeTime = decomposeTime;
//	}
	public long getGroupTime() {
		return groupTime;
	}
	public void setGroupTime(long groupTime) {
		this.groupTime = groupTime;
	}
	public long getMineTime() {
		return mineTime;
	}
	public void setMineTime(long mineTime) {
		this.mineTime = mineTime;
	}
	public long getClearTime() {
		return clearTime;
	}
	public void setClearTime(long clearTime) {
		this.clearTime = clearTime;
	}
	public long getMergeTime() {
		return mergeTime;
	}
	public void setMergeTime(long mergeTime) {
		this.mergeTime = mergeTime;
	}

}
