package config;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Update;

import model1.DeptTO;

public interface SqlMapperInter {
	
	@Insert("insert into dept values (#{deptno} , #{dname}, #{loc})")
	public int insert(DeptTO to);
	
	@Update( "update dept set dname=#{dname} where deptno=#{deptno}")
	public int update(DeptTO to);
	
	@Delete("delete from dept where deptno=#{deptno}")
	public int delete(DeptTO to);
}
