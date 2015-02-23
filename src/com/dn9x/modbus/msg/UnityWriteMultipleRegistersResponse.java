package com.dn9x.modbus.msg;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class UnityWriteMultipleRegistersResponse extends UnityModbusResponse {

	// instance attributes
	private int m_WordCount;
	private int m_Reference;


	/**
	 * Constructs a new <tt>WriteMultipleRegistersResponse</tt> instance.
	 */
	public UnityWriteMultipleRegistersResponse(UnityModbusCoupler umc) {
		super();
	}// constructor

	/**
	 * Constructs a new <tt>WriteMultipleRegistersResponse</tt> instance.
	 * 
	 * @param reference
	 *            the offset to start reading from.
	 * @param wordcount
	 *            the number of words (registers) to be read.
	 */
	public UnityWriteMultipleRegistersResponse(int reference, int wordcount,UnityModbusCoupler umc) {
		super();
		m_Reference = reference;
		m_WordCount = wordcount;
		setDataLength(4);
	}// constructor

	/**
	 * Sets the reference of the register to start writing to with this
	 * <tt>WriteMultipleRegistersResponse</tt>.
	 * <p>
	 * 
	 * @param ref
	 *            the reference of the register to start writing to as
	 *            <tt>int</tt>.
	 */
	private void setReference(int ref) {
		m_Reference = ref;
	}// setReference

	/**
	 * Returns the reference of the register to start writing to with this
	 * <tt>WriteMultipleRegistersResponse</tt>.
	 * <p>
	 * 
	 * @return the reference of the register to start writing to as <tt>int</tt>
	 *         .
	 */
	public int getReference() {
		return m_Reference;
	}// getReference

	/**
	 * Returns the number of bytes that have been written.
	 * <p>
	 * 
	 * @return the number of bytes that have been read as <tt>int</tt>.
	 */
	public int getByteCount() {
		return m_WordCount * 2;
	}// getByteCount

	/**
	 * Returns the number of words that have been read. The returned value
	 * should be half of the byte count of the response.
	 * <p>
	 * 
	 * @return the number of words that have been read as <tt>int</tt>.
	 */
	public int getWordCount() {
		return m_WordCount;
	}// getWordCount

	/**
	 * Sets the number of words that have been returned.
	 * <p>
	 * 
	 * @param count
	 *            the number of words as <tt>int</tt>.
	 */
	private void setWordCount(int count) {
		m_WordCount = count;
	}// setWordCount

	public void writeData(DataOutput dout) throws IOException {
		dout.writeShort(m_Reference);
		dout.writeShort(getWordCount());
	}// writeData

	public void readData(DataInput din) throws IOException {

		setReference(din.readUnsignedShort());
		setWordCount(din.readUnsignedShort());
		// NOTE: register values are not echoed

		// update data length
		setDataLength(4);
	}// readData

}// class WriteMultipleRegistersResponse
