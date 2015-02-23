package com.dn9x.modbus.controller;

import java.util.Map;

import com.dn9x.modbus.UnityNotifier;
import com.dn9x.modbus.entity.ControllerEntity;
import com.dn9x.modbus.entity.RegisterEntity;
import com.dn9x.modbus.util.Constant;
import com.dn9x.modbus.util.ModbusUtil;

public class BaseController implements IController, Runnable {

	private ControllerEntity ctorEntity;

	@Override
	public void showTest() {
		// TODO Auto-generated method stub
	}

	@Override
	public void setDigitalValue(String ip, int port, int slaveId, int address,
			int value) {
		// TODO Auto-generated method stub
		ModbusUtil.writeDigitalOutput(ip, port, slaveId, address, value);
	}

	@Override
	public void setRegisterValue(String ip, int port, int slaveId, int address,
			int value) {
		// TODO Auto-generated method stub
		ModbusUtil.writeRegister(ip, port, slaveId, address, value);
	}

	@Override
	public void setValue(Map<Integer, Integer> data) throws Exception {

		Map<Integer, RegisterEntity> map = this.ctorEntity.getRegisters();

		for (int key : map.keySet()) {
			for (int key2 : data.keySet()) {
				// 如果这个address已经存在，那就先覆盖本地，再写入到真机上面
				if (key == key2) {
					// 只处理类型是读写的，只读的不处理
					if (map.get(key).getType().equals("DO")
							|| map.get(key).getType().equals("RE")) {
						map.get(key).setValue(data.get(key2));
					} else {
						if (Constant.ENV == "local") {
							throw new Exception("只能对写入类型的寄存器写入值");
						} else {
							System.err.println("只能对写入类型的寄存器写入值");
						}
					}
				}
			}
		}

		// 覆盖完成之后，写入到真机，循环写入
		for (int key : map.keySet()) {
			if (map.get(key).getType().equals("DO")) {
				setDigitalValue(this.ctorEntity.getIp(),
						this.ctorEntity.getPort(), this.ctorEntity.getId(), map
								.get(key).getAddress(), map.get(key).getValue());
			} else if (map.get(key).getType().equals("RE")) {
				setRegisterValue(this.ctorEntity.getIp(),
						this.ctorEntity.getPort(), this.ctorEntity.getId(), map
								.get(key).getAddress(), map.get(key).getValue());
			}
		}

	}

	@Override
	public void setValue(int address, int value) throws Exception {

		Map<Integer, RegisterEntity> map = this.ctorEntity.getRegisters();

		for (int key : map.keySet()) {
			if (key == address) {
				if (map.get(key).getType().equals("DO")) {
					map.get(key).setValue(value);
					setDigitalValue(this.ctorEntity.getIp(),
							this.ctorEntity.getPort(), this.ctorEntity.getId(),
							address, value);
				} else if (map.get(key).getType().equals("RE")) {
					map.get(key).setValue(value);
					setRegisterValue(this.ctorEntity.getIp(),
							this.ctorEntity.getPort(), this.ctorEntity.getId(),
							address, value);
				} else {
					if (Constant.ENV == "local") {
						throw new Exception("只能对写入类型的寄存器写入值");
					} else {
						System.err.println("只能对写入类型的寄存器写入值");
					}
				}
			}
		}
	}

	@Override
	public void run() {
		//一直循环
		do {
			if (this.ctorEntity != null
					&& this.ctorEntity.getRegisters().size() > 0) {

				boolean ctroChange = false;

				for (int key : this.ctorEntity.getRegisters().keySet()) {
					int data = 0;

					String type = this.ctorEntity.getRegisters().get(key)
							.getType().toUpperCase();
					RegisterEntity re = this.ctorEntity.getRegisters().get(key);

					if (type.equals("DI")) {
						data = ModbusUtil.readDigitalInput(
								this.ctorEntity.getIp(),
								this.ctorEntity.getPort(), re.getAddress(),
								this.ctorEntity.getId());
					} else if (type.toUpperCase().equals("DO")) {
						data = ModbusUtil.readDigitalOutput(
								this.ctorEntity.getIp(),
								this.ctorEntity.getPort(), re.getAddress(),
								this.ctorEntity.getId());
					} else if (type.toUpperCase().equals("IR")) {
						data = ModbusUtil.readInputRegister(
								this.ctorEntity.getIp(),
								this.ctorEntity.getPort(), re.getAddress(),
								this.ctorEntity.getId());
					} else {
						data = ModbusUtil.readRegister(this.ctorEntity.getIp(),
								this.ctorEntity.getPort(), re.getAddress(),
								this.ctorEntity.getId());
					}

					System.out.println(">>>>>>>>>>>>>>read : " + type + " : "
							+ data);

					// 说明变化了
					if (re.getValue() != data) {
						// 改变掉值
						re.setValue(data);

						// 通知
						UnityNotifier
								.sendControllerRegisterStateChangedNotification(
										this.ctorEntity.getId(),
										this.ctorEntity.getRegisters().get(key)
												.getAddress());

						// 外围也要通知
						ctroChange = true;
					}
				}

				if (ctroChange) {
					UnityNotifier
							.sendControllerStatesChangedNotification(this.ctorEntity
									.getId());
				}
			}
			
			try {
				//这里每个controller为一个单位，一次完整的检测之后暂停一秒
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} while (1 == 1);
	}

	
	public ControllerEntity getCtorEntity() {
		return ctorEntity;
	}

	public void setCtorEntity(ControllerEntity ctorEntity) {
		this.ctorEntity = ctorEntity;
	}
}
