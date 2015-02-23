package com.dn9x.modbus;

import java.util.Map;

import com.dn9x.modbus.util.SimulationMode;

public interface UnityBridge {
	/**
	 * 根据controllerId查询这个控制器下面的所有的寄存器状态，
	 * @param countrollerId
	 * @return
	 */
	Map<Integer, Integer> getAllRegisterState(int controllerId);
	
	/**
	 * 根据控制器id设置控制器下的所有的寄存器信息
	 * @param countrollerId
	 * @param data
	 */
	void setAllRegisterState(int controllerId, Map<Integer, Integer> data);

	/**
	 * 查询所有寄存器的数目
	 * @param controllerId
	 * @return
	 */
	int getTotalRegisters(int controllerId);

	/**
	 * 根据控制器和寄存器的id查询寄存器的状态
	 * @param controllerId
	 * @param registerAddress
	 * @return
	 */
	int getRegisterState(int controllerId, int registerAddress);

	/**
	 * 设置控制器和寄存器的值
	 * @param controllerId
	 * @param registerAddress
	 * @param value
	 */
	void setRegisterState(int controllerId, int registerAddress, int value);

	/**
	 * 这里查询寄存器是否是读写的，这里的意思是寄存器原本的类型是否是读写的，比如inputRegister就不能写入，
	 * @param controllerId
	 * @param registerAddress
	 * @return
	 */
	boolean isRegisterWritable(int controllerId, int registerAddress);
	
	/**
	 * 这里查询寄存器是否是读写的，这里的意思是寄存器原本的类型是否是读写的，比如inputRegister就不能写入，
	 * @param controllerId
	 * @param registerAddress
	 * @return
	 */
	boolean isRegisterReadOnly(int controllerId, int registerAddress);
	
	/**
	 * 查询所有的控制器数目
	 * @return
	 */
	int getTotalControllers();
	
	/**
	 * 设置所有控制器的状态，如果传递的值是SimulationMode.SimulateOnly，那么就重置所有的controller的所有寄存器为默认值，默认值从配置文件读取
	 * 如果设置的值是SimulationMode.SimulateAndForwarding那么就读取所有的真机设备的寄存器的值，覆盖到本地。 然后调用sendControllerRegisterStateChangedNotification方法进行通知
	 * @param mode
	 */
	void setSimulationMode(SimulationMode mode);
	
	/**
	 * 得到当前模拟器的状态，只有全部都开才返回true,否则都是false
	 */
	boolean isSimulateOnly();

	/**
	 * 设置指定控制器的状态，如果传递的值是SimulationMode.SimulateOnly，那么就重置指定的controller的所有寄存器为默认值，默认值从配置文件读取
	 * 如果设置的值是SimulationMode.SimulateAndForwarding那么就读取指定的真机设备的寄存器的值，覆盖到本地。 然后调用sendControllerRegisterStateChangedNotification方法进行通知
	 * @param controllerId
	 * @param mode
	 */
	void setSimulationMode(int controllerId, SimulationMode mode);
	
	/**
	 * 得到指定控制器的状态
	 * @param controllerId
	 * @return
	 */
	SimulationMode getSimulationMode(int controllerId);

}
