package com.dn9x.modbus.msg;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class UnityWriteSingleRegisterResponse extends UnityModbusResponse {

	// instance attributes
	private int m_Reference;
	private int m_RegisterValue;

	/**
	 * Constructs a new <tt>WriteSingleRegisterResponse</tt> instance.
	 */
	public UnityWriteSingleRegisterResponse(UnityModbusCoupler umc) {
		super();
		setDataLength(4);
	}// constructor

	/**
	 * Constructs a new <tt>WriteSingleRegisterResponse</tt> instance.
	 * 
	 * @param reference
	 *            the offset of the register written.
	 * @param value
	 *            the value of the register.
	 */
	public UnityWriteSingleRegisterResponse(int reference, int value, UnityModbusCoupler umc) {
		super();
		setReference(reference);
		setRegisterValue(value);
		setDataLength(4);
	}// constructor

	/**
	 * Returns the value that has been returned in this
	 * <tt>WriteSingleRegisterResponse</tt>.
	 * <p>
	 * 
	 * @return the value of the register.
	 */
	public int getRegisterValue() {
		return m_RegisterValue;
	}// getValue

	/**
	 * Sets the value that has been returned in the response message.
	 * <p>
	 * 
	 * @param value
	 *            the returned register value.
	 */
	private void setRegisterValue(int value) {
		m_RegisterValue = value;
	}// setRegisterValue

	/**
	 * Returns the reference of the register that has been written to.
	 * <p>
	 * 
	 * @return the reference of the written register.
	 */
	public int getReference() {
		return m_Reference;
	}// getReference

	/**
	 * Sets the reference of the register that has been written to.
	 * <p>
	 * 
	 * @param ref
	 *            the reference of the written register.
	 */
	private void setReference(int ref) {
		m_Reference = ref;
		// setChanged(true);
	}// setReference

	public void writeData(DataOutput dout) throws IOException {
		dout.writeShort(getReference());
		dout.writeShort(getRegisterValue());
	}// writeData

	public void readData(DataInput din) throws IOException {
		setReference(din.readUnsignedShort());
		setRegisterValue(din.readUnsignedShort());
		// update data length
		setDataLength(4);
	}// readData
	/*
	 * protected void assembleData() throws IOException {
	 * m_DataOut.writeShort(getReference());
	 * m_DataOut.writeShort(getRegisterValue()); }//assembleData
	 * 
	 * protected void readData(DataInputStream in) throws EOFException,
	 * IOException {
	 * 
	 * setReference(in.readUnsignedShort());
	 * setRegisterValue(in.readUnsignedShort()); //update data length
	 * setDataLength(4); }//readData
	 */

}// class WriteSingleRegisterResponse
