package com.trilemon.boss.infra.base.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TaobaoApiUsageExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public TaobaoApiUsageExample() {
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

        public Criteria andServiceNameIsNull() {
            addCriterion("service_name is null");
            return (Criteria) this;
        }

        public Criteria andServiceNameIsNotNull() {
            addCriterion("service_name is not null");
            return (Criteria) this;
        }

        public Criteria andServiceNameEqualTo(String value) {
            addCriterion("service_name =", value, "serviceName");
            return (Criteria) this;
        }

        public Criteria andServiceNameNotEqualTo(String value) {
            addCriterion("service_name <>", value, "serviceName");
            return (Criteria) this;
        }

        public Criteria andServiceNameGreaterThan(String value) {
            addCriterion("service_name >", value, "serviceName");
            return (Criteria) this;
        }

        public Criteria andServiceNameGreaterThanOrEqualTo(String value) {
            addCriterion("service_name >=", value, "serviceName");
            return (Criteria) this;
        }

        public Criteria andServiceNameLessThan(String value) {
            addCriterion("service_name <", value, "serviceName");
            return (Criteria) this;
        }

        public Criteria andServiceNameLessThanOrEqualTo(String value) {
            addCriterion("service_name <=", value, "serviceName");
            return (Criteria) this;
        }

        public Criteria andServiceNameLike(String value) {
            addCriterion("service_name like", value, "serviceName");
            return (Criteria) this;
        }

        public Criteria andServiceNameNotLike(String value) {
            addCriterion("service_name not like", value, "serviceName");
            return (Criteria) this;
        }

        public Criteria andServiceNameIn(List<String> values) {
            addCriterion("service_name in", values, "serviceName");
            return (Criteria) this;
        }

        public Criteria andServiceNameNotIn(List<String> values) {
            addCriterion("service_name not in", values, "serviceName");
            return (Criteria) this;
        }

        public Criteria andServiceNameBetween(String value1, String value2) {
            addCriterion("service_name between", value1, value2, "serviceName");
            return (Criteria) this;
        }

        public Criteria andServiceNameNotBetween(String value1, String value2) {
            addCriterion("service_name not between", value1, value2, "serviceName");
            return (Criteria) this;
        }

        public Criteria andServiceIdIsNull() {
            addCriterion("service_id is null");
            return (Criteria) this;
        }

        public Criteria andServiceIdIsNotNull() {
            addCriterion("service_id is not null");
            return (Criteria) this;
        }

        public Criteria andServiceIdEqualTo(String value) {
            addCriterion("service_id =", value, "serviceId");
            return (Criteria) this;
        }

        public Criteria andServiceIdNotEqualTo(String value) {
            addCriterion("service_id <>", value, "serviceId");
            return (Criteria) this;
        }

        public Criteria andServiceIdGreaterThan(String value) {
            addCriterion("service_id >", value, "serviceId");
            return (Criteria) this;
        }

        public Criteria andServiceIdGreaterThanOrEqualTo(String value) {
            addCriterion("service_id >=", value, "serviceId");
            return (Criteria) this;
        }

        public Criteria andServiceIdLessThan(String value) {
            addCriterion("service_id <", value, "serviceId");
            return (Criteria) this;
        }

        public Criteria andServiceIdLessThanOrEqualTo(String value) {
            addCriterion("service_id <=", value, "serviceId");
            return (Criteria) this;
        }

        public Criteria andServiceIdLike(String value) {
            addCriterion("service_id like", value, "serviceId");
            return (Criteria) this;
        }

        public Criteria andServiceIdNotLike(String value) {
            addCriterion("service_id not like", value, "serviceId");
            return (Criteria) this;
        }

        public Criteria andServiceIdIn(List<String> values) {
            addCriterion("service_id in", values, "serviceId");
            return (Criteria) this;
        }

        public Criteria andServiceIdNotIn(List<String> values) {
            addCriterion("service_id not in", values, "serviceId");
            return (Criteria) this;
        }

        public Criteria andServiceIdBetween(String value1, String value2) {
            addCriterion("service_id between", value1, value2, "serviceId");
            return (Criteria) this;
        }

        public Criteria andServiceIdNotBetween(String value1, String value2) {
            addCriterion("service_id not between", value1, value2, "serviceId");
            return (Criteria) this;
        }

        public Criteria andTaobaoAppKeyIsNull() {
            addCriterion("taobao_app_key is null");
            return (Criteria) this;
        }

        public Criteria andTaobaoAppKeyIsNotNull() {
            addCriterion("taobao_app_key is not null");
            return (Criteria) this;
        }

        public Criteria andTaobaoAppKeyEqualTo(String value) {
            addCriterion("taobao_app_key =", value, "taobaoAppKey");
            return (Criteria) this;
        }

        public Criteria andTaobaoAppKeyNotEqualTo(String value) {
            addCriterion("taobao_app_key <>", value, "taobaoAppKey");
            return (Criteria) this;
        }

        public Criteria andTaobaoAppKeyGreaterThan(String value) {
            addCriterion("taobao_app_key >", value, "taobaoAppKey");
            return (Criteria) this;
        }

        public Criteria andTaobaoAppKeyGreaterThanOrEqualTo(String value) {
            addCriterion("taobao_app_key >=", value, "taobaoAppKey");
            return (Criteria) this;
        }

        public Criteria andTaobaoAppKeyLessThan(String value) {
            addCriterion("taobao_app_key <", value, "taobaoAppKey");
            return (Criteria) this;
        }

        public Criteria andTaobaoAppKeyLessThanOrEqualTo(String value) {
            addCriterion("taobao_app_key <=", value, "taobaoAppKey");
            return (Criteria) this;
        }

        public Criteria andTaobaoAppKeyLike(String value) {
            addCriterion("taobao_app_key like", value, "taobaoAppKey");
            return (Criteria) this;
        }

        public Criteria andTaobaoAppKeyNotLike(String value) {
            addCriterion("taobao_app_key not like", value, "taobaoAppKey");
            return (Criteria) this;
        }

        public Criteria andTaobaoAppKeyIn(List<String> values) {
            addCriterion("taobao_app_key in", values, "taobaoAppKey");
            return (Criteria) this;
        }

        public Criteria andTaobaoAppKeyNotIn(List<String> values) {
            addCriterion("taobao_app_key not in", values, "taobaoAppKey");
            return (Criteria) this;
        }

        public Criteria andTaobaoAppKeyBetween(String value1, String value2) {
            addCriterion("taobao_app_key between", value1, value2, "taobaoAppKey");
            return (Criteria) this;
        }

        public Criteria andTaobaoAppKeyNotBetween(String value1, String value2) {
            addCriterion("taobao_app_key not between", value1, value2, "taobaoAppKey");
            return (Criteria) this;
        }

        public Criteria andApiNameIsNull() {
            addCriterion("api_name is null");
            return (Criteria) this;
        }

        public Criteria andApiNameIsNotNull() {
            addCriterion("api_name is not null");
            return (Criteria) this;
        }

        public Criteria andApiNameEqualTo(String value) {
            addCriterion("api_name =", value, "apiName");
            return (Criteria) this;
        }

        public Criteria andApiNameNotEqualTo(String value) {
            addCriterion("api_name <>", value, "apiName");
            return (Criteria) this;
        }

        public Criteria andApiNameGreaterThan(String value) {
            addCriterion("api_name >", value, "apiName");
            return (Criteria) this;
        }

        public Criteria andApiNameGreaterThanOrEqualTo(String value) {
            addCriterion("api_name >=", value, "apiName");
            return (Criteria) this;
        }

        public Criteria andApiNameLessThan(String value) {
            addCriterion("api_name <", value, "apiName");
            return (Criteria) this;
        }

        public Criteria andApiNameLessThanOrEqualTo(String value) {
            addCriterion("api_name <=", value, "apiName");
            return (Criteria) this;
        }

        public Criteria andApiNameLike(String value) {
            addCriterion("api_name like", value, "apiName");
            return (Criteria) this;
        }

        public Criteria andApiNameNotLike(String value) {
            addCriterion("api_name not like", value, "apiName");
            return (Criteria) this;
        }

        public Criteria andApiNameIn(List<String> values) {
            addCriterion("api_name in", values, "apiName");
            return (Criteria) this;
        }

        public Criteria andApiNameNotIn(List<String> values) {
            addCriterion("api_name not in", values, "apiName");
            return (Criteria) this;
        }

        public Criteria andApiNameBetween(String value1, String value2) {
            addCriterion("api_name between", value1, value2, "apiName");
            return (Criteria) this;
        }

        public Criteria andApiNameNotBetween(String value1, String value2) {
            addCriterion("api_name not between", value1, value2, "apiName");
            return (Criteria) this;
        }

        public Criteria andSuccessfulCallIsNull() {
            addCriterion("successful_call is null");
            return (Criteria) this;
        }

        public Criteria andSuccessfulCallIsNotNull() {
            addCriterion("successful_call is not null");
            return (Criteria) this;
        }

        public Criteria andSuccessfulCallEqualTo(Long value) {
            addCriterion("successful_call =", value, "successfulCall");
            return (Criteria) this;
        }

        public Criteria andSuccessfulCallNotEqualTo(Long value) {
            addCriterion("successful_call <>", value, "successfulCall");
            return (Criteria) this;
        }

        public Criteria andSuccessfulCallGreaterThan(Long value) {
            addCriterion("successful_call >", value, "successfulCall");
            return (Criteria) this;
        }

        public Criteria andSuccessfulCallGreaterThanOrEqualTo(Long value) {
            addCriterion("successful_call >=", value, "successfulCall");
            return (Criteria) this;
        }

        public Criteria andSuccessfulCallLessThan(Long value) {
            addCriterion("successful_call <", value, "successfulCall");
            return (Criteria) this;
        }

        public Criteria andSuccessfulCallLessThanOrEqualTo(Long value) {
            addCriterion("successful_call <=", value, "successfulCall");
            return (Criteria) this;
        }

        public Criteria andSuccessfulCallIn(List<Long> values) {
            addCriterion("successful_call in", values, "successfulCall");
            return (Criteria) this;
        }

        public Criteria andSuccessfulCallNotIn(List<Long> values) {
            addCriterion("successful_call not in", values, "successfulCall");
            return (Criteria) this;
        }

        public Criteria andSuccessfulCallBetween(Long value1, Long value2) {
            addCriterion("successful_call between", value1, value2, "successfulCall");
            return (Criteria) this;
        }

        public Criteria andSuccessfulCallNotBetween(Long value1, Long value2) {
            addCriterion("successful_call not between", value1, value2, "successfulCall");
            return (Criteria) this;
        }

        public Criteria andFailedCallIsNull() {
            addCriterion("failed_call is null");
            return (Criteria) this;
        }

        public Criteria andFailedCallIsNotNull() {
            addCriterion("failed_call is not null");
            return (Criteria) this;
        }

        public Criteria andFailedCallEqualTo(Long value) {
            addCriterion("failed_call =", value, "failedCall");
            return (Criteria) this;
        }

        public Criteria andFailedCallNotEqualTo(Long value) {
            addCriterion("failed_call <>", value, "failedCall");
            return (Criteria) this;
        }

        public Criteria andFailedCallGreaterThan(Long value) {
            addCriterion("failed_call >", value, "failedCall");
            return (Criteria) this;
        }

        public Criteria andFailedCallGreaterThanOrEqualTo(Long value) {
            addCriterion("failed_call >=", value, "failedCall");
            return (Criteria) this;
        }

        public Criteria andFailedCallLessThan(Long value) {
            addCriterion("failed_call <", value, "failedCall");
            return (Criteria) this;
        }

        public Criteria andFailedCallLessThanOrEqualTo(Long value) {
            addCriterion("failed_call <=", value, "failedCall");
            return (Criteria) this;
        }

        public Criteria andFailedCallIn(List<Long> values) {
            addCriterion("failed_call in", values, "failedCall");
            return (Criteria) this;
        }

        public Criteria andFailedCallNotIn(List<Long> values) {
            addCriterion("failed_call not in", values, "failedCall");
            return (Criteria) this;
        }

        public Criteria andFailedCallBetween(Long value1, Long value2) {
            addCriterion("failed_call between", value1, value2, "failedCall");
            return (Criteria) this;
        }

        public Criteria andFailedCallNotBetween(Long value1, Long value2) {
            addCriterion("failed_call not between", value1, value2, "failedCall");
            return (Criteria) this;
        }

        public Criteria andAvgExecTimeIsNull() {
            addCriterion("avg_exec_time is null");
            return (Criteria) this;
        }

        public Criteria andAvgExecTimeIsNotNull() {
            addCriterion("avg_exec_time is not null");
            return (Criteria) this;
        }

        public Criteria andAvgExecTimeEqualTo(Integer value) {
            addCriterion("avg_exec_time =", value, "avgExecTime");
            return (Criteria) this;
        }

        public Criteria andAvgExecTimeNotEqualTo(Integer value) {
            addCriterion("avg_exec_time <>", value, "avgExecTime");
            return (Criteria) this;
        }

        public Criteria andAvgExecTimeGreaterThan(Integer value) {
            addCriterion("avg_exec_time >", value, "avgExecTime");
            return (Criteria) this;
        }

        public Criteria andAvgExecTimeGreaterThanOrEqualTo(Integer value) {
            addCriterion("avg_exec_time >=", value, "avgExecTime");
            return (Criteria) this;
        }

        public Criteria andAvgExecTimeLessThan(Integer value) {
            addCriterion("avg_exec_time <", value, "avgExecTime");
            return (Criteria) this;
        }

        public Criteria andAvgExecTimeLessThanOrEqualTo(Integer value) {
            addCriterion("avg_exec_time <=", value, "avgExecTime");
            return (Criteria) this;
        }

        public Criteria andAvgExecTimeIn(List<Integer> values) {
            addCriterion("avg_exec_time in", values, "avgExecTime");
            return (Criteria) this;
        }

        public Criteria andAvgExecTimeNotIn(List<Integer> values) {
            addCriterion("avg_exec_time not in", values, "avgExecTime");
            return (Criteria) this;
        }

        public Criteria andAvgExecTimeBetween(Integer value1, Integer value2) {
            addCriterion("avg_exec_time between", value1, value2, "avgExecTime");
            return (Criteria) this;
        }

        public Criteria andAvgExecTimeNotBetween(Integer value1, Integer value2) {
            addCriterion("avg_exec_time not between", value1, value2, "avgExecTime");
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