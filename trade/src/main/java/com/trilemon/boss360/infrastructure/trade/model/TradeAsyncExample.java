package com.trilemon.boss360.infrastructure.trade.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TradeAsyncExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public TradeAsyncExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Integer value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Integer value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Integer value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Integer value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Integer value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Integer> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Integer> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Integer value1, Integer value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Integer value1, Integer value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andUserIdIsNull() {
            addCriterion("user_id is null");
            return (Criteria) this;
        }

        public Criteria andUserIdIsNotNull() {
            addCriterion("user_id is not null");
            return (Criteria) this;
        }

        public Criteria andUserIdEqualTo(Long value) {
            addCriterion("user_id =", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotEqualTo(Long value) {
            addCriterion("user_id <>", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdGreaterThan(Long value) {
            addCriterion("user_id >", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdGreaterThanOrEqualTo(Long value) {
            addCriterion("user_id >=", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLessThan(Long value) {
            addCriterion("user_id <", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLessThanOrEqualTo(Long value) {
            addCriterion("user_id <=", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdIn(List<Long> values) {
            addCriterion("user_id in", values, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotIn(List<Long> values) {
            addCriterion("user_id not in", values, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdBetween(Long value1, Long value2) {
            addCriterion("user_id between", value1, value2, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotBetween(Long value1, Long value2) {
            addCriterion("user_id not between", value1, value2, "userId");
            return (Criteria) this;
        }

        public Criteria andShopIdIsNull() {
            addCriterion("shop_id is null");
            return (Criteria) this;
        }

        public Criteria andShopIdIsNotNull() {
            addCriterion("shop_id is not null");
            return (Criteria) this;
        }

        public Criteria andShopIdEqualTo(Long value) {
            addCriterion("shop_id =", value, "shopId");
            return (Criteria) this;
        }

        public Criteria andShopIdNotEqualTo(Long value) {
            addCriterion("shop_id <>", value, "shopId");
            return (Criteria) this;
        }

        public Criteria andShopIdGreaterThan(Long value) {
            addCriterion("shop_id >", value, "shopId");
            return (Criteria) this;
        }

        public Criteria andShopIdGreaterThanOrEqualTo(Long value) {
            addCriterion("shop_id >=", value, "shopId");
            return (Criteria) this;
        }

        public Criteria andShopIdLessThan(Long value) {
            addCriterion("shop_id <", value, "shopId");
            return (Criteria) this;
        }

        public Criteria andShopIdLessThanOrEqualTo(Long value) {
            addCriterion("shop_id <=", value, "shopId");
            return (Criteria) this;
        }

        public Criteria andShopIdIn(List<Long> values) {
            addCriterion("shop_id in", values, "shopId");
            return (Criteria) this;
        }

        public Criteria andShopIdNotIn(List<Long> values) {
            addCriterion("shop_id not in", values, "shopId");
            return (Criteria) this;
        }

        public Criteria andShopIdBetween(Long value1, Long value2) {
            addCriterion("shop_id between", value1, value2, "shopId");
            return (Criteria) this;
        }

        public Criteria andShopIdNotBetween(Long value1, Long value2) {
            addCriterion("shop_id not between", value1, value2, "shopId");
            return (Criteria) this;
        }

        public Criteria andSyncAppKeyIsNull() {
            addCriterion("sync_app_key is null");
            return (Criteria) this;
        }

        public Criteria andSyncAppKeyIsNotNull() {
            addCriterion("sync_app_key is not null");
            return (Criteria) this;
        }

        public Criteria andSyncAppKeyEqualTo(String value) {
            addCriterion("sync_app_key =", value, "syncAppKey");
            return (Criteria) this;
        }

        public Criteria andSyncAppKeyNotEqualTo(String value) {
            addCriterion("sync_app_key <>", value, "syncAppKey");
            return (Criteria) this;
        }

        public Criteria andSyncAppKeyGreaterThan(String value) {
            addCriterion("sync_app_key >", value, "syncAppKey");
            return (Criteria) this;
        }

        public Criteria andSyncAppKeyGreaterThanOrEqualTo(String value) {
            addCriterion("sync_app_key >=", value, "syncAppKey");
            return (Criteria) this;
        }

        public Criteria andSyncAppKeyLessThan(String value) {
            addCriterion("sync_app_key <", value, "syncAppKey");
            return (Criteria) this;
        }

        public Criteria andSyncAppKeyLessThanOrEqualTo(String value) {
            addCriterion("sync_app_key <=", value, "syncAppKey");
            return (Criteria) this;
        }

        public Criteria andSyncAppKeyLike(String value) {
            addCriterion("sync_app_key like", value, "syncAppKey");
            return (Criteria) this;
        }

        public Criteria andSyncAppKeyNotLike(String value) {
            addCriterion("sync_app_key not like", value, "syncAppKey");
            return (Criteria) this;
        }

        public Criteria andSyncAppKeyIn(List<String> values) {
            addCriterion("sync_app_key in", values, "syncAppKey");
            return (Criteria) this;
        }

        public Criteria andSyncAppKeyNotIn(List<String> values) {
            addCriterion("sync_app_key not in", values, "syncAppKey");
            return (Criteria) this;
        }

        public Criteria andSyncAppKeyBetween(String value1, String value2) {
            addCriterion("sync_app_key between", value1, value2, "syncAppKey");
            return (Criteria) this;
        }

        public Criteria andSyncAppKeyNotBetween(String value1, String value2) {
            addCriterion("sync_app_key not between", value1, value2, "syncAppKey");
            return (Criteria) this;
        }

        public Criteria andSyncStatusIsNull() {
            addCriterion("sync_status is null");
            return (Criteria) this;
        }

        public Criteria andSyncStatusIsNotNull() {
            addCriterion("sync_status is not null");
            return (Criteria) this;
        }

        public Criteria andSyncStatusEqualTo(Byte value) {
            addCriterion("sync_status =", value, "syncStatus");
            return (Criteria) this;
        }

        public Criteria andSyncStatusNotEqualTo(Byte value) {
            addCriterion("sync_status <>", value, "syncStatus");
            return (Criteria) this;
        }

        public Criteria andSyncStatusGreaterThan(Byte value) {
            addCriterion("sync_status >", value, "syncStatus");
            return (Criteria) this;
        }

        public Criteria andSyncStatusGreaterThanOrEqualTo(Byte value) {
            addCriterion("sync_status >=", value, "syncStatus");
            return (Criteria) this;
        }

        public Criteria andSyncStatusLessThan(Byte value) {
            addCriterion("sync_status <", value, "syncStatus");
            return (Criteria) this;
        }

        public Criteria andSyncStatusLessThanOrEqualTo(Byte value) {
            addCriterion("sync_status <=", value, "syncStatus");
            return (Criteria) this;
        }

        public Criteria andSyncStatusIn(List<Byte> values) {
            addCriterion("sync_status in", values, "syncStatus");
            return (Criteria) this;
        }

        public Criteria andSyncStatusNotIn(List<Byte> values) {
            addCriterion("sync_status not in", values, "syncStatus");
            return (Criteria) this;
        }

        public Criteria andSyncStatusBetween(Byte value1, Byte value2) {
            addCriterion("sync_status between", value1, value2, "syncStatus");
            return (Criteria) this;
        }

        public Criteria andSyncStatusNotBetween(Byte value1, Byte value2) {
            addCriterion("sync_status not between", value1, value2, "syncStatus");
            return (Criteria) this;
        }

        public Criteria andTaobaoCheckCodeIsNull() {
            addCriterion("taobao_check_code is null");
            return (Criteria) this;
        }

        public Criteria andTaobaoCheckCodeIsNotNull() {
            addCriterion("taobao_check_code is not null");
            return (Criteria) this;
        }

        public Criteria andTaobaoCheckCodeEqualTo(String value) {
            addCriterion("taobao_check_code =", value, "taobaoCheckCode");
            return (Criteria) this;
        }

        public Criteria andTaobaoCheckCodeNotEqualTo(String value) {
            addCriterion("taobao_check_code <>", value, "taobaoCheckCode");
            return (Criteria) this;
        }

        public Criteria andTaobaoCheckCodeGreaterThan(String value) {
            addCriterion("taobao_check_code >", value, "taobaoCheckCode");
            return (Criteria) this;
        }

        public Criteria andTaobaoCheckCodeGreaterThanOrEqualTo(String value) {
            addCriterion("taobao_check_code >=", value, "taobaoCheckCode");
            return (Criteria) this;
        }

        public Criteria andTaobaoCheckCodeLessThan(String value) {
            addCriterion("taobao_check_code <", value, "taobaoCheckCode");
            return (Criteria) this;
        }

        public Criteria andTaobaoCheckCodeLessThanOrEqualTo(String value) {
            addCriterion("taobao_check_code <=", value, "taobaoCheckCode");
            return (Criteria) this;
        }

        public Criteria andTaobaoCheckCodeLike(String value) {
            addCriterion("taobao_check_code like", value, "taobaoCheckCode");
            return (Criteria) this;
        }

        public Criteria andTaobaoCheckCodeNotLike(String value) {
            addCriterion("taobao_check_code not like", value, "taobaoCheckCode");
            return (Criteria) this;
        }

        public Criteria andTaobaoCheckCodeIn(List<String> values) {
            addCriterion("taobao_check_code in", values, "taobaoCheckCode");
            return (Criteria) this;
        }

        public Criteria andTaobaoCheckCodeNotIn(List<String> values) {
            addCriterion("taobao_check_code not in", values, "taobaoCheckCode");
            return (Criteria) this;
        }

        public Criteria andTaobaoCheckCodeBetween(String value1, String value2) {
            addCriterion("taobao_check_code between", value1, value2, "taobaoCheckCode");
            return (Criteria) this;
        }

        public Criteria andTaobaoCheckCodeNotBetween(String value1, String value2) {
            addCriterion("taobao_check_code not between", value1, value2, "taobaoCheckCode");
            return (Criteria) this;
        }

        public Criteria andTaobaoTaskIdIsNull() {
            addCriterion("taobao_task_id is null");
            return (Criteria) this;
        }

        public Criteria andTaobaoTaskIdIsNotNull() {
            addCriterion("taobao_task_id is not null");
            return (Criteria) this;
        }

        public Criteria andTaobaoTaskIdEqualTo(Long value) {
            addCriterion("taobao_task_id =", value, "taobaoTaskId");
            return (Criteria) this;
        }

        public Criteria andTaobaoTaskIdNotEqualTo(Long value) {
            addCriterion("taobao_task_id <>", value, "taobaoTaskId");
            return (Criteria) this;
        }

        public Criteria andTaobaoTaskIdGreaterThan(Long value) {
            addCriterion("taobao_task_id >", value, "taobaoTaskId");
            return (Criteria) this;
        }

        public Criteria andTaobaoTaskIdGreaterThanOrEqualTo(Long value) {
            addCriterion("taobao_task_id >=", value, "taobaoTaskId");
            return (Criteria) this;
        }

        public Criteria andTaobaoTaskIdLessThan(Long value) {
            addCriterion("taobao_task_id <", value, "taobaoTaskId");
            return (Criteria) this;
        }

        public Criteria andTaobaoTaskIdLessThanOrEqualTo(Long value) {
            addCriterion("taobao_task_id <=", value, "taobaoTaskId");
            return (Criteria) this;
        }

        public Criteria andTaobaoTaskIdIn(List<Long> values) {
            addCriterion("taobao_task_id in", values, "taobaoTaskId");
            return (Criteria) this;
        }

        public Criteria andTaobaoTaskIdNotIn(List<Long> values) {
            addCriterion("taobao_task_id not in", values, "taobaoTaskId");
            return (Criteria) this;
        }

        public Criteria andTaobaoTaskIdBetween(Long value1, Long value2) {
            addCriterion("taobao_task_id between", value1, value2, "taobaoTaskId");
            return (Criteria) this;
        }

        public Criteria andTaobaoTaskIdNotBetween(Long value1, Long value2) {
            addCriterion("taobao_task_id not between", value1, value2, "taobaoTaskId");
            return (Criteria) this;
        }

        public Criteria andTaobaoStatusIsNull() {
            addCriterion("taobao_status is null");
            return (Criteria) this;
        }

        public Criteria andTaobaoStatusIsNotNull() {
            addCriterion("taobao_status is not null");
            return (Criteria) this;
        }

        public Criteria andTaobaoStatusEqualTo(String value) {
            addCriterion("taobao_status =", value, "taobaoStatus");
            return (Criteria) this;
        }

        public Criteria andTaobaoStatusNotEqualTo(String value) {
            addCriterion("taobao_status <>", value, "taobaoStatus");
            return (Criteria) this;
        }

        public Criteria andTaobaoStatusGreaterThan(String value) {
            addCriterion("taobao_status >", value, "taobaoStatus");
            return (Criteria) this;
        }

        public Criteria andTaobaoStatusGreaterThanOrEqualTo(String value) {
            addCriterion("taobao_status >=", value, "taobaoStatus");
            return (Criteria) this;
        }

        public Criteria andTaobaoStatusLessThan(String value) {
            addCriterion("taobao_status <", value, "taobaoStatus");
            return (Criteria) this;
        }

        public Criteria andTaobaoStatusLessThanOrEqualTo(String value) {
            addCriterion("taobao_status <=", value, "taobaoStatus");
            return (Criteria) this;
        }

        public Criteria andTaobaoStatusLike(String value) {
            addCriterion("taobao_status like", value, "taobaoStatus");
            return (Criteria) this;
        }

        public Criteria andTaobaoStatusNotLike(String value) {
            addCriterion("taobao_status not like", value, "taobaoStatus");
            return (Criteria) this;
        }

        public Criteria andTaobaoStatusIn(List<String> values) {
            addCriterion("taobao_status in", values, "taobaoStatus");
            return (Criteria) this;
        }

        public Criteria andTaobaoStatusNotIn(List<String> values) {
            addCriterion("taobao_status not in", values, "taobaoStatus");
            return (Criteria) this;
        }

        public Criteria andTaobaoStatusBetween(String value1, String value2) {
            addCriterion("taobao_status between", value1, value2, "taobaoStatus");
            return (Criteria) this;
        }

        public Criteria andTaobaoStatusNotBetween(String value1, String value2) {
            addCriterion("taobao_status not between", value1, value2, "taobaoStatus");
            return (Criteria) this;
        }

        public Criteria andTradeStartTimeIsNull() {
            addCriterion("trade_start_time is null");
            return (Criteria) this;
        }

        public Criteria andTradeStartTimeIsNotNull() {
            addCriterion("trade_start_time is not null");
            return (Criteria) this;
        }

        public Criteria andTradeStartTimeEqualTo(Date value) {
            addCriterion("trade_start_time =", value, "tradeStartTime");
            return (Criteria) this;
        }

        public Criteria andTradeStartTimeNotEqualTo(Date value) {
            addCriterion("trade_start_time <>", value, "tradeStartTime");
            return (Criteria) this;
        }

        public Criteria andTradeStartTimeGreaterThan(Date value) {
            addCriterion("trade_start_time >", value, "tradeStartTime");
            return (Criteria) this;
        }

        public Criteria andTradeStartTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("trade_start_time >=", value, "tradeStartTime");
            return (Criteria) this;
        }

        public Criteria andTradeStartTimeLessThan(Date value) {
            addCriterion("trade_start_time <", value, "tradeStartTime");
            return (Criteria) this;
        }

        public Criteria andTradeStartTimeLessThanOrEqualTo(Date value) {
            addCriterion("trade_start_time <=", value, "tradeStartTime");
            return (Criteria) this;
        }

        public Criteria andTradeStartTimeIn(List<Date> values) {
            addCriterion("trade_start_time in", values, "tradeStartTime");
            return (Criteria) this;
        }

        public Criteria andTradeStartTimeNotIn(List<Date> values) {
            addCriterion("trade_start_time not in", values, "tradeStartTime");
            return (Criteria) this;
        }

        public Criteria andTradeStartTimeBetween(Date value1, Date value2) {
            addCriterion("trade_start_time between", value1, value2, "tradeStartTime");
            return (Criteria) this;
        }

        public Criteria andTradeStartTimeNotBetween(Date value1, Date value2) {
            addCriterion("trade_start_time not between", value1, value2, "tradeStartTime");
            return (Criteria) this;
        }

        public Criteria andTradeEndTimeIsNull() {
            addCriterion("trade_end_time is null");
            return (Criteria) this;
        }

        public Criteria andTradeEndTimeIsNotNull() {
            addCriterion("trade_end_time is not null");
            return (Criteria) this;
        }

        public Criteria andTradeEndTimeEqualTo(Date value) {
            addCriterion("trade_end_time =", value, "tradeEndTime");
            return (Criteria) this;
        }

        public Criteria andTradeEndTimeNotEqualTo(Date value) {
            addCriterion("trade_end_time <>", value, "tradeEndTime");
            return (Criteria) this;
        }

        public Criteria andTradeEndTimeGreaterThan(Date value) {
            addCriterion("trade_end_time >", value, "tradeEndTime");
            return (Criteria) this;
        }

        public Criteria andTradeEndTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("trade_end_time >=", value, "tradeEndTime");
            return (Criteria) this;
        }

        public Criteria andTradeEndTimeLessThan(Date value) {
            addCriterion("trade_end_time <", value, "tradeEndTime");
            return (Criteria) this;
        }

        public Criteria andTradeEndTimeLessThanOrEqualTo(Date value) {
            addCriterion("trade_end_time <=", value, "tradeEndTime");
            return (Criteria) this;
        }

        public Criteria andTradeEndTimeIn(List<Date> values) {
            addCriterion("trade_end_time in", values, "tradeEndTime");
            return (Criteria) this;
        }

        public Criteria andTradeEndTimeNotIn(List<Date> values) {
            addCriterion("trade_end_time not in", values, "tradeEndTime");
            return (Criteria) this;
        }

        public Criteria andTradeEndTimeBetween(Date value1, Date value2) {
            addCriterion("trade_end_time between", value1, value2, "tradeEndTime");
            return (Criteria) this;
        }

        public Criteria andTradeEndTimeNotBetween(Date value1, Date value2) {
            addCriterion("trade_end_time not between", value1, value2, "tradeEndTime");
            return (Criteria) this;
        }

        public Criteria andDownloadUrlIsNull() {
            addCriterion("download_url is null");
            return (Criteria) this;
        }

        public Criteria andDownloadUrlIsNotNull() {
            addCriterion("download_url is not null");
            return (Criteria) this;
        }

        public Criteria andDownloadUrlEqualTo(String value) {
            addCriterion("download_url =", value, "downloadUrl");
            return (Criteria) this;
        }

        public Criteria andDownloadUrlNotEqualTo(String value) {
            addCriterion("download_url <>", value, "downloadUrl");
            return (Criteria) this;
        }

        public Criteria andDownloadUrlGreaterThan(String value) {
            addCriterion("download_url >", value, "downloadUrl");
            return (Criteria) this;
        }

        public Criteria andDownloadUrlGreaterThanOrEqualTo(String value) {
            addCriterion("download_url >=", value, "downloadUrl");
            return (Criteria) this;
        }

        public Criteria andDownloadUrlLessThan(String value) {
            addCriterion("download_url <", value, "downloadUrl");
            return (Criteria) this;
        }

        public Criteria andDownloadUrlLessThanOrEqualTo(String value) {
            addCriterion("download_url <=", value, "downloadUrl");
            return (Criteria) this;
        }

        public Criteria andDownloadUrlLike(String value) {
            addCriterion("download_url like", value, "downloadUrl");
            return (Criteria) this;
        }

        public Criteria andDownloadUrlNotLike(String value) {
            addCriterion("download_url not like", value, "downloadUrl");
            return (Criteria) this;
        }

        public Criteria andDownloadUrlIn(List<String> values) {
            addCriterion("download_url in", values, "downloadUrl");
            return (Criteria) this;
        }

        public Criteria andDownloadUrlNotIn(List<String> values) {
            addCriterion("download_url not in", values, "downloadUrl");
            return (Criteria) this;
        }

        public Criteria andDownloadUrlBetween(String value1, String value2) {
            addCriterion("download_url between", value1, value2, "downloadUrl");
            return (Criteria) this;
        }

        public Criteria andDownloadUrlNotBetween(String value1, String value2) {
            addCriterion("download_url not between", value1, value2, "downloadUrl");
            return (Criteria) this;
        }

        public Criteria andDownloadFileIsNull() {
            addCriterion("download_file is null");
            return (Criteria) this;
        }

        public Criteria andDownloadFileIsNotNull() {
            addCriterion("download_file is not null");
            return (Criteria) this;
        }

        public Criteria andDownloadFileEqualTo(String value) {
            addCriterion("download_file =", value, "downloadFile");
            return (Criteria) this;
        }

        public Criteria andDownloadFileNotEqualTo(String value) {
            addCriterion("download_file <>", value, "downloadFile");
            return (Criteria) this;
        }

        public Criteria andDownloadFileGreaterThan(String value) {
            addCriterion("download_file >", value, "downloadFile");
            return (Criteria) this;
        }

        public Criteria andDownloadFileGreaterThanOrEqualTo(String value) {
            addCriterion("download_file >=", value, "downloadFile");
            return (Criteria) this;
        }

        public Criteria andDownloadFileLessThan(String value) {
            addCriterion("download_file <", value, "downloadFile");
            return (Criteria) this;
        }

        public Criteria andDownloadFileLessThanOrEqualTo(String value) {
            addCriterion("download_file <=", value, "downloadFile");
            return (Criteria) this;
        }

        public Criteria andDownloadFileLike(String value) {
            addCriterion("download_file like", value, "downloadFile");
            return (Criteria) this;
        }

        public Criteria andDownloadFileNotLike(String value) {
            addCriterion("download_file not like", value, "downloadFile");
            return (Criteria) this;
        }

        public Criteria andDownloadFileIn(List<String> values) {
            addCriterion("download_file in", values, "downloadFile");
            return (Criteria) this;
        }

        public Criteria andDownloadFileNotIn(List<String> values) {
            addCriterion("download_file not in", values, "downloadFile");
            return (Criteria) this;
        }

        public Criteria andDownloadFileBetween(String value1, String value2) {
            addCriterion("download_file between", value1, value2, "downloadFile");
            return (Criteria) this;
        }

        public Criteria andDownloadFileNotBetween(String value1, String value2) {
            addCriterion("download_file not between", value1, value2, "downloadFile");
            return (Criteria) this;
        }

        public Criteria andSyncStartTimeIsNull() {
            addCriterion("sync_start_time is null");
            return (Criteria) this;
        }

        public Criteria andSyncStartTimeIsNotNull() {
            addCriterion("sync_start_time is not null");
            return (Criteria) this;
        }

        public Criteria andSyncStartTimeEqualTo(Date value) {
            addCriterion("sync_start_time =", value, "syncStartTime");
            return (Criteria) this;
        }

        public Criteria andSyncStartTimeNotEqualTo(Date value) {
            addCriterion("sync_start_time <>", value, "syncStartTime");
            return (Criteria) this;
        }

        public Criteria andSyncStartTimeGreaterThan(Date value) {
            addCriterion("sync_start_time >", value, "syncStartTime");
            return (Criteria) this;
        }

        public Criteria andSyncStartTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("sync_start_time >=", value, "syncStartTime");
            return (Criteria) this;
        }

        public Criteria andSyncStartTimeLessThan(Date value) {
            addCriterion("sync_start_time <", value, "syncStartTime");
            return (Criteria) this;
        }

        public Criteria andSyncStartTimeLessThanOrEqualTo(Date value) {
            addCriterion("sync_start_time <=", value, "syncStartTime");
            return (Criteria) this;
        }

        public Criteria andSyncStartTimeIn(List<Date> values) {
            addCriterion("sync_start_time in", values, "syncStartTime");
            return (Criteria) this;
        }

        public Criteria andSyncStartTimeNotIn(List<Date> values) {
            addCriterion("sync_start_time not in", values, "syncStartTime");
            return (Criteria) this;
        }

        public Criteria andSyncStartTimeBetween(Date value1, Date value2) {
            addCriterion("sync_start_time between", value1, value2, "syncStartTime");
            return (Criteria) this;
        }

        public Criteria andSyncStartTimeNotBetween(Date value1, Date value2) {
            addCriterion("sync_start_time not between", value1, value2, "syncStartTime");
            return (Criteria) this;
        }

        public Criteria andSyncEndTimeIsNull() {
            addCriterion("sync_end_time is null");
            return (Criteria) this;
        }

        public Criteria andSyncEndTimeIsNotNull() {
            addCriterion("sync_end_time is not null");
            return (Criteria) this;
        }

        public Criteria andSyncEndTimeEqualTo(Date value) {
            addCriterion("sync_end_time =", value, "syncEndTime");
            return (Criteria) this;
        }

        public Criteria andSyncEndTimeNotEqualTo(Date value) {
            addCriterion("sync_end_time <>", value, "syncEndTime");
            return (Criteria) this;
        }

        public Criteria andSyncEndTimeGreaterThan(Date value) {
            addCriterion("sync_end_time >", value, "syncEndTime");
            return (Criteria) this;
        }

        public Criteria andSyncEndTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("sync_end_time >=", value, "syncEndTime");
            return (Criteria) this;
        }

        public Criteria andSyncEndTimeLessThan(Date value) {
            addCriterion("sync_end_time <", value, "syncEndTime");
            return (Criteria) this;
        }

        public Criteria andSyncEndTimeLessThanOrEqualTo(Date value) {
            addCriterion("sync_end_time <=", value, "syncEndTime");
            return (Criteria) this;
        }

        public Criteria andSyncEndTimeIn(List<Date> values) {
            addCriterion("sync_end_time in", values, "syncEndTime");
            return (Criteria) this;
        }

        public Criteria andSyncEndTimeNotIn(List<Date> values) {
            addCriterion("sync_end_time not in", values, "syncEndTime");
            return (Criteria) this;
        }

        public Criteria andSyncEndTimeBetween(Date value1, Date value2) {
            addCriterion("sync_end_time between", value1, value2, "syncEndTime");
            return (Criteria) this;
        }

        public Criteria andSyncEndTimeNotBetween(Date value1, Date value2) {
            addCriterion("sync_end_time not between", value1, value2, "syncEndTime");
            return (Criteria) this;
        }

        public Criteria andServerNameIsNull() {
            addCriterion("server_name is null");
            return (Criteria) this;
        }

        public Criteria andServerNameIsNotNull() {
            addCriterion("server_name is not null");
            return (Criteria) this;
        }

        public Criteria andServerNameEqualTo(String value) {
            addCriterion("server_name =", value, "serverName");
            return (Criteria) this;
        }

        public Criteria andServerNameNotEqualTo(String value) {
            addCriterion("server_name <>", value, "serverName");
            return (Criteria) this;
        }

        public Criteria andServerNameGreaterThan(String value) {
            addCriterion("server_name >", value, "serverName");
            return (Criteria) this;
        }

        public Criteria andServerNameGreaterThanOrEqualTo(String value) {
            addCriterion("server_name >=", value, "serverName");
            return (Criteria) this;
        }

        public Criteria andServerNameLessThan(String value) {
            addCriterion("server_name <", value, "serverName");
            return (Criteria) this;
        }

        public Criteria andServerNameLessThanOrEqualTo(String value) {
            addCriterion("server_name <=", value, "serverName");
            return (Criteria) this;
        }

        public Criteria andServerNameLike(String value) {
            addCriterion("server_name like", value, "serverName");
            return (Criteria) this;
        }

        public Criteria andServerNameNotLike(String value) {
            addCriterion("server_name not like", value, "serverName");
            return (Criteria) this;
        }

        public Criteria andServerNameIn(List<String> values) {
            addCriterion("server_name in", values, "serverName");
            return (Criteria) this;
        }

        public Criteria andServerNameNotIn(List<String> values) {
            addCriterion("server_name not in", values, "serverName");
            return (Criteria) this;
        }

        public Criteria andServerNameBetween(String value1, String value2) {
            addCriterion("server_name between", value1, value2, "serverName");
            return (Criteria) this;
        }

        public Criteria andServerNameNotBetween(String value1, String value2) {
            addCriterion("server_name not between", value1, value2, "serverName");
            return (Criteria) this;
        }

        public Criteria andServerIdIsNull() {
            addCriterion("server_id is null");
            return (Criteria) this;
        }

        public Criteria andServerIdIsNotNull() {
            addCriterion("server_id is not null");
            return (Criteria) this;
        }

        public Criteria andServerIdEqualTo(String value) {
            addCriterion("server_id =", value, "serverId");
            return (Criteria) this;
        }

        public Criteria andServerIdNotEqualTo(String value) {
            addCriterion("server_id <>", value, "serverId");
            return (Criteria) this;
        }

        public Criteria andServerIdGreaterThan(String value) {
            addCriterion("server_id >", value, "serverId");
            return (Criteria) this;
        }

        public Criteria andServerIdGreaterThanOrEqualTo(String value) {
            addCriterion("server_id >=", value, "serverId");
            return (Criteria) this;
        }

        public Criteria andServerIdLessThan(String value) {
            addCriterion("server_id <", value, "serverId");
            return (Criteria) this;
        }

        public Criteria andServerIdLessThanOrEqualTo(String value) {
            addCriterion("server_id <=", value, "serverId");
            return (Criteria) this;
        }

        public Criteria andServerIdLike(String value) {
            addCriterion("server_id like", value, "serverId");
            return (Criteria) this;
        }

        public Criteria andServerIdNotLike(String value) {
            addCriterion("server_id not like", value, "serverId");
            return (Criteria) this;
        }

        public Criteria andServerIdIn(List<String> values) {
            addCriterion("server_id in", values, "serverId");
            return (Criteria) this;
        }

        public Criteria andServerIdNotIn(List<String> values) {
            addCriterion("server_id not in", values, "serverId");
            return (Criteria) this;
        }

        public Criteria andServerIdBetween(String value1, String value2) {
            addCriterion("server_id between", value1, value2, "serverId");
            return (Criteria) this;
        }

        public Criteria andServerIdNotBetween(String value1, String value2) {
            addCriterion("server_id not between", value1, value2, "serverId");
            return (Criteria) this;
        }

        public Criteria andAddTimeIsNull() {
            addCriterion("add_time is null");
            return (Criteria) this;
        }

        public Criteria andAddTimeIsNotNull() {
            addCriterion("add_time is not null");
            return (Criteria) this;
        }

        public Criteria andAddTimeEqualTo(Date value) {
            addCriterion("add_time =", value, "addTime");
            return (Criteria) this;
        }

        public Criteria andAddTimeNotEqualTo(Date value) {
            addCriterion("add_time <>", value, "addTime");
            return (Criteria) this;
        }

        public Criteria andAddTimeGreaterThan(Date value) {
            addCriterion("add_time >", value, "addTime");
            return (Criteria) this;
        }

        public Criteria andAddTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("add_time >=", value, "addTime");
            return (Criteria) this;
        }

        public Criteria andAddTimeLessThan(Date value) {
            addCriterion("add_time <", value, "addTime");
            return (Criteria) this;
        }

        public Criteria andAddTimeLessThanOrEqualTo(Date value) {
            addCriterion("add_time <=", value, "addTime");
            return (Criteria) this;
        }

        public Criteria andAddTimeIn(List<Date> values) {
            addCriterion("add_time in", values, "addTime");
            return (Criteria) this;
        }

        public Criteria andAddTimeNotIn(List<Date> values) {
            addCriterion("add_time not in", values, "addTime");
            return (Criteria) this;
        }

        public Criteria andAddTimeBetween(Date value1, Date value2) {
            addCriterion("add_time between", value1, value2, "addTime");
            return (Criteria) this;
        }

        public Criteria andAddTimeNotBetween(Date value1, Date value2) {
            addCriterion("add_time not between", value1, value2, "addTime");
            return (Criteria) this;
        }

        public Criteria andUpdTimeIsNull() {
            addCriterion("upd_time is null");
            return (Criteria) this;
        }

        public Criteria andUpdTimeIsNotNull() {
            addCriterion("upd_time is not null");
            return (Criteria) this;
        }

        public Criteria andUpdTimeEqualTo(Date value) {
            addCriterion("upd_time =", value, "updTime");
            return (Criteria) this;
        }

        public Criteria andUpdTimeNotEqualTo(Date value) {
            addCriterion("upd_time <>", value, "updTime");
            return (Criteria) this;
        }

        public Criteria andUpdTimeGreaterThan(Date value) {
            addCriterion("upd_time >", value, "updTime");
            return (Criteria) this;
        }

        public Criteria andUpdTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("upd_time >=", value, "updTime");
            return (Criteria) this;
        }

        public Criteria andUpdTimeLessThan(Date value) {
            addCriterion("upd_time <", value, "updTime");
            return (Criteria) this;
        }

        public Criteria andUpdTimeLessThanOrEqualTo(Date value) {
            addCriterion("upd_time <=", value, "updTime");
            return (Criteria) this;
        }

        public Criteria andUpdTimeIn(List<Date> values) {
            addCriterion("upd_time in", values, "updTime");
            return (Criteria) this;
        }

        public Criteria andUpdTimeNotIn(List<Date> values) {
            addCriterion("upd_time not in", values, "updTime");
            return (Criteria) this;
        }

        public Criteria andUpdTimeBetween(Date value1, Date value2) {
            addCriterion("upd_time between", value1, value2, "updTime");
            return (Criteria) this;
        }

        public Criteria andUpdTimeNotBetween(Date value1, Date value2) {
            addCriterion("upd_time not between", value1, value2, "updTime");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}