package com.xzh.base;

import com.lmax.disruptor.EventHandler;

//���ǻ���Ҫһ���¼������ߣ�Ҳ����һ���¼�������������¼��������򵥵ذ��¼��д洢�����ݴ�ӡ���նˣ�
public class LongEventHandler implements EventHandler<LongEvent>  {
    
	//�����¼�
	@Override
	public void onEvent(LongEvent longEvent, long l, boolean b) throws Exception {
		System.out.println(longEvent.getValue()); 		
	}

}
