package com.dn9x.modbus;

public class UnityNotifier {

	/**
	 * 检测到有多个寄存器的值变化的时候就调用这个方法
	 * @param controllerID
	 */
	public static void sendControllerStatesChangedNotification(int controllerID){
		System.out.println(">>>>>>>controllID:" + controllerID + " 里面有值改变了");
	}

	/**
	 * 检测到一个寄存器变化就调用这个方法
	 * @param controllerID
	 * @param registerAddress
	 */
	public static void sendControllerRegisterStateChangedNotification(int controllerID, int registerAddress){
		System.out.println(">>>>>>>controllID:" + controllerID + " registerAddress:" + registerAddress + " 值改变了");
	}


}
