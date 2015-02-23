package com.dn9x.modbus.msg;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import net.wimpi.modbus.Modbus;
import net.wimpi.modbus.procimg.InputRegister;
import net.wimpi.modbus.procimg.ProcessImageFactory;

public class UnityReadInputRegistersResponse extends UnityModbusResponse {

	// instance attributes
	private int m_ByteCount;
	// private int[] m_RegisterValues;
	private InputRegister[] m_Registers;
	private UnityModbusCoupler m_umc;

	/**
	 * Constructs a new <tt>ReadInputRegistersResponse</tt> instance.
	 */
	public UnityReadInputRegistersResponse(UnityModbusCoupler umc) {
		super();
		 m_umc = umc;
		setFunctionCode(Modbus.READ_INPUT_REGISTERS);
	}// constructor

	/**
	 * Constructs a new <tt>ReadInputRegistersResponse</tt> instance.
	 * 
	 * @param registers
	 *            the InputRegister[] holding response input registers.
	 */
	public UnityReadInputRegistersResponse(InputRegister[] registers, UnityModbusCoupler umc) {
		super();
		 m_umc = umc;
		setFunctionCode(Modbus.READ_INPUT_REGISTERS);
		m_ByteCount = registers.length * 2;
		m_Registers = registers;
		// set correct data length excluding unit id and fc
		setDataLength(m_ByteCount + 1);
	}// constructor

	/**
	 * Returns the number of bytes that have been read.
	 * <p/>
	 * 
	 * @return the number of bytes that have been read as <tt>int</tt>.
	 */
	public int getByteCount() {
		return m_ByteCount;
	}// getByteCount

	/**
	 * Returns the number of words that have been read. The returned value
	 * should be twice as much as the byte count of the response.
	 * <p/>
	 * 
	 * @return the number of words that have been read as <tt>int</tt>.
	 */
	public int getWordCount() {
		return m_ByteCount / 2;
	}// getWordCount

	/**
	 * Sets the number of bytes that have been returned.
	 * <p/>
	 * 
	 * @param count
	 *            the number of bytes as <tt>int</tt>.
	 */
	private void setByteCount(int count) {
		m_ByteCount = count;
	}// setByteCount

	/**
	 * Returns the <tt>InputRegister</tt> at the given position (relative to the
	 * reference used in the request).
	 * <p/>
	 * 
	 * @param index
	 *            the relative index of the <tt>InputRegister</tt>.
	 * @return the register as <tt>InputRegister</tt>.
	 * @throws IndexOutOfBoundsException
	 *             if the index is out of bounds.
	 */
	public InputRegister getRegister(int index)
			throws IndexOutOfBoundsException {

		if (index >= getWordCount()) {
			throw new IndexOutOfBoundsException();
		} else {
			return m_Registers[index];
		}
	}// getRegister

	/**
	 * Returns the value of the register at the given position (relative to the
	 * reference used in the request) interpreted as usigned short.
	 * <p/>
	 * 
	 * @param index
	 *            the relative index of the register for which the value should
	 *            be retrieved.
	 * @return the value as <tt>int</tt>.
	 * @throws IndexOutOfBoundsException
	 *             if the index is out of bounds.
	 */
	public int getRegisterValue(int index) throws IndexOutOfBoundsException {

		if (index >= getWordCount()) {
			throw new IndexOutOfBoundsException();
		} else {
			return m_Registers[index].toUnsignedShort();
		}
	}// getRegisterValue
	
	/**
	 * Returns a reference to the array of input registers read.
	 * 
	 * @return a <tt>InputRegister[]</tt> instance.
	 */
	public InputRegister[] getRegisters() {
		return m_Registers;
	}// getRegisters

	public void writeData(DataOutput dout) throws IOException {
		dout.writeByte(m_ByteCount);
		for (int k = 0; k < getWordCount(); k++) {
			dout.write(m_Registers[k].toBytes());
		}
	}// writeData

	public void readData(DataInput din) throws IOException {
		setByteCount(din.readUnsignedByte());

		InputRegister[] registers = new InputRegister[getWordCount()];
		ProcessImageFactory pimf =  m_umc
				.getProcessImageFactory();
		for (int k = 0; k < getWordCount(); k++) {
			registers[k] = pimf.createInputRegister(din.readByte(),
					din.readByte());
		}
		m_Registers = registers;
		// update data length
		setDataLength(getByteCount() + 1);
	}// readData

}// class ReadInputRegistersResponse
