package org.sunso.mini.crawler.common.http.option;

import lombok.Data;

/**
 * @author sunso520
 * @Title:Option
 * @Description: <br>
 * @Created on 2023/11/24 15:12
 */
public class Option {

	/**
	 * 开启无界面模式
	 */
	private boolean switchArgHeadless;

	/**
	 * 禁用gpu
	 */
	private boolean switchArgDisableGpu;

	/**
	 * 最大化窗口
	 */
	private boolean switchArgStartMaximized;

	/**
	 * 禁用浏览器正在被自动化程序控制的提示
	 */
	private boolean switchArgDisableInfoBars;

	/**
	 * 隐身模式 (无痕模式)
	 */
	private boolean switchArgIncognito;

	/**
	 * 禁用javascript
	 */
	private boolean switchArgDisableJavascript;

	public boolean isSwitchArgHeadless() {
		return switchArgHeadless;
	}

	public Option setSwitchArgHeadless(boolean switchArgHeadless) {
		this.switchArgHeadless = switchArgHeadless;
		return this;
	}

	public boolean isSwitchArgDisableGpu() {
		return switchArgDisableGpu;
	}

	public Option setSwitchArgDisableGpu(boolean switchArgDisableGpu) {
		this.switchArgDisableGpu = switchArgDisableGpu;
		return this;
	}

	public boolean isSwitchArgStartMaximized() {
		return switchArgStartMaximized;
	}

	public Option setSwitchArgStartMaximized(boolean switchArgStartMaximized) {
		this.switchArgStartMaximized = switchArgStartMaximized;
		return this;
	}

	public boolean isSwitchArgDisableInfoBars() {
		return switchArgDisableInfoBars;
	}

	public Option setSwitchArgDisableInfoBars(boolean switchArgDisableInfoBars) {
		this.switchArgDisableInfoBars = switchArgDisableInfoBars;
		return this;
	}

	public boolean isSwitchArgIncognito() {
		return switchArgIncognito;
	}

	public Option setSwitchArgIncognito(boolean switchArgIncognito) {
		this.switchArgIncognito = switchArgIncognito;
		return this;
	}

	public boolean isSwitchArgDisableJavascript() {
		return switchArgDisableJavascript;
	}

	public Option setSwitchArgDisableJavascript(boolean switchArgDisableJavascript) {
		this.switchArgDisableJavascript = switchArgDisableJavascript;
		return this;
	}

}
