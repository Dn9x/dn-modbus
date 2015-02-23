package com.dn9x.modbus.controller;

import java.util.Map;

public interface IController {
	
	/**
	 * 用于显示测试使用的
	 */
	void showTest();
	
	/**
	 * 写入单个数据到寄存器，这里是之写入Digital Output类型的寄存器，只有0和1
	 * @param ip
	 * @param address
	 * @param value
	 */
	void setDigitalValue(String ip, int port, int slaveId, int address, int value);
	
	/**
	 * 这里写入0-100的数字到寄存器，寄存器的类型是RE
	 * @param ip
	 * @param address
	 * @param value
	 */
	void setRegisterValue(String ip, int port, int slaveId, int address, int value);
	
	
	/**
	 * 批量写入数据到寄存器
	 * @param data
	 * @throws Exception 
	 */
	void setValue(Map<Integer, Integer> data) throws Exception;
	

	
	/**
	 * 写入数据到寄存器
	 * @param data
	 * @throws Exception 
	 */
	void setValue(int address, int value) throws Exception;
	
}
