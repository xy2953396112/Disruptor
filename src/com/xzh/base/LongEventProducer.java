package com.xzh.base;

import java.nio.ByteBuffer;

import com.lmax.disruptor.RingBuffer;
/**
 * �����Ե��ǣ�����һ���򵥶����������¼���ʱ���ǣ������ϸ�ڣ�������Ϊ�¼�������ҪԤ�ȴ�����
 * �����¼�������Ҫ��������ȡ��һ���¼��۲������¼��������¼���ʱ��Ҫʹ��try/finnally��֤�¼�һ���ᱻ��������
 * �������ʹ��RingBuffer.next()��ȡһ���¼��ۣ���ôһ��Ҫ������Ӧ���¼���
 * ������ܷ����¼�����ô�ͻ�����Disruptor״̬�Ļ��ҡ�
 * �������ڶ���¼������ߵ�����»ᵼ���¼�������ʧ�٣��Ӷ����ò�����Ӧ�ò��ܻ�ָ���
 * <B>ϵͳ���ƣ�</B><BR>
 * <B>ģ�����ƣ�</B><BR>
 * <B>����������</B><BR>
 * <B>��Ҫ˵����</B><BR>
 * @author ������ѧ�ã�alienware��
 * @since 2015��11��23��
 */
public class LongEventProducer {

	private final RingBuffer<LongEvent> ringBuffer;
	
	public LongEventProducer(RingBuffer<LongEvent> ringBuffer){
		this.ringBuffer = ringBuffer;
	}
	
	/**
	 * onData���������¼���ÿ����һ�ξͷ���һ���¼�
	 * ���Ĳ������ù��¼����ݸ�������
	 */
	public void onData(ByteBuffer bb){
		//1.���԰�ringBuffer����һ���¼����У���ônext���ǵõ�����һ���¼���
		long sequence = ringBuffer.next();
		try {
			//2.�����������ȡ��һ���յ��¼�������䣨��ȡ����Ŷ�Ӧ���¼�����
			LongEvent event = ringBuffer.get(sequence);
			//3.��ȡҪͨ���¼����ݵ�ҵ������
			event.setValue(bb.getLong(0));
		} finally {
			//4.�����¼�
			//ע�⣬���� ringBuffer.publish ������������� finally ����ȷ������õ����ã����ĳ������� sequence δ���ύ��������������ķ����������������� producer��
			ringBuffer.publish(sequence);
		}
	}
	
	
	
	
	
}
