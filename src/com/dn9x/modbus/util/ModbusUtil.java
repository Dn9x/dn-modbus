package com.dn9x.modbus.util;

import java.net.InetAddress;

import net.wimpi.modbus.ModbusException;
import net.wimpi.modbus.ModbusIOException;
import net.wimpi.modbus.ModbusSlaveException;
import net.wimpi.modbus.io.ModbusTCPTransaction;
import net.wimpi.modbus.msg.ReadCoilsRequest;
import net.wimpi.modbus.msg.ReadCoilsResponse;
import net.wimpi.modbus.msg.ReadInputDiscretesRequest;
import net.wimpi.modbus.msg.ReadInputDiscretesResponse;
import net.wimpi.modbus.msg.ReadInputRegistersRequest;
import net.wimpi.modbus.msg.ReadInputRegistersResponse;
import net.wimpi.modbus.msg.ReadMultipleRegistersRequest;
import net.wimpi.modbus.msg.ReadMultipleRegistersResponse;
import net.wimpi.modbus.msg.WriteCoilRequest;
import net.wimpi.modbus.msg.WriteSingleRegisterRequest;
import net.wimpi.modbus.net.TCPMasterConnection;

import com.dn9x.modbus.procimg.UnityRegister;

public class ModbusUtil {
	/**
	 * 查询Function 为Input Status的寄存器
	 * 
	 * @param ip
	 * @param address
	 * @param count
	 * @param slaveId
	 * @return
	 * @throws ModbusIOException
	 * @throws ModbusSlaveException
	 * @throws ModbusException
	 */
	public static int readDigitalInput(String ip, int port, int address, int slaveId) {
		int data = 0;

		try {
			InetAddress addr = InetAddress.getByName(ip);

			// 建立连接
			TCPMasterConnection con = new TCPMasterConnection(addr);

			con.setPort(port);

			con.connect();

			// 第一个参数是寄存器的地址，第二个参数时读取多少个
			ReadInputDiscretesRequest req = new ReadInputDiscretesRequest(address, 1);

			// 这里设置的Slave Id, 读取的时候这个很重要
			req.setUnitID(slaveId);

			ModbusTCPTransaction trans = new ModbusTCPTransaction(con);

			trans.setRequest(req);

			// 执行查询
			trans.execute();

			// 得到结果
			ReadInputDiscretesResponse res = (ReadInputDiscretesResponse) trans.getResponse();

			if(res.getDiscretes().getBit(0)){
				data = 1;
			}

			// 关闭连接
			con.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return data;
	}

	public static int readInputRegister(String ip, int port, int address,
			int slaveId) {
		int data = 0;

		try {
			InetAddress addr = InetAddress.getByName(ip);
			TCPMasterConnection con = new TCPMasterConnection(addr);
			con.setPort(port);
			con.connect();

			ReadInputRegistersRequest req = new ReadInputRegistersRequest(address, 1);
			req.setUnitID(slaveId);

			ModbusTCPTransaction trans = new ModbusTCPTransaction(con);

			trans.setRequest(req);

			trans.execute();

			ReadInputRegistersResponse res = (ReadInputRegistersResponse) trans.getResponse();

			data = res.getRegisterValue(0);

			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return data;
	}

	public static int readDigitalOutput(String ip, int port, int address,
			int slaveId) {
		int data = 0;
		try {
			InetAddress addr = InetAddress.getByName(ip);

			TCPMasterConnection con = new TCPMasterConnection(addr);
			con.setPort(port);
			con.connect();

			ReadCoilsRequest req = new ReadCoilsRequest(address, 1);

			req.setUnitID(slaveId);

			ModbusTCPTransaction trans = new ModbusTCPTransaction(con);

			trans.setRequest(req);

			trans.execute();

			ReadCoilsResponse res = ((ReadCoilsResponse) trans.getResponse());

			if(res.getCoils().getBit(0)){
				data = 1;
			}

			con.close();

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return data;
	}

	public static int readRegister(String ip, int port, int address,
			int slaveId) {
		int data = 0;
		try {
			InetAddress addr = InetAddress.getByName(ip);

			TCPMasterConnection con = new TCPMasterConnection(addr);

			con.setPort(port);
			con.connect();
			ReadMultipleRegistersRequest req = new ReadMultipleRegistersRequest(address, 1);
			req.setUnitID(slaveId);

			ModbusTCPTransaction trans = new ModbusTCPTransaction(con);

			trans.setRequest(req);

			trans.execute();

			ReadMultipleRegistersResponse res = (ReadMultipleRegistersResponse) trans.getResponse();
			
			data = res.getRegisterValue(0);

			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return data;
	}

	/**
	 * 写入数据到真机，数据类型是RE
	 * 
	 * @param ip
	 * @param port
	 * @param slaveId
	 * @param address
	 * @param value
	 */
	public static void writeRegister(String ip, int port, int slaveId,
			int address, int value) {

		try {
			InetAddress addr = InetAddress.getByName(ip);

			TCPMasterConnection connection = new TCPMasterConnection(addr);
			connection.setPort(port);
			connection.connect();

			ModbusTCPTransaction trans = new ModbusTCPTransaction(connection);

			UnityRegister register = new UnityRegister(value);

			WriteSingleRegisterRequest req = new WriteSingleRegisterRequest(
					address, register);

			req.setUnitID(slaveId);
			trans.setRequest(req);

			System.out.println("ModbusSlave: FC" + req.getFunctionCode()
					+ " ref=" + req.getReference() + " value="
					+ register.getValue());
			trans.execute();

			connection.close();
		} catch (Exception ex) {
			System.out.println("Error in code");
			ex.printStackTrace();
		}
	}

	/**
	 * 写入数据到真机的DO类型的寄存器上面
	 * 
	 * @param ip
	 * @param port
	 * @param slaveId
	 * @param address
	 * @param value
	 */
	public static void writeDigitalOutput(String ip, int port, int slaveId,
			int address, int value) {

		try {
			InetAddress addr = InetAddress.getByName(ip);

			TCPMasterConnection connection = new TCPMasterConnection(addr);
			connection.setPort(port);
			connection.connect();

			ModbusTCPTransaction trans = new ModbusTCPTransaction(connection);

			boolean val = true;

			if (value == 0) {
				val = false;
			}

			WriteCoilRequest req = new WriteCoilRequest(address, val);

			req.setUnitID(slaveId);
			trans.setRequest(req);

			trans.execute();
			connection.close();
		} catch (Exception ex) {
			System.out.println("writeDigitalOutput Error in code");
			ex.printStackTrace();
		}
	}

}
