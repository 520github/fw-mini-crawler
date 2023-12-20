package org.sunso.mini.crawler.common.utils;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

	public static List<String> getMultiJsonPath(String jsonPath) {
		List<String> pathList = new ArrayList<>();
		String multiJsonPathFlag = ",";
		String leftFlag = "[";
		String rightFlag = "]";
		while (jsonPath.contains(multiJsonPathFlag)) {
			int firstMultiJsonPathFlagIndex = jsonPath.indexOf(multiJsonPathFlag);
			int leftFlagIndex = jsonPath.indexOf(leftFlag);
			int rightFlagIndex = jsonPath.indexOf(rightFlag);
			if (rightFlagIndex < firstMultiJsonPathFlagIndex) {
				if (pathList.isEmpty()) {
					pathList.add(jsonPath.substring(0, rightFlagIndex + rightFlag.length()));
					jsonPath = jsonPath.substring(rightFlagIndex + rightFlag.length());
					continue;
				}
				List<String> tempPathList = new ArrayList<>();
				for (String path : pathList) {
					tempPathList.add(path + jsonPath.substring(0, rightFlagIndex + rightFlag.length()));
				}
				pathList = tempPathList;
				jsonPath = jsonPath.substring(rightFlagIndex + rightFlag.length());
				continue;
			}
			String multiJsonPath = jsonPath.substring(leftFlagIndex + leftFlag.length(), rightFlagIndex);
			String leftStr = jsonPath.substring(0, leftFlagIndex + leftFlag.length());
			if (pathList.isEmpty()) {
				for (String onePath : multiJsonPath.split(multiJsonPathFlag)) {
					pathList.add(leftStr + onePath + rightFlag);
				}
				jsonPath = jsonPath.substring(rightFlagIndex + rightFlag.length());
				continue;
			}
			List<String> tempPathList = new ArrayList<>();
			for (String path : pathList) {
				String tempPath = path + leftStr;
				for (String onePath : multiJsonPath.split(multiJsonPathFlag)) {
					tempPathList.add(tempPath + onePath + rightFlag);
				}
			}
			jsonPath = jsonPath.substring(rightFlagIndex + rightFlag.length());
			pathList = tempPathList;
		}

		List<String> tempPathList = new ArrayList<>();
		for (String path : pathList) {
			tempPathList.add(path + jsonPath);
		}
		return tempPathList;
	}

}
