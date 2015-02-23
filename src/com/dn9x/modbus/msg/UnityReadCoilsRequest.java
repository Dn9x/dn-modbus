package com.dn9x.modbus.msg;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import net.wimpi.modbus.Modbus;
import net.wimpi.modbus.procimg.DigitalOut;
import net.wimpi.modbus.procimg.IllegalAddressException;
import net.wimpi.modbus.procimg.ProcessImage;

public class UnityReadCoilsRequest extends UnityModbusRequest {

	// instance attributes
	private int m_Reference;
	private int m_BitCount;
	private UnityModbusCoupler m_umc;

	/**
	 * Constructs a new <tt>ReadCoilsRequest</tt> instance.
	 */
	public UnityReadCoilsRequest(UnityModbusCoupler umc) {
		super();
		m_umc = umc;
		setFunctionCode(Modbus.READ_COILS);
		// 4 bytes (unit id and function code is excluded)
		setDataLength(4);
	}// constructor

	/**
	 * Constructs a new <tt>ReadCoilsRequest</tt> instance with a given
	 * reference and count of coils (i.e. bits) to be read.
	 * <p>
	 * 
	 * @param ref
	 *            the reference number of the register to read from.
	 * @param count
	 *            the number of bits to be read.
	 */
	public UnityReadCoilsRequest(int ref, int count) {
		super();
		setFunctionCode(Modbus.READ_COILS);
		// 4 bytes (unit id and function code is excluded)
		setDataLength(4);
		setReference(ref);
		setBitCount(count);
	}// constructor

	public UnityModbusResponse createResponse() {
		UnityReadCoilsResponse response = null;
		DigitalOut[] douts = null;

		// 1. get process image
		ProcessImage procimg = m_umc.getProcessImage();
		// 2. get coil range
		try {
			douts = procimg.getDigitalOutRange(this.getReference(),
					this.getBitCount());
		} catch (IllegalAddressException iaex) {
			return createExceptionResponse(Modbus.ILLEGAL_ADDRESS_EXCEPTION);
		}
		response = new UnityReadCoilsResponse(douts.length, m_umc);

		// transfer header data
		if (!isHeadless()) {
			response.setTransactionID(this.getTransactionID());
			response.setProtocolID(this.getProtocolID());
		} else {
			response.setHeadless();
		}
		response.setUnitID(this.getUnitID());
		response.setFunctionCode(this.getFunctionCode());

		for (int i = 0; i < douts.length; i++) {
			response.setCoilStatus(i, douts[i].isSet());
		}
		return response;
	}// createResponse

	/**
	 * Sets the reference of the register to start reading from with this
	 * <tt>ReadCoilsRequest</tt>.
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
	 * <tt>ReadCoilsRequest</tt>.
	 * <p>
	 * 
	 * @return the reference of the register to start reading from as
	 *         <tt>int</tt>.
	 */
	public int getReference() {
		return m_Reference;
	}// getReference

	/**
	 * Sets the number of bits (i.e. coils) to be read with this
	 * <tt>ReadCoilsRequest</tt>.
	 * <p>
	 * 
	 * @param count
	 *            the number of bits to be read.
	 */
	public void setBitCount(int count) {
		if (count > Modbus.MAX_BITS) {
			throw new IllegalArgumentException("Maximum bitcount exceeded.");
		} else {
			m_BitCount = count;
		}
	}// setBitCount

	/**
	 * Returns the number of bits (i.e. coils) to be read with this
	 * <tt>ReadCoilsRequest</tt>.
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

}// class ReadCoilsRequest
