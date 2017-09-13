package com.xzh.base;

import java.nio.ByteBuffer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.YieldingWaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;

public class LongEventMain {

	public static void main(String[] args) throws Exception {
		//���������
		ExecutorService  executor = Executors.newCachedThreadPool();
		//��������
		LongEventFactory factory = new LongEventFactory();
		//����bufferSize ,Ҳ����RingBuffer��С��������2��N�η�
		int ringBufferSize = 1024 * 1024; // 

		/**
		//BlockingWaitStrategy �����Ч�Ĳ��ԣ������CPU��������С�����ڸ��ֲ�ͬ���𻷾������ṩ����һ�µ����ܱ���
		WaitStrategy BLOCKING_WAIT = new BlockingWaitStrategy();
		//SleepingWaitStrategy �����ܱ��ָ�BlockingWaitStrategy��࣬��CPU������Ҳ���ƣ�������������̵߳�Ӱ����С���ʺ������첽��־���Ƶĳ���
		WaitStrategy SLEEPING_WAIT = new SleepingWaitStrategy();
		//YieldingWaitStrategy ����������õģ��ʺ����ڵ��ӳٵ�ϵͳ����Ҫ�󼫸��������¼���������С��CPU�߼��������ĳ����У��Ƽ�ʹ�ô˲��ԣ����磬CPU�������̵߳�����
		WaitStrategy YIELDING_WAIT = new YieldingWaitStrategy();
		*/
		
		//����disruptor
		Disruptor<LongEvent> disruptor = 
				new Disruptor<LongEvent>(factory, ringBufferSize, executor, ProducerType.SINGLE, new YieldingWaitStrategy());
		// ���������¼�����
		disruptor.handleEventsWith(new LongEventHandler());
		
		// ����
		disruptor.start();
		
		//Disruptor ���¼�����������һ�����׶��ύ�Ĺ��̣�
		//�����¼�
		RingBuffer<LongEvent> ringBuffer = disruptor.getRingBuffer();
		
		LongEventProducer producer = new LongEventProducer(ringBuffer); 
		//LongEventProducerWithTranslator producer = new LongEventProducerWithTranslator(ringBuffer);
		ByteBuffer byteBuffer = ByteBuffer.allocate(8);
		//����100������
		for(long l = 0; l<100; l++){
			byteBuffer.putLong(0, l);
			producer.onData(byteBuffer);
			//Thread.sleep(1000);
		}

		
		disruptor.shutdown();//�ر� disruptor�������������ֱ�����е��¼����õ�����
		executor.shutdown();//�ر� disruptor ʹ�õ��̳߳أ������Ҫ�Ļ��������ֶ��رգ� disruptor �� shutdown ʱ�����Զ��رգ�		
		
		
		
		
		
		
		
	}
}
