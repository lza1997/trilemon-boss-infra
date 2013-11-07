package com.trilemon.boss360.infrastructure.trade.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TradeSyncExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public TradeSyncExample() {
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

        public Criteria andIdEqualTo(Long value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Long value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Long value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Long value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Long value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Long value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Long> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Long> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Long value1, Long value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Long value1, Long value2) {
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

        public Criteria andSyncTradeStartTimeIsNull() {
            addCriterion("sync_trade_start_time is null");
            return (Criteria) this;
        }

        public Criteria andSyncTradeStartTimeIsNotNull() {
            addCriterion("sync_trade_start_time is not null");
            return (Criteria) this;
        }

        public Criteria andSyncTradeStartTimeEqualTo(Date value) {
            addCriterion("sync_trade_start_time =", value, "syncTradeStartTime");
            return (Criteria) this;
        }

        public Criteria andSyncTradeStartTimeNotEqualTo(Date value) {
            addCriterion("sync_trade_start_time <>", value, "syncTradeStartTime");
            return (Criteria) this;
        }

        public Criteria andSyncTradeStartTimeGreaterThan(Date value) {
            addCriterion("sync_trade_start_time >", value, "syncTradeStartTime");
            return (Criteria) this;
        }

        public Criteria andSyncTradeStartTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("sync_trade_start_time >=", value, "syncTradeStartTime");
            return (Criteria) this;
        }

        public Criteria andSyncTradeStartTimeLessThan(Date value) {
            addCriterion("sync_trade_start_time <", value, "syncTradeStartTime");
            return (Criteria) this;
        }

        public Criteria andSyncTradeStartTimeLessThanOrEqualTo(Date value) {
            addCriterion("sync_trade_start_time <=", value, "syncTradeStartTime");
            return (Criteria) this;
        }

        public Criteria andSyncTradeStartTimeIn(List<Date> values) {
            addCriterion("sync_trade_start_time in", values, "syncTradeStartTime");
            return (Criteria) this;
        }

        public Criteria andSyncTradeStartTimeNotIn(List<Date> values) {
            addCriterion("sync_trade_start_time not in", values, "syncTradeStartTime");
            return (Criteria) this;
        }

        public Criteria andSyncTradeStartTimeBetween(Date value1, Date value2) {
            addCriterion("sync_trade_start_time between", value1, value2, "syncTradeStartTime");
            return (Criteria) this;
        }

        public Criteria andSyncTradeStartTimeNotBetween(Date value1, Date value2) {
            addCriterion("sync_trade_start_time not between", value1, value2, "syncTradeStartTime");
            return (Criteria) this;
        }

        public Criteria andSyncTradeEndTimeIsNull() {
            addCriterion("sync_trade_end_time is null");
            return (Criteria) this;
        }

        public Criteria andSyncTradeEndTimeIsNotNull() {
            addCriterion("sync_trade_end_time is not null");
            return (Criteria) this;
        }

        public Criteria andSyncTradeEndTimeEqualTo(Date value) {
            addCriterion("sync_trade_end_time =", value, "syncTradeEndTime");
            return (Criteria) this;
        }

        public Criteria andSyncTradeEndTimeNotEqualTo(Date value) {
            addCriterion("sync_trade_end_time <>", value, "syncTradeEndTime");
            return (Criteria) this;
        }

        public Criteria andSyncTradeEndTimeGreaterThan(Date value) {
            addCriterion("sync_trade_end_time >", value, "syncTradeEndTime");
            return (Criteria) this;
        }

        public Criteria andSyncTradeEndTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("sync_trade_end_time >=", value, "syncTradeEndTime");
            return (Criteria) this;
        }

        public Criteria andSyncTradeEndTimeLessThan(Date value) {
            addCriterion("sync_trade_end_time <", value, "syncTradeEndTime");
            return (Criteria) this;
        }

        public Criteria andSyncTradeEndTimeLessThanOrEqualTo(Date value) {
            addCriterion("sync_trade_end_time <=", value, "syncTradeEndTime");
            return (Criteria) this;
        }

        public Criteria andSyncTradeEndTimeIn(List<Date> values) {
            addCriterion("sync_trade_end_time in", values, "syncTradeEndTime");
            return (Criteria) this;
        }

        public Criteria andSyncTradeEndTimeNotIn(List<Date> values) {
            addCriterion("sync_trade_end_time not in", values, "syncTradeEndTime");
            return (Criteria) this;
        }

        public Criteria andSyncTradeEndTimeBetween(Date value1, Date value2) {
            addCriterion("sync_trade_end_time between", value1, value2, "syncTradeEndTime");
            return (Criteria) this;
        }

        public Criteria andSyncTradeEndTimeNotBetween(Date value1, Date value2) {
            addCriterion("sync_trade_end_time not between", value1, value2, "syncTradeEndTime");
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

        public Criteria andSyncLockIsNull() {
            addCriterion("sync_lock is null");
            return (Criteria) this;
        }

        public Criteria andSyncLockIsNotNull() {
            addCriterion("sync_lock is not null");
            return (Criteria) this;
        }

        public Criteria andSyncLockEqualTo(Byte value) {
            addCriterion("sync_lock =", value, "syncLock");
            return (Criteria) this;
        }

        public Criteria andSyncLockNotEqualTo(Byte value) {
            addCriterion("sync_lock <>", value, "syncLock");
            return (Criteria) this;
        }

        public Criteria andSyncLockGreaterThan(Byte value) {
            addCriterion("sync_lock >", value, "syncLock");
            return (Criteria) this;
        }

        public Criteria andSyncLockGreaterThanOrEqualTo(Byte value) {
            addCriterion("sync_lock >=", value, "syncLock");
            return (Criteria) this;
        }

        public Criteria andSyncLockLessThan(Byte value) {
            addCriterion("sync_lock <", value, "syncLock");
            return (Criteria) this;
        }

        public Criteria andSyncLockLessThanOrEqualTo(Byte value) {
            addCriterion("sync_lock <=", value, "syncLock");
            return (Criteria) this;
        }

        public Criteria andSyncLockIn(List<Byte> values) {
            addCriterion("sync_lock in", values, "syncLock");
            return (Criteria) this;
        }

        public Criteria andSyncLockNotIn(List<Byte> values) {
            addCriterion("sync_lock not in", values, "syncLock");
            return (Criteria) this;
        }

        public Criteria andSyncLockBetween(Byte value1, Byte value2) {
            addCriterion("sync_lock between", value1, value2, "syncLock");
            return (Criteria) this;
        }

        public Criteria andSyncLockNotBetween(Byte value1, Byte value2) {
            addCriterion("sync_lock not between", value1, value2, "syncLock");
            return (Criteria) this;
        }

        public Criteria andSyncServiceNameIsNull() {
            addCriterion("sync_service_name is null");
            return (Criteria) this;
        }

        public Criteria andSyncServiceNameIsNotNull() {
            addCriterion("sync_service_name is not null");
            return (Criteria) this;
        }

        public Criteria andSyncServiceNameEqualTo(String value) {
            addCriterion("sync_service_name =", value, "syncServiceName");
            return (Criteria) this;
        }

        public Criteria andSyncServiceNameNotEqualTo(String value) {
            addCriterion("sync_service_name <>", value, "syncServiceName");
            return (Criteria) this;
        }

        public Criteria andSyncServiceNameGreaterThan(String value) {
            addCriterion("sync_service_name >", value, "syncServiceName");
            return (Criteria) this;
        }

        public Criteria andSyncServiceNameGreaterThanOrEqualTo(String value) {
            addCriterion("sync_service_name >=", value, "syncServiceName");
            return (Criteria) this;
        }

        public Criteria andSyncServiceNameLessThan(String value) {
            addCriterion("sync_service_name <", value, "syncServiceName");
            return (Criteria) this;
        }

        public Criteria andSyncServiceNameLessThanOrEqualTo(String value) {
            addCriterion("sync_service_name <=", value, "syncServiceName");
            return (Criteria) this;
        }

        public Criteria andSyncServiceNameLike(String value) {
            addCriterion("sync_service_name like", value, "syncServiceName");
            return (Criteria) this;
        }

        public Criteria andSyncServiceNameNotLike(String value) {
            addCriterion("sync_service_name not like", value, "syncServiceName");
            return (Criteria) this;
        }

        public Criteria andSyncServiceNameIn(List<String> values) {
            addCriterion("sync_service_name in", values, "syncServiceName");
            return (Criteria) this;
        }

        public Criteria andSyncServiceNameNotIn(List<String> values) {
            addCriterion("sync_service_name not in", values, "syncServiceName");
            return (Criteria) this;
        }

        public Criteria andSyncServiceNameBetween(String value1, String value2) {
            addCriterion("sync_service_name between", value1, value2, "syncServiceName");
            return (Criteria) this;
        }

        public Criteria andSyncServiceNameNotBetween(String value1, String value2) {
            addCriterion("sync_service_name not between", value1, value2, "syncServiceName");
            return (Criteria) this;
        }

        public Criteria andSyncServiceIdIsNull() {
            addCriterion("sync_service_id is null");
            return (Criteria) this;
        }

        public Criteria andSyncServiceIdIsNotNull() {
            addCriterion("sync_service_id is not null");
            return (Criteria) this;
        }

        public Criteria andSyncServiceIdEqualTo(String value) {
            addCriterion("sync_service_id =", value, "syncServiceId");
            return (Criteria) this;
        }

        public Criteria andSyncServiceIdNotEqualTo(String value) {
            addCriterion("sync_service_id <>", value, "syncServiceId");
            return (Criteria) this;
        }

        public Criteria andSyncServiceIdGreaterThan(String value) {
            addCriterion("sync_service_id >", value, "syncServiceId");
            return (Criteria) this;
        }

        public Criteria andSyncServiceIdGreaterThanOrEqualTo(String value) {
            addCriterion("sync_service_id >=", value, "syncServiceId");
            return (Criteria) this;
        }

        public Criteria andSyncServiceIdLessThan(String value) {
            addCriterion("sync_service_id <", value, "syncServiceId");
            return (Criteria) this;
        }

        public Criteria andSyncServiceIdLessThanOrEqualTo(String value) {
            addCriterion("sync_service_id <=", value, "syncServiceId");
            return (Criteria) this;
        }

        public Criteria andSyncServiceIdLike(String value) {
            addCriterion("sync_service_id like", value, "syncServiceId");
            return (Criteria) this;
        }

        public Criteria andSyncServiceIdNotLike(String value) {
            addCriterion("sync_service_id not like", value, "syncServiceId");
            return (Criteria) this;
        }

        public Criteria andSyncServiceIdIn(List<String> values) {
            addCriterion("sync_service_id in", values, "syncServiceId");
            return (Criteria) this;
        }

        public Criteria andSyncServiceIdNotIn(List<String> values) {
            addCriterion("sync_service_id not in", values, "syncServiceId");
            return (Criteria) this;
        }

        public Criteria andSyncServiceIdBetween(String value1, String value2) {
            addCriterion("sync_service_id between", value1, value2, "syncServiceId");
            return (Criteria) this;
        }

        public Criteria andSyncServiceIdNotBetween(String value1, String value2) {
            addCriterion("sync_service_id not between", value1, value2, "syncServiceId");
            return (Criteria) this;
        }

        public Criteria andCheckTradeStartTimeIsNull() {
            addCriterion("check_trade_start_time is null");
            return (Criteria) this;
        }

        public Criteria andCheckTradeStartTimeIsNotNull() {
            addCriterion("check_trade_start_time is not null");
            return (Criteria) this;
        }

        public Criteria andCheckTradeStartTimeEqualTo(Date value) {
            addCriterion("check_trade_start_time =", value, "checkTradeStartTime");
            return (Criteria) this;
        }

        public Criteria andCheckTradeStartTimeNotEqualTo(Date value) {
            addCriterion("check_trade_start_time <>", value, "checkTradeStartTime");
            return (Criteria) this;
        }

        public Criteria andCheckTradeStartTimeGreaterThan(Date value) {
            addCriterion("check_trade_start_time >", value, "checkTradeStartTime");
            return (Criteria) this;
        }

        public Criteria andCheckTradeStartTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("check_trade_start_time >=", value, "checkTradeStartTime");
            return (Criteria) this;
        }

        public Criteria andCheckTradeStartTimeLessThan(Date value) {
            addCriterion("check_trade_start_time <", value, "checkTradeStartTime");
            return (Criteria) this;
        }

        public Criteria andCheckTradeStartTimeLessThanOrEqualTo(Date value) {
            addCriterion("check_trade_start_time <=", value, "checkTradeStartTime");
            return (Criteria) this;
        }

        public Criteria andCheckTradeStartTimeIn(List<Date> values) {
            addCriterion("check_trade_start_time in", values, "checkTradeStartTime");
            return (Criteria) this;
        }

        public Criteria andCheckTradeStartTimeNotIn(List<Date> values) {
            addCriterion("check_trade_start_time not in", values, "checkTradeStartTime");
            return (Criteria) this;
        }

        public Criteria andCheckTradeStartTimeBetween(Date value1, Date value2) {
            addCriterion("check_trade_start_time between", value1, value2, "checkTradeStartTime");
            return (Criteria) this;
        }

        public Criteria andCheckTradeStartTimeNotBetween(Date value1, Date value2) {
            addCriterion("check_trade_start_time not between", value1, value2, "checkTradeStartTime");
            return (Criteria) this;
        }

        public Criteria andCheckTradeEndTimeIsNull() {
            addCriterion("check_trade_end_time is null");
            return (Criteria) this;
        }

        public Criteria andCheckTradeEndTimeIsNotNull() {
            addCriterion("check_trade_end_time is not null");
            return (Criteria) this;
        }

        public Criteria andCheckTradeEndTimeEqualTo(Date value) {
            addCriterion("check_trade_end_time =", value, "checkTradeEndTime");
            return (Criteria) this;
        }

        public Criteria andCheckTradeEndTimeNotEqualTo(Date value) {
            addCriterion("check_trade_end_time <>", value, "checkTradeEndTime");
            return (Criteria) this;
        }

        public Criteria andCheckTradeEndTimeGreaterThan(Date value) {
            addCriterion("check_trade_end_time >", value, "checkTradeEndTime");
            return (Criteria) this;
        }

        public Criteria andCheckTradeEndTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("check_trade_end_time >=", value, "checkTradeEndTime");
            return (Criteria) this;
        }

        public Criteria andCheckTradeEndTimeLessThan(Date value) {
            addCriterion("check_trade_end_time <", value, "checkTradeEndTime");
            return (Criteria) this;
        }

        public Criteria andCheckTradeEndTimeLessThanOrEqualTo(Date value) {
            addCriterion("check_trade_end_time <=", value, "checkTradeEndTime");
            return (Criteria) this;
        }

        public Criteria andCheckTradeEndTimeIn(List<Date> values) {
            addCriterion("check_trade_end_time in", values, "checkTradeEndTime");
            return (Criteria) this;
        }

        public Criteria andCheckTradeEndTimeNotIn(List<Date> values) {
            addCriterion("check_trade_end_time not in", values, "checkTradeEndTime");
            return (Criteria) this;
        }

        public Criteria andCheckTradeEndTimeBetween(Date value1, Date value2) {
            addCriterion("check_trade_end_time between", value1, value2, "checkTradeEndTime");
            return (Criteria) this;
        }

        public Criteria andCheckTradeEndTimeNotBetween(Date value1, Date value2) {
            addCriterion("check_trade_end_time not between", value1, value2, "checkTradeEndTime");
            return (Criteria) this;
        }

        public Criteria andCheckStartTimeIsNull() {
            addCriterion("check_start_time is null");
            return (Criteria) this;
        }

        public Criteria andCheckStartTimeIsNotNull() {
            addCriterion("check_start_time is not null");
            return (Criteria) this;
        }

        public Criteria andCheckStartTimeEqualTo(Date value) {
            addCriterion("check_start_time =", value, "checkStartTime");
            return (Criteria) this;
        }

        public Criteria andCheckStartTimeNotEqualTo(Date value) {
            addCriterion("check_start_time <>", value, "checkStartTime");
            return (Criteria) this;
        }

        public Criteria andCheckStartTimeGreaterThan(Date value) {
            addCriterion("check_start_time >", value, "checkStartTime");
            return (Criteria) this;
        }

        public Criteria andCheckStartTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("check_start_time >=", value, "checkStartTime");
            return (Criteria) this;
        }

        public Criteria andCheckStartTimeLessThan(Date value) {
            addCriterion("check_start_time <", value, "checkStartTime");
            return (Criteria) this;
        }

        public Criteria andCheckStartTimeLessThanOrEqualTo(Date value) {
            addCriterion("check_start_time <=", value, "checkStartTime");
            return (Criteria) this;
        }

        public Criteria andCheckStartTimeIn(List<Date> values) {
            addCriterion("check_start_time in", values, "checkStartTime");
            return (Criteria) this;
        }

        public Criteria andCheckStartTimeNotIn(List<Date> values) {
            addCriterion("check_start_time not in", values, "checkStartTime");
            return (Criteria) this;
        }

        public Criteria andCheckStartTimeBetween(Date value1, Date value2) {
            addCriterion("check_start_time between", value1, value2, "checkStartTime");
            return (Criteria) this;
        }

        public Criteria andCheckStartTimeNotBetween(Date value1, Date value2) {
            addCriterion("check_start_time not between", value1, value2, "checkStartTime");
            return (Criteria) this;
        }

        public Criteria andCheckEndTimeIsNull() {
            addCriterion("check_end_time is null");
            return (Criteria) this;
        }

        public Criteria andCheckEndTimeIsNotNull() {
            addCriterion("check_end_time is not null");
            return (Criteria) this;
        }

        public Criteria andCheckEndTimeEqualTo(Date value) {
            addCriterion("check_end_time =", value, "checkEndTime");
            return (Criteria) this;
        }

        public Criteria andCheckEndTimeNotEqualTo(Date value) {
            addCriterion("check_end_time <>", value, "checkEndTime");
            return (Criteria) this;
        }

        public Criteria andCheckEndTimeGreaterThan(Date value) {
            addCriterion("check_end_time >", value, "checkEndTime");
            return (Criteria) this;
        }

        public Criteria andCheckEndTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("check_end_time >=", value, "checkEndTime");
            return (Criteria) this;
        }

        public Criteria andCheckEndTimeLessThan(Date value) {
            addCriterion("check_end_time <", value, "checkEndTime");
            return (Criteria) this;
        }

        public Criteria andCheckEndTimeLessThanOrEqualTo(Date value) {
            addCriterion("check_end_time <=", value, "checkEndTime");
            return (Criteria) this;
        }

        public Criteria andCheckEndTimeIn(List<Date> values) {
            addCriterion("check_end_time in", values, "checkEndTime");
            return (Criteria) this;
        }

        public Criteria andCheckEndTimeNotIn(List<Date> values) {
            addCriterion("check_end_time not in", values, "checkEndTime");
            return (Criteria) this;
        }

        public Criteria andCheckEndTimeBetween(Date value1, Date value2) {
            addCriterion("check_end_time between", value1, value2, "checkEndTime");
            return (Criteria) this;
        }

        public Criteria andCheckEndTimeNotBetween(Date value1, Date value2) {
            addCriterion("check_end_time not between", value1, value2, "checkEndTime");
            return (Criteria) this;
        }

        public Criteria andCheckStatusIsNull() {
            addCriterion("check_status is null");
            return (Criteria) this;
        }

        public Criteria andCheckStatusIsNotNull() {
            addCriterion("check_status is not null");
            return (Criteria) this;
        }

        public Criteria andCheckStatusEqualTo(Byte value) {
            addCriterion("check_status =", value, "checkStatus");
            return (Criteria) this;
        }

        public Criteria andCheckStatusNotEqualTo(Byte value) {
            addCriterion("check_status <>", value, "checkStatus");
            return (Criteria) this;
        }

        public Criteria andCheckStatusGreaterThan(Byte value) {
            addCriterion("check_status >", value, "checkStatus");
            return (Criteria) this;
        }

        public Criteria andCheckStatusGreaterThanOrEqualTo(Byte value) {
            addCriterion("check_status >=", value, "checkStatus");
            return (Criteria) this;
        }

        public Criteria andCheckStatusLessThan(Byte value) {
            addCriterion("check_status <", value, "checkStatus");
            return (Criteria) this;
        }

        public Criteria andCheckStatusLessThanOrEqualTo(Byte value) {
            addCriterion("check_status <=", value, "checkStatus");
            return (Criteria) this;
        }

        public Criteria andCheckStatusIn(List<Byte> values) {
            addCriterion("check_status in", values, "checkStatus");
            return (Criteria) this;
        }

        public Criteria andCheckStatusNotIn(List<Byte> values) {
            addCriterion("check_status not in", values, "checkStatus");
            return (Criteria) this;
        }

        public Criteria andCheckStatusBetween(Byte value1, Byte value2) {
            addCriterion("check_status between", value1, value2, "checkStatus");
            return (Criteria) this;
        }

        public Criteria andCheckStatusNotBetween(Byte value1, Byte value2) {
            addCriterion("check_status not between", value1, value2, "checkStatus");
            return (Criteria) this;
        }

        public Criteria andCheckLockIsNull() {
            addCriterion("check_lock is null");
            return (Criteria) this;
        }

        public Criteria andCheckLockIsNotNull() {
            addCriterion("check_lock is not null");
            return (Criteria) this;
        }

        public Criteria andCheckLockEqualTo(Byte value) {
            addCriterion("check_lock =", value, "checkLock");
            return (Criteria) this;
        }

        public Criteria andCheckLockNotEqualTo(Byte value) {
            addCriterion("check_lock <>", value, "checkLock");
            return (Criteria) this;
        }

        public Criteria andCheckLockGreaterThan(Byte value) {
            addCriterion("check_lock >", value, "checkLock");
            return (Criteria) this;
        }

        public Criteria andCheckLockGreaterThanOrEqualTo(Byte value) {
            addCriterion("check_lock >=", value, "checkLock");
            return (Criteria) this;
        }

        public Criteria andCheckLockLessThan(Byte value) {
            addCriterion("check_lock <", value, "checkLock");
            return (Criteria) this;
        }

        public Criteria andCheckLockLessThanOrEqualTo(Byte value) {
            addCriterion("check_lock <=", value, "checkLock");
            return (Criteria) this;
        }

        public Criteria andCheckLockIn(List<Byte> values) {
            addCriterion("check_lock in", values, "checkLock");
            return (Criteria) this;
        }

        public Criteria andCheckLockNotIn(List<Byte> values) {
            addCriterion("check_lock not in", values, "checkLock");
            return (Criteria) this;
        }

        public Criteria andCheckLockBetween(Byte value1, Byte value2) {
            addCriterion("check_lock between", value1, value2, "checkLock");
            return (Criteria) this;
        }

        public Criteria andCheckLockNotBetween(Byte value1, Byte value2) {
            addCriterion("check_lock not between", value1, value2, "checkLock");
            return (Criteria) this;
        }

        public Criteria andCheckServiceNameIsNull() {
            addCriterion("check_service_name is null");
            return (Criteria) this;
        }

        public Criteria andCheckServiceNameIsNotNull() {
            addCriterion("check_service_name is not null");
            return (Criteria) this;
        }

        public Criteria andCheckServiceNameEqualTo(String value) {
            addCriterion("check_service_name =", value, "checkServiceName");
            return (Criteria) this;
        }

        public Criteria andCheckServiceNameNotEqualTo(String value) {
            addCriterion("check_service_name <>", value, "checkServiceName");
            return (Criteria) this;
        }

        public Criteria andCheckServiceNameGreaterThan(String value) {
            addCriterion("check_service_name >", value, "checkServiceName");
            return (Criteria) this;
        }

        public Criteria andCheckServiceNameGreaterThanOrEqualTo(String value) {
            addCriterion("check_service_name >=", value, "checkServiceName");
            return (Criteria) this;
        }

        public Criteria andCheckServiceNameLessThan(String value) {
            addCriterion("check_service_name <", value, "checkServiceName");
            return (Criteria) this;
        }

        public Criteria andCheckServiceNameLessThanOrEqualTo(String value) {
            addCriterion("check_service_name <=", value, "checkServiceName");
            return (Criteria) this;
        }

        public Criteria andCheckServiceNameLike(String value) {
            addCriterion("check_service_name like", value, "checkServiceName");
            return (Criteria) this;
        }

        public Criteria andCheckServiceNameNotLike(String value) {
            addCriterion("check_service_name not like", value, "checkServiceName");
            return (Criteria) this;
        }

        public Criteria andCheckServiceNameIn(List<String> values) {
            addCriterion("check_service_name in", values, "checkServiceName");
            return (Criteria) this;
        }

        public Criteria andCheckServiceNameNotIn(List<String> values) {
            addCriterion("check_service_name not in", values, "checkServiceName");
            return (Criteria) this;
        }

        public Criteria andCheckServiceNameBetween(String value1, String value2) {
            addCriterion("check_service_name between", value1, value2, "checkServiceName");
            return (Criteria) this;
        }

        public Criteria andCheckServiceNameNotBetween(String value1, String value2) {
            addCriterion("check_service_name not between", value1, value2, "checkServiceName");
            return (Criteria) this;
        }

        public Criteria andCheckServiceIdIsNull() {
            addCriterion("check_service_id is null");
            return (Criteria) this;
        }

        public Criteria andCheckServiceIdIsNotNull() {
            addCriterion("check_service_id is not null");
            return (Criteria) this;
        }

        public Criteria andCheckServiceIdEqualTo(String value) {
            addCriterion("check_service_id =", value, "checkServiceId");
            return (Criteria) this;
        }

        public Criteria andCheckServiceIdNotEqualTo(String value) {
            addCriterion("check_service_id <>", value, "checkServiceId");
            return (Criteria) this;
        }

        public Criteria andCheckServiceIdGreaterThan(String value) {
            addCriterion("check_service_id >", value, "checkServiceId");
            return (Criteria) this;
        }

        public Criteria andCheckServiceIdGreaterThanOrEqualTo(String value) {
            addCriterion("check_service_id >=", value, "checkServiceId");
            return (Criteria) this;
        }

        public Criteria andCheckServiceIdLessThan(String value) {
            addCriterion("check_service_id <", value, "checkServiceId");
            return (Criteria) this;
        }

        public Criteria andCheckServiceIdLessThanOrEqualTo(String value) {
            addCriterion("check_service_id <=", value, "checkServiceId");
            return (Criteria) this;
        }

        public Criteria andCheckServiceIdLike(String value) {
            addCriterion("check_service_id like", value, "checkServiceId");
            return (Criteria) this;
        }

        public Criteria andCheckServiceIdNotLike(String value) {
            addCriterion("check_service_id not like", value, "checkServiceId");
            return (Criteria) this;
        }

        public Criteria andCheckServiceIdIn(List<String> values) {
            addCriterion("check_service_id in", values, "checkServiceId");
            return (Criteria) this;
        }

        public Criteria andCheckServiceIdNotIn(List<String> values) {
            addCriterion("check_service_id not in", values, "checkServiceId");
            return (Criteria) this;
        }

        public Criteria andCheckServiceIdBetween(String value1, String value2) {
            addCriterion("check_service_id between", value1, value2, "checkServiceId");
            return (Criteria) this;
        }

        public Criteria andCheckServiceIdNotBetween(String value1, String value2) {
            addCriterion("check_service_id not between", value1, value2, "checkServiceId");
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