package org.sunso.mini.crawler.annotation;

import lombok.Data;
import org.junit.Test;
import org.sunso.mini.crawler.BaseTest;
import org.sunso.mini.crawler.annotation.storage.FileStorage;
import org.sunso.mini.crawler.annotation.storage.FileStorageAliYunOss;
import org.sunso.mini.crawler.common.utils.ReflectUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Type;

public class FileStorageTest extends BaseTest {

	@Test
	public void fileStorageTest() {
		Field fields[] = Demo.class.getDeclaredFields();
		for (Field field : fields) {

			if (ReflectUtils.isAnnotationPresentRecursion(field, FileStorage.class)) {
				System.out.println(field.getName() + " contain " + FileStorage.class);
			}

			FileStorage fileStorage = ReflectUtils.getAnnotationRecursion(field, FileStorage.class);
			if (fileStorage != null) {
				System.out.println(field.getName() + "--" + fileStorage.fileStorage().getName());
			}

			// System.out.println(field.getName());
			// if (field.isAnnotationPresent(FileStorage.class)) {
			// System.out.println(field.getName() + " is FileStorage");
			// }
			// if (field.isAnnotationPresent(FileStorageAliYunOss.class)) {
			// System.out.println(field.getName() + " is FileStorageAliYunOss");
			// }
			//
			// Annotation annotations[] = field.getAnnotations();
			// for(Annotation annotation: annotations) {
			// System.out.println(field.getName()+": annotation is :" +
			// annotation.toString());
			// Class<? extends Annotation> clszz = annotation.annotationType();
			// Annotation annotationChilds[] = clszz.getAnnotations();
			// for(Annotation child: annotationChilds) {
			// System.out.println(annotation.toString()+": child annotation is :" +
			// child.toString());
			// }
			// }
		}
	}

	@Data
	class Demo {

		@FileStorageAliYunOss
		private String url;

		@FileStorage
		private String download;

	}

}
