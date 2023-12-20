package org.sunso.mini.crawler.hutool;

import cn.hutool.db.Db;
import cn.hutool.db.Entity;
import cn.hutool.db.ds.simple.SimpleDataSource;
import lombok.Data;
import lombok.SneakyThrows;
import org.junit.Test;
import org.sunso.mini.crawler.BaseTest;
import org.sunso.mini.crawler.common.result.CrawlerResult;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class DbUtilTest extends BaseTest {

	@SneakyThrows
	@Test
	public void dbInsertTest() {
		String name = "力图";
		String mobile = "18937884994";
		String content = "我的超级的力图";
		UserConsult userConsult = UserConsult.newInstance(name, mobile, content);
		long id = Db.use().insertForGeneratedKey(Entity.create("user_consult").parseBean(userConsult, true, true));
		print(id);
	}

	@SneakyThrows
	@Test
	public void dbQueryTest() {
		String name = "力图";
		String mobile = "18937884994";
		String content = "我的超级的力图";
		UserConsult userConsult = UserConsult.newInstance(name, mobile, content);
		Entity entity = Entity.create("user_consult").parseBean(userConsult, true, true);
		List<Entity> entityList = Db.use(getDataSource()).find(Arrays.asList("name", "mobile"), entity);
		print(entityList);
		print(entityList.size());
	}

	private DataSource getDataSource() {
		String url = "jdbc:mysql://192.168.5.23:3306/zz_official?useUnicode=true&characterEncoding=UTF-8&autoReconnect=true&useSSL=false&zeroDateTimeBehavior=convertToNull&allowMultiQueries=true&serverTimezone=Asia/Shanghai";
		String userName = "lvdong";
		String password = "Ld123!@#";
		return new SimpleDataSource(url, userName, password);
	}

	@Data
	static class UserConsult implements CrawlerResult {

		private Integer id;

		private String name;

		private String mobile;

		private String content;

		private Date createTime;

		private Date updateTime;

		public static UserConsult newInstance(String name, String mobile, String content) {
			UserConsult userConsult = new UserConsult();
			userConsult.setName(name);
			userConsult.setMobile(mobile);
			userConsult.setContent(content);
			// userConsult.setCreate_time(new Date());
			// userConsult.setUpdate_time(new Date());
			return userConsult;
		}

	}

}
