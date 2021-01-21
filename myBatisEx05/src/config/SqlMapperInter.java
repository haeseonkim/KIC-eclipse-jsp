package config;

import org.apache.ibatis.annotations.Select;

import model1.DeptTO;

public interface SqlMapperInter {
	
	@Select("select deptno, dname, loc from dept where deptno=#{deptno}")
	public DeptTO selectByDeptno(String deptno);
}
