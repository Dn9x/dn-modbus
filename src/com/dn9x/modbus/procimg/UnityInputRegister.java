package com.dn9x.modbus.procimg;

import net.wimpi.modbus.procimg.InputRegister;
import net.wimpi.modbus.procimg.SynchronizedAbstractRegister;

public class UnityInputRegister extends SynchronizedAbstractRegister implements
		InputRegister {

	public UnityInputRegister() {}
	
	public UnityInputRegister(byte b1, byte b2) {
		m_Register[0] = b1;
		m_Register[1] = b2;
	}
	
	public UnityInputRegister(int value) {
		setValue(value);
	}

}
