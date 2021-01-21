package config;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import model1.DeptTO;

public interface SqlMapperInter {
	
	// overloading  -> 하지마라 
	/*@Select("select deptno, dname, loc from dept where deptno=10") */
	//public DeptTO selectByDeptno();

	@Select("select deptno, dname, loc from dept where deptno=#{deptno}")
	public DeptTO selectByDeptno(String deptno);

	@Select("select deptno, dname, loc from dept")
	public List<DeptTO> selectList();
}
