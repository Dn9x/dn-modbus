package com.dn9x.modbus.msg;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import net.wimpi.modbus.Modbus;

public class UnityWriteMultipleCoilsResponse extends UnityModbusResponse {

	// instance attributes
	private int m_Reference;
	private int m_BitCount;

	/**
	 * Constructs a new <tt>WriteMultipleCoilsResponse</tt> instance.
	 */
	public UnityWriteMultipleCoilsResponse(UnityModbusCoupler umc) {
		super();
		setFunctionCode(Modbus.WRITE_MULTIPLE_COILS);
		setDataLength(4);
	}// constructor(int)

	/**
	 * Constructs a new <tt>WriteMultipleCoilsResponse</tt> instance with a
	 * given count of coils (i.e. bits). <b>
	 * 
	 * @param ref
	 *            the offset to begin writing from.
	 * @param count
	 *            the number of bits to be read.
	 */
	public UnityWriteMultipleCoilsResponse(int ref, int count,UnityModbusCoupler umc) {
		super();
		setFunctionCode(Modbus.WRITE_MULTIPLE_COILS);
		setDataLength(4);
		m_Reference = ref;
		m_BitCount = count;
	}// constructor(int)

	/**
	 * Returns the reference of the register to to start reading from with this
	 * <tt>WriteMultipleCoilsRequest</tt>.
	 * <p>
	 * 
	 * @return the reference of the register to start reading from as
	 *         <tt>int</tt>.
	 */
	public int getReference() {
		return m_Reference;
	}// getReference

	/**
	 * Returns the number of bits (i.e. coils) read with the request.
	 * <p>
	 * 
	 * @return the number of bits that have been read.
	 */
	public int getBitCount() {
		return m_BitCount;
	}// getBitCount

	/**
	 * Sets the number of bits (i.e. coils) that will be in a response.
	 * 
	 * @param count
	 *            the number of bits in the response.
	 */
	public void setBitCount(int count) {
		m_BitCount = count;
	}// setBitCount

	public void writeData(DataOutput dout) throws IOException {

		dout.writeShort(m_Reference);
		dout.writeShort(m_BitCount);
	}// writeData

	public void readData(DataInput din) throws IOException {

		m_Reference = din.readUnsignedShort();
		m_BitCount = din.readUnsignedShort();
	}// readData

}// class ReadCoilsResponse
