package com.dn9x.modbus.procimg;

import net.wimpi.modbus.procimg.DigitalOut;

public class UnityDigitalOut implements DigitalOut {

	protected boolean m_Set;

	public UnityDigitalOut() {
	}

	public UnityDigitalOut(boolean b) {
		set(b);
	}

	public boolean isSet() {
		return m_Set;
	}

	public synchronized void set(boolean b) {
		m_Set = b;

		onChanged();
	}

	public void onChanged() {
		System.out.println(">>>>>>>>>>>>UnityDigitalOut: 这里set了值：" + m_Set);
	}
}
