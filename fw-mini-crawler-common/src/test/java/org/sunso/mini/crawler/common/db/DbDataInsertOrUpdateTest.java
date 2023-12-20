package org.sunso.mini.crawler.common.db;

import org.junit.Test;
import org.sunso.mini.crawler.common.BaseTest;

/**
 * @author sunso520
 * @Title:DbDataInsertOrUpdateTest
 * @Description: <br>
 * @Created on 2023/11/23 17:33
 */
public class DbDataInsertOrUpdateTest extends BaseTest {

	@Test
	public void dbDataInsertOrUpdateTest() {
		int result = HuToolDb.insertOrUpdateData(newDbDataInsertOrUpdate(), newConvertibleBond());
		print(result);
	}

	private DbDataInsertOrUpdate newDbDataInsertOrUpdate() {
		DbDataInsertOrUpdate insertOrUpdate = new DbDataInsertOrUpdate();
		insertOrUpdate.setTableName("convertible_bond");
		insertOrUpdate.setCheckExistColumns(new String[] { "bond_code" });
		return insertOrUpdate;
	}

	private ConvertibleBond newConvertibleBond() {
		ConvertibleBond convertibleBond = new ConvertibleBond();
		convertibleBond.setBondCode("123456");
		convertibleBond.setBondName("haha--ddd");
		return convertibleBond;
	}

	private UserConsult newUserConsult() {
		UserConsult userConsult = new UserConsult();
		userConsult.setName("liming_dddd");
		userConsult.setMobile("1349999999");
		userConsult.setContent("k快递寄快递dddd");
		return userConsult;
	}

}
