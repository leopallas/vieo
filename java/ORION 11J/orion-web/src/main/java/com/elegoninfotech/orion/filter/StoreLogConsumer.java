/**
 * 
 */
package com.elegoninfotech.orion.filter;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.workin.queue.PeriodConsumer;

/**
 * @author <a href="mailto:yao.angellin@gmail.com">Angellin Yao</a>
 *
 */
@Repository
public class StoreLogConsumer extends PeriodConsumer {

	@Override
	protected void processMessageList(List<?> messageList) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void clean() {
		// TODO Auto-generated method stub

	}

}
