package com.xzh.multi;

import java.nio.ByteBuffer;
import java.util.UUID;

import com.xzh.base.LongEvent;

import com.lmax.disruptor.EventTranslatorOneArg;
import com.lmax.disruptor.RingBuffer;

/**
 * <B>ϵͳ���ƣ�</B><BR>
 * <B>ģ�����ƣ�</B><BR>
 * <B>����������</B><BR>
 * <B>��Ҫ˵����</B><BR>
 * @author ������ѧ�ã�alienware��
 * @since 2015��11��23��
 */
public class Producer {

	private final RingBuffer<Order> ringBuffer;
	
	public Producer(RingBuffer<Order> ringBuffer){
		this.ringBuffer = ringBuffer;
	}
	
	/**
	 * onData���������¼���ÿ����һ�ξͷ���һ���¼�
	 * ���Ĳ������ù��¼����ݸ�������
	 */
	public void onData(String data){
		//���԰�ringBuffer����һ���¼����У���ônext���ǵõ�����һ���¼���
		long sequence = ringBuffer.next();
		try {
			//�����������ȡ��һ���յ��¼�������䣨��ȡ����Ŷ�Ӧ���¼�����
			Order order = ringBuffer.get(sequence);
			//��ȡҪͨ���¼����ݵ�ҵ������
			order.setId(data);
		} finally {
			//�����¼�
			//ע�⣬���� ringBuffer.publish ������������� finally ����ȷ������õ����ã����ĳ������� sequence δ���ύ��������������ķ����������������� producer��
			ringBuffer.publish(sequence);
		}
	}
	
	
}
