package co.micol.prj.common;


import java.io.IOException;
import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class DataSource {
	//싱글톤 class 
	//인스턴스는 하나만 생성된다.
	private static SqlSessionFactory sqlSessionFactory;
	private DataSource() {}; //외부에서 나를 생성하지 못하게 한다.
	//sqlSessionFactory 값을 고정하는것
	public static SqlSessionFactory getInstance() {
		String resource = "config/mybatis-config.xml";
		try {
			InputStream inputStream = Resources.getResourceAsStream(resource);
			sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
			
		}catch (IOException e) {
			e.printStackTrace();
		}
		return sqlSessionFactory;
	}
}
