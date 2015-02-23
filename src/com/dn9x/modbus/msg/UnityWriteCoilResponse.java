package com.dn9x.modbus.msg;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import net.wimpi.modbus.Modbus;

public class UnityWriteCoilResponse extends UnityModbusResponse {

	// instance attributes
	private boolean m_Coil = false;
	private int m_Reference;

	/**
	 * Constructs a new <tt>WriteCoilResponse</tt> instance.
	 */
	public UnityWriteCoilResponse(UnityModbusCoupler umc) {
		super();
		setFunctionCode(Modbus.WRITE_COIL);
		setDataLength(4);
	}// constructor

	/**
	 * Constructs a new <tt>WriteCoilResponse</tt> instance.
	 * 
	 * @param reference
	 *            the offset were writing was started from.
	 * @param b
	 *            the state of the coil; true set, false reset.
	 */
	public UnityWriteCoilResponse(int reference, boolean b, UnityModbusCoupler umc) {
		super();
		setFunctionCode(Modbus.WRITE_COIL);
		setDataLength(4);
		setReference(reference);
		setCoil(b);
	}// constructor

	/**
	 * Sets the state that has been returned in the raw response.
	 * <p>
	 * 
	 * @param b
	 *            true if the coil should be set of false if it should be unset.
	 */
	private void setCoil(boolean b) {
		m_Coil = b;
	}// setCoil

	/**
	 * Gets the state that has been returned in this <tt>WriteCoilRequest</tt>.
	 * <p>
	 * 
	 * @return true if the coil is set, false if unset.
	 */
	public boolean getCoil() {
		return m_Coil;
	}// getCoil

	/**
	 * Returns the reference of the register of the coil that has been written
	 * to with the request.
	 * <p>
	 * 
	 * @return the reference of the coil's register.
	 */
	public int getReference() {
		return m_Reference;
	}// getReference

	/**
	 * Sets the reference of the register of the coil that has been written to
	 * with the request.
	 * <p>
	 * 
	 * @param ref
	 *            the reference of the coil's register.
	 */
	private void setReference(int ref) {
		m_Reference = ref;
		// setChanged(true);
	}// setReference

	public void writeData(DataOutput dout) throws IOException {
		dout.writeShort(getReference());
		if (getCoil()) {
			dout.write(Modbus.COIL_ON_BYTES, 0, 2);
		} else {
			dout.write(Modbus.COIL_OFF_BYTES, 0, 2);
		}
	}// writeData

	public void readData(DataInput din) throws IOException {
		setReference(din.readUnsignedShort());

		byte[] data = new byte[2];
		for (int k = 0; k < 2; k++) {
			data[k] = din.readByte();
		}
		// set toggle
		if (data[0] == Modbus.COIL_ON) {
			setCoil(true);
		} else {
			setCoil(false);
		}

		// update data length
		setDataLength(4);
	}// readData

}// class WriteCoilResponse
