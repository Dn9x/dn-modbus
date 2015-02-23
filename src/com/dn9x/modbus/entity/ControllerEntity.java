package com.dn9x.modbus.entity;

import java.util.Map;

import net.wimpi.modbus.Modbus;
import com.dn9x.modbus.controller.IController;
import com.dn9x.modbus.util.SimulationMode;

public class ControllerEntity {
	
	private int id;
	private SimulationMode mode;
	private IController controller;
	private Map<Integer, RegisterEntity> registers;
	private String ip;
	private int port;
	
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public int getPort() {
		if(port == 0){
			return Modbus.DEFAULT_PORT;
		}
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	public SimulationMode getMode() {
		return mode;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setMode(SimulationMode mode) {
		this.mode = mode;
	}
	public IController getController() {
		return controller;
	}
	public void setController(IController controller) {
		this.controller = controller;
	}
	public Map<Integer, RegisterEntity> getRegisters() {
		return registers;
	}
	public void setRegisters(Map<Integer, RegisterEntity> registers) {
		this.registers = registers;
	}
}
