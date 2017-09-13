package com.xzh.base;

import java.nio.ByteBuffer;

import com.lmax.disruptor.EventTranslatorOneArg;
import com.lmax.disruptor.RingBuffer;

/**
 * Disruptor 3.0�ṩ��lambdaʽ��API���������԰�һЩ���ӵĲ�������Ring Buffer�� 
 * ������Disruptor3.0�Ժ�İ汾���ʹ��Event Publisher����Event Translator�������¼�
 * <B>ϵͳ���ƣ�</B><BR>
 * <B>ģ�����ƣ�</B><BR>
 * <B>����������</B><BR>
 * <B>��Ҫ˵����</B><BR>
 * @author ������ѧ�ã�alienware��
 * @since 2015��11��23��
 */
public class LongEventProducerWithTranslator {

	//һ��translator���Կ���һ���¼���ʼ������publicEvent�����������
	//���Event
	private static final EventTranslatorOneArg<LongEvent, ByteBuffer> TRANSLATOR = 
			new EventTranslatorOneArg<LongEvent, ByteBuffer>() {
				@Override
				public void translateTo(LongEvent event, long sequeue, ByteBuffer buffer) {
					event.setValue(buffer.getLong(0));
				}
			};
	
	private final RingBuffer<LongEvent> ringBuffer;
	
	public LongEventProducerWithTranslator(RingBuffer<LongEvent> ringBuffer) {
		this.ringBuffer = ringBuffer;
	}
	
	public void onData(ByteBuffer buffer){
		ringBuffer.publishEvent(TRANSLATOR, buffer);
	}
	
	
	
}
