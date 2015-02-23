package com.dn9x.modbus.msg;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import net.wimpi.modbus.Modbus;
import net.wimpi.modbus.util.BitVector;

public class UnityReadCoilsResponse extends UnityModbusResponse {

	// instance attributes
	private BitVector m_Coils;

	/**
	 * Constructs a new <tt>ReadCoilsResponse</tt> instance.
	 */
	public UnityReadCoilsResponse(UnityModbusCoupler umc) {
		super();
		setFunctionCode(Modbus.READ_COILS);
	}// constructor(int)

	/**
	 * Constructs a new <tt>ReadCoilsResponse</tt> instance with a given count
	 * of coils (i.e. bits). <b>
	 * 
	 * @param count
	 *            the number of bits to be read.
	 */
	public UnityReadCoilsResponse(int count,UnityModbusCoupler umc) {
		super();
		m_Coils = new BitVector(count);
		setFunctionCode(Modbus.READ_COILS);
		setDataLength(m_Coils.byteSize() + 1);
	}// constructor(int)

	/**
	 * Returns the number of bits (i.e. coils) read with the request.
	 * <p>
	 * 
	 * @return the number of bits that have been read.
	 */
	public int getBitCount() {
		if (m_Coils == null) {
			return 0;
		} else {
			return m_Coils.size();
		}
	}// getBitCount

	/**
	 * Returns the <tt>BitVector</tt> that stores the collection of bits that
	 * have been read.
	 * <p>
	 * 
	 * @return the <tt>BitVector</tt> holding the bits that have been read.
	 */
	public BitVector getCoils() {
		return m_Coils;
	}// getCoils

	/**
	 * Convenience method that returns the state of the bit at the given index.
	 * <p>
	 * 
	 * @param index
	 *            the index of the coil for which the status should be returned.
	 * 
	 * @return true if set, false otherwise.
	 * 
	 * @throws IndexOutOfBoundsException
	 *             if the index is out of bounds
	 */
	public boolean getCoilStatus(int index) throws IndexOutOfBoundsException {

		return m_Coils.getBit(index);
	}// getCoilStatus

	/**
	 * Sets the status of the given coil.
	 * 
	 * @param index
	 *            the index of the coil to be set.
	 * @param b
	 *            true if to be set, false for reset.
	 */
	public void setCoilStatus(int index, boolean b) {
		m_Coils.setBit(index, b);
	}// setCoilStatus

	public void writeData(DataOutput dout) throws IOException {
		dout.writeByte(m_Coils.byteSize());
		dout.write(m_Coils.getBytes(), 0, m_Coils.byteSize());
	}// writeData

	public void readData(DataInput din) throws IOException {
		int count = din.readUnsignedByte();
		byte[] data = new byte[count];
		for (int k = 0; k < count; k++) {
			data[k] = din.readByte();
		}
		// decode bytes into bitvector
		m_Coils = BitVector.createBitVector(data);
		// update data length
		setDataLength(count + 1);
	}// readData

}// class ReadCoilsResponse
