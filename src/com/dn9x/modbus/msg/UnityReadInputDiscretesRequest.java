package com.dn9x.modbus.msg;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import net.wimpi.modbus.Modbus;
import net.wimpi.modbus.procimg.DigitalIn;
import net.wimpi.modbus.procimg.IllegalAddressException;
import net.wimpi.modbus.procimg.ProcessImage;

public class UnityReadInputDiscretesRequest extends UnityModbusRequest {

	// instance attributes
	private int m_Reference;
	private int m_BitCount;
	private UnityModbusCoupler m_umc;

	/**
	 * Constructs a new <tt>ReadInputDiscretesRequest</tt> instance.
	 */
	public UnityReadInputDiscretesRequest(UnityModbusCoupler umc) {
		super();
		m_umc = umc;
		setFunctionCode(Modbus.READ_INPUT_DISCRETES);
		// 4 bytes (unit id and function code is excluded)
		setDataLength(4);
	}// constructor

	/**
	 * Constructs a new <tt>ReadInputDiscretesRequest</tt> instance with a given
	 * reference and count of input discretes (i.e. bits) to be read.
	 * <p>
	 * 
	 * @param ref
	 *            the reference number of the register to read from.
	 * @param count
	 *            the number of bits to be read.
	 */
	public UnityReadInputDiscretesRequest(int ref, int count) {
		super();
		setFunctionCode(Modbus.READ_INPUT_DISCRETES);
		// 4 bytes (unit id and function code is excluded)
		setDataLength(4);
		setReference(ref);
		setBitCount(count);
	}// constructor

	/*
	 * public ModbusResponse getResponse() { ReadInputDiscretesResponse response
	 * = new ReadInputDiscretesResponse(getBitCount());
	 * response.setHeadless(isHeadless()); return response; }//getResponse
	 */

	public UnityModbusResponse createResponse() {
		UnityReadInputDiscretesResponse response = null;
		DigitalIn[] dins = null;

		// 1. get process image
		ProcessImage procimg = m_umc.getProcessImage();
		// 2. get inputdiscretes range
		try {
			dins = procimg.getDigitalInRange(this.getReference(),
					this.getBitCount());
		} catch (IllegalAddressException iaex) {
			return createExceptionResponse(Modbus.ILLEGAL_ADDRESS_EXCEPTION);
		}
		response = new UnityReadInputDiscretesResponse(dins.length, m_umc);
		// transfer header data
		if (!isHeadless()) {
			response.setTransactionID(this.getTransactionID());
			response.setProtocolID(this.getProtocolID());
		} else {
			response.setHeadless();
		}
		response.setUnitID(this.getUnitID());
		response.setFunctionCode(this.getFunctionCode());

		for (int i = 0; i < dins.length; i++) {
			response.setDiscreteStatus(i, dins[i].isSet());
		}
		return response;
	}// createResponse

	/**
	 * Sets the reference of the register to start reading from with this
	 * <tt>ReadInputDiscretesRequest</tt>.
	 * <p>
	 * 
	 * @param ref
	 *            the reference of the register to start reading from.
	 */
	public void setReference(int ref) {
		m_Reference = ref;
		// setChanged(true);
	}// setReference

	/**
	 * Returns the reference of the register to to start reading from with this
	 * <tt>ReadInputDiscretesRequest</tt>.
	 * <p>
	 * 
	 * @return the reference of the register to start reading from as
	 *         <tt>int</tt>.
	 */
	public int getReference() {
		return m_Reference;
	}// getReference

	/**
	 * Sets the number of bits (i.e. input discretes) to be read with this
	 * <tt>ReadInputDiscretesRequest</tt>.
	 * <p>
	 * 
	 * @param count
	 *            the number of bits to be read.
	 */
	public void setBitCount(int count) {
		// assert <=2000
		m_BitCount = count;
		// setChanged(true);
	}// setBitCount

	/**
	 * Returns the number of bits (i.e. input discretes) to be read with this
	 * <tt>ReadInputDiscretesRequest</tt>.
	 * <p>
	 * 
	 * @return the number of bits to be read.
	 */
	public int getBitCount() {
		return m_BitCount;
	}// getBitCount

	public void writeData(DataOutput dout) throws IOException {
		dout.writeShort(m_Reference);
		dout.writeShort(m_BitCount);
	}// writeData

	public void readData(DataInput din) throws IOException {
		m_Reference = din.readUnsignedShort();
		m_BitCount = din.readUnsignedShort();
	}// readData

	/*
	 * protected void assembleData() throws IOException {
	 * m_DataOut.writeShort(m_Reference); m_DataOut.writeShort(m_BitCount);
	 * }//assembleData
	 * 
	 * public void readData(DataInputStream in) throws IOException, EOFException
	 * { m_Reference = in.readUnsignedShort(); m_BitCount =
	 * in.readUnsignedShort(); }//readData
	 */
}// class ReadInputDiscretesRequest
