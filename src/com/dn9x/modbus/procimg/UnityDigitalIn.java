package com.dn9x.modbus.procimg;

import net.wimpi.modbus.procimg.DigitalIn;

public class UnityDigitalIn implements DigitalIn {

	protected boolean m_Set = false;

	public UnityDigitalIn() {
	}

	public UnityDigitalIn(boolean b) {
		set(b);
	}

	public boolean isSet() {
		return m_Set;
	}

	public synchronized void set(boolean b) {
		m_Set = b;
		onChanged();
	}
	
	public void onChanged(){
		System.out.println(">>>>>>>>>>>>UnityDigitalIn: 这里set了值：" + m_Set);
	}

}
