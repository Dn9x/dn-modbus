package com.dn9x.modbus.msg;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import net.wimpi.modbus.Modbus;
import net.wimpi.modbus.procimg.IllegalAddressException;
import net.wimpi.modbus.procimg.ProcessImage;
import net.wimpi.modbus.procimg.Register;

public class UnityReadMultipleRegistersRequest extends UnityModbusRequest {

	// instance attributes
	private int m_Reference;
	private int m_WordCount;
	private UnityModbusCoupler m_umc;

	/**
	 * Constructs a new <tt>ReadMultipleRegistersRequest</tt> instance.
	 */
	public UnityReadMultipleRegistersRequest(UnityModbusCoupler umc) {
		super();
		setFunctionCode(Modbus.READ_MULTIPLE_REGISTERS);
		// 4 bytes (remember unit identifier and function
		// code are excluded)
		setDataLength(4);
	}// constructor

	/**
	 * Constructs a new <tt>ReadMultipleRegistersRequest</tt> instance with a
	 * given reference and count of words to be read.
	 * <p>
	 * 
	 * @param ref
	 *            the reference number of the register to read from.
	 * @param count
	 *            the number of words to be read.
	 */
	public UnityReadMultipleRegistersRequest(int ref, int count) {
		super();
		setFunctionCode(Modbus.READ_MULTIPLE_REGISTERS);
		setDataLength(4);
		setReference(ref);
		setWordCount(count);
	}// constructor

	public UnityModbusResponse createResponse() {
		UnityReadMultipleRegistersResponse response = null;
		Register[] regs = null;

		// 1. get process image
		ProcessImage procimg = m_umc.getProcessImage();
		// 2. get input registers range
		try {
			regs = procimg.getRegisterRange(this.getReference(),
					this.getWordCount());
		} catch (IllegalAddressException iaex) {
			return createExceptionResponse(Modbus.ILLEGAL_ADDRESS_EXCEPTION);
		}
		response = new UnityReadMultipleRegistersResponse(regs, m_umc);
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
	 * Sets the reference of the register to start reading from with this
	 * <tt>ReadMultipleRegistersRequest</tt>.
	 * <p>
	 * 
	 * @param ref
	 *            the reference of the register to start reading from.
	 */
	public void setReference(int ref) {
		m_Reference = ref;
	}// setReference

	/**
	 * Returns the reference of the register to to start reading from with this
	 * <tt>ReadMultipleRegistersRequest</tt>.
	 * <p>
	 * 
	 * @return the reference of the register to start reading from as
	 *         <tt>int</tt>.
	 */
	public int getReference() {
		return m_Reference;
	}// getReference

	/**
	 * Sets the number of words to be read with this
	 * <tt>ReadMultipleRegistersRequest</tt>.
	 * <p>
	 * 
	 * @param count
	 *            the number of words to be read.
	 */
	public void setWordCount(int count) {
		m_WordCount = count;
		// setChanged(true);
	}// setWordCount

	/**
	 * Returns the number of words to be read with this
	 * <tt>ReadMultipleRegistersRequest</tt>.
	 * <p>
	 * 
	 * @return the number of words to be read as <tt>int</tt>.
	 */
	public int getWordCount() {
		return m_WordCount;
	}// getWordCount

	public void writeData(DataOutput dout) throws IOException {
		dout.writeShort(m_Reference);
		dout.writeShort(m_WordCount);
	}// writeData

	public void readData(DataInput din) throws IOException {
		m_Reference = din.readUnsignedShort();
		m_WordCount = din.readUnsignedShort();
	}// readData

}// class ReadMultipleRegistersRequest
