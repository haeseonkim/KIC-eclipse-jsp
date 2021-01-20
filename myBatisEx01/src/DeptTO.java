
public class DeptTO {
	private String deptno;
	private String dname;
	private String loc;
	
	public String getDeptno() {
		System.out.println("getDeptno() 호출");
		return deptno;
	}
	public void setDeptno(String deptno) {
		System.out.println("setDeptno() 호출");
		this.deptno = deptno;
	}
	public String getDname() {
		return dname;
	}
	public void setDname(String dname) {
		this.dname = dname;
	}
	public String getLoc() {
		return loc;
	}
	public void setLoc(String loc) {
		this.loc = loc;
	}
}
