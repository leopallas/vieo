package org.workin.orm;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.google.common.collect.Lists;

/**
 * Pagination support class, the Sequence number will be start with 1.
 * 
 * @author <a href="mailto:yao.angellin@gmail.com">Angellin Yao</a>
 *
 * @param <T> Record type in page
 */
public class Page<T> {
	//-- Global variables --//
	public static final String ASC = "asc";
	public static final String DESC = "desc";

	//-- Pagination support variables --//
	protected int pageNo = 1;
	protected int pageSize = -1;
	protected String orderBy = null;
	protected String order = null;
	protected boolean autoCount = true;

	//-- return result --//
	protected List<T> result = Lists.newArrayList();
	protected long totalCount = -1;

	//-- construct methods --//
	public Page() {
	}

	public Page(int pageSize) {
		this.pageSize = pageSize;
	}

	/**
	 * Get current page No., start with 1 and default is 1.
	 * @return
	 */
	public int getPageNo() {
		return pageNo;
	}

	/**
	 * Set current page No. start with 1 and the minimize No. is 1.
	 * @param pageNo
	 */
	public void setPageNo(final int pageNo) {
		this.pageNo = pageNo;

		if (pageNo < 1) {
			this.pageNo = 1;
		}
	}

	/**
	 * Return setPageNo method of page object, and it could be used for continue settings.
	 * @param thePageNo
	 * @return
	 */
	public Page<T> pageNo(final int thePageNo) {
		setPageNo(thePageNo);
		return this;
	}

	/**
	 * @return page size of each page, default -1.
	 */
	public int getPageSize() {
		return pageSize;
	}

	/**
	 * Set page size of each page
	 * @param pageSize
	 */
	public void setPageSize(final int pageSize) {
		this.pageSize = pageSize;
	}

	/**
	 * Return setPageSize method of page object, and it could be used for continue settings.
	 * @param thePageSize
	 * @return
	 */
	public Page<T> pageSize(final int thePageSize) {
		setPageSize(thePageSize);
		return this;
	}

	/**
	 * Calculate first record position of the current page, start with 1.
	 * @return first record position of the current page
	 */
	public int getFirst() {
		return ((pageNo - 1) * pageSize) + 1;
	}

	/**
	 * Return order fields for the list. If there are more than one field, then split with ','.
	 * @return order fields string
	 */
	public String getOrderBy() {
		return orderBy;
	}

	/**
	 * Set order fields. If there are more than one order field, then split with ','.
	 * @param orderBy
	 */
	public void setOrderBy(final String orderBy) {
		this.orderBy = orderBy;
	}

	/**
	 * Return setOrderBy method of page object, and it could be used for continue settings.
	 * @param theOrderBy
	 * @return
	 */
	public Page<T> orderBy(final String theOrderBy) {
		setOrderBy(theOrderBy);
		return this;
	}

	/**
	 * @return order 'ASC' or 'DESC', not determine if null
	 */
	public String getOrder() {
		return order;
	}

	/**
	 * Set order fields
	 * @param order 'ASC' or 'DESC', and will split with ',' if there are more than one filed
	 */
	public void setOrder(final String order) {
		String lowcaseOrder = StringUtils.lowerCase(order);

		//check if order string is valid
		String[] orders = StringUtils.split(lowcaseOrder, ',');
		for (String orderStr : orders) {
			if (!StringUtils.equals(DESC, orderStr) && !StringUtils.equals(ASC, orderStr)) {
				throw new IllegalArgumentException("Order direction " + orderStr + " is invalid.");
			}
		}

		this.order = lowcaseOrder;
	}

	/**
	 * Return setOrder method of page object, and it could be used for continue settings.
	 * @param theOrder
	 * @return
	 */
	public Page<T> order(final String theOrder) {
		setOrder(theOrder);
		return this;
	}

	/**
	 * @return if there exist order fields
	 */
	public boolean isOrderBySetted() {
		return (StringUtils.isNotBlank(orderBy) && StringUtils.isNotBlank(order));
	}

	/**
	 * When retrieve the query list, check if system need to auto count the total records, default is false.
	 * @return 
	 */
	public boolean isAutoCount() {
		return autoCount;
	}

	/**
	 * When retrieve the query list, set if system need to auto count the total records, default is false.
	 * @param autoCount
	 */
	public void setAutoCount(final boolean autoCount) {
		this.autoCount = autoCount;
	}

	/**
	 * Return setAutoCount method of page object, and it could be used for continue settings.
	 * @param theAutoCount
	 * @return
	 */
	public Page<T> autoCount(final boolean theAutoCount) {
		setAutoCount(theAutoCount);
		return this;
	}

	//--methods for visit query result --//

	/**
	 * @return page records
	 */
	public List<T> getResult() {
		return result;
	}

	/**
	 * Set page records
	 * @param result
	 */
	public void setResult(final List<T> result) {
		this.result = result;
	}

	/**
	 * @return total count of records, default is -1.
	 */
	public long getTotalCount() {
		return totalCount;
	}

	/**
	 * Set total count
	 * @param totalCount
	 */
	public void setTotalCount(final long totalCount) {
		this.totalCount = totalCount;
	}

	/**
	 * Calculate total pages based on the pageSize and totalCount, default is -1.
	 * @return
	 */
	public long getTotalPages() {
		if (totalCount < 0) {
			return -1;
		}

		long count = totalCount / pageSize;
		if (totalCount % pageSize > 0) {
			count++;
		}
		return count;
	}

	/**
	 * @return if the next page exist
	 */
	public boolean isHasNext() {
		return (pageNo + 1 <= getTotalPages());
	}

	/**
	 * @return Page number of next page, start with 1. When current page is the last page, then still return page number of last page.
	 */
	public int getNextPage() {
		if (isHasNext()) {
			return pageNo + 1;
		} else {
			return pageNo;
		}
	}

	/**
	 * @return if previous page exist
	 */
	public boolean isHasPre() {
		return (pageNo - 1 >= 1);
	}

	/**
	 * @return Page number of previous page, start with 1. When current page is the first page, then still return page number of first page.
	 */
	public int getPrePage() {
		if (isHasPre()) {
			return pageNo - 1;
		} else {
			return pageNo;
		}
	}
}
