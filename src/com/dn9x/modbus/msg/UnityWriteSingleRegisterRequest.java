package com.dn9x.modbus.msg;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import net.wimpi.modbus.Modbus;
import net.wimpi.modbus.ModbusCoupler;
import net.wimpi.modbus.procimg.IllegalAddressException;
import net.wimpi.modbus.procimg.ProcessImage;
import net.wimpi.modbus.procimg.Register;

public class UnityWriteSingleRegisterRequest extends UnityModbusRequest {

	// instance attributes
	private int m_Reference;
	private Register m_Register;
	private UnityModbusCoupler m_umc;

	/**
	 * Constructs a new <tt>WriteSingleRegisterRequest</tt> instance.
	 */
	public UnityWriteSingleRegisterRequest(UnityModbusCoupler umc) {
		super();
		 m_umc = umc;
		setFunctionCode(Modbus.WRITE_SINGLE_REGISTER);
		// 4 bytes (unit id and function code is excluded)
		setDataLength(4);
	}// constructor

	/**
	 * Constructs a new <tt>WriteSingleRegisterRequest</tt> instance with a
	 * given reference and value to be written.
	 * <p/>
	 * 
	 * @param ref
	 *            the reference number of the register to read from.
	 * @param reg
	 *            the register containing the data to be written.
	 */
	public UnityWriteSingleRegisterRequest(int ref, Register reg) {
		super();
		setFunctionCode(Modbus.WRITE_SINGLE_REGISTER);
		m_Reference = ref;
		m_Register = reg;
		// 4 bytes (unit id and function code is excluded)
		setDataLength(4);
	}// constructor

	public UnityModbusResponse createResponse() {
		UnityWriteSingleRegisterResponse response = null;
		Register reg = null;

		// 1. get process image
		ProcessImage procimg = m_umc.getProcessImage();
		// 2. get register
		try {
			reg = procimg.getRegister(m_Reference);
			// 3. set Register
			reg.setValue(m_Register.toBytes());
		} catch (IllegalAddressException iaex) {
			return createExceptionResponse(Modbus.ILLEGAL_ADDRESS_EXCEPTION);
		}
		response = new UnityWriteSingleRegisterResponse(this.getReference(),
				reg.getValue(), m_umc);
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
	 * Sets the reference of the register to be written to with this
	 * <tt>WriteSingleRegisterRequest</tt>.
	 * <p/>
	 * 
	 * @param ref
	 *            the reference of the register to be written to.
	 */
	public void setReference(int ref) {
		m_Reference = ref;
		// setChanged(true);
	}// setReference

	/**
	 * Returns the reference of the register to be written to with this
	 * <tt>WriteSingleRegisterRequest</tt>.
	 * <p/>
	 * 
	 * @return the reference of the register to be written to.
	 */
	public int getReference() {
		return m_Reference;
	}// getReference

	/**
	 * Sets the value that should be written to the register with this
	 * <tt>WriteSingleRegisterRequest</tt>.
	 * <p/>
	 * 
	 * @param reg
	 *            the register to be written.
	 */
	public void setRegister(Register reg) {
		m_Register = reg;
	}// setRegister

	/**
	 * Returns the value that should be written to the register with this
	 * <tt>WriteSingleRegisterRequest</tt>.
	 * <p/>
	 * 
	 * @return the value to be written to the register.
	 */
	public Register getRegister() {
		return m_Register;
	}// getRegister

	public void writeData(DataOutput dout) throws IOException {
		dout.writeShort(m_Reference);
		dout.write(m_Register.toBytes(), 0, 2);
	}// writeData

	public void readData(DataInput din) throws IOException {
		m_Reference = din.readUnsignedShort();
		m_Register = ModbusCoupler.getReference().getProcessImageFactory()
				.createRegister(din.readByte(), din.readByte());
	}// readData

}// class WriteSingleRegisterRequest
