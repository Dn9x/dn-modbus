package com.dn9x.modbus.msg;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import net.wimpi.modbus.Modbus;
import net.wimpi.modbus.procimg.DigitalOut;
import net.wimpi.modbus.procimg.IllegalAddressException;
import net.wimpi.modbus.procimg.ProcessImage;

public class UnityWriteCoilRequest extends UnityModbusRequest {

	// instance attributes
	private int m_Reference;
	private boolean m_Coil;
	private UnityModbusCoupler m_umc;

	/**
	 * Constructs a new <tt>WriteCoilRequest</tt> instance.
	 */
	public UnityWriteCoilRequest(UnityModbusCoupler umc) {
		super();
		m_umc = umc;
		setFunctionCode(Modbus.WRITE_COIL);
		// 4 bytes (unit id and function code is excluded)
		setDataLength(4);
	}// constructor

	/**
	 * Constructs a new <tt>WriteCoilRequest</tt> instance with a given
	 * reference and state to be written.
	 * <p>
	 * 
	 * @param ref
	 *            the reference number of the register to read from.
	 * @param b
	 *            true if the coil should be set of false if it should be unset.
	 */
	public UnityWriteCoilRequest(int ref, boolean b) {
		super();
		setFunctionCode(Modbus.WRITE_COIL);
		// 4 bytes (unit id and function code is excluded)
		setDataLength(4);
		setReference(ref);
		setCoil(b);
	}// constructor

	public UnityModbusResponse createResponse() {
		UnityWriteCoilResponse response = null;
		DigitalOut dout = null;

		// 1. get process image
		ProcessImage procimg = m_umc.getProcessImage();
		// 2. get coil
		try {
			dout = procimg.getDigitalOut(this.getReference());
			// 3. set coil
			dout.set(this.getCoil());
			// if(Modbus.debug)
			// System.out.println("set coil ref="+this.getReference()+" state="
			// + this.getCoil());
		} catch (IllegalAddressException iaex) {
			return createExceptionResponse(Modbus.ILLEGAL_ADDRESS_EXCEPTION);
		}
		response = new UnityWriteCoilResponse(this.getReference(), dout.isSet(), m_umc);
		// transfer header data
		if (!isHeadless()) {
			response.setTransactionID(this.getTransactionID());
			response.setProtocolID(this.getProtocolID());
		} else {
			response.setHeadless();
		}
		response.setUnitID(this.getUnitID());
		response.setFunctionCode(this.getFunctionCode());
		return response;
	}// createResponse

	/**
	 * Sets the reference of the register of the coil that should be written to
	 * with this <tt>ReadCoilsRequest</tt>.
	 * <p>
	 * 
	 * @param ref
	 *            the reference of the coil's register.
	 */
	public void setReference(int ref) {
		m_Reference = ref;
		// setChanged(true);
	}// setReference

	/**
	 * Returns the reference of the register of the coil that should be written
	 * to with this <tt>ReadCoilsRequest</tt>.
	 * <p>
	 * 
	 * @return the reference of the coil's register.
	 */
	public int getReference() {
		return m_Reference;
	}// getReference

	/**
	 * Sets the state that should be written with this <tt>WriteCoilRequest</tt>
	 * .
	 * <p>
	 * 
	 * @param b
	 *            true if the coil should be set of false if it should be unset.
	 */
	public void setCoil(boolean b) {
		m_Coil = b;
		// setChanged(true);
	}// setCoil

	/**
	 * Returns the state that should be written with this
	 * <tt>WriteCoilRequest</tt>.
	 * <p>
	 * 
	 * @return true if the coil should be set of false if it should be unset.
	 */
	public boolean getCoil() {
		return m_Coil;
	}// getCoil

	public void writeData(DataOutput dout) throws IOException {
		dout.writeShort(m_Reference);
		if (m_Coil) {
			dout.write(Modbus.COIL_ON_BYTES, 0, 2);
		} else {
			dout.write(Modbus.COIL_OFF_BYTES, 0, 2);
		}
	}

	public void readData(DataInput din) throws IOException {
		m_Reference = din.readUnsignedShort();
		if (din.readByte() == Modbus.COIL_ON) {
			m_Coil = true;
		} else {
			m_Coil = false;
		}
		// skip last byte
		din.readByte();
	}// readData

}// class WriteCoilRequest
